package com.yonyou.proxy.jws.virtualimpl;

import com.ufida.g3.jmjkfw.JmDjService;

public class JmDjServiceVMImpl extends ServiceVMFactory implements JmDjService{

	private String url;
	private JmDjService jmdj = null;
	
	private JmDjService getJmdj(){	
		if (jmdj == null){
			jmdj = createProxyBean(JmDjService.class,  url);
		}
		return jmdj;
	}
	
	public JmDjServiceVMImpl(String url){
		//TODO:正则，是否是有效的网址
		this.url = url;
	}
	
	
	public int getLsh(String sqbm) {		
		return getJmdj().getLsh( sqbm);
	}

	public String sayHello() {
		return getJmdj().sayHello();
	}

	public String sayHello2(String name, String bm) {
		return getJmdj().sayHello2(name, bm);
	}

	public String sayHello3(String name, String bm, int id) {
		return getJmdj().sayHello3(name, bm, id);
	}

}
