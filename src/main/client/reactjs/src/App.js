import React, { useState, useMemo } from "react";
import './App.css';

import {Container , Row , Col} from 'react-bootstrap';
import {BrowserRouter as Router, Switch , Route} from 'react-router-dom';
import NavigatorBar from './components/NavigationBar';
// import Element from './components/Element';
// import ElementList from './components/ElementList';
import Apartment from './components/Apartment';
import ApartmentsList from './components/ApartmentsList';
import Building from './components/Building';
import BuildingsList from './components/BuildingsList';

import Welcome from './components/Welcome';
// import Login from './components/Login';
// import Register from './components/Register';
// import {LoginN} from './components/loginN';
// import LoginN from './components/loginN';

// import Profile from './components/Profile';
import { UserContext } from './components/UserContext';
// import { loginsetUser } from './components/loginsetUser';







function AppRouter() {

  const [user, setUser] = useState(null);

  const value = useMemo(() => ({ user, setUser }), [user, setUser]);
  return (
    <UserContext.Provider value={value}>
    <Router>
      <NavigatorBar/>
      <Container>
        <Row>
          <Col lg={12} className={"margin-top"}>
            <Switch>
              <Route path="/" exact component={Welcome}/>
              {/* {loginsetUser} */}
              {/* <Route path="/profile" exact component={LoginN}/> */}
              {/* <Route path="/profile" exact component={loginsetUser} /> */}
              {/* <Route path="/login" exact component={Login}/>
              <Route path="/register" exact component={Register}/>
              <Route path="/profile/:email" exact component={Profile}/> */}
              <Route path="/addApartment" exact component={Apartment}/>
              <Route path="/Apartments" exact component={ApartmentsList}/>
              <Route path="/addBuilding" exact component={Building}/>
              <Route path="/Buildings" exact component={BuildingsList}/>
              {/* <Route path="/addElement" exact component={Element}/>
              <Route path="/Elements" exact component={ElementList}/> */}
              {/* <Route path="/edit/:elementId" exact component={Element}/> */}
              <Route path="/edit/Apartment/:elementIdDomain/:elementIdId" exact component={Apartment}/>
              <Route path="/edit/Building/:elementIdDomain/:elementIdId" exact component={Building}/>

            </Switch>
          </Col>
        </Row>
      </Container>
    </Router>
    </UserContext.Provider>

  );
}

export default AppRouter;
