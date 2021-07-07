package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static ServerSocket serverSocket;
    public static final Integer PORT = 6666;

    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("[+]WAITING FOR THE CLIENT TO CONNECT");
        }
        catch (IOException ioEX){
            System.out.println("Error unable to create socket");
            System.exit(1);
        }
        ServerClient();
    }
    static void ServerClient(){
        Socket link = null;
        try {

            link = serverSocket.accept();

            Scanner userInput = new Scanner(System.in);

            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            String message, response;

            int messages = 0, numResponses = 0;

            do{

                response = input.next();
                System.out.println("Response " + numResponses + ":" +  response);
                numResponses++;

                System.out.println("Message " + messages + ":");
                message = userInput.next();

                output.println(message);
                messages++;


                if(response.equals("***CLOSE***")){
                    output.println(response);
                    link.close();
                    break;
                }else if(message.equals("***CLOSE***")){
                    output.println(message);
                    link.close();
                    break;
                }

            }while (!message.equals("***CLOSE***"));

            System.out.println("Number of messages send:" + messages);
            System.out.println("Number of responses received:" + numResponses);
        }
        catch (IOException ioEx){
            System.out.println("[+]SOME FATAL ERROR DURING DATA TRANSMISSION");
            ioEx.printStackTrace();

            System.exit(1);
        }
        finally {
            try{
                System.out.println("\nClosing connection.......");

                link.close();
            }
            catch (IOException ioEx){
                System.out.println("unable to close connection");
                System.exit(1);
            }
        }
    }
}