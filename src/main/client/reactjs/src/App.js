import React from 'react';
import './App.css';

import {Container , Row , Col} from 'react-bootstrap';
import {BrowserRouter as Router, Switch , Route} from 'react-router-dom';
import NavigatorBar from './components/NavigationBar';
import Element from './components/Element';
import ElementList from './components/ElementList';
import Welcome from './components/Welcome';



function App() {
  return (
    <Router>
      <NavigatorBar/>
      <Container>
        <Row>
          <Col lg={12} className={"margin-top"}>
            <Switch>
              <Route path="/" exact component={Welcome}/>
              <Route path="/addElement" exact component={Element}/>
              <Route path="/Elements" exact component={ElementList}/>
              {/* <Route path="/edit/:elementId" exact component={Element}/> */}
              <Route path="/edit/:elementIdDomain/:elementIdId" exact component={Element}/>
            </Switch>
          </Col>
        </Row>
      </Container>
    </Router>
  );
}

export default App;
