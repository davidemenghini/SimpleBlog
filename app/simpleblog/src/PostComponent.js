import React,{ Component } from "react";
import Post from './ViewObjects/Post'



export default class PostComponent extends Component{


    constructor(props){
        super(props)
        this.showCommentsHandler = this.showCommentsHandler.bind(this);
        this.state = {
            id: props.id,
            ida: props.ida,
            title: Buffer.from(props.title, 'utf-8').toString(),
            img: props.img,
            text:  Buffer.from(props.text, 'utf-8').toString(),
            showComments: false,
            Comments: []
        };

    }


    render(props){
        return (
        <div className="rounded" style={{backgroundColor: 'gray' ,marginLeft: '25%', marginRight: '25%', marginTop: '10pt'}}>
            <div style={{textAlign: 'center'}}>
                <h3>{this.state.title}</h3>
                <span>{this.state.text}</span>
                {this.state.img!=='' ? <img src={'data:image/jpeg;base64,${'+this.state.img+'}'}></img>: null}
            </div>
            
            <br></br>
            <div /*style={{textAlign: 'center'}}*/>
                <span style={{}}>
                    <button className="btn btn-light" style={{marginLeft:'5%'}}>mi piace!</button>
                    <button style={{float:'right', marginRight:'5%'}} className="btn btn-dark">non mi piace!</button>
                </span>
                <br></br>
                <br></br>
                <span style={{textAlign: 'center'}}>
                    <button className="btn btn-light" onClick={this.showCommentsHandler()} style={{}}>mostra commmenti</button>
                    <button className="btn btn-light" style={{}}>aggiungi un commento...</button>
                </span>
            </div>
            
            
        </div>)
    }


    showCommentsHandler(){
        console.log('ciao')
    }


}