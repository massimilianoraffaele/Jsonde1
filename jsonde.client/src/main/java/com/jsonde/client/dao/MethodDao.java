package com.jsonde.client.dao;

import com.jsonde.client.domain.Method;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class MethodDao extends AbstractEntityDao<Method> {

	/**
	 * 
	 * @param dataSource
	 * @throws DaoException
	 */
    public MethodDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

    @Override
    public void createTable() throws DaoException {
        super.createTable();
        execute("CREATE INDEX METHOD_CLASSID_IDX ON METHOD (CLASSID);");
    }
}