import axios from "axios";

export default class UserApi{


    #loginUrl = "http://localhost:8080/api/public/login/";
    
    #userFromId = "http://localhost:8080/api/public/user/";

    #csrfTokenUrl = "http://localhost:8080/api/public/csrf/";

    #logoutUrl = "http://localhost:8080/api/private/logout/"


    async makeLogin(userAndPass){
        var user = userAndPass.user
        var psw = userAndPass.psw
        let returningValue = null;
        await axios.post(this.#loginUrl,{
            user:user, psw:psw
        },{
            withCredentials: true
        }).then(function (response){
            console.log(response)
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
            console.log(response.headers);
            return response.data;
        }).catch(function (error){
            console.log(error);
            return null;
        });
    }





    async getUserFromId(idUser){
        var url = this.#userFromId+idUser+"/"
        return axios.post(url,{

        }).then((resp)=>{
             return resp.data
        }).catch(error=>console.log(error))
    }

    async makeLogout(usernameAndIdUser){
        var url = this.#logoutUrl;
        var user = usernameAndIdUser.user
        var id = usernameAndIdUser.id
        await axios.post(url,{
            user:user,id:id
        },{
            withCredentials: true
        }).then(function(response){
            return response.data;
        }).catch(function (error){
            console.log(error);
            return null;
        })
    }


}