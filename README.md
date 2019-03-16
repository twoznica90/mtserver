# MTserver

This multi-threaded web server serves static files from a rocroot through GET requests only.
It's pretty basic, its purpose being mainly educational as it uses standard Java packages (no dependencies).

It has been developed with Eclipse and built as a jar with the Maven m2e plugin, from the project archetype *maven-archetype-quickstart*.

The jar is built in the **/target** folder and the project contains scripts (.sh and .bat) for quick execution.


## Description

- The class **MTServer**, which contains the main method, implements the Runnable interface and initiates a connection with a pool thread. The max. number of threads can be configured by the user (see usage below).

- Then each thread is an instance of the class named **PoolThread**, which also implements the Runnable interface. It handles the client request (through the HTTPRequest class) and retrieves the corresponding file from the folder *htdocs*.

- The class **HTTPResponse** returns a valid http response to the running thread, which in turn sends it back to the client.



## Usage

The project already contains the built jar in the **target** folder. It can also be rebuilt with **mvn clean install**.
To start the server, execute the jar with 2 arguments :

- port number (required)
- max number of threads (optional - default value = 8)

A start script **launch\_MTServer** is also provided in two forms (.sh and .bat), using port 8082:

```sh
java -jar target/mtserver-0.0.1-SNAPSHOT.jar 8082
pause
```

Some output debug messages in the bash window have been left for information.

Once the server started, place the files to be served in the **htdocs** folder (there's a sample web application for testing).  Just open a browser and request the URL  :

localhost:8082

