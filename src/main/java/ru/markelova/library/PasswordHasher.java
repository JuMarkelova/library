package ru.markelova.library;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Генерируем хеш для пароля "userpassword"
        String userPasswordHash = encoder.encode("userpassword");
        System.out.println("Хеш для 'userpassword': " + userPasswordHash);

        // Генерируем хеш для пароля "adminpassword"
        String adminPasswordHash = encoder.encode("adminpassword");
        System.out.println("Хеш для 'adminpassword': " + adminPasswordHash);
    }
}