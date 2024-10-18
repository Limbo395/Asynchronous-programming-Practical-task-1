# Practical-task-1.-Multithreading

This program implements a simple library management system in Java that allows students to borrow and return books. It uses semaphore mechanisms to manage the available copies of books.

## Description

The program consists of a single class `LibrarySystem`, which includes the implementation of the following components:

- **Book**: A class that represents a book with its title and the number of available copies. It has methods for borrowing (`borrow`) and returning (`returnBook`) a book.
  
- **Library**: A class that represents the library. It allows opening and closing the library.

- **Student**: A class that implements the `Runnable` interface for students trying to borrow a book while the library is open.

## Running the Program

1. Compile the code:
   ```bash
   javac LibrarySystem.java

2.	Run the program:
	```bash
	java LibrarySystem



## How It Works

	1.	The program creates a library and a book with a specified number of available copies.
	2.	Several students are created, trying to borrow the same book.
	3.	The library remains closed for 3 seconds, after which it opens.
	4.	Students attempt to borrow the book. If the book is available, a student can hold it for 2 seconds before returning it.
	5.	The library closes 10 seconds after opening.

## Dependencies

	•	Java Development Kit (JDK) version 8 or higher.
