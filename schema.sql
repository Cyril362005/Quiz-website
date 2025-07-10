-- --- schema.sql ---
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    high_score INT DEFAULT 0
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL
);

CREATE TABLE results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL,
    taken_on DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO questions(question, option_a, option_b, option_c, option_d, correct_option, category) VALUES
('Who is the current President of India?', 'Draupadi Murmu', 'Ram Nath Kovind', 'Pratibha Patil', 'Pranab Mukherjee', 'Draupadi Murmu', 'Current Affairs'),
('Which city hosted the 2023 G20 summit?', 'Mumbai', 'New Delhi', 'Bengaluru', 'Hyderabad', 'New Delhi', 'Current Affairs'),
('ISRO launched which moon mission in 2023?', 'Chandrayaan-2', 'Mangalyaan', 'Chandrayaan-3', 'Shukrayaan', 'Chandrayaan-3', 'Current Affairs'),
('Who won the 2023 IPL trophy?', 'Gujarat Titans', 'Mumbai Indians', 'Chennai Super Kings', 'Kolkata Knight Riders', 'Chennai Super Kings', 'Sports'),
('Which state launched the Mission LiFE initiative?', 'Gujarat', 'Uttar Pradesh', 'Kerala', 'Tamil Nadu', 'Gujarat', 'Environment'),
('India signed a trade deal in 2022 with which country called CEPA?', 'UAE', 'USA', 'Japan', 'France', 'UAE', 'Economy'),
('Which Indian became the world chess champion in 2023?', 'Viswanathan Anand', 'R Praggnanandhaa', 'Vidit Gujrathi', 'None', 'R Praggnanandhaa', 'Sports'),
('Name the Indian vaccine approved by WHO in 2021.', 'Covaxin', 'Covishield', 'Sputnik V', 'Pfizer', 'Covaxin', 'Health'),
('Which river was declared as a living entity by Uttarakhand HC?', 'Ganga', 'Yamuna', 'Godavari', 'Kaveri', 'Ganga', 'Environment'),
('The 2022 Nobel Peace Prize laureate from India is?', 'Kailash Satyarthi', 'Abhijit Banerjee', 'Har Gobind Khorana', 'None', 'None', 'Awards'),
('Which scheme replaced the Planning Commission in India?', 'Digital India', 'NITI Aayog', 'Make in India', 'Skill India', 'NITI Aayog', 'Government'),
('What is India''s rank in the Global Innovation Index 2022?', '10', '40', '48', '76', '40', 'Economy'),
('Which Indian city won UNESCO World Heritage status in 2021?', 'Jaipur', 'Dholavira', 'Varanasi', 'Ahmedabad', 'Dholavira', 'Culture'),
('The Agni-P missile is developed by which organisation?', 'DRDO', 'ISRO', 'HAL', 'BHEL', 'DRDO', 'Defense'),
('Which bank merged with HDFC in 2023?', 'ICICI Bank', 'SBI', 'HDFC Bank', 'Axis Bank', 'HDFC Bank', 'Economy');
-- --- END schema.sql ---
