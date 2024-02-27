-- Insert a user
INSERT INTO users (first_name, last_name, email, created_date, modified_date)
VALUES ('John', 'Doe', 'john.doe@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert products
INSERT INTO products (code, name, price_eur, price_usd, description, created_date, modified_date)
VALUES
    ('PROD00000000001', 'iPhone 12', 800.00, 950.00, 'Apple iPhone 12 with 64GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000002', 'iPhone 12 Pro', 1000.00, 1200.00, 'Apple iPhone 12 Pro with 128GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000003', 'Samsung Galaxy S21', 850.00, 1000.00, 'Samsung Galaxy S21 with 128GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000004', 'Samsung Galaxy Note 20', 950.00, 1125.00, 'Samsung Galaxy Note 20 with 256GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000005', 'Huawei P40 Pro', 700.00, 830.00, 'Huawei P40 Pro with 256GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000006', 'Huawei Mate 40 Pro', 1100.00, 1300.00, 'Huawei Mate 40 Pro with 256GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000007', 'iPhone 13', 900.00, 1050.00, 'Apple iPhone 13 with 128GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('PROD00000000008', 'Samsung Galaxy S20 FE', 600.00, 700.00, 'Samsung Galaxy S20 FE with 128GB storage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert reviews
-- iPhone 12 Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (1, 1, 'The new A14 chip is incredibly fast. Multitasking is a breeze.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 1, 'Was expecting more from the battery life. Barely gets through the day.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 1, 'Camera quality is top-notch, especially in night mode.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 1, 'Lacks a high refresh rate screen which is a letdown at this price point.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 1, 'iOS is smooth as always, but the lack of customization is frustrating.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- iPhone 12 Pro Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (2, 1, 'The camera upgrade is worth it. Photos and videos look professional.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'Not a significant upgrade from the previous model except for the camera.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'Pricey for the features offered, considering the competition.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'Build quality is unmatched. Feels premium in the hand.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'Battery life doesn’t impress. Expected more from a Pro model.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Samsung Galaxy S21 Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (3, 1, 'The 120Hz display is a game-changer. Super smooth scrolling.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 'Feels like Samsung cut corners with the plastic back.', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 'Camera bump is quite noticeable and makes the phone wobble on flat surfaces.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 'Performance is stellar, thanks to the Snapdragon 888.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 'Battery life is just okay, not what I expected from a flagship.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Samsung Galaxy Note 20 Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (4, 1, 'S-Pen is more responsive than ever. Great for note-taking.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, 'Feels too expensive for what it offers, especially with a plastic back.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, 'Screen is gorgeous. Watching movies on this is a pleasure.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, 'Battery can barely last a full day with heavy use.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, 'The UI can be clunky at times, not as smooth as competitors.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Huawei P40 Pro Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (5, 1, 'Camera system is excellent. Best in class for photography.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 1, 'The absence of Google Play Services is a major drawback.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 1, 'Battery life is impressive. Easily lasts more than a day.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 1, 'The design and build quality are superb. Feels premium.', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 1, 'Limited app availability due to no Google services. Hard to recommend.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Huawei Mate 40 Pro Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (6, 1, 'The camera is fantastic, especially in low light conditions.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 1, 'Missing Google services is a deal-breaker for many.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 1, 'Screen and design are top-notch. One of the best-looking phones.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 1, 'Performance is great, but the software experience is lacking.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 1, 'If you can live without Google, it’s a great phone. Otherwise, it’s a tough sell.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- iPhone 13 Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (7, 1, 'Battery life is much improved over the iPhone 12. Lasts all day.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 1, 'A15 chip is overkill. Phone runs super smooth.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 1, 'Not much of an upgrade from the iPhone 12 if you have that.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 1, 'Cinematic mode in the camera is a game-changer for video.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 1, 'Still no USB-C port, which is disappointing in 2021.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Samsung Galaxy S20 FE Reviews
INSERT INTO reviews (product_id, reviewer_id, text, rating, created_date, modified_date)
VALUES
    (8, 1, 'Battery life is much improved over the iPhone 12. Lasts all day.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 1, 'A15 chip is overkill. Phone runs super smooth.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 1, 'Not much of an upgrade from the iPhone 12 if you have that.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 1, 'Cinematic mode in the camera is a game-changer for video.', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 1, 'Still no USB-C port, which is disappointing in 2021.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);