package com.CustomInstaller.internals;

import java.util.ArrayList;
import java.util.List;

import com.CustomInstaller.tools.Utils;

public class ReadConf {
	public String getApplictationName() {
		return Utils.indexOf(Utils.readLine(0), '=');
	}
	
	public String getApplicationDesc() {
		return Utils.indexOf(Utils.readLine(1), '=');
	}
	
	public String getInstallDirectory() {
		return Utils.indexOf(Utils.readLine(2), '=');
	}
	public String getInstallFolderName() {
		return Utils.indexOf(Utils.readLine(4), '=');
	}
	
}
