package measurePoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class represents a well.
 * 
 * @author Jaroslav Hrabal
 *
 */
public class Well extends AbstractGroup implements iMeasureGroup {

	public Well(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Adds data from the List(TreeMap(Date,Float)) structure into the proper
	 * AbstractQuantity object in the ObjectList. If the object representing
	 * quantity does not exist yet, new one will be created.
	 * 
	 * @param quantity
	 *            Template class to be compared to others by name, if there is
	 *            no match, new quantity will be created.
	 * @param list
	 *            list of well names to be used for the
	 *            List(TreeMap(Date,Float)) structure labeling.
	 * @param map
	 *            List(TreeMap(Date,Float)) structure containing data to be put
	 *            into this object.
	 */
	@Override
	public void addData(iMeasurePoint quantity, List<String> list,
			List<TreeMap<Date, Float>> map) {
		for (String name : list) {
			if (name.equals(this.getName())) {
				if (ObjectList.isEmpty()) {
					iMeasurePoint newQuant = quantity.clonePoint();
					newQuant.addData(map.get(list.indexOf(name)));
					ObjectList.add(newQuant);
					return;
				}
				for (Object obj : ObjectList) {
					iMeasurePoint object = (iMeasurePoint) obj;
					if (object.getName().equals(quantity.getName())) {
						object.addData(map.get(list.indexOf(name)));
						return;
					}
					iMeasurePoint newQuant = quantity.clonePoint();
					newQuant.addData(map.get(list.indexOf(name)));
					ObjectList.add(newQuant);
					return;
				}
			}
		}

	}

	@Override
	public iMeasureGroup removeObject(String name) {
		return null;
	}

	@Override
	public List<String> getPointNames() {
		List<String> nameList = new ArrayList<String>();
		for (Object obj : this.ObjectList) {
			iMeasurePoint o = (iMeasurePoint) obj;
			nameList.add(o.getName());
		}
		return nameList;
	}

	@Override
	public HashMap<SortedMap<Date, Float>, String> getData(
			String meassurePointName, Date from, Date to) {
		HashMap<SortedMap<Date, Float>, String> result = new HashMap<SortedMap<Date, Float>, String>();
		for (Object obj : this.ObjectList) {
			iMeasurePoint object = (iMeasurePoint) obj;
			if (object.getName().equals(meassurePointName)) {
				result.put(object.getData(from, to), this.getName());
				return result;
			}
		}
		return result;
	}

	@Override
	public iMeasurePoint getMeasurePoint(String name) {
		iMeasurePoint result = null;
		for (Object obj : this.ObjectList) {
			iMeasurePoint object = (iMeasurePoint) obj;
			if (object.getName().equals(name)) {
				result = object;
				if (result != null) {
					return result;
				}
			}
		}
		return result;
	}

	@Override
	public iMeasureGroup getObject(String name) {
		if (this.getName().equals(name)) {
			return this;
		}
		return null;
	}

	@Override
	public List<iMeasurePoint> getMeasurePoints(String name) {
		iMeasurePoint point = (iMeasurePoint) getMeasurePoint(name);
		if (point != null) {
			List<iMeasurePoint> mp = new ArrayList<iMeasurePoint>();
			mp.add(point);
			return mp;
		}
		return null;
	}
	/*
	 * @override public Float aggregate(Date from, Date to, String quantityName)
	 * { if (quantityName != null) { AbstractQuantity quant =
	 * getQuantity(quantityName); if (quant != null) { return
	 * quant.aggregate(from, to); }
	 * 
	 * } else { float result = 0; for (Object quant : ObjectList) { result +=
	 * ((AbstractQuantity) quant).aggregate(from, to); } return result;
	 * 
	 * } return null; }
	 * 
	 * @Override public List<Float> cumulativeAggregate(Date from, Date to,
	 * String quantityName, String method) { if (quantityName != null) {
	 * AbstractQuantity quant = getQuantity(quantityName); if (quant != null) {
	 * return quant.cumulativeAggregate(from, to, method); }
	 * 
	 * } else { List<Float> result = null;
	 * 
	 * for (Object quant : ObjectList) { AbstractQuantity quantiy =
	 * ((AbstractQuantity) quant); if(result == null){ result =
	 * quantiy.cumulativeAggregate(from, to, method); }else{ List<Float> tmp
	 * =quantiy.cumulativeAggregate(from, to, method); int index = 0; for(Float
	 * value: tmp){ result.add(index, value+result.get(index)); } } } return
	 * result; } return null; }
	 */

}
