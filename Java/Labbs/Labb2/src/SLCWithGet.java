/**
 * Created by konglobemeralt on 2018-02-08.
 */
public class SLCWithGet<E extends Comparable<? super E>> extends LinkedCollection<E> implements CollectionWithGet<E> {

    public SLCWithGet() {
        super();
    }

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


    Entry findParent(E element) throws IllegalArgumentException {

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
