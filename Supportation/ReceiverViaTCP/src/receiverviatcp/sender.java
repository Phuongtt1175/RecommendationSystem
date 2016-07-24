/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiverviatcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author phuon
 */
public class sender {
    public static void main(String[] args) throws IOException 
    {
        Socket socket1;
    int portNumber = 1777;
    String str = "initialize";

    socket1 = new Socket(InetAddress.getLocalHost(), portNumber);
    
    BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

    PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

    pw.println(str);

    while ((str = br.readLine()) != null) {
      System.out.println(str);
      pw.println("bye");

      if (str.equals("bye"))
        break;
    }

    br.close();
    pw.close();
    socket1.close();
    }
    
}
