package functions.interpolation;

import java.util.Date;
import java.util.SortedMap;

public interface iInterpolation {
	public SortedMap<Date, Float> interpolate(SortedMap<Date, Float> data);
}
