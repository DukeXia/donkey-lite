package io.donkeycode.mybatis.internal;

import static io.donkeycode.mybatis.internal.util.StringUtility.stringHasValue;
import static io.donkeycode.mybatis.internal.util.messages.Messages.getString;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import io.donkeycode.mybatis.api.ConnectionFactory;
import io.donkeycode.mybatis.config.JDBCConnectionConfiguration;

/**
 * This class assumes that classes are cached elsewhere for performance reasons,
 * but also to make sure that any native libraries are only loaded one time
 * (avoids the dreaded UnsatisfiedLinkError library loaded in another
 * classloader)
 *
 * @author Jeff Butler
 */
public class JDBCConnectionFactory implements ConnectionFactory {

    private String userId;
    private String password;
    private String connectionURL;
    private String driverClass;
    private Properties otherProperties;

    /**
     * This constructor is called when there is a JDBCConnectionConfiguration
     * specified in the configuration.
     *
     * @param config the configuration
     */
    public JDBCConnectionFactory(JDBCConnectionConfiguration config) {
        super();
        userId = config.getUserId();
        password = config.getPassword();
        connectionURL = config.getConnectionURL();
        driverClass = config.getDriverClass();
        otherProperties = config.getProperties();
    }

    /**
     * This constructor is called when this connection factory is specified 
     * as the type in a ConnectionFactory configuration element. 
     */
    public JDBCConnectionFactory() {
        super();
    }

    @Override
    public Connection getConnection() throws SQLException {

        Properties props = new Properties();

        if (stringHasValue(userId)) {
            props.setProperty("user", userId);
        }

        if (stringHasValue(password)) {
            props.setProperty("password", password);
        }

        props.putAll(otherProperties);

        Driver driver = getDriver();
        Connection conn = driver.connect(connectionURL, props);

        if (conn == null) {
            throw new SQLException(getString("RuntimeError.7"));
        }

        return conn;
    }

    private Driver getDriver() {
        Driver driver;
        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.8"), e);
        }

        return driver;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // this should only be called when this connection factory is
        // specified in a ConnectionFactory configuration
        userId = properties.getProperty("userId");
        password = properties.getProperty("password");
        connectionURL = properties.getProperty("connectionURL");
        driverClass = properties.getProperty("driverClass");

        otherProperties = new Properties();
        otherProperties.putAll(properties);

        // remove all the properties that we have specific attributes for
        otherProperties.remove("userId");
        otherProperties.remove("password");
        otherProperties.remove("connectionURL");
        otherProperties.remove("driverClass");
    }
}
