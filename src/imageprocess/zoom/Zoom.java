package imageprocess.zoom;
import imageprocess.Image_Format;
import imageprocess.Panel;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Zoom {
	JFrame myFrame = new JFrame("放大及縮小");
	JTextField factor = new JTextField();
	JLabel explantion = new JLabel("請輸入想要放大縮小的倍率");

	public Zoom(Image_Format outputimg,Panel outPanel) {
		Container cp = myFrame.getContentPane();
		cp.setLayout(null);
		cp.add(explantion);
		explantion.setBounds(10, 0, 200, 50);
		cp.add(factor);
		factor.setBounds(210, 10, 40, 30);
		
		factor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					zoom(outputimg);
					outPanel.initialize(outputimg);
					outPanel.repaint();
					myFrame.dispose();
				}
			}
		});
		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				myFrame.dispose();
			}
		});
		myFrame.setSize(300, 100);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}
	void zoom(Image_Format outputimg) {
		try {
			float Magnification = Float.parseFloat(factor.getText());
			if (Magnification<1) {
				Magnification = 1 / Magnification;
			}
			Image_Format tempimg = new Image_Format();
			tempimg.Initialize((int) Math.ceil((outputimg.getHeight() / Magnification)), (int) Math
					.ceil((outputimg.getWidth() / Magnification)));			
			
			zoomout(Magnification, outputimg, tempimg);
			zoomin(Magnification, tempimg, outputimg);
			
		} catch (NumberFormatException e) {
			factor.setText("");
		}
	}
	public static void zoomout(float Magnification,Image_Format inputimg,Image_Format outputimg) { //縮小
		for (int i = 0; i < outputimg.getHeight(); i++) {
			for (int j = 0; j < outputimg.getWidth(); j++) {
				outputimg.setRGB(i, j
						,inputimg.getred((int) (i * Magnification), (int) (j * Magnification))
						,inputimg.getGreen((int) (i * Magnification), (int) (j * Magnification)) 
						,inputimg.getBlue((int) (i * Magnification), (int) (j * Magnification)) );
			}
		}
	}
	void zoomin(float Magnification,Image_Format inputimg,Image_Format outputimg) { //放大
		for (int i = 0; i < outputimg.getHeight(); i++) {
			for (int j = 0; j < outputimg.getWidth(); j++) {
				outputimg.setRGB(i, j
						,inputimg.getred((int) (i / Magnification), (int) (j / Magnification))
						,inputimg.getGreen((int) (i / Magnification), (int) (j / Magnification)) 
						,inputimg.getBlue((int) (i / Magnification), (int) (j / Magnification)) );
			}
		}
	}
}
