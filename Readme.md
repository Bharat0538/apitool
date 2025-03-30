Built with ❤️ by [Bharat Kumar Ahirwar]
GitHub | LinkedIn
# 🛠️ ApiTool – API Testing Tool (Web-based)

**ApiTool** is a lightweight, web-based API testing tool (like Postman) built with **Spring Boot**, **Thymeleaf**, and **Java 21**. It lets developers test REST APIs via a modern UI and stores request history for easy re-use.

---

## 🚀 Features

- 🔍 Send requests with **GET, POST, PUT, PATCH, DELETE**
- 🧾 Add custom **headers**, **query parameters**, and **request body**
- 💬 Parse full `curl` commands and auto-fill the form
- 📜 View full **request history**
- 🛡️ Built-in support for **JSON APIs**
- 🎨 Pretty and responsive UI

---

## 💻 Tech Stack

- Backend: `Java 21`, `Spring Boot 3.4.3`
- Frontend: `Thymeleaf` + `HTML/CSS/JS`
- DB: `H2` (in-memory, with console)
- HTTP Client: Apache HttpClient 5

---

## 📦 Running the App

```bash
# 1. Clone the repo
git clone https://github.com/yourusername/ApiTool.git
cd ApiTool

# 2. Run the Spring Boot application
./mvnw spring-boot:run


curl -X GET "https://jsonplaceholder.typicode.com/posts/1"

curl -X POST "https://jsonplaceholder.typicode.com/posts" \
     -H "Content-Type: application/json" \
     -d '{"title": "ApiTool", "body": "Test", "userId": 1}'


