package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.net.*;
import java.util.Date;

public class JavaNetworkDateTimeProgrammingClientSide {
    private static ServerSocket serverSocket ;
    final static int DateTimePort = 13;
    private static Socket socket;
    private static InetAddress localhost;

    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(DateTimePort);
        }
        catch (Exception e){
            System.out.println("[+]Unable to open the server...............");
            System.exit(1);
        }
        sendDate();
    }
    private static void sendDate(){
        try{
            localhost = InetAddress.getLocalHost();
            System.out.println(localhost);
            do{
                socket = serverSocket.accept();
                System.out.println("Got he connection from>" + socket.getRemoteSocketAddress());

                PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

                Date date = new Date();

                output.println(date.getTime() + "::" + date.getDate());

            }while (true);
        }
        catch (Exception e){
            System.out.println("SOME ERROR OCCURED");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
