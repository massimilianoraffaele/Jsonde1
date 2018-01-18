package com.jsonde.client.dao;

import com.jsonde.util.db.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author admin
 *
 */
public class ReportDao extends AbstractDao {

	/**
	 * 
	 * @param dataSource
	 */
    public ReportDao(DataSource dataSource) {
        super(dataSource);
    }

    public Map<Long, Set<Long>> getDependencies() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Map<Long, Set<Long>> dependencies = new HashMap<Long, Set<Long>>();

        try {
            connection = connection();

            statement = connection.prepareStatement(
                    "select distinct callerCodeSource.id, calleeCodeSource.id" +
                            "from methodcallsummary caller" +
                            "inner join method callerMethod on callerMethod.id = caller.methodid" +
                            "inner join clazz callerClass on callerClass.id = callerMethod.classId" +
                            "inner join codesource callerCodeSource on callerCodeSource.id = callerClass.codeSourceId" +
                            "" +
                            "inner join methodcallsummary callee on callee.callerid = caller.id" +
                            "" +
                            "inner join method calleeMethod on calleeMethod.id = callee.methodid" +
                            "inner join clazz calleeClass on calleeClass.id = calleeMethod.classId" +
                            "inner join codesource calleeCodeSource on calleeCodeSource.id = calleeClass.codeSourceId");

            statement.execute();

            resultSet = statement.getResultSet();

            while (resultSet.next()) {

                long callerCodeSourceId = resultSet.getLong(1);
                long calleeCodeSourceId = resultSet.getLong(2);

                if (!dependencies.containsKey(callerCodeSourceId)) {
                    dependencies.put(callerCodeSourceId, new LinkedHashSet<Long>());
                }

                if (callerCodeSourceId != calleeCodeSourceId)
                    dependencies.get(callerCodeSourceId).add(calleeCodeSourceId);

            }

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(resultSet);
            DbUtils.close(statement);
            DbUtils.close(connection);
        }

        return dependencies;

    }

    public Map<String, Long> getTopCodeSourcesByExecutionTime() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Map<String, Long> topCodeSourcesByExecutionTime = new HashMap<String, Long>();

        try {
            connection = connection();

            statement = connection.prepareStatement(
                    "select cs.source, sum(mc.executiontime) as executionTime" +
                            "from methodcallsummary mc" +
                            "inner join method m on mc.methodid = m.id" +
                            "inner join clazz c on m.classid = c.id" +
                            "inner join codesource cs on cs.id = c.codesourceid" +
                            "group by c.codesourceid" +
                            "order by executionTime desc");

            statement.execute();

            resultSet = statement.getResultSet();

            while (resultSet.next()) {

                String codeSource = resultSet.getString(1);
                long totalExecutionTime = resultSet.getLong(2);

                topCodeSourcesByExecutionTime.put(codeSource, totalExecutionTime);

            }

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(resultSet);
            DbUtils.close(statement);
            DbUtils.close(connection);
        }

        return topCodeSourcesByExecutionTime;

    }

    public Map<String, Long> getTopMethodsThrowingExcetion() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Map<String, Long> topCodeSourcesByExecutionTime = new HashMap<String, Long>();

        try {
            connection = connection();

            statement = connection.prepareStatement(
                    "select c.name, m.name, mc.throwExceptionCounter" +
                            "from methodcallsummary mc" +
                            "inner join method m on mc.methodid = m.id" +
                            "inner join clazz c on m.classid = c.id" +
                            "where mc.throwExceptionCounter > 0" +
                            "group by c.name, m.name" +
                            "limit 20");

            statement.execute();

            resultSet = statement.getResultSet();

            while (resultSet.next()) {

                String className = resultSet.getString(1);
                String methodName = resultSet.getString(2);
                long exceptionCounter = resultSet.getLong(3);

                String name = className + "." + methodName;

                topCodeSourcesByExecutionTime.put(name, exceptionCounter);

            }

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(resultSet);
            DbUtils.close(statement);
            DbUtils.close(connection);
        }

        return topCodeSourcesByExecutionTime;

    }

}
