import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * Class that extends the Observation class in order to provide
 * information based on dates
 * @author Miki Cheng
 * @version 2018-10-29
 */
public class Statistics extends Observation
{

	/**
	 * Required Date_Time format
	 */
	protected String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
	
	/**
	 * Variable used to handle the format of the date
	 */
	protected DateTimeFormatter format;
	
	/**
     * UTC time zone for date and time
     */
	private GregorianCalendar utcDateTime;
	
	/**
     * ZDT time zone for date and time
     */
	private ZonedDateTime zdtDateTime;
	
	/**
     * Number of Reporting Stations
     */
	private int numberOfReportingStations;
	
	/**
     * Stat type
     */
	private StatsType statType;
	
	/**
	 * Constructor for Statistics using dateTime
     * @param value Double value of the observation
     * @param stid String stid of the observation
     * @param dateTime The corresponding dateTime
     * @param numberOfValidStations the current number of valid stations
     * @param inStatType the stat type of the data
     */
	public Statistics (double value, String stid, GregorianCalendar dateTime, int numberOfValidStations, StatsType inStatType)
	{
		// call the super constructor of observation 
		super(value,stid);
		
		// initialize the values to the corresponding variables
		this.numberOfReportingStations = numberOfValidStations;
		this.statType = inStatType;
		this.utcDateTime = dateTime;
		
	
	}
	
	/**
	 * Constructor for Statistics using ZonedDateTime
     * @param value Double value of the observation
     * @param stid String stid of the observation
     * @param dateTime The corresponding dateTime
     * @param numberOfValidStations the current number of valid stations
     * @param inStatType the stat type of the data
     */
	public Statistics (double value, String stid, ZonedDateTime dateTime, int numberOfValidStations, StatsType inStatType)
	{
		// call the super constructor of observation 
		super(value, stid);
		
		// initialize the values to the corresponding variables
		this.numberOfReportingStations = numberOfValidStations;
		this.statType = inStatType;
		this.zdtDateTime = dateTime;
	}
	
	/**
	 * createDateFromString method that returns a GregorianCalendar object based on the date
     * @param dateTimeStr String that represents the date_Time
     * @return GregorianCalendar object that represents the date_Time
     * @throws ParseException throw
     */
	public GregorianCalendar createDateFromString(String dateTimeStr) throws ParseException
	{
		// create the date with the required format 
        SimpleDateFormat date = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        // parses the string to date
        Date newDate = date.parse(dateTimeStr);

        // Create a GregorianCalendar object and set the newDate
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(newDate);
 
        return calendar; 
	}
	
	/**
	 * createZDateFromString method that returns a ZonedDateTime object based on the date
     * @param dateTimeStr String that represents the date_Time
     * @return ZonedDateTime object that represents the date_Time
     */
	public ZonedDateTime createZDateFromString(String dateTimeStr) 
	{
		// set the format and create a zonedatetime object
		format = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
		ZonedDateTime zdt = ZonedDateTime.parse(dateTimeStr, format);
		
		return zdt;
	}
	
	/**
	 * createStringFromDate method that returns a String that represents the date based on calendar
     * @param calendar GregorianCalendar object that represents a date
     * @return String that represents the date
     */
	public String createStringFromDate(GregorianCalendar calendar) 
	{
		 SimpleDateFormat stringFromData = new SimpleDateFormat(DATE_TIME_FORMAT);
	     
		 // create String to store the date in format
	     String date = stringFromData.format(calendar.getTime());

	     return date;

	}
	
	/**
	 * createStringFromDate method that returns a String that represents the date based on ZonedDateTime
     * @param calendar ZonedDateTime object that represents a date
     * @return String that represents the date
     */
	public String createStringFromDate(ZonedDateTime calendar) 
	{
		 format  = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
		 
		 // create String to store the date in format
	     String date = format.format(calendar); 
	     
	     return date;
	}
	
	/**
	 * Getter that returns the number of reporting stations
     * @return int get number of reporting stations
     */
	public int getNumberOfReportingStations() 
	{
		return this.numberOfReportingStations;
	}
	
	/**
	 * Getter that returns UTCDate in a String 
     * @return String representing the UTC Date and Time
     */
	public String getUTCDateTimeString() 
	{
		return createStringFromDate(this.utcDateTime);
	}
	
	/**
	 * newerThan method
     * Return true if newer than utc date time, else false
     * @param inDateTime gregorianCalendar to compare
     * @return boolean for if newer Than
     */
	public boolean newerThan(GregorianCalendar inDateTime) 
	{
		if (utcDateTime.compareTo(inDateTime) > 0)
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
     * @param inDateTime gregorianCalendar to compare
     * @return boolean for if older than
     */
	public boolean olderThan(GregorianCalendar inDateTime) 
	{
		if (utcDateTime.compareTo(inDateTime) < 0)
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
     * @param inDateTime gregorianCalendar to compare
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
	 * newerThan method for ZonedDateTime
     * Return true if newer than zdt date time, else false
     * @param inDateTime ZonedDateTime to compare
     * @return boolean for if same as
     */
	public boolean newerThan(ZonedDateTime inDateTime) 
	{
		if (zdtDateTime.compareTo(inDateTime) > 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * olderThan method for ZonedDateTime
     * Return true if older than zdt date time, else false
     * @param inDateTime ZonedDateTime to compare
     * @return boolean for if same as
     */
	public boolean olderThan(ZonedDateTime inDateTime) 
	{
		if (zdtDateTime.compareTo(inDateTime) < 0)
		{
		    return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * sameAs method for ZonedDateTime
     * Return true if same as zdt date time, else false
     * @param inDateTime ZonedDateTime to compare
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
	 * toString method that outputs the information
	 * @return String holding the informatin about the Observation data
	 */
	 @Override
	 public String toString()
	 {
		 return "Station: " + getStid() + " | Value: " + getValue();
	 }
}
