package AssignmentOne;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Jesper on 2018-01-21.
 */
public class ExerciseTwo {

    private char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z', 'å', 'ä', 'ö'};

    String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    public void runExercise() throws IOException{

        String fileContent = readFile("src/AssignmentOne/text.txt", Charset.defaultCharset());
        fileContent = fileContent.toLowerCase().replaceAll("[^a-zåäö]", "");
        System.out.println(fileContent);
       // try (InputStream in = new FileInputStream("src/AssignmentOne/text.txt");
       //      Reader reader = new InputStreamReader(in)) {
//
//
       //     int c;
       //     while ((c = reader.read()) != -1) {
       //         dealWithCharacter((char) c);
       //     }
       // }
    }

    public void dealWithCharacter(char c){
        for(char testChar : alphabet){
            if(Character.toLowerCase(c) == testChar){
                System.out.println(c);

            }
        }
    }
}
