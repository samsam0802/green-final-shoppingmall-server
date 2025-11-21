
-- 1. categories 테이블에 데이터 삽입
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (1, '메이크업', 0, 0, NOW(), NOW(), FALSE, NULL);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (2, '스킨케어', 0, 1, NOW(), NOW(), FALSE, NULL);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (3, '마스크/팩', 0, 2, NOW(), NOW(), FALSE, NULL);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (4, '선케어', 0, 3, NOW(), NOW(), FALSE, NULL);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (5, '클렌징', 0, 4, NOW(), NOW(), FALSE, NULL);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (6, '헤어케어', 0, 5, NOW(), NOW(), FALSE, NULL);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (7, '바디케어', 0, 6, NOW(), NOW(), FALSE, NULL);

-- 메이크업 하위 카테고리
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (8, '베이스', 1, 0, NOW(), NOW(), FALSE, 1);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (9, '립', 1, 1, NOW(), NOW(), FALSE, 1);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (10, '아이', 1, 2, NOW(), NOW(), FALSE, 1);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (11, '치크/컨투어링', 1, 3, NOW(), NOW(), FALSE, 1);

-- 베이스 하위
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (12, '쿠션', 2, 0, NOW(), NOW(), FALSE, 8);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (13, '파운데이션', 2, 1, NOW(), NOW(), FALSE, 8);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (14, '컨실러', 2, 2, NOW(), NOW(), FALSE, 8);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (15, '파우더', 2, 3, NOW(), NOW(), FALSE, 8);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (16, '프라이머/베이스', 2, 4, NOW(), NOW(), FALSE, 8);

-- 립 하위
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (17, '립스틱', 2, 0, NOW(), NOW(), FALSE, 9);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (18, '틴트', 2, 1, NOW(), NOW(), FALSE, 9);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (19, '립케어', 2, 2, NOW(), NOW(), FALSE, 9);

-- 아이 하위
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (20, '아이라이너', 2, 0, NOW(), NOW(), FALSE, 10);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (21, '아이섀도우/팔레트', 2, 1, NOW(), NOW(), FALSE, 10);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (22, '마스카라', 2, 2, NOW(), NOW(), FALSE, 10);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (23, '아이브로우', 2, 3, NOW(), NOW(), FALSE, 10);

-- 치크/컨투어링 하위
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (24, '블러셔', 2, 0, NOW(), NOW(), FALSE, 11);
INSERT INTO categories (id, name, depth, display_order, created_at, updated_at, is_deleted, parent_id)
VALUES (25, '하이라이터/쉐딩', 2, 1, NOW(), NOW(), FALSE, 11);


-- 2. brands 테이블에 데이터 삽입 (임시)
INSERT INTO brands (name, is_deleted, created_at, updated_at) VALUES
('설화수', FALSE, NOW(), NOW()),
('후', FALSE, NOW(), NOW()),
('雪花秀', FALSE, NOW(), NOW()),
('에스트라', FALSE, NOW(), NOW()),
('라네즈', FALSE, NOW(), NOW()),
('이니스프리', FALSE, NOW(), NOW()),
('더페이스샵', FALSE, NOW(), NOW()),
('미샤', FALSE, NOW(), NOW()),
('스킨푸드', FALSE, NOW(), NOW()),
('토니모리', FALSE, NOW(), NOW()),
('에뛰드하우스', FALSE, NOW(), NOW()),
('클리오', FALSE, NOW(), NOW()),
('헤라', FALSE, NOW(), NOW());

