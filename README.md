Simple Library Management System
--------------------------------

programming language - java, mysql
connecting to database via JDBC

Database name - library_management

+------------------------------+
| Tables_in_library_management |
+------------------------------+
| books                        |
| books_tracker                |
| fine_tracker                 |
| members                      |
| staffs                       |
+------------------------------+


**Books table** 
+------------------+-------------+------+-----+---------+----------------+
| Field            | Type        | Null | Key | Default | Extra          |
+------------------+-------------+------+-----+---------+----------------+
| book_id          | int         | NO   | PRI | NULL    | auto_increment |
| title            | varchar(50) | YES  |     | NULL    |                |
| author           | varchar(20) | YES  |     | NULL    |                |
| genre            | varchar(20) | YES  |     | NULL    |                |
| available_copies | int         | YES  |     | NULL    |                |
+------------------+-------------+------+-----+---------+----------------+


**Books Tracker Table**

+---------------+-------------+------+-----+---------+-------+
| Field         | Type        | Null | Key | Default | Extra |
+---------------+-------------+------+-----+---------+-------+
| email         | varchar(40) | YES  | MUL | NULL    |       |
| book_id       | int         | YES  | MUL | NULL    |       |
| borrowed_date | date        | YES  |     | NULL    |       |
| due_date      | date        | YES  |     | NULL    |       |
+---------------+-------------+------+-----+---------+-------+


**Fine Tracker Table**

+-------+-------------+------+-----+---------+-------+
| Field | Type        | Null | Key | Default | Extra |
+-------+-------------+------+-----+---------+-------+
| email | varchar(40) | YES  | MUL | NULL    |       |
| fine  | int         | YES  |     | NULL    |       |
+-------+-------------+------+-----+---------+-------+


**Members table**

+--------------+-------------+------+-----+---------+-------+
| Field        | Type        | Null | Key | Default | Extra |
+--------------+-------------+------+-----+---------+-------+
| email        | varchar(40) | NO   | PRI | NULL    |       |
| name         | varchar(20) | YES  |     | NULL    |       |
| Phone_number | varchar(10) | YES  |     | NULL    |       |
| password     | varchar(20) | YES  |     | NULL    |       |
+--------------+-------------+------+-----+---------+-------+


**Staffs Table**

+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| email    | varchar(40) | NO   | PRI | NULL    |       |
| name     | varchar(20) | YES  |     | NULL    |       |
| password | varchar(20) | YES  |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+



**INTERFACE, CLASSES AND METHODS**



DbHandler 

   protected static Connection connection;
   public static boolean createConnection() -> Creates connection with the dataBase
   public static void closeConnection() -> Terminates connection with the dataBase


Validation inherit DbHandler

   public static boolean login(email, password, staffOrNot) -> Validate and login the user 
   public static void signUp() -> Only the members can be sign Up assuming that Staff are assigned by higher place


interface Privileges

   void showPrivileges() -> Display the privileges for member or staff


Notifications inherits DbHandler

   private static String name;
   public static void displayWelcomeMsg(email, isStaff) -> Just a welcome Msg
   public static void displayThankYouMsg() -> Display good bye msg


User inherits DbHandler             //Common methods that can be used by any user either he is an member or staff

   public void showBookSearchOptions() -> Show all the search options(title, author, genre)
   public void showAllBooks() -> Show all the books
   public void searchByGenre() -> filter and display the book by genre
   public String selectGenre() -> Used to select the genre of the book
   public void searchByTitle() -> show the book based on the title given by the user
   public void searchByTitle(email) -> same with argument
   public void searchByAuthor() -> filter and display books based on author


Staff inherits User, implements Privileges 

   public void showPrivileges() -> overrided function
   public void addBooks() -> to add new books or to increase the available copies of the existing book
   public void removeBooks() -> Decreasing the available_copies or to completely remove an existing book
   public void showAllMembers()
   public void showNonAvailableBooks() -> display books whose available_copies is 0
   public void showMembersAndBorrowedBooks()


Member inherits User, implements Privileges

   private final String email;
   
   overrided methods
      public void showPrivileges()
      public void showAllBooks()
      public void searchByGenre() -> It will display title, author and genre whose available_copies > 0

   public int showBorrowedBooksCount()
   public void borrowBooks()
   public void returnBooks()
   public void payFine()


Fine inherits DbHandler

   private final String email;

   public static void showFineDetails()
   public void calculateFine(int bookId)
   public void updateFineInDb(int fine)
   public int checkIfAlreadyFined()
   public void updateExistingFine(int fine)
   public void createNewFineRecord(int fine)
   public void payFine()
   public void deleteFineAfterPayment()
   public void updateFineAfterPayment(int fine)

LibraryService 
   Main() function
