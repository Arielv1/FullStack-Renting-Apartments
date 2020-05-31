import axios from 'axios';
// import React, {useContext} from "react";
// import { UserContext } from "./UserContext";

export const loginUser = async (email,domain) => 
{
    console.log(email);
    console.log(domain);

    var mydata = ''
    console.log("calling log in");
    await axios.get("/acs/users/login/2020b.ofir.cohen/m@gmail.com")
        .then(response => response.data)
        .then((data) => {
            console.log(data);
            mydata = data;
        });
    console.log(" in log in , but afte axios");
    return {
        user: mydata.userId,
      };
    // console.log("we called login user");
    // return {
                /// try 2
    //   await axios.get("/acs/users/login/2020b.ofir.cohen/m@gmail.com")
    //     .then(response => response.data)
    //     .then((data) => {
    //         console.log(data);
    //         console.log(data.userId.email);
    //         if (data.userId.email)
    //         {

    //             console.log(data.userId);
    //             console.log("user?");
    //             return(data.userId.email);
    //         }
    //         else
    //         {
    //             return("failed");
    //         }
    //         });
        // };
            
};