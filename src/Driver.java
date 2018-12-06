import java.io.IOException;

public class Driver
{
  
    public static void main(String[] args) throws IOException 
    {
	// Preset file name
	final int YEAR = 2018;
	final int MONTH = 8;
	final int DAY = 30;
	final int HOUR = 17;
	final int MINUTE = 45;
	final String DIRECTORY = "data";
	
	MapData mapdata = new MapData(YEAR, MONTH, DAY, HOUR, MINUTE, DIRECTORY);
	MapData.parseFile();
	
	System.out.println(mapdata);
    }
}
