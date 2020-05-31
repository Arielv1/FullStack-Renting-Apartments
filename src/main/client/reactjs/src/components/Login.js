// import React, {Component , useContext} from 'react';
import React from 'react';
import axios from 'axios';
import {Route, Redirect , browserHistory} from 'react-router';
import {Card, Form, Button, Col} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faPlusSquare,faSign} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import { UserContext } from './UserContext';
import {loginUser} from './loginUser';
import {loginsetUser} from './loginsetUser';
import {LoginN} from './loginN';



import LoginForm from './LoginForm';
import Welcome from './Welcome';
import Profile from './Profile';
// import { PageContext } from "./PageContextProvider";





 class Login extends React.Component {

    static contextType = UserContext;

    constructor(props)
    {
        super(props);
        this.state = 
        {
            user:'',
            email: "",
            domain: "",
            loginErrors: "",
            redirects: null
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);

    }
    handleChange(event) {
        this.setState({
          [event.target.name]: event.target.value
        });
      }
      
    handleSubmit(event) {
        const { email, domain } = this.state;

        console.log("we in handle submit try to login");
        console.log("email and domain is :");
        console.log(email);
        console.log(domain);
        axios.get("/acs/users/login/"+domain +"/"+email)
        // axios.get("/acs/users/login/2020b.ofir.cohen/m@gmail.com")
            .then((response) => {
                const data = response.data
                console.log(data);
                // this.setState({
                //     email: data.userId.email,
                //     domain: data.userId.domain
                // });
                console.log(data.userId.email);
                if (data.userId.email)
                {
                    console.log(data.userId);
                    // console.log(user)
                    // <Route path="/profile" exact component={Profile}/>
                    // this.setState({ redirects: "/profile" });
                    // LoginN();

                    // use Function and passing to variable theUser
                    // <LoginN theUser= {data.userId.email}/>

                    // window.location = '/profile'
                    // document.cookie = 'userEmail='+data.userId.email
                    // window.location = '/profile/'+data.userId.email;
                }
                // else
                // {
                //     console.log("we get to else");
                //     document.cookie = 'userEmail='+""
                //     console.log(document.cookie);
                //     this.setState({"show":false});
                // }
            }).catch((error) => {
                console.log("Error - "+error);
            });
            event.preventDefault();
    }

    render(){
        console.log("render called");
        console.log(this.context);
        console.log(this.context.user = 'shalom');



        const {email,domain} = this.state;
          
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
                                placeholder="Enter User email" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} controlId="formGridDomain">
                            <Form.Label>Domain</Form.Label>
                            <Form.Control required
                                type="text" name="domain"
                                value={domain}
                                onChange={this.handleChange}
                                className={"bg-dark text-white"}
                                placeholder="Enter User Domain " />
                        </Form.Group>
                    </Form.Row>
                </Card.Body>
                <Card.Footer style={{"textAlign":"right"}}>
                    <Button size="sm" variant="success" type="submit">
                        <FontAwesomeIcon icon={faSign} /> Login
                    </Button>{' '}
                </Card.Footer>
            </Form>
        </Card>
        </div>

            );
            // <Button size="sm" variant="outline-danger" onClick={this.loginUser.bind(this)}><FontAwesomeIcon icon={faPlusSquare} /></Button>
    };


}
export default Login;


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