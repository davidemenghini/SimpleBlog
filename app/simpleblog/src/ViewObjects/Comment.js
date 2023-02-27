
export default class Comment{

    idPost;

    likeNumber;
    
    dislikeNumber;

    text;

    ida;

    id;



    constructor(){

    }


    setId(id){
        this.id = id;
    }

    getId(){
        return this.id;
    }


    setIda(ida){
        this.ida = ida;
    }

    getIda(){
        return this.ida;
    }

    setText(text){
        this.text = Buffer.from(text, 'utf-8').toString();
    }


    getText(){
        return this.text;
    }


    setIdPost(idPost){
        this.idPost = idPost;
    }

    getIdPost(){
        return this.idPost;
    }

    setLikeNumber(likeNumber){
        this.likeNumber = likeNumber;
    }

    getLikeNumber(){
        return this.likeNumber;
    }

    setDislikeNumber(dislike_number){
        this.dislikeNumber = dislike_number;
    }

    getDislikeNumber(){
        return this.dislikeNumber;
    }

}