package com.votes.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@CommonsLog
public abstract class BaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void clearAllTables() {
        try (Connection connection = dataSource.getConnection()) {
            clear(fetchTableNames(connection), connection);
        } catch (SQLException e) {
            log.error("Error clearing tables", e);
            throw new RuntimeException(e);
        }
    }

    private List<String> fetchTableNames(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();
        try (ResultSet resultSet = connection.getMetaData().getTables(
                connection.getCatalog(), null, null, new String[]{"TABLE"})) {
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            log.error("Cannot fetch all tables", e);
            throw e;
        }
        return tableNames;
    }

    private void clear(List<String> tableNames, Connection connection) throws SQLException {
        try (Statement statement = buildSqlStatement(tableNames, connection)) {
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("Error during clearing execution", e);
            throw e;
        }
    }

    private Statement buildSqlStatement(List<String> tableNames, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS = 0");
        addDeleteStatements(tableNames, statement);
        statement.addBatch("SET FOREIGN_KEY_CHECKS = 1");
        return statement;
    }

    private void addDeleteStatements(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                statement.addBatch("DELETE FROM " + tableName);
            } catch (SQLException e) {
                log.error("Error adding delete statement for table " + tableName, e);
                throw new RuntimeException(e);
            }
        });
    }

}
