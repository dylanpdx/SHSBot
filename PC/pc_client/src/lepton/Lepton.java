package lepton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Lepton {
	
	public static final int lower_limit=992;
	public static final int upper_limit=2000;
	public static JFrame f;
	static int speeda = -0;
	static int speedb = 0;
	static int speedc = -0;
	static int speedd = 0;
	
	static int drive_speed=0;
	
	static int camhor = lower_limit;
	static int camver = lower_limit;
	static int armhor = lower_limit;
	static int armver = lower_limit;
	
	
	public static void connectServ(String ip,main mclass){
		
		Thread motor = new Thread(){
			@Override
			public void run(){
				try {
					Socket s = new Socket(ip, 1020);
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					while (s.isConnected()){ // honestly this is just basically while true, since isConnected is always true
						oos.writeObject(new controlMessage(speeda, speedb, speedc, speedd, camhor, camver, armhor, armver));
						oos.flush();
						Thread.sleep(200);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		motor.start();
		
		Thread data = new Thread(){
			@Override
			public void run(){
				try {
					Socket s = new Socket(ip, 1021);
					ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
					while (s.isConnected()){ // honestly this is just basically while true, since isConnected is always true
						dataMessage dm = (dataMessage) ois.readObject();
						mclass.setImages(dm.pii1, dm.pii2, dm.pii3, dm.pii4);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		data.start();
	}
	
	
	public static void setCameraPos(int horizontal,int vertical){
		camhor = horizontal;
		camver = vertical;
	}
	
	public static void changeSpeed(int a,int b, int c, int d){
		speeda = a;
		speedb = b;
		speedc = c;
		speedd = d;
	}
	
	public static void setArmPos(int horizontal,int vertical){
		armhor = horizontal;
		armver = vertical;
	}
}
