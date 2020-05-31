import React, {useContext} from "react";
import { UserContext } from "./UserContext";

// export function loginsetUser(theUser) {
export function loginsetUser() {

    // const { user, setUser } = useContext(UserContext);
        const { user, setUser } = null

  return (
    <div>
        <h2>Home</h2>
            {user ? (
                <button
                onClick={() => {
                    // call logout
                    setUser(null);
                }}
                >
                logout
                </button>
            ) : (
                <button
                onClick={async () => {
                    // const user = await login();
                    const user = null
                    setUser(user);
                }}
                >
                login
                </button>
            )}
    </div>
  );
}

        {/* <h2>Login To </h2>
           { setUser(user)} */}
      {/* <h2>Login To {theUser}</h2>
           { setUser(theUser)} */}