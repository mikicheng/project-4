import org.junit.Assert;
import org.junit.Test;

/**
 * Testing Class for Observation Class
 * @author Miki
 */
public class ObservationTest 
{
	/**
	 * Test getValue method of Observation
	 */
	@Test
	public void getValueTest()
	{
		Observation actual = new Observation(123.4, "COOL");
		
		Assert.assertEquals(123.4, actual.getValue(), 0.0001);
	}
	
	/**
	 * Test isValid method of Observation
	 */
	@Test
	public void isValidTest()
	{
		Observation actual = new Observation(-999, "COOL");
		
		Assert.assertFalse(actual.isValid());
		
		Observation actual2 = new Observation(1234.0, "COOL");
		Assert.assertTrue(actual2.isValid());
	}

	/**
	 * Test getStid method of Observation
	 */
	@Test
	public void getStidTest()
	{
		Observation actual = new Observation(-999, "COOL");
		
		Assert.assertEquals("COOL", actual.getStid());
	}

	/**
	 * Test toString method of Observation
	 */
	@Test
	public void toStringTest()
	{
		Observation actual = new Observation(-999, "COOL");	
		Assert.assertEquals("Station: COOL | Value: -999.0", actual.toString());
	}
}
