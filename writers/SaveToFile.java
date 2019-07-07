package writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;

/**
 * This class can be used to save data maps from getData or some functions
 * methods into a file
 * 
 * @author Jaroslav Hrabal
 *
 */
public class SaveToFile {
	/**
	 * Types values from the list of maps into the file.
	 * 
	 * @param hashMap
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void saveList(
			HashMap<SortedMap<Date, Float>, String> hashMap, Date from,
			Date to, File file) throws IOException {

		for (Entry<SortedMap<Date, Float>, String> dataMap : hashMap.entrySet()) {
			FileWriter fw = new FileWriter(file, true);
			fw.write(dataMap.getValue() + "\n");
			fw.close();
			saveMap(dataMap.getKey(), from, to, file);
		}

	}

	/**
	 * Types values from the map into the file.
	 * 
	 * @param dataMap
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void saveMap(SortedMap<Date, Float> dataMap, Date from,
			Date to, File file) throws IOException {
		FileWriter fw = new FileWriter(file, true);

		if (!(from == null || to == null)) {
			dataMap = dataMap.subMap(from, to);
		}
		for (Entry<Date, Float> entry : dataMap.entrySet()) {
			fw.write(entry.getKey() + " = " + entry.getValue() + "\n");
		}

		fw.close();
	}
}
