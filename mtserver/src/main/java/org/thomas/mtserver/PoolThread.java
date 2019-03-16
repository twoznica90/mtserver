package org.thomas.mtserver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.thomas.mtserver.HTTPResponse;

public class PoolThread implements Runnable {
	
	// Class for a pooled thread that handles requests from MTServer
	
	private Socket cSock = null;
	
	
	// Constructor
	public PoolThread(Socket cSock)
	{
		this.cSock = cSock;
	}
	
	 public void run() {
		 try{
				// Get the stream from client request
			 	InputStream input = cSock.getInputStream();
				// Create an output stream
				OutputStream output = cSock.getOutputStream();
				
				// Input stream is parsed by HTTPRequest
				HTTPRequest request = new HTTPRequest(input);
			 	System.out.println("........................................");
			 	System.out.println(request.reqMethod);
			 	System.out.println(request.reqURI);
			 	System.out.println(request.reqVersion);
			 	
			 	// Resulting request is processed by HTTPResponse			 	
				HTTPResponse response = new HTTPResponse(request);
				response.addToOutput(output);
				
				// Close socket
				cSock.close();

			}
			catch(IOException e) {
				e.printStackTrace();
			}
	    }

}
