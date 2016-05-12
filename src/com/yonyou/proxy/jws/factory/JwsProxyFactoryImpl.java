package com.yonyou.proxy.jws.factory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.yonyou.proxy.jws.util.C;
import com.yonyou.proxy.jws.util.NoSettingException;
import com.yonyou.proxy.jws.util.XmlProxyElement;
import com.yonyou.proxy.jws.virtualimpl.JaxWsProxyHelper;
import com.yonyou.proxy.jws.virtualimpl.ServiceVMFactory;

public class JwsProxyFactoryImpl implements JwsProxyFactory {
	private Properties props = new Properties();

	public JwsProxyFactoryImpl() {
		try {
			Resource resource = new ClassPathResource("jwsproxy.properties");
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getVal(String key) {
		Object obj = props.get(key);
		if (obj != null)
			return obj.toString();
		return null;
	}

	private Class<?> getTypeByProp(String methodnam){
		String t = getVal(methodnam);
		if (t != null){
			return getTypeByString( t);
		}
		return String.class;
	}

	private Class<?> getTypeByString(String name) {
		if (name.equalsIgnoreCase(String.class.getSimpleName())) {
			return String.class;
		} else if (name.equalsIgnoreCase(Integer.class.getSimpleName())) {
			return Integer.class;
		} else if (name.equalsIgnoreCase(int.class.getSimpleName())) {
			return int.class;
		}
		return null;
	}
	
	private Object getParamValue(Class<?> clazz, String val){
		if (clazz.getSimpleName().equalsIgnoreCase( String.class.getSimpleName())){
			return val;
		}else if (clazz.getSimpleName().equalsIgnoreCase( Integer.class.getSimpleName())){
			if (val == null || val.equals("")) return 0;
			return Integer.parseInt( val);
		}else if (clazz.getSimpleName().equalsIgnoreCase(int.class.getSimpleName())){
			if (val == null || val.equals("")) return 0;
			return Integer.parseInt( val);
		}
		return null;
	}

	private void parseXMLText(Map<String, XmlProxyElement> xps, String param) throws DocumentException {
		Document doc = DocumentHelper.parseText(param);
		Element element = (Element) doc.selectSingleNode(C.XML_PARAM_XPATH);
		Iterator<Element> itr = element.elementIterator();
		while (itr.hasNext()) {
			Element m = itr.next();
			XmlProxyElement xpe = new XmlProxyElement();
			xpe.setName( m.getName());
			xpe.setType( getTypeByString( m.attributeValue(C.XML_ATTR_TYPE)));
			xpe.setValue( getParamValue(xpe.getType(), m.getText()));
			
			xps.put( m.getName(), xpe);
		}

	}
	
	private void parseXMLXML(Map<String, XmlProxyElement> xps, String param) throws DocumentException {
		Document doc = DocumentHelper.parseText(param);
		List<Element> elements = doc.selectNodes(C.XML_PARAM_XPATH);
		
		for (Element ment :elements) {
			XmlProxyElement xpe = new XmlProxyElement();
			String n = ment.attributeValue(C.XML_ATTR_PARAM_RNAME);
			xpe.setName( n);
			xpe.setType( String.class);
			Element e= (Element) ment.selectSingleNode(C.XML_ATTR_PARAM_RNAME);
			xpe.setValue( e.asXML());
			
			xps.put( n, xpe);
		}

	}

	private void buildParams(Map<String, XmlProxyElement> xps, Class<?>[] paraT, Object[] paraO) {
		int xh = 0;
		for (Entry<String, XmlProxyElement> entry : xps.entrySet()) {
			paraO[xh] = entry.getValue().getValue();
			paraT[xh] = entry.getValue().getType();

			xh++;
		}
	}

	private String execMethod(String proxyId, String methodName, String param) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSettingException, DocumentException {
		ServiceVMFactory svm = JaxWsProxyHelper.getVmFactory(proxyId, getVal(proxyId));
		// 取对应的参数处理
		String paramType = getVal( methodName);
		Method method;
		if (paramType == null || paramType.equals("null")) {
			method = svm.getClass().getDeclaredMethod(methodName);
			Object obj = (Object) method.invoke(svm, null);
			return obj.toString();
		} else {

			Map<String, XmlProxyElement> xps = new LinkedHashMap<String, XmlProxyElement>();
			// 处理多个参数 -->只允许基本类型，暂不支持复杂类型
			if (paramType.equalsIgnoreCase(C.XML_TYPE_TEXT)) {
				parseXMLText(xps, param);
			}else if (paramType.equalsIgnoreCase(C.XML_TYPE_XML)){
				parseXMLXML(xps, param);
			}else{
				XmlProxyElement xpe = new XmlProxyElement();
				xpe.setName( methodName);
				xpe.setType( getTypeByProp( methodName));
				xpe.setValue( param);
				xps.put( methodName, xpe);
			}

			Class<?>[] paraT = new Class[xps.size()];
			Object[] paraO   = new Object[xps.size()];
			buildParams(xps, paraT, paraO);

			method = svm.getClass().getDeclaredMethod(methodName, paraT);

			Object obj = (Object) method.invoke(svm, paraO);
			return obj.toString();
		}

	}

	public String proxyMethod(String proxyId, String methodName, String param) {

		try {
			return execMethod(proxyId, methodName, param);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSettingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {			
			e.printStackTrace();
		}

		return null;
	}

}
