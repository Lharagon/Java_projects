//Programmer: Luis Garcia
//Assignment: Cs990Homework3B
//Date: 02/23/2019


import java.util.Scanner;
import java.io.*;

public class UppercaseFileConverter {

	public static void main(String[] args) throws IOException {
		
		String fileName, line;
		
		Scanner keyboard = new Scanner(System.in);
		
		
		System.out.print("Enter the name of the file to be read: ");
		fileName = keyboard.nextLine();
		
//		Create a new file with fileName
		File testFile = new File(fileName);
		
//		Confirm file Exist
		if(!testFile.exists())
		{
			System.out.print("The file " + fileName + " does not exist or could not be opened.");
			System.exit(0);
		}
		
//		After confirming file exist create scanner with file
		Scanner inputFile = new Scanner(testFile);
		
		System.out.println("Here is the file converted into Uppercase.");
		
//		If there is a next line, read it and turn to upper case
		while(inputFile.hasNext())
		{
			line = inputFile.nextLine();
			System.out.println(line.toUpperCase());
		}
		
		System.out.println("Files read, converted and then closed.");
		
//		Close the scanners
		keyboard.close();
		inputFile.close();

	}

}
