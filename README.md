# AI Helpdesk – Ticket Management System

A role-based helpdesk backend built with Java and Spring Boot, featuring JWT authentication 
and AI-powered ticket classification using Ollama LLM.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- MySQL
- Docker
- Ollama (Local LLM)

## Features
- User registration and login with JWT authentication
- Role-based access control (Admin, Agent, User)
- Ticket creation, assignment, escalation and tracking
- AI-powered automatic ticket classification and priority detection
- Smart agent assignment based on specialization and workload
- Dockerized for easy deployment

## API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /auth/register | Public | Register new user |
| POST | /auth/login | Public | Login and receive JWT token |

### Tickets
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /tickets | USER | Create new ticket (AI auto-classifies) |
| GET | /tickets/my | USER | View own tickets |
| GET | /tickets/all | ADMIN | View all tickets |
| GET | /tickets/assigned | AGENT | View assigned tickets |
| PUT | /tickets/{id}/assign/{agentId} | ADMIN | Assign ticket to agent |
| PUT | /tickets/{id}/status/{status} | ADMIN, AGENT | Update ticket status |

### Admin
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | /admin/agents | ADMIN | Create new agent |

## How It Works
1. User submits a ticket with title and description
2. Ollama LLM automatically classifies the category (LOGIN_ISSUE, PAYMENT, BUG, etc.)
3. Ollama LLM detects priority level (LOW, MEDIUM, HIGH, CRITICAL)
4. SmartAgentAllocator finds the best available agent based on specialization and workload
5. Ticket is assigned automatically and status updated to IN_PROGRESS

## Project Structure
src/main/java/com/anant/helpdesk/
├── auth/          → Registration, Login, JWT
├── ticket/        → Ticket CRUD, lifecycle management
├── admin/         → Agent management
├── ai/            → Ollama LLM integration, Smart allocation
├── config/        → Security, JWT filter, User details
├── common/        → Enums (Role, Status, Category, Priority)
└── user/          → User entity and repository
## Local Setup

### Prerequisites
- Java 17+
- MySQL
- Ollama (with qwen2:0.5b model)
- Docker (optional)

### Steps
1. Clone the repository
```bash
git clone https://github.com/AnantVarma/ai-helpdesk-backend.git
```

2. Create `.env` file in root (refer `.env.example`)
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret

3. Create MySQL database
```sql
CREATE DATABASE ai_helpdesk;
```

4. Run the application
```bash
./mvnw spring-boot:run
```

5. API runs at `http://localhost:8080`

## Architecture
Controller → Service → Repository → MySQL
↓
AI Classifier (Ollama)
↓
SmartAgentAllocator

## Author
**Perecherla Anant Varma**  
Java Backend Developer | CS Undergrad at Silicon University  
[LinkedIn](https://linkedin.com/in/anant-varma-615220330) | [GitHub](https://github.com/AnantVarma)
