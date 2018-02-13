/**
 * Created by konglobemeralt and isZumpo on 2018-02-08.
 *
 * @version (2018)
 * @authors (Jesper Blidkvist, Hampus Carlsson)
 */

public class SLCWithGet<E extends Comparable<? super E>> extends LinkedCollection<E> implements CollectionWithGet<E> {





    /**
     * Add the element into the list at first proper empty place
     *
     * @param element the element to be included
     * @throws NullPointerException if parameter <tt>element<tt> is null.
     * @returns true if the element is in included in the tree.
     */
    @Override
    public boolean add(E element) {
        if (element == null)
            throw new NullPointerException();
        else {

            Entry entryParent = findParent(element);

            if (entryParent != null) {
                entryParent.next = new Entry(element, entryParent.next);
            } else {
                head = new Entry(element, head);
            }
        }
        return true;
    } // add


    /**
     * Finds the parent of
     *
     * @param element the element to find the parent of
     * @returns The parent of the element
     */
    Entry findParent(E element) {

        Entry pointer = head;
        Entry prev = null;

        while (pointer != null) {

            if (element.compareTo(pointer.element) < 0 || element.compareTo(pointer.element) == 0) {
                return prev;
            }
            prev = pointer;
            pointer = pointer.next;
        }

        return prev;
    }


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
        Entry pointer = head;

        while (pointer != null) {
            if (e.compareTo(pointer.element) == 0) {
                return pointer.element;
            }
            pointer = pointer.next;
        }
        return null;
    }
}
