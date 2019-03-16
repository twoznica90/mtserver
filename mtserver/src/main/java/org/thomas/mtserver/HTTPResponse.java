package org.thomas.mtserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class HTTPResponse {
	
	// This class returns an HTTP response to a request submitted by HTTPRequest
	// Source : https://www.tutorialspoint.com/http/http_responses.htm

	static final String STATUS_OK = "200 OK";
	static final String STATUS_NOT_FOUND = "404 Not Found";
	static final String STATUS_BAD_REQUEST = "400 Bad Request";
	static final String STATUS_NOT_IMPLEMENTED = "501 Not Implemented";
	
	static final String DOCROOT_PATH = "./htdocs";
	
	
	private List<String> headers = new ArrayList<String>();
	private byte[] body;
	
	
	public HTTPResponse(HTTPRequest request) throws IOException {


		if (request.reqMethod.equals("GET"))
		{
			String resourcePath = request.reqURI;
			// For requests on server root, a file named index.html will be served by default
			if (resourcePath.equals("/"))
				resourcePath = "/index.html";					

			try {
				// Create a file from the resource defined by the URI
				File file = new File(DOCROOT_PATH + resourcePath);
				if (file.exists()) {
					setHeaders(request.reqVersion, STATUS_OK);
					setContentKeys(file, headers);
					setBody(Files.readAllBytes(file.toPath()));
				} else {
					// Return a 404 whether the file is not found or the request URI isn't a file 
					setHeaders(request.reqVersion, STATUS_NOT_FOUND);
					setBody(STATUS_NOT_FOUND);
				}
			} catch (Exception e) {
				setHeaders(request.reqVersion, STATUS_BAD_REQUEST);
				setBody(STATUS_BAD_REQUEST);
			}
		}
		else
		{
			// Methods other than GET are not handled
			setHeaders(request.reqVersion, STATUS_NOT_IMPLEMENTED);
			setBody(STATUS_NOT_IMPLEMENTED);
		}
		
	
	}


	private void setHeaders(String version, String status) {
		headers.add(version + " " + status);
	}

	private void setBody(String response) {
		body = response.getBytes();
	}

	private void setBody(byte[] response) {
		body = response;
	}
	
	
	private void setContentKeys(File file, List<String> headers) {
		// When the resource is a file, this method adds the header fields Content-Length and Content-Type 
		headers.add("Content-Length: " + String.valueOf((int)file.length()));
		
		if (file.getName().endsWith(".htm") || file.getName().endsWith(".html")) {
			headers.add("Content-Type: " + "text/html");
		}
		else if (file.getName().endsWith(".txt")){
			headers.add("Content-Type: " + "text/plain");
		}
		else if (file.getName().endsWith(".xml")){
			headers.add("Content-Type: " + "text/xml");
		}
		else if (file.getName().endsWith(".css")){
			headers.add("Content-Type:" + "text/css");
		}
		else if (file.getName().endsWith(".js")){
			headers.add("Content-Type:" + "application/javascript");
		}
		else if (file.getName().endsWith(".json")){
			headers.add("Content-Type:" + "application/json");
		}
		else if (file.getName().endsWith(".png")){
			headers.add("Content-Type:" + "image/png");
		}
		else if (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")){
			headers.add("Content-Type:" + "image/jpeg");
		}
		else if (file.getName().endsWith(".mp3") || file.getName().endsWith(".mpeg")){
			headers.add("Content-Type:" + "audio/mpeg");
		}
	}
	

	public void addToOutput(OutputStream os) throws IOException {
		// Get the output stream to write into
		DataOutputStream output = new DataOutputStream(os);
		
		// Add the headers to the output
		for (String header : headers) {
			output.writeBytes(header + "\r\n");
		}
		output.writeBytes("\r\n");
		
		// Add the body to the output
		if (body != null) {
			output.write(body);
		}
		
		output.writeBytes("\r\n");
		output.flush();
	}
	
	

}
