/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiverviatcp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phuon
 */
public class ReceiverViaTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
      int cTosPortNumber = 1777;
    String str;

    ServerSocket servSocket = new ServerSocket(cTosPortNumber);
    System.out.println("Waiting for a connection on " + cTosPortNumber);
    while(true)
    {
        System.out.println("Open socket...");
        Socket fromClientSocket = servSocket.accept();
        PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true);

        BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));

        while ((str = br.readLine()) != null) {
          System.out.println("The message: " + str);

          if (str.equals("bye")) {
            pw.println("bye");
            break;
          } else {
            str = "Server returns " + str;
            pw.println(str);
          }
        }
        pw.close();
        br.close();

        fromClientSocket.close();
    }
    }
    
}
