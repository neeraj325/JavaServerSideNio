package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class ServerSocketChannelProgramming {

    private static ServerSocketChannel serverSocketChannel;
    private static SocketChannel socketChannel;
    private static InetSocketAddress inetSocketAddress;
    private static ServerSocket serverSocket;
    private static final int PORT = 7777;

    public static void main(String[] args) {
        try{

            //SERVER SOCKET CHANNEL
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();
            //SOCKET CHANNEL
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            try{
                inetSocketAddress = new InetSocketAddress(PORT);
            }

            catch (Exception e){
                e.printStackTrace();
            }
            serverSocket.bind(inetSocketAddress);
        }

        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
