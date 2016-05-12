package com.yonyou.proxy.jws.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://service.jwsproxy.yonyou.com")
public interface JwsProxyService {

	//mid 对应的方法号， data对应的数据内容， xml或json格式
	@WebMethod 
	public String proxyMethod(@WebParam(name="proxyId") String proxyId,@WebParam(name="methodName") String methodName, @WebParam(name="data") String param);
}
