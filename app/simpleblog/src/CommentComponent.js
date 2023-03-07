import React,{ Component } from "react";
import PostApi from "./ViewObjects/PostApi";
import UserApi from './ViewObjects/UserApi';
import CommentApi from './ViewObjects/CommentApi'
export default class CommentComponent extends Component{

    constructor(props){
        super(props)
        if (props.isEmpty === true){
            this.state = {
                isEmpty: true,
                isUserLogged: props.isUserLogged
            }
        }else{
            this.state = {
                idPost: props.idPost,
                idA: props.idA,
                text: Buffer.from(props.text, 'utf-8').toString(),
                likeNumber: props.likeNumber,
                dislikeNumber: props.dislikeNumber,
                id: props.id,
                isEmpty: false,
                isUserLogged: props.isUserLogged,
                isLikedToUser: false,
                isDislikedToUser: false
            }
        }
        this.renderUser = this.renderUser.bind(this);
        this.renderTextComment = this.renderTextComment.bind(this);
        this.renderLikeDislikeFeature = this.renderLikeDislikeFeature.bind(this);
        this.fetchIsLikedToUser = this.fetchIsLikedToUser.bind(this);
        this.fetchIsDislikedToUser = this.fetchIsDislikedToUser.bind(this);
        this.renderButtonLikeDislike = this.renderButtonLikeDislike.bind(this);
        this.addLike = this.addLike.bind(this);
        this.addDislike = this.addDislike.bind(this);
    }

    async addLike(evt){
        let idUser = sessionStorage.getItem("idUser");
        let username = sessionStorage.getItem("username");
        if(username!== undefined && idUser!==undefined){
            if(evt.target.value==='mi piace!'){
                let api = new CommentApi();
                let ret = await api.addLike(idUser,this.state.id);
                if(ret===true){
                    this.setState({isLikedToUser: true, likeNumber: this.state.likeNumber+1});
                }
            }else{
                let api = new CommentApi();
                let ret = await api.removeLike(idUser,this.state.id);
                if(ret===true){
                    this.setState({isLikedToUser: false, likeNumber: this.state.likeNumber-1});
                }
            }
        }
    }

    async addDislike(evt){
        let idUser = sessionStorage.getItem("idUser");
        let username = sessionStorage.getItem("username");
        if(username!== undefined && idUser!==undefined){
            if(evt.target.value==='non mi piace!'){
                let api = new CommentApi();
                let ret = await api.addDislike(idUser,this.state.id);
                if(ret===true){
                    this.setState({isDislikedToUser: true, dislikeNumber: this.state.dislikeNumber+1});
                }
            }else{
                let api = new CommentApi();
                let ret = await api.removeDislike(idUser,this.state.id);
                if(ret===true){
                    this.setState({isDislikedToUser: false, dislikeNumber: this.state.dislikeNumber-1});
                }
            }
        }
    }


    render(props){
        if (this.state.isEmpty === true){
            return (<div style={{textAlign:'center'}}>
                Non ci sono commenti...
            </div>)
        }else{
            return(
            <div style={{borderStyle: 'solid', borderColor: '#000000', display: 'inline-block', width:'100%'}} className="rounded">
                <div style={{backgroundColor:'#918B8B'}}>
                    {this.renderUser()}
                    {this.renderTextComment()}
                </div>
                <div style={{}}>
                    {this.renderLikeDislikeFeature()}
                    <br></br>
                    {this.renderButtonLikeDislike()}
                </div>
            </div>)
        }
    }

    async componentDidMount(){
        if(this.state.isEmpty===false){
            var userApi = new UserApi();
            const username = await userApi
                                    .getUserFromId(this.state.idA)
                                    .then((data)=> data.username);
            this.setState({
                username: username
            })
        }
        this.fetchIsLikedToUser();
        this.fetchIsDislikedToUser();
    }

    async componentDidUpdate(prevProps, prevState,snapshot){
        if(this.props.isUserLogged !== prevProps.isUserLogged){
            
            this.fetchIsLikedToUser();
            this.fetchIsDislikedToUser();
        }
    }

    renderUser(){
        return (
        <div style={{color: '#652C2C'}}>
            <p className="font-weight-bold h5">
                <span style={{fontWeight: 'bold'}}>
                {this.state.username}
                </span>
            </p>
        </div>
        )
    }

    renderTextComment(){
        return(
            <div style={{textAlign: 'center'}} className="h6">
                {this.state.text}
            </div>
        )
    }

    renderLikeDislikeFeature(){
        return (
            <div style={{}}>
                {this.state.isLikedToUser===false ? <span style={{float: 'left', marginLeft:'6%'}} className="text-light bg-dark">{this.state.likeNumber} mi piace</span> :
                    <span style={{float: 'left', marginLeft:'6%'}} className="text-white bg-danger">{this.state.likeNumber} mi piace</span>}
                {this.state.isDislikedToUser===false ? <span   style={{float: 'right', marginRight: '6%'}} className="text-light bg-dark">{this.state.dislikeNumber} non mi piace</span> : 
                    <span   style={{float: 'right', marginRight: '6%'}} className="text-white bg-danger">{this.state.dislikeNumber} non mi piace</span>}
            </div>
        )
    }

    renderButtonLikeDislike(){
        return (<div style={{}}>
            {this.state.isLikedToUser===false ?     <button value="mi piace!" className="btn btn-light btn-sm" style={{float: 'left', marginLeft:'6%'}} onClick={(evt)=>this.addLike(evt)}>mi piace!</button> :
                                            <button className="btn btn-light btn-sm" value="annulla mi piace!" style={{float: 'left', marginLeft:'6%'}} onClick={(evt)=>this.addLike(evt)}>annulla mi piace!</button>}
            {this.state.isDislikedToUser===false ?  <button value="non mi piace!" className="btn btn-dark btn-sm" style={{float: 'right', marginRight: '6%'}} onClick={(evt)=>this.addDislike(evt)}>non mi piace!</button> :                                 
                                            <button value="annulla non mi piace!"className="btn btn-dark btn-sm" style={{float: 'right', marginRight: '6%'}} onClick={(evt)=>this.addDislike(evt)}>annulla non mi piace!</button>                                    }        
        </div>)
    }


    async fetchIsLikedToUser(){
        if(this.state.isUserLogged===true){
            var api = new PostApi();
            var retIsLikedToUser = await api.fetchCommentIsLikedToUser(this.state.id,sessionStorage.getItem('idUser'));
            if(retIsLikedToUser==='yes') {
                this.setState({isLikedToUser: true})
            } else{
                this.setState({isLikedToUser: false})
            } 
        }
        
    }

    async fetchIsDislikedToUser(){
        if(this.state.isUserLogged===true){
            var api = new PostApi();
            var retIsDislikedToUser = await api.fetchCommentIsDislikedToUser(this.state.id,sessionStorage.getItem('idUser'));
            if(retIsDislikedToUser==='yes'){
                this.setState({isDislikedToUser: true}) 
            }else{
                this.setState({isDislikedToUser: false})
            }
        }
    }

}