package backend;

import db.*;

public class Auth {
    public Auth(DBManager Manager) {
        _DB = Manager;

    }

    public boolean Attempt(String Username, String Password) {
        return false;
    }

    DBManager _DB;
}
