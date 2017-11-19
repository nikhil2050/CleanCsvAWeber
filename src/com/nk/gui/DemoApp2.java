package com.nk.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.codejava.swing.JFilePicker;

import com.nk.scrape.WebsiteScrap;

public class DemoApp2 {
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JPanel inputPanel;
	   private JPanel controlPanel;
	   private JLabel statusLabel;
	   
	   private JLabel lbl_url;
	   private JTextField txt_url;
	   
	   private JLabel lbl_inputFile;
	   private JFilePicker fp_inputFile;
	   private JFilePicker fp_inputFile2;
	   private JFilePicker fp_inputFile3;
	   
	   private JLabel lbl_outputPath;
	   private JTextField txt_outputPath;
	   
	   public DemoApp2(){
	      prepareGUI();
	   }

	   private void prepareGUI(){					// Called in constructor
	      mainFrame = new JFrame("Website Scraper");
	      mainFrame.setSize(700,600);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      mainFrame.setResizable(false);

//		  headerLabel = new JLabel("",JLabel.CENTER );
	      statusLabel = new JLabel("",JLabel.CENTER);        

	      statusLabel.setSize(150,100);
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
	         }        
	      });    
	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());

	      inputPanel = new JPanel();
	      FlowLayout grdLayout = new FlowLayout(FlowLayout.LEFT,5,5);
	      grdLayout.setHgap(10);
	      grdLayout.setVgap(10);
	      inputPanel.setLayout(grdLayout);
	      
	      mainFrame.add(inputPanel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);  
	   }

	   public void showEventDemo(){
	      lbl_url = new JLabel("Enter a Website : ");
	      lbl_url.setVisible(false);
	      txt_url  = new JTextField("http://www.ecomagnet.fr/");
	      txt_url.setVisible(false);
	      
//	      lbl_inputFile = new JLabel("Pick a file : ");
	      fp_inputFile = new JFilePicker("Pick a URL file : ", "Browse...");
	      fp_inputFile.setMode(JFilePicker.MODE_OPEN);
	      fp_inputFile.addFileTypeFilter(".txt", "Text Files");
	      
//	      lbl_inputFile2 = new JLabel("Pick a file : ");
	      fp_inputFile2 = new JFilePicker("Pick a Blacklist words file : ", "Browse...");
	      fp_inputFile2.setMode(JFilePicker.MODE_OPEN);
	      fp_inputFile2.addFileTypeFilter(".txt", "Text Files");
//	      
//	      lbl_inputFile3 = new JLabel("Pick a file : ");
	      fp_inputFile3 = new JFilePicker("Pick a replacement words file : ", "Browse...");
	      fp_inputFile3.setMode(JFilePicker.MODE_OPEN);
	      fp_inputFile3.addFileTypeFilter(".txt", "Text Files");
	      
	      lbl_outputPath = new JLabel("Enter Output Path : ");
	      txt_outputPath = new JTextField(30);
	      
	      JButton okButton = new JButton("OK");

	      okButton.setActionCommand("OK");

	      okButton.addActionListener(new ButtonClickListener()); 

//	      inputPanel.add(lbl_url);
//	      inputPanel.add(txt_url);
//	      inputPanel.add(lbl_inputFile);
	      inputPanel.add(fp_inputFile);
	      inputPanel.add(fp_inputFile2);
	      inputPanel.add(fp_inputFile3);
	      
	      inputPanel.add(lbl_outputPath);
	      inputPanel.add(txt_outputPath);
	      
	      inputPanel.add(okButton);

	      mainFrame.setVisible(true);  
	   }

	   private class ButtonClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	    	  String command = e.getActionCommand();
	    	  String status = "Error";
	    	  if(command.equals("OK")) {
	        	 statusLabel.setText("Status:Busy");
	    		  
//	        	 String[] args = {txt_url.getText(),txt_outputPath.getText()};
//	        	 WebsiteScrap ws = new WebsiteScrap();
//	        	 ws.scrapeText(args);

	        	 String inputFilePath=fp_inputFile.getSelectedFilePath();
	        	 String inputFilePath2=fp_inputFile2.getSelectedFilePath();
	        	 String inputFilePath3=fp_inputFile3.getSelectedFilePath();
	        	 String outputFilePath=txt_outputPath.getText();
	        	 if(inputFilePath==null || inputFilePath.equals("") || 
	        			 inputFilePath2==null || inputFilePath2.equals("") || 
    					 inputFilePath3==null || inputFilePath3.equals("") || 
    					 outputFilePath==null || outputFilePath.equals("") ){
	        		 status = "Please fill all fields";
	        	 }else{
		        	 String[] args = {inputFilePath, inputFilePath2, inputFilePath3, outputFilePath};
		        	 WebsiteScrap ws = new WebsiteScrap();
		        	 ws.parseFileAndScrapeText(args);
		        	 status = "Success";
	        	 }
	        	 statusLabel.setText("Status: "+status);
	         }
	      }
	   }
	   
	   
	}
