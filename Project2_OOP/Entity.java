package tema2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author Keithox
 */
public class Entity {
    String name;
    int rep_Factor;
    int no_Atributes;
    ArrayList<String> types;
    ArrayList<String> fields;
    public Entity(String name, int rep_Factor, int no_Atributes, ArrayList types, ArrayList fields)
    {
        this.name = name;
        this.rep_Factor = rep_Factor;
        this.no_Atributes = no_Atributes;
        this.types = types;
        this.fields = fields;
    }
}
