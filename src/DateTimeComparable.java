import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public interface DateTimeComparable 
{

	boolean newerThan (GregorianCalendar inDateTimeUTC) throws ParseException;
	 
	boolean olderThan (GregorianCalendar inDateTimeUTC) throws ParseException;
	
	boolean sameAs (GregorianCalendar inDateTimeUTC) throws ParseException;
	
	boolean newerThan (ZonedDateTime inDateTimeUTC) throws ParseException;

	boolean olderThan (ZonedDateTime inDateTimeUTC) throws ParseException;

	boolean sameAs (ZonedDateTime inDateTimeUTC) throws ParseException;

}
