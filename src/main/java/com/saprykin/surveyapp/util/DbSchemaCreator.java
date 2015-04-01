package com.saprykin.surveyapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

/**
 * Created by Ivan on 3/12/2015.
 */
public class DbSchemaCreator {

    private static final Logger logger = LoggerFactory.getLogger(DbSchemaCreator.class);

    private static final String QUERY_CREATE_ROLE_TABLE = "CREATE TABLE IF NOT EXISTS roles (id SERIAL NOT NULL PRIMARY KEY, role VARCHAR(15))";
    private static final String QUERY_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users (id SERIAL NOT NULL PRIMARY KEY, email VARCHAR(100), emailConfirmation BOOLEAN, emailNotifications BOOLEAN, confirmationToken VARCHAR(100))";
    private static final String QUERY_CREATE_USER_TABLE_DATA = "INSERT INTO users (email, emailConfirmation, emailNotifications, confirmationToken) " +
            "VALUES ('foo@bar.com', false, false, '-'), ('bar@foo.com', false, false, '-'), ('foobar@bf.com', true, true, '324r3rqef32fddf5y6')";

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        logger.info("get connection...SUCCESS!");
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public void createDbIfNotExist(){

        Connection connection = null;
        try {
            logger.info("create tables if not exist...");
            connection = getConnection();


            Statement stmt = connection.createStatement();

            if(stmt != null) {
                stmt.executeUpdate(QUERY_CREATE_ROLE_TABLE);
                stmt.executeUpdate(QUERY_CREATE_USER_TABLE);
            }
            logger.info("create tables if not exist...SUCCESS!");
        } catch(URISyntaxException | SQLException e) {
            logger.error("couldn't get connection to database", e);
        } finally {
            try {
                connection.close();
            } catch(SQLException e) {
                logger.error("couldn't close connection", e);
            }
        }
    }

    public void createSomeDataInDb(){

        Connection connection = null;
        try {
            logger.info("write some data into users table...");
            connection = getConnection();

            Statement stmt = connection.createStatement();

            if(stmt != null) {
                stmt.executeUpdate(QUERY_CREATE_USER_TABLE_DATA);
            }
            logger.info("write some data into users table...SUCCESS!");
        } catch(URISyntaxException | SQLException e) {

            logger.error("couldn't get connection to database", e);
        } finally {
            try {
                connection.close();
            } catch(SQLException e) {
                logger.error("couldn't close connection", e);
            }
        }
    }

    public String readAllUsers(){
        Connection connection = null;
        StringBuilder result = new StringBuilder();
        try {
            logger.info("reading all users from DB...");
            connection = getConnection();


            Statement stmt = connection.createStatement();

            if(stmt != null) {
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM users");

                while(resultSet.next()) {
                    result.append("id: ");
                    result.append(resultSet.getInt("id"));
                    result.append(" e-mail: ");
                    result.append(resultSet.getString("email"));
                    result.append(" emailConfirmation: ");
                    result.append(resultSet.getBoolean("emailConfirmation"));
                    result.append(" emailNotifications: ");
                    result.append(resultSet.getBoolean("emailNotifications"));
                    result.append(" confirmationToken: ");
                    result.append(resultSet.getString("confirmationToken"));
                    result.append("<br>");
                }
            }
            logger.info("reading all users from DB...SUCCESS!");
        } catch(URISyntaxException | SQLException e) {
            logger.error("couldn't get read from database", e);
        } finally {
            try {
                connection.close();
            } catch(SQLException e) {
                logger.error("couldn't close connection", e);
            }
        }
        return result.toString();
    }

}
