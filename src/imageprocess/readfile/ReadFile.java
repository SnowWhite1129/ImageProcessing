package imageprocess.readfile;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import imageprocess.*;
import imageprocess.readJPGFile.ReadJPGFile;
import imageprocess.readPPMFile.ReadPPMFile;
import imageprocess.zoom.Zoom;

public class ReadFile {
	public static void readfile(ShowImg showImg,JButton button1,Panel inputPanel,JFileChooser file, Image_Format inputimg,Image_Format outputimg) {
		int state = file.showOpenDialog(showImg);
		if (state == JFileChooser.APPROVE_OPTION) {
			String name = file.getSelectedFile().getPath();
			String filetype = null;

			if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
				filetype = name.substring(name.lastIndexOf(".") + 1);
			
			switch (filetype) {
			case "jpg":
				ReadJPGFile.readJPGFile(name, inputimg);
				break;
			case "ppm":
				ReadPPMFile.readPPMFile(name, inputimg);
				break;
			case "bmp":
				ReadJPGFile.readJPGFile(name, inputimg);
				break;
			}
			float Magnification = Math.max((float)inputimg.getHeight()/(float)ShowImg.window_height, (float)inputimg.getWidth()/(float)ShowImg.window_width);
			
			if(Magnification>1)
			{
				Image_Format tempimg = new Image_Format();
				tempimg.Initialize((int)Math.ceil(inputimg.getHeight()/Magnification), (int)Math.ceil(inputimg.getWidth()/Magnification));
							
				Zoom.zoomout(Magnification, inputimg, tempimg);
				inputimg.copy(tempimg);				
			}			
			outputimg.copy(inputimg);
			inputPanel.initialize(inputimg);
			inputPanel.repaint();
			button1.setEnabled(true);
		}
	}
}
