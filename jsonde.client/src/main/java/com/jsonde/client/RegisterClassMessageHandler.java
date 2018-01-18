package com.jsonde.client;

import java.sql.SQLException;

import com.jsonde.api.methodCall.RegisterClassMessage;
import com.jsonde.client.dao.DaoException;
import com.jsonde.client.dao.DaoFactory;
import com.jsonde.client.domain.Clazz;
import com.jsonde.client.Client;
/**
 * 
 * @author admin
 *
 */
public class RegisterClassMessageHandler implements MessageHandler<RegisterClassMessage> {

	/**
	 * 
	 * @param message
	 * @throws SQLException 
	 */
	public void onMessage(RegisterClassMessage message) throws SQLException{
		RegisterClassMessage registerClassMessage = (RegisterClassMessage) message;

        Clazz clazz = new Clazz();
        clazz.setId(registerClassMessage.getClassId());
        clazz.setName(registerClassMessage.getName());

        try {
            DaoFactory.getClazzDao().insert(clazz);
        } catch (DaoException e) {
        	System.out.println("Something was wrong");
        }

        Client.fireRegisterClassEvent(clazz);
	}

}
