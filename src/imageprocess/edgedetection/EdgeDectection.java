package imageprocess.edgedetection;

import imageprocess.Image_Format;
import imageprocess.histogram.Histogram;
import imageprocess.mask.Mask;

public class EdgeDectection {
	public static boolean edgedetection(int mask[][],Image_Format inputimg,Image_Format outputimg) {
		int sum = 0;
		for(int i=0;i<Mask.num;i++) {
			for(int j=0;j<Mask.num;j++) {
				sum += mask[i][j];
			}			
		}
		if(sum!=0) {
			return false;
		}
		else {
			for(int i=Mask.num/2;i<inputimg.getHeight()-Mask.num/2;i++)
			{
				for(int j=Mask.num/2;j<inputimg.getWidth()-Mask.num/2;j++) {
					int gray = Image_Format.Correlation(i, j, mask, inputimg);
					if(gray<0)
					{
						gray = 0;
					}
					else if(gray>=Histogram.MaxValue)
					{
						gray = Histogram.MaxValue-1;
					}
					outputimg.setGrayLevel(i, j, gray);
				}
			}
			return true;
		}
	}
}
