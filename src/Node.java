
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author flavi
 */
public class Node {
    
    public int flag;    //Flag =0 if non leaf, Flag =1 if leaf
    public int numOfKeys = 0;   //Number of keys in node
    public Node parent;     //Pointer to parent node

    /**Store the list of keys at the node */
    public List<Integer> keys;  //List of keys in the node

    // Only for leaf nodes. 
    public Node previous;   //previous node in the doubly linked list*/
    public Node next;   //next node in the doubly linked list

    //Only for internal nodes
    public List<Node> children;     //children of the node
    
    //Instantiating a new node in the tree
    public Node(){
        keys = new ArrayList<>();
        children = new ArrayList<>();
        previous = null;
        next = null;
    }
    
    //Creating a new leaf in the tree
    public void createLeafNode(int key){
        keys.add(key);
        numOfKeys = 1;
        flag = 1;
    }
    
    //Creating an internal node in the tree
    public void createInternalNode(){
        flag = 0;
    }
    
    
}
