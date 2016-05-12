package com.yonyou.proxy.jws.virtualimpl;

import com.yonyou.proxy.jws.util.C;
import com.yonyou.proxy.jws.util.NoSettingException;

public class JaxWsProxyHelper {

		
	public static ServiceVMFactory getVmFactory(String proxyId, String url) throws NoSettingException{
		if (url == null || url.equals(""))
			throw new NoSettingException("未配置对应的url地址："+proxyId);
		if ( proxyId.equalsIgnoreCase( C.JmDjService)){
			return new JmDjServiceVMImpl( url);
		}
		
		return null;
	}
}
