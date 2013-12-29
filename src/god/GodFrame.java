package god;

import util.Trace;
import gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GodFrame extends JMinSizeFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8999040258283619751L;

	/**
	 * Panel buttons
	 */
	JButton generate, save, exit;
	JCheckBox shownames, showids;
	
	Border border;

	/**
	 * Map visualization
	 */
	GuiGalaxyMap map;

	/**
	 * Right instruments panel initialization
	 * 
	 * @return
	 */
	public JComponent createControlPanel()
	{
		// Controls
		this.generate = new JButton("Generate");
		this.generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionGenerate();
			}
		});
		this.save = new JButton("Save");
		this.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionSave();
			}
		});
		this.exit = new JButton("Exit");
		this.exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionExit();
			}
		});
		this.shownames = new JCheckBox("Show names");
		this.shownames.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				map.setShowNames(((JCheckBox) ie.getItem()).isSelected());
				map.repaint();
			}
		});
		this.showids = new JCheckBox("Show IDs");
		this.showids.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				map.setShowIds(((JCheckBox) ie.getItem()).isSelected());
				map.repaint();
			}
		});
		
		JPanel rightPanel = new JPanel();
		//rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new GridLayout(5, 1));
		rightPanel.setBorder(BorderFactory.createCompoundBorder(
				border,
				new EmptyBorder(5, 5, 5, 5)));

		rightPanel.add(this.generate);
		rightPanel.add(this.save);
		rightPanel.add(this.exit);
		rightPanel.add(this.shownames);
		rightPanel.add(this.showids);
		rightPanel.setMinimumSize(new Dimension(200,2000));

		
		return rightPanel;
	}
	
	public JComponent createTracePanel()
	{
		JTraceTextArea trace = new JTraceTextArea();
		Trace.getInstance().setHandler(trace);
		JScrollPane areaScrollPane = new JScrollPane(trace);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 150));
		areaScrollPane.setBorder(border);
		
		return areaScrollPane;
	}
	
	public JComponent createMapPanel()
	{
		this.map = new GuiGalaxyMap();

		JScrollPane mapPanel = new JScrollPane(this.map);
		//mapPanel.add(this.map);
		//mapPanel.setBorder(border);
		mapPanel.setBorder(BorderFactory.createCompoundBorder(
				border,
				new EmptyBorder(2, 2, 2, 3)));
		
		return mapPanel;
	}

	/**
	 * Initialization
	 */
	public GodFrame() {
		// Standard border
		border = BorderFactory.createCompoundBorder(
				new EmptyBorder(5, 5, 5, 5),
				new EtchedBorder() 
				);

		JComponent mapPanel = createMapPanel();
		JComponent controlPanel = createControlPanel();
		JComponent tracePanel = createTracePanel();
		
		JPanel topArea = new JPanel();
		topArea.setMinimumSize(new Dimension(300,250));
		topArea.setLayout(new BorderLayout());
		topArea.add(mapPanel, BorderLayout.CENTER);
		topArea.add(controlPanel, BorderLayout.EAST);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				topArea,
				tracePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.9);
		add(splitPane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				actionExit();
			}
		});
	}

	public void actionGenerate() {
		ThreadGenerate gen = new ThreadGenerate(this);
		gen.start();
	}

	public void actionSave() {
		ThreadSave save = new ThreadSave(this);
		save.start();
	}
	
	public void actionExit() {
		System.exit(0);
	}

	/**
	 * God interface entrypoint
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		GodFrame godGui = new GodFrame();
		godGui.setExtendedState(MAXIMIZED_BOTH | godGui.getExtendedState());
		godGui.setSize(800, 600);
		godGui.setMinimumSize(640, 4);
		godGui.setTitle("sg [god mode]");
		godGui.setVisible(true);
	}
}
