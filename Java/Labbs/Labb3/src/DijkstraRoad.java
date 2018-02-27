import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraRoad<E extends Edge> implements Comparable<DijkstraRoad> {


	private final E edge;
	private final DijkstraRoad road;
	private final int node;

	public DijkstraRoad(int node, E edge, DijkstraRoad road) {
		this.node = node;
		this.edge = edge;
		this.road = road;
	}

	/**
	 * @return node which this road goes to
	 */
	public int getNode() {
		return node;
	}


	/**
	 * @return total weight
	 */
	public double getTotalWeight() {
		if(road != null) {
			return edge.getWeight() + road.getTotalWeight();
		}
		return getInternalWeight();
	}

	public double getInternalWeight() {
		if(edge == null) {
			return 0;
		}
		return edge.getWeight();
	}

	@Override
	public int compareTo(DijkstraRoad otherRoad) {
		if(getTotalWeight() > otherRoad.getTotalWeight()) {
			return 1;
		}else if(getTotalWeight() < otherRoad.getTotalWeight()) {
			return -1;
		}else {
			return 0;
		}
	}

	public DijkstraRoad getRoad() {
		return road;
	}

	public  List<E> getEdges() {
		List<E> edges = new ArrayList<E>();
		addEdgesToList(edges);
		Collections.reverse(edges);
		return edges;
	}

	private void addEdgesToList(List<E> list) {
		if(edge == null) {
			return;
		}
		list.add(edge);
		if(road != null) {
			road.addEdgesToList(list);
		}
	}
}
