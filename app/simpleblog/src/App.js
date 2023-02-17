import './App.css';
import LoginForm from './loginComponent';
import React,{Component} from 'react';
import PostApi from'./ViewObjects/PostApi'
import PostComponent from './PostComponent';
import Post from './ViewObjects/Post';
import axios from "axios"
export default class App extends Component{


      constructor(props){
        super(props);
        this.showLoginFun = this.showLoginFun.bind(this);
        this.goToHome = this.goToHome.bind(this);
        this.state = {
          showLogin: false
        }
      }

      render(){
        return(
          <div style={{backgroundColor: '#393B41', minHeight: '100vh', height: '100%', width: '100%', margin: 0}}>
            <div style={{border: '2px solid gray'}}>
                  <button type="button" className="btn btn-dark" onClick={this.goToHome}>Home</button>
                  <button type="button" className="btn btn-dark" onClick={this.showLoginFun} >login</button>            
            </div>
            <br></br>
            <div style={{backgroundColor: '#393B41'}}>
              {this.state.showLogin===true ? <LoginForm/> : undefined }
              {this.state.posts!== undefined ? <PostComponent title={this.state.posts[0].getTitleText()} text ={this.state.posts[0].getDataText()} img = {this.state.posts[0].getBase64DataImg()} id_author = {this.state.posts[0].getIda()} id = {this.state.posts[0].getId()}/>: null}
              
              {this.state.posts!== undefined ? <PostComponent title={this.state.posts[1].getTitleText()} text ={this.state.posts[1].getDataText()} img = {this.state.posts[1].getBase64DataImg()} id_author = {this.state.posts[1].getIda()} id = {this.state.posts[1].getId()}/>: null}
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


      

      componentDidMount(){
        let self = this;
        axios.post(new PostApi().randomPostUrl, {

        }).then(function (response){
          /*var p1 = new Post(response.data[0].id,response.data[0].id_author,response.data[0].data_text,response.data[0].title_text);
          p1.setRawDataImg(response.data[0].data_img)*/
          /*var p2 = new Post(response.data[1].id,response.data[1].id_author,response.data[1].data_text,response.data[1].title_text);
          p2.setRawDataImg(response.data[1].data_img);*/
          var p1Obj = JSON.parse(response.data[0])
          var p2Obj = JSON.parse(response.data[1])
          var p1 = new Post(p1Obj.id,p1Obj.id_author,p1Obj.data_text,p1Obj.title_text);
          p1.setRawDataImg(p1Obj.data_img)
          var p2 = new Post(p2Obj.id,p2Obj.id_author,p2Obj.data_text,p2Obj.title_text);
          p2.setRawDataImg(p2Obj.data_img);
          self.setState({
            posts: [p1,p2]
          });
        }).catch(function(error){
          console.log(error)
        });

      }

      

      


}

