import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

/**
 * Created by Jesper on 2018-01-21.
 */
public class ExerciseOne {

    private int coolArray [];

    public ExerciseOne(){
        int coolArray []= new int [10];
    }

    public void insertElement(int[] array, int index, int element){

        if(index > array.length ||  index < 0){
            System.out.print("Index to large or too small for array.");
        }
        else{
            for(int i = array.length; i >= index; i-- ){
                array[i] = array[i-1];
            }
            array[index] = element;
        }


    }

}
