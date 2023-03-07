import axios from "axios";

export default class CommentApi{


    #addLikeUrl = "private/comment/like/add/";

    #addDislikeUrl = "private/comment/dislike/add/";

    #removeDislikeUrl = "private/comment/dislike/remove/";

    #removelikeUrl = "private/comment/like/remove/";



    async addLike(idUser,idComment){
        return await axios.post(this.#addLikeUrl+idComment+"/",{
            idUser   
        },{
            withCrediental:true
        }).then(function (response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return false;
        });
    }


    async removeLike(idUser,idComment){
        return await axios.post(this.#removelikeUrl,{
            idUser   
        },{
            withCrediental:true
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
            withCrediental:true
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
            withCrediental:true
        }).then(function (response){
            return response.data;
        }).catch(function(error){
            console.log(error)
            return false;
        });
    }


}