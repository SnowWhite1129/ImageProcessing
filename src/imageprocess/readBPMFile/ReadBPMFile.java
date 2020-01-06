package imageprocess.readBPMFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import imageprocess.*;
public class ReadBPMFile{
	public void readBPMFile(String path,Image_Format inputimg) {
		try {

			FileInputStream fin = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fin);

			byte[] array1 = new byte[14];
			bis.read(array1, 0, 14);

			byte[] array2 = new byte[40];
			bis.read(array2, 0, 40);
			
			inputimg.Initialize(ChangeInt(array2, 11),ChangeInt(array2, 7));

			getInf(bis,inputimg);

			fin.close();
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getInf(java.io.BufferedInputStream bis,Image_Format inputimg) {
		int skip_width = 0;
		int m = inputimg.getWidth() * 3 % 4;
		if (m != 0) {
			skip_width = 4 - m;
		}

		for (int i = inputimg.getHeight() - 1; i >= 0; i--) {
			for (int j = 0; j < inputimg.getWidth(); j++) {
				try {

					inputimg.setBlue(i, j, bis.read()); 
					inputimg.setGreen(i, j,bis.read());
					inputimg.setRed(i, j,bis.read());

					//outblue[i][j] = blue[i][j];
					//outred[i][j] = red[i][j];
					//outgreen[i][j] = green[i][j];

					if (j == 0) {
						bis.skip(skip_width);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public int ChangeInt(byte[] array2, int start) {

		int i = (int) ((array2[start] & 0xff) << 24) | ((array2[start - 1] & 0xff) << 16)
				| ((array2[start - 2] & 0xff) << 8) | (array2[start - 3] & 0xff);
		return i;
	}
}