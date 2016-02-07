package eu.jangos.manager.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Realmtype generated by hbm2java
 */
public class Realmtype  implements java.io.Serializable {


     private int id;
     private String type;
     private Set realms = new HashSet(0);

    public Realmtype() {
    }

	
    public Realmtype(int id) {
        this.id = id;
    }
    public Realmtype(int id, String type, Set realms) {
       this.id = id;
       this.type = type;
       this.realms = realms;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public Set getRealms() {
        return this.realms;
    }
    
    public void setRealms(Set realms) {
        this.realms = realms;
    }




}


