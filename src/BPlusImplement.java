
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
public class BPlusImplement {
    
    public static Node[] leafnode;
    public static Node[] internalnode;
    public static Node rootnode;
    
    public int m;   //Order of tree
    public Node root;   //Root Node of the tree
    
    //Initializes tree with order m and root = null
    public void initialize(int m){
        this.m = m;
        this.root = null;
    }
    
    public void printtreeroot()
    {
        System.out.print("Root Node:");
        printtree(root);
    }
    
    public void printtree(Node r)
    {
        System.out.println(r.keys);
        if(r.flag==0){
            //System.out.println(r.keys.size());
            for(int j=0; j<r.keys.size(); j++){
                printtree(r.children.get(j));
        }  
        }
        
    }
    
    //To create dense tree
    public void createDenseTree(List key, int m){
        
        int eachnode = key.size()/m;    //200/10 = 20 nodes
        //System.out.println(eachnode);
        leafnode = new Node[eachnode];
        int i = 0;
        for(int j=0; j<eachnode; j++)
        {
            leafnode[j] = new Node();
            leafnode[j].flag = 1;
           
            leafnode[j].keys.addAll(key.subList(i, i+m));
            i+=m;
        }
        for(int j=0; j<eachnode; j++)
        {
            if (j==0)
            {
                leafnode[j].next=leafnode[j+1];
            }
            else if (j==eachnode-1)
            {
                leafnode[j].previous=leafnode[j-1];
            }
            else
            {
                leafnode[j].previous=leafnode[j-1];
                leafnode[j].next=leafnode[j+1];
            }
        }
        int temp = eachnode;
        eachnode = eachnode/m;  //20/10 = 2
        
        while(eachnode!=0)
        {
            if (eachnode==1)
            {
                break;              
            }
            internalnode = new Node[eachnode];
            i = 0;
            for(int j=0; j<eachnode; j++)
            {
                internalnode[j] = new Node();
                internalnode[j].flag = 0;
                for(int k=0; k<m; k++)
                {
                    internalnode[j].keys.add(leafnode[k+i].keys.get(0));
                    internalnode[j].children.add(leafnode[k+i]);
                    leafnode[k+i].parent = internalnode[j];
                }
                i+=m;
            }
            leafnode=internalnode;
            temp = eachnode;
            eachnode = eachnode/m;
        }
        
        if (eachnode==1 || eachnode==0)
        {
            rootnode = new Node();
            rootnode.flag =0;
            rootnode.parent = null;
            for(int k=0; k<temp; k++)
                {
                    rootnode.keys.add(internalnode[k].keys.get(0));
                    rootnode.children.add(internalnode[k]);
                    internalnode[k].parent = rootnode;
                } 
            root=rootnode;
        }
    }
    
