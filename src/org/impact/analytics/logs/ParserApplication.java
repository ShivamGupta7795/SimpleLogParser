package org.impact.analytics.logs;

import java.util.Scanner;

import org.impact.analytics.logs.parser.ParseLogByExpression;
import org.impact.analytics.logs.parser.ParseLogByExpressionAndTimestamp;

public class ParserApplication {
	
	public static void main(String[] ar) {
		
		System.out.println("################################################");
		System.out.println("Please provide full path to the log directory");
		
		Scanner sc = new Scanner(System.in);
		String dirPath = sc.nextLine();
		
		System.out.println("################################################");
		new ParserApplication().selectOperation(dirPath);
	}
	
	public void selectOperation(String dirPath) {
		System.out.println(" Please select any of the following operations");
		
		System.out.println("Press 1 to find the logs by expression.");
		System.out.println("Press 2 to find the logs by expression and date-time range");
		System.out.println("Press 3 to find logs by expression and range of records");
		
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		
		switch(input) {
			case("1"): { 
				System.out.println("Please enter the expression to be searched");
				String expression = sc.nextLine();
				new ParseLogByExpression().findLogByExpression(expression, dirPath);
			} break;
			
			case("2"): {
				System.out.println("Please enter the expression to be searched");
				String expression = sc.nextLine();
				System.out.println("Please enter the start range date-time to be searched (YYYY-MM-dd HH:mm:ss format. Eg: 1995-07-03 12:00:00)");
				String fromTime = sc.nextLine();
				System.out.println("Please enter the end range date-time to be searched (YYYY-MM-dd HH:mm:ss format. Eg: 1995-07-03 12:00:00)");
				String toTime = sc.nextLine();
				new ParseLogByExpressionAndTimestamp().findLogByExpressionAndTime(expression, fromTime, toTime, dirPath);
				} break;
				
			case("3"): {
				System.out.println("Please enter the expression to be searched");
				String expression = sc.nextLine();
				System.out.println("Please enter the range of the records to be fetched");
				String range = sc.nextLine();
				new ParseLogByExpression().findLogByExpression(expression, dirPath, range);
				}break;
			default: { System.out.println("INVALID OPTION"); 
			System.out.println("################################################");
			new ParserApplication().selectOperation(dirPath);
			}break;
		}
	}
	
	
}
