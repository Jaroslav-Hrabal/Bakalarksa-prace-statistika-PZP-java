package functions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import measurePoints.iMeasurePoint;
import functions.aggregate.AggregateFactory;
import functions.cumulativeAggregate.CuAggregateFactory;
import functions.dataFilter.DataFilterFactory;
import functions.interpolation.InterpolationFactory;
/**
 * This class can use all methods from functions package while working with iMeasurePoint objects.
 * every method points  to a specific Factory, where a concrete function is chosen.
 * 
 * @author Jaroslav Hrabal
 *
 */
public class MeasurePointClient {
	/**
	 * 
	 * filters data from an iMeasurePoint instance. 
	 * Filtered data can be viewed, removed or recalculated using interpolation.
	 * @param measurePoint targeted instance for filtering
	 * @param from starting date, included
	 * @param to ending date, excluded
	 * @param method filtering method (Range/Peak/Constant)
	 * @param action action to be used when errors are found (Check, Remove, Replace + interpolation method)
	 * @param firstParameter parameter for filtering
	 * @param secondParameter parameter for filtering
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public List<Date> filterData(iMeasurePoint measurePoint, Date from,
			Date to, String method, String action, float firstParameter,
			float secondParameter) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		SortedMap<Date, Float> result = (SortedMap<Date, Float>) (new DataFilterFactory())
				.filter(measurePoint.getData(from, to), method, firstParameter,
						secondParameter);
		String arr[] = action.split(" ");
		List<Date> errors = new ArrayList<Date>();
		switch (arr[0]) {
		default:
			for (Entry<Date, Float> entry : result.entrySet()) {
				if (entry.getValue().isNaN()) {
					errors.add(entry.getKey());

				}
			}
			return errors;
		case "Remove":
			for (Entry<Date, Float> entry : result.entrySet()) {
				if (entry.getValue().isNaN()) {
					// measurePoint.removeData(entry.getKey());
					SortedMap<Date, Float> tmp = new TreeMap<Date, Float>();
					tmp.put(entry.getKey(), Float.NaN);
					measurePoint.addData(tmp);
				}
			}
			break;
		case "Replace":
			measurePoint.addData((new InterpolationFactory()).interpolate(
					result, arr[1]));
			break;
		}
		return null;
	}
/**
 * This method can calculate cumulative functions on a specified target iMeasurePoint instance.
 * @param measurePoint targeted instance for calculating
 * @param from starting date, included
 * @param to ending date, excluded
 * @param method to be used in calculations. String must equal one of Classes in cumulativeAggregate package
 * @return
 * @throws ClassNotFoundException
 * @throws InstantiationException
 * @throws IllegalAccessException
 */
	public SortedMap<Date, Float> cumulativeAggregate(
			iMeasurePoint measurePoint, Date from, Date to, String method)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		return (SortedMap<Date, Float>) (new CuAggregateFactory()).aggregate(
				measurePoint.getData(from, to), method);

	}
/**
 * This method can calculate aggregate functions on a specified target iMeasurePoint instance.
 * @param measurePoint targeted instance for calculating
 * @param from starting date, included
 * @param to ending date, excluded
 * @return
 * @throws InstantiationException
 * @throws IllegalAccessException
 * @throws ClassNotFoundException
 */
	public float aggregate(iMeasurePoint measurePoint, Date from, Date to)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return (float) (new AggregateFactory()).aggregate(
				measurePoint.getData(from, to), measurePoint.getAggregate());
	}

}
