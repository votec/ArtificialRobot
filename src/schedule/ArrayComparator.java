package schedule;

import java.util.Comparator;
import java.util.List;

/**
 * Compare the first item of list.
 * @author fraenklt
 *
 */
public class ArrayComparator implements Comparator<List<Integer>> {

	@Override
	public int compare(List<Integer> o1, List<Integer> o2) {

		if (o1.get(0) < o2.get(0)) {
			return -1;
		}
		if (o1.get(0) > o2.get(0)) {
			return 1;
		}
		if (o1.get(0) == o2.get(0)) {
			return 0;
		}
		return 0;
	}

}
