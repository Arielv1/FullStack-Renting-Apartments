import React, {useContext} from "react";
import { UserContext } from "./UserContext";
import {loginUser} from "./loginUser";

export function LoginN(theUser) {

    const { user, setUser } = useContext(UserContext);
    var email;
    var domain;

    var state = {
        email:'asdas',
        domain:'asdas'
   }
    // const value = loginUser();
    return(
        <div>
            <h2> Hello </h2>
            {setUser("hamami")}
        </div>
    );

}



        //     {/* <form async onSubmit={setUser(loginUser())}> */}
        //     <input
        //     type="email"
        //     name="email"
        //     placeholder="email"
        //     value={email}
        //     onChange={state.email}
        //     required
        //   />

        //   <input
        //     type="text"
        //     name="domain"
        //     placeholder="domain"
        //     value={domain}
        //     required
        //   />
        //     {/* </form> */}
        // {user ? (
            
        //         <button
        //         onClick={() => {
        //             // call logout
        //             setUser(null);
        //         }}
        //         >
        //         logout
        //         </button>
        //     ) : (
        //         <button
        //         onClick={async () => {
        //             const user = await loginUser(state.email,domain);
        //             console.log("after login");
        //             console.log(user);
        //             setUser(user);
        //         }}
        //         >
        //         login
        //         </button>
        //     )}
        // {/* <button onClick={async () => {
        //     const user = await loginUser();
        //     setUser(user);
        // }}>
        //     login
        // </button> */}