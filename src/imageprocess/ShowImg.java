package imageprocess;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import imageprocess.readfile.*;
import imageprocess.showIcon.*;
import imageprocess.translateAndrotate.*;
import imageprocess.zoom.*;
import imageprocess.capture.Capture;
import imageprocess.fileiconview.*;
import imageprocess.histogram.Histogram;
import imageprocess.mask.Mask;
import imageprocess.mask.Matrix;

public class ShowImg extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton button1 = new JButton("���");

	JFileChooser file = new JFileChooser(".");
	
	Image_Format inputimg = new Image_Format();
	Image_Format outputimg = new Image_Format();

	Panel inputPanel = new Panel();
	Panel outputPanel = new Panel();
	
	final public static int window_height = 700;
	final public static int window_width = 500; 

	public JMenuBar buildMenu(ShowImg showImg) {
		JMenuBar mbar = new JMenuBar();
		JMenu menu = new JMenu("�ɮ�");
		JMenu function = new JMenu("�\��");
		JMenu histogram = new JMenu("�����");
		JMenu mask = new JMenu("�v���B�n");
		JMenu morphology = new JMenu("�κA��");
		menu.setMnemonic(KeyEvent.VK_F);
		function.setMnemonic(KeyEvent.VK_F);
		mbar.add(menu);
		mbar.add(function);
		mbar.add(histogram);
		mbar.add(mask);
		mbar.add(morphology);
		inputPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent event) {
				// TODO �۰ʲ��ͪ���k Stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent event) {
				// TODO �۰ʲ��ͪ���k Stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent event) {
				// TODO �۰ʲ��ͪ���k Stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent event) {
				// TODO �۰ʲ��ͪ���k Stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent event) {
				int i = event.getY();
				int j = event.getX();
				
				int r = inputimg.getred(i, j);
				int g = inputimg.getGreen(i, j);
				int b = inputimg.getBlue(i, j);
				new Capture(r,g,b, inputimg ,outputimg ,outputPanel);
			}
		});			
		JMenuItem item = new JMenuItem("�}��", KeyEvent.VK_O);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadFile.readfile(showImg, button1, inputPanel, file,inputimg,outputimg);
				outputPanel.initialize(outputimg);
			}
		});
		menu.add(item);

		item = new JMenuItem("��j���Y�p", KeyEvent.VK_I);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Zoom(outputimg,outputPanel);
			}
		});
		function.add(item);
		
		item = new JMenuItem("�����P����", KeyEvent.VK_R);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TranslateAndRotate(inputimg, outputimg, outputPanel);
			}
		});
		function.add(item);
		
		item = new JMenuItem("�k�ϥ���", KeyEvent.VK_R);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputimg.copy(outputimg);
				inputPanel.initialize(outputimg);
				inputPanel.repaint();
			}
		});
		function.add(item);
		
		item = new JMenuItem("�����");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Histogram(inputimg);		
			}
		});
		histogram.add(item);
		
		item = new JMenuItem("�v�����Ƥ�");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Matrix(inputimg, outputimg, inputPanel,outputPanel, Mask.MaskConstant.SMOOTHING);
			}
		});
		mask.add(item);
		
		item = new JMenuItem("��t����");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				new Mask(inputimg, outputimg,inputPanel, outputPanel, Mask.MaskConstant.EDGEDETECTION,3);			
			}
		});
		mask.add(item);
		
		item = new JMenuItem("�I�k");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				new Matrix(inputimg, outputimg, inputPanel,outputPanel, Mask.MaskConstant.EROSION);
				
			}
		});
		morphology.add(item);
		
		item = new JMenuItem("����");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				new Matrix(inputimg, outputimg, inputPanel,outputPanel, Mask.MaskConstant.DILATION);
			}
		});
		morphology.add(item);
		
		return mbar;
	}
	public ShowImg() {

		JPanel cp = (JPanel) this.getContentPane();
		JPanel buttomPanel = new JPanel();
		
		inputimg.whiteimg();
		outputimg.whiteimg();
		
		inputPanel.initialize(inputimg);
		outputPanel.initialize(outputimg);

		button1.setEnabled(false);
		buttomPanel.add(button1);

		button1.addActionListener(new ButtonListener());

		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & BMP &PPM files", "jpg", "bmp", "ppm");
		file.setFileFilter(filter);
		file.setAccessory(new ImagePreviewer(file));
		file.setFileView(new FileIconView(filter, new ImageIcon()));

		cp.add(buildMenu(this), BorderLayout.NORTH);
		cp.add(inputPanel, BorderLayout.WEST);

		inputPanel.setPreferredSize(new Dimension(window_width, window_height));
		cp.add(outputPanel, BorderLayout.EAST);
		outputPanel.setPreferredSize(new Dimension(window_width, window_height));
		cp.add(buttomPanel, BorderLayout.SOUTH);
		this.setSize(1024, 768);
		this.setResizable(false);
		this.setTitle("IP 40447039S");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == button1) {
				outputPanel.repaint();
				button1.setEnabled(false);
			}
		}
	}

	public static void main(String args[]) {		
		new ShowImg();
	}

}
