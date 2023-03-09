import React,{ Component } from "react";
import CommentComponent from "./CommentComponent";
import axios from "axios";
import Comment from "./ViewObjects/Comment";
import PostApi from "./ViewObjects/PostApi";
import CommentApi from "./ViewObjects/CommentApi";

export default class PostComponent extends Component{


    constructor(props){
        super(props)
        this.showCommentsHandler = this.showCommentsHandler.bind(this);
        this.fetchCommentsFromPost = this.fetchCommentsFromPost.bind(this);
        this.updateEmptyComments = this.updateEmptyComments.bind(this)
        this.updateNotEmptyComments = this.updateNotEmptyComments.bind(this);
        this.renderMultipleComments = this.renderMultipleComments.bind(this);
        this.showAddNewComment = this.showAddNewComment.bind(this);
        this.renderAddNewComment = this.renderAddNewComment.bind(this);
        this.addNewLike = this.addNewLike.bind(this);
        this.addNewDislike = this.addNewDislike.bind(this);
        this.addNewComment = this.addNewComment.bind(this);
        this.updateNewCommentText = this.updateNewCommentText.bind(this);
        this.state = {
            id: props.id,
            ida: props.ida,
            title: Buffer.from(props.title, 'utf-8').toString(),
            img: props.img,
            text:  Buffer.from(props.text, 'utf-8').toString(),
            showComments: false,
            Comments: [],
            likeNumber: props.likeNumber,
            dislikeNumber: props.dislikeNumber,
            showAddNewComment: false,
            isUserLogged: props.isUserLogged,
            isLikedToUser: false,
            isDislikedToUser: false,
            showLikeAlert: false,
            showDislikeAlert: false,
            newCommentText: ""
        };

    }

    async addNewComment(evt){
        if(this.state.newCommentText!==""){
            let idUser = sessionStorage.getItem("idUser");
            let utf8Encode = new TextEncoder();
            let textArr = utf8Encode.encode(this.state.newCommentText)
            let ta = []
            for(let i=0;i<textArr.length;i++){
                ta[i] = textArr[i]
            }
            var comment = {
                idPost: this.state.id,
                idAuthor: idUser,
                like_number: 0,
                dislike_number: 0,
                textComment: ta 
            };
            await new CommentApi().createComment(comment);
        }
    }

    updateNewCommentText(evt){
        this.setState({newCommentText: evt.target.value})
        evt.preventDefault();
    }
    


    async componentDidUpdate(prevProps, prevState,snapshot){
        const delay = ms => new Promise(res => setTimeout(res, ms));
        if(this.props.isUserLogged !== prevProps.isUserLogged){
            this.setState({
                isUserLogged: this.props.isUserLogged
            });
            if(this.props.isUserLogged===true){
                await delay(2000)
                var postApi = new PostApi();
                var respIsLiked = await postApi.isLikedToUser(sessionStorage.getItem('idUser'),this.state.id);
                var respIsDisliked = await postApi.isDislikedToUser(sessionStorage.getItem('idUser'),this.state.id)
                respIsLiked==='yes' ? this.setState({isLikedToUser: true}) : this.setState({isLikedToUser: false})           
                respIsDisliked==='yes'? this.setState({ isDislikedToUser: true}):this.setState({isDislikedToUser: false}) 
            }else{
                this.setState({
                    isLikedToUser: false,
                    isDislikedToUser: false
                });
            }
            
        }
    }

    render(props){
        return (
        <div className="rounded shadow p-3 mb-5" style={{backgroundColor: 'gray',float:'center' ,marginLeft: '25%', marginRight: '25%'}}>
            <div style={{textAlign: 'center'}}>
                <h3>{this.state.title}</h3>
                <span>{this.state.text}</span>
                {this.state.img!=='' ? <img src={'data:image/jpeg;base64,${'+this.state.img+'}'}></img>: null}
            </div>
            
            <br></br>
            <div>
                <span style={{}}>
                    {this.state.isLikedToUser===false ? 
                        <span className="text-light bg-black" style={{float:'left', marginLeft:'6%'}} >{this.state.likeNumber} mi piace</span>:
                        <span className="text-white bg-danger" style={{float:'left', marginLeft:'6%'}} >{this.state.likeNumber} mi piace</span>}
                    {this.state.isDislikedToUser===false ? 
                        <span className="text-light bg-black" style={{float:'right', marginRight:'6%'}}>{this.state.dislikeNumber} non mi piace</span>:
                        <span className="text-white bg-danger" style={{float:'right', marginRight:'6%'}}>{this.state.dislikeNumber} non mi piace</span>}
                    <br></br>
                    {this.state.isLikedToUser===false ? <button value="mi piace!" className="btn btn-light" onClick={(evt)=>{this.addNewLike(evt)}} style={{marginLeft:'5%'}}>mi piace!</button> : 
                        <button value="annulla mi piace!" className="btn btn-light" onClick={(evt)=>{this.addNewLike(evt)}} style={{marginLeft:'5%'}}>annulla mi piace!</button>}
                    {this.state.isDislikedToUser===false ? <button value="non mi piace!" style={{float:'right', marginRight:'5%'}} className="btn btn-dark" onClick={(evt)=>this.addNewDislike(evt)}>non mi piace!</button> : 
                        <button value="annulla non mi piace!"style={{float:'right', marginRight:'5%'}} className="btn btn-dark" onClick={(evt)=>this.addNewDislike(evt)}> annulla non mi piace!</button> }
                </span>
                <br></br>
                <br></br>
                <span style={{textAlign: 'center'}}>
                    {this.state.showComments === true ? <button type="button" className="btn btn-light btn-block" onClick={(evt)=>this.showCommentsHandler(evt)} style={{width: '50%'}}>nascondi commmenti</button>:
                                                        <button type="button" className="btn btn-light btn-block" onClick={(evt)=>this.showCommentsHandler(evt)} style={{width: '50%'}}>mostra commmenti</button>}
                    <button type="button" className="btn btn-light btn-block" style={{width: '50%'}} onClick={(evt)=>this.showAddNewComment()}>aggiungi un commento...</button>
                </span>
            </div>
            <div>
                {this.state.showAddNewComment ? (this.renderAddNewComment()) :null}
                {this.state.showComments===true ?
                        this.state.Comments.length !==0 ? 
                             (this.renderMultipleComments())
                        :
                            (<CommentComponent isUserLogged={this.state.isUserLogged} isEmpty={true}/>)
                    : null}
            </div>
            
        </div>)
    }


