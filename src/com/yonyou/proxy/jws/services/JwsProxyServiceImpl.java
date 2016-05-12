package com.yonyou.proxy.jws.services;

import javax.jws.WebService;

import com.yonyou.proxy.jws.factory.JwsProxyFactory;

@WebService(endpointInterface = "com.yonyou.proxy.jws.services.JwsProxyService")
public class JwsProxyServiceImpl implements JwsProxyService {

	private JwsProxyFactory proxyFactory;
	
	
	public JwsProxyFactory getProxyFactory() {
		return proxyFactory;
	}

   
	public void setProxyFactory(JwsProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}

	public String proxyMethod(String proxyId, String methodName, String param) {
		return proxyFactory.proxyMethod(proxyId, methodName, param);
	}

}
