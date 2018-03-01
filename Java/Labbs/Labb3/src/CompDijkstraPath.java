import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Data element representing a path consisting of a number of edges in a graph.
 * Each instance repesents one section and has a connection to a previous node.
 */

public class CompDijkstraPath<E extends Edge> implements Comparable<CompDijkstraPath> {


	private final E edge;
	private final CompDijkstraPath path;
	private final int node;

	public CompDijkstraPath(int node, E edge, CompDijkstraPath path) {
		this.node = node;
		this.edge = edge;
		this.path = path;
	}

	/**
	 * @return node which this path goes to
	 */
	public int getNode() {
		return node;
	}


	/**
	 * @return total weight
	 */
	public double getTotalWeight() {
		if(path != null) {
			return edge.getWeight() + path.getTotalWeight();
		}
		return getInternalWeight();
	}

	/**
	 * @return get weight of edge connecting to recent node. s
	 */
	public double getInternalWeight() {
		if(edge == null) {
			return 0;
		}
		return edge.getWeight();
	}

	@Override
	public int compareTo(CompDijkstraPath otherRoad) {
		if(getTotalWeight() > otherRoad.getTotalWeight()) {
			return 1;
		}else if(getTotalWeight() < otherRoad.getTotalWeight()) {
			return -1;
		}else {
			return 0;
		}
	}

	/**
	 * @return Gets the edges in natural order from start to finish.
	 */
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
		if(path != null) {
			path.addEdgesToList(list);
		}
	}
}
