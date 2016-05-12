package com.yonyou.proxy.jws.interceptors;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.yonyou.proxy.jws.util.C;

public class GatewayInInterceptor extends AbstractPhaseInterceptor<Message> {

	public GatewayInInterceptor() {
		super(Phase.PRE_STREAM);
		addBefore(SoapPreProtocolOutInterceptor.class.getName());
	}

	public void handleMessage(Message message) throws Fault {
		if (isRequestor(message)) {
			if (isGET(message)) {
				String param = (String) message.get(Message.QUERY_STRING);
				message.remove(Message.QUERY_STRING);
				param = dueParam(param);
				message.put(Message.QUERY_STRING, param);
			} else {
				try {
					InputStream is = message.getContent(InputStream.class);
					String param = is.toString();
					String nParam = dueParam(param);
					message.removeContent(InputStream.class);
					is = IOUtils.toInputStream(nParam, "UTF-8");
					message.setContent(InputStream.class, is);
					IOUtils.closeQuietly(is);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String dueParam(String param) {
		String nParam = "";
		if (param.indexOf("&lt;") != -1) {
			nParam = param;
		} else if (param.indexOf("<![CDATA") == -1) {
			nParam = param.replaceAll(String.format("<%s>", C.REQUEST_ROOT_NODE), String.format("<![CDATA[<%s>", C.REQUEST_ROOT_NODE));
			nParam = nParam.replaceAll(String.format("</%s>", C.REQUEST_ROOT_NODE), String.format("</%s>]]>", C.REQUEST_ROOT_NODE));
		} else {
			nParam = param;
		}
		return nParam;
	}
}
