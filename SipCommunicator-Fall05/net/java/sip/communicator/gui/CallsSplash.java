package net.java.sip.communicator.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.java.sip.communicator.db.BillingDB;

public class CallsSplash extends JDialog {
	private static final long serialVersionUID = 1L;

    private AuthenticationSplash authenticationSplash = null;
    String username = "";
	
	public CallsSplash(Frame parent, boolean modal, String user) {
		super(parent, modal);
		this.username = user;
		initComponents();
		pack();
		centerWindow();
	}
	
	private void initComponents() {
		
		Container contents = getContentPane();
		contents.setLayout(new BorderLayout());
		String title = "Call History";
		setTitle(title);
		setResizable(true);
		
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridBagLayout());
		
		int gridy = 0;
		JLabel jtitle = new JLabel();
		jtitle.setText("Call history:");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(jtitle, c);
		
		BillingDB dbManager = new BillingDB();
		
		String history = dbManager.getCalls(username);
//		String history = "";
		JTextArea jcolumns = new JTextArea();
		jcolumns.setText(history);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(jcolumns, c);


		contents.add(centerPane, BorderLayout.CENTER);
	}
	
	private void centerWindow() {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit()
				.getScreenSize());
		Point center = new Point((int) screen.getCenterX(),
				(int) screen.getCenterY());
		Point newLocation = new Point(center.x - this.getWidth() / 2, center.y
				- this.getHeight() / 2);
		if (screen.contains(newLocation.x, newLocation.y, this.getWidth(),
				this.getHeight())) {
			this.setLocation(newLocation);
		}
	} 
}
