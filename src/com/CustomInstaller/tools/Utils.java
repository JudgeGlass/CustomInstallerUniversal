package com.CustomInstaller.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class Utils {
	public static String indexOf(String txt, char ch) {
		return txt.substring(txt.lastIndexOf(ch) + 1);
	}
	
	public static String readLine(int lineNumber) {
		String line;
		try {
			line = Files.readAllLines(Paths.get("Conf.txt")).get(lineNumber);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error";
		}
		return line;
	}
	
	public static void copyFile(File src, File dec) {
		try {
			FileUtils.copyFile(src, dec);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String readFile(String fileName) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(new File(fileName).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
	public static void writeFile(String fileName, String txt) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.print(txt);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.err.println("Could not write conf file!");
		}
	}
}
