package gui;

import javax.swing.*;
import util.TraceInterface;

public class JTraceTextArea extends JTextArea implements TraceInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3233879149497248150L;

	public JTraceTextArea() {
		this.setEditable(false);
	}

	public void message(String msg) {
		this.setText(this.getText() + msg + "\r\n");
		this.setCaretPosition(this.getText().length());
	}

	public void warning(String msg) {
		this.setText(this.getText() + msg + "\r\n");
		this.setCaretPosition(this.getText().length());
	}

	public void error(String msg) {
		this.setText(this.getText() + msg + "\r\n");
		this.setCaretPosition(this.getText().length());
	}

	public void critical(String msg) {
		this.setText(this.getText() + msg + "\r\n");
		this.setCaretPosition(this.getText().length());
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), msg);
	}
}
