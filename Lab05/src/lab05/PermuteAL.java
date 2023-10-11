package lab05;

import java.util.ArrayList;

public interface PermuteAL {
  /**
   * Creates a sequence of numbers.
   * Thus, sequence(3) generates [0,1,2].
   */
  ArrayList<Integer> sequence(int n);

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
  ArrayList<ArrayList<Integer>> permutation(ArrayList<Integer> arr);
}