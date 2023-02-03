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

    @Column(name="data_img")
    private byte[] data_img;

    @Column(name = "role_user")
    private String role_user;

    @Column(name="enabled")
    private int enabled;

    @Column(name="salt")
    private String salt;

    @Column(name="session_id")
    private String session_id;

}