    //To create sparse tree    
    public void createSparseTree(List key, int m){
        int minkey = (m+1)/2;
 
        int eachnode = key.size()/minkey;    //200/10 = 20 nodes
        
        leafnode = new Node[eachnode];
        int i = 0;
        for(int j=0; j<eachnode; j++)
        {
            leafnode[j] = new Node();
            leafnode[j].flag = 1;
            leafnode[j].keys.addAll(key.subList(i, i+minkey));
            i+=minkey;
        }
        for(int j=0; j<eachnode; j++)
        {
            if (j==0)
            {
                leafnode[j].next=leafnode[j+1];
            }
            else if (j==eachnode-1)
            {
                leafnode[j].previous=leafnode[j-1];
            }
            else
            {
                leafnode[j].previous=leafnode[j-1];
                leafnode[j].next=leafnode[j+1];
            }
        }
        if (m%2!=0)
            {
                minkey-=1;
            }
        int temp = eachnode;
        eachnode = eachnode/minkey;  //20/10 = 2
        
        while(eachnode!=0)
        {
            if (eachnode==1)
            {
                break;              
            }
            internalnode = new Node[eachnode];
            
            i = 0;
            for(int j=0; j<eachnode; j++)
            {
                internalnode[j] = new Node();
                internalnode[j].flag = 0;
                
                for(int k=0; k<minkey; k++)
                {
                    internalnode[j].keys.add(leafnode[k+i].keys.get(0));
                    internalnode[j].children.add(leafnode[k+i]);
                    leafnode[k+i].parent = internalnode[j];
                }
                i+=minkey;
            }
            leafnode=internalnode;
            temp = eachnode;
            eachnode = eachnode/minkey;
        }
        
        if (eachnode==1 || eachnode==0)
        {
            rootnode = new Node();
            rootnode.flag =0;
            rootnode.parent = null;
            for(int k=0; k<temp; k++)
                {
                    rootnode.keys.add(internalnode[k].keys.get(0));
                    rootnode.children.add(internalnode[k]);
                    internalnode[k].parent = rootnode;
                } 
            root=rootnode;
        }
    }
     
    
    //INSERTION OPERATION
    public void insert_d(int key){
        
        System.out.println("Inserting: "+key);
        
        System.out.println("Searching key "+key+" in the tree to see if it already exists.");
        
        if (search(key)==true)
        {
            System.out.println("The key " +key + " that you want to insert already exists in the B+ tree.");
            System.out.println("**********************************");
            System.out.println("\n");
            return;
        }
        System.out.println("The key " +key + " that you want to insert is not already in the B+ tree.");
        System.out.println("Inserting it");
        //When root is null --> i.e. New Tree
        if(root==null){
            root = new Node();
            root.createLeafNode(key);
            System.out.println("New Tree 1st node: " +root.keys);
        }
        
        //When root is leaf node
        else if(root.flag == 1 && root.numOfKeys < m - 1){
            insertLeafNode(key, root);
            System.out.println("When root is leaf node: " +root.keys);
        }
        
        //Insert in respective leaf node
        else{
            System.out.println("Traversing the tree to insert key ");
            Node curr = new Node();
            curr = root;
            Node parent = null;
            System.out.println("Root Node: " +curr.keys);
            int level = 1;
            //loop till curr is a leaf node
            while(curr.flag == 0){
                curr.parent = parent;
                parent = curr;
                int index = searchInNode(curr, key);
                if (index>=curr.children.size())
                {
                    index=curr.children.size()-1;
                }
                curr = curr.children.get(index);
                System.out.println("Level"+level+": "+curr.keys);
                level+=1;
            }
            
            insertLeafNode(key, curr);
            System.out.println(key+" has been inserted in the tree.");
            
            
            //When leaf is full
            if(curr.keys.size() > m){
                System.out.println("Leaf node is full after insertion. Splitting leaf node to accomodate future keys");
                splitLeafNode(curr);
                System.out.println("**********************************");
                System.out.println("\n");
                
            } 
            System.out.println("**********************************");
            System.out.println("\n");
        }
    }
    
    //insert key in the Node node
    public void insertLeafNode(int key, Node node){
        System.out.println("Inserting "+key+" in leaf node "+node.keys);
        int index = searchInNode(node, key);
        //System.out.println(index);
        if (index==0)
        {
            node.keys.add(index, key);
        }
        else if (index>=node.keys.size())
            {
                node.keys.add(index, key);
            }
        else
        {
            node.keys.add(index+1, key);
        }
        node.numOfKeys += 1;
        System.out.println("After inserting: " +node.keys);
    }
    
    
    //Traversing the tree. Search for location of key within a node. If the key already exists, returns the index where it exists. Else, returns the index where the key should exist.
    public int searchInNode(Node node, int key){
        List<Integer> keyList = node.keys;
        int low = 0;
        int high = keyList.size()-1;
        //int index = -1;
        
        if(key < keyList.get(0)){
            return 0;
        }
        
        for (int ki = 1; ki<keyList.size();ki++)
        {
            if(key>=keyList.get(ki-1) && key<keyList.get(ki))
            {
                return ki-1; 
            }
        }
        return keyList.size();
    }
    
