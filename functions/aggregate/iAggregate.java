package functions.aggregate;

import java.util.Date;
import java.util.SortedMap;

public interface iAggregate {
	public float calculate(SortedMap<Date, Float> data);
}
