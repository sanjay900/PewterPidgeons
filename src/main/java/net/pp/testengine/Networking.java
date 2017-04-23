package net.pp.testengine;

import processing.core.PVector;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Klimpen on 22/04/2017.
 */
public class Networking {
    public Networking(TestEngine engine) {
        this.engine = engine;
    }

    public static final int PORT_NUM = 12903;
    TestEngine engine;

    public void connect(boolean isServer, String hostName) {
        try {
            if (isServer) {
                ServerSocket serverSocket;
                serverSocket = new ServerSocket(PORT_NUM);
                new Thread(() -> {
                    while (true) {
                        try {
                            Socket clientSocket = serverSocket.accept();

                            DataOutputStream ds = new DataOutputStream(clientSocket.getOutputStream());
                            ds.writeUTF("SYNC");
                            //TODO: Pick a random seed!
                            ds.writeLong(31);
                            engine.setSeed(31);
                            createSocketThread(clientSocket, ds);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                Socket clientSocket = new Socket(hostName, Networking.PORT_NUM);
                createSocketThread(clientSocket,new DataOutputStream(clientSocket.getOutputStream()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void createSocketThread(Socket socket,DataOutputStream ds) {
        new Thread(() -> {
            try {
                while(socket.isConnected()) {
                    socketSender(ds);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                while(socket.isConnected()) {
                    socketListener(new DataInputStream(socket.getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void socketListener(DataInputStream in) throws IOException{
        String magic = in.readUTF();
        if (magic.startsWith("SYNC")) {
            engine.setSeed(in.readLong());
        }
        if(magic.startsWith("MAGIC")){
            String s = in.readUTF();
            Player p;
            if(engine.playerMap.containsKey(s)){
                p = engine.playerMap.get(s);
                if (p.equals(engine.player)) {
                    //Read in the data anyways
                    new PVector(in.readFloat(),in.readFloat(),in.readFloat());
                    in.readFloat();
                    if (in.readBoolean()) {
                        engine.window.doHit();
                    }
                    return;
                }
                p.setCamPos(new PVector(in.readFloat(),in.readFloat(),in.readFloat()));
            } else {
                engine.playerMap.put(s, p=new Player(s,new PVector(in.readFloat(),in.readFloat(),in.readFloat())));
            }
            p.setCamRot(in.readFloat());
            in.readBoolean();
        } else if (magic.startsWith("BULLET")) {
            int id = in.readInt();
            PVector mot = new PVector(in.readFloat(),in.readFloat(),in.readFloat());
            PVector pos = new PVector(in.readFloat(),in.readFloat(),in.readFloat());
            if (!engine.projectileMap.containsKey(id)) {
                engine.projectileMap.put(id,new Projectile(id,mot,pos));
            }
            if (in.readBoolean()) {
                engine.projectileMap.get(id).dead = true;
            }
        }
    }

    public void socketSender(DataOutputStream out) throws IOException{
        for(Player p : engine.playerMap.values()){
            out.writeUTF("MAGIC");
            out.writeUTF(p.getPlayerName());
            out.writeFloat(p.getCamPos().x);
            out.writeFloat(p.getCamPos().y);
            out.writeFloat(p.getCamPos().z);
            out.writeFloat(p.getCamRot());
            out.writeBoolean(p.hit);
            p.hit = false;
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
            out.writeBoolean(p.dead);
        }
    }
}