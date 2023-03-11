import axios from "axios";

export default class CommentApi{


    #addLikeUrl = "http://localhost:8080/api/private/comment/like/add/";

    #addDislikeUrl = "http://localhost:8080/api/private/comment/dislike/add/";

    #removeDislikeUrl = "http://localhost:8080/api/private/comment/dislike/remove/";

    #removelikeUrl = "http://localhost:8080/api/private/comment/like/remove/";

    #createNewComment = "http://localhost:8080/api/private/comment/add/";

    

    async addLike(idUser,idComment){
        return await axios.post(this.#addLikeUrl+idComment+"/",{
            idUser   
        },{
            withCredentials:true
        }).then(function (response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return false;
        });
    }


    async removeLike(idUser,idComment){
        return await axios.post(this.#removelikeUrl+idComment+"/",{
            idUser   
        },{
            withCredentials:true
        }).then(function (response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return false;
        });
    }


    async addDislike(idUser,idComment){
        return await axios.post(this.#addDislikeUrl+idComment+"/",{
            idUser   
        },{
            withCredentials:true
        }).then(function (response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return false;
        });
    }


    async removeDislike(idUser,idComment){
        return await axios.post(this.#removeDislikeUrl+idComment+"/",{
            idUser   
        },{
            withCredentials:true
        }).then(function (response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return false;
        });
    }


    async createComment(comment){
        await axios.post(this.#createNewComment,{
            comment
        },{
            withCredentials:true
        }).then(function(response){

        }).catch(function(error){
            console.log(error)
        })
    }

}