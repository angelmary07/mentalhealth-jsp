create database if not exists mentalhealthdb;
use mentalhealthdb;

create table if not exists patients (
    id varchar(10) primary key,
    name varchar(50) not null,
    age int,
    gender varchar(15),
    mood varchar(15),
    severity varchar(15),
    symptoms varchar(100),
    status varchar(10)
);

-- optional sample data
insert ignore into patients values
('P001', 'Anjali Nair', 21, 'Female', 'Anxious', 'Medium', 'Anxiety, Insomnia', 'Active'),
('P002', 'Rohan Mehta', 24, 'Male', 'Calm', 'Low', 'None', 'Inactive'),
('P003', 'Sam Thomas', 19, 'Other', 'Stressed', 'High', 'Fatigue, Irritability', 'Active');
