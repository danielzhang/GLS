package cn.gls.ui.component;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;


public class JLDFrame extends JFrame  implements MouseMotionListener{
	private static final long serialVersionUID = 1L;

	private int dragEnabled;
	private Point newPoint;
	private Point oldPoint;
	public JLDFrame(){
		this.setUndecorated(true);
//		init();
		eventHandle();
//		showMe();
		this.addMouseMotionListener(this);
	}

	//实现JFrame的拖动
	public void eventHandle(){
		this.addMouseMotionListener(new MouseMotionListener(){
			int old_x;
			int old_y;
			public void mouseDragged(MouseEvent e) {
				JLDFrame.this.setLocation(JLDFrame.this.getLocation().x+e.getX()-old_x, 
						JLDFrame.this.getLocation().y+e.getY()-old_y);
			}

			public void mouseMoved(MouseEvent e) {
				old_x = e.getX();
				old_y = e.getY();
			}
			
		});
		
	}
	
	//实现JFrame的拉动
	public void mouseDragged(MouseEvent e) {
		if (dragEnabled == 0) {
			return;
		}
		oldPoint = newPoint;
		newPoint = e.getPoint();
		if (dragEnabled == 2) {
			this.setSize(new Dimension(this.getSize().width + newPoint.x
					- oldPoint.x, this.getSize().height));
		}
		if (dragEnabled == 3) {
			this.setSize(new Dimension(this.getSize().width,
					this.getSize().height + newPoint.y - oldPoint.y));
		}
		if (dragEnabled == 5) {
			this.setSize(new Dimension(this.getSize().width + newPoint.x
					- oldPoint.x, this.getSize().height + newPoint.y
					- oldPoint.y));
		}
	}

	public void mouseMoved(MouseEvent e) {
		Dimension ds = this.getSize();
		// 北：1，东：2，南：3，西：4
		if (ds.height - e.getY() > 0 && ds.height - e.getY() <= 5
				&& ds.width - e.getX() > 0 && ds.width - e.getX() <= 5) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			dragEnabled = 5;
			newPoint = e.getPoint();
		} else if (ds.width - e.getX() > 0 && ds.width - e.getX() <= 5) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			dragEnabled = 2;
			newPoint = e.getPoint();
		} else if (ds.height - e.getY() > 0 && ds.height - e.getY() <= 5) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			dragEnabled = 3;
			newPoint = e.getPoint();
		} else {
			this.setCursor(Cursor.getDefaultCursor());
			dragEnabled = 0;
		}
	}
	
//	public void showMe(){
//		this.setVisible(true);
////		this.setSize(600,500);
////		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	public static void main(String[] args) {
		new JLDFrame();
	}
}
