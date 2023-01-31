package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "default_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DefaultUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;

    @Column(name = "psw")
    private String psw;

    @Column
    private byte[] data_img;

    @Column
    private String role_user;

    @Column
    private int enabled;


}
