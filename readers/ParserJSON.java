package readers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Contains methods for parsing JSON head files. Methods read data files, parse
 * these data and return them as objects.
 * 
 * @author Jaroslav Hrabal
 *
 */

public class ParserJSON {

	/**
	 * 
	 * Parses JSON configuration file containing a group file and configuration
	 * files for all quantities.
	 * 
	 * @param Address
	 *            of the configuration file.
	 * @return data List of String values representing file addresses.
	 */
	public List<String> parseConfig(String Address) {
		List<String> Filepaths = new ArrayList<String>();

		try {
			FileReader reader = new FileReader(Address);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			String groupFile = (String) jsonObject.get("Groups");
			Filepaths.add(groupFile);
			JSONArray confFiles = (JSONArray) jsonObject.get("confFiles");

			for (int i = 0; i < confFiles.size(); i++) {

				Filepaths.add((String) confFiles.get(i));

			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return Filepaths;
	}

	/**
	 * Parses quantity configuration file. The file must contain Name and Units
	 * parameters. Addresses for the name and data file are also required.
	 * Optional parameters are Class, Date Format and Aggregate.
	 * 
	 * @param Address
	 * @return
	 */
	public String[] parseQuant(String Address) {
		String[] description = new String[7];
		try {

			FileReader reader = new FileReader(Address);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			if (jsonObject.containsKey("RATE")) {
				description[0] = (String) jsonObject.get("RATE");
			} else {
				description[0] = "0";
			}
			description[1] = (String) jsonObject.get("Name");
			description[2] = (String) jsonObject.get("Units");
			if (jsonObject.containsKey("Aggregate")) {
				description[3] = (String) jsonObject.get("Aggregate");
			} else {
				description[3] = "Average";
			}
			if (jsonObject.containsKey("Date Format")) {
				description[4] = (String) jsonObject.get("Date Format");
			} else {
				description[4] = "dd.MM.yy:HH:mm";
			}
			description[5] = (String) jsonObject.get("Name File");
			description[6] = (String) jsonObject.get("Data File");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return description;
	}
}
