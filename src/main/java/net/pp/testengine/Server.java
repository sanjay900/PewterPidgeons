package net.pp.testengine;

import processing.core.PVector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;

/**
 * Created by Klimpen on 22/04/2017.
 */
public class Server {

    public static final int PORT_NUM = 12903;
    TestEngine engine;

    public void connect() {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT_NUM);
            new Thread(()->{
                while(true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        new Thread(() -> {
                            try {
                                PrintWriter out =
                                        new PrintWriter(clientSocket.getOutputStream(), true);
                                while(clientSocket.isConnected()) {
                                    serverWriter(clientSocket, out);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        new Thread(() -> {
                            try {
                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(clientSocket.getInputStream()));

                                while(clientSocket.isConnected()) {
                                    serverListener(clientSocket, in);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverListener(Socket clientSocket, BufferedReader in) throws IOException{
        String[] starter = in.readLine().split(",");
        if(starter[0].equals("MAGIC")){
            String s = starter[1];
            if(engine.playerMap.containsKey(s)){
                Player p = engine.playerMap.get(s);
                p.setCamPos(new PVector(
                        checkFloat(p, in.readLine().split(",")),
                        checkFloat(p, in.readLine().split(",")),
                        checkFloat(p, in.readLine().split(","))));
                p.setCamRot(checkFloat(p, in.readLine().split(",")));
            }
        }
    }

    private float checkFloat(Player p, String[] stringIn){
        if(p.getPlayerName().equals(stringIn[0])) {
            return Float.parseFloat(stringIn[1]);
        } throw new InputMismatchException();

    }

    public void serverWriter(Socket clientSocket, PrintWriter out) throws IOException{
        for(Player p : engine.playerMap.values()){
            out.println("MAGIC" + "," + p.getPlayerName());
            out.println(p.getPlayerName() + "," + p.getCamPos().x);
            out.println(p.getPlayerName() + "," + p.getCamPos().y);
            out.println(p.getPlayerName() + "," + p.getCamPos().z);
            out.println(p.getPlayerName() + "," + p.getCamRot());
        }
    }
}
