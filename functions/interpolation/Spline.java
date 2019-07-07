package functions.interpolation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class Spline implements iInterpolation {
	/**
	 * Replaces all NaN values by spline interpolation calculated from the rest
	 * of the values.
	 * 
	 * @param data
	 *            map with missing values.
	 * @return data complete map.
	 */
	public SortedMap<Date, Float> interpolate(SortedMap<Date, Float> data) {
		double index = 1;
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		for (Entry<Date, Float> entry : data.entrySet()) {
			if (!entry.getValue().isNaN()) {
				y.add((double) entry.getValue());
				x.add(index);
			}
			index++;
		}
		SplineInterpolator function = new SplineInterpolator();
		double[] X = new double[x.size()];
		for (int i = 0; i < x.size(); i++)
			X[i] = x.get(i);
		double[] Y = new double[y.size()];
		for (int i = 0; i < y.size(); i++)
			Y[i] = y.get(i);
		PolynomialSplineFunction f = function.interpolate(X, Y);
		index = 1;
		for (Entry<Date, Float> entry : data.entrySet()) {
			if (entry.getValue().isNaN()) {
				float value = (float) f.value(index);
				data.put(entry.getKey(), value);
			}
			index++;
		}
		return data;

	}
}
