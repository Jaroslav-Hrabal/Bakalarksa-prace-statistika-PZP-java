package functions;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

public class RatePressureIntegrity {
	public boolean DataError;

	/**
	 * If the rate changes but pressure remains the same, there is an error and
	 * the method will return a map with NaN values where errors were found. If
	 * the method finds an error, it will set the static boolean DataError to
	 * true. If not, the DataError value is set to be false.
	 * 
	 * @param rate
	 * @param pressure
	 * @param difference
	 *            Float value of minimal difference in pressure.
	 * @return pressure SortedMaP(Date,Float) with NaN values where the error
	 *         was found.
	 */
	public SortedMap<Date, Float> check(SortedMap<Date, Float> rate,
			SortedMap<Date, Float> pressure, float difference) {
		// data - fills in insides from first and last value
		DataError = false;
		float lastRate = 0;
		Date lastRateKey = null;
		for (Entry<Date, Float> entry : rate.entrySet()) {
			if ((lastRate - entry.getValue() > difference)) {
				if (lastRateKey != null) {
					if (pressure.get(entry.getKey()).equals(
							pressure.get(entry.getKey()))) {
						pressure.put(entry.getKey(), Float.NaN);
						DataError = true;
					}
				}
			}
			lastRate = entry.getValue();
			lastRateKey = entry.getKey();
		}
		return pressure;
	}
}
