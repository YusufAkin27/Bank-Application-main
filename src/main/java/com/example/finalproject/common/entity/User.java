package com.example.finalproject.common.entity;


import com.example.finalproject.customer.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userNumber; // this is identity or user number

    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();


}
