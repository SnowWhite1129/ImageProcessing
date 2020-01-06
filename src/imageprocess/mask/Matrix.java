package imageprocess.mask;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import imageprocess.Image_Format;
import imageprocess.Panel;
import imageprocess.mask.Mask;

public class Matrix {
	public Matrix(Image_Format inputimg, Image_Format outputimg,Panel inputPanel ,Panel outPanel, final int Constant) {
		JFrame myFrame = new JFrame("請決定矩陣的大小");
		JTextField factor = new JTextField();
		JLabel explantion = new JLabel("請決定矩陣的大小");
		Container cp = myFrame.getContentPane();
		cp.setLayout(new GridLayout(1, 1));
		cp.add(explantion);
		cp.add(factor);
		factor.setText("0");
		
		factor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					new Mask(inputimg, outputimg,inputPanel, outPanel, Constant,Integer.parseInt(factor.getText()));
					myFrame.dispose();
				}
			}
		});
		
		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				myFrame.dispose();
			}
		});
		myFrame.setSize(300, 200);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}
}
