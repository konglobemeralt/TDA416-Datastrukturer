/**
 * Created by konglobemeralt and isZumpo on 2018-02-08.
 *
 * @version (2018)
 * @authors (Jesper Blidkvist, Hampus Carlsson)
 */

public class SplayWithGet<E extends Comparable<? super E>>
        extends BinarySearchTree<E> implements CollectionWithGet<E>{

    /**
     * Find the first occurence of an element
     * in the collection that is equal to the argument
     * <tt>e</tt> with respect to its natural order.
     * I.e. <tt>e.compateTo(element)</tt> is 0.
     *
     * @param e The dummy element to compare to.
     * @return An element  <tt>e'</tt> in the collection
     * satisfying <tt>e.compareTo(e') == 0</tt>.
     * If no element is found, <tt>null</tt> the parent is
     * returned
     */
    @Override
    protected Entry find( E elem, Entry t ) {
        if ( t == null )
            return null;
        else {
            int jfr = elem.compareTo( t.element );
            if ( jfr  < 0 ) {
                if(t.left == null){
                    return t;
                }
                return find(elem, t.left);
            }
            else if ( jfr > 0 ) {
                if(t.right == null){
                    return t;
                }
                return find(elem, t.right);
            }
            else
                return t;
        }
    }  //   find


    /**
     * Find the first occurence of an element
     * in the collection that is equal to the argument
     * <tt>e</tt> with respect to its natural order.
     * I.e. <tt>e.compateTo(element)</tt> is 0.
     *
     * @param e The dummy element to compare to.
     * @return An element  <tt>e'</tt> in the collection
     * satisfying <tt>e.compareTo(e') == 0</tt>.
     * If no element is found, <tt>null</tt> is returned
     */
    @Override
    public E get(E e) {
        if(e == null) {
            return null;
        }
        Entry x = find(e, root);

        if(x == null){
            return null;
        }

        E xElement = x.element;

        splay(x);

        if(e.compareTo(xElement) != 0) {
            return null;
        }

        return xElement;
    }

    /**
     * @param x Entry to splay
     */
    private void splay(Entry x){
        while( x.parent != null ){
            Entry parent = x.parent;
            Entry grandParent = parent.parent;

            //zig is Right
            //zag is Left

            if(grandParent == null){
                if(parent.right == x){
                    zag(parent);
                    x = parent;
                }
                else{
                    zig(parent);
                    x = parent;
                }
            }
            else if(grandParent.left == parent && parent.left == x){
                System.out.println("zigZig");
                zigZig(x); //Todo: fix Right Right
                x = grandParent;
            }

            else if(grandParent.right == parent && parent.left == x){
                System.out.println("zigZag");
                zigZag(grandParent); //Todo: fix Right-Left rotation
                x = grandParent;
            }

            else if(grandParent.right == parent && parent.right == x){
                System.out.println("zagZag");
                zagZag(x); //Todo: fix Left Left
                x = grandParent;
            }

            else if(grandParent.left == parent && parent.right == x){
                System.out.println("zagZig");
                zagZig(grandParent); //Todo: Left-right rotation
                x = grandParent;
            }
            else{
                System.out.println("Error... :( ");
                return;
            }

        }
        root = x;
    }

    /**
     * @param x Entry to zigZag
     */
    private void zigZig(Entry x){
        zig(x.parent.parent);
        zig(x.parent);
    }
    /**
     * @param x Entry to zagZag
     */
    private void zagZag(Entry x){
        zag(x.parent.parent);
        zag(x.parent);
    }

    // ========== ========== ========== ==========

    /* Rotera 1 steg i hogervarv, dvs
              x'                 y'
             / \                / \
            y'  C   -->        A   x'
           / \                    / \
          A   B                  B   C
    */
    /**
     * @param x Entry to zig
     */
    private void zig(Entry x) {
        Entry y = x.left;
        E temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.left = y.left;
        if (x.left != null)
            x.left.parent = x;
        y.left = y.right;
        y.right = x.right;
        if (y.right != null)
            y.right.parent = y;
        x.right = y;

    }
    //   rotateRight
    // ========== ========== ========== ==========

    /* Rotera 1 steg i vanstervarv, dvs
              x'                 y'
             / \                / \
            A   y'  -->        x'  C
               / \            / \
              B   C          A   B
    */
    /**
     * @param x Entry to zag
     */
    private void zag(Entry x) {
        Entry y = x.right;
        E temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.right = y.right;
        if (x.right != null)
            x.right.parent = x;
        y.right = y.left;
        y.left = x.left;
        if (y.left != null)
            y.left.parent = y;
        x.left = y;
    }
    //   rotateLeft
    // ========== ========== ========== ==========

    /* Rotera 2 steg i hogervarv, dvs
              x'                  z'
             / \                /   \
            y'  D   -->        y'    x'
           / \                / \   / \
          A   z'             A   B C   D
             / \
            B   C
    */
    /**
     * @param x Entry to zagZig
     */
    private void zagZig(Entry x) {
        Entry y = x.left,
                z = x.left.right;
        E e = x.element;
        x.element = z.element;
        z.element = e;
        y.right = z.left;
        if (y.right != null)
            y.right.parent = y;
        z.left = z.right;
        z.right = x.right;
        if (z.right != null)
            z.right.parent = z;
        x.right = z;
        z.parent = x;
    }
    //  doubleRotateRight
    // ========== ========== ========== ==========

    /* Rotera 2 steg i vanstervarv, dvs
               x'                  z'
              / \                /   \
             A   y'   -->       x'    y'
                / \            / \   / \
               z   D          A   B C   D
              / \
             B   C
     */
    /**
     * @param x Entry to zigZag
     */
    private void zigZag(Entry x) {
        Entry y = x.right,
                z = x.right.left;
        E e = x.element;
        x.element = z.element;
        z.element = e;
        y.left = z.right;
        if (y.left != null)
            y.left.parent = y;
        z.right = z.left;
        z.left = x.left;
        if (z.left != null)
            z.left.parent = z;
        x.left = z;
        z.parent = x;
    }
    //  doubleRotateLeft
    // ========== ========== ========== ==========
}

