/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phuong;

/**
 *
 * @author phuongtrant
 */
public class ProgressPrinter {
    
    int currPosition=-1;
    int scale=4;
    
    int multiScale=10;
    int max;
    
    public ProgressPrinter(int max)
    {
        this.max=max;
    }
    
    void printProgress(int curr)
    {
        String prog="";
        int percent= (curr*100)/max;
        
        int position= (curr*scale)/max;
        
        if(position == currPosition)
            return;
        for(int i=0;i<scale*multiScale;i++)
        {
            if(i<=position*multiScale)
                prog=prog+"=";
            else
                prog=prog+"-";
                    
        }
        
        System.out.println("["+prog+"]"+" "+percent+"%");
        currPosition=position;
        
        
     
    }
    
}
