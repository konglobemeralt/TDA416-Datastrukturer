/**
 * Created by konglobemeralt on 2018-02-08.
 */
public class SLCWithGet <E extends Comparable<? super E>> extends LinkedCollection implements CollectionWithGet<E> {

    @Override
    public boolean add( E element ) {
        try {
            if ( element == null )
                throw new NullPointerException();
            else {

                Entry entryParent = findParent(element);

                if(entryParent != null){
                    entryParent.next = new Entry(element, entryParent.next);
                }
                else{
                    head = new Entry(element, head );
                }
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    } // add


    Entry findParent(E element) throws IllegalArgumentException{

        Entry pointer = head;
        Entry prev = null;

        while(pointer != null ){
            if(element.compareTo(pointer.element) == 0){
                throw new IllegalArgumentException("Already in list..." + element.toString());
            }
            else if(element.compareTo(pointer.element) < 0){
                return prev;
            }
            prev = pointer;
            pointer = pointer.next;
        }

        return prev;
    }

    E get(E element){

        Entry pointer = head;

        while(pointer != null ){
            if(element.compareTo(pointer.element) == 0){
                return pointer.element;
            }

            pointer = pointer.next;
        }

        return prev;

    }
}
