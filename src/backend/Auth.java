package backend;

import db.*;

public class Auth {
    public Auth(DBManager Manager, Crypto Engine) {
        _DB = Manager;
        _Auth = _DB._AuthTable;
        _Crypto = Engine;
    }

    public boolean Attempt(String Username, String Password) {
        if (!_Auth.UsernameExists(Username))
            return false;
        String HashedPassword = _Auth.GetPassword(Username);
        if (HashedPassword == null)
            return false;
        return _Crypto.Verify(Password, HashedPassword);
    }

    public boolean CreateUser(String Username, String Password) {
        if (_Auth.UsernameExists(Username))
            return false;
        String HashedPassword = _Crypto.Hash(Password);
        _Auth.InsertUser(Username, HashedPassword);
        return true;
    }

    public void MakeAdmin(String Username) {
        _Auth.MakePrivileged(Username);
    }

    public boolean IsAdmin(String Username) {
        return _Auth.IsPrivileged(Username);
    }

    Crypto _Crypto;
    AuthTable _Auth;
    DBManager _DB;
}
