package lab05;

/**
 *
 * @author alexm
 */
public class Lab05 implements Permute{
    /**
    * Implement a recursive factorial function. 
    * Factorial is defined this way:
    * 0! = 1
    * 1! = 1
    * n! = n*(n-1)!
    */
    @Override
    public int fac(int n) {
        if(n < 2) return 1;
        return n * fac(n-1);
    }

    /**
    * Creates a sequence of numbers.
    * Thus, sequence(3) generates [0,1,2].
    */
    @Override
    public int[] sequence(int n) {
        int[] x = new int[n];
        for(int i = 0; i < n; i++){
            x[i] = i;
        }
        return x;
    }

    /**
    * Given an array, this routine
    * removes the item at 
    * position n inside the array arr.
    * This function should allocate a new array
    * that's smaller than arr by one, and copy
    * all but the element at position n of arr
    * into the newly allocated array.
    */
    @Override
    public int[] remove(int[] arr, int n) {
        if(arr == null) return null;
        int[] x = new int[arr.length - 1];
        for(int i = 0; i < n; i++)
            x[i] = arr[i];
        for(int i = n; i < x.length; i++)
            x[i] = arr[i + 1];
        return x;
    }

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
    @Override
    public int[] append(int[] arr, int elem) {
        if(arr == null) return null;
        int[] x = new int[arr.length + 1];
        for(int i = 0; i < arr.length; i++)
            x[i] = arr[i];
        x[x.length - 1] = elem;
        return x;
    }

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
    @Override
    public int[][] permutation(int[] arr) {
        if(arr == null) return null;
        int[][] result = new int[fac(arr.length)][];
        if(arr.length == 1){
            result[0] = arr;
            return result;
        }
        int index = 0;
        for(int i = 0; i < arr.length; i++){
            int[] sm = remove(arr, i);
            int[][] ires = permutation(sm);
            for(int j = 0; j < ires.length; j++)
                result[index++] = append(ires[j], arr[i]);
        }
        return result;
    }
}