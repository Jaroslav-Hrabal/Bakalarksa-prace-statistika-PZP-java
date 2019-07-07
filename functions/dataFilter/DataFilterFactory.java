package functions.dataFilter;

import java.util.Date;
import java.util.SortedMap;

public class DataFilterFactory {
	/**
	 * Allows the use of any method in functions.dataFilter package.
	 * 
	 * @param data
	 *            data to be checked.
	 * @param method
	 *            String method to be used for checking.
	 * @param firstParameter
	 *            Float value of first parameter used in the filtering.
	 * @param secondParameter
	 *            Float value of second parameter used in the filtering.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public SortedMap<Date, Float> filter(SortedMap<Date, Float> data,
			String method, float firstParameter, float secondParameter)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {

		Class<?> c = Class.forName("functions.dataFilter." + method);
		iDataFilter t = (iDataFilter) c.newInstance();
		return (SortedMap<Date, Float>) t.check(data, firstParameter,
				secondParameter);

	}
}
