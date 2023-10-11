package lab05;

import java.util.ArrayList;

/**
 *
 * @author alexm
 */
public class Lab052 implements PermuteAL{

    /**
    * Creates a sequence of numbers.
    * Thus, sequence(3) generates [0,1,2].
    */
    @Override
    public ArrayList<Integer> sequence(int n) {
        ArrayList<Integer> x = new ArrayList<Integer>();
        for(int i = 0; i < n; i++){
            x.add(i);
        }
        return x;
    }

    /**
    * Permute the elements of the array.
    * Step 1: if the size of the input array, arr, is 1
    *         add it to the result and return it. Done.
    * Step 2: for each element i of the list arr, create
    *         a new and smaller list, sm, with that element removed.
    *         To copy a ArrayList, you can pass it into the constructor
    *         of ArrayList. The code below copies arr into sm.
    *         <pre>
    *         ArrayList<Integer> sm = new ArrayList<>(arr);
    *         </pre>
    * Step 3: call permutation on the smaller list and
    *         store the result in ires.
    * Step 4: for each list in ires, create a new
    *         list by appending the element i of arr.
    *         Add the return value of append to result.
    * Step 5: return the result.
    */
    @Override
    public ArrayList<ArrayList<Integer>> permutation(ArrayList<Integer> arr) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if(arr.size() == 1){
            result.add(arr);
            return result;
        }
        int index = 0;
        for(int i = 0; i < arr.size(); i++){
            ArrayList<Integer> sm = new ArrayList<>(arr);
            sm.remove(i);
            ArrayList<ArrayList<Integer>> ires = permutation(sm);
            for(int j = 0; j < ires.size(); j++){
                ArrayList<Integer> sm2 = ires.get(j);
                sm2.add(arr.get(i));
                result.add(sm2);
            }
        }
        return result;
    }
}