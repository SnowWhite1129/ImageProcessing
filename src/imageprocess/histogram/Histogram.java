package imageprocess.histogram;
import javax.swing.JFrame;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import imageprocess.HistogramPanel;
import imageprocess.Image_Format;
import imageprocess.Panel;
import imageprocess.zoom.Zoom;


public class Histogram {
	static final public int MaxValue = 256;
	JFrame myFrame = new JFrame("直方圖均質化");
	private Panel inputPanel = new Panel();
	private Panel outputPanel = new Panel();
	private HistogramPanel inhist = new HistogramPanel();
	private HistogramPanel outhist = new HistogramPanel();
	private Image_Format outputimg = new Image_Format();
	
	static public final int Panel_width = 500;
	static public final int Panel_height = 350;
	
	public Histogram(Image_Format inputimg) {
		Container cp = myFrame.getContentPane();
		cp.setLayout(null);
		Image_Format tempimg = new Image_Format();
		
		float Magnification = Math.max((float)inputimg.getHeight()/(float)Panel_height, (float)inputimg.getWidth()/(float)Panel_width);
		
		if(Magnification>1)
		{
			tempimg.Initialize((int)Math.ceil(inputimg.getHeight()/Magnification), (int)Math.ceil(inputimg.getWidth()/Magnification));
						
			Zoom.zoomout(Magnification, inputimg, tempimg);				
		}
		else
		{
			tempimg.copy(inputimg);
		}
		tempimg.RGBtoGrayLevel();
		
		outputimg.Initialize(tempimg.getHeight(), tempimg.getWidth());
		equalization(tempimg,outputimg,inhist,outhist);
		
		inputPanel.initialize(tempimg);
		outputPanel.initialize(outputimg);
		
		cp.add(inputPanel);
		cp.add(outputPanel);
		cp.add(inhist);
		cp.add(outhist);
		
		inputPanel.setBounds(0, 0, Panel_width, Panel_height);
		inhist.setBounds(Panel_width+20, 0, Panel_width, Panel_height);
		outputPanel.setBounds(0, Panel_height+20, Panel_width, Panel_height);
		outhist.setBounds(Panel_width+20, Panel_height+20, Panel_width, Panel_height);
		
		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				myFrame.dispose();
			}
		});		
		myFrame.setSize(1024,768);
		myFrame.setResizable(false);
		myFrame.setVisible(true);		
	}
	public void equalization(Image_Format inputimg,Image_Format outputimg,HistogramPanel inhistPanel,HistogramPanel outhistPanel) {
		int []inhist = new int[MaxValue];
		int []outhist = new int[MaxValue];
		int min;
		for(int i=0;i<inputimg.getHeight();i++)
		{
			for(int j=0;j<inputimg.getWidth();j++)
			{
				inhist[inputimg.getred(i, j)]++;
			}
		}
		min = searchminindex(inhist);
		inhistPanel.initialize(normaliztion(inhist));
		int []cumulative = new int[MaxValue];
		cumulative[0] = inhist[0]; 
		for(int i=1;i<MaxValue;i++) {
			cumulative[i] = cumulative[i-1]+inhist[i];
		}
		int []T = new int [MaxValue];
		for(int i=min;i<MaxValue;i++)
		{
			T[i] = Math.round((float)((cumulative[i])-inhist[min]) / (float)(cumulative[MaxValue-1])*(MaxValue-1));			
		}
		for(int i=0;i<outputimg.getHeight();i++)
		{
			for(int j=0;j<outputimg.getWidth();j++)
			{
				int gray = T[inputimg.getred(i, j)];
				outputimg.setRGB(i, j, gray, gray, gray);
			}
		}
		for(int i=0;i<inputimg.getHeight();i++)
		{
			for(int j=0;j<inputimg.getWidth();j++)
			{
				outhist[outputimg.getred(i, j)]++;
			}
		}
		outhistPanel.initialize(normaliztion(outhist));
	}
	public int[] normaliztion(int hist[]) {
		int max = 0;
		int temp[] = new int [hist.length];
		for(int i=0;i<hist.length;i++)
		{
			if(hist[i]>max)
			{
				max = hist[i];
			}
		}
		for(int i=0;i<hist.length;i++)
		{
			temp[i] = (int)((float)(hist[i]) / (float)(max) * 340);
		}
		return temp;
	}
	public int searchminindex(int arr[]) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]>0) {
				return i;
			}
		}
		return 0;
	}
}
