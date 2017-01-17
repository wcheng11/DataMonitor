package edu.logRecorder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.logRecorder.imp.LogRecorder;

public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(args[0]);
			props.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LogReceiver receiver = new LogReceiver(props);
		receiver.setRecorder(new LogRecorder());
		Thread thread = new Thread(receiver);
		thread.start();
	}

}
