package com.yonyou.proxy.jws.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://service.jwsproxy.yonyou.com")
public interface JwsProxyService {

	//mid ��Ӧ�ķ����ţ� data��Ӧ���������ݣ� xml��json��ʽ
	@WebMethod 
	public String proxyMethod(@WebParam(name="proxyId") String proxyId,@WebParam(name="methodName") String methodName, @WebParam(name="data") String param);
}
