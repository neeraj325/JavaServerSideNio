package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.*;
import java.net.*;

public class MultiThreadedServerProgramming {

    private static final int PORT = 7777;
    public static ServerSocket serverSocket;
    public static  Socket client;

    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioException){
            System.out.println("Unable to attach to port >" + ioException);
            System.exit(1);
        }

        Resource item = new Resource(1);

        Producer producer = new Producer(item);
        producer.start();

        do{
            try{
                client = serverSocket.accept();
                System.out.println("\nNew client accepted");

                Client consumption = new Client(client, item);
                consumption.start();
            }
            catch (IOException ioException){
                System.out.println(ioException);
            }
        }while (true);

    }
}
class Resource {
    private int numResources;
    private int MAX_RESOURCES = 5;
    private Socket socket;
    private static BufferedReader bufferedReader ;
    private static PrintWriter printWriter;

    public Resource(int startLevel){
        numResources=startLevel;
    }

    public int getLevel(){
        return numResources;
    }

    public synchronized int addOne(){
        try{
            while(numResources >= MAX_RESOURCES){
                System.out.println("Resource storage is full please wait for few moments");
                wait();
            }
            numResources++;
            notifyAll();

            try{
                printWriter = new PrintWriter(MultiThreadedServerProgramming.client.getOutputStream(),true);
                bufferedReader = new BufferedReader(new InputStreamReader(MultiThreadedServerProgramming.client.getInputStream()));
            }
            catch (IOException ioException){
                System.out.println(ioException);
            }

            try{
                String message = "Please enter '1' or '0' to request for resources else exit";

                printWriter.println(message);

                String Response = bufferedReader.readLine();

                if(Response == "1"){
                    addOne();
                }
                else if(Response.equals("******WRONG VALUE DETECTED PRODUCER SERVER RESTARTING******")){
                    System.out.println("[+}A wrong value was received the server is restarting ");
                }

                else{
                    try{
                        socket.close();
                    }
                    catch (IOException ioException){
                        System.out.println("Unable to close the socket>" + ioException);
                    }
                }
            }
            catch (IOException ioException){
                System.out.println(ioException);
            }
        }
        catch (InterruptedException interruptedException){
            System.out.println("A thread has been interupted>" + interruptedException);
        }
        return numResources;
    }
    public synchronized int takeOne(){
        try{
            if(numResources == 0){
                System.out.println("Dear costomer please wait the resources are being refilled");
                wait();
            }
            numResources--;
            notifyAll();

            try{
                String message = "Please enter '1' or '0' to request for resources else exit";

                printWriter.println(message);

                String Response = bufferedReader.readLine();

                if(Response == "1"){
                    takeOne();
                }
                else if(Response.equals("******WRONG VALUE DETECTED CLIENT SERVER RESTARTING******")){
                    System.out.println("*************A WRONG VALUE WAS DETECTED THE SERVER IS RESTARTING******************");
                }
                else {
                    try{
                        socket.close();
                    }
                    catch (IOException ioException){
                        System.out.println("Unable to close the port");
                    }
                }
            }
            catch (IOException ioException){
                System.out.println(ioException);
            }
        }
        catch (InterruptedException interruptedException){
            System.out.println("The thread has been suspended>" + interruptedException);
        }
        return numResources;
    }
}

class Producer extends Thread {
    private Resource item;
    private static BufferedReader InputStreamReader, userEntryReader;
    private static PrintWriter outStream;

    public Producer(Resource resource){
        item = resource;
    }


    public void run(){
        int newLevel;
        int pause;

        try{
            InputStreamReader = new BufferedReader(new InputStreamReader(MultiThreadedServerProgramming.client.getInputStream()));
            outStream = new PrintWriter(MultiThreadedServerProgramming.client.getOutputStream(), true);
            userEntryReader = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (IOException ioException){
            System.out.println(ioException);
        }
        do{
            try{
                newLevel = item.addOne();

                System.out.println("<Producer>[new_item][itemProducerOrderNum]>" + newLevel);

                pause = (int)(Math.random()*3000);

                Thread.sleep(pause);
            }
            catch (InterruptedException interruptedException){
                System.out.println("Thread Interupted>" + interruptedException);
            }
        }while (true);
    }
}

class Client extends Thread {
    private static Socket client;
    private Resource item;
    private static BufferedReader bufferedReader,InputStream;
    private static PrintWriter printWriter;

    public Client(Socket socket, Resource resource) {
        client = socket;
        item = resource;

        try {
            printWriter = new PrintWriter(MultiThreadedServerProgramming.client.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(MultiThreadedServerProgramming.client.getInputStream()));
            InputStream = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }

    public void run() {
        int ResourcesLeft;

        try {

            do {
                String message="",response="";

                ResourcesLeft = item.takeOne();

                System.out.println("[Resource_taken][Resources_left]>" + ResourcesLeft);

                message = bufferedReader.readLine();

                System.out.println(message);

                response = InputStream.readLine();

                if(!response.equals("0")&&(!response.equals("1"))){
                    System.out.println("******INVALID*****");
                    printWriter.println("******WRONG VALUE DETECTED CLIENT SERVER RESTARTING******");
                }
                else{
                    printWriter.println(response);
                }

            } while (true);
        } catch (IOException ioException) {
            System.out.println(ioException);
            System.exit(0);
        }

    }
}

