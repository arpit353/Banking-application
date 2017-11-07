Distributed-Systems-Banking-Application

Introduction

We see distributed system in our everyday life, whether it’s world wide web or online banking system. Usage of distributed system has grown significantly. Here, we have implement distributed bank system. . We have specifically used distributed system only because it make the process faster as the work is divided among different system. Every atm is a client and the bank is the server. There is only one server and many clients because in real life also, there is only a single bank and many atms. We have provided various remote function which the atms can use such as withdrawal of money, transfer of money, view bank statement. We have provided security measures also, for eg. every time user logs in there is a session established which is valid for only 5 mins. As we know that in real world, new account can only be made on going to bank. So, here also new account can be created from server only

Execution Steps:


•	Steps to follow to run on single machine :-
1.	javac -cp interfaces\*.java
2.	jar cvf bank.jar interfaces\*.class
3.	javac client\ATM.java
4.	javac server\*.java
5.	rmiregistry 7777


•	On different terminal 
1. java -Djava.security.policy=all.policy server.Bank 7777


•	On different terminal
1. java client.ATM localhost 7777 login arpit arpit123


•	Commands for ATM
1. java client.ATM localhost 7777 deposit 88769912 100 726463
2. java client.ATM localhost 7777 withdraw 88769912 100 726463
3. java client.ATM localhost 7777 statement 88769912 03/11/2017 08/11/2017 726463


•	Steps to follow to run on different machine :-
1. rmiregistry 1099
2. java -Djava.security.policy=all.policy server.Bank 1099
3. java client.ATM 192.168.43.158 1099 login arpit123 up27q6370
