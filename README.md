<em>You can find the jar there: JavaSocket/out/artifacts/</em><br/>
<strong>Server side</strong>
1. Create a config.txt (where you start the application) (Ex in C:\Users\MyName)<br/>
  1.1 Enter: [IP];[NetworkInterface];45000
    Ex: 192.168.1.125;wlan1;45000
2. In the same place as the config.txt, create a folder named logger<br/>
(This gonna save your log)
   
3. Start the server application

<strong>Client side</strong>
1. Same as the server (enter the port 45000, but your default used port gonna be the 45001 if you go in a server mode)

**  User Manual for client application  **

=> Start the .jar file for the client's part.


1. First, the server will ask you your pseudo and password:
	- if you're a new client, the server creates your account;
	- else, it checks your input.
	
2. A new repository "JavaSocket" is created on your desktop (if it doesn't already exist). You will be asked to add the files you want to share in this folder and then press the ENTER key.

3. The third step is to choose what action you want to do. You have the choice between two actions, the first is just to register. You will be transformed into a new server and will be able to accept other clients' connections. The second option is to connect as "guest", you will be able to download files from another clients.

4. In the case you choose the first option, then, you just turn into a new server and that's all. It's the moment to take a break and have a coffee :-)
In the second case, a list of files offered by the other clients is shown. The server asks you to type the number (index) of the file you want to download. You can type a few numbers, all the files will be added to your "wish list". When all the files you want are added to your list, you can type -1. It will send the request to the differents clients to download their files.

5. A new connection is made to all the clients who host some files you want to download. A new repository is created on your desktop, this time, its name is "DownloadsP2P". The files you added in your wish list are now downloaded in this folder. Enjoy your new content !
