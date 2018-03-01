import java.security.InvalidParameterException;
import java.util.*;

public class DirectedGraph<E extends Edge> {

	private List<E>[] nodeArray;

	public DirectedGraph(int noOfNodes) {
		if(noOfNodes <= 0) {
			throw  new InvalidParameterException("Amount of nodes has to be larger than 0: is " + noOfNodes);
		}

		nodeArray = createListArray(noOfNodes);
	}

	private List<E> getNeighboursOfNode(int node) {
		return nodeArray[node];
	}

	public void addEdge(E e) {
		if(e == null) {
			throw new NullPointerException("Edge can't be null");
		}
		if(e.to < 0|| e.from < 0 || e.to >= nodeArray.length || e.from >= nodeArray.length ) {
			throw new InvalidParameterException("Edge" + e.toString() + " is referencing to node not in the graph with size: " + getNumNodes());
		}
		nodeArray[e.from].add(e);
	}

	private int getNumNodes() {
		return nodeArray.length;
	}

	/**
	 *
	 * @param from Node to go from
	 * @param to Node to go to
	 * @return Edge going from 'from' to 'to'
	 */


	public Iterator shortestPath(int from, int to) {
		boolean[] visited = new boolean[getNumNodes()];
		PriorityQueue<CompDijkstraPath> priorityQueue = new PriorityQueue<>();

		// Add first element
		priorityQueue.add(new CompDijkstraPath(from, null, null));


		while(priorityQueue.size() > 0) {
			CompDijkstraPath road = priorityQueue.poll();

			if(!visited[road.getNode()]) {
				// If endpoint is reached, stop
				if(road.getNode() == to) {
					return road.getEdges().iterator();
				}

				visited[road.getNode()] = true;

				for(E edge : getNeighboursOfNode(road.getNode())) {
					if(!visited[edge.to]) {
						priorityQueue.add(new CompDijkstraPath(edge.to, edge, road));
					}
				}
			}
		}
		return null;
	}

	public Iterator minimumSpanningTree() {
		List<E>[] cc = createListArray(getNumNodes());
		int ccLength = cc.length;

		PriorityQueue<E> priorityQueue = new PriorityQueue<>(new CompKruskalEdge<E>());

		// Add to priority queue from nodeArray
		for(int i = 0; i < cc.length; i++) {
			priorityQueue.addAll(getNeighboursOfNode(i));
		}

		while(!priorityQueue.isEmpty() && ccLength > 1) {
			E edge = priorityQueue.poll();

			if(cc[edge.from] != cc[edge.to]) {
				if(cc[edge.from].size() <= cc[edge.to].size()) {
					cc[edge.to].add(edge);
					mergeCCNodes(cc, edge.from, edge.to);
				} else {
					cc[edge.from].add(edge);
					mergeCCNodes(cc, edge.to, edge.from);
				}
				ccLength--;
			}
		}
		return cc[0].iterator();
	}

	/**
	 * Creates a LinkedList<E>[] array, fills it with empty lists and returns it as a List<E>[]
	 * @param length of array to create
	 * @return filled List<E>
	 */
	private List<E>[] createListArray(int length) {
		List<E>[] listArray = new LinkedList[length];
		for(int i = 0; i < length; i++) {
			listArray[i] = new LinkedList<E>();
		}
		return listArray;
	}

	/**
	 * @param cc cc array containing lists
	 * @param from node to move all edges from
	 * @param to node to move all edges to
	 */
	private void mergeCCNodes(List<E> cc[], int from, int to) {
		cc[from].addAll(cc[to]);
		List<E> toChange = cc[to];
		for(int i = 0; i < cc.length; i++) {
			if(cc[i] == toChange) {
				cc[i] = cc[from];
			}
		}
		cc[to] = cc[from];
	}

}
