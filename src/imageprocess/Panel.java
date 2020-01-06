package imageprocess;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Panel extends JPanel{
	private static final long serialVersionUID = 8429834570534021472L;
	private Image_Format img = new Image_Format();
	public void initialize(Image_Format inputimg) {
		img.copy(inputimg);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img.existence(img.getred(), img.getblue(), img.getgreen())) {
			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = 0; j < img.getWidth(); j++) {
					g.setColor(new Color(img.getred(i, j), img.getGreen(i, j), img.getBlue(i, j)));
					g.fillRect(j, i, 1, 1);
				}
			}
		}
	}
}

