package measurePoints;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AbstractPoint implements iMeasureGroup{
	String Name, Units, Aggregate;
	TreeMap<Date, Float> data;
/**
 * if the Aggregate parameter is not specified, Sum will be chosen instead.
 * @param name
 * @param units
 */
	public AbstractPoint(String name, String units) {
		Name = name;
		Units = units;
		data = new TreeMap<Date, Float>();
		Aggregate = "Sum";
	}

	public AbstractPoint(String name, String units, String aggregate) {
		Name = name;
		Units = units;
		Aggregate = aggregate;
		data = new TreeMap<Date, Float>();
	}

	public String getAggregate() {
		return Aggregate;
	}

	public void setAggregate(String aggregate) {
		Aggregate = aggregate;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getUnits() {
		return Units;
	}

	public void setUnits(String units) {
		Units = units;
	}
/**
 * method for getting data. From date included, to date excluded.
 * Is used by getdata() method from iMeasureGroup.
 */
	public SortedMap<Date, Float> getData(Date from, Date to) {
		if (from == null || to == null) {
			return data;
		}
		SortedMap<Date, Float> result = data.subMap(from, to);
		return result;
	}

	public SortedMap<Date, Float> getData() {
		return data;
	}
/**
 * method for adding data.
 * Is used by adddata() method from iMeasureGroup.
 */
	public void addData(SortedMap<Date, Float> map) {
		// TODO Auto-generated method stub
		this.data.putAll(map);
	}
}
