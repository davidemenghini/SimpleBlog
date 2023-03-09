import { Component } from "react";


export default class AddPostComponent extends Component{


    constructor(props){
        super(props);
        this.setState({
            isUserLogged: props.isUserLogged,
            text: "",
            image : "",
            titolo: "",
            id_author: 0,
            likeNumber: 0,
            dislikeNumber: 0
        })
    }

    render(){

    }

    async componentDidUpdate(prevProps, prevState,snapshot){
        
    }


}