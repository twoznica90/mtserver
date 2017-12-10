package org.thomas.mtserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.NavigableMap;
import java.util.TreeMap;


public class HttpResponseForFile {
	
	
	// This class returns an HTTP response based on an input file
	
	public static final String OK = "200 OK";
	public static final String NOT_FOUND = "404 Not Found";

	private String response;
	private NavigableMap<String, String> headers = new TreeMap<String, String>();
	private byte[] body;
	private String status;
	
	public HttpResponseForFile(File file) 
	{
		if (file.isFile()) {
			try {
				// Get a stream from file
				FileInputStream reader = new FileInputStream(file);
				int length = reader.available();
				body = new byte[length];
				reader.read(body);
				reader.close();
				
				// Insert some headers to the response
				headers.put("Date", new Date().toString());
				headers.put("Content-Length", String.valueOf(length));
				
				if (file.getName().endsWith(".htm") || file.getName().endsWith(".html")) {
					headers.put("Content-Type", "text/html");
				} else {
					headers.put("Content-Type", "text/plain");
				}
			} catch (IOException e) {
				System.err.println("Error while streaming " + file.getName());
			}

			status = OK;
		}

		else
		{
			status = NOT_FOUND;
		}


		// Set the response
		response = "HTTP/1.0 " + status +"\n";
		for (String key : headers.descendingKeySet()) {
			response += key + ": " + headers.get(key) + "\n";
		}
		response += "\r\n";
		if (body != null) {
			response += new String(body);
		}
		
	}


	@Override
	public String toString() {

		// Return type for this class is String
		return response;
	}
	
	
	
}