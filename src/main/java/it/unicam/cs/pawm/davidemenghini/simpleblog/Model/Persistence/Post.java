package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Post")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Post {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="data_text")
    private byte[] data_text;

    @Column(name="data_img")
    private byte[] data_img;

    @Column(name="id_author")
    private int id_author;

    @Column(name="title_text")
    private byte[] title_text;

}
