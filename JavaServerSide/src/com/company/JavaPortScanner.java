package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.chrono.IsoChronology;

public class JavaPortScanner extends JFrame implements ActionListener {

    private  JButton BeginPortScan,TerminatePortScan,Exit;
    private  JPanel buttonPanel,HostPanel;
    private  JTextArea displayArea;
    private  JTextField textField;
    private JLabel hostLabel;
    private static Socket socket;
    private static InetAddress localhost;

    public static void main(String[] args) {
        JavaPortScanner frame = new JavaPortScanner();

        frame.setSize(500,500);
        frame.setVisible(true);

        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if(socket != null){
                            try{
                                socket.close();
                            }catch (IOException ioException){
                                ioException.printStackTrace();
                                System.out.println("Unable to close the socket.............");
                                System.exit(0);
                            }
                        }
                        System.exit(0);
                    }

                }
        );
    }

    public JavaPortScanner(){

        try{
            localhost = InetAddress.getLocalHost();
        }
        catch (UnknownHostException unknownHostException){
            System.out.println("Warning unable to get the localhost!!!");
        }
        HostPanel = new JPanel();
        buttonPanel = new JPanel();

        hostLabel = new JLabel("Host Name");
        textField = new JTextField(localhost.toString(),20);

        HostPanel.add(hostLabel);
        HostPanel.add(textField);

        add(HostPanel, BorderLayout.NORTH);

        displayArea = new JTextArea(1,1);

        displayArea.setWrapStyleWord(true);
        displayArea.setLineWrap(true);

        displayArea.setAutoscrolls(true);
        add(displayArea, BorderLayout.CENTER);

        BeginPortScan = new JButton("StartScan");
        TerminatePortScan = new JButton("Terminate Scan");
        Exit = new JButton("Exit");

        BeginPortScan.addActionListener(this);
        TerminatePortScan.addActionListener(this);
        Exit.addActionListener(this);

        buttonPanel.add(BeginPortScan);
        buttonPanel.add(TerminatePortScan);
        buttonPanel.add(Exit);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Exit){
            System.exit(0);
        }
        if(e.getSource() == BeginPortScan){
            for(int i = 0;i<5;i++){
                try{
                    socket = new Socket(textField.getText(),i);
                    displayArea.append("There is a server running at port::: " + i + "\n");
                }
                catch (IOException ioException){
                    displayArea.append("There is no server running at port>>>" + i + "\n");
                }
            }
        }
    }
}
