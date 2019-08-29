package org.donkeycode.codegen.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.ColumnField;

/**
 * Author GreedyStar Date 2018/4/19
 */
public class JDBCConnectionFactory {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * 初始化数据库连接
     *
     * @return
     */
    public boolean initConnection(ContextConfiguration.Datasources datasources) {

        try {
            Class.forName(DatabaseDialects.getDriver(datasources.getUrl()));
            String url = datasources.getUrl();
            String username = datasources.getUsername();
            String password = datasources.getPassword();
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取表结构数据
     *
     * @param tableName 表名
     * @return 包含表结构数据的列表
     */
    public List<ColumnField> getMetaData(String tableName) throws SQLException {
        ResultSet tempResultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
        String primaryKey = null;
        if (tempResultSet.next()) {
            primaryKey = tempResultSet.getObject(4).toString();
        }
        List<ColumnField> columnFields = new ArrayList<>();
        statement = connection.createStatement();
        String sql = "SELECT * FROM " + tableName + " WHERE 1 != 1";
        resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            ColumnField info;
            if (metaData.getColumnName(i).equals(primaryKey)) {
                info = new ColumnField(metaData.getColumnName(i), metaData.getColumnType(i), true);
            } else {
                info = new ColumnField(metaData.getColumnName(i), metaData.getColumnType(i), false);
            }
            columnFields.add(info);
        }
        statement.close();
        resultSet.close();
        return columnFields;
    }

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

}
