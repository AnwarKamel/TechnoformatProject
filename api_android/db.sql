

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
   id int(11) primary key auto_increment,
   name varchar(50) not null,
   email varchar(100) not null unique,
   password varchar(80) not null,
   
   created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at datetime null
); 
