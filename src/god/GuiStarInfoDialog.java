package god;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import world.Star;

public class GuiStarInfoDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1882707662596072789L;

	/**
	 * Dialog constructor
	 * 
	 * @param frame
	 * @param star object to display
	 */
	public GuiStarInfoDialog(Frame frame, Star star) {
		super(frame, "", true);
		setLayout(new GridLayout(6, 2));
		setSize(300, 200);
		this.setResizable(false);

		setTitle(star.getName() + " - star info");
		
		add(new JLabel("#"));
		add(new JLabel("" + star.getId()));
		
		add(new JLabel("Name:"));
		add(new JLabel(star.getName()));

		add(new JLabel("Coordinates:"));
		add(new JLabel("(" + star.getX() + ", " + star.getY() + ")"));

		add(new JLabel("Temperature:"));
		add(new JLabel("" + star.getTemperature()+  " K"));

		add(new JLabel("Planets:"));
		add(new JLabel("" + star.getPlanets().size()));

		JButton okButton;
		add(okButton = new JButton("Close"));
		okButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		dispose();
	}

}
