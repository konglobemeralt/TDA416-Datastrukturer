/**
 * Created by konglobemeralt on 2018-02-13.
 */
public class SplayWithGet<E extends Comparable<? super E>>
        extends BinarySearchTree<E> implements CollectionWithGet<E>{

    @Override
    public E get(E e) {
        Entry x = find(e, root);

        // TODO Null check
        if(x == null){
            return null;
        }
        splay(x);
        return x.element;
    }

    private void splay(Entry x){

        //Todo: Node has parent. Change logic
        while( x.parent != null ){
            System.out.println("Trying to place " + x.element.toString() + " where head is" + x.parent.toString());
            Entry parent = x.parent;
            Entry grandParent = parent.parent;


            if(grandParent == null){
                if(parent.right != null && parent.right.element.compareTo(x.element) == 0){
                    zag(x.parent);
                    //System.out.println("zag");
                }
                else{
                    zig(x.parent);
                    //System.out.println("zig");

                }
            }

            //zig is Right
            //zag is Left

            else if(grandParent.left == parent && parent.left == x){
                System.out.println("ZigZag");
                zigZig(x); //Todo: fix Right Right
            }

            else if(grandParent.right == parent && parent.left == x){
                System.out.println("zigZag");
                zigZag(x); //Todo: fix Right-Left rotation
            }

            else if(grandParent.right == parent && parent.right == x){
                System.out.println("zagZag");
                zagZag(x); //Todo: fix Left Left
            }

            else if(grandParent.left == parent && parent.right == x){
                System.out.println("zagZig");
                zagZig(x); //Todo: Left-right rotation
            }
            else{
                System.out.println("Error... :( ");
                return;
            }

        }
        root = x;
        System.out.println("Done");


    }





    private void zigZag(Entry x){
        zig(x.parent);
        zag(x.parent);
    }
    private void zagZig(Entry x){
        zag(x.parent);
        zig(x.parent);
    }

    // ========== ========== ========== ==========

    /* Rotera 1 steg i hogervarv, dvs
              x'                 y'
             / \                / \
            y'  C   -->        A   x'
           / \                    / \
          A   B                  B   C
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
    private void zigZig(Entry x) {
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
    private void zagZag(Entry x) {
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

