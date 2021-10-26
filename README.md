# Project Banking Application

**Description**

Leveraging Java 8, create an application that simulates simple banking transactions

**Getting Started**

You have two options to get started,

1.
   * Create a new Repository on GitHub and clone it to your machine
   * Download the files for the project from the remote repository and place them in the repository on your machine
   * Push the code your remote repository, and import the project into STS as a Maven project
2.
   * Create a new Repository on GitHub
   * Clone the Project0-BankingOperations repository to your machine
   * `cd Project0-BankingOperations`
   * `git remote set-url origin https://github.com/YOURGITREPOURLGOESHERE.git` this will start tracking the repo you just created
   * `git push -u origin master` this will push the project 0 code template to your created repository
   * Import the project as a Maven project in STS and start coding

**Requirements**
*	Build the application using Java 8
*	All interaction with the user should be done through the console using the `Scanner` class
*	Customers of the bank should be able to register with a username and password, and apply to open an account.
    * Stretch Goal: Customers should be able to apply for joint accounts
*	Once the account is open, customers should be able to withdraw, deposit, and transfer funds between accounts
    * All basic validation should be done, such as trying to input negative amounts, overdrawing from accounts etc.
*	Employees of the bank should be able to view all of their customers information. This includes:
    * Account information
    * Account balances
    * Personal information
*	Employees should be able to approve/deny open applications for accounts
*	Bank admins should be able to view and edit all accounts. This includes:
    * Approving/denying accounts
    * withdrawing, depositing, transferring from all accounts
    * canceling accounts
*	Reasonable test coverage for the service layer is expected using JUnit.
    * TDD is encouraged.
*	Logging should be accomplished using Log4J
    * All transactions should be logged

* Create an SQL script that will create a user in an SQL database and a table schema for storing your bank users and account information.
* Your database should include at least 1 stored procedure or trigger.
* Have your bank application connect to your SQL database using JDBC and store all information that way.
* You should use the DAO design pattern for data connectivity.
