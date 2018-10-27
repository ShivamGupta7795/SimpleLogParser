package org.impact.analytics.logs.parser;

import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.impact.analytics.logs.ParserApplication;

public class ParseLogByExpression {

	public void findLogByExpression(String expression, String dirPath) {
		System.out.println("Expression :"+ expression);
		System.out.println("dirPath: "+dirPath);
		
		Pattern searchPattern = null;
		try {
			searchPattern = Pattern.compile(expression);
		}catch(Exception e) {
			System.out.println("Invalid Regular Expression");
			return;
		}
		
		File logDir = new File(dirPath);
		File[] logFiles = logDir.listFiles();
		List<String> searchResult = new ArrayList<>();
		List<String> searchResultfileName = new ArrayList<>();
		
		for(File file: logFiles) {
			BufferedReader inputStream = null;
			
			try {
				inputStream = new BufferedReader(new FileReader(file));
				String line;
				
				while((line= inputStream.readLine()) != null) {
					Matcher matcher = searchPattern.matcher(line);
					if(matcher.find()) {
						searchResult.add(line);
						searchResultfileName.add(file.getName());
					}
				}
			}catch(Exception e) {
				System.out.println("Error while parsing files: Directory path is wrong");
				System.out.println("################################################");
				System.out.println("please enter the log directory path");
				Scanner sc = new Scanner(System.in);
				dirPath = sc.nextLine();
				new ParserApplication().selectOperation(dirPath);
			}finally{
				if(inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(searchResult.isEmpty()) {
			System.out.println("There are no logs matching with the given expression");
		}else {
			System.out.println("################################################");
			System.out.println("Following files and logs contains given expression");
			for(int i=0;i<searchResult.size();i++) {
				System.out.print("Filename: "+ searchResultfileName.get(i) +"\t");
				System.out.println("line: "+ searchResult.get(i));
			}
			
			System.out.println("Total Records:  "+ searchResult.size());
		}
		
		System.out.println("Want to save the results in a file: \n 1. PRESS Y  for YES \n 2. PRESS N for NO");
		Scanner sc = new Scanner(System.in);
		String isSaveFile= sc.nextLine();
		
		if(isSaveFile.equals("Y")) {
			BufferedWriter writer = null;
			String currentTime = LocalDateTime.now().toString(); 
			try {
				writer = new BufferedWriter(new FileWriter(dirPath+"/LogSearchResult"+currentTime+".txt"));
				for(String line: searchResult) {
					writer.write(line);
					writer.newLine();
				}
				System.out.println("LogSearchResult"+currentTime+".txt saved successfully in the log directory");
			}catch(Exception e) {
				System.out.println("Failed to write search result into file");
			}finally {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
		}
		
		
		System.out.println("################################################");
		
		new ParserApplication().selectOperation(dirPath);
	}
	
	public void findLogByExpression(String expression, String dirPath, String range) {
		Pattern searchPattern = null;
		try {
			searchPattern = Pattern.compile(expression);
		}catch(Exception e) {
			System.out.println("Invalid Regular Expression");
			return;
		}
		
		File logDir = new File(dirPath);
		File[] logFiles = logDir.listFiles();
		List<String> searchResult = new ArrayList<>();
		List<String> searchResultfileName = new ArrayList<>();
		
x:		for(File file: logFiles) {
			BufferedReader inputStream = null;
			
			try {
				inputStream = new BufferedReader(new FileReader(file));
				String line;
				
				while((line= inputStream.readLine()) != null) {
					Matcher matcher = searchPattern.matcher(line);
					if(matcher.find()) {
						searchResult.add(line);
						searchResultfileName.add(file.getName());
					}
					if(searchResult.size() > Integer.parseInt(range)) {
						break x;
					}
				}
			}catch(Exception e) {
				System.out.println("Error while parsing files: Directory path is wrong");
				System.out.println("################################################");
				System.out.println("please enter the log directory path");
				Scanner sc = new Scanner(System.in);
				dirPath = sc.nextLine();
				new ParserApplication().selectOperation(dirPath);
			}finally{
				if(inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(searchResult.isEmpty()) {
			System.out.println("There are no logs matching with the given expression");
		}else {
			System.out.println("################################################");
			System.out.println("Following files and logs contains given expression");
			for(int i=0;i<searchResult.size();i++) {
				System.out.print("Filename: "+ searchResultfileName.get(i) +"\t");
				System.out.println("line: "+ searchResult.get(i));
			}
			
			System.out.println("Total Records:  "+ searchResult.size());
		}
		
		System.out.println("Want to save the results in a file: \n 1. PRESS Y  for YES \n 2. PRESS N for NO");
		Scanner sc = new Scanner(System.in);
		String isSaveFile= sc.nextLine();
		
		if(isSaveFile.equals("Y")) {
			BufferedWriter writer = null;
			String currentTime = LocalDateTime.now().toString(); 
			try {
				writer = new BufferedWriter(new FileWriter(dirPath+"/LogSearchResult"+currentTime+".txt"));
				for(String line: searchResult) {
					writer.write(line);
					writer.newLine();
				}
				System.out.println("LogSearchResult"+currentTime+".txt saved successfully in the log directory");
			}catch(Exception e) {
				System.out.println("Failed to write search result into file");
			}finally {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
		}
		
		
		System.out.println("################################################");
		
		new ParserApplication().selectOperation(dirPath);
	}
	
}
