import axios from "axios"
import Post from "./Post";

/**
 * Questa classe serve per recuperare dal backend i Post; 
 */
export default class PostApi{

    randomPostUrl = "http://localhost:8080/api/public/post/random/"

    
    

    /**
     * Questo metodo restituisce due post random. 
     */
    async fetchRandomPosts(){
        console.log("facendo la richiesta...");
        return axios.post(this.randomPostUrl, {

        })
    }


    async createPostFromJson(postJson){
        console.log("postJson: "+postJson);
        var p = new Post(postJson.id,postJson.id_author,postJson.data_text,postJson.title_text);
        p.setRawDataImg(postJson.data_img)
        console.log("creando l'oggetto "+p);
        return p;
    }









}