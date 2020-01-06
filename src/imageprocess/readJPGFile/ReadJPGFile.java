package imageprocess.readJPGFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import imageprocess.*;

public class ReadJPGFile {
	public static void readJPGFile(String path,Image_Format inputimg) {
		try {
			BufferedImage bimg = ImageIO.read(new File(path));

			inputimg.Initialize(bimg.getHeight(),bimg.getWidth()); 

			for (int i = 0; i < inputimg.getHeight(); i++) {
				for (int j = 0; j < inputimg.getWidth(); j++) {
					int color = bimg.getRGB(j, i);
					inputimg.setRed(i, j, (color >> 16) & 0xFF);
					inputimg.setGreen(i, j, (color >> 8) & 0xFF);
					inputimg.setBlue(i, j, (color >> 0) & 0xFF);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
