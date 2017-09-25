*Compile*
javac Server_201501051.java
javac Client_201501051.java
javac Interface_201501051.java
javac ImplExample.java

*Run*
java Server_201501051
java Client_201501051

*Functions*
-> All the required methods are implemented.
-> Client has a command prompt which invokes the method in server

primality [number]: returns yes if number is prime else no
palindrome [string]: returns yes if string is palindrome else no
fibonacci [number]: returns nth fibonacci number
caseConvert [string]: returns string having case converted from lower to upper and upper to lower

-> Uses Diffie-Hellman Key exchange protocol
-> Key is generated in the server and sent to the client
-> While sending command to server command is encrypted
-> While returning output from server. Output is encrypted and then sent
