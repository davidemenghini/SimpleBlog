
export default class LoginObj{


    #username = "";

    #id = -1;

    #data_img= [];

    #role_user = "";



    constructor(user,psw) {
        this.#username = user
        this.#psw = psw
    }


    setUser(username){
        if(username !== "string"){
            return TypeError("l'username dell'utente dovrebbe essere una stringa");
        }
        this.#username = username;
    }

    getUser(){
        return this.#username;
    }

    setId(id){
        if(id !== "number"){
            return TypeError("l'id dell'utente dovrebbe essere un numero");
        }
        this.#id = id;
    }

    getId(){
        return this.#id;
    }
    
    setRoleUser(role_user){
        if(role_user !== "string"){
            return TypeError("Il ruolo dell'utente dovrebbe essere una stringa");
        }
        this.#role_user = role_user
    }

    getRoleUser(){
        return this.#role_user;
    }


    setDataImage(image){
        if(image !== "string"){
            return TypeError("l'immagine dell'utente dovrebbe essere una stringa");
        }
        this.#data_img = image;
    }

    getDataImage(){
        return this.#data_img;
    }



}