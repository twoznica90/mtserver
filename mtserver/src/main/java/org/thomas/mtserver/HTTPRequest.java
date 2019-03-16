package org.thomas.mtserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPRequest {
	
	// This class parses an input stream and returns fields from the request line:
	//	- Method token
	//	- Request URI (the resource path)
	//	- Protocol version
	//
	// Source : https://www.tutorialspoint.com/http/http_requests.htm
	
	
	String reqMethod;
	String reqURI;
	String reqVersion;
	
	
	public HTTPRequest(InputStream input) throws IOException {
		
		// How to echo outputStream to client socket :
		//
		// http://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
		//
		// Copyright � 1995, 2012 Oracle and/or its affiliates. All rights reserved.
		
		
		BufferedReader clientRequest = new BufferedReader(new InputStreamReader(input));

		String clientRequestStr = clientRequest.readLine();
		String[] parsedClientRequestStr = clientRequestStr.split(" ");

		
		reqMethod = parsedClientRequestStr[0];
		reqURI = parsedClientRequestStr[1];
		reqVersion = parsedClientRequestStr[2];
		
	}

}
