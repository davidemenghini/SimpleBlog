import axios from "axios";

export default class UserApi{


    #loginUrl = "http://localhost:8080/api/public/login/";
    
    #postUserFromId = "http://localhost:8080/api/public/user/comment/";

    #csrfTokenUrl = "http://localhost:8080/api/public/csrf/";

    #logoutUrl = "http://localhost:8080/api/private/logout/";
    
    #commentUserFromId = "http://localhost:8080/api/public/user/comment/";

    #token = "";
    

    async getUsernameFromId(idUser){
        return await axios.get(this.#commentUserFromId+idUser+"/")
        .then(function(response){
            return response.data.username
        }).catch(function(error){
            console.log(error)
            return null
        });
    }

    async makeLogin(userAndPass){
        var user = userAndPass.user
        var psw = userAndPass.psw
        let returningValue = null;
        await axios.post(this.#loginUrl,{
            user:user, psw:psw
        },{
            withCredentials: true
        }).then(function (response){
            returningValue = response.data;
        }).catch(function (error){
            console.log(error);
        });
        return returningValue;
    }


    async getCsrfToken(userAndPass){
        var user = userAndPass.user
        var psw = userAndPass.psw
        axios.post(this.#csrfTokenUrl,{
            user:user, psw:psw
        },{
            withCredentials: true
        }).then(function (response){
            sessionStorage.setItem("csrf_token",response.headers['csrf_token']);
            return response.data;
        }).catch(function (error){
            console.log(error);
            return null;
        });
    }





    async getPostUserFromId(idUser){
        var url = this.#postUserFromId+idUser+"/"
        return await axios.get(url,{

        }).then((resp)=>{
             return resp.data.username
        }).catch(function(error){
            console.log(error)
            return null;
        })
    }

    async makeLogout(usernameAndIdUser){
        var url = this.#logoutUrl;
        var user = usernameAndIdUser.user
        var id = usernameAndIdUser.id
        await axios.post(url,{
            user:user,id:id
        },{
            withCredentials: true,
            headers:{
                csrf_token: sessionStorage.getItem('csrf_token')
            }
        }).then(function(response){
            return response.data;
        }).catch(function (error){
            console.log(error);
            return null;
        })
    }

    


}