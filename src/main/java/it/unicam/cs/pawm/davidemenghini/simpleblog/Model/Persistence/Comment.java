package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Questa classe rappresenta un commento in un post.
 */
@Entity
@Table(name="Comment")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Comment {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="text_comment")
    private byte[] textComment;

    @Column(name="like_number")
    private int like_number;

    @Column(name="dislike_number")
    private int dislike_number;

    @Column(name="ida")
    private Integer idAuthor;

    @Column(name = "idp")
    private Integer idPost;


}
