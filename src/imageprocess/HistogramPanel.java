package imageprocess;

import java.awt.Color;
import java.awt.Graphics;
import imageprocess.histogram.Histogram;

import javax.swing.JPanel;

public class HistogramPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private int []hist;
	private final int gray_level = 150;
	public void initialize(int hist[]) {
		this.hist = new int [Histogram.MaxValue];
		for(int i=0;i<Histogram.MaxValue;i++)
		{
			this.hist[i] = hist[i];
		}		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (hist != null) {
			g.setColor(Color.black);  
			  
	        g.drawLine(0, 0, 0, Histogram.Panel_height-10); // Y  
	        g.drawLine(0, Histogram.Panel_height-10 , Histogram.MaxValue, Histogram.Panel_height-10);// X
	        
	        for(int i=0;i<Histogram.MaxValue;i++)
	        {
	        	g.setColor(new Color(i, i, i));
	        	g.fillRect(i, Histogram.Panel_height-6, 1, 5);
	        }
	        
	        g.setColor(new Color(gray_level, gray_level, gray_level));
			for (int i = 0; i < Histogram.MaxValue; i++) {
				g.fillRect(i+1, Histogram.Panel_height-hist[i]-11, 1, hist[i]);  
			}
		}
	}
}
