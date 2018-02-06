// new version 2018 with several possible printouts of trees
// NOTE: you can adjust the output of the toString method i.e. how to print trees on line 420-
// not in the comments here
// Primitiv, I know.
/*
// ========= ========= ========= ========= ========= =========
// Different forms of printing binary trees
// Approx. at line 410 is the toString method for BinarySearchTree
// it returns the tree in preorder or bfs
@Override
public String toString() {
	// ========= ========= ========= ========= ========= =========
	// ========= ========= ========= ========= ========= =========
	// ========= ========= ========= ========= ========= =========
	// ========= ========= ========= ========= ========= =========
	// ************ ADJUST to your preferences ************
	// a state variable that determine what kind of printout is returned
	boolean simple = true; 		// true => style is [ 1 2 3 4 5 6 ]
								// i.e. only content in preorder, no shape
								// false => try value of preorder
	boolean preorder = true;	// true => preorder, see below
								// false => bfs order, see below
								// see documentation below
	// leftBalance and debug only work on bfs 
	// leftbalance and debug is a good combination
	// !leftbalance and !debug is a good combination
	boolean leftBalance = false;	
		// true => each row of the tree start to the left,
		// use for big trees and if tree content is long like a list
		// false => root in the middle, max depth 5-6 or so
	boolean debug = false;	// true => printout with position like < 2+ 4+120>
							// which is an entry on depth 2 and "column" 4 in the tree
							// false => printout like <120>
	// ========= ========= ========= ========= ========= =========

/*
preOrder Traverse returns the tree in the form:
100 : 
   50 : 100
   |  25 : 50
   |  |  10 : 25
   |  |  30 : 25
   |  75 : 50
   150 : 100
   |  125 : 150
   |  175 : 150
here 50 and 150 are children of 100 and so on. Parents are also written after the ":" sign.
*/

/*
bfs Traverse returns the tree as a bredth first search with different formats.

**** one liner är borkommenterad i koden
one liner: the first two number is the nodes position in the tree (depth, width)
	<1+1+100>  <2+1+50>  <2+2+150>  <3+1+25>  <3+3+125>  <3+4+175>  <4+5+120> 
*** leftbalance and !debug:
	< 100>
	<  50> < 150>
	<  25>   --   < 125> < 175>
*** leftbalance and debug: (first number is level and second number is bfs numbering, third is content)
	< 1+ 1+ 100>
	< 2+ 1+  50> < 2+ 2+ 150>
	< 3+ 1+  25> < 3+ 2+null> < 3+ 3+ 125> < 3+ 4+ 175>
*** !leftbalance and !debug (only content but structured as a tree)
                                         < 100> 
              <  50>                                                  < 150> 
<  25>                        --                        < 125>                      < 175> 
		
*/


import java.util.*;

/**
 *  A simple binary searchtree ordered by the
 *  method compareTo for the elements.
 * @author (Bror Bjerner) 
 * @version (2010)
 * @author EH
 * @version (2018) toString, preorder traversal, bf traversal
 * printing
   old: findRefToMostRight, removeLeaf, removeThis
   new: removeThis, liftRightSubtree, swapWithRightMostInLeftTree

 */

