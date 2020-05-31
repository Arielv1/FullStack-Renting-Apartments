import React, {userContext} from "react";
import { UserContext } from "./UserContext";

export const logout = () => {

    const { user, setUser } = useContext(UserContext);
    setUser(null);

}