    //When leaf node overflows, this fuction is called to split the leaf node and middle key is made parent node
    public void splitLeafNode(Node node){
        System.out.println("Splitting leaf node: "+node.keys);
        int splitIndex = m/2;   //index to be split at
        Node middleParent = new Node();
        Node rightSplit = new Node();
        System.out.println("Leaf node before splitting: " +node.keys);
        System.out.println("");
        //insert keys to new leaf node
        rightSplit.keys.addAll(node.keys.subList(splitIndex, node.keys.size()));
        
        //Split existing leaf node
        node.keys = node.keys.subList(0, splitIndex);
        System.out.println("Leaf node after splitting");
        System.out.println("Left Split keys: " +node.keys);
        System.out.println("Right split keys: " +rightSplit.keys);
        System.out.println("");
        
        rightSplit.parent = middleParent;
        rightSplit.flag = 1;    //setting it as leaf node
        
        //Creating new parent internal node
        middleParent.keys.add(rightSplit.keys.get(0));
        middleParent.children.add(rightSplit);
        boolean leafChild = true;
        middleParent.flag = 0;
        //System.out.println("Middle parent keys: " +middleParent.keys);
        
        //The below function is called to merge the parent node of the newly split child node into an existing internal node 
        //If internal node overflow occurs during this merge, the fuction splits internal node and makes middle element parent
        splitInternalNode(middleParent, node, node.parent, leafChild);  
        
    }
    
    
    //To merge the parent node of the newly split child node into an existing internal node 
    //If internal node overflow occurs during this merge, the fuction splits internal node and makes middle element parent
    /*
    toBeInsertedNode --> the new node to be inserted, parent of the split child nodes
    childNode --> the left child of the toBeInsertedNode
    currentNode --> the existing node where the toBeInsertedNode has to be merged
    leafChild -->  stores true if the immediate child is a leaf node 
    */
    
    public void splitInternalNode(Node toBeInsertedNode, Node childNode, Node currentNode, boolean leafChild){
        
        System.out.println("Restructuring parent(internal) nodes");
        //Spliting root and creating now new root
        if(currentNode == null){
            root = toBeInsertedNode;
            int childIndex = searchInNode(toBeInsertedNode, childNode.keys.get(0));
            toBeInsertedNode.children.add(childIndex, childNode);
            childNode.parent = toBeInsertedNode;
            System.out.println("Parent root node: " +root.keys);
            System.out.println("");
            //if leafchild, assign next and previous
            if(leafChild){
                if(childIndex == 0){
                    toBeInsertedNode.children.get(1).previous = toBeInsertedNode.children.get(0);
                    toBeInsertedNode.children.get(0).next = toBeInsertedNode.children.get(1);    
                }
                
                else{
                    toBeInsertedNode.children.get(childIndex-1).next = toBeInsertedNode.children.get(childIndex);
                    toBeInsertedNode.children.get(childIndex).previous = toBeInsertedNode.children.get(childIndex-1);

                    toBeInsertedNode.children.get(childIndex+1).previous = toBeInsertedNode.children.get(childIndex);
                    toBeInsertedNode.children.get(childIndex).next = toBeInsertedNode.children.get(childIndex+1);
                }   
            }            
        }
        
        //When internal node is to be merged and split
        else{
            merge(currentNode, toBeInsertedNode);
            System.out.println("");
            
            //When the internal node gets full after merging
            if(currentNode.keys.size() > m){
                System.out.println("Merged internal node is full. Splitting the node: "+currentNode.keys);
                int splitIndex = m/2;
                
                Node middleParent = new Node();
                Node rightSplit = new Node();
                
                rightSplit.keys.addAll(currentNode.keys.subList(splitIndex, currentNode.keys.size()));
                middleParent.keys.add(currentNode.keys.get(splitIndex));
                currentNode.keys = currentNode.keys.subList(0, splitIndex);
               
                //assign right split as child of middle split
                rightSplit.parent = middleParent;
                middleParent.children.add(rightSplit);
                middleParent.flag = 0;
                
                //split children list
                List<Node> rightChildren = new ArrayList<>();
                List<Node> existingChildren = currentNode.children;
                
                //find the nodes split to right child and left child
                int lastCurrChild = existingChildren.size();
                
                for(int i = existingChildren.size()-1; i>= 0; i--){
                    List<Integer> keysList = existingChildren.get(i).keys;
                    
                    if(middleParent.keys.get(0) > keysList.get(0)){
                        break;
                    }
                    else{
                        existingChildren.get(i).parent = rightSplit;
                        rightChildren.add(0, existingChildren.get(i));
                        lastCurrChild--;
                    }
                }
                rightSplit.children = rightChildren;
                currentNode.children = currentNode.children.subList(0, lastCurrChild);
                currentNode.keys = currentNode.keys.subList(0, splitIndex);
                
                System.out.println("Merged node after splitting");
                System.out.println("Left Split keys: " +currentNode.keys);
                System.out.println("Right Split keys: " +rightSplit.keys);
                System.out.println("");
                //System.out.println("Middle parent keys: " +middleParent.keys);
                
                //recursive call to split parent
                splitInternalNode(middleParent, currentNode, currentNode.parent, false); 
                
            }
        }
        
        
    }
    
