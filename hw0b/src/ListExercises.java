import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        if (L.isEmpty()) return 0;
        int res = 0;
        for(int i = 0;i < L.size();i++){
            res += L.get(i);
        }
        return  res;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> res = new ArrayList<>();
        for (int i = 0;i < L.size();i++){
            if (L.get(i) % 2 == 0){
                res.add(L.get(i));
            }
        }
        return res;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < L1.size(); i++){
            if (L2.contains(L1.get(i))){
                res.add(L1.get(i));
            }
        }

        return res;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int cnt = 0;
        for (String s:words){
            for (int i = 0; i < s.length(); i++){
                if (c == s.charAt(i)){
                    cnt ++;
                }
            }
        }
        return cnt;
    }
}
