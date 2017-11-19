package com.nk.scrape;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException; 
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class WebsiteScrap {

    public void scrapeText(String[] args){
        List<String> al = new ArrayList<String>();
        int count = 0;
        String urlStr = "";
        String fileStr = "";
        try{
            if(args.length>0){
                if(args[0]!=null){
                    urlStr = args[0];
                }
            }else{
                urlStr = "http://www.docphone.org/";        // http://www.ecomagnet.fr/
            }
            
//            String timestamp = new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
            if(args.length>1){
                if(args[1]!=null){
                    fileStr = args[1]+"/sentences.txt";
                }
            }else{
                fileStr = "D:/sentences.txt";    // Windows
//                fileStr = "/home/krawler/Documents/sentences.txt";    // Unix
            }
            
            URL url = new URL(urlStr);  
            Document doc = Jsoup.parse(url, 3000);
            Element el = doc.body();
            String htmlText = el.text();
            
            Matcher m = Pattern.compile("[\"']?[A-Z][^.?!]+((?![.?!]['\"]?\\s[\"']?[A-Z][^.?!]).)+[.?!'\"]+").matcher(htmlText);
            while(m.find()){
                al.add(m.group());
            }
            
            StringBuffer content = new StringBuffer();
            for(String str : al){
	            content.append("Line "+(count+1)+": "+str+"\n");
	            count++;
            }
            
            // Write content to Text File
            try{
                File file = new File(fileStr);     // D:/ScrapedText/"+timestamp+"_sentences.txt
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content.toString());
                bw.close();
            }catch(IOException e){
                System.out.println("IOException caused. "+e);
            }
            
        } 
        catch (MalformedURLException ex){
            Logger.getLogger(WebsiteScrap.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex){
            Logger.getLogger(WebsiteScrap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Parsing a txt file with data (filePath, replacementWord)
    public static void parseFileAndScrapeText(String[] args){
        BufferedReader crunchifyBuffer = null, crunchifyBuffer2 = null, crunchifyBuffer3 = null;
        String inputFileStr = "", inputFileStr2 = "", inputFileStr3 = "";
        String outputFileStr = "";
        String crunchifyLine, crunchifyLine2, crunchifyLine3;
        try {
            if(args.length>3){
                if(args[0]!=null){
                	inputFileStr = args[0];
                }
                if(args[1]!=null){
                	inputFileStr2 = args[1];
                }
                if(args[2]!=null){
                	inputFileStr3 = args[2];
                }
                if(args[3]!=null){
                	// Random number : ((int)(Math.random()*9000)+1000)
                    outputFileStr = args[3]+"\\sentences.txt";
                }
//            }else{
//            	outputFileStr = "D:\\sentences.txt";    // Windows
////                outputFileStr = "/home/krawler/Documents/sentences.txt";    // Unix
            }
            

            // Read URL file in java line by line?
            crunchifyBuffer = new BufferedReader(new FileReader(inputFileStr));	// "/home/krawler/Documents/Crunchify-CSV-to-ArrayList.txt"
            List<String> siteList = new ArrayList<String>();
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
//              System.out.println("Raw CSV data: " + crunchifyLine + "\nConverted ArrayList data: " + crunchifyCSVtoArrayList(crunchifyLine) + "\n");
                List<String> list = crunchifyCSVtoArrayList(crunchifyLine);
                if(list.size()==1){
                    siteList.add(list.get(0));
                }
            }
            
            // Read blacklist file in java line by line?
            crunchifyBuffer2 = new BufferedReader(new FileReader(inputFileStr2));	// "/home/krawler/Documents/Crunchify-CSV-to-ArrayList.txt"
            List<String> blacklistList2 = new ArrayList<String>();
            while ((crunchifyLine2 = crunchifyBuffer2.readLine()) != null) {
//              System.out.println("Raw CSV data: " + crunchifyLine2 + "\nConverted ArrayList data: " + crunchifyCSVtoArrayList(crunchifyLine2) + "\n");
                List<String> list = crunchifyCSVtoArrayList(crunchifyLine2);
                if(list.size()==1){
                	blacklistList2.add(list.get(0));
                }
            }
            if(blacklistList2.size()>5){
            	throw new Exception();
            }
            
            // Read blacklist file in java line by line?
            crunchifyBuffer3 = new BufferedReader(new FileReader(inputFileStr3));	// "/home/krawler/Documents/Crunchify-CSV-to-ArrayList.txt"
            Map<String, String> replacementsMap3 = new HashMap<String, String>();
            while ((crunchifyLine3 = crunchifyBuffer3.readLine()) != null) {
//              System.out.println("Raw CSV data: " + crunchifyLine2 + "\nConverted ArrayList data: " + crunchifyCSVtoArrayList(crunchifyLine2) + "\n");
                List<String> list = crunchifyCSVtoArrayList(crunchifyLine3);
                if(list.size()==2){
                	replacementsMap3.put(list.get(0), list.get(1));
                }
            }
            if(replacementsMap3.size()>10){
            	throw new Exception();
            }
            	
            for(String siteName : siteList){
              ExtractDataThread edt = new ExtractDataThread(siteName, blacklistList2, replacementsMap3, outputFileStr);
              edt.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (crunchifyBuffer != null) crunchifyBuffer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }    
    }
    
    // Utility which converts CSV to ArrayList using Split Operation (Create new Utility class)
    public static ArrayList<String> crunchifyCSVtoArrayList(String crunchifyCSV) {
        ArrayList<String> crunchifyResult = new ArrayList<String>();

        if (crunchifyCSV != null) {
            String[] splitData = crunchifyCSV.split("\\s*;\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                        crunchifyResult.add(splitData[i].trim());
                }
            }
        }
        return crunchifyResult;
    }
    
}
