import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

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
        setLayout(new GridLayout(0,1));
        setBorder(BorderFactory.createTitledBorder("Parameter"));
        
        setBackground(new Color(64, 64, 64));
        airTemp = new JCheckBox("TAIR");
        ta9m = new JCheckBox("TA9M");
        srad = new JCheckBox("SRAD");
        wspd = new JCheckBox("WSPD");
        pres = new JCheckBox("PRES");
        
        add(airTemp);
        add(ta9m);
        add(srad);
        add(wspd);
        add(pres);
        
        setLayout(new GridLayout(5,1));
        
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
