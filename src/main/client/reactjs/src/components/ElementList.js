import React, {Component} from 'react';
import {Card, Table, ButtonGroup, Button} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faEdit, faTrash} from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import MyToast from './MyToast';
import {Link} from 'react-router-dom';



export default class ElementList extends Component {
    constructor(props){
        super(props);
        this.state = {
            elements : []
        };
    }
    componentDidMount(){
        this.findAllElements();
    }
    findAllElements(){
        axios.get("/acs/elements/2020b.ofir.cohen/m@gmail.com")
            .then(response => response.data)
            .then((data) => {
                console.log(data);
                this.setState({elements: data});
            });

    }
    deleteAllElement = () => {
        axios.delete("/acs/admin/elements/2020b.ofir.cohen/a@gmail.com")
            .then(response => {
                if(response.data != null) {
                    this.setState({"show":true});
                    setTimeout(() => this.setState({"show":false}), 3000);
                    this.setState({
                        elements: ""
                    });
                } else {
                    this.setState({"show":false});
                }
            });
    };

    // deleteElement = (elementId) => {
    //     // there is no delete in our Rest so i will not check it...
    //     // axios.delete("http://localhost:8081/rest/books/"+elementId)
    //         .then(response => {
    //             if(response.data != null) {
    //                 this.setState({"show":true});
    //                 setTimeout(() => this.setState({"show":false}), 3000);
    //                 this.setState({
    //                     elements: this.state.elements.filter(element => elementId !== elementId)
    //                 });
    //             } else {
    //                 this.setState({"show":false});
    //             }
    //         });
    // };

    render(){
        return (
            <div>
                <div style={{"display":this.state.show ? "block" : "none"}}>
                <MyToast show = {this.state.show} message = {"Element Deleted Successfully."} type = {"danger"}/>
                </div>
                <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header><FontAwesomeIcon icon={faList}/>Element List</Card.Header>
                <Card.Body>
                    <Table bordered hover striped variant="dark">
                        <thead>
                            <tr>
                            <th>ElementDomain</th>
                            <th>ElementId</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Active</th>
                            <th>Date Created</th>
                            <th>CreatedBy</th>
                            <th>Location</th>
                            <th>Element Attributes</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.elements.length === 0 ?
                                <tr align="center">
                                <td colSpan="10">No Elements Available.</td>
                                </tr> :
                                this.state.elements.map((element) => (
                                <tr key={element.elementId}>
                                    <td> {element.elementId.domain}</td>
                                    <td>{element.elementId.id} </td>
                                    <td>{element.name} </td>
                                    <td>{element.type} </td>
                                    <td>{element.active}</td>
                                    <td>{element.createdTimestamp} </td>
                                    <td>UserDomain: {element.createdBy.userId.domain} Email: {element.createdBy.userId.email} </td>
                                    <td>Lat:{element.location.lat} Lng:  {element.location.lng} </td>
                                    <td>{element.elementAttributes} </td>
                                    <td>
                                            <ButtonGroup>
                                                {/* <Link to={"edit/"+element.elementId} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '} */}
                                                {/* <Link to={"edit/${element.elementId}"} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '} */}
                                                <Link to={"edit/"+element.elementId.domain +"/"+element.elementId.id} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                <Button size="sm" variant="outline-danger" onClick={this.deleteAllElement.bind(this)}><FontAwesomeIcon icon={faTrash} /></Button>
                                            </ButtonGroup>
                                        </td>
                                </tr>
                                ))
                            }
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
        </div>
        );
    }
}

        // ,{
        //     headers : {
        // 'Access-Control-Allow-Origin' : '*',
        // 'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
        //     },
        //     // responseType: 'json',
        // }