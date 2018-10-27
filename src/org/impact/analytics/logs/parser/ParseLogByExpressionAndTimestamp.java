package org.impact.analytics.logs.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.impact.analytics.logs.ParserApplication;

public class ParseLogByExpressionAndTimestamp {

	public void findLogByExpressionAndTime(String expression, String fromTime, String toTime, String dirPath) {
		System.out.println("Expression :"+ expression);
		System.out.println("fromTime: "+fromTime);
		System.out.println("toTime: "+toTime);
		System.out.println("dirPath: "+dirPath);
		
		Pattern searchPattern = null;
		Pattern datePattern =  Pattern.compile("\\[(.*?)\\]");
		LocalDateTime searchStartTimestamp =null;
		LocalDateTime searchEndTimestamp =null;
		DateTimeFormatter userDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter logDateTimeFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss' 'Z");
		
		//check if search pattern is valid
		try {
			searchPattern = Pattern.compile(expression);
		}catch(Exception e) {
			System.out.println("Invalid Regular Expression");
			return;
		}
		
		//check if given timestamp is valid
		try {
			searchStartTimestamp = LocalDateTime.parse(fromTime, userDateTimeFormat);
			searchEndTimestamp = LocalDateTime.parse(toTime, userDateTimeFormat);
				
			//if end time is before start time
			if(searchEndTimestamp.isBefore(searchStartTimestamp)) {
				throw new Exception();
			}
		}catch(Exception e) {
			System.out.println("Invalid Date Time Format");
			System.out.println("################################################");
			new ParserApplication().selectOperation(dirPath);
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
					Matcher expressionMatcher = searchPattern.matcher(line);
					Matcher dateMatcher = datePattern.matcher(line);
					
					if(dateMatcher.find()) {
						try {
							//check if the log date lies between the given range and expression matches with the substring
							LocalDateTime logDate = LocalDateTime.parse(dateMatcher.group(1), logDateTimeFormat);
							if(logDate.isAfter(searchStartTimestamp) && logDate.isBefore(searchEndTimestamp) && expressionMatcher.find()) {
								searchResult.add(line);
								searchResultfileName.add(file.getName());
							}
						}catch(Exception e) {
							continue;
						}
					}

				}
			}catch(Exception e) {
				System.out.println("Error while parsing files: Directory path is wrong");
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
		
		
		//print results
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
		
		
		//save results in a file
		System.out.println("Want to save the results in a file: \n 1. PRESS Y  for YES \n 2. PRESS N for NO");
		Scanner sc = new Scanner(System.in);
		String isSaveFile= sc.nextLine();
		
		if(isSaveFile.equals("Y")) {
			BufferedWriter writer = null;
			DateTimeFormatter fileSaveFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH");
			String currentTime = LocalDateTime.now().format(fileSaveFormat);
			try {
				writer = new BufferedWriter(new FileWriter(dirPath+"/LogSearchByTimeResult"+currentTime+".txt"));
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
