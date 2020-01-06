package imageprocess.translateAndrotate;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import imageprocess.Image_Format;
import imageprocess.*;
import imageprocess.math.*;

public class TranslateAndRotate {
	private JTextField translate_x = new JTextField();
	private JTextField translate_y = new JTextField();
	private JTextField rotate = new JTextField();

	public TranslateAndRotate(Image_Format inputimg, Image_Format outputimg, Panel outputPanel) {
		JFrame myFrame = new JFrame("平移與旋轉");

		JLabel translate_explantion = new JLabel("請輸入想要平移的水平與垂直量");
		JLabel rotate_explantion = new JLabel("請輸入想要旋轉的角度");
		JLabel horizontal = new JLabel("x");
		JLabel vertical = new JLabel("y");
		Container cp = myFrame.getContentPane();
		cp.setLayout(null);
		translate_x.setText("0");
		translate_y.setText("0");
		rotate.setText("0");

		cp.add(translate_explantion);
		translate_explantion.setBounds(30, 0, 200, 50);

		cp.add(horizontal);
		horizontal.setBounds(230, 0, 20, 50);

		cp.add(translate_x);
		translate_x.setBounds(250, 10, 40, 30);

		cp.add(vertical);
		vertical.setBounds(320, 0, 20, 50);

		cp.add(translate_y);
		translate_y.setBounds(340, 10, 40, 30);

		cp.add(rotate_explantion);
		rotate_explantion.setBounds(30, 50, 150, 50);

		cp.add(rotate);
		rotate.setBounds(200, 60, 40, 30);

		rotate.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					translateAndrotate(inputimg, outputimg, outputPanel);
					myFrame.dispose();
				}
			}
		});
		translate_x.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					translateAndrotate(inputimg, outputimg, outputPanel);
					myFrame.dispose();
				}
			}
		});
		translate_y.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					translateAndrotate(inputimg, outputimg, outputPanel);
					myFrame.dispose();
				}
			}
		});

		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				myFrame.dispose();
			}
		});
		myFrame.setSize(450, 150);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}

	public void translateAndrotate(Image_Format inputimg, Image_Format outputimg, Panel outputPanel) {
		try {
			float x = Float.parseFloat(translate_x.getText());
			float y = Float.parseFloat(translate_y.getText());
			float angle = Float.parseFloat(rotate.getText());
			double radian = angle * Math.PI / 180;
			float translation[][] = { { 1, 0, 0 }, { 0, 1, 0 }, { x, y, 1 } };
			float rotation[][] = { { (float) Math.cos(radian), (float) Math.sin(radian), 0 },
					{ -(float) Math.sin(radian), (float) Math.cos(radian), 0 }, { 0, 0, 1 } };
			Image_Format tempimg = new Image_Format();
			tempimg.Initialize(inputimg.getHeight(), inputimg.getWidth());

			translation = InverseMatrix.getN(translation);
			rotation = InverseMatrix.getN(rotation);

			// translation first

			for (int i = 0; i < tempimg.getHeight(); i++) {
				for (int j = 0; j < tempimg.getWidth(); j++) {
					int v = (int) (i + translation[2][1]);
					int w = (int) (j + translation[2][0]);

					if (v < tempimg.getHeight() && v >= 0 && w < inputimg.getWidth() && w >= 0) {
						tempimg.setRGB(i, j, inputimg.getred(v, w), inputimg.getGreen(v, w), inputimg.getBlue(v, w));
					} else {
						tempimg.setRGB(i, j, 0, 0, 0);
					}
				}
			}
			// then rotate

			for (int i = 0; i < tempimg.getHeight(); i++) {
				for (int j = 0; j < tempimg.getWidth(); j++) {

					int center_y = (tempimg.getHeight() - 1) / 2;
					int center_x = (tempimg.getWidth() - 1) / 2;

					int temp_i = i - center_y;
					int temp_j = j - center_x;

					int v = (int) (temp_j * rotation[0][1] + temp_i * rotation[1][1] + center_y);
					int w = (int) (temp_j * rotation[0][0] + temp_i * rotation[1][0] + center_x);

					if (v < tempimg.getHeight() && v >= 0 && w < tempimg.getWidth() && w >= 0) {
						outputimg.setRGB(i, j, tempimg.getred(v, w), tempimg.getGreen(v, w), tempimg.getBlue(v, w));
					} else {
						outputimg.setRGB(i, j, 0, 0, 0);
					}
				}
			}
			outputPanel.initialize(outputimg);
			outputPanel.repaint();

		} catch (NumberFormatException e) {
			rotate.setText("");
		}
	}

	void translation() {

	}

	void rotation() {

	}
}
