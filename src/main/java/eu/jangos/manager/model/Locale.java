package eu.jangos.manager.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Locale generated by hbm2java
 */
public class Locale  implements java.io.Serializable {


     private int id;
     private String locale;
     private String localeString;
     private Set accounts = new HashSet(0);

    public Locale() {
    }

	
    public Locale(int id, String locale, String localeString) {
        this.id = id;
        this.locale = locale;
        this.localeString = localeString;
    }
    public Locale(int id, String locale, String localeString, Set accounts) {
       this.id = id;
       this.locale = locale;
       this.localeString = localeString;
       this.accounts = accounts;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getLocale() {
        return this.locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getLocaleString() {
        return this.localeString;
    }
    
    public void setLocaleString(String localeString) {
        this.localeString = localeString;
    }
    public Set getAccounts() {
        return this.accounts;
    }
    
    public void setAccounts(Set accounts) {
        this.accounts = accounts;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Locale other = (Locale) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.locale;
    }

}


