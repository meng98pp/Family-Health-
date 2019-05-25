package com.example.demohealth.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "user")
@Entity
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String telephone;
    private String address;
    private Integer category;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Basic
    @Column(name = "Telephone")
    public String getTelephone() {
        return telephone;
    }

    @Basic
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    @Basic
    @Column(name = "category")
    public Integer getCategory() {
        return category;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
