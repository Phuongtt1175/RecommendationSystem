/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phuong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author phuongtrant
 */
public class streamtext {

    /**
     * @param args the command line arguments
     */
    
    
  
    
    public static void main(String[] args) throws Exception 
    {
        // TODO code application logic here
        int port=9090;
        int numOfMessage = 100;
        
        if(args.length>0)
        {
             port = Integer.parseInt(args[0]);
        }
        if(args.length>1)
        {
            numOfMessage = Integer.parseInt(args[1]);
        }
        
        
        ServerSocket listener = new ServerSocket(port);
        try 
        {
            
            while (true) 
            {
                System.out.println("Waiting for client...");
                Socket socket = listener.accept();
                
                
                String clientIP=socket.getInetAddress().toString();
              
                System.out.println("Client has connected at: "+clientIP);
                System.out.println("BEGIN MESSAGE!");
                
                //Start write for 10s
                try 
                {
                    PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);
                    out.println("Hello Client");
                    out.println("Your IP is: "+clientIP);
                    
                    
                    // PRINT NAME
                    
                    StreamOut.printName(out);
                    
                    //END PRINT NAME
                    
                    //BEGIN SEND MESSAGES
                  
                    //StreamOut.printTime(out, numOfMessage);
                    
                    StreamOut.printTextFile(out, "D:\\WS\\JWS\\Para.txt", 1, 100);
                    
                    out.println("MESSAGES DONE! GOODBYE!");
                    
                    
                    
                    // DONE
                    System.out.println("END MESSAGE!");
                    
                    
                } 
                catch(InterruptedException e)
                {
                    System.out.println(e.toString());
                }
                finally 
                {
                    socket.close();
                    
                    System.out.println("Session was closed!");
                    System.out.println();
                }
            }
        }
        finally 
        {
            listener.close();
        }

      
    }
    
    
        
    
}
