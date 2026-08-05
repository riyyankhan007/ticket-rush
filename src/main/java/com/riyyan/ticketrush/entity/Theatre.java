package com.riyyan.ticketrush.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "theatres")
public class Theatre extends BaseEntity {

    private String name;

    private String city;

    private String address;

    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens = new ArrayList<>();
}