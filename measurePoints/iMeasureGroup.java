package measurePoints;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public interface iMeasureGroup {

	public String getName();

	public void addObject(iMeasureGroup obj);

	public iMeasureGroup removeObject(String name);

	public iMeasureGroup getObject(String name);

	public boolean contains(String name);

	public int getCount();

	public HashMap<SortedMap<Date, Float>, String> getData(
			String meassurePointName, Date from, Date to);

	public iMeasurePoint getMeasurePoint(String name);

	public void addData(iMeasurePoint measurePoint, List<String> list,
			List<TreeMap<Date, Float>> map);

	public List<iMeasurePoint> getMeasurePoints(String name);

	public String toString();

}
