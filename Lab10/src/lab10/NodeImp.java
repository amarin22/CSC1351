package lab10;

import java.util.Comparator;

public class NodeImp extends Node{

   /**
    * Note that your subclass will need to call
    * this constructor using super().
    */
    public NodeImp(Object k,Object v,Comparator c){
        super(k,v,c);
    }
    
   /**
    * (1) call the comparator and store the result in d.
    * (2) If d is zero, update the value on this node
    *     with the value supplied in the argument.
    * (3a) If d is greater than zero, and if the right
    *     child is not null, then put the value into the
    *     right child.
    * (3b) If d is greater than zero, and if the right
    *     child is null, then create a new node and
    *     assign it to the right child.
    * (4a) If d is less than zero, and if the left
    *     child is not null, then put the value into the
    *     left child.
    * (4b) If d is greater than zero, and if the left
    *     child is null, then create a new node and
    *     assign it to the left child.
    */
    @Override
    public void put(Object key, Object value) {
        int d = c.compare(this.key, key);
        if (d == 0)
            this.value = value;
        else if (d > 0) {
            if (right != null) {
                right.put(key, value);
            } else {
                right = new NodeImp(key, value, c);
            }
        } else if (d < 0) {
            if (left != null) {
                left.put(key, value);
            } else {
                left = new NodeImp(key, value, c);
            }
        }
    }
    
   /**
    * Compute the total number of nodes
    * beneath (and including) this one.
    */
    @Override
    public int size() {
        int s = 1;
        if(left != null) 
            s += left.size();
        if(right != null) 
            s += right.size();
        return s;
    }
    
   /**
    * Compute the maximum number of
    * nodes that can be traversed
    * starting from this one (and
    * including this one).
    */
    @Override
    public int height() {
        if(left == null && right == null) 
           return 1;
        if(left == null) 
           return right.height() + 1;
        if(right == null) 
           return left.height() + 1;
        int hr = right.height() + 1;
        int hl = left.height() + 1;
        if(hr > hl) 
           return hr;
        else 
           return hl;
    }
    
   /**
    * (1) call the comparator and store the result in d.
    * (2) If d is zero, return the value on this node.
    * (3) If d is greater than zero, and if the right
    *     child is not null, then return the value from the
    *     right child.
    * (4) If d is less than zero, and if the left
    *     child is not null, then return the value from the
    *     left child.
    * (5) If the above fails, return null.
    */
    @Override
    public Object get(Object key) {
        int d = c.compare(this.key, key);
        if(d == 0)
            return this.value;
        if(d > 0 && right != null)
            return right.get(key);
        if(d < 0 && left != null)
            return left.get(key);
        return null;
    }
    
   /**
    Transform the current node according
    to the diagram. Initially, the "this"
    variable should point to "t2". The
    return value should be a reconstructed
    tree with "t4" at the top.

    //    t2               t4
    //   /  \             /  \
    // t1    \    -->    /    t5
    //        t4       t2
    //       /  \     /  \
    //     t3    t5  t1   t3
    */
    @Override
    public Node rotateLeft() {
        Node t1 = this.left,
             t2 = this,
             t3 = this.right.left,
             t4 = this.right,
             t5 = this.right.right;
        
        t2.left = t1; t2.right = t3;
        t4.left = t2; t4.right = t5;
        
        return t4;
    }

   /**
    Transform the current node according
    to the diagram. Initially, the "this"
    variable should point to "t4". The
    return value should be a reconstructed
    tree with "t2" at the top.

    //       t4           t2
    //      /  \         /  \
    //     /    t5 --> t1    \
    //   t2                  t4
    //  /  \                /  \
    // t1   t3             t3   t5
    */
    @Override
    public Node rotateRight() {
        Node t1 = this.left.left,
             t2 = this.left,
             t3 = this.left.right,
             t4 = this,
             t5 = this.right;
        
        t4.left = t3; t4.right = t5;
        t2.left = t1; t2.right = t4;
        
        return t2;
    }
}