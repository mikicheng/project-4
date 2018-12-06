/**
 * Abstract class that represents Observation
 * 
 * @author Miki Cheng
 * @version 2018-10-04
 */
public abstract class AbstractObservation {
	
	protected boolean valid;
	
	public AbstractObservation()
	{
		valid = true;
	}
	
	public boolean isValid()
	{
		 return valid;
	}
}
