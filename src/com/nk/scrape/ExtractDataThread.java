package com.nk.scrape;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.nk.commons.IOUtilities;
import com.nk.commons.RegexUtilities;

public class ExtractDataThread extends Thread{
    private String siteName;
    private List blacklistList;
    private Map<String, String> replacementMap;
    private String outputFilePath;
    
    public ExtractDataThread(String siteName, List<String> blacklistList, Map<String, String> replacementsMap, String outputFilePath) {
        this.siteName = siteName;
        this.blacklistList = blacklistList;
        this.replacementMap = replacementsMap;
        this.outputFilePath = outputFilePath;
    }
    
    @Override
    public void run(){
        extractData();
    }
    
    public void extractData(){
        List<String> innerHtmlSentences_aL = new ArrayList<String>();
        int count = 0;
        try {
            URL url = new URL(siteName);  // http://www.ecomagnet.fr/
            Document doc = Jsoup.parse(url, 4000);          // doc.toString()
            String htmlText = doc.body().text();
            
//            Pattern p = Pattern.compile("^[A-Z].*\\.)$"/*,Pattern.CASE_INSENSITIVE*/);            // using pattern with flags
            Pattern p = Pattern.compile("[\"']?[A-Z][^.?!]+((?![.?!]['\"]?\\s[\"']?[A-Z][^.?!]).)+[.?!'\"]+");
            Matcher m = p.matcher(htmlText);
            while(m.find()){                         // Matcher : find(), group(), start() and end() methods
            	innerHtmlSentences_aL.add(m.group());
            }

            StringBuffer content = new StringBuffer();
            for(String str : innerHtmlSentences_aL){
            	
            	RegexUtilities ru = new RegexUtilities();
            	if(! ru.isBlacklistedStringFound(str, blacklistList)){
                	String str_wordsReplaced = ru.replaceWordsInSentence(str, replacementMap);
    	            content.append(siteName+ " : Line "+(count+1)+":: "+str_wordsReplaced+"\n");
    	            count++;
            		
            	}
            }
            
            synchronized (this) {
            	IOUtilities iou = new IOUtilities();
            	String opPath = this.outputFilePath.substring(0,this.outputFilePath.lastIndexOf(".txt"))
            			+ "_"+((int)(Math.random()*9000)+1000)
            			+ this.outputFilePath.substring(this.outputFilePath.lastIndexOf(".txt"),this.outputFilePath.length());
            	iou.writeContentToTextFile(opPath, content);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ExtractDataThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExtractDataThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}