package com.CustomInstaller.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.CustomInstaller.internals.ReadConf;
import com.CustomInstaller.tools.OpenDirectory;

public class InitWindow {
	private JFrame frame;
	private JPanel panel;
	private JLabel lblApName;
	private JLabel lblDec;
	private JLabel image;
	private JLabel lblChoosePath;
	private JButton btnBrowse;
	private JTextField txtPath;
	private JButton btnNext;
	private ReadConf conf;
	
	private String installPath;
	private static boolean accepted = true;
	
	public InitWindow() {
		frame = new JFrame();
		frame.setTitle("Setup");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 380);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		panel = new JPanel();
		frame.add(panel);
		
		initialize();
	}
	
	private void initialize() {
		conf = new ReadConf();
		lblApName = new JLabel(conf.getApplictationName());
		lblApName.setBounds(50, 5, 300, 15);
		lblApName.setFont(new Font(lblApName.getName(), Font.BOLD, 14));
		frame.getContentPane().add(lblApName);
		
		lblDec = new JLabel(conf.getApplicationDesc());
		lblDec.setBounds(60, 25, 300, 15);
		frame.getContentPane().add(lblDec);
		
		image = new JLabel();
		image.setBounds(500, 5, 64, 64);
		image.setIcon(new ImageIcon(getClass().getResource("/com/CustomInstaller/resources/MainIcon.png")));
		frame.getContentPane().add(image);
		
		lblChoosePath = new JLabel("Choose a path:");
		lblChoosePath.setBounds(50, 130, 200, 15);
		frame.getContentPane().add(lblChoosePath);
		
		txtPath = new JTextField(conf.getInstallDirectory().toString());
		txtPath.setBounds(50,  150, 400, 20);
		frame.getContentPane().add(txtPath);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(450, 150, 100, 20);
		btnBrowse.setActionCommand("BROWSE_DIR");
		btnBrowse.addActionListener(new BtnListener());
		frame.getContentPane().add(btnBrowse);
		
		btnNext = new JButton("Next");
		btnNext.setBounds(450, 320, 100, 20);
		btnNext.setActionCommand("NEXT");
		btnNext.addActionListener(new BtnListener());
		frame.getContentPane().add(btnNext);
	}
	
	public void setVisible(final boolean vis) {
		frame.setVisible(vis);
	}
	
	private class BtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals("BROWSE_DIR")) {
				OpenDirectory opd = new OpenDirectory();
				opd.open(panel);
				if(opd.getDir().isEmpty()) txtPath.setText(System.getProperty("user.dir") + "/Desktop");
				installPath = opd.getDir();
				txtPath.setText(installPath);
			}else if(cmd.equals("NEXT")) {
				btnNext.setEnabled(false);
				LicenseWindow licWindow = new LicenseWindow(txtPath.getText());
				licWindow.setVisable(true);
				
				frame.dispose();
				
				
			}
		}
	}
}
