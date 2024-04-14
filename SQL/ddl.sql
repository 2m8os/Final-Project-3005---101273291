CREATE TABLE Members (
member_id 		SERIAL		PRIMARY KEY, 
fname 			VARCHAR(255)	NOT NULL,
lname 			VARCHAR(255)	NOT NULL,
email			VARCHAR(255)	NOT NULL,
target_weight		INT,
target_date 		DATE,
age			INT,
height			INT,
weight			INT,
amount_owed		INT			DEFAULT 10,
sex			VARCHAR(20)	NOT NULL
);

CREATE TABLE Trainers (
trainer_id	SERIAL		PRIMARY KEY,
fname		VARCHAR(255)	NOT NULL,
lname 		VARCHAR(255)	NOT NULL,
mon_status     VARCHAR(30)	NOT NULL,
tue_status     	VARCHAR(30)	NOT NULL,
wen_status     	VARCHAR(30)	NOT NULL,
thu_status     	VARCHAR(30)	NOT NULL,
fri_status    	VARCHAR(30)	NOT NULL
);

CREATE TABLE Rooms (
room_num	INT			PRIMARY KEY,
room_desc	VARCHAR(255) 	NOT NULL,
mon_status     VARCHAR(30)	NOT NULL,
tue_status     	VARCHAR(30)	NOT NULL,
wen_status     	VARCHAR(30)	NOT NULL,
thu_status     	VARCHAR(30)	NOT NULL,
fri_status    	VARCHAR(30)	NOT NULL
);

CREATE TABLE Classes (
class_id 		SERIAL		PRIMARY KEY,
class_desc 		VARCHAR(255) 	NOT NULL,
time_slot 		VARCHAR(30) 	NOT NULL,
room_num		INT,
trainer_id		INT,
spots_left 		INT,
FOREIGN KEY (room_num)  		REFERENCES Rooms(room_num),
FOREIGN KEY (trainer_id) 			REFERENCES Trainers(trainer_id)
);

CREATE TABLE Class_Members (
class_id 		INT,
member_id		INT,
FOREIGN KEY (class_id)  			REFERENCES Classes(class_id) ON DELETE CASCADE, 
FOREIGN KEY (member_id) 			REFERENCES Members(member_id) ON DELETE CASCADE,
PRIMARY KEY (class_id, member_id)
);

CREATE TABLE Equipment (
equipement_id 	SERIAL		PRIMARY KEY, 
e_desc			VARCHAR(255)	NOT NULL,
condition 		VARCHAR(255)	NOT NULL,
amount		INT			DEFAULT 1,
purchase_date 	DATE
);
