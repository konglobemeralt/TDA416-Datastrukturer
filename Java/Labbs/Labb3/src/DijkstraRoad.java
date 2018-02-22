import java.util.ArrayList;
import java.util.Iterator;

public class DijkstraRoad implements Comparable<DijkstraRoad> {


	private final double weight;
	private final DijkstraRoad road;
	private final int node;

	public DijkstraRoad(int node, double weight, DijkstraRoad road) {
		this.node = node;
		this.weight = weight;
		this.road = road;
	}

	public int getNode() {
		return node;
	}

	public double getWeight() {
		if(road != null) {
			return weight + road.getWeight();
		}
		return weight;
	}

	@Override
	public int compareTo(DijkstraRoad otherRoad) {
		if(getWeight() > otherRoad.getWeight()) {
			return 1;
		}else if(getWeight() < otherRoad.getWeight()) {
			return -1;
		}else {
			return 0;
		}
	}

	public DijkstraRoad getRoad() {
		return road;
	}

	//	private ArrayList<Edge> getEdges() {
//		if(road == null) {
//			return new Edge()
//		}
//	}
//
//	public Iterator<Edge> getIterator() {
//		return new Iterator<Edge>() {
//
//			private int currentEdge
//
//			@Override
//			public boolean hasNext() {
//				return currentIndex < currentSize && arrayList[currentIndex] != null;
//			}
//
//			@Override
//			public Type next() {
//				return arrayList[currentIndex++];
//			}
//
//			@Override
//			public void remove() {
//				throw new UnsupportedOperationException();
//			}
//		};
//	}
}
