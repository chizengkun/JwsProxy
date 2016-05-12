package com.yonyou.proxy.jws.virtualimpl;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;


public abstract class ServiceVMFactory {

	public static <T> T createProxyBean(Class<T> clazz, String url){
		 JaxWsProxyFactoryBean jwpf = new JaxWsProxyFactoryBean();
		 
		 jwpf.getInInterceptors().add(  new LoggingInInterceptor()); 
		 jwpf.getOutInterceptors().add( new LoggingOutInterceptor()); 
		 //�ڲ�������ʱ����Ҫ����֤ jwpf.getInInterceptors().add(  new ReadSoapHeader());
		 jwpf.setAddress(url);
	     jwpf.setServiceClass( clazz);
	  
	     return jwpf.create(clazz);
	}
}
