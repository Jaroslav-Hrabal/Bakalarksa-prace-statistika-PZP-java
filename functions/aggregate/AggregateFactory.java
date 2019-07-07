package functions.aggregate;

import java.util.Date;
import java.util.SortedMap;

public class AggregateFactory {
	/**
	 * Allows the use of any method in functions.aggregate package. The method
	 * is to be called from String value.
	 * 
	 * @param method
	 *            name of the method to be used.
	 * @param data
	 *            data to be used for the calculation.
	 * @return result Float value
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public Float aggregate(SortedMap<Date, Float> data, String method)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		Class<?> c = Class.forName("functions.aggregate." + method);
		iAggregate t = (iAggregate) c.newInstance();
		return t.calculate(data);

	}
}
