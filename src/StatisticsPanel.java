import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StatisticsPanel extends JPanel
{
    public final static String MIN_BUTTON = "MINIMUM";
    public final static String AVG_BUTTON = "AVERAGE";
	public final static String MAX_BUTTON = "MAXIMUM";
	private JRadioButton minBut;
	private JRadioButton avgBut;
    private JRadioButton maxBut;
    
    private ButtonGroup buttonGroup;
    
    public void StatististicsPanel()
    {
        setBorder(BorderFactory.createTitledBorder("Statistics"));
        setLayout(new GridLayout(0,1));
        minBut = new JRadioButton(MIN_BUTTON);
        avgBut = new JRadioButton(AVG_BUTTON);
        maxBut = new JRadioButton(MAX_BUTTON);
   
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(minBut);
        buttonGroup.add(avgBut);
        buttonGroup.add(maxBut);
        
        add(minBut);
        add(avgBut);
        add(maxBut);
        
        setBackground(new Color(255, 255, 255));
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
