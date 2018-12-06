import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class MesonetFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * menu bar 
	 */
	private FileMenuBar fileMenuBar;
	/** 
	 * statistics panel
	 */
	private StatisticsPanel statistics;
	/**
	 * parameter panel 
	 */
	private ParameterPanel parameters;
	/**
	 * table panel 
	 */
	private TablePanel tablePanel;
	/**
	 * combine parameter panel and statistics panel into one - left panel 
	 */
	private LeftPanel leftPanel;

	/**
	 * JPanel button panel 
	 */
	private createButtonPanel buttonPanel;
	/** 
	 *JButton calculate button 
	 */

	private JButton calcButton;
	/**
	 * JButton exit button 
	 */
	private JButton exitButton;

	private createTopPanel topPanel;
	private JLabel label;

	public Object [] dataPoints = new Object[6];

	public String[] columnNames = {"Stations","Parameter", "Statistics",  "Value", "Reporting Stations", "Date"};
	public Object [] [] dataValues = {{ "Stations","Parameter", "Statistics",  "Value", "Reporting Stations", "Date"}};
	public JTable table = new JTable((new DefaultTableModel(dataValues, columnNames)));
	public DefaultTableModel model = (DefaultTableModel)table.getModel();

	public MesonetFrame ()
	{
		super("Oklahoma Mesonet - Statistics Calculator");
		setLayout(new BorderLayout());

		fileMenuBar = new FileMenuBar();
		topPanel = new createTopPanel();
		statistics = new StatisticsPanel();
		parameters = new ParameterPanel();
		buttonPanel = new createButtonPanel();
		//tablePanel = new TablePanel();
		//        leftPanel = new LeftPanel();


		setJMenuBar(fileMenuBar);
		add(topPanel, BorderLayout.NORTH);


		//add(topPanel, BorderLayout.NORTH);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(1,2));
		leftPanel.setBackground(Color.WHITE);
		leftPanel.add(parameters);
		leftPanel.add(statistics);
		add(leftPanel, BorderLayout.LINE_START);


		add(buttonPanel, BorderLayout.PAGE_END);
		add(table, BorderLayout.CENTER);
		//pack();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	public class FileMenuBar extends JMenuBar
	{
		/**
		 * Menu for menu bar 
		 */
		private JMenu menu;

		/**
		 * Menu options
		 */
		private JMenuItem MenuOpenFile;
		private JMenuItem MenuExit;

		/**
		 * choose file
		 */
		private JFileChooser chooseFile;

		/**
		 * list of files
		 */
		private ArrayList<String> fileList;

		public FileMenuBar()
		{
			fileList = new ArrayList<>();

			menu = new JMenu("FIle");
			add(menu);
			MenuOpenFile = new JMenuItem("Open Data File");
			MenuExit = new JMenuItem("Exit");
			menu.add(MenuOpenFile);
			menu.add(MenuExit);

			MenuExit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			});

			chooseFile = new JFileChooser(new File("C:\\Users\\mikic\\Documents\\files"));
			//open file

			MenuOpenFile.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					chooseFile.showOpenDialog(MenuOpenFile);
				}
			});
		}
		public ArrayList<String> getFileList()
		{
			return (ArrayList<String>) fileList.clone();
		}
	}



	public class createTopPanel extends JPanel
	{
		private JLabel bar;

		private createTopPanel()
		{
			super();
			bar = new JLabel();
			bar.setText("Mesonet - We don't set records, we report them!");
			add(bar);


		}
	}

	// statistics panel

	public class StatisticsPanel extends JPanel
	{
		public final static String MIN_BUTTON = "MINIMUM";
		public final static String AVG_BUTTON = "AVERAGE";
		public final static String MAX_BUTTON = "MAXIMUM";
		private JRadioButton minBut;
		private JRadioButton avgBut;
		private JRadioButton maxBut;

		private ButtonGroup buttonGroup;

		public StatisticsPanel()
		{
			super();
			setLayout(new GridLayout(5,20,20,100));
			setBorder(BorderFactory.createTitledBorder("Statistics"));
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Statistics"));
			setOpaque(true);
			setBackground(Color.GRAY);
			setLayout(new GridLayout(0,1));
			minBut = new JRadioButton(MIN_BUTTON);
			avgBut = new JRadioButton(AVG_BUTTON);
			maxBut = new JRadioButton(MAX_BUTTON);


			buttonGroup = new ButtonGroup();
			buttonGroup.add(minBut);
			buttonGroup.add(avgBut);
			buttonGroup.add(maxBut);
			minBut.setBackground(Color.GRAY);
			maxBut.setBackground(Color.GRAY);
			avgBut.setBackground(Color.GRAY);
			add(minBut);
			add(avgBut);
			add(maxBut);

			setBackground(Color.GRAY);
		}

		public String getStatisticsType()
		{
			String button = "";

			if (minBut.isSelected());
			{
				button = "min";
			}
			if (avgBut.isSelected())
			{
				button = "avg";
			}
			if (maxBut.isSelected())
			{
				button = "max";
			}

			return button;
		}
	}


	//parameter panel

	public class ParameterPanel extends JPanel
	{

		public final String TAIR = "TAIR";
		public final String TA9M = "TA9M";
		public final String SRAD = "SRAD";
		public final String WSPD = "WSPD";
		public final String PRES = "PRES";

		private JCheckBox airTemp;
		private JCheckBox ta9m;
		private JCheckBox srad;
		private JCheckBox wspd;
		private JCheckBox pres;

		public ParameterPanel()
		{
			super();
			setLayout(new GridLayout(5, 20, 20, 10));
			setBorder(BorderFactory.createTitledBorder("Parameter"));
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Parameters"));

			setBackground(Color.GRAY);
			airTemp = new JCheckBox("TAIR");
			ta9m = new JCheckBox("TA9M");
			srad = new JCheckBox("SRAD");
			wspd = new JCheckBox("WSPD");
			pres = new JCheckBox("PRES");

			airTemp.setBackground(Color.GRAY);
			ta9m.setBackground(Color.GRAY);
			srad.setBackground(Color.GRAY);
			wspd.setBackground(Color.GRAY);
			pres.setBackground(Color.GRAY);
			
			add(airTemp);
			add(ta9m);
			add(srad);
			add(wspd);
			add(pres);

		}

		public ArrayList<String> getParamIds()
		{
			ArrayList<String> params = new ArrayList();

			if (airTemp.isSelected())
			{
				params.add(TAIR);
			}
			if (ta9m.isSelected())
			{
				params.add(TA9M);
			}
			if (srad.isSelected())
			{
				params.add(SRAD);
			}
			if (wspd.isSelected())
			{
				params.add(WSPD);
			}
			if (pres.isSelected())
			{
				params.add(PRES);
			}
			return params;

		}
	}


	public class createButtonPanel extends JPanel
	{
		private createButtonPanel()
		{
			super();
			//buttonPanel = new JPanel();
			//setLayout(new GridLayout(1,1));
			setBackground(Color.GRAY);
			calcButton = new JButton("Calulate");
			exitButton = new JButton("Exit");
			add(calcButton);
			add(exitButton);
			//make button do what button do
//			calcButton.addActionListener(new ActionListener(
//			{
//				public void actionPerformed(ActionEvent e)
//				{
//						if (maxBut.isSelected())
//						{
//							param[0]:
//							param[1]:
//						}
//				}
//			});
			exitButton.addActionListener(new ActionListener()
			{ 
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					System.exit(0);

				}
			});

		}
	}








}
