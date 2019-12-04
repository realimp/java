## Background

Transactions project is given for the current task to check and modify. It is available in *The Task* folder. Below is a brief explainer of what's in it.

Bank (represented by Bank class) stores accounts represented by Account class with two fields - money and accNumber. Bank has a plenty of customers. They can transfer funds between accounts and check their account balance all in a highly concurrent environment.

All transactions larger than 50000 are verified by security service. Let's assume that there are about 5% of such transactions. Method *Bank.isFraud* is responsible for this check up and it cannot be modified.

Security service is short on staff and works slowly. It cannot check more than one transaction at a time. And it takes 1000 ms to verify each transaction. If security service finds a fraud transaction it blocks both accounts that are involved.

## The Task

1. Implement *transfer* method of Bank class for transferring funds between two accounts. If transaction is larger than 50000 then after it is finished it is sent to security service for review. If it's a fraud both accounts are blocked.
2. Implement method *getBalance* of Bank class which returns amount of funds available.
3. Emulate the work of such a bank to test implemented methods.