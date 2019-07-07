package functions.dataFilter;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import functions.aggregate.Average;

public class Peak implements iDataFilter {
	public static boolean DataError;

	/**
	 * If the value of measured data peaks with at equal or greater deviation
	 * with a given width of measurements, there is an error and the method will
	 * return a map with NaN values where errors were found. If the method finds
	 * an error, it will set the static boolean DataError to true. If not, the
	 * DataError value is set to be false.
	 * 
	 * @param data
	 *            data to be used for the check.
	 * @param Width
	 *            minimal number of measurements for the error.
	 * @param difference
	 *            minimal value deviation.
	 * @return data SortedMaP(Date,Float) with NaN values where the error was
	 *         found.
	 */
	public SortedMap<Date, Float> check(SortedMap<Date, Float> data,
			float Width, float deviation) {
		DataError = false;
		int index = 0;
		float neighbour = Width + 2;
		SortedMap<Date, Float> neighbours = new TreeMap<Date, Float>();
		for (Entry<Date, Float> entry : data.entrySet()) {
			if (index >= neighbour) {
				if (peakDetection(neighbours, deviation)) {
					SortedMap<Date, Float> tmp = neighbours;
					neighbours = new TreeMap<Date, Float>();
					neighbours.put(tmp.firstKey(), tmp.get(tmp.firstKey()));
					neighbours.put(tmp.lastKey(), tmp.get(tmp.lastKey()));
					tmp.remove(tmp.firstKey());
					tmp.remove(tmp.lastKey());
					for (Entry<Date, Float> entry2 : tmp.entrySet()) {
						entry2.setValue(Float.NaN);
					}
					data.putAll(tmp);
					index = 2;
				} else {
					neighbours.remove(neighbours.firstKey());

					index--;
				}
			}
			if (!Float.isNaN(entry.getValue())) {
				neighbours.put(entry.getKey(), entry.getValue());
				index++;
			}

		}
		return data;
	}

	/**
	 * This method is used to detect if there is a peak error occurring.
	 * 
	 * @param neighbours
	 * @param deviation
	 * @return true error found / false - no error
	 */
	private boolean peakDetection(SortedMap<Date, Float> neighbours,
			float deviation) {

		float first = neighbours.get(neighbours.firstKey());
		float last = neighbours.get(neighbours.lastKey());
		float avarage = (new Average()).calculate(neighbours.subMap(
				neighbours.firstKey(), (neighbours.lastKey())));

		if (Math.abs(first - avarage) > deviation) {
			if (Math.abs(last - avarage) > deviation) {
				DataError = true;
				return true;
			}
		}
		return false;

	}
}
