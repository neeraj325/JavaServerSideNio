package com.company;

import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.net.*;


public class InetLocalHostAddress {

    private static ServerSocket serverSocket;
    private static InetAddress localhost;
    private static Socket socket;

    public static void main(String[] args) {
        try{
            localhost = InetAddress.getLocalHost();
            System.out.println(localhost);
            try{
                serverSocket = new ServerSocket(3);
            }
            catch (IOException ioException){
                System.out.println("Unable to attach port");
                System.exit(0);
            }
            try{
                socket = serverSocket.accept();
            }
            catch (IOException ioException){
                System.out.println("Unable to ");
            }
        }
        catch (UnknownHostException unknownHostException){
            System.out.println("No host found");
        }
    }
}
