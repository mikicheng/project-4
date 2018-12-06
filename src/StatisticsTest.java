import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

/**
 * Testing Class for Statistics Class
 * @author Miki
 */

public class StatisticsTest
{

	/**
	 * Test createDateFromString method of Statistics
	 */
    @Test
    public void createDateFromStringTest() throws ParseException
    {
    	// Create a test date String
    	String date = "2018-10-10T10:45:00 CDT";
    	// Create a calendar tester to be used with a statistics object
        GregorianCalendar tester = new GregorianCalendar(2018, 9, 10, 17, 45);
        Statistics testStat = new Statistics(1, "OCT0", tester, 11, StatsType.MAXIMUM);
        // create the calendar that is to be used
        GregorianCalendar actual = testStat.createDateFromString(date);

        // create the expected Calendar
        GregorianCalendar expected = new GregorianCalendar(2018, 9, 10, 10, 45);
        
        Assert.assertEquals(expected, actual);

    }

    /**
     * Test createZDateFromString method of Statistics
     */
    @Test
    public void createZDateFromStringTest()
    {
        // create ZoneId to be tested
        ZoneId tester = ZoneId.of("America/Chicago");
        // Create a test date String
    	String date = "2018-10-10T10:45:00 CDT";
    	
        // Created a test ZonedDateTime object
        ZonedDateTime test = ZonedDateTime.of(2018, 10, 10, 10, 45, 0, 0, tester);
        
        // Created a Statistics object that is used to test
        Statistics testStat = new Statistics(10, "OCTO", test, 10, StatsType.MAXIMUM);
        // create the actual result
        ZonedDateTime actual = testStat.createZDateFromString(date);
        // create expected Result
        ZonedDateTime expected = ZonedDateTime.of(2018, 10, 10, 10, 45, 0, 0, tester);
        
        Assert.assertEquals(expected, actual);

    }

    /**
     * Test createStringFromDate method (Gregorian) of Statistics
     */
    @Test
    public void createStringFromDateTestG()
    {
    	// Create a test Gregorian Calendar
        GregorianCalendar test = new GregorianCalendar(2018, 10, 10, 10, 10);
        // Create a test Statistics object to be used along with the test Calendar
        Statistics testStat = new Statistics(10, "OCTO", test, 10, StatsType.MAXIMUM);

        // create the actual String
        String actual = testStat.getUTCDateTimeString();
        String expected = "2018-11-10T10:10:00 CST";

        Assert.assertEquals(expected, actual);

    }

