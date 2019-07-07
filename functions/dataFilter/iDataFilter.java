package functions.dataFilter;

import java.util.Date;
import java.util.SortedMap;

public interface iDataFilter {
	public SortedMap<Date, Float> check(SortedMap<Date, Float> data,
			float Width, float deviation);
}
