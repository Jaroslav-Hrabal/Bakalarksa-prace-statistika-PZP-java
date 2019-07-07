package functions.cumulativeAggregate;

import java.util.Date;
import java.util.SortedMap;

public class CuAggregateFactory {
	/**
	 * Allows the use of any method in functions.cumulativeAggregate package.
	 * The method is to be called from String value.
	 * 
	 * @param method
	 *            name of the method to be used.
	 * @param data
	 *            data to be used for the calculation.
	 * @return result List of Float values
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public SortedMap<Date, Float> aggregate(SortedMap<Date, Float> data,
			String method) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Class<?> c = Class.forName("functions.cumulativeAggregate." + method);
		iCuAggregate t = (iCuAggregate) c.newInstance();
		return t.calculate(data);

	}
}
