import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Book {
    private String title;
    private Semaphore availableCopies;

    public Book(String title, int copies) {
        this.title = title;
        this.availableCopies = new Semaphore(copies);
    }

    public String getTitle() {
        return title;
    }

    // Спроба взяти книгу (зменшити кількість екземплярів)
    public boolean borrow() throws InterruptedException {
        if (availableCopies.tryAcquire(1, 1, TimeUnit.SECONDS)) {
            System.out.println(Thread.currentThread().getName() + " взяв книгу: " + title);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " не зміг взяти книгу: " + title + " (немає доступних екземплярів)");
            return false;
        }
    }

    // Повернення книги (збільшення кількості екземплярів)
    public void returnBook() {
        availableCopies.release();
        System.out.println(Thread.currentThread().getName() + " повернув книгу: " + title);
    }
}

class Library {
    private boolean isOpen;

    public Library() {
        this.isOpen = false;
    }

    // Відкрити бібліотеку
    public void open() {
        isOpen = true;
        System.out.println("Бібліотека відкрита.");
    }

    // Закрити бібліотеку
    public void close() {
        isOpen = false;
        System.out.println("Бібліотека закрита.");
    }

    public boolean isOpen() {
        return isOpen;
    }
}

class Student implements Runnable {
    private String name;
    private Book book;
    private Library library;

    public Student(String name, Book book, Library library) {
        this.name = name;
        this.book = book;
        this.library = library;
    }

    @Override
    public void run() {
        try {
            while (!library.isOpen()) {
                System.out.println(name + " чекає відкриття бібліотеки.");
                Thread.sleep(1000);
            }

            if (book.borrow()) {
                Thread.sleep(2000);  // Студент тримає книгу деякий час
                book.returnBook();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class LibrarySystem {
    public static void main(String[] args) throws InterruptedException {
        Library library = new Library();
        Book book1 = new Book("Java Programming", 3);

        // Створюємо кількох студентів, які хочуть взяти одну й ту ж книгу
        Student student1 = new Student("Максим", book1, library);
        Student student2 = new Student("Олена", book1, library);
        Student student3 = new Student("Іван", book1, library);
        Student student4 = new Student("Анна", book1, library);

        Thread t1 = new Thread(student1);
        Thread t2 = new Thread(student2);
        Thread t3 = new Thread(student3);
        Thread t4 = new Thread(student4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Відкриваємо бібліотеку через 3 секунди
        Thread.sleep(3000);
        library.open();

        // Закриваємо бібліотеку через 10 секунд
        Thread.sleep(10000);
        library.close();
    }
}