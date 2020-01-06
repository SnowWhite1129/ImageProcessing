package imageprocess.mask;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import imageprocess.Panel;

import imageprocess.Image_Format;
import imageprocess.edgedetection.EdgeDectection;
import imageprocess.morphlogical.Dilation;
import imageprocess.morphlogical.Erosion;
import imageprocess.smoothing.Smoothing;

public class Mask {
	public static int num;

	public Mask(Image_Format inputimg, Image_Format outputimg, Panel inputPanel,Panel outPanel, final int Constant,int size) {
		num = size;
		JFrame myFrame = new JFrame("輸入參數");
		JTextField[] factor = new JTextField[num * num];
		JLabel explantion = new JLabel("請輸入mask矩陣");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(num, num));
		Container cp = myFrame.getContentPane();
		cp.setLayout(new GridLayout(1, 2));
		cp.add(explantion);
		cp.add(panel);
		inputimg.RGBtoGrayLevel();
		outputimg.copy(inputimg);
		

		for (int i = 0; i < num * num; i++) {
			factor[i] = new JTextField("1");
			panel.add(factor[i]);
		}
		for (int i = 0; i < num * num; i++) {
			factor[i].addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						int[][] filter = new int[Mask.num][Mask.num];
						for (int x = 0, k = 0; x < Mask.num; x++) {
							for (int y = 0; y < Mask.num; y++, k++) {
								filter[x][y] = Integer.parseInt(factor[k].getText());
							}
						}
						switch (Constant) {
						case MaskConstant.EDGEDETECTION:
							if (!EdgeDectection.edgedetection(filter, inputimg, outputimg)) {
								JOptionPane.showMessageDialog(null, "mask的元素總和需為0", "Error",
										JOptionPane.WARNING_MESSAGE);
							}
							break;
						case MaskConstant.SMOOTHING:
							Smoothing.smoothing(filter, inputimg, outputimg);
							break;
						case MaskConstant.EROSION:
							Erosion.erosion(filter, inputimg, outputimg);	
							break;
						case MaskConstant.DILATION:
							Dilation.dilation(filter, inputimg, outputimg);	
							break;
						}
						outPanel.initialize(outputimg);
						outPanel.repaint();
						inputPanel.initialize(inputimg);
						inputPanel.repaint();
						myFrame.dispose();
					}
				}
			});
		}
		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				myFrame.dispose();
			}
		});
		myFrame.setSize(300, 200);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}

	public interface MaskConstant {
		int SMOOTHING = 1;
		int EDGEDETECTION = 2;
		int EROSION = 3;
		int DILATION = 4;
	}
}
