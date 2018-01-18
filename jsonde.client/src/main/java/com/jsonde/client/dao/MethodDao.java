package com.jsonde.client.dao;

import com.jsonde.client.domain.Method;

import java.sql.SQLException;

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
    /**
     * createTable
     */
    public void createTable() throws DaoException {
     
        try {   super.createTable();
			execute("CREATE INDEX METHOD_CLASSID_IDX ON METHOD (CLASSID);");
		} catch (SQLException e) {
		}
    }
}