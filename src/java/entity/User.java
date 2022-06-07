
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String tel;
    private int amountMoney;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return  id + ") Имя: " + name + ", Фамилия: " + surname + ", Номер телефона: " + tel + ", количество денег: " + amountMoney;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(int amountMoney) {
        this.amountMoney = amountMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
