#  Library Minimal Management System

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://www.java.com/)
[![JavaFX](https://img.shields.io/badge/JavaFX-UI-0A66C2?style=flat&logo=openjdk&logoColor=white)](https://openjfx.io/)
[![Oracle](https://img.shields.io/badge/Oracle-Database-F80000?style=flat&logo=oracle&logoColor=white)](https://www.oracle.com/database/)
[![JDBC](https://img.shields.io/badge/JDBC-Database%20Connectivity-59666C?style=flat)]()
[![MVC](https://img.shields.io/badge/Architecture-MVC-4CAF50?style=flat)]()

> A modern desktop application built with JavaFX for managing library operations including books, members, borrowing, and administrative tasks.

---

#  Table of Contents

- [About](#-about)
- [Features](#-features)
- [Technologies](#-technologies-used)
- [Architecture](#-architecture)
- [Class Diagram](#-class-diagram)
- [Screenshots](#-screenshots)
- [Installation](#-installation)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Future Improvements](#-future-improvements)
- [Contributing](#-contributing)
- [Author](#-author)

---

#  About

Library Minimal Management System is a desktop application designed to simplify the management of a library.  
The application provides a clean JavaFX interface that allows administrators and users to interact with the system efficiently.

The system supports:

-  Book management
-  User/member management
-  Borrowing and returning operations
-  Book search functionality
-  Administrator dashboard
-  Librarian management

This project was developed following the **MVC architecture pattern** to ensure maintainability and scalability.

---

#  Features

##  Book Management
- Add new books
- Update book information
- Delete books
- View detailed book information
- Manage library inventory

##  User & Librarian Management
- Add users/members
- Manage librarians
- Authentication system
- User and admin dashboards

##  Borrowing System
- Borrow books
- Return books
- Track borrowed books
- Manage availability status

##  Search System
- Search books by title or information
- Quick inventory lookup

##  User Interface
- Modern JavaFX GUI
- Simple and intuitive navigation
- Responsive desktop interface

---

#  Technologies Used

## Backend & Logic
- **Java**
- **JDBC**
- **Oracle Database**

## Frontend
- **JavaFX**

## Architecture
- **MVC Pattern**

## Development Tools
- **IntelliJ IDEA / Eclipse**
- **Git & GitHub**
- **Maven**

---

#  Architecture

The project follows the **MVC (Model-View-Controller)** architecture.

```text
┌─────────────┐
│    View     │  → JavaFX User Interface
└─────┬───────┘
      │
      ▼
┌─────────────┐
│ Controller  │  → Handles user interactions
└─────┬───────┘
      │
      ▼
┌─────────────┐
│    Model    │  → Business logic & database operations
└─────────────┘
```

### Main Components

- **View Layer** → JavaFX interfaces
- **Controller Layer** → User actions handling
- **Model Layer** → Business logic and JDBC database communication

---

#  Class Diagram

## Draw.io Diagram

<img width="700" height="418" alt="Class Diagram" src="https://github.com/user-attachments/assets/24e35a94-a6e2-497b-a9f4-7459e3803f79" />

---

#  Project Presentation

## Presentation Document
[📄 View Presentation PDF](https://github.com/user-attachments/files/27053892/Notre.projet.a.vu.le.jour.dans.un.contexte.de.transformation.digitale.accrue.des.entreprises.Face.a.la.multiplication.des.outils.de.gestion.et.de.communication.le.Groupe.Frame.a.identifie.un.bes.1.pdf)

---

#  Screenshots

## Login Page
<img width="1217" height="727" alt="Login Page" src="https://github.com/user-attachments/assets/ab692092-b2fa-4f68-9aa7-bcc46d470428" />

---

##  Admin Dashboard
<img width="1256" height="798" alt="Dashboard Admin" src="https://github.com/user-attachments/assets/c37fdf4b-b3e7-4be4-a980-58a0297ed185" />

---

##  User Dashboard
<img width="1256" height="792" alt="Dashboard User" src="https://github.com/user-attachments/assets/6722a0f9-c10b-40d2-96b5-4e489dcbf7af" />

---

##  Library Inventory
<img width="1261" height="805" alt="Library Inventory" src="https://github.com/user-attachments/assets/100b3b0c-e959-4cdc-b98b-1deef2e2a23a" />

---

##  Modify Book
<img width="1267" height="802" alt="Modify Book" src="https://github.com/user-attachments/assets/5ca8fa77-d3c8-4437-aca1-d22092fbee57" />

---

##  Book Details
<img width="1273" height="798" alt="Book Details" src="https://github.com/user-attachments/assets/5a3a954f-7b10-4a5a-8afd-43a633e532f7" />

---

##  Borrow Book
<img width="1267" height="789" alt="Borrow Book" src="https://github.com/user-attachments/assets/dd6990f3-1a3a-4500-9b40-88e14a5d7e79" />

---

##  Return Book
<img width="1260" height="795" alt="Return Book" src="https://github.com/user-attachments/assets/2d63c2cc-6006-454b-a575-385d01ba33d8" />

---

#  Installation

## Prerequisites

Make sure you have installed:

- Java JDK 17+
- JavaFX SDK
- Oracle Database
- Maven
- Git
- IntelliJ IDEA or Eclipse

---

## Clone the Repository

```bash
git clone https://github.com/Belak17/LibraryManagement.git
cd LibraryManagement
```

---

## Configure the Database

Update your database configuration inside the project files.

Example:

```properties
DB_URL=jdbc:oracle:thin:@localhost:1521:xe
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

---

## Build the Project

```bash
mvn clean install
```

---

## Run the Application

```bash
mvn javafx:run
```

---

#  Usage

1. Launch the application
2. Login as admin or user
3. Manage books and members
4. Borrow or return books
5. Search books in the inventory

---

#  Project Structure

```text
LibraryManagement/
│
├── src/
│   ├── model/
│   ├── view/
│   ├── controller/
│   ├── dao/
│   └── utils/
│
├── resources/
├── database/
├── pom.xml
└── README.md
```

---

#  Future Improvements

- Email notifications
- Fine management system
- Statistics dashboard
- Book reservation system
- Export reports to PDF
- Multi-language support
- Dark mode UI

---

#  Contributing

Contributions are welcome!

## Steps

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature-name
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push to GitHub

```bash
git push origin feature-name
```

5. Open a Pull Request

---

#  Report Issues

If you find a bug or want to suggest improvements, please open an issue in the repository.

---

#  Author

## Kaleb AKAKPO

- Backend Developer
- Java & Spring Boot Enthusiast

###  LinkedIn
[LinkedIn Profile](https://www.linkedin.com/in/kaleb-akakpo-5a9431355/)

---

<div align="center">

###  If you like this project, consider giving it a star on GitHub!

</div>
