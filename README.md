# Banking Application

**Description**

This is an application for handling banking transactions, opening different types of accounts, and managing customers' accounts. Customers can register, and apply to open an account. Once the account is open, customers are able to withdraw, deposit, and transfer funds between accounts. Employees and managers of the bank can view and manage all the accounts like denying/ approving/ closing them or making transactions between accounts based on their role in the bank.

**Technologies Used**

* Java 8
* Postgresql Database
* AWS RDS
* Junit for unit testing
* Log4j for logging
* DAO design pattern for data connectivity

**Features**

*All basic validation is done, such as trying to input negative amounts, overdrawing from accounts, etc.
*Employees of the bank can view all of their customers' information. This includes:
    * Account information
    * Account balances
    * Personal information
*	Employees can approve/deny open applications for accounts
*	Bank admins can view and edit all accounts. This includes:
    * Approving/denying accounts
    * withdrawing, depositing, transferring from all accounts
    * canceling accounts

**Getting Started**

Download the files for the project from the remote repository and place them in the repository on your machine
Push the code to your remote repository, and import the project into STS as a Maven project
Create a new Repository on GitHub
Clone the Project0-BankingOperations repository to your machine
cd Project0-BankingOperations
For running this project you need to Import the Maven project in STS, and have an AWS RDS PostgreSQL database, and put your database connection settings in JDBC properties in the project.
