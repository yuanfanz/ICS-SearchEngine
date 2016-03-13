package com.tristan.web;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.render.ViewType;
import com.tristan.web.action.IndexAction;
import com.tristan.web.mongodb.MongoPlugin;

public class WebConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
		me.setEncoding("utf-8");
		loadPropertyFile("config.properties");
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("index", IndexAction.class, "/page");
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		String host = getProperty("mongodb.host","127.0.1");
		int port = getPropertyToInt("mongodb.port", 27017);
		String db = getProperty("mongodb.db","test");
		String db2 = getProperty("mongodb.db2","test");
		MongoPlugin mongo = new MongoPlugin(host,port,db);
		me.add(mongo);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}

}
