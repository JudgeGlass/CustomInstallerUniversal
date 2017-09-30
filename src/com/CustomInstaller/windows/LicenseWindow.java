package com.CustomInstaller.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.CustomInstaller.tools.Utils;

public class LicenseWindow {
	private JFrame frame;
	private JPanel panel;
	private JTextPane txtPane;
	private JRadioButton radAccept;
	private JRadioButton radDecline;
	private JButton btnOK;
	private String txtPath;
	public LicenseWindow(String txtPath) {
		this.txtPath = txtPath;
		frame = new JFrame();
		frame.setTitle("License");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 340);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		panel = new JPanel();
		frame.add(panel);
		
		initialize();
	}
	
	private void initialize() {
		txtPane = new JTextPane();
		txtPane.setText(Utils.readFile(Utils.indexOf(Utils.readLine(3), '=')));
		txtPane.setCaretPosition(0);
		txtPane.setEditable(false);
		JScrollPane sp = new JScrollPane(txtPane);
		sp.setBounds(5, 5, 380, 250);
		frame.getContentPane().add(sp);
		
		
		btnOK = new JButton("OK");
		btnOK.setBounds(285, 260, 100, 20);
		btnOK.setEnabled(false);
		btnOK.setActionCommand("OK");
		btnOK.addActionListener(new RadListener());
		frame.getContentPane().add(btnOK);
		
		radAccept = new JRadioButton("Accept");
		radAccept.setBounds(205, 260, 75, 15);
		radAccept.setActionCommand("ACCEPTED");
		radAccept.addActionListener(new RadListener());
		frame.getContentPane().add(radAccept);
		
		radDecline = new JRadioButton("Decline");
		radDecline.setBounds(140, 260, 69, 15);
		radDecline.setActionCommand("DECLINED");
		radDecline.addActionListener(new RadListener());
		frame.getContentPane().add(radDecline);
	}
	
	public void setVisable(final boolean vis) {
		frame.setVisible(vis);
	}
	
	private class RadListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals("ACCEPTED")) {
				if(radAccept.isSelected()) {
					radDecline.setSelected(false);
					btnOK.setEnabled(true);
				}
			}else if(cmd.equals("DECLINED")) {
				if(radDecline.isSelected()) {
					radAccept.setSelected(false);
					btnOK.setEnabled(false);
				}
			}else if(cmd.equals("OK")) {
				InstallWindow iwindow = new InstallWindow(txtPath);
				iwindow.setVisible(true);
				frame.dispose();
			}
		}
	}
}
