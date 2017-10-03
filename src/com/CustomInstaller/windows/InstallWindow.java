package com.CustomInstaller.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.commons.io.FileUtils;

import com.CustomInstaller.internals.ReadConf;
import com.CustomInstaller.tools.Utils;

import net.jimmc.jshortcut.JShellLink;

public class InstallWindow {
	private JFrame frame;
	private JPanel panel;
	private JProgressBar proBar;
	private JLabel lblStatus;
	private JButton btnClose;
	private JTextPane txtPane;
	public static boolean isRunning = true;
	private ReadConf conf;
	private String installPath;
	
	private String mainExe = "";
	private int mainExeLineNum = 0;
	
	private List<String> files = new ArrayList<String>();
	private List<String> dirs = new ArrayList<String>();
	
	public InstallWindow(String path) {
		installPath = path;
		frame = new JFrame();
		frame.setTitle("Installing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(580, 375);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setResizable(false);
		
		panel = new JPanel();
		frame.add(panel);
		
		initialize();
	}
	private void initialize() {
		conf = new ReadConf();
		getFilesAndDirs();
		System.out.println(dirs);
		System.out.println(conf.getInstallDirectory());
		System.out.println(conf.getInstallFolderName());
		lblStatus = new JLabel("Installing...");
		lblStatus.setBounds(5, 30, 100, 15);
		frame.getContentPane().add(lblStatus);
		
		proBar = new JProgressBar(0, (files.size() + dirs.size()) - 2);
		//proBar.setIndeterminate(true);
		proBar.setStringPainted(true);
		proBar.setBounds(5, 50, 550, 30);
		frame.getContentPane().add(proBar);
		
		txtPane = new JTextPane();
		txtPane.setEditable(false);
		JScrollPane sp = new JScrollPane(txtPane);
		sp.setBounds(5, 90, 550, 220);
		frame.getContentPane().add(sp);
		
		btnClose = new JButton("Close");
		btnClose.setEnabled(false);
		btnClose.setBounds(455, 315, 100, 20);
		btnClose.addActionListener(new Close());
		frame.getContentPane().add(btnClose);
		
		new Thread(new Runnable() {
			public void run() {
				copyAllFilesAndDirs();
			}
		}).start();
		
		
	}
	
	public void setVisible(final boolean vis) {
		frame.setVisible(vis);
	}
	
	private void copyAllFilesAndDirs() {
		conf = new ReadConf();
		File installDir;
		try {
			installDir = new File(installPath + "/" + conf.getInstallFolderName());
			installDir.mkdir();
			
			int endPoint = 0;
			for(int i = 1;i<files.size();i++) {
				txtPane.setText(txtPane.getText() + "\nExtracting: " + files.get(i));
				txtPane.setCaretPosition(txtPane.getText().length());
				File f = new File(files.get(i));
				try {
					FileUtils.copyFile(f, new File(installDir.getPath() + "/" + files.get(i)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				proBar.setValue((i));
				endPoint = i;
			}
			
			for(int i = 0;i<dirs.size();i++) {
				txtPane.setText(txtPane.getText() + "\nExtracting: <DIR> " + dirs.get(i));
				txtPane.setCaretPosition(txtPane.getText().length());
				File f = new File(dirs.get(i));
				try {
					FileUtils.copyDirectory(f, new File(installDir.getPath() + "/" + dirs.get(i)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				proBar.setValue((i+endPoint));
			}
			mainExe = Utils.indexOf(Utils.readLine(mainExeLineNum), '=');
			System.out.println(mainExe);
			txtPane.setText(txtPane.getText() + "\nDone.");
			txtPane.setCaretPosition(txtPane.getText().length());
			lblStatus.setText("Done.");
			btnClose.setEnabled(true);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Can not install:\n" + e);
			e.printStackTrace();
			System.exit(-1);
		}
		
		
	}
	
	private void getFilesAndDirs() {
		int startNum = 5;
		while(!Utils.readLine(startNum).equals("## FILES END ##")) {
			files.add(Utils.readLine(startNum));
			startNum++;
		}
		int startNum2 = startNum + 3;
		while(!Utils.readLine(startNum2).equals("## DIRS END ##")) {
			dirs.add(Utils.readLine(startNum2));
			startNum2++;
		}
		mainExeLineNum = startNum2 + 1;
	}
	
	private class Close implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!mainExe.isEmpty() && !System.getProperty("os.name").equals("Linux")) {
				makeShort(installPath + "\\" + conf.getInstallFolderName() +"\\" + mainExe);
				int reply = JOptionPane.showConfirmDialog(null, "Would you like to run it?", "Run?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(reply == JOptionPane.YES_OPTION) {
					try {
						Process p = Runtime.getRuntime().exec(installPath + "\\" + conf.getInstallFolderName() +"\\" + mainExe);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Could not launch main exe", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
			System.exit(0);
		}
	}
	
	private void makeShort(String path) {
		JShellLink link = new JShellLink();
		link.setFolder(JShellLink.getDirectory("desktop"));
		link.setName(conf.getApplictationName());
		System.out.println(path);
		link.setPath(path);
		link.save();
	}
}
