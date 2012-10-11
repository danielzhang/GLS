package cn.gls.database.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class JProgressEx extends JApplet {
	private int min = 0, max = 100;
	private JProgressBar pb = new JProgressBar();
	private Timer t;
	private int i;
	JPanel panel = new JPanel();

	public JProgressEx() {
	}

	public synchronized void setValue() {
		i = 0;
	}

	public synchronized int getValue() {
		return i;
	}

	public synchronized void addValue() {
		i++;
	}

	public void setMin() {
		this.min = min;
	}

	public int getMin() {
		return min;
	}

	public void setMax() {
		this.max = max;
	}

	public int getMax() {
		return max;
	}

	public void init() {
		Container cp = getContentPane();
		pb.setMinimum(min);
		pb.setMaximum(max);
		pb.setBackground(Color.white);
		pb.setForeground(Color.red);
		pb.setStringPainted(true);
		cp.add(panel.add(pb), BorderLayout.PAGE_START);
	}

	public void start() {
		t = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (getValue() < getMax()) {
					addValue();
					pb.setValue(getValue());
				} else {
					setValue();
					t.stop();
				}
			}
		});
		t.start();
	}

	public static void main(String[] args) {
		final JProgressEx pg = new JProgressEx();
		JFrame frame = new JFrame(" JProgressEx ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(pg);
		frame.setSize(300, 200);
		pg.init();
		frame.setVisible(true);
		pg.start();
	}
}
