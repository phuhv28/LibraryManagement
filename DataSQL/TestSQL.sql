-- Tạo bảng admin
CREATE TABLE IF NOT EXISTS admin (
                                     Emp_id TEXT PRIMARY KEY,
                                     Emp_name TEXT,
                                     Position TEXT,
                                     Salary REAL,
                                     branch_no TEXT,
                                     Admin_name TEXT NOT NULL,
                                     Password TEXT NOT NULL
);

-- Chèn dữ liệu vào bảng admin
INSERT INTO admin VALUES ('E101', 'John Doe', 'Manager', 60000.00, 'B001', 'E101', '123456'),
                         ('E102', 'Jane Smith', 'Clerk', 45000.00, 'B001', 'E102', '123456'),
                         ('E103', 'Mike Johnson', 'Librarian', 55000.00, 'B001', 'E103', '123456'),
                         ('E104', 'Emily Davis', 'Assistant', 40000.00, 'B001', 'E104', '123456');

-- Tạo bảng books
CREATE TABLE IF NOT EXISTS books (
                                     ISBN TEXT PRIMARY KEY,
                                     Book_title TEXT,
                                     Category TEXT,
                                     Rental_Price REAL,
                                     Status TEXT,
                                     Author TEXT,
                                     Publisher TEXT,
                                     Total_quantity INTEGER,
                                     Available_quantity INTEGER
);

-- Chèn dữ liệu vào bảng books
INSERT INTO books VALUES ('978-0-09-957807-9', 'A Game of Thrones', 'Fantasy', 7.50, 'Yes', 'George R.R. Martin', 'Bantam', 10, 10),
                         ('978-0-14-044930-3', 'The Histories', 'History', 5.50, 'Yes', 'Herodotus', 'Penguin Classics', 8, 8);

-- Tạo các bảng còn lại tương tự
