/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SquareGame;

/**
 *
 * @author Graham
 */
import java.io.*;
import java.net.*;

public class Server {

    Socket player1, player2;

    public Server() throws Exception {
        ServerSocket player_connection = new ServerSocket(6969);
        System.out.println("Waiting for players");
        player1 = player_connection.accept();
        System.out.println("Player 1 connected");
        player2 = player_connection.accept();
        System.out.println("Player 2 connected");
        new PrintStream(player1.getOutputStream()).println("1");
        new PrintStream(player2.getOutputStream()).println("2");
        startGame();
    }

    public void startGame() {
        Thread play1messages = new Thread(new Runnable() {

            public void run() {
                try {
                    while (true) {
                        BufferedReader message = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                        PrintStream send = new PrintStream(player2.getOutputStream());
                        String msg = message.readLine();
                        System.out.println(msg);
                        send.println(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread play2messages = new Thread(new Runnable() {

            public void run() {
                try {
                    while (true) {
                        BufferedReader message = new BufferedReader(new InputStreamReader(player2.getInputStream()));
                        PrintStream send = new PrintStream(player1.getOutputStream());
                        String msg = message.readLine();
                        System.out.println(msg);
                        send.println(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        play1messages.start();
        play2messages.start();
    }

    public void run() throws Exception {
        ServerSocket mySS = new ServerSocket(6969);
        while (true) {
            Socket SS_accept = mySS.accept();
            BufferedReader SS_BF = new BufferedReader(new InputStreamReader(SS_accept.getInputStream()));
            String temp = SS_BF.readLine();
            System.out.println(temp);
            if (temp != null) {
                PrintStream SSPS = new PrintStream(SS_accept.getOutputStream());
                SSPS.println("Got something!");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Server myServ = new Server();
    }
}