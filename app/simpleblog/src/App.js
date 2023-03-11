import './App.css';
import LoginForm from './loginComponent';
import React,{Component} from 'react';
import PostApi from'./ViewObjects/PostApi'
import PostComponent from './PostComponent';
import Post from './ViewObjects/Post';
import axios from "axios"
import UserApi from './ViewObjects/UserApi';
import AddPostComponent from './AddPostComponent';
import {Button}  from "react-bootstrap";
export default class App extends Component{


      constructor(props){
        super(props);
        this.showLoginFun = this.showLoginFun.bind(this);
        this.goToHome = this.goToHome.bind(this);
        this.isLogged = this.isLogged.bind(this);
        this.makeLogout = this.makeLogout.bind(this);
        this.loginClick = this.loginClick.bind(this);
        this.handleAddPost = this.handleAddPost.bind(this);
        this.searchPost = this.searchPost.bind(this);
        this.renderSearchedPosts = this.renderSearchedPosts.bind(this);
        this.state = {
          showLogin: false,
          isUserLogged: false,
          showAddNewPost: false,
          hasBeenSearched: false,
          searchedPosts: []
        }
      }

      handleAddPost(evt){
        this.setState({
          showAddNewPost: !this.state.showAddNewPost
        })
      }
      
      renderSearchedPosts(){
        return (<div>
          {this.state.searchedPosts.map((p)=>(

            <PostComponent key={p.id} isUserLogged={this.state.isUserLogged} likeNumber={p.likeNumber} dislikeNumber={p.dislikeNumber} title={p.title_text} text ={p.data_text} img = {p.data_img} id_author = {p.ida} id = {p.id}/>
          ))}
        </div>)
      }

      render(){
        return(
          <div style={{backgroundColor: '#393B41', minHeight: '100vh', height: '100vh', width: '100vw', margin: 0}}>
            <div style={{border: '2px solid gray'}}>
                  <button type="button" className="btn btn-dark" onClick={this.goToHome}>Home</button>
                  {this.state.isUserLogged===true ? 
                        <button type="button" className="btn btn-dark" onClick={this.makeLogout} >logout</button>: 
                        <button type="button" className="btn btn-dark" onClick={this.showLoginFun} >login</button>}            
                  <input type="text" onChange={evt=>this.searchPost(evt)}  className="form-control"></input>
            </div>
            <br></br>
            <div style={{backgroundColor: '#393B41'}}>
              <div style={{textAlign:'center'}}>
                {this.state.showAddNewPost===false ? <Button variant="secondary" onClick={(evt)=>this.handleAddPost(evt)}> <b>inserisci un nuovo post!</b></Button> 
                          : <Button variant="dark" onClick={(evt)=>this.handleAddPost(evt)}> annulla il nuovo post</Button>}</div>
              <br></br>
              {this.state.showLogin===true && this.state.isUserLogged===false ? <LoginForm isLogged = {this.isLogged} isUserLogged = {true} loginFunc = {this.loginClick}/> : undefined }
              {this.state.showAddNewPost=== true? <AddPostComponent isUserLogged={this.state.isUserLogged}/> : null}
              
              {this.state.posts!== undefined && this.state.hasBeenSearched===false ? <PostComponent isUserLogged={this.state.isUserLogged} likeNumber={this.state.posts[0].getLikeNumber()} dislikeNumber={this.state.posts[0].getDislikeNumber()} title={this.state.posts[0].getTitleText()} text ={this.state.posts[0].getDataText()} img = {this.state.posts[0].getDataImage()} id_author = {this.state.posts[0].getIda()} id = {this.state.posts[0].getId()}/>: null}
              {this.state.posts!== undefined && this.state.hasBeenSearched===false ? <PostComponent isUserLogged={this.state.isUserLogged} likeNumber={this.state.posts[1].getLikeNumber()} dislikeNumber={this.state.posts[1].getDislikeNumber()} title={this.state.posts[1].getTitleText()} text ={this.state.posts[1].getDataText()} img = {this.state.posts[1].getDataImage()} id_author = {this.state.posts[1].getIda()} id = {this.state.posts[1].getId()}/>: null}
              {this.state.hasBeenSearched===true && this.state.searchedPosts !== undefined && this.state.searchedPosts.length >0 ? 
                              this.renderSearchedPosts():
                               null}
            </div>
        </div>  
        );
      }

      showLoginFun(){
          this.setState( {
            showLogin: !this.state.showLogin
          }); 
      }


      goToHome(){
        this.setState({
          showLogin: false
        });
      }

      isLogged(){
        this.setState({
          isUserLogged: !this.state.isUserLogged
        });
        console.log("user loggato...");
      }
      

      componentDidMount(){
        let self = this;
        axios.post(new PostApi().randomPostUrl, {

        }).then(function (response){
          var p1Obj = JSON.parse(response.data[0])
          var p2Obj = JSON.parse(response.data[1])
          var p1 = new Post(p1Obj.id,p1Obj.id_author,p1Obj.dataText,p1Obj.titleText);
          p1.setLikeNumber(p1Obj.likeNumber);
          p1.setDislikeNumber(p1Obj.dislikeNumber);
          p1.setDataImage(p1Obj.data_img)
          var p2 = new Post(p2Obj.id,p2Obj.id_author,p2Obj.dataText,p2Obj.titleText);
          p2.setDataImage(p2Obj.data_img);
          p2.setLikeNumber(p2Obj.likeNumber);
          p2.setDislikeNumber(p2Obj.dislikeNumber);
          self.setState({
            posts: [p1,p2]
          });
        }).catch(function(error){
          console.log(error)
        });

      }


      async searchPost(evt){
        let value = evt.target.value;
        if(value===''){
          this.setState({hasBeenSearched:false,searchedPosts: []})
          //this.componentDidMount()
        }else{
          let api = new PostApi();
          let ret = await api.searchPost(value);
          let arrPosts = []
          if(ret!==undefined){
            console.log("size: "+ret.length)
              for(let i=0;i<ret.length;i++){
                let json = JSON.parse(ret[i]);
                var p = new Post(json.id,json.id_author,json.dataText,json.titleText);
                p.setLikeNumber(json.likeNumber);
                p.setDislikeNumber(json.dislikeNumber);
                p.setDataImage(json.data_img)
                arrPosts[i] = p
              }
              this.setState({hasBeenSearched:true, searchedPosts: arrPosts})
            console.log("arr"+JSON.stringify(arrPosts))
          }
          //this.setState({hasBeenSearched:true, posts: []})
        }
      }


      async makeLogout(){
        var userApi = new UserApi();
        console.log({user:sessionStorage.getItem("username"), id:sessionStorage.getItem("idUser")})
        var ret = await userApi.makeLogout({user:sessionStorage.getItem("username"), id:sessionStorage.getItem("idUser")})
        if(ret !== null || ret!==undefined){
          this.setState({
            isUserLogged: false,
            showLogin: false
          });
          console.log("ok");
        }else{
          console.log("error");
        }
      }

      async loginClick(u,p){
        console.log("parent: "+u+" "+p)
        var userApi = new UserApi();
        if(u!== "" && p !== ""){
            let ret = await userApi.makeLogin({user:u,psw:p});
            console.log(ret)
            if(ret===null || ret===undefined){
              console.log("errore nel login");
              }else{
                var csrf_token = await userApi.getCsrfToken({user:u,psw:p});
                console.log(ret)
                sessionStorage.setItem("idUser",ret.id);
                sessionStorage.setItem("username",u);
                this.setState({
                    isUserLogged: true
                })
              }
        }else{
                
        }    
    }

      


}

