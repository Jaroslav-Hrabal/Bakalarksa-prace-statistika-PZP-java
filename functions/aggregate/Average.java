package functions.aggregate;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

public class Average implements iAggregate {
	/**
	 * Calculates average of given data values.
	 * 
	 * @param data
	 *            data to be used for the calculation.
	 * @return result Float value
	 */
	public float calculate(SortedMap<Date, Float> data) {
		float sum = 0;
		int index = 0;
		for (Entry<Date, Float> entry : data.entrySet()) {
			sum += entry.getValue();
			index++;
		}
		return sum / index;
	}

}
