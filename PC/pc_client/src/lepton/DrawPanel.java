package lepton;

import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	
	int x;
	int y;
	int siz;
	
	
	
	public void drw(int xx,int yy,int size){
		//g.clearRect(0, 0, getWidth(), getHeight());
		x=xx;
		y=yy;
		siz = size;
		
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillOval(x-(siz/2), y-(siz/2), siz, siz);
    }

}
