import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
// EH 2015 01 22
// EH 2017 01 26
public class Main {

	/**
	* Draws a line between every point in the list.
	* @param it an iterator over the löist to draw
	* @param shape the graphics area tu draw in
	* @param c The color to draw in
	* @param layer draw in base layer (true) or overlay layer (false)
	*/
	public static void drawShape(Iterator it, DrawGraph shape, Color c, boolean layer) {
		DrawGraph.Layer l = null;
		if (layer) {
			l = DrawGraph.Layer.BASE;
		} else {
			l = DrawGraph.Layer.OVERLAY;
		}
		shape.clearLayer(l);
		Point p1 = (Point) it.next(); // assuming at least two points
		while (it.hasNext()) {
			Point p2 = (Point) it.next();
			shape.drawLine((int)p1.getX(), (int)p1.getY(), 
							(int)p2.getX(), (int)p2.getY(), 
							 c, 2.0, l);
			p1 = p2;
			// System.out.println( (int)p1.getX()+" "+ (int)p1.getY()+" "+ 
						//(int)p2.getX()+" "+ (int)p2.getY()); // debug
		}
		shape.repaint();
	}
	
	
	public static void main(String[] args) {

		DLList outline; // the double linked list
		boolean debug = false;
		int j = -1; // count arguments
		int k = 8; // the number of points in list after take away
		
		// take care of flags								 
		while  ( j+1 < args.length && args[j+1].charAt(0) == '-' )  {
			j = j+1;
			switch ( args[j].charAt(1) ) {
				case 'k': {
					try {
						k = Integer.parseInt( args[j].substring(2) );
					}
					catch ( NumberFormatException e ) {
						System.out.println("main: k must have numerical argument");
						System.exit(0);
					}
				}
				break;
				case 'd' : {
					debug = true;
				}
				break;
				case 'h': {
					System.out.println();
					System.out.println("usage: java Main [-kdh] < fig1.txt");
					System.out.println("-k: k is the number of points to leave in the list");
					System.out.println("-d: d turns on debug mode => lots of printout");
					System.out.println("-h: h gives this printout");
					System.out.println();
					System.exit(0);
				}
				break;
				/*
				case 'w' : {
					try {
						w = Integer.parseInt( args[j].substring(2) );
					}
					catch ( NumberFormatException e ) {
						System.out.println("main: w must have numerical argument");
						System.exit(0);
					}
				}
				break;
				case 'h' : {
					try {
						h = Integer.parseInt( args[j].substring(2) );
					}
					catch ( NumberFormatException e ) {
						System.out.println("main: h must have numerical argument");
						System.exit(0);
					}
				}
				break;
				*/
				default : 
					System.err.println("main: unknown flag: " + args[j]);
					System.exit(0);   // avsluta
			} // end switch
		} // end loop;
		// kolla argument
		if (k<2) {
			System.err.println("main: k<2" );
			System.exit(0);   // avsluta
		}
		// ============================================================
		outline = new DLList(); // create a DL list
		
		// read constant points to linked list
		//size = outline.readFakeShape();
		// read from user/file
		if (debug) System.out.println("*** reading points from user");
		outline.readShape(); 
		// create graphics area
		int x = outline.getMaxX();
		int y = outline.getMaxY();
		if (debug) System.out.println("*** maximum x coordinate is: " + x + " maximum y coordinate is: " + y);
		DrawGraph shape = new DrawGraph(x+10, y+10);
		Iterator it = outline.iterator();
		if (debug) System.out.println("************ this is the original list:");
		if (debug) System.out.println(outline.toString());
		if (debug) System.out.println("************ draws original shape in black in graphics area");
		drawShape(it, shape, Color.BLACK, true);
		outline.reduceListToKElements(k);
		if (debug) System.out.println("************ this is the list after remove to k points:");
		if (debug) System.out.println(outline);
		/*
		try{ // take a breake - oförklarliga timing problems med uppritningen
			outline.wait(1000);
		} catch (Exception e ) {
		}
			*/
		if (debug) System.out.println("********** draws generated shape with k points in red");
		it = outline.iterator();
		drawShape(it, shape, Color.RED, false);
		if (debug) System.out.println();
	}
}
