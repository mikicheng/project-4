import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Statistics extends Observation
{

	protected String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
	protected DateTimeFormatter format;
	
	/**
     * utc time zone for date and time
     */
	private GregorianCalendar utcDateTime;
	private ZonedDateTime zdtDateTime;
	/**
     * Number of Reporting Stations
     */
	private int numberOfReportingStations;
	/**
     * Stat type
     */
	private StatsType statType;
	
	public Statistics (double value, String stid, GregorianCalendar dateTime, int numberOfValidStations, StatsType inStatType)
	{
		super(value,stid);
		this.numberOfReportingStations = numberOfValidStations;
		this.statType = inStatType;
		this.utcDateTime = dateTime;
		
	
	}
	public Statistics (double value, String stid, ZonedDateTime dateTime, int numberOfValidStations, StatsType inStatType)
	{
		super(value, stid);
		this.numberOfReportingStations = numberOfValidStations;
		this.statType = inStatType;
		this.zdtDateTime = dateTime;
	}
	
	/**
	 * TODO: createDateFromString method
     * Return date from string
     * @param dateTimeStr
     */
	public void createDateFromString(String dateTimeStr) 
	{
		GregorianCalendar output = new GregorianCalendar();
	
		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		try 
		{
			output.setTime(format.parse(dateTimeStr));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * TODO: createZDateFromString method
     * Return date from string
     * @param dateTimeStr
     * @return ZonedDateTime date from string
     */
	public ZonedDateTime createZDateFromString(String dateTimeStr) 
	{
		ZonedDateTime time = ZonedDateTime.parse(dateTimeStr, format);
		return time;
	}
	
	/**
	 * TODO: createStringFromDate method
     * Return string from date
     * @param calendar from GregorianCalendar
     * @return String date from GregorianCalendar
     */
	public String createStringFromDate(GregorianCalendar calendar) 
	{
		String out = calendar.getTime().toString();
		return out;
	}
	
	/**
	 * TODO: createStringFromDate method
     * Return string from date
     * @param calendar from ZonedDateTime
     * @return String date from ZonedDateTime
     */
	public String createStringFromDate(ZonedDateTime calendar) 
	{
		String outZ = calendar.format(format);
		return outZ;
	}
	
	/**
	 * TODO: getNumberOfReportingStations method
     * Return number of reporting stations
     * @return int get number of reporting stations
     */
	public int getNumberOfReportingStations() 
	{
		return this.numberOfReportingStations;
	}
	
	/**
	 * TODO: getUTCDateTimeString method
     * Return UTC date and time
     * @return String UTC date and time
     */
	public String getUTCDateTimeString() 
	{
		return createStringFromDate(this.utcDateTime);
	}
	
	/**
	 * newerThan method
     * Return true if newer than utc date time, else false
     * @return boolean for if newer Than
     */
	public boolean newerThan(GregorianCalendar inDateTime) 
	{
		if (inDateTime.compareTo(utcDateTime) > 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * olderThan method
     * Return true if older than utc date time, else false
     * @return boolean for if older than
     */
	public boolean olderThan(GregorianCalendar inDateTime) 
	{
		if (inDateTime.compareTo(utcDateTime) < 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * sameAs method
     * Return true if same as utc date time, else false
     * @return boolean for if same as
     */
	public boolean sameAs(GregorianCalendar inDateTime) 
	{
		if (inDateTime.compareTo(utcDateTime) == 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * TODO: newerThan method
     * Return newerThan
     * @return boolean for if newer Than
     */
	public boolean newerThan(ZonedDateTime inDateTime) 
	{
		if (inDateTime.compareTo(zdtDateTime) > 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * TODO: olderThan method
     * Return olderThan
     * @return boolean for if older than
     */
	public boolean olderThan(ZonedDateTime inDateTime) 
	{
		if (inDateTime.compareTo(zdtDateTime) < 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * TODO: sameAs method
     * Return sameAs
     * @return boolean for if same as
     */
	public boolean sameAs(ZonedDateTime inDateTime) 
	{
		if (inDateTime.compareTo(zdtDateTime) == 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * toString method that outputs the specified format of information
	 */
	 @Override
	    public String toString()
	    {
	            return "";

	    }
}
