package com.nk.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtilities {
	public synchronized boolean isBlacklistedStringFound(String sentence, List<String> blacklistList){
		StringBuffer rgx = new StringBuffer("(");
		String oR = "";
		for(String blklistWord : blacklistList){
			rgx.append(oR);
			oR = "|";
			rgx.append(blklistWord);
		}
		rgx.append(")");
		
        Pattern p = Pattern.compile(rgx.toString()/*, Pattern.CASE_INSENSITIVE*/);	// Expected : "(mouse|.at|dog|wolf|bear|human)"
        Matcher m =p.matcher(sentence);
        boolean isBlacklistedStringFound = false;
        while(m.find()){
//            System.out.println(m.group());
        	isBlacklistedStringFound = true;
        }
		return isBlacklistedStringFound;
	}
	
	public synchronized String replaceWordsInSentence(String sentence, Map<String, String> replacementMap){
		Pattern p = null;
		Matcher m = null;
		for(Map.Entry<String, String> entry : replacementMap.entrySet()){
                    p = Pattern.compile(entry.getKey()/*, Pattern.CASE_INSENSITIVE*/);
                    m = p.matcher(sentence);
                    sentence = m.replaceAll(entry.getValue());			
		}
		return sentence;
	}
	
	public static void main(String[] args) {
		String sentence = "This is a dog. That is a cat.";
		List<String> blacklistList = new ArrayList<String>();
		blacklistList.add("cat");
		blacklistList.add("dog");
		System.out.println(new RegexUtilities().isBlacklistedStringFound(sentence, blacklistList));
		
		Map<String, String> replacementMap = new HashMap();
		replacementMap.put("dog", "doggy");
		replacementMap.put("cat", "kitty");
		System.out.println(new RegexUtilities().replaceWordsInSentence(sentence, replacementMap));
	}
}
