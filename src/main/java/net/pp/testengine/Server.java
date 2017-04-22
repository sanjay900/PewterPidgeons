package net.pp.testengine;

import processing.core.PVector;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Klimpen on 22/04/2017.
 */
public class Server {
    public Server(TestEngine engine) {
        this.engine = engine;
    }

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
                                //out.println("31");
                                while(clientSocket.isConnected()) {
                                    serverWriter(new DataOutputStream(clientSocket.getOutputStream()));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        new Thread(() -> {
                            try {
                                while(clientSocket.isConnected()) {
                                    serverListener(new DataInputStream(clientSocket.getInputStream()));
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

    public void serverListener(DataInputStream in) throws IOException{
        String magic = in.readUTF();
        if(magic.startsWith("MAGIC")){
            String s = in.readUTF();
            Player p;
            if(engine.playerMap.containsKey(s)){
                p = engine.playerMap.get(s);
                if (p.equals(engine.player)) {
                    //Read in the data anyways
                    new PVector(in.readFloat(),in.readFloat(),in.readFloat());
                   in.readFloat();
                    return;
                }
                p.setCamPos(new PVector(in.readFloat(),in.readFloat(),in.readFloat()));
            } else {
                engine.playerMap.put(s, p=new Player(s,new PVector(in.readFloat(),in.readFloat(),in.readFloat())));
            }
            p.setCamRot(in.readFloat());
        } else if (magic.startsWith("BULLET")) {
            int id = in.readInt();
            PVector mot = new PVector(in.readFloat(),in.readFloat(),in.readFloat());
            PVector pos = new PVector(in.readFloat(),in.readFloat(),in.readFloat());
            if (engine.projectileMap.containsKey(id)) {
                engine.projectileMap.get(id).updateWith(mot,pos);
            } else {
                engine.projectileMap.put(id,new Projectile(id,mot,pos));
            }
        }
    }

    public void serverWriter(DataOutputStream out) throws IOException{
        for(Player p : engine.playerMap.values()){
            out.writeUTF("MAGIC");
            out.writeUTF(p.getPlayerName());
            out.writeFloat(p.getCamPos().x);
            out.writeFloat(p.getCamPos().y);
            out.writeFloat(p.getCamPos().z);
            out.writeFloat(p.getCamRot());
        }
        for (Projectile p : engine.projectileMap.values()) {
            out.writeUTF("BULLET");
            out.writeInt(p.getId());
            out.writeFloat(p.getMotion().x);
            out.writeFloat(p.getMotion().y);
            out.writeFloat(p.getMotion().z);

            out.writeFloat(p.getPosition().x);
            out.writeFloat(p.getPosition().y);
            out.writeFloat(p.getPosition().z);
        }
    }
}