package org.thomas.mtserver;


import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.thomas.mtserver.PoolThread;

public class MTServer implements Runnable
{
	// This class is derived from a server project I've developed many years ago for a customer.
	// Since it was a single-threaded server, the pooled multi-thread implementation was inspired by :
	//
	// http://www.tutorialspoint.com/javaexamples/net_multisoc.htm
	// http://www.tutorialspoint.com/java/java_thread_synchronization.htm
	//
	// � 2012 TutorialsPoint.COM 
	
	// Program version
	static	String currentVersion = "1.0";
	// Socket port
	static int port;
	// Number of threads handled in the pool
	static int numMaxThreads;
	
	// Define a currently running thread in pool
	protected Thread currentPoolThread = null;
	
	
	
	// Class constructor
	public MTServer()
	{
		
	}

	/**
	 * @param args
	 * 
	 * Arguments for main class are :
	 * 
	 * 		- port (required )
	 * 		- max number of threads (optional)
	 * 
	 */
	public static void main(String[] args) 
	{
		// Command arguments check on startup
		if ((args.length < 1) || (args.length > 2)) 
		{
			System.out.println("usage error: " + MTServer.class.getSimpleName() + " <tcpip port> <num_max_threads>");
			return;
		}
		
		// Get Socket port number from command arguments
		port = Integer.valueOf(args[0]);
		
		boolean	nbThreadsDefined = (args.length == 2 ? true : false);
		if (nbThreadsDefined)
			// The number of threads is defined by user	
			numMaxThreads =  Integer.valueOf(args[1]);
		else
			// Use a default number of threads if not set in command
			numMaxThreads =  10;
		
		// Echo starting server info
		System.out.println("\nMultiThreadedServer assignment");
		System.out.println("    - current version		: " + currentVersion);
		System.out.println("    - TCP/IP port listened	: " + args[0]);		
		System.out.println("    - Number of threads in pool : " + numMaxThreads);

		// Start current class in a thread
		MTServer server = new MTServer();
		new Thread(server).start();
		
	}

	@Override
	public void run() {

		synchronized(this)
		{
			// Synchronize within the run method, for thread synchronization
			this.currentPoolThread = Thread.currentThread();
		}

		try
		{
			// Try to create a new ServerSocket instance on specified port
			ServerSocket sSock = new ServerSocket(port);
			System.out.println("Waiting for connections...\n");

			try
			{	
				// Create infinite loop to wait for client requests
				while (true)
				{
					// Accept new client socket
					Socket	cs = sSock.accept();
					System.out.println("***************");
					System.out.println("NEW CONNECTION");
					System.out.println("***************\n");

					// Instead of creating an instance of PoolThread to handle the client socket in a pooled thread
					//PoolThread mThread = new PoolThread(cs);
					// and starting the thread :
					//new Thread(mThread).start();
					
					// I use the ThreadPoolExecutor to launch a thread with a fixed number of threads
					// as described in
					// http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
					//
					// Copyright � 1993, 2012, Oracle and/or its affiliates. All rights reserved. 
					
					ExecutorService execService = Executors.newFixedThreadPool(numMaxThreads);
					
					execService.execute(new PoolThread(cs));
					
				}

			}
			catch (IOException e) 
			{
				// This catch block is to handle client connection error
				System.err.println(e.getMessage());
			}
			finally
			{
				sSock.close();
			}
		}
		catch (IOException e)
		{
			// This catch block is to handle port binding error
			System.err.println("Cannot bind to port : " + port);
		}				


	}


	
 }
