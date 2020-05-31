import React, {Component} from 'react';

export default class Welcome extends Component {

    render(){
        let user = localStorage.getItem('user');
        let username = localStorage.getItem('username');

        return (
            <div>
                {
                    user === null ?
                    <h2> Welcome Guys</h2>
                    :
                    <h2> Welcome To {username}</h2>

                }
             </div>

        );
    }
}