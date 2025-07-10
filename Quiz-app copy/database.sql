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
(1, 'Which new criminal justice laws replaced British-era laws in 2024?', '2 laws', '3 laws', '4 laws', '5 laws', '3 laws'),
(NULL, 'What is the capital of Canada?', 'Toronto', 'Ottawa', 'Vancouver', 'Montreal', 'Ottawa'),
(NULL, 'Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'Mars'),
(NULL, 'Who invented the telephone?', 'Thomas Edison', 'Nikola Tesla', 'Alexander Graham Bell', 'Albert Einstein', 'Alexander Graham Bell'),
(NULL, 'What is the chemical symbol for Gold?', 'G', 'Au', 'Ag', 'Go', 'Au'),
(NULL, 'Which is the largest mammal?', 'African Elephant', 'Blue Whale', 'Giraffe', 'Orca', 'Blue Whale'),
(NULL, 'What comes next in the series: 2, 4, 8, 16, ?', '24', '30', '32', '36', '32'),
(NULL, 'If 1=3, 2=3, 3=5, 4=4, 5=4, then 6=?', '3', '6', '5', '4', '3'),
(NULL, 'Which number is missing? 3, 6, 11, 18, ?, 38', '27', '28', '26', '25', '27'),
(NULL, 'Which shape has the least number of sides?', 'Triangle', 'Square', 'Pentagon', 'Hexagon', 'Triangle'),
(NULL, 'Find the odd one out: Apple, Banana, Mango, Carrot', 'Apple', 'Banana', 'Mango', 'Carrot', 'Carrot'),
(NULL, 'Which gas do plants absorb from the atmosphere?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Hydrogen', 'Carbon Dioxide'),
(NULL, 'Which organ in the body purifies blood?', 'Heart', 'Liver', 'Kidney', 'Lung', 'Kidney'),
(NULL, 'Which computer memory is volatile?', 'ROM', 'RAM', 'SSD', 'HDD', 'RAM'),
(NULL, 'Which programming language is known as the mother of all languages?', 'C', 'Java', 'Python', 'Ruby', 'C'),
(NULL, 'Which part of the cell contains DNA?', 'Mitochondria', 'Ribosome', 'Nucleus', 'Golgi Apparatus', 'Nucleus'),
(NULL, 'Which is the smallest continent by land area?', 'Europe', 'Antarctica', 'Australia', 'South America', 'Australia'),
(NULL, 'How many degrees are there in a right angle?', '90', '180', '45', '60', '90'),
(NULL, 'Which day is celebrated as World Environment Day?', 'June 5', 'April 22', 'March 21', 'October 2', 'June 5'),
(NULL, 'Which ocean is the deepest?', 'Atlantic', 'Indian', 'Pacific', 'Arctic', 'Pacific'),
(NULL, 'Which language has the most native speakers in the world?', 'English', 'Mandarin Chinese', 'Hindi', 'Spanish', 'Mandarin Chinese');
