/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phuong;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author phuongtrant
 */
public class StreamOut {
    
    
    public static void printName(PrintWriter out)
    {
        out.println(" _____  _    _ _    _  ____  _   _  _____   _______ _____            _   _ ");
        out.println("|  __ \\| |  | | |  | |/ __ \\| \\ | |/ ____| |__   __|  __ \\     /\\   | \\ | |");
        out.println("| |__) | |__| | |  | | |  | |  \\| | |  __     | |  | |__) |   /  \\  |  \\| |");
        out.println("|  ___/|  __  | |  | | |  | | . ` | | |_ |    | |  |  _  /   / /\\ \\ | . ` |");
        out.println("| |    | |  | | |__| | |__| | |\\  | |__| |    | |  | | \\ \\  / ____ \\| |\\  |");
        out.println("|_|    |_|  |_|\\____/ \\____/|_| \\_|\\_____|    |_|  |_|  \\_\\/_/    \\_\\_| \\_|");
        out.println();
    }
    
    public static void printTime(PrintWriter out, int numOfMessage) throws InterruptedException
    {
        ProgressPrinter pp=new ProgressPrinter(numOfMessage);
        for(int i=1; i<=numOfMessage;i++)
        {
            out.println("Message "+i+" at "+(new Date()).toString());
            pp.printProgress(i);
            Thread.sleep(1000);
        }
        out.println();
    }
    
    
    public static void printTextFile(PrintWriter out, String filePath, int numOfLines, int loop, long sleep) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<String>();
        String l;
        while ((l = br.readLine()) != null) 
        {
           lines.add(l);
        }
        
        String[] linesArr= lines.toArray(new String[lines.size()]);
        
        
  
        int loopCount=0;
        
        while(loop==-1 || loopCount <= loop)
        {
            System.out.println("Loop: "+loopCount);
            
            
            int lineIndex=0;
            ProgressPrinter pp=new ProgressPrinter(linesArr.length);
            while(lineIndex<=numOfLines || lineIndex<linesArr.length)
            {
                out.println(linesArr[lineIndex]);
                lineIndex++;
                
                pp.printProgress(lineIndex+1);
                Thread.sleep(sleep);
                

            }
            
            
            loopCount++;
            
            System.out.println();
        }
        
        out.println();
        
        
        
        
    }
    
    public static void printTextFile(PrintWriter out, String filePath, int loop, long sleep) throws Exception
    {
        printTextFile(out,filePath,-1,loop,sleep);
    }
    
}
