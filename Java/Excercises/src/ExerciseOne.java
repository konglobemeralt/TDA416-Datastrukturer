import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

/**
 * Created by Jesper on 2018-01-21.
 */
public class ExerciseOne {

    public void insertElement(int[] array, int index, int element){

        if(index > array.length ||  index < 0){
            System.out.print("Index to large or too small for array.");
        }
        else{
            for(int i = array.length-1; i > index; i-- ){
                array[i] = array[i-1];
            }
            array[index] = element;
        }
    }

    public void runExcercise(){
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        System.out.println("Array before: " + Arrays.toString(array));
        insertElement(array, 0, 23);
        System.out.println("Array after: " + Arrays.toString(array));
    }

}
