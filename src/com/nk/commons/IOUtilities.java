package com.nk.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IOUtilities {
	public synchronized void writeContentToTextFile(String fileWithPath, StringBuffer content){
        try{
//        	synchronized (this) {
            File file = new File(fileWithPath);     // D:/ScrapedText/"+timestamp+"_sentences.txt
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content.toString());
            bw.close();
//            }
        }catch(IOException e){
            System.out.println("IOException caused. "+e);
        }
		
	}
}
