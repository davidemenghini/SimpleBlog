import React from 'react'
import UserApi from './ViewObjects/UserApi';
export default class LoginForm extends React.Component{


    constructor(props){
        super(props)
        this.loginClick = this.loginClick.bind(this);
        this.userChangeHandler = this.userChangeHandler.bind(this);
        this.passChangeHandler = this.passChangeHandler.bind(this);
        this.state = {
            user : "",
            pass : "",
            api : new UserApi(),
            isLoading: false
        };
    }

    render() {
        return (
        <div>    
        {this.state.isLoading===false ? 
            <div className="rounded" style={{backgroundColor: 'gray',width: '100%',textAlign: 'center',marginBottom: '10pt',padding: '0'}}>
                <b>Inserisci i dati...</b><br></br>
                <b>Username:</b><br></br>
                <input onChange={evt => this.userChangeHandler(evt)} type="text" name="user"></input><br></br>
                <b>Password:</b><br></br>
                <input  onChange={evt => this.passChangeHandler(evt)} type="text" name="pass"></input><br></br>
                <button className="btn btn-success" onClick={(user,pass)=>this.loginClick(user,pass)}>Effettua il login!</button><br></br>
                <button className="btn btn-warning">Cancella dati!</button><br></br>
            </div> : 
            <div className="spinner-border text-primary" role="status" style={{position: 'fixed',width: '100%',textAlign: 'center'}}>
                <h1 className="sr-only">Loading...</h1>
            </div>};
        </div>
        )
    }

    
    loginClick(user,pass){
        this.setState({
            isLoading: true
        })
        console.log(this.state.isLoading);
        if(user!== "" && pass !== ""){
            var jsonLogin = JSON.stringify({user:this.state.user,psw:this.state.pass});
            var ret = this.state.api.makeLogin(jsonLogin);
            console.log(ret)
            if(ret===null){
                this.setState({
                    isLoading: false
                })
                console.log(this.state.isLoading);
                return;
            }else{
                console.log(this.state.isLoading);
                return;
            }
            }else{
                
            }    
    }

    passChangeHandler(evt){
        this.setState({
            pass: evt.target.value
        });
        evt.preventDefault();
    }

    userChangeHandler(evt){
        this.setState({
            user:evt.target.value
        });
        evt.preventDefault();
    }

}

