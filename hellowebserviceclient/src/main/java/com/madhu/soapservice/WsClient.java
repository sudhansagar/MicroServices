package com.madhu.soapservice;

import java.net.MalformedURLException;
import java.net.URL;

import com.madhu.ws.soap.Hellows;
import com.madhu.ws.soap.HellowsService;

public class WsClient {

	public static void main(String[] args) throws MalformedURLException {
		
		HellowsService ser = new HellowsService(new URL("http://localhost:8080/hellows/hello?wsdl"));
		Hellows hellowsPort = ser.getHellowsPort();
		String hello = hellowsPort.hello();
		System.out.println("Response from Hello SOAP Service : " + hello); 
	}

}
