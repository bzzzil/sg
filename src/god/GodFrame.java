package god;

import util.Trace;
import gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class GodFrame extends JMinSizeFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8999040258283619751L;

    private final Border border;

	/**
	 * Map visualization
	 */
	GuiGalaxyMap map;

	/**
	 * Right instruments panel initialization
	 * 
	 * @return JComponent
	 */
    JComponent createControlPanel()
	{
		// Controls
		/*
	  Panel buttons
	 */
        JButton generate = new JButton("Generate");
		generate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                actionGenerate();
            }
        });
        JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                actionSave();
            }
        });
        JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                actionExit();
            }
        });
        JCheckBox showNames = new JCheckBox("Show Names");
		showNames.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                map.setShowNames(((JCheckBox) ie.getItem()).isSelected());
                map.repaint();
            }
        });
        JCheckBox showIds = new JCheckBox("Show IDs");
		showIds.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                map.setShowIds(((JCheckBox) ie.getItem()).isSelected());
                map.repaint();
            }
        });
        JCheckBox showRulers = new JCheckBox("Show Rulers");
		showRulers.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                map.setShowRulers(((JCheckBox) ie.getItem()).isSelected());
                map.repaint();
            }
        });
		
		JPanel rightPanel = new JPanel();
		//rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new GridLayout(6, 1));
		rightPanel.setBorder(BorderFactory.createCompoundBorder(
				border,
				new EmptyBorder(5, 5, 5, 5)));

		rightPanel.add(generate);
		rightPanel.add(save);
		rightPanel.add(exit);
		rightPanel.add(showNames);
		rightPanel.add(showIds);
		rightPanel.add(showRulers);
		rightPanel.setMinimumSize(new Dimension(200,2000));

		
		return rightPanel;
	}
	
	JComponent createTracePanel()
	{
		JTraceTextArea trace = new JTraceTextArea();
		Trace.getInstance().setHandler(trace);
		JScrollPane areaScrollPane = new JScrollPane(trace);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 150));
		areaScrollPane.setBorder(border);
		
		return areaScrollPane;
	}
	
	JComponent createMapPanel()
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
    private GodFrame() {
		// Standard border
		border = BorderFactory.createCompoundBorder(
				new EmptyBorder(5, 5, 5, 5),
				new EtchedBorder() 
				);

		JComponent mapPanel = createMapPanel();
		JComponent controlPanel = createControlPanel();
		JComponent tracePanel = createTracePanel();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setMinimumSize(new Dimension(300, 250));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mapPanel, BorderLayout.CENTER);
		mainPanel.add(controlPanel, BorderLayout.EAST);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, tracePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.9);
		add(splitPane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				actionExit();
			}
		});
	}

	void actionGenerate() {
		ThreadGenerate gen = new ThreadGenerate(this);
		gen.start();
	}

	void actionSave() {
		ThreadSave save = new ThreadSave(this);
		save.start();
	}
	
	void actionExit() {
		System.exit(0);
	}

	/**
	 * God interface entrypoint
	 * 
	 * @param args command line arguments
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
