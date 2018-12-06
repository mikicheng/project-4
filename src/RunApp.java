
import java.io.IOException;

import javax.swing.SwingUtilities;

public class RunApp 
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						new MesonetFrame();
						
					}
				});
	}

}
