package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.*;
import java.net.*;
import java.util.Scanner;
import javax.imageio.IIOException;
import javax.swing.*;

public class EmailServer {
    private static ServerSocket serverSocket;
    private static final int PORT = 7700;
    private static Socket socket;
    private static String client1 = "devil";
    private static String client2 = "Cry";
    private static final int MAX_MESSAGES = 10;
    private static String[] mailBox1 = new String[MAX_MESSAGES];
    private static String[] mailBox2 = new String[MAX_MESSAGES];
    private static int MessageInBox1 = 0;
    private static int MessageInBox2 = 0;

    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioException){
            System.out.println("Unable to attach to port ");

            System.exit(0);
        }
        do{
            try{
                runService();
            }
            catch (InvalidClientException invalidClientException){
                System.out.println("Invalid!! username not available--->" + invalidClientException);
            }
            catch (InvalidRequestException invalidRequestException){
                System.out.println("Invalid!! Request is invalid-->" + invalidRequestException);
            }
        }while(true);
    }
    private static void runService() throws InvalidClientException, InvalidRequestException{
        try{
            socket = serverSocket.accept();

            BufferedReader IncomingData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outgoingStream = new PrintWriter(socket.getOutputStream());

            String name = IncomingData.readLine();
            String sendRead = IncomingData.readLine();

            if(!name.equals(client1)&&name.equals(client2)) throw new InvalidClientException();
            if(!sendRead.equals("send")&&sendRead.equals("read"))throw new InvalidRequestException();

            System.out.println("\n" + name + " client received");

            if(name.equals(client1)){
                if(sendRead.equals("send")){
                    SendingMessages(mailBox2,MessageInBox2,IncomingData);
                    MessageInBox2++;
                }
                else {
                    ReadingIncommingData(mailBox1,MessageInBox1,outgoingStream);
                    MessageInBox1 = 0;
                }
            }
            if(name.equals(client2)){
                if(sendRead.equals("send")){
                    SendingMessages(mailBox1,MessageInBox1,IncomingData);
                    MessageInBox1++;
                }
                else {
                    ReadingIncommingData(mailBox2,MessageInBox2,outgoingStream);
                    MessageInBox2 = 0;
                }
            }
        }
        catch (IOException ioException){
            System.out.println("Unable to contact the client");
        }
    }

    static class InvalidClientException extends Exception{
        public InvalidClientException(){
            super("No username available");
        }
        public InvalidClientException(String message){
            super(message);
        }
    }

    static class InvalidRequestException extends Exception{
        public InvalidRequestException(){
            super("Invalid request form given");
        }
        public  InvalidRequestException(String message){
            super(message);
        }
    }

    private static void SendingMessages(String[] mailBox, int numMessages, BufferedReader inputStream){
        try{
            String message = inputStream.readLine();

            if(numMessages==MAX_MESSAGES){
                System.out.println("Message box full");
            }
            else mailBox[numMessages] = message;

        }
        catch (IOException ioException){
            System.out.println("Error reading the message");
        }
    }

    private static void ReadingIncommingData(String[] mailBox, int numMessagees, PrintWriter output ){
        for(int i=0;i<numMessagees;i++){
            System.out.println("Sending message>" + i);
            output.println(mailBox[i]);
        }
    }
}
