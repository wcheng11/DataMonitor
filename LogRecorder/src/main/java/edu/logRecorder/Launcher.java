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
		
		LogRecorder recorder = new LogRecorder();
		
		LogReceiver receiver = new LogReceiver(props, "time-Log");
		receiver.setRecorder(recorder);
		Thread thread = new Thread(receiver);
		thread.start();
		
		TimeReceiver timeReceiver = new TimeReceiver(props, "times");
		timeReceiver.setRecorder(recorder);
		Thread thread2 = new Thread(timeReceiver);
		thread2.start();
		
		DataReceiver dataReceiver = new DataReceiver(props, "data-log");
		dataReceiver.setRecorder(recorder);
		Thread thread3 = new Thread(dataReceiver);
		thread3.start();
	}

}
