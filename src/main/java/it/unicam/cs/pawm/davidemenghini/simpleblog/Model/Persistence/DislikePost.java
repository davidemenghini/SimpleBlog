package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name="dislike_post")
public class DislikePost{


    @Column(name = "id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;


    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_post")
    private int idPost;
}
