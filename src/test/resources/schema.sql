CREATE TABLE students
(
    id int AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL,
    kana_name varchar(50) NOT NULL,
    nick_name varchar(50),
    email varchar(50) NOT NULL,
    area varchar(50),
    age int,
    gender varchar(10),
    job varchar(30),
    remark varchar(100),
    is_deleted boolean

);

CREATE TABLE students_courses
(
    id int AUTO_INCREMENT PRIMARY KEY,
    students_id int NOT NULL,
    course_name varchar(50) NOT NULL,
    course_start_at TIMESTAMP,
    course_end_at TIMESTAMP,
    FOREIGN KEY(students_id) REFERENCES students(id)
);