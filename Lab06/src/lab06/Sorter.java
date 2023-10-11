package lab06;

public interface Sorter {
  /**
   * Generate an array of random numbers.
   * First, instantiate a variable of type
   * Random (from the java.util package)
   * using the seed provided.
   * Next, allocate an array of the specified size.
   * Finally, fill the array with random
   * numbers using the Random varaible you
   * instantiated. Values should be between
   0 and 9999 inclusive.
   *
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Random.html">java.util.Random</a>
   */
  int[] generateRandArray(int size,int seed);

  /**
   * Create a copy of a sub array.
   * Copy the values of array arr beginning
   * with start up to (but not including) end.
   * The number of values in the returned array
   * should be equal to (end-start).
   */
  int[] subArray(int [] arr,int start, int end);

  /**
   * Given two arrays, ar1, and ar2,
   * both of which are sorted, merge
   * the two of them into a single
   * sorted array.
   * To do this,
   *
   * First, instantiate an array big enough
   * to hold all the values in ar1 and ar2. Name
   * it ar3.
   *
   * Next, declare three variables, index1 (for ar1),
   * index2 (for ar2), and index3 (for ar3),
   * each starting with value zero.
   *
   * Next, create a while loop that will iterate as
   * long as index1 is valid for ar1, and index2
   * is valid for ar2.
   *
   * Inside the while loop, determine which array
   * (ar1 or ar2) has the smallest value at its
   * index (index1 or index2) an copy that into
   * ar3 iat its index position. Increment the index
   * for ar3 and the array you copied from.
   *
   * Create a while loop that copies any remaining
   * values from ar1 to ar3.
   *
   * Create a while loop that copies any remaining
   * values from ar2 to ar3.
   *
   * Example of what Merge should do:
   * given ar1=[1,3,5] and
   * ar2=[2,4] merge to ar3=[1,2,3,4,5].
   */
  int[] mergeArrays(int[] ar1,int[] ar2);

  /**
   * Create a new array with the same
   * values as ar, but sorted. It should
   * work as follows
   * 1) If the array is size 1 or less,
   *    return a copy of the array.
   * 2) Create two arrays using subArray().
   *    Each should contain approximately
   *    half the values in ar. If
   *    the array is odd-sized, one of the
   *    two halves will be 1 bigger than
   *    the other. Example: if ar=[1,5,3,2,4]
   *    then ar1=[1,5,3] and ar2=[2,4].
   * 3) Call sort on each of the two arrays
   *    created in step two.
   * 4) Return the result of merging the
   *    two sorted arrays from step 3.
   */
  int[] sort(int[] ar);
}