package com.ufida.g3.jmjkfw;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://jmjkfw.g3.ufida.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface JmDjService {
	@WebMethod(operationName = "getLsh")
	public @WebResult(name = "lsh") int getLsh(@WebParam(name = "sqbm") String sqbm);
	
	
	@WebMethod(operationName = "sayHello")
	public @WebResult(name = "sayHello") String sayHello();
	
	
	@WebMethod(operationName = "sayHello2")
	public @WebResult(name = "sayHello2") String sayHello2(@WebParam(name = "name") String name,@WebParam(name = "bm") String bm);
	
	@WebMethod(operationName = "sayHello3")
	public @WebResult(name = "sayHello3") String sayHello3(@WebParam(name = "name") String name,@WebParam(name = "bm") String bm, @WebParam(name="id") int id);
}
