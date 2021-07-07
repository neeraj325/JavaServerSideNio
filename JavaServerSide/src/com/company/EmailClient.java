package com.company;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.io.*;

public class EmailClient {
    private static Socket socket;
    private static InetAddress host;
    private static final int PORT = 7700;
    public static void main(String[] args) {
        try{
            host = InetAddress.getLocalHost();
        }
        catch (IOException ioException){
            System.out.println("Unable to attach to port>>");
            ioException.printStackTrace();
            System.exit(1);
        }
        ConnectServer();
    }
    private static void ConnectServer(){
        try{
            socket = new Socket(host,PORT);

            do{
                BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader InputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter OutputStream = new PrintWriter(socket.getOutputStream());
                System.out.print("\nPlease enter the username for verification>>");

                String username = userEntry.readLine();
                OutputStream.println(username);
                
                System.out.print("\nPlease enter 'send' or 'read' to proceed further to email server");
                String sendOrRecive = userEntry.readLine();

                if(sendOrRecive.equals("send")){
                    System.out.print("\nPlease enter the message>");
                    String message = userEntry.readLine();
                    OutputStream.println(sendOrRecive);
                    OutputStream.println(message);
                }else{
                    System.out.print("\nContacting the server to receive messages");
                    OutputStream.println(sendOrRecive);
                    String TotalMailBoxMessage = InputStream.readLine();
                    System.out.println(TotalMailBoxMessage);
                }
                System.out.println("Please enter 'n' to exit or press 'c' to continue");
                String response = userEntry.readLine();

                if(!response.equals('n')&&!response.equals('c')){
                    System.out.println("You entered wrong values");
                    System.exit(0);
                }
                else if(response.equals('n')){
                    System.out.println("Exiting..........");
                    System.exit(0);
                }
            }while (true);

        }
        catch (IOException ioException){
            System.out.println("Unable to establish connection");
        }
    }
}
