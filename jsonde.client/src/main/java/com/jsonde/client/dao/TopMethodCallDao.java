package com.jsonde.client.dao;

import com.jsonde.client.domain.TopMethodCall;

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
    public void createTable() throws DaoException {
        super.createTable();
        execute("CREATE INDEX TOPMETHODCALL_HASHCODE_IDX ON TOPMETHODCALL (HASHCODE);");
        execute("CREATE INDEX TOPMETHODCALL_COUNT_IDX ON TOPMETHODCALL (COUNT);");
    }


}