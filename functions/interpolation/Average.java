package functions.interpolation;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

public class Average implements iInterpolation {
	/**
	 * Replaces all NaN values by an average of neighboring values.
	 * 
	 * @param data
	 *            map with missing values.
	 * @return data complete map.
	 */
	public SortedMap<Date, Float> interpolate(SortedMap<Date, Float> data) {
		Date first = data.firstKey();
		float last = Float.NaN;
		boolean NaNCheck = false;
		for (Entry<Date, Float> entry : data.entrySet()) {
			if (entry.getValue().isNaN()) {
				NaNCheck = true;

			} else {
				if (NaNCheck) {
					data.putAll(avarageInterpolation(
							data.subMap(first, entry.getKey()),
							data.get(first), entry.getValue()));
					NaNCheck = false;
				}
				first = entry.getKey();
			}

		}
		return data;
	}

	/**
	 * Replaces NaN values by an average of first and last values.
	 * 
	 * @param data
	 *            map to be changed.
	 * @param first
	 *            value used in average calculation.
	 * @param last
	 *            value used in average calculation.
	 * @return data map without NaN values.
	 */
	public SortedMap<Date, Float> avarageInterpolation(
			SortedMap<Date, Float> data, float first, float last) {
		float average = (last + first) / 2;
		int size = data.size();
		int index = 0;
		for (Entry<Date, Float> entry : data.entrySet()) {
			data.put(entry.getKey(), average);
			index++;
		}
		return data;
	}
}
