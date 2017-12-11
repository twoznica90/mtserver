# mtserver

This multi-threaded web server serves static files from a rocroot through GET requests only.
It's pretty basic, its purpose being mainly educational as it uses standard Java packages (no dependencies).

It has been developed with Eclipse and built as a jar with the Maven m2e plugin, from the project archetype *maven-archetype-quickstart*.

The jar is built in the **/target** folder and the project contains scripts for an easy execution.


## Description

- The class **MTServer**, which contains the main method, implements the Runnable interface and initiates a connection with a pool thread. The max. number of threads can be configured by the user (see usage below).

- Then each thread is an instance of the class named **PoolThread**, which also implements the Runnable interface. It handles the client request and retreives the corresponding file from the folder *htdocs*.

- The class **HttpResponseForFile** returns a valid http response based on the file to the running thread, which in turn sends it back to the client.



## Usage

Main method is executed with 2 arguments :

- port number (required)
- max number of threads (optional - default value = 10)

A start script **launch\_MTServer** is also provided in two forms (.sh and .bat) :

```sh
java -cp target/mtserver-0.0.1-SNAPSHOT.jar org.thomas.mtserver.MTServer 8082
pause
```

Output debug messages in the console have been left for information.

Once the server started, place the files to be served in the **htdocs** folder (there's a sample *index.html* and also a *document.txt* file), open a browser and enter a URL like :

localhost:8082/index.html

