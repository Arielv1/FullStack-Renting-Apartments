import React, {Component} from 'react';
import {Navbar, Nav} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import { UserContext } from './UserContext';


export default class NavigatorBar extends Component {

    static contextType = UserContext;

    render() {
        console.log("navigation bar");
        // console.log(this.context.user);
        return (
            <Navbar bg="dark" variant="dark">
                <Link to={""} className="navbar-brand">
                </Link>
                <Nav className="mr-auto">
                <Link to={""} className="nav-link">Welcome </Link>
                {/* {document.cookie ?
                (
                
                    <Link to={"login"} className="nav-link">Login</Link>

                ) : (
                    <Link to={"logout"} className="nav-link">Logout</Link>,
                    <Link to={"register"} className="nav-link">Register</Link>
                )
                } */}
                {/* <Link to={"login"} className="nav-link">Login</Link>
                <Link to={"logout"} className="nav-link">Logout</Link>,
                <Link to={"register"} className="nav-link">Register</Link> */}
                <Link to={"addApartment"} className="nav-link">Add Apartment </Link>
                <Link to={"Apartments"} className="nav-link">Apartments List </Link>
                <Link to={"addBuilding"} className="nav-link">Add Building </Link>
                <Link to={"Buildings"} className="nav-link">Buildings List </Link>


                {/* <Link to={"addElement"} className="nav-link">Add Element </Link> */}
                {/* <Link to={"Elements"} className="nav-link">Element List </Link> */}

                </Nav>
            </Navbar>
        );
    }
}