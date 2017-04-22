package net.pp.testengine;

import lombok.AllArgsConstructor;
import processing.core.PVector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnexpectedException;
import java.util.InputMismatchException;

/**
 * Created by Klimpen on 22/04/2017.
 */
@AllArgsConstructor
public class Client {
    TestEngine engine;

    public void connect(String hostName) {
        Socket kkSocket;
        try {
            kkSocket = new Socket(hostName, Server.PORT_NUM);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(kkSocket.getInputStream()));
            new Thread(()->{
                while(kkSocket.isConnected()){
                    out.println("MAGIC" + "," + engine.player.getPlayerName());
                    out.println(engine.player.getPlayerName() + "," + engine.player.getCamPos().x);
                    out.println(engine.player.getPlayerName() + "," + engine.player.getCamPos().y);
                    out.println(engine.player.getPlayerName() + "," + engine.player.getCamPos().z);
                    out.println(engine.player.getPlayerName() + "," + engine.player.getCamRot());
                }
            }).start();
            new Thread(()->{
                try {
                    while(kkSocket.isConnected()) {
                            String[] starter = in.readLine().split(",");
                        if(starter[0].equals("MAGIC")){
                            String s = starter[1];
                            if(engine.playerMap.containsKey(s) && !engine.player.getPlayerName().equals(s)){
                                Player p = engine.playerMap.get(s);
                                p.setCamPos(new PVector(
                                        checkFloat(p, in.readLine().split(",")),
                                        checkFloat(p, in.readLine().split(",")),
                                        checkFloat(p, in.readLine().split(","))));
                                p.setCamRot(checkFloat(p, in.readLine().split(",")));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float checkFloat(Player p, String[] stringIn){
        if(p.equals(stringIn[0])) {
            return Float.parseFloat(stringIn[1]);
        } throw new InputMismatchException();

    }
}
