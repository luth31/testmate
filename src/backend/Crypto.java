package backend;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Crypto {
    public Crypto() {
        this(1, 65536, 1);
    }
    public Crypto(int Iterations, int Memory, int Threads) {
        _Iterations = Iterations;
        _Memory = Memory;
        _Threads = Threads;
        _Engine = Argon2Factory.create();
        System.out.println("Crypto engine initialized!");
    }

    public boolean Verify(String Password, String Hash) {
        return _Engine.verify(Hash, Password.toCharArray());
    }

    public String Hash(String Str) {
        return _Engine.hash(_Iterations, _Memory, _Threads, Str.toCharArray());
    }

    public String Hash(char[] Str) {
        return _Engine.hash(_Iterations, _Memory, _Threads, Str);
    }

    int _Iterations;
    int _Memory;
    int _Threads;
    Argon2 _Engine;
}
