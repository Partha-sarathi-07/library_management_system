# Simple Library Management System

### Programming Language
- **Java**
- **MySQL**

### Database Connectivity
- **JDBC**

### Database Name
`library_management`

### Tables in `library_management`
- `books`
- `books_tracker`
- `fine_tracker`
- `members`
- `staffs`

---

## **Tables Structure**

### Books Table
| Field            | Type        | Null | Key | Default | Extra          |
|------------------|-------------|------|-----|---------|----------------|
| book_id          | int         | NO   | PRI | NULL    | auto_increment |
| title            | varchar(50) | YES  |     | NULL    |                |
| author           | varchar(20) | YES  |     | NULL    |                |
| genre            | varchar(20) | YES  |     | NULL    |                |
| available_copies | int         | YES  |     | NULL    |                |

---

### Books Tracker Table
| Field         | Type        | Null | Key | Default | Extra |
|---------------|-------------|------|-----|---------|-------|
| email         | varchar(40) | YES  | MUL | NULL    |       |
| book_id       | int         | YES  | MUL | NULL    |       |
| borrowed_date | date        | YES  |     | NULL    |       |
| due_date      | date        | YES  |     | NULL    |       |

---

### Fine Tracker Table
| Field | Type        | Null | Key | Default | Extra |
|-------|-------------|------|-----|---------|-------|
| email | varchar(40) | YES  | MUL | NULL    |       |
| fine  | int         | YES  |     | NULL    |       |

---

### Members Table
| Field        | Type        | Null | Key | Default | Extra |
|--------------|-------------|------|-----|---------|-------|
| email        | varchar(40) | NO   | PRI | NULL    |       |
| name         | varchar(20) | YES  |     | NULL    |       |
| Phone_number | varchar(10) | YES  |     | NULL    |       |
| password     | varchar(20) | YES  |     | NULL    |       |

---

### Staffs Table
| Field    | Type        | Null | Key | Default | Extra |
|----------|-------------|------|-----|---------|-------|
| email    | varchar(40) | NO   | PRI | NULL    |       |
| name     | varchar(20) | YES  |     | NULL    |       |
| password | varchar(20) | YES  |     | NULL    |       |

---

## **Classes and Interfaces**

### DbHandler
- **Variables**
  - `protected static Connection connection;`
- **Methods**
  - `public static boolean createConnection()` - Creates connection with the database.
  - `public static void closeConnection()` - Terminates connection with the database.

---

### Validation (inherits `DbHandler`)
- **Methods**
  - `public static boolean login(email, password, staffOrNot)` - Validates and logs in the user.
  - `public static void signUp()` - Allows only members to sign up, assuming staff are assigned separately.

---

### Interface: Privileges
- **Methods**
  - `void showPrivileges()` - Displays privileges for members or staff.

---

### Notifications (inherits `DbHandler`)
- **Variables**
  - `private static String name;`
- **Methods**
  - `public static void displayWelcomeMsg(email, isStaff)` - Displays a welcome message.
  - `public static void displayThankYouMsg()` - Displays a goodbye message.

---

### User (inherits `DbHandler`)
- **Methods**
  - `public void showBookSearchOptions()` - Shows search options (title, author, genre).
  - `public void showAllBooks()` - Displays all books.
  - `public void searchByGenre()` - Filters and displays books by genre.
  - `public String selectGenre()` - Used to select the genre of a book.
  - `public void searchByTitle()` - Displays books based on the title given.
  - `public void searchByTitle(email)` - Same as above but includes email argument.
  - `public void searchByAuthor()` - Filters and displays books by author.

---

### Staff (inherits `User`, implements `Privileges`)
- **Methods**
  - `public void showPrivileges()` - Overridden function.
  - `public void addBooks()` - Adds new books or increases the available copies of existing books.
  - `public void removeBooks()` - Decreases available copies or removes books entirely.
  - `public void showAllMembers()` - Displays all members.
  - `public void showNonAvailableBooks()` - Displays books with `available_copies = 0`.
  - `public void showMembersAndBorrowedBooks()` - Displays members and their borrowed books.
  - `public void showBookNotReturnedMembers()`
  - `public void showFinedMembers()`

---

### Member (inherits `User`, implements `Privileges`)
- **Variables**
  - `private final String email;`
- **Methods**
  - Overridden methods:
    - `public void showPrivileges()`
    - `public void showAllBooks()`
    - `public void searchByGenre()` - Displays books with `available_copies > 0`.
  - `public int showBorrowedBooksCount()` - Shows the count of borrowed books.
  - `public void borrowBooks()` - Allows borrowing books.
  - `public void returnBooks()` - Handles book returns.
  - `public void payFine()` - Facilitates fine payments.

---

### Fine (inherits `DbHandler`)
- **Variables**
  - `private final String email;`
- **Methods**
  - `public static void showFineDetails()` - Displays fine details.
  - `public void calculateFine(int bookId)` - Calculates fine for a book.
  - `public void updateFineInDb(int fine)` - Updates fine in the database.
  - `public int checkIfAlreadyFined()` - Checks if the user is already fined.
  - `public void updateExistingFine(int fine)` - Updates an existing fine.
  - `public void createNewFineRecord(int fine)` - Creates a new fine record.
  - `public void payFine()` - Processes fine payment.
  - `public void deleteFineAfterPayment()` - Deletes fine record after payment.
  - `public void updateFineAfterPayment(int fine)` - Updates fine after partial payment.

---

### LibraryService
- **Main Method**
  - The entry point of the application.
