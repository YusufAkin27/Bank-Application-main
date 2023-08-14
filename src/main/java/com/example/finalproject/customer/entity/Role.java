package com.example.finalproject.customer.entity;

import com.example.finalproject.common.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseModel {
    private String name;

}
