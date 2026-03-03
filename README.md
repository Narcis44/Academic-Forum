# Blue Seals Academic Forum 🎓

A full-stack web application designed for the academic environment, providing a centralized Q&A platform for students and professors. This system replaces disorganized chat groups with a structured, searchable, and persistent knowledge base.

## 🚀 Key Features

* **Role-Based Authentication:** Distinct views and permissions for `Students` (ask/answer) and `Professors` (manage courses, post endorsed replies, view analytics).
* **Dynamic Q&A Feed:** Filter discussions by specific courses, search by keywords, or toggle "Unanswered Only" questions.
* **Professor Analytics Dashboard:** Real-time statistics on course enrollments and forum activity, powered by complex SQL Views.
* **Modern UI/UX:** Responsive vanilla HTML/CSS/JS frontend featuring a **Dark Mode toggle** and automatic **Gravatar profile picture** generation.
* **TCP Socket Admin Monitor:** A dedicated multi-threaded backend module running on port `8888` for remote server health monitoring without needing HTTP access.

## 🛠️ Tech Stack

* **Backend:** Java 17, Spring Boot (Spring Web, Spring Data JPA, Hibernate)
* **Frontend:** HTML5, CSS3, Vanilla JavaScript (Fetch API)
* **Database:** MySQL / MariaDB (Normalized to 3rd Normal Form - 3NF)

## 🗄️ Database Architecture

The database (`blue_seals_db`) is strictly normalized to **3NF** to prevent data redundancy and update anomalies. It consists of:
* **Tables:** `users`, `courses`, `enrollments` (Many-to-Many resolution), `questions`, `answers`.
* **Views:** `view_course_stats` (used for the Analytics dashboard), `view_unanswered_questions`.

## ⚙️ Getting Started

To run this project locally, follow these steps:

### Prerequisites
* Java JDK 17 or higher
* Maven
* MySQL Server (e.g., via XAMPP)

### Installation

1.  **Clone the repository:**

2.  **Database Setup:**
    * Start your MySQL server.
    * Import the provided `db_setup.sql` script (usually via phpMyAdmin or command line) to build the schema and seed initial data.

3.  **Configure Backend:**
    * Open `src/main/resources/application.properties`.
    * Verify the database credentials match your local setup:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/blue_seals_db
        spring.datasource.username=root
        spring.datasource.password=
        ```

4.  **Run the Application:**
    * Execute the main class `Application.java` in your IDE, or use Maven:
        ```bash
        mvn spring-boot:run
        ```
    * The web server will start on `http://localhost:8080`.
    * The Admin Socket monitor will start on port `8888`.

## 🧪 Usage & Testing

You can log in using the pre-seeded demo accounts:

**Student Account:**
* **Email:** `ana@test.com`
* **Password:** `pass123`

**Professor Account:**
* **Email:** `ionescu@test.com`
* **Password:** `pass123`

Navigate to `http://localhost:8080/index.html` to access the platform.

## 👥 Team
Developed for the 2026 Programming III Project.
