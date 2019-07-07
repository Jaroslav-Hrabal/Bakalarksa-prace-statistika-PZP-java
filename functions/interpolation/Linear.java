package functions.interpolation;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

public class Linear implements iInterpolation {
	/**
	 * Replaces all NaN values by linear interpolation calculated from the rest
	 * of the values.
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
					data.putAll(linearInterpolation(
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
	 * Replaces NaN with calculated values.
	 * 
	 * @param data
	 *            map to be changed.
	 * @param first
	 *            value used in linear interpolation calculation.
	 * @param last
	 *            value used in linear interpolation calculation.
	 * @return map without NaN values.
	 */
	public SortedMap<Date, Float> linearInterpolation(
			SortedMap<Date, Float> data, float first, float last) {
		float difference = last - first;
		int size = data.size();
		int index = 0;
		for (Entry<Date, Float> entry : data.entrySet()) {
			data.put(entry.getKey(), first + difference * index / size);
			index++;
		}
		return data;
	}
}
