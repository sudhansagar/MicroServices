package com.madhu.ws.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;

@WebService
@Features(features="org.apache.cxf.feature.LoggingFeature")
public class Hellows {
	
	@WebMethod
	public String hello() {
		return "Hello";
		
	}

}
