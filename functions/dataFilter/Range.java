package functions.dataFilter;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

public class Range implements iDataFilter {
	public boolean DataError;

	/**
	 * If the value of measured data exceeds given limits, there is an error and
	 * the method will return a map with NaN values where errors were found. If
	 * the method finds an error, it will set the static boolean DataError to
	 * true. If not, the DataError value is set to be false.
	 * 
	 * @param data
	 *            data to be used for the check.
	 * @param from
	 *            minimal value.
	 * @param to
	 *            maximal value.
	 * @return data SortedMaP(Date,Float) with NaN values where the error was
	 *         found.
	 */
	public SortedMap<Date, Float> check(SortedMap<Date, Float> data,
			float from, float to) {
		DataError = false;
		for (Entry<Date, Float> entry : data.entrySet()) {
			if ((entry.getValue() < from) | (entry.getValue() > to)) {
				data.put(entry.getKey(), Float.NaN);
				DataError = true;
			}
		}
		return data;
	}
}
