export default class Post{


    id;

    ida;

    data_img;

    data_text;

    title_text;

    isBase64;

    likeNumber = 0;

    dislikeNumber = 0;

    constructor(id,ida,text,title){
        
        this.id = id;
        this.ida = ida;
        this.data_text = text;
        this.title_text = title;
        this.isBase64 = false;
    }


    /**
     * Questo metodo prende un immagine sotto forma di stringa, la trasforma in base64 e la salva.
     * @param {string} img l'immagine sotto forma di stringa.
     */
    setRawDataImg(img){
        this.data_img = img; 
        this.isBase64 = false;
    }

    /**
     * 
     * @param {String} img 
     */
    setBase64DataImg(img){
        this.data_img = Buffer.from(img).toString('base64'); 
        this.isBase64 = true;
    }
    getDataImage(){
        return this.data_img;
    }
    setDataImage(img){
        this.data_img = img;
    }
    
    
    getRawDataImg(){
        if(this.data_img = ""){
            return "";
        }
        if(this.isBase64){
            return Buffer.from(this.data_img, 'base64').toString('ascii');
        }else return this.data_img;
    }

    getBase64DataImg(){
        if(this.data_img = ""){
            return "";
        }
        if(!this.isBase64){
            this.data_img = Buffer.from(this.data_img,'base64').toString('base64');
            this.isBase64 = true;
            return this.data_img; 
        }else return this.data_img;
    }


    getId(){
        return this.id;
    }

    getIda(){
        return this.ida;
    }

    getDataText(){
        return this.data_text;
    }

    getTitleText(){
        return this.title_text;
    }


    setDislikeNumber(dislike_number){
        this.dislikeNumber = dislike_number;
    }

    getDislikeNumber(){
        return this.dislikeNumber;
    }

    setLikeNumber(likeNumber){
        this.likeNumber = likeNumber;
    }

    getLikeNumber(){
        return this.likeNumber;
    }



    














}