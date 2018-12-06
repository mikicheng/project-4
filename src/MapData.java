import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

public class MapData {
	
	private static HashMap<String, ArrayList<Observation>> dataCatalog;
	private EnumMap<StatsType, TreeMap<String,Statistics>> statistics;
	private static TreeMap<String, Integer> paramPositions;

	
	/**
     * Number of missing observations
     */
	private static final int NUMBER_OF_MISSING_OBSERVATIONS = 10;
	/**
     * Number of Stations
     */
	private static Integer numberOfStations = null;
	/**
     * Air temperature at 9m
     */
	private static String TA9M = "TA9M";
	/**
     * Air temperature
     */
	private static String TAIR = "TAIR";
	/**
     * Solar radiation
     */
	private static String SRAD = "SRAD";
	/**
     * stid of data
     */
	private static String STID = "STID";
	/**
     * String used to describe whole network
     */
	private String MESONET = "Mesonet";
	/**
     * File name
     */
	private static String fileName;
	/**
	 * utc time zone for date and time
	 */
	private GregorianCalendar utcDateTime;

	
	TreeMap<String, Statistics> maximum = new TreeMap<String, Statistics>(); // maximum stats
	   TreeMap<String, Statistics> minimum = new TreeMap<String, Statistics>(); // minimum stats
	   TreeMap<String, Statistics> average = new TreeMap<String, Statistics>(); // average stats
	   TreeMap<String, Statistics> total = new TreeMap<String, Statistics>(); // total stats

	/**
     * Constructor for MapData. Takes in information on the data.
     * Info of data by year, month, day, minute, directory.
     * @param year int year of data
     * @param month int month of data
     * @param day int day of data
     * @param hour int hour of data
     * @param minute int minute of data
     * @param directory String directory of data
     * 
     * @throws IOException error for data file
     */
	public MapData (int year, int month, int day, int hour, int minute, String directory) throws IOException
	{
		this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
		createFileName(year, month, day, hour, minute, directory);
		
	}
	
