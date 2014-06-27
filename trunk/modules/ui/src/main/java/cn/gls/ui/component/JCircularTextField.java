package cn.gls.ui.component;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class JCircularTextField extends JTextField {
	 private static final long serialVersionUID = -1946802815417758252L;  
     
	    public JCircularTextField(int columns){  
	        super(columns);  
	        //  
	        setMargin(new Insets(0,5,0,5));  
	    }  
	      
	    @Override  
	    protected void paintBorder(Graphics g)  
	    {  
	        int h = getHeight();
	        int w = getWidth();  
	          
	        Graphics2D g2d = (Graphics2D)g.create();  
	        Shape shape = g2d.getClip();  
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
	        g2d.setClip(shape);  
	        g2d.drawRoundRect(0, 0, w - 2, h - 2, h, h);  
	        g2d.dispose();  
	        super.paintBorder(g2d);  
	    }  
	    public static void main(String[] args)  
	    {  
	        JFrame jf = new JFrame();  
	        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        jf.setSize(300, 200);  
	        jf.setLayout(new FlowLayout());  
	          
	        JCircularTextField text = new JCircularTextField(20);  
	  
	        jf.add(text);  
	        jf.setVisible(true);  
	    }  
	  
}
