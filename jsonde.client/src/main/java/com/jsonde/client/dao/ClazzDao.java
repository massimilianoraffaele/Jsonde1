package com.jsonde.client.dao;

import com.jsonde.client.domain.Clazz;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class ClazzDao extends AbstractEntityDao<Clazz> {

	/**
	 * 
	 * @param dataSource
	 * @throws DaoException
	 */
    public ClazzDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

    @Override
    public void createTable() throws DaoException {
        super.createTable();
        execute("CREATE INDEX CLAZZ_CLASSLOADERID_IDX ON CLAZZ (CLASSLOADERID);");
        execute("CREATE INDEX CLAZZ_CODESOURCEID_IDX ON CLAZZ (CODESOURCEID);");
    }

}
