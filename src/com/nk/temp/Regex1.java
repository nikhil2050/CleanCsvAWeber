/******************************************************************************
 * RegEx
 * Version : 0.1
 ******************************************************************************/

package com.nk.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex1 {
    public static void main(String[] args) {
        meth9();
    }
    
    // http://www.journaldev.com/634/java-regular-expression-tutorial-with-examples
    // important methods of Pattern and Matcher classes
    // 1. We can create a Pattern object with flags. For example Pattern.CASE_INSENSITIVE enables case insensitive matching.
    // 2. Pattern class also provides split(String) that is similar to String class split() method.
    // 3. Pattern class toString() method returns the regular expression String from which this pattern was compiled.
    // 4. Matcher classes have start() and end() index methods that show precisely where the match was found in the input string.
    // 5. Matcher class also provides String manipulation methods replaceAll(String replacement) and replaceFirst(String replacement).
    public static void meth9(){
        Pattern p = Pattern.compile("ab", Pattern.CASE_INSENSITIVE);            // using pattern with flags
        Matcher m = p.matcher("ABcabdAb");
        while(m.find()){                                                        // Matcher : find(), group(), start() and end() methods
            System.out.println("Found text :"+ m.group());
            System.out.println("Starting at:"+ m.start());
            System.out.println("Ending at  :"+ m.end() + "\n");
        }
        
        p = Pattern.compile("1*2");
        m = p.matcher("11234512678");
        System.out.println("\nusing replaceFirst : "+ m.replaceFirst("_"));     // Matcher : replaceFirst() and replaceAll() methods
        System.out.println("using replaceAll : "+ m.replaceAll("_"));

        p = p.compile("\\W");                                                   
        String[] words = p.split("one@two#three:four$five");                    // Pattern : split() method
        for(String word : words){
            System.out.println("Split :"+ word);
        }
    }
    
    // http://www.journaldev.com/634/java-regular-expression-tutorial-with-examples
    // ****************BACK-REFERENCE****************
    public static void meth8(){
        System.out.println (Pattern.matches("(\\w\\d)\\1", "a2a2"));
        System.out.println (Pattern.matches("(\\w\\d)\\1", "a2b2"));
        System.out.println (Pattern.matches("(AB)(B\\d)\\2\\1","ABB2B2AB"));
        System.out.println (Pattern.matches("(AB)(B\\d)\\2\\1","ABB2B3AB"));

    }
    
    // http://www.ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/
    // ****************Modifying/Substitution (Values in text can be replaced with new values)****************
    public static void meth7(){
        String input = "User clientId=23421. Some more text clientId=33432. This clientNum=100";
        Pattern p = Pattern.compile("(clientId=)(\\d*)");
        Matcher m = p.matcher(input);

        StringBuffer result = new StringBuffer();
        while(m.find()){
            System.out.println("Masking : "+m.group(2));
            m.appendReplacement(result, m.group(1)+"*****");
        }
        m.appendTail(result);
        System.out.println("result : "+result);
    }
    
    // http://www.ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/
    // ****************Extracting/Capturing (Specific values can be selected out of a large complex body of text. These values can be used in the application)****************
    public static void meth6(){     
        String input = "I have a cat mat sat, but I like my dog better.";
        Pattern p = Pattern.compile("(mouse|.at|dog|wolf|bear|human)");
        Matcher m =p.matcher(input);
        while(m.find()){
            System.out.println(m.group());
        }
        
    }
    
    // http://www.ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/
    // ****************Matching/Validating whole ****************
    public static void meth5(){     
        List<String> input = new ArrayList<String>();
        input.add("123-45-6789");
        input.add("9876-5-4321");
        input.add("987-65-4321 (attack)");
        input.add("987-65-4321 ");
        input.add("192-83-7465");
        input.add("192837465");
        for(String s : input){
            if(s.matches("^(\\d{3}-?\\d{2}-?\\d{4})$")){
                System.out.println(s+" is matched.");
            }else{
                System.out.println(s+" NOT matched.");
            }
        }
    }
    
    
    
    
    // 6.5 Finding duplicated words
    public static void meth4(){ 
//        Pattern p = Pattern.compile("\b(\w+)")
    }
    
    // 6.4. Building a link checker  
    // Example allows to extract all valid links from a webpage. It does not consider links which start with "javascript:" or "mailto:".
    public static void meth3(){ 
        Pattern htmlTag = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");
        Pattern link = Pattern.compile("href=\"");
    }
    
    
    // 6.3 Check for a certain number range (Check if a text contains a number with 3 digits.)
    public static void meth2(){ 
        String regex = "\\d{3}";

        String s = "12340";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(s);
        System.out.println(s+" : "+matcher.find());

        s= "0";
        matcher = p.matcher(s);
        System.out.println(s+" : "+matcher.find());
        
        s = "29 Kasdkf 23000 Kdsdf";
        matcher = p.matcher(s);
        System.out.println(s+" : "+matcher.find());

        s = "99900234";        
        matcher = p.matcher(s);
        System.out.println(s+" : "+matcher.find());
    }
    
    // 6.2 Check if PHONE NO.
    // Task: Write a regular expression which matches any phone number.
    // Phone number consists either out of 7 numbers in a row or out of 3 number, a (white)space or a dash and then 4 numbers. 
    public static void meth1(){ 
        String patt = "\\d\\d\\d([\\s])?\\d\\d\\d\\d";

        String s="123456789000";
        System.out.println(s.matches(patt));
        
        s="1234567";
        System.out.println(s.matches(patt));
        
        s="123 4567";
        System.out.println(s.matches(patt));   
    }
}
