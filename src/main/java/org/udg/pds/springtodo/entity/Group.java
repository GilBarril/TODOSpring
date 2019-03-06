package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "groups")
public class Group {
    private String name;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Private.class)
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="owner")
    private User owner;


    public Group(){

    }
    public Group(String name, String description){
        this.name=name;
        this.description=description;

    }

    public void setUser(User user){
        this.owner = user;
    }

}
