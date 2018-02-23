import java.security.InvalidParameterException;
import java.util.*;

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
		if(nodeArray[e.from].contains(e)) {
//TODO			throw new InvalidParameterException("Edge " + e.toString() +" is already in graph with size " + getNumNodes());
			return;
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

		// Return reversed road
		Collections.reverse(edges);
		return edges;
	}

	public Iterator shortestPath(int from, int to) {
		boolean[] visited = new boolean[getNumNodes()];
		PriorityQueue<DijkstraRoad> priorityQueue = new PriorityQueue<>();

		// Add first element
		priorityQueue.add(new DijkstraRoad(from, 0, null));


		while(priorityQueue.size() > 0) {
			DijkstraRoad road = priorityQueue.poll();

			if(!visited[road.getNode()]) {
				// If endpoint is reached, stop
				if(road.getNode() == to) {
					return convertDijkstraRoadToEdges(road).iterator();
				}

				visited[road.getNode()] = true;

				//TODO implement rest of algorithm
				for(E edge : getNeighboursOfNode(road.getNode())) {
					if(!visited[edge.to]) {
						priorityQueue.add(new DijkstraRoad(edge.to, edge.getWeight(), road));
					}
				}
			}
		}

		System.out.println("RETURNING NULL!!!!!!!");
		return null;
	}

	public Iterator minimumSpanningTree() {
		return null;
	}

}
