package car.serwis.database.util;

/**
 * Klasa przechowująca dane uwierzytelniające do bazy danych
 * @hidden
 */
public class DataBaseCredentials {
    private String host = "car-serwis.cpoce5owftvv.eu-central-1.rds.amazonaws.com";
    private String database = "serwis";
    private String user = "serwis";
    private String password = "serwis123";


    public DataBaseCredentials() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
