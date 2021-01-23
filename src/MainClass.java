
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author flavi
 */
public class MainClass {
    
    public static List getRandom(int min, int max, int num){
        
        List<Integer> numbers= new ArrayList<Integer>();		// arraylist to store keys in each node
        List<Integer> rand_array= new ArrayList<Integer>();
        for(int i = min; i < max+1; i++)
        {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for(int j =0; j < num; j++)
        {
            rand_array.add(numbers.get(j));
        }
        return rand_array;
    }
    
    public static void dense(int m) throws FileNotFoundException{
        
        PrintStream o = new PrintStream(new File("Output_Dense_"+m+".txt"));
        // Store current System.out before assigning a new value 
        PrintStream console = System.out; 
        // Assign o to output stream 
        System.setOut(o); 
        
        List<Integer> keys;
        List<Integer> random_keys;
        keys = getRandom(100000,200000,10000);
        Collections.sort(keys);
        System.out.println("Randomly generating 10000 unique keys from 100000 to 200000");
        System.out.println("Number of keys generated: "+keys.size());
        System.out.println("Keys generated: "+keys);
        
        //Generating dense tree of order m
        BPlusImplement bplusTree1 = new BPlusImplement();
        bplusTree1.initialize(m);
        bplusTree1.createDenseTree(keys,m);
        System.out.println("Creating dense tree of order: "+m);
        System.out.println("Printing dense tree. The nodes are printed in DFS format.");
        bplusTree1.printtreeroot();
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("Experimentation");
        System.out.println("1. Generating random numbers for experimentation");
        random_keys = getRandom(99800,200200,14);
        System.out.println("Random numbers generated: "+random_keys);
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("2. Performing two random insert operations ");
        bplusTree1.insert_d(random_keys.get(0));
        bplusTree1.insert_d(random_keys.get(1));
        
        System.out.println("3. Performing random delete operations ");
        bplusTree1.delete(random_keys.get(2));
        bplusTree1.delete(random_keys.get(3));
        
        System.out.println("4. Performing random insert/deletion operations ");
        bplusTree1.insert_d(random_keys.get(4));
        bplusTree1.delete(random_keys.get(5));
        bplusTree1.insert_d(random_keys.get(6));
        bplusTree1.delete(random_keys.get(7));
        bplusTree1.insert_d(random_keys.get(8));
        
        System.out.println("5. Performing random search operations ");
        bplusTree1.search(random_keys.get(9));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(10));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(11));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(12));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(13));
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("6. Performing two random range search operations ");
        random_keys = getRandom(1,500000,2);
        Collections.sort(random_keys);
        bplusTree1.search(random_keys.get(0),random_keys.get(1));
        
        random_keys = getRandom(100000,200000,2);
        Collections.sort(random_keys);
        bplusTree1.search(random_keys.get(0),random_keys.get(1));
        
        System.out.println("**********************************");
        System.out.println("\n");
        System.out.println("More Experimentation");
        System.out.println("Generating a random number");
        random_keys = getRandom(99800,200200,1);
        System.out.println("Random number generated: "+random_keys);
        
        System.out.println("1.1. Inserting: "+random_keys.get(0));
        bplusTree1.insert_d(random_keys.get(0));
        
        System.out.println("1.2. Re - inserting: "+random_keys.get(0));
        bplusTree1.insert_d(random_keys.get(0));
        
        System.out.println("1.3. Searching: "+random_keys.get(0));
        bplusTree1.search(random_keys.get(0));
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("1.4. Deleting: "+random_keys.get(0));
        bplusTree1.delete(random_keys.get(0));
        
    }
    
    public static void sparse(int m) throws FileNotFoundException{
        
        PrintStream o = new PrintStream(new File("Output_Sparse_"+m+".txt"));
        // Store current System.out before assigning a new value 
        PrintStream console = System.out; 
        // Assign o to output stream 
        System.setOut(o); 
        
        List<Integer> keys;
        List<Integer> random_keys;
        keys = getRandom(100000,200000,10000);
        Collections.sort(keys);
        System.out.println("Randomly generating 10000 unique keys from 100000 to 200000");
        System.out.println("Number of keys generated: "+keys.size());
        System.out.println("Keys generated: "+keys);
        
        //Generating sparse tree of order m
        BPlusImplement bplusTree1 = new BPlusImplement();
        bplusTree1.initialize(m);
        bplusTree1.createSparseTree(keys,m);
        System.out.println("Creating sparse tree of order: "+m);
        System.out.println("Printing sparse tree. The nodes are printed in DFS format.");
        bplusTree1.printtreeroot();
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("Experimentation");
        System.out.println("1. Generating random numbers for experimentation");
        random_keys = getRandom(99800,200200,14);
        System.out.println("Random numbers generated: "+random_keys);
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("2. Performing two random insert operations ");
        bplusTree1.insert_d(random_keys.get(0));
        bplusTree1.insert_d(random_keys.get(1));
        
        System.out.println("3. Performing random delete operations ");
        bplusTree1.delete(random_keys.get(2));
        bplusTree1.delete(random_keys.get(3));
        
        System.out.println("4. Performing random insert/deletion operations ");
        bplusTree1.insert_d(random_keys.get(4));
        bplusTree1.delete(random_keys.get(5));
        bplusTree1.insert_d(random_keys.get(6));
        bplusTree1.delete(random_keys.get(7));
        bplusTree1.insert_d(random_keys.get(8));
        
        System.out.println("5. Performing random search operations ");
        bplusTree1.search(random_keys.get(9));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(10));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(11));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(12));
        System.out.println("**********************************");
        System.out.println("");
        bplusTree1.search(random_keys.get(13));
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("6. Performing two random range search operations ");
        random_keys = getRandom(1,500000,2);
        Collections.sort(random_keys);
        bplusTree1.search(random_keys.get(0),random_keys.get(1));
        
        random_keys = getRandom(100000,200000,2);
        Collections.sort(random_keys);
        bplusTree1.search(random_keys.get(0),random_keys.get(1));
        
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("More Experimentation");
        System.out.println("Generating random number for experimentation");
        random_keys = getRandom(99800,200200,1);
        System.out.println("Random number generated: "+random_keys);
        
        System.out.println("1.1. Inserting: "+random_keys.get(0));
        bplusTree1.insert_d(random_keys.get(0));
        
        System.out.println("1.2. Re - inserting: "+random_keys.get(0));
        bplusTree1.insert_d(random_keys.get(0));
        
        System.out.println("1.3. Searching: "+random_keys.get(0));
        bplusTree1.search(random_keys.get(0));
        System.out.println("**********************************");
        System.out.println("\n");
        
        System.out.println("1.4. Deleting: "+random_keys.get(0));
        bplusTree1.delete(random_keys.get(0));
        
        
    }
    
    
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        dense(13);
        dense(24);
        sparse(13);
        sparse(24);
        
        
    }   
}