    //merging a new node with an existing node
    public void merge(Node existingNode, Node newNode){
        System.out.print("Merging Nodes: ");
        System.out.println(existingNode.keys+" and "+ newNode.keys);
        
        int keyOfNewNode = newNode.keys.get(0);
        //System.out.println(keyOfNewNode);
        Node childOfNewNode = newNode.children.get(0);
        //System.out.println(childOfNewNode.keys);
        
        //search index and add new internal node
        int indexOfInsert = searchInNode(existingNode, keyOfNewNode);
        
        int childIndex = indexOfInsert;
        //System.out.println(childIndex);
        if(keyOfNewNode <= childOfNewNode.keys.get(0)){
            childIndex += 1;
        }
        //System.out.println(childIndex);
        //System.out.println("Index of insert: " +indexOfInsert);
        childOfNewNode.parent = existingNode;
        if (indexOfInsert>=existingNode.keys.size())
        {
            indexOfInsert=indexOfInsert;
            childIndex=childIndex-1;
        }
        else
        {
            indexOfInsert=indexOfInsert+1;
            childIndex=childIndex;
        }
        
        existingNode.keys.add(indexOfInsert, keyOfNewNode);
        existingNode.children.add(childIndex, childOfNewNode);
        //System.out.println(existingNode.children.get(childIndex).keys);
        System.out.println("Merged Node: " +existingNode.keys);
        //updating children's prev and next
        if(existingNode.children.get(0).flag == 1){
            
            //if first element is inserted
            if(existingNode.children.size()-1 != childIndex && existingNode.children.get(childIndex + 1).previous == null){
                existingNode.children.get(childIndex).next = existingNode.children.get(childIndex + 1);
                existingNode.children.get(childIndex+1).previous = existingNode.children.get(childIndex);
            }
            
            //if last element is inserted
            else if(childIndex != 0 && existingNode.children.get(childIndex-1).next == null){
                existingNode.children.get(childIndex-1).next = existingNode.children.get(childIndex);
                existingNode.children.get(childIndex).previous = existingNode.children.get(childIndex-1);
            }
            
            else{
                existingNode.children.get(childIndex).next = existingNode.children.get(childIndex - 1).next;
                existingNode.children.get(childIndex).next.previous = existingNode.children.get(childIndex);

                existingNode.children.get(childIndex-1).next = existingNode.children.get(childIndex);
                existingNode.children.get(childIndex).previous = existingNode.children.get(childIndex - 1);
            }   
        }  
    }
    
    //SEARCH OPERATION
    //Search the B+ tree for the key and returns corresponding key from the tree.  If the key does not exist, returns null. 
    public boolean search(int key){
        System.out.println("Searching: "+key);
        Node current = root;
        Node parent = null;
        System.out.println("Traversing the tree from root node to leaf:");
        System.out.println(current.keys);
        
        //traversing the tree till external node reached
        while(current.flag == 0){

            int index = searchInNode(current, key);
            if (index>=current.children.size())
            {
                index=current.children.size()-1;
            }

            current = current.children.get(index);
            System.out.println(current.keys);
        }
        
        //search key and return that it exists if found
        List<Integer> keyList = current.keys;

        for(int i=0;i<keyList.size();i++){
            if(keyList.get(i) == key){
                System.out.println("Key "+key+" exists in the tree");
                return true;
            }
        }
        System.out.println("Key "+key+" does not exist in the tree");
        return false;
        
        
    }
    
    //RANGE SEARCH OPERATION
    
    public void search(int keyStart, int keyEnd){
        System.out.println("Searching in the range: "+keyStart+", "+keyEnd);
        List<Integer> allKeys = new ArrayList<>();
        Node current = root;
        while(current.flag != 1){
            int index = searchInNode(current, keyStart);
            if (index>=current.children.size())
            {
                index=current.children.size()-1;
            }
            current = current.children.get(index);
        }
        //Checks for all the external nodes starting from the beginning key in the range 
        //till either the last key in the range or end of external nodes list 
        boolean keyEndReach = false;
        while(current != null && !keyEndReach){
            for(int i=0;i<current.keys.size();i++){
                if(current.keys.get(i) >= keyStart && current.keys.get(i) <= keyEnd){
                    allKeys.add(current.keys.get(i));
                }
                if(current.keys.get(i) > keyEnd){
                    keyEndReach = true;
                    break;
                }
            }
            current = current.next;
            
        }
        if(allKeys.size()==0){
            System.out.println("No keys exist within the specified range.t");
        }
        else{
            System.out.print("Keys within the range: ");
            for(int i=0;i<allKeys.size();i++){
                System.out.print(allKeys.get(i)+",");
            }
            System.out.println("");
        }
        //System.out.println("**********************************");
        System.out.println("\n");
        
    }
    
    
    //DELETE OPERATION
    //Search the B+ tree for the key and deletes corresponding key from the tree. 
    //If the key does not exist, prints that key does not exist and returns.
    
