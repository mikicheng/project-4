import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

/**
 * Testing Class for MapData Class
 * @author Miki Cheng
 * @version 2018-10-19
 */

public class MapDataTest
{

    /**
	 * Test createFileName method of MapData
     * @throws IOException 
     */
    @Test
    public void createFileNameTest() throws IOException
    {
    	// create MapData object with the info for the .mdf file of a certain date
        MapData actual = new MapData(2018, 8, 30, 17, 45, "data");
        Assert.assertEquals("data/201808301745.mdf", actual.createFileName(2018, 8, 30, 17, 45, "data"));
    }
    

    /**
	 * Test getIndexOf method of MapData
     * @throws IOException 
     */
    @Test
    public void getIndexOfTest() throws IOException
    {
    	// create MapData object with the info for the .mdf file of a certain date
        MapData actual = new MapData(2018, 8, 30, 17, 45, "data");
        actual.parseFile();
        int expected = 14;
        Assert.assertEquals(expected, actual.getIndexOf("TA9M"), 0);
        
    }
    /**
     * Test ParseFile method of MapData
     * @throws IOException 
     */
    @Test
    public void ParseFileTest() throws IOException
    {
        // Create mapData object and test each type of data Type
    	MapData actual = new MapData(2018, 8, 30, 17, 45, "data");
        actual.parseFile();
        
        // SRAD testing
        Assert.assertEquals(968.0, actual.getStatistics(StatsType.MAXIMUM, "SRAD").getValue(), 0.1);
        Assert.assertEquals(163.0, actual.getStatistics(StatsType.MINIMUM, "SRAD").getValue(), 0.1);
        Assert.assertEquals(828.1, actual.getStatistics(StatsType.AVERAGE, "SRAD").getValue(), 0.1);
        Assert.assertEquals(97720.0, actual.getStatistics(StatsType.TOTAL, "SRAD").getValue(), 0.1);
         
        // TAIR testing
        Assert.assertEquals(36.5, actual.getStatistics(StatsType.MAXIMUM, "TAIR").getValue(), 0.1);
        Assert.assertEquals(20.8, actual.getStatistics(StatsType.MINIMUM, "TAIR").getValue(), 0.1);
        Assert.assertEquals(32.4, actual.getStatistics(StatsType.AVERAGE, "TAIR").getValue(), 0.1);
        Assert.assertEquals(3761.3, actual.getStatistics(StatsType.TOTAL, "TAIR").getValue(), 0.1);
        
        // TA9M Testing
        Assert.assertEquals(34.9, actual.getStatistics(StatsType.MAXIMUM, "TA9M").getValue(), 0.1);
        Assert.assertEquals(20.7, actual.getStatistics(StatsType.MINIMUM, "TA9M").getValue(), 0.1);
        Assert.assertEquals(31.6, actual.getStatistics(StatsType.AVERAGE, "TA9M").getValue(), 0.1);
        Assert.assertEquals(3723.7, actual.getStatistics(StatsType.TOTAL, "TA9M").getValue(), 0.1);
 
    }
    /**
     * Test toString method of MapData
     * @throws IOException
     */
    @Test
    public void testToString() throws IOException
    {
    	// create MapData object to test toString
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        test.parseFile();
        
        // dates!
        int year = 2018;
        int month = 8;
        int day = 30;
        int hour = 17;
        int minute = 45;
        String expected = String.format(
				"========================================================\n" 
						+ "=== %d-%02d-%d %d:%d ===\n"
						+ "========================================================\n"
						+ "Maximum Air Temperature[1.5m] = 36.5 C at HOOK\r\n"
						+ "Minimum Air Temperature[1.5m] = 20.8 C at MIAM\r\n"
						+ "Average Air Temperature[1.5m] = 32.4 C at Mesonet\r\n"
						+ "========================================================\r\n"
						+ "========================================================\r\n"
						+ "Maximum Air Temperature[9.0m] = 34.9 C at HOOK\r\n"
						+ "Minimum Air Temperature[9.0m] = 20.7 C at MIAM\r\n"
						+ "Average Air Temperature[9.0m] = 31.6 C at Mesonet\r\n"
						+ "========================================================\r\n"
						+ "========================================================\r\n"
						+ "Maximum Solar Radiation[1.5m] = 968.0 W/m^2 at SLAP\r\n"
						+ "Minimum Solar Radiation[1.5m] = 163.0 W/m^2 at MIAM\r\n"
						+ "Average Solar Radiation[1.5m] = 828.1 W/m^2 at Mesonet\r\n"
						+ "========================================================\n",
						year, month, day, hour, minute);
        
        String actual = test.toString();
        Assert.assertEquals(expected, actual);
    }

}