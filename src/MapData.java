import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * Class that represents the full data for an input file
 * Stores the map data values for ta9m, tair, or srad
 * 
 * @author Miki Cheng
 * @version 2018-10-19
 */
public class MapData 
{

	private HashMap<String, ArrayList<Observation>> dataCatalog;
	private EnumMap<StatsType, TreeMap<String, Statistics>> statistics;
	private TreeMap<String, Integer> paramPositions;

	/**
	 * Number of missing observations
	 */
	private final int NUMBER_OF_MISSING_OBSERVATIONS = 10;
	/**
	 * Number of Stations
	 */
	private Integer numberOfStations = null;
	/**
	 * Air temperature at 9m
	 */
	private String TA9M = "TA9M";
	/**
	 * Air temperature
	 */
	private String TAIR = "TAIR";
	/**
	 * Solar radiation
	 */
	private String SRAD = "SRAD";
	/**
	 * stid of data
	 */
	private String STID = "STID";
	/**
	 * String used to describe whole network
	 */
	private String MESONET = "Mesonet";
	/**
	 * File name
	 */
	private String fileName;
	/**
	 * utc time zone for date and time
	 */
	private GregorianCalendar utcDateTime;

	/**
	 * Constructor for MapData. Takes in information on the data. Info of data by
	 * year, month, day, minute, directory.
	 * 
	 * @param year
	 *            int year of data
	 * @param month
	 *            int month of data
	 * @param day
	 *            int day of data
	 * @param hour
	 *            int hour of data
	 * @param minute
	 *            int minute of data
	 * @param directory
	 *            String directory of data
	 * 
	 * @throws IOException
	 *             error for data file
	 */
	public MapData(int year, int month, int day, int hour, int minute, String directory) throws IOException 
	{
		this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
		createFileName(year, month, day, hour, minute, directory);
	}
	
	public MapData(String fileName) throws IOException 
	{
		String[] split = fileName.split("");
		int fileYear = Integer.parseInt(split[0] + split[1] + split[2] + split[3]);
		int fileMonth = Integer.parseInt(split[4] + split[5]);
		int fileDay = Integer.parseInt(split[6] + split[7]);
		int fileHour = Integer.parseInt(split[8] + split[9]);
		int fileMinute = Integer.parseInt(split[10] + split[11]);
		this.utcDateTime = new GregorianCalendar(fileYear, fileMonth, fileDay, fileHour, fileMinute);
		this.fileName = "data/" + fileName;
		System.out.println(this.fileName);
	}

