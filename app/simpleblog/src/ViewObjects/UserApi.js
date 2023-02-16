import axios from "axios";

export default class UserApi{


    #loginUrl = "http://localhost:8080/api/public/login/";

    constructor(){

    }

    makeLogin(userAndPass){
        console.log(this.#loginUrl);
        axios.post(this.#loginUrl,{
            userAndPass
        }).then(function (response){
            console.log(response);
            return response;
        }).catch(function (error){
            //console.log(error);
            return null;
        });
    }

    


}