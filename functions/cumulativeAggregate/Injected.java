package functions.cumulativeAggregate;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class Injected implements iCuAggregate {
	/**
	 * Calculates cumulative sum of given data values, which are >0.
	 * 
	 * @param data
	 *            data to be used for the calculation.
	 * @return injected List of Float values
	 */
	public SortedMap<Date, Float> calculate(SortedMap<Date, Float> data) {
		float sum = 0;
		SortedMap<Date, Float> injected = new TreeMap<Date, Float>();
		for (Entry<Date, Float> entry : data.entrySet()) {
			if (entry.getValue() > 0) {
				sum += entry.getValue();
				injected.put(entry.getKey(), sum);
			}
		}
		return injected;
	}
}