public class BinarySearchTree<E extends Comparable<? super E>>
							extends AbstractCollection<E>
							implements Iterable<E>, Cloneable {

	protected Entry root;
	protected int   size;
	// ========== ========== ========== ==========
	protected  class Entry  {

		public E      element;
		public Entry  left, right, parent;

		public Entry( E element,
					  Entry  left, 
					  Entry  right,
					  Entry  parent ) {

			this.element = element; 
			this.left    = left; 
			this.right   = right; 
			this.parent  = parent; 

		} //  constructor Entry

		public  Entry( E element, Entry parent) {
			this( element, null, null, parent );
		} //  constructor Entry
		
		//@Override
		public String toString() {
			//return element.toString();
			//return String.valueOf(element); // klarar även null
			if (element==null) {
				 return "****";
			} else {
				return String.valueOf(element);
			}
		}
		
		/*
		//@Override
		public String toString() {
			return (root.left == null ? "null" : (root.left).toString()) + "||||" + (root.right == null ? "null" : (root.right).toString());
		}
		*/
	} //  class  Entry
	// ========== ========== ========== ==========

	/**
	*  The constructor creates the empty tree.
	*/
	public BinarySearchTree( ) {
		super();
		root = null;
		size = 0;
	}  // constructor BinarySearchTree
	// ========== ========== ========== ==========
	/**
	* The number of objects in this collection.
	* @return the number of elements in the tree. 
	*/
	public int size() {
		return size;
	}  // size
	// ========== ========== ========== ==========
	protected void addIn(E newElem, Entry t) {
		// dubletter borde kanske inte tillåtas
		int comp = newElem.compareTo( t.element);
		if ( comp < 0 ) {
			if ( t.left == null ) { // här behöver vi titta ner
				t.left = new Entry( newElem, t);
			} else {
				addIn( newElem, t.left );
			}
		} else if ( comp > 0 ) {
			if ( t.right == null ) {
				t.right = new Entry( newElem, t);
			} else {
				addIn( newElem, t.right );
			}
		} else {
			size--; // Update do nothing
					// update => E has to have a update method
		}
	}  //  addIn
	// ========== ========== ========== ==========
	/**
	* Add the element into the three at first proper empty place
	* @param o the element to be included  
	* @returns true if the element is in included in the tree.
	*/
	public boolean add( E elem ) {
		if ( root == null ) {
			root = new Entry( elem, null );
		} else {
			addIn( elem, root );
		}
		size++;
		return true; 
	} // add
	// ========== ========== ========== ==========
	
	protected Entry find( E elem, Entry t ) {
		if ( t == null )
			return null;
		else {
			int jfr = elem.compareTo( t.element );
			if ( jfr  < 0 )
				return find( elem, t.left );
			else if ( jfr > 0 )
				return find( elem, t.right );
			else 
				return t;
		}
	}  //   find
	// ========== ========== ========== ==========

	/**
	* Check if the element is in the the tree.
	* @param elem The element to check
	* @returns true if the element is contained in the tree,
	*          otherwise false is returned.  
	*/ 
	public boolean contains( E elem ) {
		return find( elem, root ) != null;
	}  // contains 
	// ========== ========== ========== ==========
	/**
	* Removes all of the elements from this tree
	*/ 
	public void clear() {
		root = null;
		size = 0;   
	}   //  clear
	// ========== ========== ========== ==========
	/*    protected Entry findRefToMostRight( Entry t ) {
		if ( t.right == null )
		return t;
		else 
		return findRefToMostRight( t.right );
		}  //   findRefToMostRight

		protected void removeLeaf( Entry leaf, Entry parent ) {
		if ( parent == null )
		root = null;
		else if ( parent.left == leaf )
		parent.left = null;
		else
		parent.right = null;
		} // removeLeaf
		*/
		// old: findRefToMostRight, removeLeaf, removeThis
		// new: removeThis, liftRightSubtree, swapWithRightMostInLeftTree
	// ========== ========== ========== ==========
	protected void removeThis( Entry t ) {
		if ( t.left == null )
			liftRightSubtree(t);
		else
			swapWithRightMostInLeftTree(t);
	}  // removeThis
	// ========== ========== ========== ==========

	protected void liftRightSubtree( Entry t ) {
		// Note that l.left is null
		if ( t.right != null )
			t.right.parent = t.parent; 
		if ( t.parent == null )
			root = t.right;
		else if ( t.parent.left == t )
			t.parent.left = t.right;
		else 
			t.parent.right = t.right;
	} // liftRightSubtree
	// ========== ========== ========== ==========
	protected void swapWithRightMostInLeftTree( Entry t ) {
		if ( t.left.right == null ) {
			t.element = t.left.element;
			t.left = t.left.left;
			if ( t.left != null )
				t.left.parent = t;
		}
		else {
			Entry p = t.left;
			while ( p.right.right != null )
				p = p.right;
			t.element = p.right.element;
			p.right = p.right.left;
			if ( p.right != null )
				p.right.parent = p;
		}
	} // swapWithRightMostInLeftTree
	// ========== ========== ========== ==========
	/*    protected void removeThis( Entry t ) {
	if ( t.left == null )
	if ( t.right == null )
	removeLeaf( t, t.parent );
	else {
	t.element = t.right.element;
	t.left    = t.right.left;
	if ( t.left != null )
	t.left.parent = t;
	t.right   = t.right.right;
	if ( t.right != null )
	t.right.parent = t;
	}
	else {
	Entry bytEntry = findRefToMostRight( t.left );
	t.element = bytEntry.element;
	if (bytEntry == t.left ) {
	t.left = bytEntry.left;
	if (t.left != null)
	t.left.parent = t;
	}
	else {
	bytEntry.parent.right = bytEntry.left;
	if ( bytEntry.left != null )
	bytEntry.left.parent = bytEntry.parent;
	}
	}
	size--;
	}  // removeThis
	*/
	// ========== ========== ========== ==========
	/**
	* Remove the first occurance of an element for which 
	* compareTo with the argument yields 0. If no element 
	* is removed false is returned, otherwise true.  
	* @param elem element of Comarable
	* @return true if the tree has changed, otherwise false.
	*/
	public boolean remove( E elem ) {
		Entry remElem = find( elem, root );
		if ( remElem == null )
			return false;
		else {
			removeThis( remElem );
			--size;
			return true;
		}
	}  // remove
	// ========== ========== ========== ==========
	/**
	* Create an iterator for elements in the tree in preorder.
	* @return the created iterator.
	*/
	public Iterator<E> iterator() {
		boolean preorder = true;
		boolean inorder = false;
		if (preorder) {
			return new BSTPre_Iterator();
		} else if (inorder) {
			; // return new BSTIn_Iterator();
		}
		return null;
	}  //  iterator
	// ========== ========== ========== ==========
	// An inner class to create an iterator for 
	// the collection of elements in preorder.
	protected class BSTPre_Iterator implements Iterator<E> {
		private Stacks<Entry> nextOnTop  = new LinkedStack<Entry>();
		private Entry         lastNext   = null;

		protected BSTPre_Iterator() {
			for ( Entry p = root; p != null; p = p.left )
				nextOnTop.push( p ); 
		} // constructor BST_Iterator 

		public boolean hasNext() {
			return ! nextOnTop.isEmpty();
		} // hasNext

		public E next() {
			lastNext =  nextOnTop.pop();
			// throws NoSuchElementException if empty
			for ( Entry p = lastNext.right; p != null; p = p.left )
				nextOnTop.push( p );
			return lastNext.element;
		} // next

		public void remove() {
			if ( lastNext != null ) {
				removeThis( lastNext );
				lastNext = null;
			} 
			else
				throw new IllegalStateException();    
		} // remove
	}  //  classBST_Iterator

	// ========= ========= ========= ========= ========= =========
	// ========= ========= ========= ========= ========= =========
	// ========= ========= ========= ========= ========= =========
	// some simple helpers
	/**
	* Right adjusts a string or number in a space of "i" chars.
	* @param s the string to adjust
	* @param i the minimal size of the resulting string
	* @return a String at least "i" characters long
	*/
	private static String adjustForSpace(String s, int i) {
		if (i>s.length()) {
			return chars(i-s.length(), ' ') + s;
		} else {
			return s;
		}
	}
	/**
	* creates a string with nbr c's
	*/
	private static String chars(int nbr, char c) {
		StringBuffer str = new StringBuffer(nbr);
		for ( int i=0; i<nbr; i++ ) {
			str.append(c);
		}
		return str.toString();
	}

	// ========= ========= ========= ========= ========= =========
	// Different forms of printing binary trees
	// This is the toString method for BinarySearchTree
	// it returns the tree in preorder or bfs
	@Override
	public String toString() {
		// ========= ========= ========= ========= ========= =========
		// ========= ========= ========= ========= ========= =========
		// ========= ========= ========= ========= ========= =========
		// ========= ========= ========= ========= ========= =========
		// ************ ADJUST to your preferences ************
		// a state variable that determine what kind of printout is returned
		boolean simple = true; 		// true => style is [ 1 2 3 4 5 6 ]
									// i.e. only content in preorder, no shape
									// false => trye value of preorder
		boolean preorder = true;	// true => preorder, 
									// false => bfs order
									// see documentation below
		// leftBalance and debug only work on bfs 
		// leftbalance and debug is a good combination
		// !leftbalance and !debug is a good combination
		boolean leftBalance = false;	
			// true => each row of the tree start to the left,
			// use for big trees and if tree content is long like a list
			// false => root in the middle, max depth 5-6 or so
		boolean debug = false;	// true => printout with position like < 2+ 4+120>
								// which is an entry on depth 2 and "column" 4 in the tree
								// false => printout like <120>
		// ========= ========= ========= ========= ========= =========
		// ========= ========= ========= ========= ========= =========
		// ========= ========= ========= ========= ========= =========
		// ========= ========= ========= ========= ========= =========
		StringBuilder sb = new StringBuilder();
		if (simple) {
			sb.append("[ ");
			preOrderTraverseSimple(root, sb);
			sb.append("]");
		} else if (preorder) {
			preOrderTraverse(root, 1, sb);
		} else {
			bfsTraverse(root, sb, leftBalance, debug);
		}
		return sb.toString();
	}
	// ========= ========= ========= ========= ========= =========
	/*
		Very simple preorder traversal of the tree. Only content, no shape
	*/
	private void preOrderTraverseSimple(Entry entry, StringBuilder sb){
		if (entry != null){
			sb.append(entry.element.toString() + " ");
			preOrderTraverseSimple(entry.left, sb);
			preOrderTraverseSimple(entry.right, sb);
		}
	}
	/*
	preOrderTraverse returns the tree in the form:
	100 : 
	   50 : 100
	   |  25 : 50
	   |  |  10 : 25
	   |  |  30 : 25
	   |  75 : 50
	   150 : 100
	   |  125 : 150
	   |  175 : 150
	here 50 and 150 are children of 100 and so on. Parents are also written after the ":" sign.
	*/
	private void preOrderTraverse(Entry entry, int depth, StringBuilder sb){
		if (entry == null){
			// avkommentera följande rader om du vill ha "null" utskrivet
			// men det blir typ dubbelt så många rader!
			/*
			if (depth>1) sb.append("   ");
			for (int i = 1; i < depth-1; i++) {
				sb.append("|  ");
			}
			sb.append("null\n");
			*/
		} else {
			if (depth>1) sb.append("   ");
			for (int i = 1; i < depth-1; i++) {
				sb.append("|  ");
			}
			sb.append(entry.element.toString() + " : " + ((entry.parent != null)?entry.parent.element:"") + "\n");
			// without parent if you prefer that
			//sb.append(entry.element.toString() + "\n");
			preOrderTraverse(entry.left, depth+1, sb);
			preOrderTraverse(entry.right, depth+1, sb);
		}
	}

	// This is a class for queueing elements used in bfsTraverse.
	// It contains an Entry and at what depth and what position sideways 
	// that a node is in the tree. Also a toString method that can hanle that Entry is null
	private class QElem {
		public int depth = 0;
		public int width = 0;
		public Entry entry = null;
		boolean debug = false;
		
		protected QElem (int depth, int width, Entry e, boolean debug) {
			this.depth = depth;
			this.width = width;
			this.entry = e;
			this.debug = debug;
		}
		public String toString() {
			String str = adjustForSpace(""+depth, 2) + "+" + adjustForSpace(""+width, 2) + "+";
			if (!debug) {str = "";}
			if(entry==null) {
				if(debug) return "<" + str + "null" + ">";
				return " " + str + " -- " + " ";
			} else {
				return "<" + str + adjustForSpace(""+entry,4) + ">";
			}
		}
	}

	/*
	bfsTraverse returns the tree as a bredth first search with different formats.
		
	**** one liner är borkommenterad i koden
	one liner: the first two number is the nodes position in the tree (depth, wodth)
		<1+1+100>  <2+1+50>  <2+2+150>  <3+1+25>  <3+3+125>  <3+4+175>  <4+5+120> 
	*** leftbalance and !debug:
		< 100>
		<  50> < 150>
		<  25>   --   < 125> < 175>
	*** leftbalance and debug:
		< 1+ 1+ 100>
		< 2+ 1+  50> < 2+ 2+ 150>
		< 3+ 1+  25> < 3+ 2+null> < 3+ 3+ 125> < 3+ 4+ 175>
	*** !leftbalance and !debug
                                         < 100> 
              <  50>                                                  < 150> 
<  25>                        --                        < 125>                      < 175> 
		
	*/
	private void bfsTraverse(Entry entry, StringBuilder sb, boolean leftBalance, boolean debug ){
		int maxDepth = 0;
		String[][] tree = new String[100][100];  // maximum tree depth = 100
		Queue<QElem> q = new ArrayDeque<QElem>();
		q.offer(new QElem(1, 1, entry, debug));	 // the root
		// create tree as a matrix by a dfs search
		while ( !q.isEmpty() ) {
			QElem p = q.poll();
			if (maxDepth < p.depth) {maxDepth = p.depth;}
			//if (oneliner) {
				// lägg den här noden i oneliner utskriften
				//   tag bort if testen om null noder för lövens barn skall skrivas
			//	if (p.entry!=null) sb.append(" " + p + " ");
			//} else {
				// eller lägg den i 2D utskriften
				tree[p.depth][p.width] = ""+p;
			//}
			//lägg barnen till p.entry i kön
			if (p.entry!=null) {
				q.offer(new QElem(p.depth+1, p.width*2-1, p.entry.left, debug));
				q.offer(new QElem(p.depth+1, p.width*2, p.entry.right, debug));
			}
		}
		// create a visualization of the tree
		if (leftBalance) {
			for (int row=1; row<maxDepth; row++) {
				int width = (int) Math.pow(2, (row-1));
				for (int col=1; col<=width; col++) {
					String str = tree[row][col];
					if (str != null) {
							sb.append(" " + str);
					} else {
						// since str==null, create a new QElem with null to
						// get the same formatting that was used to create str
						sb.append(" " + new QElem(row, col, null, debug));
					}
				}
				sb.append("\n\n");
				//System.out.println("space= " + space.length() + " ispace=" + ispace.length());
			}
		} else {
			for (int row=1; row<maxDepth; row++) {
				String space6 = (row==maxDepth-1)?"":"    ";
				String space13 = "       ";
				if (debug) {
					// messy is just the first name ....
					space6 = (row==maxDepth-1)?"":"      ";
					space13 = "             ";
				} 
				String space = "";
				String ispace = "";
				// calculate first indent
				for (int i = 1; i<=(int)Math.pow(2,(maxDepth-1-row-1))-1; i++) {
					ispace = ispace + space13; // use stringbuilder?
				}
				// calculate internal indent
				for (int i = 1; i<=(int)Math.pow(2,(maxDepth-1-row))-1; i++) {
					space = space + space13; // use stringbuilder?
				}
				sb.append(space6 + ispace); // first indent
				int width = (int) Math.pow(2, (row-1));
				for (int col=1; col<=width; col++) {
					String str = tree[row][col];
					if (str != null) {
							sb.append(" " + str + space);
					} else {
						// since str==null, create a new QElem with null to
						// get the same formatting that was used to create str
						sb.append(" " + new QElem(row, col, null, debug) + space);
					}
				}
				sb.append("\n\n");
			}
		}
		System.out.println("Depth is " + (maxDepth-1)); // maxDepth also count the last null-leaf
	} // end bfsTraverse

	// ========= ========= ========= ========= =========
	// for testing
	public static void main(String[] args) {
		BinarySearchTree<Integer> st = new BinarySearchTree<Integer>();
		st.add(1);
		st.add(2);
		st.add(3);
		st.add(4);
		st.add(5);
		st.add(6);
		st.add(7);
		System.out.println("st= " + st);
		System.out.print("st it= ");
		Iterator it = st.iterator();
		while(it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		System.out.println();
		
		/*
		SplayTreeWithGet<Integer> st = new SplayTreeWithGet<Integer>();
		st.add(100);
		st.add(50);
		st.add(150);
		st.add(25);
		//st.add(75);

		st.add(125);
		st.add(175);
		st.add(120);
		st.add(110);
		/*
		st.add(10);
		st.add(30);
		/*
		st.add(60);
		st.add(80);
		st.add(110);
		st.add(130);
		st.add(160);
		st.add(180);
		
		System.out.println("Tree before:");
		System.out.println(st);
	
		st.get(75);
	
		System.out.println("Tree after get(75)");
		System.out.println(st);
		*/
	} // end main
	
}  //  class BinarySearchTree