-- 3. products 테이블에 데이터 삽입 (임시)
INSERT INTO products (brand_id, category_id, product_name, product_code, search_keywords, exposure_status, sale_status, description, is_cancelable, is_deleted, created_at, updated_at) VALUES
(1, 1, '설화수 자음수', 'SHS_001', '설화수 자음수 스킨 토너', 'EXPOSURE', 'ON_SALE', '설화수 대표 토너로 촉촉한 보습감을 선사합니다.', true, false, NOW(), NOW()),
(2, 2, '후 선크림', 'WHOO_001', '후 선크림 자외선차단', 'EXPOSURE', 'ON_SALE', '우아한 보송함을 선사하는 선크림', true, false, NOW(), NOW()),
(5, 3, '라네즈 크림 스킨', 'LANEIGE_001', '라네즈 스킨 토너', 'EXPOSURE', 'ON_SALE', '수분 가득한 스킨으로 피부를 촉촉하게', true, false, NOW(), NOW()),
(6, 4, '이니스프리 그린티 씨드 세럼', 'INNIS_001', '이니스프리 세럼 그린티', 'EXPOSURE', 'ON_SALE', '녹차씨드 함유로 피부 진정에 도움을 주는 세럼', true, false, NOW(), NOW()),
(7, 5, '더페이스샵 립앤아이 리무버', 'TFS_001', '더페이스샵 아이리무버 립메이크업', 'EXPOSURE', 'ON_SALE', '순하게 메이크업을 지워주는 리무버', true, false, NOW(), NOW());


-- 민석 users 테이블에 데이터 삽입 (임시)
INSERT INTO users
(id, login_id, password_hash, name, phone_number, email, birth_date,
 postal_code, address, address_detail, email_agreement, sms_agreement,
 is_deleted, deleted_at, user_role, user_grade, created_at, updated_at)
VALUES
(1,'user1','pw1','사용자1','01011111111','user1@test.com','1996-01-01','11111','서울시 강남구','101호',true,true,false,NULL,'USER','BRONZE',NOW(),NOW()),
(2,'user2','pw2','사용자2','01022222222','user2@test.com','1996-02-02','22222','서울시 강북구','102호',true,true,false,NULL,'USER','BRONZE',NOW(),NOW()),
(3,'user3','pw3','사용자3','01033333333','user3@test.com','1996-03-03','33333','서울시 송파구','103호',true,true,false,NULL,'USER','BRONZE',NOW(),NOW()),
(4,'user4','pw4','사용자4','01044444444','user4@test.com','1996-04-04','44444','서울시 영등포구','104호',true,true,false,NULL,'USER','BRONZE',NOW(),NOW()),
(5,'user5','pw5','사용자5','01055555555','user5@test.com','1996-05-05','55555','서울시 마포구','105호',true,true,false,NULL,'USER','BRONZE',NOW(),NOW());

-- 민석 carts 테이블 데이터(임시)
INSERT INTO carts (id, user_id, created_at, updated_at)
VALUES
(1, 1, NOW(), NOW()),
(2, 2, NOW(), NOW()),
(3, 3, NOW(), NOW()),
(4, 4, NOW(), NOW()),
(5, 5, NOW(), NOW());

-- 민석 product_options 테이블 데이터(임시)
INSERT INTO product_options
(id, product_id, option_name, purchase_price, selling_price, current_stock,
 initial_stock, safety_stock, image_url, display_order, is_deleted,
 created_at, updated_at)
VALUES
(1, 1, '150ml', 3000, 5000, 100, 100, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt1', 0, false, NOW(), NOW()),
(2, 2, '120ml', 3500, 6500, 80, 80, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt2', 0, false, NOW(), NOW()),
(3, 3, '200ml', 4000, 7000, 120, 120, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt3', 0, false, NOW(), NOW()),
(4, 4, '100ml', 2500, 4500, 90, 90, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt4', 0, false, NOW(), NOW()),
(5, 5, '180ml', 5000, 9000, 70, 70, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt5', 0, false, NOW(), NOW());

-- 민석 product_main_images 테이블 데이터(임시)
INSERT INTO product_main_images
(product_id, image_url, display_order, image_type)
VALUES
(1, 'https://dummyimage.com/300x300/000/fff.png&text=PRD1', 0, 0),
(2, 'https://dummyimage.com/300x300/000/fff.png&text=PRD2', 0, 0),
(3, 'https://dummyimage.com/300x300/000/fff.png&text=PRD3', 0, 0),
(4, 'https://dummyimage.com/300x300/000/fff.png&text=PRD4', 0, 0),
(5, 'https://dummyimage.com/300x300/000/fff.png&text=PRD5', 0, 0);
