# 🗄️ Database Schema

```mermaid
erDiagram
	USER ||--o{ ORDER : places
	USER ||--o{ CART : owns
	USER ||--o{ ADDRESS : has
	USER ||--o{ MESSAGE : sends

	PRODUCT ||--o{ ORDER_ITEM : included_in
	PRODUCT ||--o{ CART_ITEM : in_cart

	ORDER ||--o{ ORDER_ITEM : contains
	ORDER ||--o{ PAYMENT : paid_by

	CART ||--o{ CART_ITEM : contains

	ORDER_ITEM }|..|{ CART_ITEM : related_to

	ADDRESS }|..|{ USER : belongs_to

	MESSAGE }|..|{ USER : belongs_to

	PAYMENT }|..|{ ORDER : for
```
# 🏰 Architecture

```mermaid
graph TB
	User[🧑‍💻 User] -->|REST| Backend[🛒 Spring Boot API]
	Backend -->|JDBC| MySQL[(🗄️ MySQL DB)]
	Backend -->|Stripe API| Stripe[💳 Stripe]
	Backend -->|Flyway| Migration[(📦 Flyway Migrations)]
	Backend -->|Swagger| Docs[📚 Swagger UI]
	Backend -->|Docker| Compose[🐳 Docker Compose]

	%% Volumes outside Docker Network
	dbVolume[(🧊 mysql-volume)]
	Backend --> dbVolume
	MySQL --> dbVolume

	subgraph Docker_Network["🐳 Docker Network"]
		Backend
		MySQL
	end
```
# EcommerceAPI

A modern, container-ready backend for e-commerce platforms, built with Spring Boot, Stripe integration, and robust security. Designed for rapid deployment, scalability, and developer productivity.

---

## 🚀 Features
- **User Authentication & Authorization**: Secure JWT-based login, registration, and role management.
- **Product Catalog**: CRUD operations for products, categories, and inventory.
- **Cart & Checkout**: Add/remove items, manage carts, and seamless checkout flow.
- **Order Management**: Track orders, statuses, and history for users and admins.
- **Stripe Payments**: Integrated Stripe API for payment processing and webhook event handling.
- **RESTful API**: Clean, documented endpoints with OpenAPI/Swagger UI.
- **Exception Handling**: Global error handler for consistent API responses.
- **Database Migrations**: Flyway-managed schema and seed data for MySQL.
- **Containerization**: Dockerfile and Docker Compose for easy local and cloud deployment.
- **Environment Config**: All secrets and keys managed via environment variables.

---

## 🛠️ Tech Stack
- **Spring Boot 3.x**
- **Java 17+**
- **Maven**
- **MySQL**
- **Flyway**
- **Stripe API**
- **Docker & Docker Compose**
- **Lombok, MapStruct, Swagger/OpenAPI**

---

## ⚡ Quickstart

### 1. Clone & Configure
```bash
git clone https://github.com/Serghini04/EcommerceAPI.git
cd EcommerceAPI
```

### 2. Set Environment Variables
Create a `.env` file or set these in your deployment environment:
```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=ecommerce
DB_USER=root
DB_PASSWORD=yourpassword
STRIPE_SECRET_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...
```

### 3. Build & Run (Docker Compose)
```bash
make up   # or docker-compose up --build
```

### 4. Access API Docs
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧩 API Overview
- `/api/auth/*` — User registration, login, JWT
- `/api/products/*` — Product catalog
- `/api/cart/*` — Cart management
- `/api/checkout` — Checkout & Stripe session
- `/api/orders/*` — Order history & admin
- `/api/stripe/webhook` — Stripe event handler

---

## 🏗️ Database Migrations
- All schema and seed data managed via Flyway (`src/main/resources/db/migration`).
- On startup, migrations are applied automatically.

---

## 🛡️ Security
- JWT authentication for all protected endpoints
- Public access for Swagger/OpenAPI docs
- Stripe webhook signature validation

---

## 🌐 Deployment
- Ready for cloud: deploy with Docker anywhere (Azure, AWS, GCP, DigitalOcean)
- Environment variables for secrets
- Database can be external or containerized

---

## 🧪 Testing
- Unit and integration tests included (see `src/test/java`)
- Run tests: `make test` or `mvn test`

---

## 💡 Creative Highlights
- **Plug & Play Payments**: Stripe integration is modular—swap for other providers easily.
- **Developer Experience**: Makefile automates build, run, logs, and clean.
- **Extensible Architecture**: Add new features (discounts, reviews, analytics) with minimal friction.
- **Production Ready**: Secure, documented, and containerized from day one.

---

## 👨‍💻 Contributing
Pull requests and issues welcome! See `CONTRIBUTING.md` for guidelines.

---

## 📄 License
MIT

---

## ✨ Author
Made with ❤️ by [Serghini04](https://github.com/Serghini04)
