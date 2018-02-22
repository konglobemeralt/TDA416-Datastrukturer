import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DirectedGraph<E extends Edge> {

	private LinkedList<E>[] nodeArray;

	public DirectedGraph(int noOfNodes) {
		if(noOfNodes <= 0) {
			throw  new InvalidParameterException("Amount of nodes has to be larger than 0: is " + noOfNodes);
		}

		nodeArray = new LinkedList[noOfNodes];

		// Fill array with LinkedLists
		for(int i = 0; i < noOfNodes; i++) {
			nodeArray[i] = new LinkedList<E>();
		}


	}

	public void addEdge(E e) {
		if(e == null) {
			throw new NullPointerException("Edge can't be null");
		}
		if(e.to < 0|| e.from < 0 | e.to >= nodeArray.length | e.from >= e.to) {
			throw new InvalidParameterException("Edge is referencing to node not in the graph");
		}
		if(nodeArray[e.from].contains(e)) {
			throw new InvalidParameterException("Edge is already in graph");
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
	private E getEdge(int from, int to) {
		Iterator<E> iterator = nodeArray[from].listIterator();
		while(iterator.hasNext()) {
			E next = iterator.next();
			if(next.getDest() == to) {
				return next;
			}
		}
		return null;
	}


	/**
	 * A way to convert from the road to edges
	 * @param road DijkstraRoad to convert into its inner edges
	 * @return A list of edges
	 */
	private ArrayList<E> convertDijkstraRoadToEdges(DijkstraRoad road) {
		if(road == null) {
			return null;
		}
		int previousNode = road.getNode();
		ArrayList<E> edges = new ArrayList<>();
		road = road.getRoad();

		// The creation of edges is done backwards
		while(road != null) {
			edges.add(getEdge(road.getNode(), previousNode));
			previousNode = road.getNode();
			road = road.getRoad();
		}

		return edges;
	}

	public Iterator shortestPath(int from, int to) {
		boolean[] visited = new boolean[getNumNodes()];
		PriorityQueue<DijkstraRoad> priorityQueue = new PriorityQueue<>();

		// Add first element
		priorityQueue.add(new DijkstraRoad(from, 0, null));


		while(priorityQueue.size() > 0) {
			DijkstraRoad road = priorityQueue.poll();

			if(visited[road.getNode()]) {
				// If endpoint is reached, stop
				if(road.getNode() == to) {
					return convertDijkstraRoadToEdges(road).iterator();
				}

				visited[road.getNode()] = true;

				//TODO implement rest of algorithm

			}
		}

		return null;
	}

	public Iterator minimumSpanningTree() {
		return null;
	}

}
