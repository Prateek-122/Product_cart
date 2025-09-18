# Product Cart

Product Cart is a full-stack e-commerce experience inspired by Flipkart. It delivers product discovery, cart and checkout flows, promotions, and role-based administration built with a Spring Boot + MySQL backend and a modern React + Tailwind front end.

## Architecture Overview

```
Product_cart/
├── backend/            # Spring Boot REST API with JWT security and MySQL persistence
│   ├── src/main/java/prateek
│   └── src/main/resources
├── frontend/           # React + Redux Toolkit + Tailwind single page application
└── README.md           # Project guide
```

### Key Features
- JWT based authentication with customer and admin roles, and signup/login endpoints.
- RESTful CRUD APIs for users, products, categories, inventory, carts, orders, payments, and promotions.
- Product search and filtering across keywords and categories.
- Mock payment gateway integration to simulate transaction success before persisting payment records.
- React storefront with responsive layout, protected routes, cart management, checkout, and authentication forms.
- MySQL schema and seed data for rapid local setup.

## Prerequisites
- **Java 17+** and **Maven 3.8+** for the backend.
- **Node.js 18+** and npm (or yarn/pnpm) for the frontend.
- **MySQL 8+** running locally with a database named `product_cart`.

## Database

The backend ships with a [`schema.sql`](backend/src/main/resources/schema.sql) that defines the full data model and a [`data.sql`](backend/src/main/resources/data.sql) file containing sample roles, users, categories, and products. Load them with the Spring Boot auto-initializer or run manually:

```sql
SOURCE backend/src/main/resources/schema.sql;
SOURCE backend/src/main/resources/data.sql;
```

Default credentials in the sample data use the password `password` (bcrypt-hashed). Update the connection string and credentials in [`backend/src/main/resources/application.properties`](backend/src/main/resources/application.properties) to match your environment before running.

## Backend (Spring Boot)

1. Update `spring.datasource.*` properties in `application.properties` with your MySQL username/password.
2. (Optional) Override `app.security.jwt.secret` and `app.security.jwt.expiration` for production-ready security.
3. From the `backend` directory run:

```bash
mvn spring-boot:run
```

> **Note:** Maven will download dependencies from Maven Central. If you are offline, resolve dependencies beforehand or configure a local mirror.

### Core Endpoints
- `POST /api/auth/signup` – Register a new account (default role `CUSTOMER`).
- `POST /api/auth/login` – Obtain a JWT for subsequent requests.
- `GET /api/products` / `GET /api/products/search` – Public catalog listing & search.
- `GET /api/categories` / `GET /api/promotions` – Public taxonomy and active promotions.
- `GET/POST/PUT/DELETE` endpoints under `/api/cart`, `/api/orders`, `/api/payments`, `/api/inventory`, `/api/admin/users`, etc. – Require authentication with proper roles.

Attach the JWT in the `Authorization: Bearer <token>` header when calling protected APIs.

## Frontend (React)

1. Navigate to the `frontend` directory and install dependencies:

```bash
npm install
```

2. Start the development server:

```bash
npm run dev
```

The React application proxies API calls to `http://localhost:8080` via Vite's development server configuration. Update `vite.config.js` if your backend runs elsewhere.

### Pages
- **Home** – Hero experience with featured items.
- **Product Listing** – Search and filter products by category and keyword.
- **Product Detail** – Item overview with add-to-cart actions.
- **Cart & Checkout** – Authenticated experiences to review cart contents and mock-pay for orders.
- **Login/Signup** – Toggleable auth form connected to the JWT flow.

Redux Toolkit centralizes authentication, product catalog, and cart state. Axios handles API calls with an interceptor that automatically attaches the JWT token stored in `localStorage`.

## Role-Based Access
- **Admin** users (see `admin@example.com` in the seed data) can manage users, categories, products, inventory, promotions, and orders.
- **Customer** users manage their cart, orders, and payments. Public catalog routes remain open for browsing before login.

## Mock Payment Gateway
The service layer includes a `MockPaymentGatewayClient` that generates transaction IDs for every payment attempt. Replace this component with a real PSP integration when moving towards production.

## Testing & Linting
- Backend: `mvn test` (includes Spring Boot test starter – add tests as needed).
- Frontend: `npm run build` to validate production builds and lint through Vite.

> This repository is intentionally dependency-free of proprietary services, making it open-source friendly. Extend it with monitoring, CI, and observability tooling as required.

## Contributing
1. Fork the repository.
2. Create feature branches against `main` (or submit patches via PRs if working locally).
3. Ensure code style compliance and add unit/integration tests when introducing new logic.
4. Submit a pull request describing your changes along with testing evidence.

Enjoy building with Product Cart! 🚀
