import React,{ Component } from "react";
import CommentComponent from "./CommentComponent";
import axios from "axios";
import Comment from "./ViewObjects/Comment";

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
            isUserLogged: props.isUserLogged
        };

    }


    render(props){
        return (
        <div className="rounded shadow p-3 mb-5" style={{backgroundColor: 'gray' ,marginLeft: '25%', marginRight: '25%', marginTop: '10pt'}}>
            <div style={{textAlign: 'center'}}>
                <h3>{this.state.title}</h3>
                <span>{this.state.text}</span>
                {this.state.img!=='' ? <img src={'data:image/jpeg;base64,${'+this.state.img+'}'}></img>: null}
            </div>
            
            <br></br>
            <div>
                <span style={{}}>
                    <span className="text-light bg-black" style={{float:'left', marginLeft:'6%'}} onClick={}>{this.state.likeNumber} mi piace</span>
                    <span className="text-light bg-black" style={{float:'right', marginRight:'6%'}}>{this.state.dislikeNumber} non mi piace</span>
                    <br></br>
                    <button className="btn btn-light" style={{marginLeft:'5%'}}>mi piace!</button>
                    <button style={{float:'right', marginRight:'5%'}} className="btn btn-dark">non mi piace!</button>
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
        idUser = sessionStorage.getItem("idUser");
        username = sessionStorage.getItem("username");
        if(username!== undefined && idUser===undefined){
            //TODO 
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
                console.log(response)
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
            console.log(i)
            console.log("first comments array type "+typeof(commentsJsonList[0]))
            const obj = JSON.parse(commentsJsonList[i])
            console.log(obj)
            c.setId(obj.id);
            c.setIdPost(obj.idPost);
            c.setIda(obj.idAuthor);
            c.setDislikeNumber(obj.dislike_number);
            c.setLikeNumber(obj.like_number);
            c.setText(obj.textComment);
            console.log("C: "+c)
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
            <input type="text" placeholder =" inserisci qui il tuo commento..." className="form-control">
            </input>
            <br></br>
            <button type="button" className="btn btn-light">inserisci commento</button>
        </div>)
    }
}