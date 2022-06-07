/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Danja
 */
@Entity
public class Person implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String salt;
    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.login);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.user);
        return hash;
    }
    @Override
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (obj==null){
            return false;
        }
        if (getClass()!=obj.getClass()){
            return false;
        }
        final Person other=(Person) obj;
        if(!Objects.equals(this.id, other.id)){
            return false;
        }
        if(!Objects.equals(this.login, other.login)){
            return false;
        }
        if(!Objects.equals(this.password, other.password)){
            return false;
        }
        if(!Objects.equals(this.user, other.user)){
            return false;
        }
        return true;
    }
      
    
    
    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", login=" + login + ", password=" + password + ", user=" + user + '}';
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}
