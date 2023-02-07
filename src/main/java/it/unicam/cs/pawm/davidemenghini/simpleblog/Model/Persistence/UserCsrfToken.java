package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="my_user_csrf_token")
@EqualsAndHashCode
@ToString
public class UserCsrfToken {

    @Id
    @Column(name="idu")
    private int id;

    @Column(name="token")
    private String token;









}
