package com.example.exam.site.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@Table(name="dog_blogs")
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String full_text;
    private String photos;

    @Transient
    public String getPhotosImagePath () {
        if (photos == null || id == null) return null;
        return "/blog-photos/" + id + "/" +photos;
    }

}
