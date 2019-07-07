package functions;

import java.io.IOException;
import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

public class WeightedAveragePressure {
	private Float float1;

	/**
	 * Calculates weighted average pressure out of given data sets.
	 * 
	 * @param rate
	 * @param pressure
	 * @return result Float value
	 * @throws IOException
	 */
	public float sum(SortedMap<Date, Float> rate,
			SortedMap<Date, Float> pressure) throws IOException {
		float sum1 = 0;
		float sum2 = 0;
		for (Entry<Date, Float> entry : rate.entrySet()) {
			Date date = entry.getKey();
			if (pressure.containsKey(date)) {
				sum1 += entry.getValue() * pressure.get(date);
				sum2 += entry.getValue();
			}

		}
		if (sum1 * sum2 == 0) {
			float1 = (Float) null;
			return float1;
		}
		return sum1 / sum2;
	}
}
