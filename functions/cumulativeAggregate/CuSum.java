package functions.cumulativeAggregate;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class CuSum implements iCuAggregate {
	/**
	 * Calculates cumulative sum of given data values.
	 * 
	 * @param data
	 *            data to be used for the calculation.
	 * @return cuSum List of Float values
	 */
	public SortedMap<Date, Float> calculate(SortedMap<Date, Float> data) {
		float sum = 0;
		SortedMap<Date, Float> cuSum = new TreeMap<Date, Float>();
		for (Entry<Date, Float> entry : data.entrySet()) {
			sum += entry.getValue();
			cuSum.put(entry.getKey(), sum);
		}
		return cuSum;
	}
}
