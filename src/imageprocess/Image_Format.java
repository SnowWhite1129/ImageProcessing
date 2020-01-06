package imageprocess;

import imageprocess.ShowImg;
import imageprocess.histogram.Histogram;
import imageprocess.mask.Mask;

public class Image_Format {
	private int width, height;
	private int red[][];
	private int green[][];
	private int blue[][];

	public void Initialize(int height, int width) {
		this.height = height;
		this.width = width;
		this.red = new int[height][width];
		this.green = new int[height][width];
		this.blue = new int[height][width];
	}

	public int getred(int i, int j) {
		return red[i][j];
	}

	public int getGreen(int i, int j) {
		return green[i][j];
	}

	public int getBlue(int i, int j) {
		return blue[i][j];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setRGB(int i, int j, int r, int g, int b) {
		setRed(i, j, r);
		setBlue(i, j, b);
		setGreen(i, j, g);
	}

	public void setRed(int i, int j, int r) {
		red[i][j] = r;
	}

	public void setBlue(int i, int j, int b) {
		blue[i][j] = b;
	}

	public void setGreen(int i, int j, int g) {
		green[i][j] = g;
	}

	public boolean existence(int red[][], int blue[][], int green[][]) {
		if (red != null && blue != null && green != null) {
			return true;
		} else {
			return false;
		}
	}

	public int[][] getred() {
		return red;
	}

	public int[][] getblue() {
		return blue;
	}

	public int[][] getgreen() {
		return green;
	}

	void setHW(int height, int width) {
		setWidth(width);
		setHeight(height);
	}

	void setWidth(int width) {
		this.width = width;
	}

	void setHeight(int height) {
		this.height = height;
	}

	public void copy(Image_Format image_Format) {
		this.Initialize(image_Format.getHeight(), image_Format.getWidth());
		for (int i = 0; i < image_Format.getHeight(); i++)
			for (int j = 0; j < image_Format.getWidth(); j++) {
				this.setRGB(i, j, image_Format.getred(i, j), image_Format.getGreen(i, j), image_Format.getBlue(i, j));
			}
	}

	public void RGBtoGrayLevel() {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				int gray = (int) (this.getred(i, j) * 0.2126 + this.getGreen(i, j) * 0.7152
						+ this.getBlue(i, j) * 0.0722);
				this.setRGB(i, j, gray, gray, gray);
			}
		}
	}

	public void whiteimg() {
		this.Initialize(ShowImg.window_height, ShowImg.window_width);
		for (int i = 0; i < ShowImg.window_height; i++) {
			for (int j = 0; j < ShowImg.window_width; j++) {
				this.setRGB(i, j, Histogram.MaxValue - 1, Histogram.MaxValue - 1, Histogram.MaxValue - 1);
			}
		}
	}

	public void setGrayLevel(int i, int j, int graylevel) {
		setRed(i, j, graylevel);
		setBlue(i, j, graylevel);
		setGreen(i, j, graylevel);
	}
	public static int Correlation(int x ,int y,int mask[][],int sum,Image_Format img) {
		int gray = 0;
		for(int i = 0;i< Mask.num;i++) {
			for(int j = 0;j<Mask.num;j++) {
				gray += mask[i][j] * img.getBlue(x+i-Mask.num/2, y+j-Mask.num/2);
			}
		}
		return (int) ((float)gray / (float)sum);
	}
	public static int Correlation(int x ,int y,int mask[][],Image_Format img) {
		int gray = 0;
		for(int i = 0;i< Mask.num;i++) {
			for(int j =0;j<Mask.num;j++) {
				gray += mask[i][j] * img.getBlue(x+i-Mask.num/2, y+j-Mask.num/2);
			}
		}
		return gray+128 ;
	}
	public static int Erosion(int x ,int y,int mask[][],Image_Format img) {
		int min = 255;
		for(int i = 0;i< Mask.num;i++) {
			for(int j =0;j<Mask.num;j++) {
				if(mask[i][j]==0)
				{
					continue;
				}
				int gray = mask[i][j] * img.getBlue(x+i-Mask.num/2, y+j-Mask.num/2);
				if(gray < min)
				{
					min = gray;
				}
			}
		}
		return min;
	}
	public static int Dilation(int x ,int y,int mask[][],Image_Format img) {
		int max = 0;
		for(int i = 0;i< Mask.num;i++) {
			for(int j =0;j<Mask.num;j++) {
				if(mask[i][j]==0)
				{
					continue;
				}
				int gray = mask[i][j] * img.getBlue(x+i-Mask.num/2, y+j-Mask.num/2);
				if(gray > max)
				{
					max = gray;
				}
			}
		}
		return max;
	}
}
