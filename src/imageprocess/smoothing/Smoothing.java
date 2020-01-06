package imageprocess.smoothing;

import imageprocess.Image_Format;
import imageprocess.mask.Mask;

public class Smoothing {
	public static void smoothing(int mask[][],Image_Format inputimg,Image_Format outputimg) {
		int sum = 0;
		for(int i=0;i<Mask.num;i++) {
			for(int j=0;j<Mask.num;j++) {
				sum += mask[i][j];
			}			
		}
		for(int i=Mask.num/2;i<inputimg.getHeight()-Mask.num/2;i++)
		{
			for(int j=Mask.num/2;j<inputimg.getWidth()-Mask.num/2;j++) {
				int gray = Image_Format.Correlation(i, j, mask, sum, inputimg);
				outputimg.setGrayLevel(i, j, gray);
			}
		}
	}	
}
