import React,{ Component } from "react";
import UserApi from './ViewObjects/UserApi'
export default class CommentComponent extends Component{

    constructor(props){
        super(props)
        console.log("comment "+Buffer.from(new Uint8Array(props.text), 'utf-8'))
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
                isUserLogged: props.isUserLogged
            }
        }
        this.renderUser = this.renderUser.bind(this);
        this.renderTextComment = this.renderTextComment.bind(this);
        this.renderLikeDislikeFeature = this.renderLikeDislikeFeature.bind(this);
    }


    render(props){
        if (this.state.isEmpty === true){
            return (<div style={{textAlign:'center'}}>
                Non ci sono commenti...
            </div>)
        }else{
            return(
            <div style={{borderStyle: 'solid', borderColor: '#000000'}} className="rounded">
                <div style={{backgroundColor:'#918B8B'}}>
                    {this.renderUser()}
                    {this.renderTextComment()}
                </div>
                {this.renderLikeDislikeFeature()}
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
            <div>
                <p>
                    mi piace
                </p>
                <p>
                    non mi piace
                </p>
            </div>
        )
    }



}