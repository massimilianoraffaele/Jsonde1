package com.jsonde.client.dao;

import com.jsonde.client.domain.TopMethodCall;

import java.sql.SQLException;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class TopMethodCallDao extends AbstractEntityDao<TopMethodCall> {

	/**
	 * 
	 * @param dataSource
	 * @throws DaoException
	 */
    public TopMethodCallDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

    @Override
    /**
     * createTable
     */
    public void createTable() throws DaoException {
 
        try {       super.createTable();
			execute("CREATE INDEX TOPMETHODCALL_HASHCODE_IDX ON TOPMETHODCALL (HASHCODE);");
		} catch (SQLException e) {
		}
        try {
			execute("CREATE INDEX TOPMETHODCALL_COUNT_IDX ON TOPMETHODCALL (COUNT);");
		} catch (SQLException e) {
		}
    }


}