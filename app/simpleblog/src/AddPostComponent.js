import React ,{ Component } from "react";
import{Button} from "react-bootstrap"
import PostApi from "./ViewObjects/PostApi";
export default class AddPostComponent extends Component{


    constructor(props){
        super(props);
        if(props.isUserLogged===false){
            this.state= ({
                isUserLogged: props.isUserLogged,
                text: "",
                image : [],
                titolo: "",
                id_author: 0,
                likeNumber: 0,
                dislikeNumber: 0,
                commentHasBeenSend: false
            });
        }else{
            this.state= ({
                commentHasBeenSend: false,
                isUserLogged: props.isUserLogged,
                text: "",
                image : [],
                titolo: "",
                id_author: sessionStorage.getItem('idUser'),
                likeNumber: 0,
                dislikeNumber: 0
            });
        }
        
        this.handleChangeTitle = this.handleChangeTitle.bind(this);
        this.handleChangeText = this.handleChangeText.bind(this);
        this.handleChangeImg = this.handleChangeImg.bind(this);
        this.addNewPost = this.addNewPost.bind(this);
    }


    

    async addNewPost(){
        if(this.state.text!=="" &&  this.state.id_author!==0 && this.state.titolo!==""){
            let idUser = sessionStorage.getItem("idUser"); 
            if(idUser!==undefined){
                let utf8Encode = new TextEncoder();
                let data_text = utf8Encode.encode(this.state.text,);
                let title_text = utf8Encode.encode(this.state.titolo);
                let dt = new Array(data_text.length);
                let tt = new Array(title_text.length)
                for(let i=0;i<data_text.length;i++){
                    dt[i] = data_text[i]
                }
                for(let i=0;i<title_text.length;i++){
                    tt[i] = title_text[i]
                }
                const post = {
                    dataText: dt,
                    titleText: tt,
                    id_author: idUser,
                    likeNumber: 0,
                    dislikeNumber: 0,
                    data_img: this.state.image
                }
                let api = new PostApi();
                await api.createPostFromJson(post)
                this.setState({commentHasBeenSend: true})
            }
        }
    }

    async handleChangeImg(evt){
        let f = evt.target.files[0];
        var reader = new FileReader();
        reader.onloadend= ()=>{
            let utf8Encode = new TextEncoder();
            let utf8Byte = utf8Encode.encode(reader.result)
            let j= []
            for(let i=0;i<utf8Byte.length;i++){
                j[i] = utf8Byte[i]
            }
            this.setState({image: j});
        };
       reader.readAsDataURL(f)
        evt.preventDefault();
    }
    

    handleChangeText(evt){
        this.setState({text: evt.target.value})
        evt.preventDefault();
    }

    render(props){
        if(this.state.commentHasBeenSend===false){
            return (<div style={{backgroundColor:'gray',textAlign:'center',float:'center', marginLeft:'25%',marginRight:'25%',border: '2px solid gray'}} className="rounded shadow p-3 mb-5">
                <span>
                    <b>titolo</b>
                </span>
                <br></br>
                <textarea type="text" onChange={(evt)=>this.handleChangeTitle(evt)} style={{padding:'5px',minHeight:'16px',lineHeight:'16px',width:'96%',display:'block', margin:'0px auto' }} className="form-control" placeholder="inserisci qui il titolo del post..."></textarea>            
                <span>
                    <b>testo:</b>
                </span>
                <textarea type="text" onChange={(evt)=>this.handleChangeText(evt)} style={{padding:'5px',minHeight:'16px',lineHeight:'16px',width:'96%',display:'block', margin:'0px auto' }} className="form-control" placeholder="inserisci qui il titolo del post..."></textarea>            
                <br></br>
                <input className="form-control" accept=".jpg, .jpeg, .png" type="file" name="img" onChange={(evt)=>this.handleChangeImg(evt)}></input>
                <br></br>
                <Button variant="secondary" onClick={()=>{this.addNewPost();
                                                        setTimeout(5000);
                                                        this.props.showAddNewPost()}}>CREA IL POST!</Button>
                

        </div>)
        }else{
            return null;
        }
        
    }

    async componentDidUpdate(prevProps, prevState,snapshot){
        if(prevProps.isUserLogged!== this.props.isUserLogged){
            this.setState({isUserLogged: this.props.isUserLogged, id_author: this.props.isUserLogged})
        }
    }


    handleChangeTitle(evt){

        this.setState({titolo: evt.target.value})
    }
}