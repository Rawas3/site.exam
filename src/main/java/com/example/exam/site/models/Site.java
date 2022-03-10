package com.example.exam.site.models;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name="site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String full_text;

}
