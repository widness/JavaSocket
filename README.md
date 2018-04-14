@TODO
Minimal specifications of the server:
 The server must be able to:
o Register new clients
o Maintain a list of registered clients (and the files they share)
o Give back a client IP and its list of file to another client
o Accept multiple clients simultaneously (use threads).
 The server must be able to write logs:
o On a file
o The history must be kept (one file per month)
o 3 levels of log (info, warning, severe) should be handled
 Info for all the useful operations
 Warning for all the possible network errors
 Severe for the exceptions
 You can use command words to discuss between the client and the server or use different ports on the
server for the client communications 
HES-SO Valais-Wallis • rue de la Plaine 2 • 3960 Sierre
+41 27 606 89 11 • info@hevs.ch • www.hevs.ch
Minimal specifications of the client:
 The client will be able to connect to the server through socket connections
 The client should be able to give its list of file to the server
 The client should be able to give its IP address
 The client should be able to get a list of clients with their available files
 The client should be able to ask for another client IP address
 The client should be able to connect to another client and ask for a file (one after the other)
 The client should be able to accept a network connection from another client and give back a file
The features described hereafter are examples of possible add-on that could be developed according to the
progress of your project:
 A client can handle multiple input connections simultaneously
 A client can handle multip