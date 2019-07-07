package functions.cumulativeAggregate;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class Total implements iCuAggregate {
	/**
	 * Calculates cumulative sum of given data absolute values.
	 * 
	 * @param data
	 *            data to be used for the calculation.
	 * @return total List of Float values
	 */
	public SortedMap<Date, Float> calculate(SortedMap<Date, Float> data) {
		float sum = 0;
		SortedMap<Date, Float> total = new TreeMap<Date, Float>();
		for (Entry<Date, Float> entry : data.entrySet()) {
			sum += Math.abs(entry.getValue());
			total.put(entry.getKey(), sum);

		}
		return total;
	}
}
