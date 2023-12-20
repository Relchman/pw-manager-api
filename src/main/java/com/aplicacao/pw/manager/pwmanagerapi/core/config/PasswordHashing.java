package com.aplicacao.pw.manager.pwmanagerapi.core.config;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHashing {

    public static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.hash(10, 65536, 1, password);
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(hashedPassword, password);
    }

}

