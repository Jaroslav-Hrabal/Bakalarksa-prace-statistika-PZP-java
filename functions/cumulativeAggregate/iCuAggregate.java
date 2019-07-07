package functions.cumulativeAggregate;

import java.util.Date;
import java.util.SortedMap;

public interface iCuAggregate {
	public SortedMap<Date, Float> calculate(SortedMap<Date, Float> data);
}
