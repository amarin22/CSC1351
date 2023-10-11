package lab05;

/**
 * For this part of the lab you are expected
 * to use regular arrays only (no ArrayLists).
 */
public interface Permute {
  /**
   * Implement a recursive factorial function. 
   * Factorial is defined this way:
   * 0! = 1
   * 1! = 1
   * n! = n*(n-1)!
   */
  int fac(int n);

  /**
   * Creates a sequence of numbers.
   * Thus, sequence(3) generates [0,1,2].
   */
  int[] sequence(int n);

  /**
   * Given an array, this routine
   * removes the item at 
   * position n inside the array arr.
   * This function should allocate a new array
   * that's smaller than arr by one, and copy
   * all but the element at position n of arr
   * into the newly allocated array.
   */
  int[] remove(int[] arr,int n);

  /**
   * This method creates a new
   * array from arr by copying
   * its contents and appending
   * elem to the end.
   * This function should allocate a new array
   * that's bigger than arr by one, copy in
   * all values from arr, and put elem in
   * the last place.
   */
  int[] append(int[] arr,int elem);

  /**
    * Permute the elements of the array.
    * Step 1: create a array named result that's n! in size
    *         This will be a two-dimensional array.
    *         <pre>
    *         int[][] foo = new int[6][];
    *         </pre>
    *         Will allocate an array of 6 int arrays.
    * Step 2: if the size of the input array, arr, is 1
    *         insert it into result at index zero,
    *         then return from the function.
    * Step 3: for each element i of the array arr, create
    *         a smaller array, sm, with that element removed.
    *         Use the remove function you created for this
    *         purpose.
    * Step 4: Call permute on the smaller array and store
    *         the result in ires.
    * Step 5: For each array in the ires array, append
              arr[i], and put the return value in result.
    * Step 6: return the result.
    */
  int[][] permutation(int[] arr);
}