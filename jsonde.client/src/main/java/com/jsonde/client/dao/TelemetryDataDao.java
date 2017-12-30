package com.jsonde.client.dao;

import com.jsonde.client.domain.TelemetryData;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class TelemetryDataDao extends AbstractEntityDao<TelemetryData> {

    public TelemetryDataDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

}
