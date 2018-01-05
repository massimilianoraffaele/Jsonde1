package com.jsonde.client.dao;

import com.jsonde.client.domain.TelemetryData;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class TelemetryDataDao extends AbstractEntityDao<TelemetryData> {

	/**
	 * 
	 * @param dataSource
	 * @throws DaoException
	 */
    public TelemetryDataDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

}
