# PasswordCracker

PasswordCracker is an assignment completed in the Data Structures and Algorithms Course (CSI 2110) at the University of Ottawa. The purpose of this assignment was to familiarize ourselves with **Hash Maps** and **Encryption**.  Given a list of 10 000 common passwords ([commonPwd10K](https://github.com/EricHaggar/PasswordCracker/blob/master/commonPwd10K.txt)), 5 augmentation rules had to be applied to this list in order to generate as many passwords as possible. Here are the 5 rules: 

1. Capitalize the first letter of each word starting with a letter, e.g. dragon becomes Dragon
2. Add the current year to the word, e.g. dragon becomes dragon2018
3. Use @ instead of a, e.g. dragon becomes dr@gon
4. Use 3 instead of e, e.g. baseball becomes bas3ball
5. Use 1 instead of i, e.g. michael becomes m1chael
You can also combine these rules to generate even more password.

**Chosen Algorithm** : I decided to use the Power Set Theorem in order to generate all possible combinations of these 5 rules.

Once all passwords generated and hashed using the SHA-1 algorithm, they are stored in 2 databases. The first is a java.util.HashMap and the second is my own implementation of a hash table. The [leakedAccounts](https://github.com/EricHaggar/PasswordCracker/blob/master/leakedAccounts.txt) are then searched in the databases to crack the passwords.

## Getting Started 

Clone the repository with:

```
git clone https://github.com/EricHaggar/PasswordCracker.git
```

Change your directory to the project

```
cd PasswordCracker
```

### Prerequisites

Make sure you have the Java SE Development Kit 8 or higher. If not install it from:

    https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html


## Running the tests

Open a terminal within the directory and compile the all java files using:

```
javac *.java
```
Run the test 

```
java Tester
```

## Built With

* [HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)
* [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)

## More Information

For more information on the assignment requirements, please see 

* [Assignment Handout](https://github.com/EricHaggar/PasswordCracker/blob/master/Assignment%20Handout.pdf)



