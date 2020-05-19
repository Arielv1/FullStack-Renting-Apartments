import React, {Component} from 'react';
import {Navbar, Nav} from 'react-bootstrap';
import {Link} from 'react-router-dom';

export default class NavigatorBar extends Component {
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Link to={""} className="navbar-brand">
                </Link>
                <Nav className="mr-auto">
                <Link to={""} className="nav-link">Welcome </Link>
                <Link to={"addElement"} className="nav-link">Add Element </Link>
                <Link to={"Elements"} className="nav-link">Element List </Link>
                </Nav>
            </Navbar>
        );
    }
}