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
            isLoading: false,
            isUserLogged: props.isUserLogged
        };
    }
    

    render(props) {
        return (
        <div>    
        {this.state.isLoading===false ? 
            <div className="rounded" style={{backgroundColor: 'gray',width: '100%',textAlign: 'center',marginBottom: '10pt',padding: '0'}}>
                <b>Inserisci i dati...</b><br></br>
                <b>Username:</b><br></br>
                <input onChange={evt => this.userChangeHandler(evt)} type="text" name="user"></input><br></br>
                <b>Password:</b><br></br>
                <input  onChange={evt => this.passChangeHandler(evt)} type="password" name="pass"></input><br></br>
                <button className="btn btn-success" onClick={()=>this.props.loginFunc(this.state.user,this.state.pass)}>Effettua il login!</button><br></br>
            </div> : 
            <div className="spinner-border text-primary" role="status" style={{position: 'fixed',width: '100%',textAlign: 'center'}}>
                <h1 className="sr-only">Loading...</h1>
            </div>};
        </div>
        )
    }

    
    async loginClick(user,pass){
        this.setState({
            isLoading: true
        })
        if(user!== "" && pass !== ""){
            var ret = await this.state.api.makeLogin({user:this.state.user,psw:this.state.pass});
            if(ret===null){
                this.setState({
                    isLoading: false
                })
                return;
            }else{
                this.setState({
                    isLoading: false
                })
            }
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

