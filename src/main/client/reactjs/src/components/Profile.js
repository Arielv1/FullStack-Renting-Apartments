import React, {Component} from 'react';
// import {Card, Form, Button, Col} from 'react-bootstrap';
// import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
// import {faSave, faPlusSquare, faUndo, faList, faEdit} from '@fortawesome/free-solid-svg-icons';
// import { UserContext } from './UserContext';


// import MyToast from './MyToast';
// import axios from 'axios';




export default class Profile extends Component {

    constructor(props) {
        super(props);

    }

    render(){
        return (
            <div>
                <h2> Welcome Profile {this.props.match.params.email}</h2>
             </div>

        );
    }
}
    
