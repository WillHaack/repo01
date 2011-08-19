package SquareGame;    

import java.io.*;
import java.net.*;

public class Client {

    private PrintStream sendStream;
    private BufferedReader receiveReader;
    private int playerNum;

    public Client() throws Exception {
        long time = System.currentTimeMillis();
        Socket mySkt = new Socket("192.168.1.36", 6969);
        PrintStream myPS = new PrintStream(mySkt.getOutputStream());
        sendStream = myPS;
        myPS.println("");
        BufferedReader myBF = new BufferedReader(new InputStreamReader(mySkt.getInputStream()));
        receiveReader = myBF;
        System.out.println("It took you " + (System.currentTimeMillis() - time) + "ms");
        String temp = myBF.readLine();
        playerNum = Integer.parseInt(temp);
        System.out.println("You are player : " + getPlayerNum());
        System.out.println("It took you " + (System.currentTimeMillis() - time) + "ms to connect");
        Thread gameRunner = getGameRunner();
        gameRunner.start();
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public Thread getGameRunner() {
        Thread gameRunner = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    try {
                        String str = receiveReader.readLine();
                        if (str != null) {
                            response(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return gameRunner;
    }

    public void response(String str) {
        System.out.println(str);
    }

    ;
public void action(String act) {
        sendStream.println(act);
    }

    public static void main(String[] args) throws Exception {
        Client myClient = new Client();
    }
}