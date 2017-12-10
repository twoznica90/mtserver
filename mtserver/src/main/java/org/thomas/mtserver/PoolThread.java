package org.thomas.mtserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.thomas.mtserver.HttpResponseForFile;

public class PoolThread implements Runnable {
	
	// Class for a pooled thread used in a MTServer thread
	
	private Socket cSock = null;
	
	static final String ROOTDIR_PATH = "htdocs/";
	
	// Constructor
	public PoolThread(Socket cSock)
	{
		this.cSock = cSock;
	}
	
	 public void run() {
		 try{
				// Get the stream from client request
			 	InputStream input = cSock.getInputStream();
			 	int length = input.available();
				StringBuffer clientRequest = new StringBuffer(length);
			
				// Create an output stream
				OutputStream output = cSock.getOutputStream();
				
				// Create a buffer array to store stream
				byte[] buffer = new byte[4096];
				int i = input.read(buffer);

				// Store stream in stringBuffer
				for (int j=0; j<i; j++) 
				{	
					clientRequest.append((char) buffer[j]);		
				}
				
				String clientRequestStr = clientRequest.toString();			

				// At this point, I output a message to display the client requestHeader
				// for debug purposes.
				System.out.println("NEW THREAD WITH REQUEST :\n");
				System.out.println("----------------------------");
				System.out.println(clientRequest);

				
				// Define a default filename
				String filename = "";
				
				// I did the test with various web browsers and versions, and
				// figured out, from that, a way to extract the request URI in the header.
				
				// This webserver only responds to GET requests
				if (clientRequestStr.contains("GET"))
				{	
					// Since I only want to echo a file to the client, 
					// I read the requested file name after the GET tag.
					int beginIndex = clientRequestStr.indexOf("GET") + 5;
					int endIndex = clientRequestStr.indexOf("HTTP") - 1;
					filename = clientRequestStr.substring(beginIndex, endIndex);
					
					// Append filename to the path of the files root folder
					filename = ROOTDIR_PATH + filename;
				}
				else
				{
					System.out.println("This webserver only serves GET requests");
				}
				
				// Create a file from given filename
				File file = new File(filename);
				System.out.println("filename : " + filename + " exists : " + file.exists());
				System.out.println("----------------------------\n");
				
				
				// From here, I found in this tutorial how to echo outputStream to client socket :
				//
				// http://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
				//
				// Copyright � 1995, 2012 Oracle and/or its affiliates. All rights reserved.
				

				// Get the response for this file
				HttpResponseForFile response = new HttpResponseForFile(file);
								
				// Print the response in console
				System.out.println("RESPONSE:\n");
				System.out.println(response + "\n");

				// Send the response to the client
				byte[] byteresp = response.toString().getBytes();
				output.write(byteresp);
				
				// Close socket
				System.out.println("**************");
				System.out.println("CLOSING SOCKET");
				System.out.println("**************");
				System.out.println("-----------------------------------------------------------\n\n\n");
				cSock.close();
				
					

			}
			catch(IOException e) {
				e.printStackTrace();
			}
	    }

}
