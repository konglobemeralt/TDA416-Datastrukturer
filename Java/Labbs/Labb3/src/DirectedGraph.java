import java.security.InvalidParameterException;
import java.util.*;

public class DirectedGraph<E extends Edge> {

	private List<E>[] nodeArray;

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
//			return;
			System.out.println("Adding duplicate edge: " + e.toString());
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
	private E getEdge(int from, int to, double weight) {
		Iterator<E> iterator = nodeArray[from].listIterator();
		while(iterator.hasNext()) {
			E next = iterator.next();
			if(next.getDest() == to && next.getWeight() == weight) {
				return next;
			}
		}
		return null;
	}


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

				//TODO implement rest of algorithm
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

	private List<E>[] createListArray(int length) {
		List<E>[] listArray = new LinkedList[length];
		for(int i = 0; i < length; i++) {
			listArray[i] = new LinkedList<E>();
		}
		return listArray;
	}

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
