// import React, {Component , useContext} from 'react';
import React, {useState} from 'react';
import axios from 'axios';
import {Route, Redirect , browserHistory} from 'react-router';
import {Card, Form, Button, Col} from 'react-bootstrap';
import Select from 'react-select';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faPlusSquare,faSign} from '@fortawesome/free-solid-svg-icons';
// import MyToast from './MyToast';
// import { UserContext } from './UserContext';
// import { PageContext } from "./PageContextProvider";



const optionsBoolean = [
    {value:'True', label: 'True'},
    {value:'False', label: 'False'},
];

 class Register extends React.Component {

    constructor(props)
    {
        super(props);
        this.state = 
        {
            username:'',
            email: "",
            role: "",
            avatar:""
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        // this.handleChangeSelect = this.handleChangeSelect.bind(this);


    }

    handleChange(event) {
        this.setState({
          [event.target.name]: event.target.value
        });
      }

    //   handleChangeSelect(e) {
    //     let {name, value} = e.target;
    //     this.setState({
    //       [name]: value,
        
    //     });
        
    //     }
      
    handleSubmit(event) {
        const user = {
           email: this.state.email,
           username: this.state.username,
           role: this.state.role,
           avatar: this.state.avatar


        }
        axios.post("/acs/users",user)
            .then((response) => {
                console.log(response.data);
                if (response.data)
                {
                    window.location = '/'
                }
            }).catch((error) => {
                console.error("Error - "+error);
            });
            event.preventDefault();
    }

    render(){
        console.log("render called");
        
        const {email,username,role,avatar} = this.state;


        return (
            <div>
            <Card className={"border border-dark bg-dark text-white"}>
            <Card.Header>
                <FontAwesomeIcon icon={faPlusSquare} /> Login
            </Card.Header>
            <Form onSubmit={this.handleSubmit}>
                <Card.Body>
                    <Form.Row>
                        <Form.Group as={Col} controlId="formGridEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control required
                                type="text" name="email"
                                value={email}
                                onChange={this.handleChange}
                                className={"bg-dark text-white"}
                                placeholder="Enter User Email" />
                        </Form.Group>
                        <Form.Group as={Col} controlId="formGridEmail">
                            <Form.Label>User Name</Form.Label>
                            <Form.Control required
                                type="text" name="username"
                                value={username}
                                onChange={this.handleChange}
                                className={"bg-dark text-white"}
                                placeholder="Enter User name" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} controlId="formGridRole">
                            <Form.Label>Role</Form.Label>
                            <select class="browser-default custom-select"
                            onChange={this.handleChange}
                            name="role"
                            value={role}
                            defaultValue={role}
                            required
                            placeholder="Please Choose Role"
                            >
                                
                            <option value='' selected>Choose Role</option>
                            <option value="ADMIN">Admin</option>
                            <option value="MANAGER">Manager</option>
                            <option value="PLAYER">Player</option>
                            </select>
                            {/* <Form.Control required/> */}
                                {/* <Select
                                name="role"
                                options={optionsBoolean}
                                className={"bg-dark text-white"}
                                onChange={this.handleChange}

                                /> */}
                                 {/* <option selected>Open this select menu</option>
                                <option value="1">Option 1</option>
                                <option value="2">Option 2</option>
                                <option value="3">Option 3</option>
                                <option value="4">Option 4</option>
                                <option value="5">Option 5</option> */}
                            {/* <Form.Control required
                                type="select" name="role"
                                value={role}
                                onChange={this.handleChange}
                                className={"bg-dark text-white"}
                                placeholder="Enter User Role " />
                                <select class="mdb-select md-form colorful-select dropdown-primary">
                                <option value="1">Option 1</option>
                                <option value="2">Option 2</option>
                                <option value="3">Option 3</option>
                                <option value="4">Option 4</option>
                                <option value="5">Option 5</option>
                                </select> */}
                        </Form.Group>
                        <Form.Group as={Col} controlId="formGridAvatar">
                            <Form.Label>Avatar</Form.Label>
                            <Form.Control required
                                type="select" name="avatar"
                                value={avatar}
                                onChange={this.handleChange}
                                className={"bg-dark text-white"}
                                placeholder="Enter Avatar " />
                        </Form.Group>
                    </Form.Row>
                </Card.Body>
                <Card.Footer style={{"textAlign":"right"}}>
                    <Button size="sm" variant="success" type="submit">
                        <FontAwesomeIcon icon={faSign} /> Register
                    </Button>{' '}
                </Card.Footer>
            </Form>
        </Card>
        </div>

            );
            // <Button size="sm" variant="outline-danger" onClick={this.loginUser.bind(this)}><FontAwesomeIcon icon={faPlusSquare} /></Button>
    };


}
export default Register;


{/* <div>
<LoginForm/>
<div style={{"display":this.state.show ? "block" : "none"}}>
<MyToast show = {this.state.show} message = {this.state.method === "put" ? "Login Successfully." : "Login Failed."} type = {"info"}/>
</div>

<form onSubmit={this.handleSubmit}>
 <input
   type="email"
   name="email"
   placeholder="email"
   value={this.state.email}
   onChange={this.handleChange}
   required
 />

 <input
   type="text"
   name="domain"
   placeholder="domain"
   value={this.state.domain}
   onChange={this.handleChange}
   required
 />
 <button type="submit">Login</button>
</form>
</div> */}