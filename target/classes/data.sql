-- ===== 10 Authors =====
INSERT INTO author (name, nationality, birth_year) VALUES ('George Orwell',         'British',  1903);
INSERT INTO author (name, nationality, birth_year) VALUES ('Jane Austen',           'British',  1775);
INSERT INTO author (name, nationality, birth_year) VALUES ('Mark Twain',            'American', 1835);
INSERT INTO author (name, nationality, birth_year) VALUES ('Leo Tolstoy',           'Russian',  1828);
INSERT INTO author (name, nationality, birth_year) VALUES ('Gabriel Garcia Marquez','Colombian',1927);
INSERT INTO author (name, nationality, birth_year) VALUES ('Haruki Murakami',       'Japanese', 1949);
INSERT INTO author (name, nationality, birth_year) VALUES ('Chinua Achebe',         'Nigerian', 1930);
INSERT INTO author (name, nationality, birth_year) VALUES ('Virginia Woolf',        'British',  1882);
INSERT INTO author (name, nationality, birth_year) VALUES ('Ernest Hemingway',      'American', 1899);
INSERT INTO author (name, nationality, birth_year) VALUES ('R. K. Narayan',         'Indian',   1906);

-- ===== 10 Books (some authors have multiple, some have none -- proves the INNER JOIN behaviour) =====
INSERT INTO book (title, isbn, price, author_id) VALUES ('1984',                       '978-0451524935',  9.99,  1);
INSERT INTO book (title, isbn, price, author_id) VALUES ('Animal Farm',                '978-0451526342',  7.50,  1);
INSERT INTO book (title, isbn, price, author_id) VALUES ('Pride and Prejudice',        '978-0141439518',  6.95,  2);
INSERT INTO book (title, isbn, price, author_id) VALUES ('The Adventures of Tom Sawyer','978-0143039563',  8.25,  3);
INSERT INTO book (title, isbn, price, author_id) VALUES ('War and Peace',              '978-0199232765', 14.50,  4);
INSERT INTO book (title, isbn, price, author_id) VALUES ('Anna Karenina',              '978-0143035008', 12.00,  4);
INSERT INTO book (title, isbn, price, author_id) VALUES ('One Hundred Years of Solitude','978-0060883287',11.99, 5);
INSERT INTO book (title, isbn, price, author_id) VALUES ('Norwegian Wood',             '978-0375704024', 10.99,  6);
INSERT INTO book (title, isbn, price, author_id) VALUES ('Things Fall Apart',          '978-0385474542',  9.50,  7);
INSERT INTO book (title, isbn, price, author_id) VALUES ('Malgudi Days',               '978-0140185430',  7.75, 10);
