package com.jsonde.client.dao;

import com.jsonde.client.domain.ClazzLoader;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class ClazzLoaderDao extends AbstractEntityDao<ClazzLoader> {

	/**
	 * 
	 * @param dataSource
	 * @throws DaoException
	 */
    public ClazzLoaderDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

}