package com.company;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class UDPClientSide {

    private static InetAddress host;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket,outPacket;
    private static byte[] buffer, InitBuffer;
    private static int Intilizeport = 4700;

    public static void main(String[] args) {
        try{
            datagramSocket = new DatagramSocket();
        }
        catch (SocketException soEx){
            System.out.println("UNABLE TO INITIALIZE SOCKET");
        }
        accessServer();
    }

    private static void accessServer(){

        //variable,objects initialization
        InetAddress ClientAddress;
        InetAddress port;

        try{
            host = InetAddress.getLocalHost();
        }
        catch (UnknownHostException unHostEx){
            System.out.println("Not able to find local machine ip");
            System.exit(1);
        }
        try{
            String Inmessage,Outmessage;

            BufferedReader message = new BufferedReader(new InputStreamReader(System.in));
            do {
                Inmessage = "";
                Outmessage = "";
                //MESSAGE
                System.out.print("Please enter your message>");
                Outmessage =  message.readLine();

                //CLOSING THE CONNECTION BY OUTGOING
                if (Outmessage.equals("***CLOSE***")) {
                    outPacket = new DatagramPacket(Outmessage.getBytes(StandardCharsets.UTF_8),Outmessage.length(),host,Intilizeport);
                    datagramSocket.send(outPacket);
                    break;
                }
                //OUTPUT PACKET
                outPacket = new DatagramPacket(Outmessage.getBytes(StandardCharsets.UTF_8),Outmessage.length(),host,Intilizeport);
                datagramSocket.send(outPacket);


                //BUFFER SIZE INITIALIZATION
                buffer = new byte[256];

                //INPACKET
                inPacket = new DatagramPacket(buffer, buffer.length);

                datagramSocket.receive(inPacket);

                //MESSAGES
                Inmessage = new String(inPacket.getData(),0,inPacket.getLength());


                System.out.println("Response from the server>"+ Inmessage +"\n\n");

                //CLOSING THE CONNECTION BY INCOMMING
                if(Inmessage.equals("***CLOSE***")){
                    outPacket = new DatagramPacket(Outmessage.getBytes(StandardCharsets.UTF_8),Outmessage.length(),host,Intilizeport);
                    datagramSocket.send(outPacket);
                    break;
                }

            }while(!Outmessage.equals("***CLOSE***"));

        }
        catch (IOException ioException){
            System.out.println("Fatal problem during transmitting data");
            System.exit(1);
        }
        finally {
            System.out.println("Closing the connection................");
            try{
                datagramSocket.close();
            }
            catch (Exception e){
                System.out.println("Unable to close the connection some fatal error occured");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
