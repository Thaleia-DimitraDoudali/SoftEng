package net.java.sip.communicator.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ForwardSplash extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private String CMD_CANCEL = "cmd.cancel";

	private String CMD_FORWARD = "cmd.forward";
		
	JTextField forwardTextField;
	JButton forwardButton;
	
	protected String toUser;
	
	protected void setForwardTo(String toUser) {
		//this.toUser = toUser;
		forwardTextField.setText(toUser);
		forwardButton.setEnabled(true);
	}
	public ForwardSplash(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		pack();
		centerWindow();
	}
	
	private void initComponents() {
		Container contents = getContentPane();
		contents.setLayout(new BorderLayout());

		String title = "Forward Panel";

		setTitle(title);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				dialogDone(CMD_CANCEL);
			}
		});

		// Accessibility -- all frames, dialogs, and applets should
		// have a description
		getAccessibleContext()
				.setAccessibleDescription("Forward Splash");

		String authPromptLabelValue  = "Please enter the username to forward to";

		JLabel splashLabel = new JLabel(authPromptLabelValue);
		splashLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		splashLabel.setHorizontalAlignment(SwingConstants.CENTER);
		splashLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		contents.add(splashLabel, BorderLayout.NORTH);

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridBagLayout());

		forwardTextField = new JTextField(); // needed below

		// user name label
		JLabel forwardLabel = new JLabel();
		forwardLabel.setDisplayedMnemonic('F');
		// setLabelFor() allows the mnemonic to work
		forwardLabel.setLabelFor(forwardTextField);


		int gridy = 0;

		forwardLabel.setText("Forward to:");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(forwardLabel, c);

		// user name text
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(forwardTextField, c);
		// Buttons along bottom of window
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
		// register
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		forwardButton = new JButton();
		forwardButton.setEnabled(false);
		forwardButton.setText("Ok");
		forwardButton.setActionCommand(CMD_FORWARD);
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialogDone(event);
			}
		});
		buttonPanel.add(forwardButton);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.insets = new Insets(11, 12, 11, 11);

		centerPane.add(buttonPanel, c);

		contents.add(centerPane, BorderLayout.CENTER);
		getRootPane().setDefaultButton(forwardButton);

		//setFocusTraversalPolicy(new FocusTraversalPol());

	} // initComponents()

	/**
	 * The user has selected an option. Here we close and dispose the dialog. If
	 * actionCommand is an ActionEvent, getCommandString() is called, otherwise
	 * toString() is used to get the action command.
	 * 
	 * @param actionCommand
	 *            may be null
	 */
	private void dialogDone(Object actionCommand) {
		String cmd = null;
		if (actionCommand != null) {
			if (actionCommand instanceof ActionEvent) {
				cmd = ((ActionEvent) actionCommand).getActionCommand();
			} else {
				cmd = actionCommand.toString();
			}
		}
		if (cmd == null) {
			// do nothing
		} else if (cmd.equals(CMD_CANCEL)) {
			toUser = null;
		} else if (cmd.equals(CMD_FORWARD)) {
			toUser = forwardTextField.getText();
		}
		setVisible(false);
		dispose();
	} // dialogDone()
	
	/**
	 * Centers the window on the screen.
	 */
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
	} // centerWindow()
}
