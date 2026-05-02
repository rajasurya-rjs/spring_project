# Library Management — Spring Boot CRUD Assignment

A Spring Boot 3 / Java 21 web application that demonstrates **Create, Read,
and Update** operations for two related entities — **Author** and **Book** —
using JPA, JSP, JSTL and an in-memory H2 database.

---

## 1. Tech Stack

| Layer | Technology |
|---|---|
| Language / Runtime | Java 21 |
| Framework | Spring Boot 3.3.5 (Spring MVC + Spring Data JPA) |
| ORM | Hibernate (auto-configured by Spring Boot) |
| Database | H2 (in-memory) |
| View | JSP + JSTL (Jakarta) |
| Build | Maven |
| Testing | JUnit 5 + Mockito + AssertJ |

---

## 2. Entity Relationship Design

```
┌─────────────┐ 1        * ┌──────────┐
│   Author    │────────────│   Book   │
├─────────────┤             ├──────────┤
│ id (PK)     │             │ id (PK)  │
│ name        │             │ title    │
│ nationality │             │ isbn (U) │
│ birthYear   │             │ price    │
│             │             │ author_id│
└─────────────┘             │  (FK)    │
                            └──────────┘
```

- One **Author** has many **Books** (`@OneToMany`).
- Each **Book** belongs to exactly one **Author** (`@ManyToOne`, `optional=false`).
- `Book.isbn` carries a `unique=true` constraint — this is the constraint
  that drives the integrity-violation demo on the Add Book form.
- The schema is generated automatically by Hibernate from the JPA
  annotations (`spring.jpa.hibernate.ddl-auto=create-drop`).

---

## 3. Project Structure

```
src/main/java/com/example/library/
├── LibraryApplication.java        # Spring Boot entry point
├── entity/
│   ├── Author.java                # @Entity, @OneToMany books
│   └── Book.java                  # @Entity, @ManyToOne author, unique ISBN
├── repository/
│   ├── AuthorRepository.java      # extends JpaRepository
│   └── BookRepository.java        # custom INNER JOIN @Query
├── service/
│   ├── AuthorService.java
│   └── BookService.java
├── controller/
│   ├── HomeController.java        # /  -> redirect /books
│   ├── AuthorController.java
│   └── BookController.java
├── dto/
│   └── BookWithAuthor.java        # projection for inner-join result
└── exception/
    └── GlobalExceptionHandler.java   # @ControllerAdvice

src/main/resources/
├── application.properties         # H2 + JSP + JPA config
├── data.sql                       # 10 authors + 10 books seeded on startup
└── static/css/styles.css

src/main/webapp/WEB-INF/jsp/
├── common/header.jsp, footer.jsp
├── books/list.jsp, form.jsp
├── authors/list.jsp, form.jsp
└── error.jsp
```

---

## 4. How to Run

### Prerequisites
- Java 21
- Maven 3.9+

### Run the app
```bash
mvn spring-boot:run
```

> Use `mvn spring-boot:run` (NOT `java -jar`) — JSP files in
> `src/main/webapp/WEB-INF/jsp/` are not bundled into a Spring Boot fat-jar.

The app starts on **http://localhost:8085**.

### Run the tests
```bash
mvn test
```

---

## 5. Operations Implemented

### 5.1 Populate Database
Schema is created from JPA entities at startup. `src/main/resources/data.sql`
inserts **10 authors** and **10 books**. The setting
`spring.jpa.defer-datasource-initialization=true` ensures `data.sql`
runs **after** Hibernate creates the tables.

You can confirm the rows directly in the **H2 console**:
- URL: `http://localhost:8085/h2-console`
- JDBC URL: `jdbc:h2:mem:librarydb`
- User: `sa` (no password)
- Run `SELECT * FROM AUTHOR;` and `SELECT * FROM BOOK;`

### 5.2 Create
- `GET /books/new` → JSP form
- `POST /books` → controller method `BookController.create` saves a new book
- Bean Validation (`@NotBlank`, `@Positive`, etc.) is applied; field errors
  render via `<form:errors>`
- Same pattern for `/authors/new`

### 5.3 Read (with custom INNER JOIN query)
- `GET /books` lists every book together with its author's name
- The list is produced by `BookRepository.findAllBooksWithAuthor()`:

```java
@Query("""
    SELECT new com.example.library.dto.BookWithAuthor(
        b.id, b.title, b.isbn, b.price, a.name, a.nationality)
    FROM Book b INNER JOIN b.author a
    ORDER BY a.name, b.title
""")
List<BookWithAuthor> findAllBooksWithAuthor();
```

