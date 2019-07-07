package functions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import measurePoints.iMeasureGroup;
import measurePoints.iMeasurePoint;
/**
 * This class can use all methods from functions package while working with iMeasureGroup objects.
 * most methods point to a method from MeasurePointClient and then to a specific Factory, where a concrete function is chosen.
 * 
 * @author Jaroslav Hrabal
 *
 */
public class MeasureGroupClient {
	/**
	 * filters data from an iMeasureGroup instance. 
	 * Filtered data can be viewed, removed or recalculated using interpolation.
	 * @param measureGroup targeted instance for filtering
	 * @param measurePoint quantity to be filtered
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
	public List<Date> filterData(iMeasureGroup measureGroup,
			String measurePoint, Date from, Date to, String method,
			String action, float firstParameter, float secondParameter)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		List<Date> errors = new ArrayList<Date>();
		List error;
		MeasurePointClient mpc = new MeasurePointClient();
		for (Object obj : measureGroup.getMeasurePoints(measurePoint)) {

			iMeasurePoint object = (iMeasurePoint) obj;
			error = mpc.filterData(object, from, to, method, action,
					firstParameter, secondParameter);
			if (error != null) {
				errors.addAll(error);
			}
		}
		return errors;
	}
/**
 * This method can calculate cumulative functions on a specified target iMeasureGroup instance.
 * @param measureGroup targeted instance for calculating
 * @param measurePoint quantity to be calculated
 * @param from starting date, included
 * @param to ending date, excluded
 * @param method to be used in calculations. String must equal one of Classes in cumulativeAggregate package
 * @return
 * @throws ClassNotFoundException
 * @throws InstantiationException
 * @throws IllegalAccessException
 */
	public SortedMap<Date, Float> cumulativeAggregate(
			iMeasureGroup measureGroup, String measurePoint, Date from,
			Date to, String method) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		MeasurePointClient mpc = new MeasurePointClient();
		SortedMap<Date, Float> result = new TreeMap<Date, Float>();
		SortedMap<Date, Float> tmp;
		float temp;
		List<iMeasurePoint> points = measureGroup
				.getMeasurePoints(measurePoint);
		for (Object obj : points) {
			iMeasurePoint object = (iMeasurePoint) obj;
			tmp = mpc.cumulativeAggregate(object, from, to, method);
			if (!tmp.isEmpty()) {
				for (Entry<Date, Float> entry : tmp.entrySet()) {
					if (result.containsKey(entry.getKey())) {
						temp = result.get(entry.getKey());
					} else {
						temp = 0;
					}
					result.put(entry.getKey(), entry.getValue() + temp);
				}

			}
		}
		return result;
	}
	/**
	 * This method can calculate aggregate functions on a specified target iMeasureGroup instance.
	 * @param measureGroup targeted instance for calculating
	 * @param measurePoint quantity to be calculated
	 * @param from starting date, included
	 * @param to ending date, excluded
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public float aggregate(iMeasureGroup measureGroup, String measurePoint,
			Date from, Date to) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		MeasurePointClient mpc = new MeasurePointClient();
		float result = 0;
		Float tmp;
		for (Object obj : measureGroup.getMeasurePoints(measurePoint)) {
			iMeasurePoint object = (iMeasurePoint) obj;
			tmp = mpc.aggregate(object, from, to);
			if (tmp != null) {
				result += tmp;
			}
		}
		return result;
	}
/**
 * This method can calculate weighted average. 
 * It's inteded to be used for weighted average pressure calculation with rate and pressure quantity.
 * @param measureGroup targeted instance for calculating.
 * @param rate Rate to be included in calculation.
 * @param pressure Pressure to be included in calculation.
 * @param from starting date, included
 * @param to ending date, excluded
 * @return
 * @throws IOException
 */
	public List<Float> weightedAverage(iMeasureGroup measureGroup, String rate,
			String pressure, Date from, Date to) throws IOException {
		List<Float> result = new ArrayList();
		Float tmp;
		WeightedAveragePressure wap = new WeightedAveragePressure();
		int i = 0;
		List<iMeasurePoint> press = measureGroup.getMeasurePoints(pressure);
		for (Object obj : measureGroup.getMeasurePoints(rate)) {
			iMeasurePoint rateO = (iMeasurePoint) obj;
			iMeasurePoint pressO = press.get(i);
			tmp = wap.sum(rateO.getData(from, to), pressO.getData(from, to));
			if (tmp != null) {
				result.add(tmp);
			}
		}
		return result;
	}
/**
 * This method can check for integrity on given target Group.
 * If the rate changes but pressure remains the same, there is an error and
 * the method will return a map with NaN values where errors were found. If
 * the method finds an error, it will set the static boolean DataError to
 * true. If not, the DataError value is set to be false.
 * @param measureGroup targeted instance for calculating.
 * @param rate Rate to be included in calculation.
 * @param pressure Pressure to be included in calculation.
 * @param from starting date, included
 * @param to ending date, excluded
 * @param range calculation parameter
 * @return
 * @throws IOException
 */
	public SortedMap<Date, Float> integrity(iMeasureGroup measureGroup,
			String rate, String pressure, Date from, Date to, float range)
			throws IOException {
		SortedMap<Date, Float> result = new TreeMap<Date, Float>();
		SortedMap<Date, Float> tmp;
		RatePressureIntegrity rpi = new RatePressureIntegrity();
		int i = 0;
		List<iMeasurePoint> press = measureGroup.getMeasurePoints(pressure);
		for (Object obj : measureGroup.getMeasurePoints(rate)) {
			iMeasurePoint rateO = (iMeasurePoint) obj;
			iMeasurePoint pressO = press.get(i);
			tmp = rpi.check(rateO.getData(from, to), pressO.getData(from, to),
					range);
			if (tmp != null) {
				result.putAll(tmp);
			}
		}
		return result;
	}
}
