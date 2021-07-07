package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;


public class ClientConnectionResponse {
    public static void main(String[] args) {
        ServerSocketConnectionResponse.Intialization();
    }
}
class ServerSocketConnectionResponse{
    private static InetAddress host;
    private static final Integer PORT = 6666;

    public static void Intialization(){

        try{
            host = InetAddress.getLocalHost();
        }

        catch(IOException ioEx){

            System.out.println("[+]Fatal error occured:" );
            ioEx.printStackTrace();
            System.exit(1);

        }

        MakingConnection();

    }
    public static void MakingConnection(){

        Socket link = null;

        try{

            link = new Socket(host,PORT);

            Scanner userEntry = new Scanner(System.in);

            Scanner inputData = new Scanner(link.getInputStream());
            PrintWriter outputData = new PrintWriter(link.getOutputStream(),true);


            String message,response;
            int numMessages =1 , numResponses = 1;



            do{

                System.out.println( "Message " + numMessages + ":");
                message = userEntry.next();
                numMessages++;
                outputData.println(message);

                response =  inputData.nextLine();

                System.out.println("Response "+ numResponses + ":"  + response);
                numResponses++;

                if(response.equals("***CLOSE***")){
                    outputData.println(response);
                    link.close();
                    break;
                }
                else if(message.equals("***CLOSE***")){
                    outputData.println(message);
                    link.close();
                    break;
                }


            }while (!message.equals("***CLOSE***"));

            System.out.println("Number of messages received:" + numMessages);
            System.out.println("Number of responses recived:" + numResponses);
        }

        catch (IOException ioEx){
            System.out.println("SOME FATAL ERROR OCCURED PROGRAM IS EXITING");
            ioEx.printStackTrace();
            System.exit(1);

        }

        finally {
            try{
                System.out.println("closing the connection..........");
                link.close();
            }
            catch (IOException ioEx){
                System.out.println("Something..................");
            }
        }
    }
}
