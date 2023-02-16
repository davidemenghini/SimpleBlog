import React,{ Component } from "react";
import Post from './ViewObjects/Post'



export default class PostComponent extends Component{


    constructor(props){
        super(props)
        this.state = {
            id: props.id,
            ida: props.ida,
            title: Buffer.from(props.title, 'utf-8').toString(),
            img: props.img,
            text:  Buffer.from(props.text, 'utf-8').toString()
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
            <div style={{textAlign: 'center'}}>
                <span style={{}}>
                    <button className="btn btn-light">mi piace!</button>
                    <button style={{marginLeft:'5%'}}className="btn btn-dark">non mi piace!</button>
                </span>
                <br></br>
                <br></br>
                <span style={{position: 'center'}}>
                <button className="btn btn-light">aggiungi un commento...</button>
                </span>
            </div>
            
            
        </div>)
    }


}