import axios from "axios"
import Post from "./Post";

/**
 * Questa classe serve per recuperare dal backend i Post; 
 */
export default class PostApi{

    randomPostUrl = "http://localhost:8080/api/public/post/random/";

    #isLikedToUserUrl = "http://localhost:8080/api/private/post/like/";
    
    #isDislikedToUserUrl = "http://localhost:8080/api/private/post/dislike/";
    
    #commentisLikedToUserUrl = "http://localhost:8080/api/private/comment/like/";

    #commentisDislikedToUserUrl = "http://localhost:8080/api/private/comment/dislike/";

    #postAddLike = "http://localhost:8080/api/private/post/like/add/";

    #postAddDislike = "http://localhost:8080/api/private/post/dislike/add/";

    #postRemoveLike = "http://localhost:8080/api/private/post/like/remove/";

    #postRemoveDislike = "http://localhost:8080/api/private/post/dislike/remove/";

    #createPost = "http://localhost:8080/api/private/post/add/";

    #searchPostUrl = "http://localhost:8080/api/public/post/search/";

    #token = "";
    
    constructor(){
        this.#token = sessionStorage.getItem("csrf_token");
    }

    /**
     * Questo metodo restituisce due post random. 
     */
    async fetchRandomPosts(){
        return axios.post(this.randomPostUrl, {

        })
    }



    async searchPost(value){
        return await axios.post(this.#searchPostUrl,{
            value
        })
        .then(function(response){
            return response.data;
        }).catch(function(error){
            return undefined;
        });
    }

    async createPostFromJson(post){
        await axios.post(this.#createPost,{
            post
        },{
            withCredentials: true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){

        }).catch(function(error){
            console.log(error);
        })
    }



    async isLikedToUser(idUser,idPost){
        var url = this.#isLikedToUserUrl+idPost+"/";
        var ret = await axios.post(url,{
            idUser
        },{
            withCredentials:true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return response.data;
        }).catch(function(error){
            console.log(error);
            return null;
        });
        return ret;
    }


    async isDislikedToUser(idUser,idPost){
        var url = this.#isDislikedToUserUrl+idPost+"/"
       return await axios.post(url,{
            idUser
        },{
            withCredentials: true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return response.data;
        }).catch(function(error){
            console.log(error);  
        });
    }

    async fetchCommentIsLikedToUser(idComment,idUser){
        var url = this.#commentisLikedToUserUrl+idComment+"/"
        return await axios.post(url,{
            idUser
        },{
            withCredentials:true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return null;
        });
    }



    async fetchCommentIsDislikedToUser(idComment,idUser){
        var url = this.#commentisDislikedToUserUrl+idComment+"/"
        return await axios.post(url,{
            idUser
        },{
            withCredentials:true,
            headers:{
                csrf_token: this.#token
            }
        })
        .then(function(response){
            return response.data;
        })
        .catch(function(error){
            console.log(error);
            return null;
        });
    }



    async addLikePost(idPost,idUser){
        var url = this.#postAddLike+idPost+"/";
        return await axios.post(url,{
            idUser
        },{
            withCredentials: true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return true;
        }).catch(function(error){
            console.log(error);
            return false;
        })
    }

    async removeLikePost(idPost,idUser){
        var url = this.#postRemoveLike+idPost+"/";
        return await axios.post(url,{
            idUser
        },{
            withCredentials: true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return true;
        }).catch(function(error){
            console.log(error);
            return false;
        })
    }


    async addDislikeToPost(idPost,idUser){
        var url = this.#postAddDislike+idPost+"/";
        return await axios.post(url,{
            idUser
        },{
            withCredentials: true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return true;
        }).catch(function(error){
            console.log(error);
            return false;
        })
    }


    async removeDislikePost(idPost,idUser){
        var url = this.#postRemoveDislike+idPost+"/";
        return await axios.post(url,{
            idUser
        },{
            withCredentials: true,
            headers:{
                csrf_token: this.#token
            }
        }).then(function(response){
            return true;
        }).catch(function(error){
            console.log(error);
            return false;
        })
    }

}