/**
 * Class that represents the data for ta9m, tair and srad
 * Represents one individual value for ta9m, tair, or srad
 * 
 * @author Miki Cheng
 * @version 2018-09-19
 */
public class Observation extends AbstractObservation
{

	/**
     * double value for the data
     */
	private double value;
	/**
     * string stid for the data
     */
	private String stid;
	
	/**
	 * Constructor for Observations. initializes stid and value
     * @param value double value of data
     * @param stid String stid of data
     */
	public Observation(double value, String stid)
	{
		this.value = value;
		this.stid = stid;
	}
	
	/**
     * Return the value of data
     * @return double value of data
     */
	public double getValue()
	{
		return value;
	}
	/**
     * Return true if the value is less than or equal to -900
     * @return boolean valid 
     */
	public boolean isValid()
	{
		if (value <= -900) // check if less than or equal to -900
        {
            valid = false;
        }
        else
        {
            valid = true;
        }
        
        return valid;
	}
	/**
	 * Return the stid of data
	 * @return String stid of data
	 */
	public String getStid()
	{
		return stid;
	}
	/**
     * Return the toString of data for station and value
     * @return String toString of data
     */
	@Override
    public String toString()
    {
    	return "Station: " + stid + " | Value: " + value;
    }
}
