package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;


import jakarta.persistence.*;

/**
 * TODO implemetare
 * Questa classe rappresenta un commento in un post.
 */
@Entity
@Table(name="Comment")
public class Comment {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="textComment")
    private byte[] textComment;

    @Column(name="like_number")
    private int like_number;

    @Column(name="dislike_number")
    private int dislike_number;

    public Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getTextComment() {
        return textComment;
    }

    public void setTextComment(byte[] text) {
        this.textComment = text;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(int like) {
        this.like_number = like;
    }

    public int getDislike_number() {
        return dislike_number;
    }

    public void setDislike_number(int dislike) {
        this.dislike_number = dislike;
    }


}
