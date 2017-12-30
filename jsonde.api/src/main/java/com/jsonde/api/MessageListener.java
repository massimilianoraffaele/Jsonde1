package com.jsonde.api;

import java.util.EventListener;
/**
 * 
 * @author admin
 *
 */
public interface MessageListener extends EventListener {

    void onMessage(Message message);

}
