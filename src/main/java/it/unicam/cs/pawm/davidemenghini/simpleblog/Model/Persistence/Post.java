package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;

import jakarta.persistence.*;
import lombok.*;


/**
 * Questa classe
 */
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
    private int id;

    @Column(name="data_text")
    private byte[] dataText;

    @Column(name="data_img")
    private byte[] data_img;

    @Column(name="id_author")
    private int id_author;

    @Column(name="title_text")
    private byte[] titleText;

    @Column(name="like_number")
    private int likeNumber;

    @Column(name="dislike_number")
    private int dislikeNumber;

}