	/**
	 * createFileName method that creates the string that holds the file name
	 * @return String that holds file name
	 */
	public static String createFileName(int year, int month, int day, int hour, int minute, String directory)
	{
		fileName = String.format("%s/%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);
    	return fileName;
	}
	
	/**
	 * Method that parses the param String
	 * @param inParamStr string parameter parse from
	 */
	private static void parseParamHeader (String inParamStr)
	{
		ArrayList<String> paramID = new ArrayList<>();
		String[] params = (inParamStr.trim()).split("\\s+");
		paramID.addAll(Arrays.asList(params));
		
		paramPositions = new TreeMap<String, Integer>();
		
		paramPositions.put(TA9M, paramID.indexOf(TA9M));
		paramPositions.put(TAIR, paramID.indexOf(TA9M));
		paramPositions.put(SRAD, paramID.indexOf(TA9M));
		paramPositions.put(STID, paramID.indexOf(TA9M));
			
	}
	
	public static Integer getIndexOf (String inParamStr)
	{
		return paramPositions.get(inParamStr);
	}
	
	/**
	 * Method that reads in the file and fills in the Observation arrays with the data
	 * @throws IOException throws IOException
	 */
	public static void parseFile() throws IOException
	{			
			ArrayList<Observation> SRADinfo = new ArrayList<Observation>();
			ArrayList<Observation> TAIRinfo = new ArrayList<Observation>();
			ArrayList<Observation> TA9Minfo = new ArrayList<Observation>();
        
			BufferedReader br = new BufferedReader(new FileReader(fileName));
	        String line = br.readLine();  
	        line = br.readLine();
	        line = br.readLine(); 
	        parseParamHeader(line); // parse the parameter header
	        
	        line = br.readLine(); 
	        int index = 0;
	        while (line != null)
	        {
	            line = line.trim();
	            
	            //Split
	            String splitData[] = line.split("\\s+");
	            
	            SRADinfo.add(index, new Observation(Double.parseDouble(splitData[getIndexOf(SRAD)]), splitData[getIndexOf(SRAD)]));
	            TAIRinfo.add(index, new Observation(Double.parseDouble(splitData[getIndexOf(TAIR)]), splitData[getIndexOf(TAIR)]));
	            TA9Minfo.add(index, new Observation(Double.parseDouble(splitData[getIndexOf(TA9M)]), splitData[getIndexOf(TA9M)]));
	            
	            line = br.readLine();
	            numberOfStations++;
	            index++;
	            
	        }
	        br.close();
	        
	        dataCatalog = new HashMap<String, ArrayList<Observation>>();
	        dataCatalog.put("SRADData", SRADinfo);
	        dataCatalog.put("TAIRData", TAIRinfo);
	        dataCatalog.put("TA9MData", TA9Minfo);
	        
	        
    	}
	   
     
	
	private void calculateAllStatistics()
	{	   
		double max = Double.NEGATIVE_INFINITY;
		double min = Double.POSITIVE_INFINITY;
		double avg = 0;
		double tot = 0;
		int size = 0;
		   statistics = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
		   Set<String> IDparameters = dataCatalog.keySet();
		   
		   
		  // SRADdATA
		  for (int i = 0; i < dataCatalog.get("SRADData").size(); i++)
		  {
			  if (dataCatalog.get("SRADData").get(i).isValid())
			  {
				  if (dataCatalog.get("SRADData").get(i).getValue() > max)
				  {
					  max = dataCatalog.get("SRADData").get(i).getValue();
				  }
				  if (dataCatalog.get("SRADData").get(i).getValue() < min)
				  {
					  min = dataCatalog.get("SRADData").get(i).getValue();
				  }
				  
				  tot += dataCatalog.get("SRADData").get(i).getValue(); 
				  size++;
			  }
		  } 
		  
		  avg = tot / size;
		  
		  //TAIRdATA
		  for (int i = 0; i < dataCatalog.get("TAIRData").size(); i++)
		  {
			  if (dataCatalog.get("TAIRData").get(i).isValid())
			  {
				  if (dataCatalog.get("TAIRData").get(i).getValue() > max)
				  {
					  max = dataCatalog.get("TAIRData").get(i).getValue();
				  }
				  if (dataCatalog.get("TAIRData").get(i).getValue() < min)
				  {
					  min = dataCatalog.get("TAIRData").get(i).getValue();
				  }
				  
				  tot += dataCatalog.get("TAIRData").get(i).getValue(); 
				  size++;
			  }
		  } 
		  
		  avg = tot / size;
		  
		  //TA9MdATA
		  for (int i = 0; i < dataCatalog.get("TA9MData").size(); i++)
		  {
			  if (dataCatalog.get("TA9MData").get(i).isValid())
			  {
				  if (dataCatalog.get("TA9MData").get(i).getValue() > max)
				  {
					  max = dataCatalog.get("TA9MData").get(i).getValue();
				  }
				  if (dataCatalog.get("TA9MData").get(i).getValue() < min)
				  {
					  min = dataCatalog.get("TA9MData").get(i).getValue();
				  }
				  
				  tot += dataCatalog.get("TA9MData").get(i).getValue(); 
				  size++;
			  }
		  } 
		  
		  avg = tot / size;
	}
	
	
	
	private void prepareDataCatalog()
	{
		dataCatalog = new HashMap<String, ArrayList<Observation>>();
//		dataCatalog = new HashMap<String, ArrayList<Observation>>();
//		statistics = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
//		paramPositions = new TreeMap<String, Integer>();
//		
	}
	
	private void calculateStatistics()
	{
		calculateAllStatistics();
	}
	
	public Statistics getStatistics(StatsType type, String paramId)
	{
		return Statistics.get(type).get(paramId);
	}
	
	public String toString()
	{
		String sYear;
		String sMonth;
		String sDay;
		String sHour;
		String sMinute; 
		
		return String.format
				("========================================================\n"
				+ "=== %d-%s-%s %s:%s ===\n"
				+ "========================================================\n"
				+ "Maximum Air Temperature[1.5m] = %.1f C at %s\r\n" + 
				"Minimum Air Temperature[1.5m] = %.1f C at %s\r\n" + 
				"Average Air Temperature[1.5m] = %.1f C at %s\r\n" + 
				"========================================================\r\n" + 
				"========================================================\r\n" + 
				"Maximum Air Temperature[9.0m] = %.1f C at %s\r\n" + 
				"Minimum Air Temperature[9.0m] = %.1f C at %s\r\n" + 
				"Average Air Temperature[9.0m] = %.1f C at %s\r\n" + 
				"========================================================\r\n" + 
				"========================================================\r\n" + 
				"Maximum Solar Radiation[1.5m] = %.1f W/m^2 at %s\r\n" + 
				"Minimum Solar Radiation[1.5m] = %.1f W/m^2 at %s\r\n" + 
				"Average Solar Radiation[1.5m] = %.1f W/m^2 at %s\r\n" + 
				"========================================================\n"
				, sYear, sMonth, sDay, sHour, sMinute,
				TAIR.getValue());
	}
}

