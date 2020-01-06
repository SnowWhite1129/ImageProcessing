package imageprocess.morphlogical;

import imageprocess.Image_Format;

import imageprocess.mask.Mask;

public class Erosion {
	public static void erosion(int mask[][],Image_Format inputimg,Image_Format outputimg)
	{
		for(int i=Mask.num/2;i<inputimg.getHeight()-Mask.num/2;i++)
		{
			for(int j=Mask.num/2;j<inputimg.getWidth()-Mask.num/2;j++) {
				int gray = Image_Format.Erosion(i, j, mask, inputimg);
				if(gray>255)
				{
					gray =255;
				}
				else if (gray<0) {
					gray = 0;
				}
				outputimg.setGrayLevel(i, j, gray);
			}
		}
	}
}
