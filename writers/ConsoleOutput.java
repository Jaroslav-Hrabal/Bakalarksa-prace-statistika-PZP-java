package writers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;

/**
 * Contains methods for console output. Methods read data files, parse these
 * data and return them as objects.
 * 
 * @author Jaroslav Hrabal
 *
 */

public class ConsoleOutput {
	/**
	 * Types values from the list of maps into the console.
	 * 
	 * @param hashMap
	 * @param from
	 * @param to
	 */
	public static void viewList(
			HashMap<SortedMap<Date, Float>, String> hashMap, Date from, Date to) {
		for (Entry<SortedMap<Date, Float>, String> dataMap : hashMap.entrySet()) {
			System.out.println(dataMap.getValue());
			viewMap(dataMap.getKey(), from, to);
		}
	}

	/**
	 * Types values from the map into the console.
	 * 
	 * @param dataMap
	 * @param from
	 * @param to
	 */
	public static void viewMap(SortedMap<Date, Float> dataMap, Date from,
			Date to) {
		if (!(from == null || to == null)) {
			dataMap = dataMap.subMap(from, to);
		}
		for (Entry<Date, Float> entry : dataMap.entrySet()) {

			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
		System.out.println();

	}
}
