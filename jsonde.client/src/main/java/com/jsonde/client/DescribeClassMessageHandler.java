package com.jsonde.client;

import java.sql.SQLException;
import java.util.List;

import com.jsonde.api.methodCall.DescribeClassMessage;
import com.jsonde.client.dao.ClazzDao;
import com.jsonde.client.dao.ClazzLoaderDao;
import com.jsonde.client.dao.CodeSourceDao;
import com.jsonde.client.dao.DaoException;
import com.jsonde.client.dao.DaoFactory;
import com.jsonde.client.domain.Clazz;
import com.jsonde.client.domain.ClazzLoader;
import com.jsonde.client.domain.CodeSource;
import com.jsonde.client.domain.Method;

/**
 * 
 * @author admin
 *
 */
public class DescribeClassMessageHandler implements MessageHandler<DescribeClassMessage>{

	/**
	 * 
	 * @param message
	 * @throws SQLException 
	 */
	public void onMessage(DescribeClassMessage message) throws SQLException{
		DescribeClassMessage describeClassMessage =
                (DescribeClassMessage) message;

        try {

            long classId;

            if (describeClassMessage.isClassRedefined()) {

                classId = describeClassMessage.getClassId();

            } else {

                long staticConstructorMethodId = describeClassMessage.getMethodId();
                Method method = DaoFactory.getMethodDao().get(staticConstructorMethodId);
                classId = method.getClassId();

            }

            ClazzDao clazzDao = DaoFactory.getClazzDao();
            Clazz clazz = clazzDao.get(classId);

            {
                ClazzLoaderDao clazzLoaderDao = DaoFactory.getClazzLoaderDao();

                ClazzLoader clazzLoader = clazzLoaderDao.get(describeClassMessage.getClassLoaderId());

                if (null == clazzLoader) {
                    clazzLoader = new ClazzLoader();
                    clazzLoader.setId(describeClassMessage.getClassLoaderId());
                    clazzLoaderDao.insert(clazzLoader);
                }

                clazz.setClassLoaderId(clazzLoader.getId());

            }

            {
                CodeSourceDao codeSourceDao =
                        DaoFactory.getCodeSourceDao();

                CodeSource codeSource;

                List<CodeSource> codeSources =
                        null == describeClassMessage.getCodeLocation() ?
                                codeSourceDao.getByCondition("source is null") :
                                codeSourceDao.getByCondition("source = ?", describeClassMessage.getCodeLocation());

                if (codeSources.isEmpty()) {

                    codeSource = new CodeSource();
                    codeSource.setId(Client.codeSourceIdGenerator.getAndIncrement());
                    codeSource.setSource(describeClassMessage.getCodeLocation());

                    codeSourceDao.insert(codeSource);

                } else {
                    codeSource = codeSources.get(0);
                }

                clazz.setCodeSourceId(codeSource.getId());

            }

            clazzDao.update(clazz);

        } catch (DaoException e) {
        	System.out.println("Something was wrong");
        }
		
	}
}
