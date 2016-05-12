package com.yonyou.proxy.jws.interceptors;

import java.sql.SQLException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yonyou.proxy.jws.util.Md5Utils;


public class HeaderIntercepter extends AbstractSoapInterceptor {
 
	public HeaderIntercepter(){
		super(Phase.WRITE);
	}

	public void handleMessage(SoapMessage arg0) throws Fault {
		
		QName qname=new QName("soap:Header");   
        Document doc=DOMUtils.createDocument();   
        //自定义节点
        Element myheader = doc.createElementNS("","MySoapHeader");   
        myheader.setAttribute("xmlns","http://tempuri.org/");   
        //自定义节点        
        Element spPass=doc.createElement("Password");          
		//+DateUtils.currentDateString(DateUtils.YEAR_MONTH_DAY_PATTERN)
		String md5 = Md5Utils.MD5("lhyy");        
		spPass.setTextContent(md5);
        myheader.appendChild( spPass); 
         
        /*Element root=doc.createElementNS("", "");   
        root.appendChild( myheader);*/
           
        SoapHeader head=new SoapHeader(qname, myheader);   
        List<Header> headers= arg0.getHeaders();   
        headers.add(head);   
		
	}

	

	
}
