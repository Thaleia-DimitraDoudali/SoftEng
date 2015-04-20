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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BlockSplash extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String CMD_CANCEL = "cmd.cancel";

	private String CMD_UNBLOCK = "cmd.unblock";

	private String CMD_BLOCK = "cmd.block";

	JTextArea unblocklist;
	JTextField blockControlTextField;
	JButton unblockButton;
	JButton blockButton;

	protected String toUser;
	protected String action;

	protected void blockList(String blocklist) {
		// this.toUser = toUser;
		unblocklist.setText(blocklist);
		unblocklist.setEditable(false);
		// blockControlTextField.setText(blocklist);
		unblockButton.setEnabled(true);
	}

	public BlockSplash(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		pack();
		centerWindow();
	}

	private void initComponents() {
		Container contents = getContentPane();
		contents.setLayout(new BorderLayout());

		String title = "Block Control Panel";

		setTitle(title);
		setResizable(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				dialogDone(CMD_CANCEL);
			}
		});

		// Accessibility -- all frames, dialogs, and applets should
		// have a description
		getAccessibleContext().setAccessibleDescription("Unblock Splash");

		String authPromptLabelValue = "Please enter the username to block or unblock";

		JLabel splashLabel = new JLabel(authPromptLabelValue);
		splashLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		splashLabel.setHorizontalAlignment(SwingConstants.CENTER);
		splashLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		contents.add(splashLabel, BorderLayout.NORTH);

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridBagLayout());

		blockControlTextField = new JTextField(); // needed below
		unblocklist = new JTextArea();// new JLabel();

		// user name label
		JLabel unblockLabel = new JLabel();
		unblockLabel.setDisplayedMnemonic('U');
		// setLabelFor() allows the mnemonic to work
		unblockLabel.setLabelFor(blockControlTextField);

		int gridy = 0;
		JLabel blockListLabel = new JLabel();
		blockListLabel.setText("Blocked users:");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(blockListLabel, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(unblocklist, c);

		unblockLabel.setText("Select user:");
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(12, 12, 0, 0);
		centerPane.add(unblockLabel, c);
		// user name text
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.insets = new Insets(12, 7, 0, 11);
		centerPane.add(blockControlTextField, c);
		// Buttons along bottom of window
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
		// register
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		blockButton = new JButton();
		blockButton.setEnabled(true);
		blockButton.setText("Block");
		blockButton.setActionCommand(CMD_BLOCK);
		blockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialogDone(event);
			}
		});
		buttonPanel.add(blockButton);

		unblockButton = new JButton();
		unblockButton.setEnabled(true);
		unblockButton.setText("Unblock");
		unblockButton.setActionCommand(CMD_UNBLOCK);
		unblockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialogDone(event);
			}
		});
		buttonPanel.add(unblockButton);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.insets = new Insets(11, 12, 11, 11);

		centerPane.add(buttonPanel, c);

		contents.add(centerPane, BorderLayout.CENTER);
		getRootPane().setDefaultButton(unblockButton);

		// setFocusTraversalPolicy(new FocusTraversalPol());

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
		} else if (cmd.equals(CMD_UNBLOCK)) {
			toUser = blockControlTextField.getText();
			action = "unblock";
		} else if (cmd.equals(CMD_BLOCK)) {
			toUser = blockControlTextField.getText();
			action = "block";
		}
		setVisible(false);
		dispose();
	} // dialogDone()
	
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

