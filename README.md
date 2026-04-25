
# Customer Management System (Full-Stack)

A high-performance full-stack application built to manage and display a large-scale database of over **1 million customer records**. This project demonstrates efficient data handling, bulk database operations, and smooth front-end rendering using pagination.

### Key Features
* **Bulk Data Generation:** Optimized backend logic to generate and insert 1,000,000 unique customer records into the MariaDB/MySQL database using batch processing.
* **Server-Side Pagination:** Integrated Spring Data JPA pagination to fetch data in small chunks, ensuring zero lag even with millions of records.
* **Modern Frontend:** Developed a responsive UI with React and Vite, featuring a dynamic pagination system for seamless navigation.
* **CORS Security:** Configured Cross-Origin Resource Sharing for secure communication between the frontend and backend.

### Tech Stack
* **Backend:** Java, Spring Boot, Spring Data JPA
* **Database:** MariaDB / MySQL
* **Frontend:** React.js (Vite), CSS3
* **Tools:** IntelliJ IDEA, Postman, HeidiSQL, Maven

### How to Run
1. **Backend:** Configure your database in `application.properties` and run the Spring Boot application.
2. **Frontend:** Navigate to the frontend folder, run `npm install` and then `npm run dev`.
3. **Generate Data:** Visit `http://localhost:8080/api/customers/generate-bulk-data` to populate 1 million records.
