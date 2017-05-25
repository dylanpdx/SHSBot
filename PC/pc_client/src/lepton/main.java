package lepton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class main extends JFrame {

	private JPanel contentPane;
	
	public JPanel cameraPanel = new JPanel();
	
	public static ImageIcon pii1; // thermal 1
	public static ImageIcon pii2; // thermal 2
	public static ImageIcon pii3; // tango
	public static ImageIcon pii4; // webcam
	
	final static int LEFT=37;
	final static int UP=38;
	final static int RIGHT=39;
	final static int DOWN=40;
	
	JLabel pilab1;
	JLabel pilab2;
	JLabel pilab3;
	JLabel pilab4;
	
	static main frame;

	public void setImages(ImageIcon image,ImageIcon image2,ImageIcon image3,ImageIcon image4){
		pilab1.setIcon(image);
		pilab2.setIcon(image2);
		pilab3.setIcon(image3);
		pilab4.setIcon(image4);
		pilab1.repaint();
		pilab2.repaint();
		pilab3.repaint();
		pilab4.repaint();
		frame.repaint();
		frame.layout();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pii1 = new ImageIcon("def.png");
					pii2 = new ImageIcon("def.png");
					pii3 = new ImageIcon("def.png");
					pii4 = new ImageIcon("def.png");
					frame = new main(pii1,pii2,pii3,pii4);
					frame.setVisible(true);
					
					Lepton.connectServ(args[0],frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main(ImageIcon pi1,ImageIcon pi2,ImageIcon pi3,ImageIcon pi4) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 875, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		        if (e.getID()==KeyEvent.KEY_PRESSED){
		        	if (e.getKeyCode()==UP){
		        		Lepton.changeSpeed(Lepton.drive_speed, Lepton.drive_speed, Lepton.drive_speed, Lepton.drive_speed);
		        		return true;
		        	}
		        	if (e.getKeyCode()==DOWN){
		        		Lepton.changeSpeed(-Lepton.drive_speed, -Lepton.drive_speed, -Lepton.drive_speed, -Lepton.drive_speed);
		        		return true;
		        	}
		        	if (e.getKeyCode()==LEFT){
		        		Lepton.changeSpeed(Lepton.drive_speed, Lepton.drive_speed, -Lepton.drive_speed, -Lepton.drive_speed);
		        		return true;
		        	}
		        	if (e.getKeyCode()==RIGHT){
		        		Lepton.changeSpeed(-Lepton.drive_speed, -Lepton.drive_speed, Lepton.drive_speed, Lepton.drive_speed);
		        		return true;
		        	}
		        }
		        if (e.getID()==KeyEvent.KEY_RELEASED){
		        	if (e.getKeyCode()==UP || e.getKeyCode()==DOWN || e.getKeyCode()==LEFT || e.getKeyCode()==RIGHT){
		        		Lepton.changeSpeed(0, 0, 0, 0);
		        		return true;
		        	}
		        }
		        return false;
		      }
		});
		
		cameraPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Camera View", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		cameraPanel.setBounds(10, 11, 602, 539);
		contentPane.add(cameraPanel);
		cameraPanel.setLayout(new BorderLayout(0, 0));
		
		pilab1 = new JLabel(pi1);
		cameraPanel.add(pilab1, BorderLayout.EAST);
		
		pilab2 = new JLabel(pi2);
		cameraPanel.add(pilab2, BorderLayout.WEST);
		
		pilab3 = new JLabel(pi3);
		cameraPanel.add(pilab3, BorderLayout.NORTH);
		
		pilab4 = new JLabel(pi4);
		cameraPanel.add(pilab4, BorderLayout.SOUTH);
		
		JPanel controlpanel = new JPanel();
		controlpanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controlpanel.setBounds(624, 11, 221, 366);
		contentPane.add(controlpanel);
		controlpanel.setLayout(null);
		
		DrawPanel camcontrol = new DrawPanel();
		camcontrol.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		camcontrol.setBounds(41, 30, 140, 116);
		camcontrol.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				boolean oob = false;
				int x = e.getX();
				int y = e.getY();
				
				if (x>camcontrol.getWidth()){ // prevent out of bound movement
					x=camcontrol.getWidth();
					oob = true;
				}else if (x<0){
					x=0;
					oob = true;
				}
				if (y>camcontrol.getHeight()){ // prevent out of bound movement
					y=camcontrol.getHeight();
					oob = true;
				}else if (y<0){
					y=0;
					oob = true;
				}
				
				if (oob){
					camcontrol.drw(x, y,20); // makes cursor more visible if out of bounds
				}else{
					camcontrol.drw(x, y,10);
				}
				
				
				float pctx = ((x*1F)/camcontrol.getWidth());
				float pcty = ((y*1F)/camcontrol.getHeight());
				
				int px=(int) (Lepton.upper_limit-
						((Lepton.upper_limit-Lepton.lower_limit)*pctx)
						);
				int py=(int) (Lepton.lower_limit+
						((Lepton.upper_limit-Lepton.lower_limit)*pcty)
						);
				
				Lepton.setCameraPos(px, py);
				
				
			}
		});
		
		controlpanel.add(camcontrol);
		
		JLabel lblCameraControl = new JLabel("Camera Control");
		lblCameraControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblCameraControl.setBounds(41, 11, 140, 14);
		controlpanel.add(lblCameraControl);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				Lepton.drive_speed=slider.getValue();
			}
		});
		slider.setValue(0);
		slider.setBounds(12, 171, 200, 26);
		controlpanel.add(slider);
	}

}
