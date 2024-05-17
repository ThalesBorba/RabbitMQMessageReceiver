package com.infotech.messagereceiver.environment;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.TimeZone;

@UtilityClass
public class DotenvClass {

    static Dotenv dotenv = Dotenv.load();

    static final String DB_HOST = dotenv.get("DB_HOST");
    static final String DB_PORT = dotenv.get("DB_PORT");
    static final String DB_NAME = dotenv.get("DB_NAME");
    static final String DB_USER = dotenv.get("DB_USER");
    static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    public static void loadDotenv() {
        System.setProperty("spring.datasource.url", "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME);
        System.setProperty("spring.datasource.username", Objects.requireNonNull(DB_USER));
        System.setProperty("spring.datasource.password", Objects.requireNonNull(DB_PASSWORD));
        System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