This is a JPQL `INNER JOIN` projected into the `BookWithAuthor` DTO via
JPQL's `SELECT new ...` constructor expression — exactly the inner join
the assignment requires.

### 5.4 Update
- `GET /books/{id}/edit` → form pre-populated from the existing row
- `POST /books/{id}` → controller method `BookController.update` overwrites
  the fields and saves
- Same pattern for `/authors/{id}/edit`

### 5.5 Exception Handling
`GlobalExceptionHandler` (annotated `@ControllerAdvice`) catches
`DataIntegrityViolationException` (e.g. duplicate ISBN, FK violation)
and renders `error.jsp` with a friendly message.

---

## 6. Tests

| Test | What it verifies |
|---|---|
| `BookRepositoryTest` (`@DataJpaTest`) | The custom `INNER JOIN` query joins correctly, drops authors with no books, and orders results by author name then title. |
| `BookServiceTest` (Mockito) | `create` attaches the right author and persists; `update` overwrites fields; missing ids throw `IllegalArgumentException`. |
| `AuthorServiceTest` (Mockito) | Service layer correctly delegates to the repository and propagates errors. |

Run with:
```bash
mvn test
```

---

## 7. PDF Submission Checklist

The assignment asks for a **PDF** documenting the project. Once the app is
running, capture screenshots of the following pages (the order below mirrors
the assignment requirements) and paste them into a Word/Docs file alongside
this README's content, then export to PDF.

| # | What to screenshot | URL |
|---|---|---|
| 1 | The list of 10 books — proves the **INNER JOIN** query works | `http://localhost:8085/books` |
| 2 | The list of 10 authors | `http://localhost:8085/authors` |
| 3 | The Add Book form (empty) | `http://localhost:8085/books/new` |
| 4 | The Add Book form filled in (a brand new ISBN) | same |
| 5 | The book list **after** the new book was added | `http://localhost:8085/books` |
| 6 | The error page when you submit an existing ISBN — proves **integrity-violation handling** | `http://localhost:8085/books/new` (use ISBN `978-0451524935`) |
| 7 | The Edit Book form pre-populated | `http://localhost:8085/books/1/edit` |
| 8 | The book list **after** the edit | `http://localhost:8085/books` |
| 9 | The H2 console showing both tables populated | `http://localhost:8085/h2-console` |
| 10 | Terminal output of `mvn test` (all tests passing) | — |

### Suggested PDF section order
1. Cover page (your name, course, date)
2. Tech stack (Section 1 of this README)
3. Entity-relationship design (Section 2)
4. Project structure (Section 3)
5. How to run (Section 4)
6. Operation-by-operation explanation **with screenshots** (Section 5)
7. Tests **with screenshot of the green build** (Section 6)
8. Challenges faced — see Section 8 below
9. GitHub URL of the project

---

## 8. Challenges Faced (sample text — feel free to edit)

- **JSP with Spring Boot 3:** Spring Boot 3 moved to the Jakarta EE
  namespace, so the JSTL imports use `jakarta.tags.core` instead of the older
  `http://java.sun.com/jsp/jstl/core` URI, and the JSTL implementation
  artifact had to be `org.glassfish.web:jakarta.servlet.jsp.jstl`. Also,
  JSPs cannot be packaged inside a Spring Boot fat-jar, so the app must be
  launched with `mvn spring-boot:run`.
- **`data.sql` running before the schema existed:** initially the seed
  inserts failed because Hibernate had not yet created the tables when
  Spring tried to execute `data.sql`. Setting
  `spring.jpa.defer-datasource-initialization=true` reorders the lifecycle
  so the schema is created first.
- **Inner-join projection:** rather than fetching `List<Book>` and then
  reading `book.getAuthor().getName()` (which would trigger N+1 lazy
  loads), we use a JPQL `SELECT new ...` constructor expression to project
  the join straight into a `BookWithAuthor` DTO. This is the textbook
  inner-join technique with JPA.
- **Integrity-violation UX:** Hibernate's raw exception is not user-friendly,
  so a `@ControllerAdvice` translates `DataIntegrityViolationException`
  into a clear `error.jsp` page that explains the problem (for example,
  duplicate ISBN).

---

## 9. GitHub URL

> Replace this line with your repository URL after you push.
>
> Example: `https://github.com/<your-username>/spring_project`
