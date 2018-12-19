package fun.nya.backend.dao.model;

import java.io.Serializable;

/**
 * User info Model
 */
public class UserInfoModel implements Serializable {
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
