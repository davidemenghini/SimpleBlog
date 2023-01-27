package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "default_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DefaultUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;

    @Column(name = "psw")
    private String psw;


}
