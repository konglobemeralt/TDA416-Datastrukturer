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
     * @param elem The dummy element to compare to.
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
                zigZig(grandParent);
                x = grandParent;
            }

            else if(grandParent.right == parent && parent.left == x){
                zigZag(grandParent);
                x = grandParent;
            }

            else if(grandParent.right == parent && parent.right == x){
                zagZag(grandParent);
                x = grandParent;
            }

            else if(grandParent.left == parent && parent.right == x){
                zagZig(grandParent);
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
     * @param x Entry to zigZig
     */
    private void zigZig(Entry x){
        Entry y = x.left;
        Entry z = x.left.left;

        Entry D = x.right;
        Entry C = y.right;
        Entry A = z.left;
        Entry B = z.right;

        E temp = x.element;
        x.element = z.element;
        z.element = temp;

        y.right = z;
        x.right = y;

        x.left = A;
        y.left = B;
        z.left = C;
        z.right = D;

        setParent(x, A);
        setParent(y, B);
        setParent(z, C);
        setParent(z, D);
    }


    private void setParent(Entry parent, Entry child){

        if(child != null){
            child.parent = parent;
        }


    }
    /**
     * @param x Entry to zagZag
     */
    private void zagZag(Entry x){
        Entry y = x.right;
        Entry z = x.right.right;

        Entry A = x.left;
        Entry B = y.left;
        Entry C = z.left;
        Entry D = z.right;

        E temp = x.element;
        x.element = z.element;
        z.element = temp;

        y.left = z;
        x.left = y;

        x.right = D;
        y.right = C;
        z.right = B;
        z.left = A;

        setParent(x, D);
        setParent(y, C);
        setParent(z, B);
        setParent(z, A);
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

