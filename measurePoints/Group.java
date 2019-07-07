package measurePoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class represents a group of wells.
 * 
 * @author Jaroslav Hrabal
 *
 */
public class Group extends AbstractGroup {

	public Group(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method creates objects of class Well based on the given list and adds
	 * them to ObjectList
	 * 
	 * @param objectNames
	 *            list of Strings, containing names of wells to be added.
	 */
	public void addWells(List<String> objectNames) {

		Iterator<String> it = objectNames.iterator();
		String well;
		while (it.hasNext()) {
			well = (String) it.next();

			if (!this.getObjectList().contains(well)) {
				this.addObject(new Well(well));
			}
			;

			it.remove();
		}
	}

	/**
	 * Creates objects of class Group based on the given list and adds them to
	 * ObjectList
	 * 
	 * @param objectNames
	 *            - list of Strings, containing names of wells to be added.
	 */
	public void addGroups(List<String> objectNames) {
		Iterator<String> it = objectNames.iterator();
		String group;
		while (it.hasNext()) {
			group = it.next();

			if (!this.contains(group)) {
				Group obje = new Group(group);
				ObjectList.add(obje);
			}

			it.remove();
		}
	}

	/**
	 * Creates objects of class Well based on the given hashmap of strings. If
	 * these objects do not exist, then the method puts these objects into
	 * ObjectList.
	 * 
	 * @param map
	 *            key = Well, value = Group
	 */
	public void addWells(HashMap<String, String> map) {
		List<String> list = new ArrayList<String>();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		String name = "";
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (pair.getValue().equals(name)) {
				list.add((String) pair.getKey());
			} else {
				name = (String) pair.getValue();
				list.add((String) pair.getKey());
				for (Object obj : ObjectList) {
					Group object = (Group) obj;

					if (name.equals((object.getName()))) {
						object.addWells(list);
					}
				}
				list.clear();
			}
		}
		for (Object obj : this.ObjectList) {
			if (name.equals((((iMeasureGroup) obj).getName()))) {
				((Group) obj).addWells(list);
			}

		}

	}

}
