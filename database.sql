CREATE DATABASE quiz_app;
USE quiz_app;
CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  full_name VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_score INT DEFAULT 0,
  quizzes_taken INT DEFAULT 0
);
CREATE TABLE categories (
  category_id INT PRIMARY KEY AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  description TEXT
);
CREATE TABLE questions (
  question_id INT PRIMARY KEY AUTO_INCREMENT,
  category_id INT,
  question_text TEXT NOT NULL,
  option1 VARCHAR(255) NOT NULL,
  option2 VARCHAR(255) NOT NULL,
  option3 VARCHAR(255) NOT NULL,
  option4 VARCHAR(255) NOT NULL,
  correct_answer VARCHAR(255) NOT NULL,
  difficulty_level VARCHAR(20) DEFAULT 'MEDIUM',
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
CREATE TABLE quiz_results (
  result_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  category_id INT,
  score INT,
  total_questions INT,
  correct_answers INT,
  wrong_answers INT,
  percentage DECIMAL(5,2),
  time_taken INT,
  completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
CREATE TABLE leaderboard (
  leaderboard_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  total_score INT,
  rank_position INT,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
INSERT INTO categories (category_name, description) VALUES 
('Current Affairs India', 'Recent developments and news from India');
INSERT INTO questions (category_id, question_text, option1, option2, option3, option4, correct_answer) VALUES
(1, 'Who is the current President of India as of 2025?', 'Ram Nath Kovind', 'Droupadi Murmu', 'Pranab Mukherjee', 'APJ Abdul Kalam', 'Droupadi Murmu'),
(1, 'Which state launched the Sarvepalli Radhakrishnan Student Kits scheme in 2025?', 'Tamil Nadu', 'Karnataka', 'Andhra Pradesh', 'Kerala', 'Andhra Pradesh'),
(1, 'What is India''s rank in the Global Innovation Index 2024?', '45th', '39th', '52nd', '41st', '39th'),
(1, 'Which city will host India''s first AI City project?', 'Lucknow', 'Bangalore', 'Hyderabad', 'Pune', 'Lucknow'),
(1, 'How many medals did India win at the Paris 2024 Olympics?', '4', '5', '6', '8', '6'),
(1, 'Who won the 2024 World Chess Championship from India?', 'Viswanathan Anand', 'Praggnanandhaa', 'Gukesh D', 'Arjun Erigaisi', 'Gukesh D'),
(1, 'Which Indian achieved a historic double gold at the 2024 Chess Olympiad?', 'Men''s and Women''s teams', 'Individual boards', 'Rapid and Blitz', 'All categories', 'Men''s and Women''s teams'),
(1, 'What is the name of India''s new multi-state dairy cooperative launched in 2024?', 'Amul Federation', 'Sardar Patel Cooperative Dairy Federation', 'National Dairy Board', 'Bharat Milk Union', 'Sardar Patel Cooperative Dairy Federation'),
(1, 'Which scheme was launched by Rajasthan for poverty-free villages?', 'Garib Kalyan Yojana', 'Pandit Deendayal Upadhyay Garibi Mukt Gaon Yojana', 'Rural Development Mission', 'Village Upliftment Program', 'Pandit Deendayal Upadhyay Garibi Mukt Gaon Yojana'),
(1, 'India''s rank in the Global Unicorn Index 2025 is:', '2nd', '3rd', '4th', '5th', '3rd'),
(1, 'Which three private banks were allowed to handle state business in Odisha in 2024?', 'SBI, HDFC, ICICI', 'HDFC, ICICI, Axis', 'HDFC, SBI, Kotak', 'ICICI, Axis, Kotak', 'HDFC, ICICI, Axis'),
(1, 'What is the name of India''s new flood prediction platform launched in 2024?', 'FloodCast', 'C-Flood', 'WaterAlert', 'FloodWatch', 'C-Flood'),
(1, 'Which Indian port is now among the top 30 globally as per World Bank rankings?', 'Chennai Port', 'Mumbai Port', 'Visakhapatnam Port', 'Kochi Port', 'Visakhapatnam Port'),
(1, 'How much renewable energy capacity did India add in 2024?', '20 GW', '24.5 GW', '30 GW', '27.9 GW', '27.9 GW'),
(1, 'Which new criminal justice laws replaced British-era laws in 2024?', '2 laws', '3 laws', '4 laws', '5 laws', '3 laws');
