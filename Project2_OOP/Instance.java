package tema2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

public class Instance implements Comparable{
    ArrayList<Object> values;
    String instance_Name;
    private float timestamp;
    public Instance(String name, ArrayList values)
    {
        this.instance_Name = name;
        this.values = values;
        timestamp = System.nanoTime()%1000000000;
    }
    public float getTimestamp()
    {
        return this.timestamp;
    }
    public void setTimestamp(float timestamp)
    {
        this.timestamp = timestamp;
    }
    @Override
    public int compareTo(Object o) {
        float compare = ((Instance)o).getTimestamp();
        if(this.getTimestamp() > compare)
        {
            return 1;
        }
        else if(this.getTimestamp() == compare)
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }
}
