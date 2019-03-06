package tema2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

public class Database {
    ArrayList<Node> nodeList;
    ArrayList<Entity> Entities;
    String name;
    int no_Nodes;
    int max_Capacity;
    public Database(String name, int no_Nodes, int max_Capacity)
    {
        nodeList = new ArrayList();
        this.name = name;
        this.no_Nodes = no_Nodes;
        this.max_Capacity = max_Capacity;
        Entities = new ArrayList();
        int i;
        for(i = 0; i < no_Nodes; i++)
        {
            nodeList.add(new Node(max_Capacity));
        }
    }
}
