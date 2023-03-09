package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name="like_comment")
public class LikeComment {


    @Column(name = "id")
    @Id
    private int id;



    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_comment")
    private int idComment;
}
