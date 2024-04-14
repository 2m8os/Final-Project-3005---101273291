INSERT INTO Members (fname,lname,email, weight, target_weight,target_date,age,height,sex,amount_owed)
VALUES 
('Greg', 'Thomson', 'gthom@email.com', 195, 180, '2024-07-07', 22, 180, 'Male',30),
('Makr', 'Thomson', 'mthom@email.com', 300, 220, '2024-07-07', 25, 200, 'Male',20),
('Lebron', 'James', 'cleveland@email.com', 220, 210, '2024-08-08', 39, 190, 'Male',20),
('Princess', 'Peach', 'shrooms@email.com', 150, 135, '2024-09-09', 99, 177, 'Female',20),
('BD', 'Barry', 'tripod@email.com', 300, 350, '2025-07-07', 22, 220, 'Male',20),
('Elizebeth', 'Windsor', 'EltonJohn@email.com', 140, 150, '2028-09-09', 103, 135, 'Female',20),
('Ava', 'Small', 'minia@email.com', 1, 2, '2024-09-09', 20, 1, 'Female',10);

INSERT INTO Equipment (e_desc, condition, amount, purchase_date)
VALUES 
('Pec Fly Machine', 'Usable', 1,  '2020-07-07'),
('Dumbbells, 50lbs', 'Usable/Usable/Usable', 3,  '2020-07-07'),('Ice Machine', 'Broken/Broken', 2,  '2020-07-07');


INSERT INTO Trainers (fname,lname,mon_status,tue_status,wen_status,thu_status,fri_status)
VALUES 
('Hill', 'Hill','Booked', 'Available','Available','Available','Available'),
('Dead', 'Beat','Unavailable', 'Unavailable','Unavailable','Unavailable','Unavailable'),
('Free', 'Man','Available', 'Available','Available','Available','Available'),
('Help', 'Me','Available', 'Booked','Available','Unavailable','Booked');

INSERT INTO Rooms (room_num, room_desc,mon_status,tue_status,wen_status,thu_status,fri_status)
VALUES 
(107, 'Workout Room 1','Booked', 'Available','Available','Available','Available'),
(1408, 'Workout Room 2','Unavailable', 'Unavailable','Unavailable','Unavailable','Unavailable'),
(113, 'Workout Room 3','Available', 'Booked','Available','Unavailable','Booked');
(111, 'Workout Room 4','Available', 'Available','Available','Available','Available');
(121, 'Workout Room 5','Available', 'Available','Available','Available','Available');

INSERT INTO Classes (class_desc, time_slot, room_num, trainer_id, spots_left)
VALUES 
('Pilates - Group','MONDAY', 107, 1,7),
('Zumba - Group','TUESDAY', 113,3,6),
('Climbing - Personal','FRIDAY', 113,3,1);

INSERT INTO Class_Members (class_id, member_id)
VALUES 
(1,1),
(1,2),
(1,3),
(2,4),
(2,5),
(2,6),
(2,1);