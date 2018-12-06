import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TablePanel extends JPanel
{

	private JTextArea report;
	
	public TablePanel()
	{
		   final int COLUMN_FIELD_WIDTH = 40;
	        BorderLayout layoutBorder = new BorderLayout();
	        this.setLayout(layoutBorder);
	        setBorder(BorderFactory.createTitledBorder(""));
	        GridBagConstraints layoutMargins = new GridBagConstraints();
	        
	        report = new JTextArea();
	        report.setColumns(COLUMN_FIELD_WIDTH);
	        
	        layoutMargins.gridx = 1;
	        layoutMargins.gridy = 1;
	        layoutMargins.weightx = 0.5;
	        layoutMargins.weighty = 1;
	        layoutMargins.insets = new Insets(10, 10, 10, 10);
	        layoutMargins.anchor = GridBagConstraints.CENTER;
	        layoutMargins.fill = GridBagConstraints.BOTH;
	        
	        add(report, layoutMargins);
	        this.setBackground(new Color(0, 128, 210));
	        
	}
	

    public synchronized void updateData(String result)
    {
        report.setText(result);
    }
}
