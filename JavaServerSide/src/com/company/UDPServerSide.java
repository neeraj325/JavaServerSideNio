package com.company;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;


public class UDPServerSide {
    private static InetAddress host;
    private static int PORT = 4700;
    private static DatagramSocket datagramSocket ;
    private static DatagramPacket inPacket,outPacket;
    private static byte[] buffer, initializedBuffer;

    public static void main(String[] args) {
        try{
            System.out.println("[+]Opening the port..........");
            datagramSocket = new DatagramSocket(PORT);
            try{
                host = InetAddress.getLocalHost();
            }
            catch (UnknownHostException unHex){
                System.out.println("Unable to get the ip of local machine");
                System.exit(1);
            }
        }
        catch (SocketException soEx){
            System.out.println("Unable to create socket something went wrong ");
            soEx.printStackTrace();
            System.exit(1);
        }
        ServerCreate();

    }
    private static void initialConnection() {

        System.out.println("[+]Waiting for someone to connect.....................");

        //INITIALIZATION OF BUFFER
        initializedBuffer = new byte[256];

        try{
            //Initializtion of Incomming  packet
            inPacket = new DatagramPacket(initializedBuffer, initializedBuffer.length);
            datagramSocket.receive(inPacket);

            //Retriviation of data from the incomming packet
            String clientAddress = inPacket.getAddress().toString();
            int ClientPort = inPacket.getPort();
            String data = "Echoing back:" + inPacket.getData().toString();

            //Displaying the info of the client
            System.out.println("\n[+]Got the connection from >" + clientAddress + "\n[+]Port number>" + ClientPort);

            //Sending a one time response for confirmation
            outPacket = new DatagramPacket(data.getBytes(StandardCharsets.UTF_8),data.length(),inPacket.getAddress(),ClientPort);
            datagramSocket.send(outPacket);
        }
        catch (IOException ioException){
            System.out.println("Unable to retrive the client information");
        }
    }

    private static void ServerCreate(){
        try{
            String messageIn ,messageOut;
            initialConnection();
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            int port;
            do{
                messageIn = "";
                messageOut = "";
                //BUFFER TRAIN SLOTS SIZE
                buffer = new byte[1000];

                //INCOMING PACKET OBJECT CREATION

                inPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(inPacket);

                //MESSAGE IN
                messageIn = new String(inPacket.getData(),0,inPacket.getLength());
                System.out.println("MESSAGE FROM SERVER>" + messageIn + "\n\n");

                //CLOSING THE CONNECTION THROUGH INCOMMING
                if(messageIn.equals("***CLOSE***")){
                    outPacket = new DatagramPacket(messageIn.getBytes(StandardCharsets.UTF_8),messageIn.length(),host,inPacket.getPort());
                    datagramSocket.send(outPacket);
                    break;
                }


                System.out.print("PLESAE ENTER YOUR MESSAGE>");
                messageOut = userEntry.readLine();

                if(messageOut.equals("***CLOSE***")){
                    //QUITTING PACKET
                    outPacket = new DatagramPacket(messageOut.getBytes(StandardCharsets.UTF_8),messageOut.length(), inPacket.getAddress(), inPacket.getPort());
                    datagramSocket.send(outPacket);
                    break;
                }

                //OUTPUT PACKET
                outPacket = new DatagramPacket(messageOut.getBytes(StandardCharsets.UTF_8),messageOut.length(), inPacket.getAddress(), inPacket.getPort());
                datagramSocket.send(outPacket);

            }while (!messageIn.equals("***CLOSE***"));

        }
        catch (IOException ioEx){
            System.out.println("[+]UNABLE TO CLOSE THE PORT");
            System.exit(1);
        }
        finally {
            System.out.println("Closing the connection..................");
            datagramSocket.close();
        }
    }
}
