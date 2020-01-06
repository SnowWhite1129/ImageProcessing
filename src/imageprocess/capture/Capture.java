package imageprocess.capture;

import imageprocess.Panel;
import imageprocess.Image_Format;

public class Capture {
	public Capture(int r,int g,int b,Image_Format inputimg,Image_Format outputimage,Panel outputPanel)
	{
		for(int i=0;i<inputimg.getHeight();i++)
		{
			for(int j=0;j<inputimg.getWidth();j++)
			{
				if(Math.abs(r-inputimg.getred(i, j))<60 && Math.abs(g-inputimg.getGreen(i, j))<60 && Math.abs(b-inputimg.getBlue(i, j))<60)
				{
					outputimage.setRGB(i, j, inputimg.getred(i, j), inputimg.getGreen(i, j), inputimg.getBlue(i, j));
				}
				else
				{
					outputimage.setRGB(i, j, 127, 127, 127);
				}
			}
		}
		outputPanel.initialize(outputimage);
		outputPanel.repaint();
	}
}
