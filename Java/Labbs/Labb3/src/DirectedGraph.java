import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.LinkedList;

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

	public Iterator shortestPath(int from, int to) {
		return null;
	}

	public Iterator minimumSpanningTree() {
		return null;
	}

}
