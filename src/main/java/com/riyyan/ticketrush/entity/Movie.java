package com.riyyan.ticketrush.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {

    private String title;

    private String language;

    private Integer duration;

    private String genre;
}