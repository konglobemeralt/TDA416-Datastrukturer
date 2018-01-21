import AssignmentOne.ExerciseOne;
import AssignmentOne.ExerciseTwo;

import java.io.IOException;

/**
 * Created by Jesper on 2018-01-21.
 */
public class Main{
    public static void main(String [ ] args)
    {
       //ExerciseOne exOne = new ExerciseOne();
       //exOne.runExercise();

        ExerciseTwo exTwo = new ExerciseTwo();
        try{
            exTwo.runExercise();
        }
        catch(IOException ex){
           System.out.println("You done goofed");
        }

        }
}
