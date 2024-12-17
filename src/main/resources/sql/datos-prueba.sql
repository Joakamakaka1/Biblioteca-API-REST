INSERT INTO authors (id, name, nationality) VALUES (1, 'F. Scott Fitzgerald', 'American'), (2, 'Herman Melville', 'American');
INSERT INTO categories (id, name) VALUES (1, 'Fiction'), (2, 'Adventure');
INSERT INTO users (id, first_name, last_name, email, password, role) VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password123', 'CLIENT'), (2, 'Jane', 'Smith', 'jane.smith@example.com', 'securepassword', 'ADMIN');
INSERT INTO books (id, title, isbn, author_id, category_id) VALUES (1, 'The Great Gatsby', '9780743273565', 1, 1), (2, 'Moby Dick', '9781503280786', 2, 2)
INSERT INTO comments (id, content, user_id, book_id) VALUES (1, 'Amazing book, highly recommend!', 1, 1), (2, 'A bit too long for my taste, but still a classic.', 2, 2);
INSERT INTO loans (id, loan_date, return_date, user_id, book_id) VALUES (1, '2024-12-01', '2024-12-15', 1, 1), (2, '2024-12-02', '2024-12-16', 2, 2);
INSERT INTO reservations (id, reservation_date, status, user_id, book_id) VALUES (1, '2024-12-10', 'PENDING', 1, 1), (2, '2024-12-12', 'COMPLETED', 2, 2);