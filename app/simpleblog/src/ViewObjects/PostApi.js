import axios from "axios"
import Post from "./Post";

/**
 * Questa classe serve per recuperare dal backend i Post; 
 */
export default class PostApi{

    randomPostUrl = "http://localhost:8080/api/public/post/random/"

    #isLikedToUserUrl = "http://localhost:8080/api/private/post/like/"
    
    #isDislikedToUserUrl = "http://localhost:8080/api/private/post/dislike/"
    
    #commentisLikedToUserUrl = "http://localhost:8080/api/private/comment/like/"

    #commentisDislikedToUserUrl = "http://localhost:8080/api/private/comment/dislike/"

    #postAddLike = "http://localhost:8080/api/private/post/like/add/"

    #postAddDislike = "http://localhost:8080/api/private/post/dislike/add/"

    #postRemoveLike = "http://localhost:8080/api/private/post/like/remove/"

    #postRemoveDislike = "http://localhost:8080/api/private/post/dislike/remove/"

    #createPost = "http://localhost:8080/api/private/post/add/"

    /**
     * Questo metodo restituisce due post random. 
     */
    async fetchRandomPosts(){
        return axios.post(this.randomPostUrl, {

        })
    }


    async createPostFromJson(post){
        console.log("p: "+post)
        await axios.post(this.#createPost,{
            post
        },{
            withCredentials: true
        }).then(function(response){

        }).catch(function(error){
            console.log(error);
        })
    }



    async isLikedToUser(idUser,idPost){
        var url = this.#isLikedToUserUrl+idPost+"/"
        console.log("p: "+idPost)
        var ret = await axios.post(url,{
            idUser
        },{
            withCredentials:true
        }).then(function(response){
            //console.log(response)
            return response.data;
        }).catch(function(error){
            console.log(error);
            return null;
        });
        return ret;
    }


    async isDislikedToUser(idUser,idPost){
        var url = this.#isDislikedToUserUrl+idPost+"/"
        console.log("p: "+idPost)
       return await axios.post(url,{
            idUser
        },{
            withCredentials: true
        }).then(function(response){
            console.log(response.data)
            return response.data;
        }).catch(function(error){
            console.log(error);  
        });
    }

    async fetchCommentIsLikedToUser(idComment,idUser){
        console.log("p: "+idComment)
        var url = this.#commentisLikedToUserUrl+idComment+"/"
        return await axios.post(url,{
            idUser
        },{
            withCredentials:true
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
            withCredentials:true
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
            withCredentials: true
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
            withCredentials: true
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
            withCredentials: true
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
            withCredentials: true
        }).then(function(response){
            return true;
        }).catch(function(error){
            console.log(error);
            return false;
        })
    }

}