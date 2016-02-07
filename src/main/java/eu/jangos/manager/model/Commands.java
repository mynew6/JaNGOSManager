package eu.jangos.manager.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Commands generated by hbm2java
 */
public class Commands  implements java.io.Serializable {


     private Integer id;
     private String name;
     private String description;
     private Set roleses = new HashSet(0);

    public Commands() {
    }

	
    public Commands(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Commands(String name, String description, Set roleses) {
       this.name = name;
       this.description = description;
       this.roleses = roleses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Set getRoleses() {
        return this.roleses;
    }
    
    public void setRoleses(Set roleses) {
        this.roleses = roleses;
    }




}


