package com.nk;

import java.net.URL;
import java.util.Scanner;
import javax.swing.text.html.HTMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

public class TempClass {
    public static void main2(String[] args) {
        String html = "<?xml version=\"1.0\" encoding=\"UTF-8\"><tests><test><id>xxx</id><status>xxx</status></test><test><id>xxx</id><status>xxx</status></test></tests></xml>";
        Document doc = Jsoup.parse(html, "", Parser.xmlParser());
        for (Element e : doc.select("test")) {
            System.out.println(e);
        }        
    }
    
	public static void main(String[] args) {
//		System.out.println((int)(Math.random()*9000)+1000);
		
//            Scanner sc = new Scanner(System.in);
//            String xmlInput = sc.nextLine();    //"<hml><hed></hed><bdy></bdy></hml>";
            String xmlInput = "<SCETAB OP='I' UD='F'>\n" +
                    "<\n" +
                    "IR>\n" +
                    "<\n" +
                    "C>100</\n" +
                    "C>\n" +
                    "<C>100</C>\n" +
                    "</\n" +
                    "IR>\n" +
                    "</SCETAB>";           
            System.out.println("XML INPUT :: \n"+xmlInput);
//            Document doc = Jsoup.parse(xmlInput.replaceAll("\n", ""), "", Parser.xmlParser());          // doc.toString()
            System.out.println("XML OUTPUT :: ");
            Document doc = Jsoup.parseBodyFragment(xmlInput.replaceAll("\n", ""));
//            System.out.println(htmlWriter.write(doc.toString().replaceAll(">\\s+",">").replaceAll("\\s+<","<")););
        }
        
}