	/**
	 * createFileName method that creates the string that holds the file name
	 * 
	 * @return String that holds file name
	 */
	public String createFileName(int year, int month, int day, int hour, int minute, String directory) 
	{
		fileName = String.format("%s/%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);
		return fileName;
	}

	/**
	 * Method that parses the param String
	 * 
	 * @param inParamStr string parameter to parse From
	 */
	private void parseParamHeader(String inParamStr)
	{
		// create an arrayList to store the paramID
		ArrayList<String> paramID = new ArrayList<>();
		String[] params = (inParamStr.trim()).split("\\s+");
		// add to the arrayList
		paramID.addAll(Arrays.asList(params));

		// intialize the paramPositions TreeMap
		paramPositions = new TreeMap<String, Integer>();

		// put the index of each required column into the tree
		paramPositions.put(TA9M, paramID.indexOf(TA9M));
		paramPositions.put(TAIR, paramID.indexOf(TAIR));
		paramPositions.put(SRAD, paramID.indexOf(SRAD));
		paramPositions.put(STID, paramID.indexOf(STID));
		
	}

	/**
	 * Method that returns the index of a specified column name
	 * 
	 * @param inParamStr name of the Column
	 * @return integer that represents the position of the name
	 */
	public Integer getIndexOf(String inParamStr) 
	{
		return paramPositions.get(inParamStr);
	}

	/**
	 * Method that reads in the file and fills in the Observation arrays with the
	 * data
	 * 
	 * @throws IOException throw IO Exception
	 */
	public void parseFile() throws IOException 
	{
		// Create Observation ArrayLists for each type of data
		ArrayList<Observation> sradInfo = new ArrayList<Observation>();
		ArrayList<Observation> tairInfo = new ArrayList<Observation>();
		ArrayList<Observation> ta9mInfo = new ArrayList<Observation>();

		// set equal to 0 in order to increment
		numberOfStations = 0;
		
		// create bufferedReader object to read the file
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine(); // get rid beginning headers
		line = br.readLine();
		line = br.readLine();
		parseParamHeader(line); // parse the parameter header

		line = br.readLine(); // read the first line of Data
		while (line != null)
		{
			// split the line by white spaces
			String splitData[] = line.trim().split("\\s+"); 
			// add the data from each line into corresponding ArrayLists
			sradInfo.add(new Observation(Double.parseDouble(splitData[getIndexOf(SRAD)]), splitData[getIndexOf(STID)]));
			tairInfo.add(new Observation(Double.parseDouble(splitData[getIndexOf(TAIR)]), splitData[getIndexOf(STID)]));
			ta9mInfo.add(new Observation(Double.parseDouble(splitData[getIndexOf(TA9M)]), splitData[getIndexOf(STID)]));
			// read the next line
			line = br.readLine();
			// increase number of Stations!
			numberOfStations++;
		}
		// close buffer Reader
		br.close();

		// prepare the catalog
		prepareDataCatalog();
		
		// fill the dataCatalog HashMap with corresponding data type name and ArrayList of data
		dataCatalog.put(SRAD, sradInfo);
		dataCatalog.put(TAIR, tairInfo);
		dataCatalog.put(TA9M, ta9mInfo);
		
		// calculate the statistics of the data
		calculateStatistics();
	}

	/**
	 * Method that calculates the Statistics of the data
	 */
	private void calculateAllStatistics()
	{
		// Create Treemaps for max, min, avg, and total to store into the EnumMap		
		TreeMap<String, Statistics> maximum = new TreeMap<String, Statistics>(); // maximum stats
		TreeMap<String, Statistics> minimum = new TreeMap<String, Statistics>(); // minimum stats
		TreeMap<String, Statistics> average = new TreeMap<String, Statistics>(); // average stats
		TreeMap<String, Statistics> total = new TreeMap<String, Statistics>(); // total stats

		// Create Observation Arraylist in order to retrieve statistics needed
		ArrayList<Observation> sradInfo = new ArrayList<Observation>();
		ArrayList<Observation> tairInfo = new ArrayList<Observation>();
		ArrayList<Observation> ta9mInfo = new ArrayList<Observation>();

		// assign each dataType Arraylist the values from the dataCatalog Tree Map
		sradInfo = dataCatalog.get(SRAD);
		tairInfo = dataCatalog.get(TAIR);
		ta9mInfo = dataCatalog.get(TA9M);

		 // used to check if less than number_of_missing_observations
		int validity = 0;
		
		// set the initial values for these variables to help calculate statistics
		double max = Double.NEGATIVE_INFINITY;
		double min = Double.POSITIVE_INFINITY;
		double avg = 0;
		double tot = 0;
		int size = 0;
		int maxIndex = 0;
		int minIndex = 0;
		

		// Loop through sradInfo and checks if valids, then finds max, min, and average values
		for (int i = 0; i < numberOfStations; i++)
		{
			if (sradInfo.get(i).isValid()) 
			{
				if (sradInfo.get(i).getValue() > max)
				{
					max = sradInfo.get(i).getValue();
					maxIndex = i;
				}
				if (sradInfo.get(i).getValue() < min)
				{
					min = sradInfo.get(i).getValue();
					minIndex = i;
				}

				tot += sradInfo.get(i).getValue();
				size++;
			}
			else 
			{
				validity++;
			}
		}
		// calculate average for SRAD information
		avg = tot / size;

		// if the validity is less than 10, store the data in each tree
		if (validity < NUMBER_OF_MISSING_OBSERVATIONS)
		{
			maximum.put(SRAD, new Statistics(max, sradInfo.get(maxIndex).getStid(), utcDateTime,
					(numberOfStations - validity), StatsType.MAXIMUM));
			minimum.put(SRAD, new Statistics(min, sradInfo.get(minIndex).getStid(), utcDateTime,
					(numberOfStations - validity), StatsType.MINIMUM));
			average.put(SRAD,
					new Statistics(avg, MESONET, utcDateTime, (numberOfStations - validity), StatsType.AVERAGE));

			total.put(SRAD, new Statistics(tot, MESONET, utcDateTime, (numberOfStations - validity), StatsType.TOTAL));
		}
		// if not, then put NULL values in
		else
		{
			 maximum.put(SRAD, new Statistics(-900, "NULL", utcDateTime, (numberOfStations - validity), StatsType.MAXIMUM));
		     minimum.put(SRAD, new Statistics(-900, "NULL", utcDateTime, (numberOfStations - validity), StatsType.MINIMUM));
		     average.put(SRAD, new Statistics(0.0, "NULL", utcDateTime, (numberOfStations - validity), StatsType.AVERAGE));
		     total.put(SRAD, new Statistics(0.0, "NULL", utcDateTime, (numberOfStations - validity), StatsType.TOTAL));
		}
       
		// Reinitialize values to use for calculations!
		validity = 0;
		max = Double.NEGATIVE_INFINITY;
		min = Double.POSITIVE_INFINITY;
		avg = 0;
		tot = 0;
		size = 0;
		maxIndex = 0;
		minIndex = 0;
		
		// Loop through tairInfo and checks if valids, then finds max, min, and average values
		for (int i = 0; i < numberOfStations; i++
				) {
			if (tairInfo.get(i).isValid()) 
			{
				if (tairInfo.get(i).getValue() > max) 
				{
					max = tairInfo.get(i).getValue();
					maxIndex = i;
				}
				if (tairInfo.get(i).getValue() < min)
				{
					min = tairInfo.get(i).getValue();
					minIndex = i;
				}

				tot += tairInfo.get(i).getValue();
				size++;
			} 
			else 
			{
				validity++;
			}

		}
		// calculates average for tair information
		avg = tot / size;
		
		// if the validity is less than 10, store the data in each tree
		if (validity < NUMBER_OF_MISSING_OBSERVATIONS)
		{

			// Treemaps stores the information
			maximum.put(TAIR, new Statistics(max, tairInfo.get(maxIndex).getStid(), utcDateTime,
					(numberOfStations - validity), StatsType.MAXIMUM));
			minimum.put(TAIR, new Statistics(min, tairInfo.get(minIndex).getStid(), utcDateTime,
					(numberOfStations - validity), StatsType.MINIMUM));
			average.put(TAIR,
					new Statistics(avg, MESONET, utcDateTime, (numberOfStations - validity), StatsType.AVERAGE));

			total.put(TAIR, new Statistics(tot, MESONET, utcDateTime, (numberOfStations - validity), StatsType.TOTAL));
		}
		// if not, then put NULL values in
		else
		{
			 maximum.put(TAIR, new Statistics(-900, "NULL", utcDateTime, (numberOfStations - validity), StatsType.MAXIMUM));
		     minimum.put(TAIR, new Statistics(-900, "NULL", utcDateTime, (numberOfStations - validity), StatsType.MINIMUM));
		     average.put(TAIR, new Statistics(0.0, "NULL", utcDateTime, (numberOfStations - validity), StatsType.AVERAGE));
		     total.put(TAIR, new Statistics(0.0, "NULL", utcDateTime, (numberOfStations - validity), StatsType.TOTAL));
		}
		
		// Reinitialize values to use for calculations!
		validity = 0;
		max = Double.NEGATIVE_INFINITY;
		min = Double.POSITIVE_INFINITY;
		avg = 0;
		tot = 0;
		size = 0;
		maxIndex = 0;
		minIndex = 0;
		
		// Loop through ta9mInfo and checks if valids, then finds max, min, and average values
		for (int i = 0; i < numberOfStations; i++) 
		{
			if (ta9mInfo.get(i).isValid()) 
			{
				if (ta9mInfo.get(i).getValue() > max) 
				{
					max = ta9mInfo.get(i).getValue();
					maxIndex = i;
				}
				if (ta9mInfo.get(i).getValue() < min) 
				{
					min = ta9mInfo.get(i).getValue();
					minIndex = i;
				}

				tot += ta9mInfo.get(i).getValue();
				size++;
			}
			else
			{
				validity++;
			}
		}
		// calculates average for ta9m information
		avg = tot / size;
		
		// if the validity is less than 10, store the data in each tree
		if (validity < NUMBER_OF_MISSING_OBSERVATIONS)
		{
			maximum.put(TA9M, new Statistics(max, ta9mInfo.get(maxIndex).getStid(), utcDateTime,
					(numberOfStations - validity), StatsType.MAXIMUM));
			minimum.put(TA9M, new Statistics(min, ta9mInfo.get(minIndex).getStid(), utcDateTime,
					(numberOfStations - validity), StatsType.MINIMUM));
			average.put(TA9M,
					new Statistics(avg, MESONET, utcDateTime, (numberOfStations - validity), StatsType.AVERAGE));

			total.put(TA9M, new Statistics(tot, MESONET, utcDateTime, (numberOfStations - validity), StatsType.TOTAL));
		}
		// if not, then put NULL values in
		else
		{
			 maximum.put(TA9M, new Statistics(-900, "NULL", utcDateTime, (numberOfStations - validity), StatsType.MAXIMUM));
			 minimum.put(TA9M, new Statistics(-900, "NULL", utcDateTime, (numberOfStations - validity), StatsType.MINIMUM));
		     average.put(TA9M, new Statistics(0.0, "NULL", utcDateTime, (numberOfStations - validity), StatsType.AVERAGE));
		     total.put(TA9M, new Statistics(0.0, "NULL", utcDateTime, (numberOfStations - validity), StatsType.TOTAL));
		}
		
		// finally, store the trees corresponding to the Statistic Type into the statistics EnumMap!
        statistics.put(StatsType.MAXIMUM, maximum);
        statistics.put(StatsType.MINIMUM, minimum);
        statistics.put(StatsType.TOTAL, total);
        statistics.put(StatsType.AVERAGE, average);
	}

	/**
	 * Method that prepares the dataCatalg by initializing dataCatalog and statistics
	 */
	private void prepareDataCatalog() 
	{
		dataCatalog = new HashMap<String, ArrayList<Observation>>();
		statistics = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
	}

	/**
	 * Method that calls the calculateAllStatistics to calculate all Statistics
	 */
	private void calculateStatistics()
	{
		calculateAllStatistics();
	}
	
	/**
	 * Method that gets the statistics for a certain Type and Data Type
	 * @param type references the Statistic Type
	 * @param paramId represents the data type
	 * @return returns Statistic Value that represents the wanted stat
	 */
	public Statistics getStatistics(StatsType type, String paramId) 
	{
		return statistics.get(type).get(paramId);
	}

	/**
	 * toString method that outprints information for each data Type
	 * @return returns a String that represents statistics for the dataset
	 */
	public String toString() 
	{
		return String.format(
				"========================================================\n" 
						+ "=== %d-%02d-%d %d:%d ===\n"
						+ "========================================================\n"
						+ "Maximum Air Temperature[1.5m] = %.1f C at %s\r\n"
						+ "Minimum Air Temperature[1.5m] = %.1f C at %s\r\n"
						+ "Average Air Temperature[1.5m] = %.1f C at %s\r\n"
						+ "========================================================\r\n"
						+ "========================================================\r\n"
						+ "Maximum Air Temperature[9.0m] = %.1f C at %s\r\n"
						+ "Minimum Air Temperature[9.0m] = %.1f C at %s\r\n"
						+ "Average Air Temperature[9.0m] = %.1f C at %s\r\n"
						+ "========================================================\r\n"
						+ "========================================================\r\n"
						+ "Maximum Solar Radiation[1.5m] = %.1f W/m^2 at %s\r\n"
						+ "Minimum Solar Radiation[1.5m] = %.1f W/m^2 at %s\r\n"
						+ "Average Solar Radiation[1.5m] = %.1f W/m^2 at %s\r\n"
						+ "========================================================\n",
						utcDateTime.get(Calendar.YEAR), utcDateTime.get(Calendar.MONTH), utcDateTime.get(Calendar.DATE), utcDateTime.get(Calendar.HOUR_OF_DAY),
		                utcDateTime.get(Calendar.MINUTE), 
		                getStatistics(StatsType.MAXIMUM, TAIR).getValue(), getStatistics(StatsType.MAXIMUM, TAIR).getStid(),
		                getStatistics(StatsType.MINIMUM, TAIR).getValue(), getStatistics(StatsType.MINIMUM, TAIR).getStid(),
		                getStatistics(StatsType.AVERAGE, TAIR).getValue(), getStatistics(StatsType.AVERAGE, TAIR).getStid(),
		                getStatistics(StatsType.MAXIMUM, TA9M).getValue(), getStatistics(StatsType.MAXIMUM, TA9M).getStid(),
		                getStatistics(StatsType.MINIMUM, TA9M).getValue(), getStatistics(StatsType.MINIMUM, TA9M).getStid(),
		                getStatistics(StatsType.AVERAGE, TA9M).getValue(), getStatistics(StatsType.AVERAGE, TA9M).getStid(),
		                getStatistics(StatsType.MAXIMUM, SRAD).getValue(), getStatistics(StatsType.MAXIMUM, SRAD).getStid(),
		                getStatistics(StatsType.MINIMUM, SRAD).getValue(), getStatistics(StatsType.MINIMUM, SRAD).getStid(),
		                getStatistics(StatsType.AVERAGE, SRAD).getValue(), getStatistics(StatsType.AVERAGE, SRAD).getStid());
	}
}
