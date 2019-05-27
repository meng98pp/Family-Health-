package com.example.demohealth.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "Connect")
@Entity
public class Connect implements Serializable {
    private Integer id;
    private String usernameParent;
    private String usernameChild;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Basic
    @Column(name = "usernameChild")
    public String getUsernameChild() {
        return usernameChild;
    }

    @Basic
    @Column(name = "usernameParent")
    public String getUsernameParent() {
        return usernameParent;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsernameParent(String usernameParent) {
        this.usernameParent = usernameParent;
    }

    public void setUsernameChild(String usernameChild) {
        this.usernameChild = usernameChild;
    }


}