    async addNewLike(evt){
        var idUser = sessionStorage.getItem("idUser");
        var username = sessionStorage.getItem("username");
        console.log('controllando id e username...'+ evt.target.value)
        if(username!== undefined && idUser!==undefined){
            console.log("value: "+evt.target.value)
            if(evt.target.value==='mi piace!'){
                var apiPost = new PostApi();
                var ret = await apiPost.addLikePost(this.state.id,idUser);
                if(ret===true){
                    this.setState({likeNumber: this.state.likeNumber+1,isLikedToUser: true}) 
                }else{

                }
            }else{
                var apiPost = new PostApi();
                var ret = await apiPost.removeLikePost(this.state.id,idUser);
                if(ret===true){
                    this.setState({likeNumber: this.state.likeNumber-1,isLikedToUser: false}) 
                }else{
                    
                }
            }
           
        }
    }


    async addNewDislike(evt){
        var idUser = sessionStorage.getItem("idUser");
        var username = sessionStorage.getItem("username");
        console.log('controllando id e username...')
        if(username!== undefined && idUser!==undefined){
            if(evt.target.value==='non mi piace!'){
                var apiPost = new PostApi();
                var ret = await apiPost.addDislikeToPost(this.state.id,idUser);
                if(ret===true){
                    this.setState({dislikeNumber: this.state.dislikeNumber+1,isDislikedToUser: true})
                }else{

                }
                    
            }else{
                var apiPost = new PostApi();
                var ret = await apiPost.removeDislikePost(this.state.id,idUser);
                if(ret===true){
                    this.setState({dislikeNumber: this.state.dislikeNumber-1,isDislikedToUser: false}) 
                }else{
                    
                }
            }
           
        }
    }


    renderMultipleComments(){
        return (<div>
            {this.state.Comments.map((value)=>(
                    <CommentComponent key= {value.id} isUserLogged={this.state.isUserLogged} idPost= {value.idPost} idA= {value.ida} text= {value.text} likeNumber= {value.likeNumber} dislikeNumber= {value.dislikeNumber} id= {value.id} isEmpty= {false}/>
        ))}</div>)
    }

    showCommentsHandler(){
        if(this.state.showComments===true){
            this.setState({
                showComments: false
            })
        }else{
            var url = "http://localhost:8080/api/public/comment/"+this.state.id+"/"
            this.fetchCommentsFromPost(url)
        }
    }

    fetchCommentsFromPost(path){
        axios.post(path,{
            headers: {
                'Accept-Encoding': 'application/json'
            }
        }).then(response=>{
            if(response.data.length === 0){
                this.updateEmptyComments();
            }else{
                this.updateNotEmptyComments(response.data)
            }
        }).catch(error=>{
            console.log(error)
        })
    }

    /**
     * 
     */
    updateEmptyComments(){
        this.setState({
            showComments: true,
            Comments: []
        })
    }

    /**
     * 
     * @param {String} commentsJsonList 
     */
    updateNotEmptyComments(commentsJsonList){
        var c = new Comment();
        var arr = [];
        console.log("size: "+commentsJsonList.length+"\n updateNotEmptyComm: "+commentsJsonList)
        for(var i=0; i<commentsJsonList.length;i++){
            const obj = JSON.parse(commentsJsonList[i])
            c.setId(obj.id);
            c.setIdPost(obj.idPost);
            c.setIda(obj.idAuthor);
            c.setDislikeNumber(obj.dislike_number);
            c.setLikeNumber(obj.like_number);
            c.setText(obj.textComment);
            arr[i] = c
            c = new Comment();
        }
        console.log("arr: "+arr)
        this.setState({
            Comments: arr,
            showComments: true
        })
    }

    showAddNewComment(evt){
        this.setState({
            showAddNewComment: !this.state.showAddNewComment
        })
    }


    renderAddNewComment(){
        return (
        <div style={{textAlign:'center'}}>
            <input type="text" placeholder =" inserisci qui il tuo commento..." className="form-control" onChange={(evt)=>this.updateNewCommentText(evt
                )}>
            </input>
            <br></br>
            <button type="button" className="btn btn-light" onClick={(evt)=>this.addNewComment(evt)}>inserisci commento</button>
        </div>)
    }
}