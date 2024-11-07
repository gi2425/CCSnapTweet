//Todd, Gi and John

import java.util.*;

/**
 * Priority queue contianing all of the users Posts. For easy acess to the highest liked Post, the highest liked
 * Post is stored at root, thus the prority queue can output the highest liked Post very quickly. 
 * CUP stands for Collective User Posts.
 * 
 * @pre
 *     CUP.heapifyUp is called whenever a Post is liked
 *     CUP.purge is called whenever a user logs in
 */

public class CUP
{
    //instance variables
    
    long currenttime = System.currentTimeMillis(); //current time, used in purge() function
    Post[] Heap; //list of all the users Posts, used in nested heap class
    int size = -1; //size of the array

    /**
     * Constructor method for CUP
     * @pre
     *     param H contains at least 1 value
     * @param H
     *     the array that will be used in the CUP nested heap class
     */
    public CUP (Post[] H)
    {
        Heap = H;
    }
    
    //Nested heap class functions
    
    //These functions are for the nested heap class in this class, and thus have no reason to be public
    
    /**
     * Returns parent of the node at position i
     * @param i
     *     where the node is located
     */
    private int parent(int i)
    {
       return (i--) / 2;
    }
     
    /**
     * Returns left child of the node at position i
     * @param i
     *     where the node is located
     */
    private int leftChild(int i)
    {
        int j = 2*i; 
        return j++;
    }
     
    /**
     * Returns right child of the node at position i
     * @param i
     *     where the node is located
     */
    private int rightChild(int i)
    {
      int j = 2*i; 
      return (j + 2);
    }
   
    //Accessor methods
    
    //Methods used by outside classes to access elements of CLP
    
    /**
     * Returns the current most liked Post.
     * Should be used in app to display the current most liked Post
     * @return
     *     the current most liked Post
     */
     public Post getMax() 
    {
      return Heap[0];
    }  
        
    //Updater methods
    
    //Methods used to edit CUP, usually to keep heap order property

    /**
     * Method used to shift Posts up in prority when they are modified.
     * Used to keep the heap order property.
     * @param i
     *     the value being shifted up
     */
     public void heapifyUp(int i) //used in app when a user likes a Post to keep heap order property
    {
      while (i > 0 && Heap[parent(i)].likes < Heap[i].likes) //while parents likes are less than childs likes
      {
        swap(parent(i), i); //swap child and parent
        i = parent(i);
      }
    }
    
    /**
     * Method used to shift Posts down in prority when they are modified.
     * Used to keep the heap order property.
     * @param i
     *     the value being shifted down
     */
     public void heapifyDown(int i)
    {
      //variables
      int j = i; //used later
      int LC = leftChild(i);
     
      if (Heap[LC].likes > Heap[i].likes && size >= LC) //if left child's likes is less i's likes and in bounds
      {
        i = LC;
      }
     
      int RC = rightChild(i);
      
      if (Heap[RC].likes > Heap[i].likes && size >= RC) //if right child's likes is less i's likes and in bounds
      {
        i = RC;
      }
     
      if (j != i) //if j no longer matchs i
      {
        swap(j, i);
        heapifyDown(i);
      }
    }
     
    /**
     * Method used to add a Post to CUP.
     * @param Post
     *     the Post being added
     */
    public void insert(Post p)
    {
      size++;
      Heap[size] = p;
      heapifyUp(size);
    }
     
    /**
     * Removes and returns Post with the highest likes
     * @return
     *     the Post with the highest likes
     */
    public Post takeMax()
    {
      Post max = Heap[0];
      Heap[0] = Heap[size]; //replace max with lowest value
      size = size - 1;
      heapifyDown(0); //heapify down
      return max;
    }
    
    /**
     * Swaps Post at index a and index b
     * @param a
     *     Post at index a
     * @param b
     *     Post at index b
     */
    private void swap(int a, int b)
    {
      Post c = Heap[a];
      Heap[a] = Heap[b];
      c = Heap[a];
    }

        /**
     * Returns a specific post based on a timestamp
     *     @param ts
     *          the timestamp
     *     @return
     *          the post being returned
     */
    public Post getPost(long ts)
    {
        if (size == -1)
        {   
            System.out.println("Must have entries in CUP");
        }
        else 
        {
            for (int i = 0; size >= i; i++)
            {
                if (Heap[i].time == ts) 
                {
                    return Heap[i];
                }
            }
        }
        return null;
    }


     
    /**
     * Removes Post at index i
     * @param i
     *     removes Post at index i
     */
    public void remove(int i)
    {
      Heap[i].likes = getMax().likes + 1; //i is now the max
      heapifyUp(i); //shifted to the top
      takeMax(); //removed
    }
    
    //Psudeocode: 
    //Note: for the below method, there should be a similiar one removing Posts from records when a time limit is reached.
    //this method is psudeocode, it requires input from the main method, which I do not have, I only have the one I made.
    //thus I am going to code this in psudeocode like its the main method I created, all you have to do is change this psuedocode to real code
    //using an input from your main method. - love John
    
    /**
     * This method is run when any user logs in to determine which Posts are a week old in miliseconds. 
     * If this limit has been reached, the Posts are removed from CUP.
     */
    public void purge()
    {
       //in main method whenever option A "login" is called
       //CUP.purge();
       
           //currentime = System.currentTimeMillis();
           //this actully might be redundant im not sure
           
           //iterate through each element in CUP. 
           //for each element
           //   if ((Post.time - currenttime)*-1 > 604800000 (1 week in ms) )
           //       Post.remove
           //
           //this is assuming that Post.time is a timestamp of when the Post was made, tracked in ms since jan 1st 1970
           //for this to work, Post.time must be a long
    }

}
