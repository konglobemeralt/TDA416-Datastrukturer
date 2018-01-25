import AssignmentOne.ExcerciseThree;
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

       //ExerciseTwo exTwo = new ExerciseTwo();
       //try{
       //    exTwo.runExercise();
       //}
       //catch(IOException ex){
       //   System.out.println("You done goofed");
       //}

        ExcerciseThree exThree = new ExcerciseThree();
        exThree.combineStrings("abcdefg", "123456");
        exThree.combineStrings("abcdefg", "1");
        exThree.combineStrings("a", "123456");
        exThree.combineStrings("Test Test Test", "WWWWWWWW");

        System.out.println(exThree.recursiveCombineStrings("abcdefg", "123456"));
        System.out.println(exThree.recursiveCombineStrings("abcdefg", "1"));
        System.out.println(exThree.recursiveCombineStrings("a", "123456"));
        System.out.println(exThree.recursiveCombineStrings("Test Test Test", "WWWWWWWW"));

    }
}
