package measurePoints;

import java.util.Date;
import java.util.SortedMap;

public interface iMeasurePoint {
	public String getName();

	public String getUnits();

	public String getAggregate();

	public SortedMap<Date, Float> getData(Date from, Date to);

	public void addData(SortedMap<Date, Float> sortedMap);

	public void removeData(Date date);

	public iMeasurePoint clonePoint();
}