    public void delete(int key){
        System.out.println("Deleting key: "+key);
        Node curr = new Node();
        int i = 0;                      
        curr = root;
        Node parent = null;

        //traverse path in tree to leaf node
        while(curr.flag == 0){
            i = searchInNode(curr, key);
            parent = curr;
            if (i>=curr.children.size())
            {
                i=curr.children.size()-1;
            }
            curr = curr.children.get(i);
            curr.parent = parent;
        }
        
        //Find index to delete the leaf node at
        int deleteIndex = searchInNode(curr, key);

        if (deleteIndex>=curr.keys.size())
            {
                deleteIndex=curr.keys.size()-1;
            }
        
        //If key does not exist
        if(curr.keys.get(deleteIndex) != key){
            System.out.println("The key" + key+" that you want to delete does not exist in the tree");
            System.out.println("**********************************");
            System.out.println("\n");
            return;
        }
        
        //delete key from leaf and check for root
        System.out.println("Node before deleting: "+curr.keys);
        curr.keys.remove(deleteIndex);
        System.out.println("Node after deleting: "+curr.keys);
        System.out.println("Key deletion completed");
        System.out.println("**********************************");
        System.out.println("\n");
        
        //System.out.println("Checking if nodes have number of keys below minimum after deletion operation.");
        if(root.flag == 1 && root.keys.size()==0){
            root = null;
            System.out.println("Root node is empty. So it is deleted");
            return;
        }
        //if leaf node is deficient with less than minimum keys left, balance the tree
        if(curr.keys.size() < (m+1)/2)
        {
            
            //if previous sibling exists, has more than one pairs and has the same parent, borrow from previous sibling
            if(curr.previous!= null && curr.previous.keys.size() > (m+1)/2 && curr.previous.parent == curr.parent){
                System.out.println("Leaf node has number of keys below minimum. And it can borrow from previous sibling without making it subminimum.");
                Node prev = curr.previous;
                //add to current leaf
                System.out.println("Leaf node before borrowing fom previous sibling: "+curr.keys);
                curr.keys.add(0,prev.keys.get(prev.keys.size()-1));
                System.out.println("Leaf node after borrowing fom previous sibling: "+curr.keys);
                
                //update parent keys
                System.out.println("It's parent node before update: "+curr.parent.keys);
                curr.parent.keys.set(i, curr.keys.get(0));
                System.out.println("It's parent node updated to: "+curr.parent.keys);
                
                //remove from previous sibling pairs
                System.out.println("The previous sibling before giving a key: "+prev.keys);
                prev.keys.remove(prev.keys.size()-1);  
                System.out.println("The previous sibling after giving a key: "+prev.keys);
                System.out.println("**********************************");
                System.out.println("\n");
            }
            
            //if next sibling exists, has more than one pairs and has the same parent, borrow from next sibling
            else if(curr.next!=null && curr.next.keys.size() > (m+1)/2 && curr.next.parent == curr.parent){
                System.out.println("Leaf node has number of keys below minimum. And it can borrow from next sibling without making it subminimum.");
                Node nextSib = curr.next;
                
                System.out.println("Leaf node before borrowing fom next sibling: "+curr.keys);
                //add to current leaf
                curr.keys.add(nextSib.keys.get(0));
                System.out.println("Leaf node after borrowing fom next sibling: "+curr.keys);
                
                //remove from next sibling
                nextSib.keys.remove(0);

                //update parent keys
                System.out.println("It's parent node before update: "+curr.parent.keys);
                curr.parent.keys.set(i+1, nextSib.keys.get(0));
                System.out.println("It's parent node updated to: "+curr.parent.keys);
                System.out.println("The next sibling after giving a key: "+nextSib.keys);
                System.out.println("**********************************");
                System.out.println("\n");
                
            }
             
        }
        System.out.println("Deletion operation complete.");
        System.out.println("**********************************");
        System.out.println("\n");
    }
    
    
}
