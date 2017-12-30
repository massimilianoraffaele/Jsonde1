package com.jsonde.client.dao;

import com.jsonde.client.domain.CodeSource;

import javax.sql.DataSource;
/**
 * 
 * @author admin
 *
 */
public class CodeSourceDao extends AbstractEntityDao<CodeSource> {

    public CodeSourceDao(DataSource dataSource) throws DaoException {
        super(dataSource);
    }

}