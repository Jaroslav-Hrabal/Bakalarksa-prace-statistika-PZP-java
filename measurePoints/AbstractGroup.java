package measurePoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This is an abstract class for objects representing virtuals, storages, groups
 * and wells. Contains methods for object manipulation, including their
 * properties and data / sub objects.
 * 
 * @author Jaroslav Hrabal
 *
 */
public abstract class AbstractGroup implements iMeasureGroup {
	/**
	 * Name of the object
	 */
	String Name = null;
	/**
	 * Value for determining if the object is active or not.
	 */
	Boolean open = true;
	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	/**
	 * List containing sub objects of the object. Objects are to be expected of
	 * AbstractObject or AbstractQuantity type.
	 */
	public List<Object> ObjectList = null;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getCount() {
		return ObjectList.size();
	}

	public List<Object> getObjectList() {
		return ObjectList;
	}

	public void setObjectList(List<Object> objectList) {
		ObjectList = objectList;
	}

	public AbstractGroup(String name) {
		Name = name;
		ObjectList = new ArrayList<Object>();
	}

	public void addObject(iMeasureGroup obj) {
		ObjectList.add(obj);
	}
/**
 * This method returns a list of all quantity objects in ObjectList.
 * @return
 */
	public List<String> getPointNames() {
		List<String> nameList = new ArrayList<String>();
		for (Object obj : this.ObjectList) {
			AbstractGroup o = ((AbstractGroup) obj);
			nameList.addAll(o.getPointNames());
		}
		return nameList;
	}
/**
 * This method removes an object from ObjectList
 */
	public iMeasureGroup removeObject(String name) {
		iMeasureGroup result = null;

		for (Object obj : this.ObjectList) {

			iMeasureGroup object = (iMeasureGroup) obj;

			if (name.equals(object.getName())) {
				this.ObjectList.remove(object);

			}
			result = object.removeObject(name);
			if (result != null) {
				return result;
			}
		}

		return result;
	}
/**
 * This method returns specified object.
 */
	public iMeasureGroup getObject(String name) {
		iMeasureGroup result = null;
		if (this.getName().equals(name)) {
			return this;
		}
		for (Object obj : this.ObjectList) {
			iMeasureGroup object = (iMeasureGroup) obj;
			result = object.getObject(name);
			if (result != null) {
				return result;
			}
		}
		return result;
	}
/**
 * checks if ListObject contains given item
 */
	public boolean contains(String name) {
		if (this.getObject(name) == null) {
			return false;
		}
		return true;
	}
/**
 * method for obtaining data in understandable form. every datamap has a String description
 */
	public HashMap<SortedMap<Date, Float>, String> getData(
			String meassurePointName, Date from, Date to) {
		HashMap<SortedMap<Date, Float>, String> result = new HashMap<SortedMap<Date, Float>, String>();
		for (Object obj : this.ObjectList) {
			iMeasureGroup object = (iMeasureGroup) obj;
			HashMap<SortedMap<Date, Float>, String> tmp = object.getData(
					meassurePointName, from, to);
			if (tmp != null) {
				for (Entry<SortedMap<Date, Float>, String> entry : tmp
						.entrySet()) {
					entry.setValue(entry.getValue() + " " + this.getName());
				}
				result.putAll(tmp);
			}
		}
		return result;
	}
/**
 * this method returns object representing quantity according to given name
 */
	public iMeasurePoint getMeasurePoint(String name) {
		iMeasurePoint result = null;
		for (Object obj : this.ObjectList) {
			iMeasureGroup object = (iMeasureGroup) obj;
			result = object.getMeasurePoint(name);
		}
		return result;
	}
/**
 * method for adding data
 */
	public void addData(iMeasurePoint measurePoint, List<String> list,
			List<TreeMap<Date, Float>> map) {
		for (Object obj : this.ObjectList) {
			iMeasureGroup object = (iMeasureGroup) obj;
			object.addData(measurePoint, list, map);
		}

	}
/**
 * This method returns a list of all iMeasurePoints instances inside this object.
 */
	public List<iMeasurePoint> getMeasurePoints(String name) {
		List<iMeasurePoint> mp = new ArrayList<iMeasurePoint>();
		for (Object obj : this.ObjectList) {
			iMeasureGroup object = (iMeasureGroup) obj;
			List<iMeasurePoint> tmp = object.getMeasurePoints(name);
			if (tmp != null) {
				mp.addAll(tmp);
			}
		}
		return mp;
	}

	@Override
	public String toString() {
		return this.getName();

	}
}