    /**
     * Test createStringFromDate method (ZonedDateTime) of Statistics
     */
    @Test
    public void createStringFromDateTestZ()
    { 
    	// create test Zone
    	ZoneId tester = ZoneId.of("America/Chicago");
    	// c reate test Zoned object
        ZonedDateTime test = ZonedDateTime.of(2018, 10, 10, 10, 10, 0, 0, tester);
        Statistics testStat = new Statistics(10, "OCTO", test, 10, StatsType.MAXIMUM);

        // create the expected String
        String expected = "2018-10-10T10:10:00 CDT";

        // create the actual String
        String actual = testStat.createStringFromDate(test);
       
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test getNumberOfReportingStations method of Statistics 
     */
    @Test
	public void getNumberOfReportingStationsTest() 
	{
    	// create Gregorian objects to compare
        GregorianCalendar tester1 = new GregorianCalendar(2020, 10, 10, 10, 10);
        // Create statistics object to compare
        Statistics testStat = new Statistics(10, "OCTO", tester1, 10, StatsType.MAXIMUM);
        
        Assert.assertEquals(10, testStat.getNumberOfReportingStations());
	}
	
    /**
     * Test newerThan method (Gregorian) of Statistics 
     */
    @Test
    public void newerThanTestG()
    {
        // create Gregorian objects to compare
        GregorianCalendar tester1 = new GregorianCalendar(2020, 10, 10, 10, 10);
        GregorianCalendar tester2 = new GregorianCalendar(2015, 10, 10, 10, 10);

        // Create statistics object to compare
        Statistics testStat = new Statistics(10, "OCTO", tester1, 10, StatsType.MAXIMUM);
        Statistics testStat2 = new Statistics(10, "OCTO", tester2, 10, StatsType.MAXIMUM);
        
        Assert.assertTrue(testStat.newerThan(tester2));
        Assert.assertFalse(testStat2.newerThan(tester1));
    }

    /**
     * Test olderThan method (Gregorian) of Statistics 
     */
    @Test
    public void olderThanTestG()
    {
    	 // create Gregorian objects to compare
        GregorianCalendar tester1 = new GregorianCalendar(2020, 10, 10, 10, 10);
        GregorianCalendar tester2 = new GregorianCalendar(2015, 10, 10, 10, 10);

        // Create statistics object to compare
        Statistics testStat = new Statistics(10, "OCTO", tester2, 10, StatsType.MAXIMUM);
        Statistics testStat2 = new Statistics(10, "OCTO", tester1, 10, StatsType.MAXIMUM);
        
        Assert.assertTrue(testStat.olderThan(tester1));
        Assert.assertFalse(testStat2.olderThan(tester2));

    }

    /**
     * Test sameAs method (Gregorian) of Statistics 
     */
    @Test
    public void sameAsTestG()
    {
    	 // create Gregorian objects to compare
        GregorianCalendar tester1 = new GregorianCalendar(2015, 10, 10, 10, 10);
        GregorianCalendar tester2 = new GregorianCalendar(2015, 10, 10, 10, 10);
        GregorianCalendar tester3 = new GregorianCalendar(2016, 10, 10, 10, 10);

        // Create statistics object to compare
        Statistics testStat = new Statistics(10, "OCTO", tester2, 10, StatsType.MAXIMUM);
     
        Assert.assertTrue(testStat.sameAs(tester2));
        Assert.assertFalse(testStat.sameAs(tester3));

    }

    /**
     * Test newerThan method (ZoneDateTime) of Statistics 
     */
    @Test
    public void newerThanTestZ()
    {

        // Create test Zone
        ZoneId tester = ZoneId.of("America/Chicago");
        // Create two ZDT test objects to compare
        ZonedDateTime test1 = ZonedDateTime.of(2018, 10, 10, 10, 45, 0, 0, tester);
        ZonedDateTime test2 = ZonedDateTime.of(2020, 10, 10, 10, 45, 0, 0, tester);
        Statistics testStat = new Statistics(10, "OCTO", test2, 10, StatsType.MAXIMUM);
        Statistics testStat2 = new Statistics(10, "OCTO", test1, 10, StatsType.MAXIMUM);


        Assert.assertTrue(testStat.newerThan(test1));
        Assert.assertFalse(testStat2.newerThan(test2));

    }

    /**
     * Test olderThan method (ZoneDateTime) of Statistics
     */
    @Test
    public void olderThanTestZ() throws ParseException
    {
    	 // Create test Zone
        ZoneId tester = ZoneId.of("America/Chicago");
        // Create two ZDT test objects to compare
        ZonedDateTime test1 = ZonedDateTime.of(2018, 10, 10, 10, 45, 0, 0, tester);
        ZonedDateTime test2 = ZonedDateTime.of(2020, 10, 10, 10, 45, 0, 0, tester);
        Statistics testStat = new Statistics(10, "OCTO", test1, 10, StatsType.MAXIMUM);
        Statistics testStat2 = new Statistics(10, "OCTO", test2, 10, StatsType.MAXIMUM);


        Assert.assertTrue(testStat.olderThan(test2));
        Assert.assertFalse(testStat2.olderThan(test1));
    }

    /**
     * Test sameAs method (ZoneDateTime) of Statistics
     */
    @Test
    public void sameAsTestZ() throws ParseException
    {
    	 // Create test Zone
        ZoneId tester = ZoneId.of("America/Chicago");
        // Create two ZDT test objects to compare
        ZonedDateTime test1 = ZonedDateTime.of(2020, 10, 10, 10, 45, 0, 0, tester);
        ZonedDateTime test2 = ZonedDateTime.of(2020, 10, 10, 10, 45, 0, 0, tester);
        ZonedDateTime test3 = ZonedDateTime.of(2010, 10, 10, 10, 45, 0, 0, tester);
        Statistics testStat = new Statistics(10, "OCTO", test1, 10, StatsType.MAXIMUM);

        Assert.assertTrue(testStat.sameAs(test2));
        Assert.assertFalse(testStat.sameAs(test3)); 
    }

    /**
     * Test toString method of Statistics
     */
    @Test
    public void toStringTest()
    {
        // Create a test Gregorian object
        GregorianCalendar test = new GregorianCalendar(2018, 10, 10, 10, 10);

        Statistics testStat = new Statistics(10, "OCTO", test, 10, StatsType.MAXIMUM);;
        
        // Expected String and actual using the method
        Assert.assertEquals("Station: OCTO | Value: 10.0", testStat.toString());

    }
}