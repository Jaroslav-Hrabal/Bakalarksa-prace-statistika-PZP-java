package functions.interpolation;

import java.util.Date;
import java.util.SortedMap;

public class InterpolationFactory {
	/**
	 * Allows the use of any method in functions.interpolation package.
	 * 
	 * @param data
	 *            data to be completed.
	 * @param method
	 *            String method to be used for checking.
	 * @return result complete data.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public SortedMap<Date, Float> interpolate(SortedMap<Date, Float> data,
			String method) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<?> c = Class.forName("functions.interpolation." + method);
		iInterpolation t = (iInterpolation) c.newInstance();
		return t.interpolate(data);

	}
}
