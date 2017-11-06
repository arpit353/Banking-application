# CT414-Distributed-Systems-Banking-Application

Problem Description

In this assignment you will implement a Distributed Banking System that consists of a server and some Automated Teller Machine (ATM) clients. The server manages all users’ account information. A customer can invoke the following operations at an ATM.
- void deposit(int acnt, int amt): this operation increases the balance of user account acnt by amt, and returns nothivoid
- withdraw(int acnt, int amt): this operation decreases the balance of user account acnt by amt, and returns nothing
- float inquiry(int acnt): this operation returns the balance of user account account
- Statement getStatement(Date from, Date to): this operation returns a statment object encapsulating transactions over a time period

For simplicity, in this assignment you do not need to consider the synchronization problem where multiple clients run concurrently.

# Compilation Steps
1) Compile the interfaces into a JAR file
- In the src directory type `javac -cp <root_from_C:_to_\src interfaces\*.java`
  This will compile the interfaces into .class files
- Then type `jar cvf bank.jar interfaces\*.class` 
   Again from the `src` directory. This will compile a class out of the interface files. 

2) Compile the client files
- in the `src` directory again type `javac client\ATM.java`

3) Compile the server files
- in the `src` directory type `javac server\*.java`

3) Now all the files are compiled. The next step is to start the rmiregistry:
- type `rmiregistry 7777` (can be any port, this will start the registry on port 7777)

4) Note: You will need to change the `all.policy` file to update the path to be the path to the `src` directory on your machine to make the RMI SecurityManager work. There is an example `all.policy` file included as an example.

5) In a new console tab start the server:
- type `java -Djava.security.policy=all.policy server.Bank 7777` (needs to be the same port as the rmiregistry.

6) In a new console tab start the client and use client actions:
- type `java client.ATM localhost 7777 login user1 pass1` (this will open the client and create a session in the server.


# System Requirements

You are required to write this client-server (banking system) program which communicates via Java RMI. Specifically, your program should consist of two parts: one for the client and another for the server. The client (as the ATM) will initiate an operation by calling a remote method on the bank server to execute a specified procedure (e.g. deposit) with parameters.
You can use the following interface and class definitions as a starting point for your project. However, you are free to develop your own interface and class definitions so long as the basic requirements are still met e.g. you might want to add more exception handling to the remote methods or add some additional features as you see fit.

The Bank server should be initialised with a number of test accounts that have various balances that can then be accessed by the ATM clients.
The bank server program has only one command line parameter “server_port”, which specifies the port of rmiregistry. The default port of rmiregistry is 1099, but we may have to use other ports, if 1099 has already been used by some other programs.
You will need to define suitable classes for Account and Transaction and also provide a class that implements the Statement interface.

The command line parameters of the ATM client application will include:
- server_address: the address of the rmiregistry
- server_port: the port of the rmiregistry
- operation: one of "login", "deposit", "withdraw", and "inquiry"
- account: the user account
- username: only for "login" operation
- password: only for "login" operation
- amount: only for “deposit” and “withdraw” operations

The ATM client application can be run at the command line using the parameters shown and a full GUI based client application is not required. The first operation that is called is login and if this succeeds a session ID is returned which is then valid for some predefined time period. This session ID then acts as an authentication token that must be passed for each of the other remote methods.

The statement for account 100 for the period shown is returned and printed out using the accessor methods in the statement object
The assignment should be done in groups of two students. Where the assignment is submitted by one student then don't forget to mention the name and ID number of the other person in your group so that they will also be credited for the assignment. When completed you should submit copies of the source code you have written for the assignment as well as a description of how you tested it and screen shots of the application running. All submissions should be done via Blackboard and if you submit more than one attempt then only the final attempt will be marked.

The ATM client application can be run at the command line using the parameters shown and a full GUI based client application is not required. The first operation that is called is login and if this succeeds a session ID is returned which is then valid for some predefined time period. This session ID then acts as an authentication token that must be passed for each of the other remote methods. Running the application will then look something like this:

C:\>rmiregistry 7777

Then, start the server
C:\>java Bank 7777

Finally, test the ATM client application
C:\>java ATM localhost 7777 login user1 pass1

Successful login for user1 session is valid for 5 minutes

C:\>java ATM localhost 7777 inquiry 100

The current balance of account 100 is €1000

C:\>java ATM localhost 7777 deposit 100 200 

Successfully deposited €200 to account 100!

C:\>java ATM localhost 7777 withdraw 100 50

Successfully withdrew €50 from account 100!

C:\>java ATM localhost 7777 inquiry 100

The current balance of account 100 is €1150

C:\>java ATM localhost 7777 statement 100 01/10/2016 31/12/2016

The statement for account 100 for the period shown is returned and printed out using the accessor methods in the statement object
