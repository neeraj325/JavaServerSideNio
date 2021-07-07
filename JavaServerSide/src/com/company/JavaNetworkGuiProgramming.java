package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;

public class  JavaNetworkGuiProgramming extends JFrame implements ActionListener {
    private JTextField hostInput;
    private JTextArea displayArea;
    private JPanel buttonPanel,hostPanel;
    private JButton exitButton;
    private JButton timeButton;
    private JButton clearButton;
    private JLabel hostNameLabel;
    private static Socket socket = null;

    public static void main(String[] args) {
        JavaNetworkGuiProgramming frame = new JavaNetworkGuiProgramming();
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    socket.close();
                    System.exit(1);
                }catch (Exception exception){
                    System.out.println("[+]Unable to close the socket some exception occured");
                    System.exit(0);
                }
            }
        });
    }

    public JavaNetworkGuiProgramming(){
        hostPanel = new JPanel();

        hostInput = new JTextField(20);
        hostNameLabel = new JLabel("Enter host name");

        hostPanel.add(hostNameLabel);
        hostPanel.add(hostInput);

        add(hostPanel, BorderLayout.NORTH);

        displayArea = new JTextArea(0,0);
        displayArea.setWrapStyleWord(true);
        displayArea.setLineWrap(true);

        add(new JScrollPane(displayArea),BorderLayout.CENTER);

        buttonPanel = new JPanel();

        timeButton = new JButton("Get/time and date");
        exitButton = new JButton("Terminate");
        clearButton = new JButton("Clear Screen");

        timeButton.addActionListener(this);
        exitButton.addActionListener(this);
        clearButton.addActionListener(this);

        buttonPanel.add(timeButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(clearButton);

        add(buttonPanel,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton){
            System.exit(0);
        }
        String theTime;
        final int DaytimePort = 13;
        String host = hostInput.getText();

        try{
            socket = new Socket(host,DaytimePort);
            displayArea.append("GOT THE CONNECTION FROM>" + socket.getRemoteSocketAddress());

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            theTime = inputStream.readLine();

            displayArea.append("The date/time at " + host + "-->" + theTime );
        }
        catch(IOException ioException){
            displayArea.append("Reality is far more stranger than what we think!!!!");
            ioException.printStackTrace();
        }
        finally {
            if(socket != null){
                try{
                    socket.close();
                }
                catch (IOException ioException){
                    System.out.println("Unable to close the link");
                    System.exit(1);
                }
            }
        }
    }
}