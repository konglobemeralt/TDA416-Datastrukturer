import java.util.Comparator;

public class CompKruskalEdge<E extends Edge> implements Comparator<E> {
	@Override
	public int compare(E o1, E o2) {
		if(o1 == null || o2 == null) {
			throw new NullPointerException("Can't compare with null");
		}
		if(o1.getWeight() > o2.getWeight()) {
			return 1;
		}else if(o1.getWeight() < o2.getWeight()) {
			return -1;
		}else {
			return 0;
		}
	}
}
