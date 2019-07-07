package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Contains methods for parsing data files. Methods read data files, parse
 * theese data and return them as objects.
 * 
 * @author Jaroslav Hrabal
 *
 */

public class ParserData {
	/**
	 * This value checks if its nessessary to skip the first collum. It is
	 * usualy used when reading Rate quantity, where first collum is the sum of
	 * following collums and therefore unnessessary.
	 */
	boolean skipCollum = false;

	public boolean isSkipCollum() {
		return skipCollum;
	}

	public void setSkipCollum(boolean skipCollum) {
		this.skipCollum = skipCollum;
	}

	/**
	 * Creates a list of names from a file. Each name must be on a new line.
	 * 
	 * @param Address
	 *            Address of name file.
	 * @return List List of String values.
	 */
	public List<String> parseWells(String Address) {
		List<String> wells = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(Address));
			String line = br.readLine();
			while (line != null) {
				wells.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wells;

	}

	/**
	 * Parses a data file and returns data in a form of a list containing maps
	 * of dates and float values.
	 * 
	 * @param Address
	 *            Address of data file
	 * @param DateFormat
	 *            String value describing the date format found in the file to
	 *            be used as a <code>SimpleDateFormat</code>
	 * @return data List of maps (Date, Float)
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<TreeMap<Date, Float>> parseData(String Address,
			String DateFormat) throws IOException, ParseException {
		List<TreeMap<Date, Float>> data = new ArrayList<TreeMap<Date, Float>>();
		DateFormat format = new SimpleDateFormat(DateFormat);
		BufferedReader br = new BufferedReader(new FileReader(Address));
		try {
			String line = br.readLine();
			if (line.length() < 2) {
				line = br.readLine();
			}
			String[] lineArray = line.split("\\s+");
			int wellCount = lineArray.length - 2;
			for (int i = 0; i < wellCount; i++) {
				data.add(new TreeMap<Date, Float>());
			}
			if (skipCollum) {
				data.add(new TreeMap<Date, Float>());
			}
			while (line != null) {
				lineArray = line.split("\\s+");
				Date date = format.parse(lineArray[1]);

				for (int i = 0; i < wellCount; i++) {
					(data.get(i)).put(date, Float.parseFloat(lineArray[i + 2]));
				}
				line = br.readLine();
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		} finally {
			br.close();
		}
		if (skipCollum) {
			data.remove(1);
		}
		return data;
	}

}
