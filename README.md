
# Money Transfer Application

## Overview
A simple **Money Transfer Application** that includes a **server-side backend API** built with Spring Boot and a **CLI-based client** that interacts with the API. The system allows users to register, authenticate, manage their bank accounts, send/receive money, and view transaction history. It also includes administrative features for managing users and accounts.

The **backend API** follows a RESTful architecture, exposing endpoints for account management, transactions, and authentication. The **CLI client** serves as the user interface, allowing users to perform operations by making API requests and displaying results in a user-friendly format.

## Core Features
- **User Registration & Authentication**  
  ✅ Create an account and log in securely via CLI (calls backend API).

- **Account Management**  
  ✅ View account balance and update profile details.

- **Money Transfers**  
  ✅ Send/receive money between user accounts with balance validation.

- **Transaction History**  
  ✅ View past money transfers with detailed information.

- **Error Handling**  
  ✅ Prevent invalid transactions and ensure authentication is required for all actions.

## Tech Stack & Tools
- **Backend:**  
  🔹 Java, Spring Boot (MVC), JDBC (DAO)

- **Database:**  
  🔹 PostgreSQL

- **Testing:**  
  🔹 JUnit (Unit & Integration Tests)

- **API:**  
  🔹 RESTful API, Postman for testing

- **Client:**  
  🔹 CLI-based application for user interactions

- **Security:**  
  🔹 Basic Authentication (upgrade to JWT in the future)

- **Version Control:**  
  🔹 Git & GitHub

- **IDE:**  
  🔹 IntelliJ IDEA

## Architecture
- **MVC Design Pattern:**  
  ✅ Model (data), Controller (business logic), View (CLI interface)

- **DAO Layer:**  
  ✅ Interfaces & JDBC used for database access, ensuring modular and maintainable code.

- **Client-Server Architecture:**  
  ✅ The **backend API** serves as the central point for communication.  
  ✅ The **CLI client** interacts with the API to execute user commands and display responses.

## User Roles
- **Admin**  
  Admins have full access to all features, including managing users and viewing all accounts and transactions.

- **User**  
  Users can only manage their own accounts, transfer money, and view their own transaction history.

## Features & User Options

### **CLI User Options**
- **Create New Account**
  - Create a bank account (not a user account for the application).

- **View All Accounts**
  - View a list of all bank accounts belonging to the logged-in user.

- **View Account Details**
  - View specific details of a bank account by entering its ID.

- **Transfer Money**
  - Send money to another account after validating balance.

- **View Transactions Sent**
  - View a list of all transactions sent by the logged-in user.

- **View Transactions Received**
  - View a list of all transactions received by the logged-in user.

- **Exit**
  - Exit the application.

### **Admin-Only Features**
- **Delete Options**
  - Delete accounts or users. (Restricted to admins only.)

- **GET Functions**
  - Admins can retrieve lists of all accounts, users, and transactions in the system.

### **User Restrictions**
- **GET Functions:**
  - Regular users can only use GET functions if it retrieves information related to their own accounts or transactions.

- **No Access Before Login:**
  - The CLI and API both require authentication before any features can be accessed.

## Login Credentials

- **Admin User:**
  - Username: `guest_admin`
  - Password: `ADMIN`

- **Regular User:**
  - Username: `guest`
  - Password: `USER`

## Installation & Setup

1. Clone the repository:
    ```bash
    git clone <https://github.com/jettlewisd/money-sender-backend-cli.git>
    ```

2. Navigate to the project directory:
    ```bash
    cd <money_sender_backend_cli>
    ```

3. Build the project using Maven:
    ```bash
    mvn clean install
    ```

4. Start the application:
    ```bash
    mvn spring-boot:run
    ```

5. Access the application through the CLI or Postman by interacting with the REST API.

**The SQL script for this project is located in the Resources folder.

## License
This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.

---

Feel free to reach out if you have any questions or suggestions!