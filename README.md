# Setup Instructions for Frontend

## Overview

Our frontend application is built using **Vite**, **React**, and **JavaScript** for fast and efficient development.

## Getting Started

### 1. Navigate to the Frontend Directory

Ensure you are in the project root and move to the `frontend` folder:

```bash
cd frontend
```

### 2. Install Dependencies

Run the following command to install all required dependencies:

```bash
npm install
```

### 3. Start the Development Server

To start the frontend in development mode, run:

```bash
npm run dev
```

### 4. Access the Application

Once the server starts, you can access the application in your browser at:

```
http://localhost:5173
```

## Additional Notes

- Ensure you have **Node.js** and **npm** installed before running the setup.
- If you encounter any issues, try running `npx vite` directly.

# Setup Instructions for Backend

## Overview
Our backend application is built using **Spring Boot**.

### 1. Install Java 17 and set JAVA_HOME

1. Install Java 17
2. Set the path in environment variables as JAVA_HOME

If you have Eclipse installed you can also target the JRE in `\USER\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.11.v20240426-1830\jre`

### 2.  Navigate to the Backend Directory

```bash
cd backend
```

### 3. Compile & Install dependencies

Run:

```bash
mvn clean install
```
If you do not have Maven installed, run:

```bash
.\mvnw.cmd clean install
```

### 4. Run the application

Run:

```bash
mvn spring-boot:run
```

If you do not have Maven installed, run:

```bash
.\mvnw.cmd spring-boot:run
```

### 5. Access the application

Once the server starts, you can access the application in your browser at:
[http://localhost:8080](http://localhost:8080)


### 5. Access the documentation

Once the app is running, you can access the documentation for the endpoints in:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
