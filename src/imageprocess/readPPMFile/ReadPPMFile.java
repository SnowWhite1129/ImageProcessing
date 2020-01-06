package imageprocess.readPPMFile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import imageprocess.*;
public class ReadPPMFile{
	public static void readPPMFile(String path,Image_Format inputimg) {
		try {
			FileInputStream fis = new FileInputStream(path);

			Scanner scan = new Scanner(fis);
			// Discard the magic number
			scan.nextLine();
			// Read pic width, height and max value
			int width = scan.nextInt();
			int height =scan.nextInt();
			
			inputimg.Initialize(height, width);

			scan.nextInt();//max_value

			fis.close();

			// Now parse the file as binary data
			fis = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(fis);

			// look for 4 lines (i.e.: the header) and discard them
			int numnewlines = 3;
			while (numnewlines > 0) {
				char c;
				do {
					c = (char) (dis.readUnsignedByte());
				} while (c != '\n');
				numnewlines--;
			}

			// read the image data
			inputimg.Initialize(inputimg.getHeight(), inputimg.getWidth());
			
			for (int i = 0; i < inputimg.getHeight(); i++) {
				for (int j = 0; j < inputimg.getWidth(); j++) {

					inputimg.setRed(i, j,dis.readUnsignedByte());
					inputimg.setGreen(i, j, dis.readUnsignedByte());
					inputimg.setBlue(i, j,dis.readUnsignedByte());

					//outblue[row][col] = blue[row][col];
					//outred[row][col] = red[row][col];
					//outgreen[row][col] = green[row][col];
				}
			}
			dis.close();
			scan.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}



