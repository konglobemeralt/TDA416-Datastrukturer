package AssignmentOne;

/**
 * Created by konglobemeralt on 2018-01-24.
 */
public class ExcerciseThree {

    public void combineStrings(String a, String b){

        String newString = "";

        for(int i = 0; i < Math.min(a.length(), b.length()); i++){
            newString += a.charAt(i);
            newString += b.charAt(i);
        }

        if( a.length() > b.length()){
                newString += a.substring(b.length());
            }
        else {
                newString += b.substring(a.length());
        }

        System.out.println(newString);
    }

    public String recursiveCombineStrings(String a, String b){
        if(a.isEmpty() || b.isEmpty()){
            return a + b;
        }
        return a.substring(0, 1) + b.substring(0, 1) + recursiveCombineStrings(a.substring(1), b.substring(1));
    }

}
