package AssignmentOne;

import java.io.*;

/**
 * Created by Jesper on 2018-01-21.
 */
public class ExerciseTwo {

    private char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z', 'å', 'ä', 'ö'};


    public void runExercise() throws IOException {

        try (InputStream in = new FileInputStream("src/AssignmentOne/text.txt");
             Reader reader = new InputStreamReader(in)) {


            int c;
            while ((c = reader.read()) != -1) {
                dealWithCharacter((char) c);
            }
        }
    }

    public void dealWithCharacter(char c){
        for(char testChar : alphabet){
            if(Character.toLowerCase(c) == testChar){
                System.out.println(c);

            }
        }
    }
}
