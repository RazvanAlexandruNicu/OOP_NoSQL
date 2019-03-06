package tema2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Keithox
 */
public class Tema2 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Keithox\\Documents\\NetBeansProjects\\Tema2\\src\\tema2\\input.txt")); 
        PrintStream fileStream = new PrintStream("C:\\Users\\Keithox\\Documents\\NetBeansProjects\\Tema2\\src\\tema2\\output.txt");
        System.setOut(fileStream);
        Scanner scanner;
        String st = br.readLine();
        scanner = new Scanner(st);
        String operation;
        operation = scanner.next();
        String databaseName = null;
        int nrNodes = 0;
        int maxCapacity = 0;
        if(operation.compareTo("CREATEDB") == 0)
        {
            databaseName = scanner.next();
            nrNodes = scanner.nextInt();
            maxCapacity = scanner.nextInt();           
        }
        Database myDatabase = new Database(databaseName, nrNodes, maxCapacity);
        while((st = br.readLine())!= null && st.length()!=0) {
            scanner = new Scanner(st);
            
            operation = scanner.next();
            if( operation.compareTo("CREATE") == 0)
            {
                String name = scanner.next();
                int rep_Factor = scanner.nextInt();
                int no_Attributes = scanner.nextInt();
                ArrayList<String> fields = new ArrayList();
                ArrayList<String> types = new ArrayList();
                for(int i = 0; i < no_Attributes; i++)
                {
                    String attribute = scanner.next();
                    String type = scanner.next();
                    boolean add1 = fields.add(attribute);
                    boolean add2 = types.add(type);
                }
                Entity myEntity = new Entity(name, rep_Factor, no_Attributes, types, fields);
                myDatabase.Entities.add(myEntity);
            }
            
            if( operation.compareTo("INSERT") == 0)
            {
                String name = scanner.next();
                int sw = 0;
                int index = 0;
                for( Entity entityLoop : myDatabase.Entities)
                {
                    if(entityLoop.name.compareTo(name) == 0)
                    {
                        sw = 1;
                        index = myDatabase.Entities.indexOf(entityLoop);
                        break;
                    }
                }
                if(sw == 1) // If i found the entity
                { 
                    Entity myEntity = myDatabase.Entities.get(index);
                    String[] instanceStringValues = new String[myEntity.no_Atributes];
                    for(int j = 0; j < myEntity.no_Atributes; j++)
                    {
                        instanceStringValues[j] = scanner.next();
                    }
                    ArrayList<Object> instanceValues = new ArrayList();
                    for(int j = 0; j< myEntity.types.size(); j++ )
                    {
                        if(myEntity.types.get(j).compareTo("Integer") == 0)
                        {
                            int intValue = Integer.parseInt(instanceStringValues[j]);
                            instanceValues.add(intValue);
                        }
                        if(myEntity.types.get(j).compareTo("String") == 0)
                        {
                            String stringValue = instanceStringValues[j];
                            instanceValues.add(stringValue);
                        }
                        if(myEntity.types.get(j).compareTo("Float") == 0)
                        {
                            float floatValue = Float.parseFloat(instanceStringValues[j]);
                            DecimalFormat df = new DecimalFormat("#.##");
                            String result = df.format(floatValue);
                            instanceValues.add(result);
                        }         
                    }
                    Instance newInstance = new Instance(myEntity.name, instanceValues);
                    // I created the instance with the proper values
                    int nr = myEntity.rep_Factor;
                    while(nr > 0)
                    {
                        for(int k = 0 ; k < myDatabase.no_Nodes ; k++)
                        {
                            if(myDatabase.nodeList.get(k).max_Capacity > myDatabase.nodeList.get(k).Instances.size()) // daca am loc sa inserez
                            {
                                myDatabase.nodeList.get(k).Instances.add(newInstance);
                                Collections.sort(myDatabase.nodeList.get(k).Instances);
                                nr--;
                            }
                            if(nr == 0)
                            {
                                break;
                            }
                        }
                    }
                    // I inserted the instance in rep_factor nodes.
                }
                else
                {
                    System.out.println("NO SUCH ENTITY");
                }
                
            }
            
            if( operation.compareTo("SNAPSHOTDB") == 0)
            {
                if(myDatabase.nodeList.get(0).Instances.isEmpty() == true)
                {
                    System.out.println("EMPTY DB");
                    continue;
                }
                for(Node nodeLoop : myDatabase.nodeList) // k -> every node
                {
                    if(nodeLoop.Instances.isEmpty() == false)
                    {
                        System.out.println("Nod"+(myDatabase.nodeList.indexOf(nodeLoop)+1));                       
                        for(int m = nodeLoop.Instances.size() - 1; m >= 0; m--) // m -> every instance
                        {
                            String name = nodeLoop.Instances.get(m).instance_Name;
                            System.out.print(name);
                            int h;
                            for(h = 0; h < myDatabase.Entities.size(); h++)
                            {
                                if(myDatabase.Entities.get(h).name.compareTo(name) == 0)
                                {
                                    break;
                                }
                            }
                            if(h < myDatabase.Entities.size())
                            {
                                for(int g = 0; g < myDatabase.Entities.get(h).no_Atributes; g++)
                                {
                                    System.out.print(" "+myDatabase.Entities.get(h).fields.get(g) +":"+nodeLoop.Instances.get(m).values.get(g));
                                }
                            }
                            System.out.println("");
                        } 
                    }
                    
                }
                
            }
            
            if( operation.compareTo("DELETE") == 0)
            {
                String name = scanner.next();
                String primaryKey = scanner.next();
                int sw = 0;
                int nrDeleted = 0;
                for (Entity entityLoop : myDatabase.Entities)
                {
                    if(entityLoop.name.compareTo(name) == 0)
                    {
                        sw = 1;
                        break;
                    }
                }
                if(sw == 1) // I found the entity
                {
                    for( Node nodeLoop : myDatabase.nodeList)
                    {
                        Instance refference = null;
                        for( Instance instanceLoop : nodeLoop.Instances)
                        {
                            if( instanceLoop.instance_Name.compareTo(name) == 0 && (instanceLoop.values.get(0)+"").compareTo(primaryKey) == 0)
                            {
                                refference = instanceLoop;
                                nrDeleted++;                              
                            }
                        }
                        if(refference != null)
                        {
                            nodeLoop.Instances.remove(refference);
                        }                       
                    }
                    if(nrDeleted == 0)
                    {
                        System.out.println("NO INSTANCE TO DELETE");
                    }
                }
                
            }
            
            if( operation.compareTo("GET") == 0)
            {
                String name = scanner.next();
                String primaryKey = scanner.next();
                int sw = 0;
                int nrFound = 0;
                for (Entity entityLoop : myDatabase.Entities)
                {
                    if(entityLoop.name.compareTo(name) == 0)
                    {
                        sw = 1;
                        break;
                    }
                }
                if(sw == 1) // I found the entity
                {
                    Instance refference = null;
                    for( Node nodeLoop : myDatabase.nodeList)
                    {
                        for( Instance instanceLoop : nodeLoop.Instances)
                        {
                                             
                            if( instanceLoop.instance_Name.compareTo(name) == 0 && (instanceLoop.values.get(0)+"").compareTo(primaryKey) == 0)
                            {
                                refference = instanceLoop;
                                nrFound++;    
                                System.out.print("Nod"+(myDatabase.nodeList.indexOf(nodeLoop) + 1)+" ");
                            }
                        }                                           
                    }
                    if(refference != null)
                    {
                        int h;
                        for(h = 0; h < myDatabase.Entities.size(); h++)
                        {
                            if(myDatabase.Entities.get(h).name.compareTo(refference.instance_Name) == 0)
                            {
                                break;
                            }
                        }
                        System.out.print(myDatabase.Entities.get(h).name);
                        for(int g = 0; g < myDatabase.Entities.get(h).no_Atributes; g++)
                        {
                            System.out.print(" "+myDatabase.Entities.get(h).fields.get(g) +":"+refference.values.get(g));
                        }
                        System.out.println("");
                        
                    }
                    if(nrFound == 0)
                    {
                        System.out.println("NO INSTANCE FOUND");
                    }
                }
                
            }

            if( operation.compareTo("UPDATE") == 0)
            {
                String name = scanner.next();
                String primaryKey = scanner.next();
                String attribute1 = scanner.next();
                String value1 = scanner.next();
                int sw = 0;
                int nrFound = 0;
                int index = 0;
                for (Entity entityLoop : myDatabase.Entities)
                {
                    if(entityLoop.name.compareTo(name) == 0)
                    {
                        sw = 1;
                        index = myDatabase.Entities.indexOf(entityLoop);
                        break;
                    }
                }
                if(sw == 1) // I found the entity
                {
                    Entity myEntity = myDatabase.Entities.get(index);
                    Instance refference = null;
                    int indexToModify = -1;
                    int instanceIndex = -1;
                    int updated = 0;
                    int exit;
                    float timestamp = 0;
                    for( Node nodeLoop : myDatabase.nodeList)
                    {
                        exit = 0;
                        for( Instance instanceLoop : nodeLoop.Instances)
                        {       
                            instanceIndex = -1; 
                            if( instanceLoop.instance_Name.compareTo(name) == 0 && (instanceLoop.values.get(0)+"").compareTo(primaryKey) == 0)
                            {                               
                                indexToModify = -1;
                                for( int g = 0; g < myEntity.fields.size(); g++)
                                {                                   
                                    if( myEntity.fields.get(g).compareTo(attribute1) == 0)
                                    {
                                        indexToModify = g;
                                        instanceIndex = nodeLoop.Instances.indexOf(instanceLoop);
                                        exit = 1;
                                        break;
                                    }
                                }                                
                            } 
                            if(exit == 1)
                            {
                                break;
                            }
                        }
                        if(indexToModify >=0 && instanceIndex >= 0)
                        {
                            if(myEntity.types.get(indexToModify).compareTo("Integer") == 0)
                            {
                                int intValue = Integer.parseInt(value1);
                                nodeLoop.Instances.get(instanceIndex).values.set(indexToModify, intValue);
                            }
                            if(myEntity.types.get(indexToModify).compareTo("String") == 0)
                            {
                                String stringValue = value1;
                                nodeLoop.Instances.get(instanceIndex).values.set(indexToModify, stringValue);
                            }
                            if(myEntity.types.get(indexToModify).compareTo("Float") == 0)
                            {
                                float floatValue = Float.parseFloat(value1);
                                DecimalFormat df = new DecimalFormat("#.##");
                                String result = df.format(floatValue);
                                nodeLoop.Instances.get(instanceIndex).values.set(indexToModify, result);
                            }
                            // update timestamp
                            if(timestamp == 0)
                            {
                                timestamp = System.nanoTime()%1000000000;
                                nodeLoop.Instances.get(instanceIndex).setTimestamp(timestamp);
                            }
                            else
                            {
                                nodeLoop.Instances.get(instanceIndex).setTimestamp(timestamp);
                            }
                        }
                        
                        Collections.sort(myDatabase.nodeList.get(myDatabase.nodeList.indexOf(nodeLoop)).Instances);                                             
                    }                  
                }
            }            
        }      
   }    
}
