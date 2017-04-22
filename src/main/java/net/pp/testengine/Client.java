package net.pp.testengine;

import lombok.AllArgsConstructor;
import processing.core.PVector;

import java.io.*;
import java.net.Socket;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by Klimpen on 22/04/2017.
 */
public class Client {
    TestEngine engine;
    Server server;
    public Client(TestEngine engine) {
        this.engine = engine;
        server = new Server(engine);
    }
    public void connect(String hostName) {
        Socket kkSocket;
        try {
            kkSocket = new Socket(hostName, Server.PORT_NUM);
            new Thread(() -> {
                try {
                    //out.println("31");
                    while(kkSocket.isConnected()) {
                        server.serverWriter(new DataOutputStream(kkSocket.getOutputStream()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    while(kkSocket.isConnected()) {
                        server.serverListener(new DataInputStream(kkSocket.getInputStream()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
