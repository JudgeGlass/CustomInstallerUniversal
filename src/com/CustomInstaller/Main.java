package com.CustomInstaller;

import java.awt.Font;

import javax.swing.UIManager;

import com.CustomInstaller.windows.InitWindow;

public class Main {
	public static void main(String args[]) {
		System.out.println(System.getProperty("os.name"));
		try {
			if(!System.getProperty("os.name").equals("Linux")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
			UIManager.put("Button.font", new Font(Font.SANS_SERIF, 0, 11));
			UIManager.put("Label.font", new Font(Font.SANS_SERIF, 0, 11));
			UIManager.put("RadioButton.font", new Font(Font.SANS_SERIF, 0, 11));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		InitWindow window = new InitWindow();
		window.setVisible(true);
		
	}
}
