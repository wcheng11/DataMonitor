package edu.thss.monitor.pub.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CodeCounter {
	static long normalLines = 0;

	static long commentLines = 0;

	static long whiteLines = 0;

	public static void main(String[] args) {
		File f = new File("D:\\workspace\\MMS\\src\\");
		listChilds(f, 0);
		System.out.println("normalLines:" + normalLines);
		System.out.println("commentLines:" + commentLines);
		System.out.println("whiteLines:" + whiteLines);
	}

	private static void listChilds(File f, int level) {
		String preStr = "";
		for (int i = 0; i < level; i++) {
			preStr += "    ";
		}
		System.out.println(preStr + f.getName());
		if (!f.isDirectory())
			return;
		File[] childs = f.listFiles();
		for (File child : childs) {
			//modify by yt 跳过svn文件夹
			if(child.getName().equals(".svn")){
				continue;
			}
			parse(child);
			listChilds(child, level + 1);
		}
	}

	private static void parse(File f) {
		if (f.isDirectory()) {
			return;
		}
		BufferedReader br = null;
		boolean comment = false;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = "";
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.matches("^[\\s&&[^\\n]]*$")) {
					whiteLines++;
				} else if (line.startsWith("/*") && !line.endsWith("*/")) {
					commentLines++;
					comment = true;
				} else if (line.startsWith("/*") && line.endsWith("*/")) {
					commentLines++;
				} else if (true == comment) {
					commentLines++;
					if (line.endsWith("*/")) {
						comment = false;
					}
				} else if (line.startsWith("//")) {
					commentLines++;
				} else {
					normalLines++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}