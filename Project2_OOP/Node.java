package tema2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

public class Node {
    int max_Capacity;
    ArrayList<Instance> Instances;
    public Node(int max_Capacity)
    {
        Instances = new ArrayList(); 
        this.max_Capacity = max_Capacity;
    }
}
