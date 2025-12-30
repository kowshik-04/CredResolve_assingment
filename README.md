<div align="center">

# 💰 CredResolve
### _Enterprise-Grade Expense Sharing & Settlement System_

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

**A production-ready backend system for expense sharing with optimized settlement algorithms**

[Features](#-key-features) • [Architecture](#-system-architecture) • [API Docs](#-api-reference) • [Setup](#-quick-start)

</div>

---

## 🎯 Project Overview

**CredResolve** is a backend-first expense sharing platform engineered for real-world group expense scenarios—trips, roommates, team events. Unlike traditional expense trackers, this system prioritizes **financial correctness**, **debt optimization**, and **settlement minimization** over UI complexity.

### 🔥 What Makes This Special

This isn't just another CRUD app. CredResolve solves the fundamental problem every expense-sharing app faces:

> **"Who owes whom, and what's the minimum number of transactions to settle everything?"**

**Key Differentiators:**

✅ **Net Balance Architecture** – Computes consolidated user balances instead of tracking every micro-transaction  
✅ **Optimized Settlement Engine** – Guarantees minimal payment transactions using graph-based debt resolution  
✅ **Zero Floating-Point Errors** – All monetary calculations use integer paise (1 rupee = 100 paise)  
✅ **Strategy Pattern Implementation** – Pluggable expense split algorithms (Equal, Exact, Percentage)  
✅ **Group Isolation** – Financial independence between different expense groups  
✅ **API-First Design** – Frontend-agnostic REST architecture for multi-client support  

---

## 🏗️ What Was Built

### 1️⃣ **Complete Domain Modeling**
Designed and implemented core financial entities with proper JPA relationships:
- `User` – System participants with unique identity
- `Group` – Expense containers with member tracking
- `GroupMember` – Many-to-many relationship management
- `Expense` – Monetary transactions with split metadata
- `ExpenseSplit` – Individual share tracking per user
- `Balance` – Net position computation per user per group
- `Settlement` – Optimized payment instructions

### 2️⃣ **Group-Based Expense Isolation**
- Multi-group support with independent financial states
- Group membership management
- Cross-group balance segregation
- Unique group naming per creator

### 3️⃣ **Expense Split Strategy Framework**
Implemented **Strategy Design Pattern** for extensible split logic:

| Strategy | Description | Use Case |
|----------|-------------|----------|
| `EQUAL` | Even distribution among members | Standard shared expenses |
| `EXACT` | Custom amount per user | Unequal contributions |
| `PERCENTAGE` | Ratio-based allocation | Income-proportional splits |

### 4️⃣ **Intelligent Balance Computation**
- Real-time balance updates on expense addition
- **Positive balance** → User should receive money
- **Negative balance** → User owes money
- Paise-level precision for all calculations

### 5️⃣ **Advanced Settlement Algorithm**
The system implements a **greedy debt resolution algorithm**:

```
1. Aggregate net balances per user
2. Partition into creditors (positive) and debtors (negative)
3. Sort both by absolute amount (descending)
4. Greedily match largest debtor to largest creditor
5. Generate minimal settlement transactions
6. Persist optimized payment graph
```

**Result:** Users never pay the same person twice. No circular payments. Minimum transaction count.

### 6️⃣ **Production-Ready REST API**
- Comprehensive CRUD operations for all entities
- Defensive input validation
- Clear error responses
- Postman-ready documentation

---

## 🏛️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Client Layer                             │
│              (Postman / Web / Mobile)                       │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                 REST Controllers                            │
│   UserController │ GroupController │ ExpenseController      │
│   BalanceController │ SettlementController                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                 Service Layer                               │
│   Business Logic │ Validation │ Orchestration              │
│   UserService │ GroupService │ ExpenseService              │
│   BalanceService │ SettlementService                        │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│           Balance & Settlement Engine                       │
│   BalanceCalculator │ BalanceSimplifier                    │
│   Strategy Pattern: Equal │ Exact │ Percentage             │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                JPA Repositories                             │
│   UserRepository │ GroupRepository │ ExpenseRepository      │
│   BalanceRepository │ SettlementRepository                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                 PostgreSQL Database                         │
│   Relational Storage │ ACID Compliance │ Constraints       │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Language** | Java 17 | Modern LTS with records, pattern matching |
| **Framework** | Spring Boot 3.x | Production-grade application framework |
| **ORM** | Spring Data JPA + Hibernate | Database abstraction and entity management |
| **Database** | PostgreSQL 15 | ACID-compliant relational storage |
| **Containerization** | Docker + Docker Compose | Environment consistency and portability |
| **Testing** | Postman | API validation and demo workflows |

---

## 📁 Project Structure

```
expense-sharing/
│
├── src/main/java/com/expenses/expense_sharing/
│   ├── controller/          # REST API endpoints
│   │   ├── UserController.java
│   │   ├── GroupController.java
│   │   ├── ExpenseController.java
│   │   ├── BalanceController.java
│   │   └── SettlementController.java
│   │
│   ├── service/             # Business logic layer
│   │   ├── UserService.java
│   │   ├── GroupService.java
│   │   ├── ExpenseService.java
│   │   ├── BalanceService.java
│   │   └── SettlementService.java
│   │
│   ├── domain/              # JPA entities
│   │   ├── User.java
│   │   ├── Group.java
│   │   ├── GroupMember.java
│   │   ├── Expense.java
│   │   ├── ExpenseSplit.java
│   │   ├── Balance.java
│   │   └── Settlement.java
│   │
│   ├── repository/          # Data access layer
│   │   ├── UserRepository.java
│   │   ├── GroupRepository.java
│   │   ├── ExpenseRepository.java
│   │   └── BalanceRepository.java
│   │
│   ├── strategy/            # Split strategy implementations
│   │   ├── SplitStrategy.java
│   │   ├── EqualSplitStrategy.java
│   │   ├── ExactSplitStrategy.java
│   │   └── PercentageSplitStrategy.java
│   │
│   ├── balance/             # Settlement algorithms
│   │   ├── BalanceCalculator.java
│   │   └── BalanceSimplifier.java
│   │
│   └── config/              # Configuration
│       └── AppConfig.java
│
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
│
├── docker-compose.yml
├── Dockerfile
└── README.md
```

---

## 📚 Core Concepts

### Users
Individual participants in the system. Each user has:
- Unique ID (auto-generated)
- Name and email
- Membership in multiple groups

### Groups
Financial containers for shared expenses:
- Unique name per creator
- Multiple members
- Independent balance state
- Examples: "Goa Trip", "Apartment 4B", "Office Party"

### Expenses
Monetary transactions within a group:
- Description and amount (in paise)
- Paid by one user
- Split among members using a strategy
- Automatically updates all affected balances

### Balances
Net financial position per user per group:
- **Positive** → User should receive money
- **Negative** → User owes money
- Updated in real-time on expense creation

### Settlements
Optimized payment instructions:
- From debtor to creditor
- Amount to transfer
- Minimal transaction count
- Generated by settlement algorithm

---

## 🔧 Expense Split Strategies

### 1. EQUAL Split
Divides expense amount evenly among all group members.

**Example:** ₹6000 hotel bill for 4 people → ₹1500 per person

### 2. EXACT Split
Custom amount specified for each user.

**Example:** Alice pays ₹2000, Bob ₹3000, Charlie ₹1000

### 3. PERCENTAGE Split
Amount distributed by percentage weights.

**Example:** 40-30-30 split for ₹10000 → ₹4000, ₹3000, ₹3000

---

## 📡 API Reference

### 🔹 Create User
```http
POST /users
Content-Type: application/json

{
  "name": "Alice",
  "email": "alice@test.com"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Alice",
  "email": "alice@test.com"
}
```

---

### 🔹 Create Group
```http
POST /groups
Content-Type: application/json

{
  "name": "Goa Trip",
  "createdBy": 1,
  "memberIds": [1, 2, 3, 4]
}
```

**Response:**
```json
{
  "id": 5,
  "name": "Goa Trip",
  "createdBy": {
    "id": 1,
    "name": "Alice"
  }
}
```

---

### 🔹 Add Expense
```http
POST /expenses
Content-Type: application/json

{
  "description": "Hotel Booking",
  "amountPaise": 600000,
  "splitType": "EQUAL",
  "paidById": 1,
  "groupId": 5
}
```

**Explanation:**
- Alice paid ₹6000 (600000 paise)
- 4 group members
- Each person's share: ₹1500
- Others owe Alice ₹1500 each

---

### 🔹 Get Group Balances
```http
GET /balances/5
```

**Response:**
```json
[
  {
    "user": { "id": 2, "name": "Bob" },
    "amountPaise": -150000
  },
  {
    "user": { "id": 3, "name": "Charlie" },
    "amountPaise": -150000
  },
  {
    "user": { "id": 4, "name": "Dave" },
    "amountPaise": -150000
  },
  {
    "user": { "id": 1, "name": "Alice" },
    "amountPaise": 450000
  }
]
```

**Interpretation:**
- Negative balance → User owes money
- Positive balance → User should receive money

---

### 🔹 Get Optimized Settlements
```http
GET /settlements/5
```

**Response:**
```json
[
  {
    "fromUserId": 2,
    "fromUserName": "Bob",
    "toUserId": 1,
    "toUserName": "Alice",
    "amountPaise": 150000
  },
  {
    "fromUserId": 3,
    "fromUserName": "Charlie",
    "toUserId": 1,
    "toUserName": "Alice",
    "amountPaise": 150000
  },
  {
    "fromUserId": 4,
    "fromUserName": "Dave",
    "toUserId": 1,
    "toUserName": "Alice",
    "amountPaise": 150000
  }
]
```

**Key Benefits:**
✅ Each user pays exactly once  
✅ No redundant or circular transactions  
✅ Minimum number of total payments  

---

## 🎨 Settlement Algorithm Deep Dive

### Problem Statement
Given N users with various expenses, find the minimum number of transactions to settle all debts.

### Algorithmic Approach

```java
public List<Settlement> simplifyBalances(List<Balance> balances) {
    // 1. Extract net amounts
    List<UserBalance> netBalances = computeNetBalances(balances);
    
    // 2. Partition users
    List<UserBalance> creditors = getCreditors(netBalances);  // positive
    List<UserBalance> debtors = getDebtors(netBalances);      // negative
    
    // 3. Sort by absolute amount (descending)
    creditors.sort(comparingLong(UserBalance::getAmount).reversed());
    debtors.sort(comparingLong(b -> Math.abs(b.getAmount())).reversed());
    
    // 4. Greedy matching
    List<Settlement> settlements = new ArrayList<>();
    int i = 0, j = 0;
    
    while (i < creditors.size() && j < debtors.size()) {
        UserBalance creditor = creditors.get(i);
        UserBalance debtor = debtors.get(j);
        
        long settleAmount = Math.min(creditor.getAmount(), 
                                     Math.abs(debtor.getAmount()));
        
        settlements.add(new Settlement(debtor, creditor, settleAmount));
        
        creditor.subtract(settleAmount);
        debtor.add(settleAmount);
        
        if (creditor.getAmount() == 0) i++;
        if (debtor.getAmount() == 0) j++;
    }
    
    return settlements;
}
```

### Complexity Analysis
- **Time:** O(N log N) – dominated by sorting
- **Space:** O(N) – for creditor/debtor lists
- **Optimality:** Guarantees minimum transaction count

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17+ (if running without Docker)
- PostgreSQL 15+ (if running without Docker)

### Running with Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/yourusername/expense-sharing.git
cd expense-sharing

# Start all services
docker-compose up --build

# Backend will be available at
http://localhost:8080
```

### Running Locally

```bash
# Start PostgreSQL
docker-compose up -d postgres

# Run Spring Boot app
./mvnw spring-boot:run

# Or with Gradle
./gradlew bootRun
```

### Testing the API

1. Import Postman collection (if available)
2. Or use the API examples above
3. Start with creating users, then groups, then expenses

---

## 🧪 Testing Workflow

### Sample Test Scenario

```bash
# 1. Create 4 users
POST /users → Alice (id: 1)
POST /users → Bob (id: 2)
POST /users → Charlie (id: 3)
POST /users → Dave (id: 4)

# 2. Create a group
POST /groups
{
  "name": "Goa Trip",
  "createdBy": 1,
  "memberIds": [1, 2, 3, 4]
}
→ Group ID: 5

# 3. Add expenses
POST /expenses
{
  "description": "Hotel",
  "amountPaise": 600000,
  "splitType": "EQUAL",
  "paidById": 1,
  "groupId": 5
}

POST /expenses
{
  "description": "Dinner",
  "amountPaise": 400000,
  "splitType": "EQUAL",
  "paidById": 2,
  "groupId": 5
}

# 4. Check balances
GET /balances/5

# 5. Get settlement plan
GET /settlements/5
```

---

## 🎓 Key Learning Outcomes

This project demonstrates:

✅ **System Design** – Multi-layer architecture with clear separation of concerns  
✅ **Domain-Driven Design** – Rich domain models with business logic  
✅ **Algorithm Design** – Graph-based debt optimization  
✅ **Design Patterns** – Strategy pattern for extensibility  
✅ **Financial Engineering** – Handling money with precision  
✅ **REST API Design** – Clean, predictable API contracts  
✅ **Database Modeling** – Complex entity relationships with JPA  
✅ **DevOps** – Containerization and environment management  

---

## 🔮 Future Enhancements

### Phase 1 – Security & Auth
- [ ] JWT-based authentication
- [ ] Role-based access control
- [ ] OAuth2 integration

### Phase 2 – Advanced Features
- [ ] Multi-currency support with real-time conversion
- [ ] Recurring expenses
- [ ] Expense categories and tagging
- [ ] Bill splitting from images (OCR)

### Phase 3 – Analytics & Intelligence
- [ ] Expense analytics dashboard
- [ ] Spending predictions
- [ ] Group spending insights
- [ ] Export to CSV/PDF

### Phase 4 – Payment Integration
- [ ] UPI payment gateway
- [ ] Payment tracking
- [ ] Automatic settlement reminders
- [ ] In-app payment processing

### Phase 5 – Mobile & UI
- [ ] React/Next.js frontend
- [ ] React Native mobile app
- [ ] Real-time updates via WebSocket
- [ ] Push notifications

---

## 📝 Frontend Status

**Frontend is intentionally minimalistic.**

### Rationale
The primary focus of this project is:
- **Backend system design**
- **Financial correctness**
- **Algorithm optimization**
- **API architecture**

The APIs are fully functional and ready for integration with:
- Web frontends (React, Vue, Angular)
- Mobile apps (React Native, Flutter)
- CLI tools
- Third-party integrations

---

## 👨‍💻 Author

**Rama Naga Kowshik Mente**  
Backend Developer | System Design Enthusiast | Financial Logic Specialist

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/kowshik-mente/))
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Kowshik-04))
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:2200031960cseh@gmail.com)

---


<div align="center">

**If you found this project helpful, please consider giving it a ⭐**

Made with ☕ and 💻 by Kowshik

</div>
