package com.compassitesinc.chat.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ChatServerContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
//		InputStream keyStoreResource = new FileInputStream("/path/to/keystore.jks");
//
//		char[] keyStorePassphrase = "secret".toCharArray();
//
//		KeyStore ksKeys = KeyStore.getInstance("JKS");
//
//		ksKeys.load(keyStoreResource, keyStorePassphrase);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
