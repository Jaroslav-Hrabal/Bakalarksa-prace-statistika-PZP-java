package measurePoints;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * This class reprezents quantities such as pressure, rate or temperature.
 * It is inteded to be used under iMeasurePoint interface and inside Well instance's ObjectList.
 * @author Jaroslav Hrabal
 *
 * @param <iMeasurepoint>
 */
public class Quantity<iMeasurepoint> extends AbstractPoint{



public Quantity(String name, String units, String aggregate) {
		super(name, units, aggregate);
		// TODO Auto-generated constructor stub
	}

	public Quantity(String name, String units) {
		super(name, units);
		// TODO Auto-generated constructor stub
	}

/**
 * This method is used by readers to create new instances according to a template instance.
 */
	public iMeasurePoint clonePoint() {
		return (iMeasurePoint) new Quantity<Object>(this.getName(), this.getUnits(),
				this.getAggregate());
	}

	
	public void removeData(Date date) {
		this.data.remove(date);

	}
}
