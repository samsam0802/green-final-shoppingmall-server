
-- 1. categories 테이블에 데이터 삽입
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('메이크업', 0, 0, NOW(), NOW(), FALSE, NULL),
('스킨케어', 0, 1, NOW(), NOW(), FALSE, NULL),
('마스크/팩', 0, 2, NOW(), NOW(), FALSE, NULL),
('선케어', 0, 3, NOW(), NOW(), FALSE, NULL),
('클렌징', 0, 4, NOW(), NOW(), FALSE, NULL),
('헤어케어', 0, 5, NOW(), NOW(), FALSE, NULL),
('바디케어', 0, 6, NOW(), NOW(), FALSE, NULL);

-- 메이크업 하위 카테고리
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('베이스', 1, 0, NOW(), NOW(), FALSE, 1),
('립', 1, 1, NOW(), NOW(), FALSE, 1),
('아이', 1, 2, NOW(), NOW(), FALSE, 1),
('치크/컨투어링', 1, 3, NOW(), NOW(), FALSE, 1);

-- 베이스 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('쿠션', 2, 0, NOW(), NOW(), FALSE, 8),
('파운데이션', 2, 1, NOW(), NOW(), FALSE, 8),
('컨실러', 2, 2, NOW(), NOW(), FALSE, 8),
('파우더', 2, 3, NOW(), NOW(), FALSE, 8),
('프라이머/베이스', 2, 4, NOW(), NOW(), FALSE, 8);

-- 립 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('립스틱', 2, 0, NOW(), NOW(), FALSE, 9),
('틴트', 2, 1, NOW(), NOW(), FALSE, 9),
('립케어', 2, 2, NOW(), NOW(), FALSE, 9);

-- 아이 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('아이라이너', 2, 0, NOW(), NOW(), FALSE, 10),
('아이섀도우/팔레트', 2, 1, NOW(), NOW(), FALSE, 10),
('마스카라', 2, 2, NOW(), NOW(), FALSE, 10),
('아이브로우', 2, 3, NOW(), NOW(), FALSE, 10);

-- 치크/컨투어링 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('블러셔', 2, 0, NOW(), NOW(), FALSE, 11),
('하이라이터/쉐딩', 2, 1, NOW(), NOW(), FALSE, 11);


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

-- 배송비 정책
INSERT INTO delivery_policies (name, policy_type, basic_delivery_fee, free_condition_amount, is_default, is_deleted, created_at, updated_at) VALUES
('유료정책', 'PAID', 3000, null, FALSE, FALSE, NOW(), NOW()),
('조건부 무료정책', 'CONDITIONAL_FREE', 3000, 50000, TRUE, FALSE, NOW(), NOW()),
('무료정책', 'FREE', 0, null, FALSE, FALSE, NOW(), NOW());

-- 3. products 테이블에 데이터 삽입 (임시)
INSERT INTO products (brand_id, category_id, delivery_policy_id, use_restock_noti, product_name, product_code, search_keywords, exposure_status, sale_status, description, is_cancelable, is_deleted, created_at, updated_at) VALUES
(1, 1, 1, TRUE, '설화수 자음수', 'SHS_001', '설화수 자음수 스킨 토너', 'EXPOSURE', 'ON_SALE', '설화수 대표 토너로 촉촉한 보습감을 선사합니다.', true, false, NOW(), NOW()),
(2, 2, 3, TRUE, '후 선크림', 'WHOO_001', '후 선크림 자외선차단', 'EXPOSURE', 'ON_SALE', '우아한 보송함을 선사하는 선크림', true, false, NOW(), NOW()),
(5, 3, 2, FALSE, '라네즈 크림 스킨', 'LANEIGE_001', '라네즈 스킨 토너', 'EXPOSURE', 'ON_SALE', '수분 가득한 스킨으로 피부를 촉촉하게', true, false, NOW(), NOW()),
(6, 4, 2, FALSE, '이니스프리 그린티 씨드 세럼', 'INNIS_001', '이니스프리 세럼 그린티', 'EXPOSURE', 'ON_SALE', '녹차씨드 함유로 피부 진정에 도움을 주는 세럼', true, false, NOW(), NOW()),
(7, 5, 2, FALSE, '더페이스샵 립앤아이 리무버', 'TFS_001', '더페이스샵 아이리무버 립메이크업', 'EXPOSURE', 'ON_SALE', '순하게 메이크업을 지워주는 리무버', true, false, NOW(), NOW());


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


-- review 테이블 더미 데이터
INSERT INTO reviews
(id, user_id, product_id, content, rating, is_visible, is_deleted, created_at, updated_at)
VALUES
(1, 1, 1, '리뷰1', 5, true, false, now(), now()),
(2, 2, 1, '리뷰2', 4, true, false, now(), now()),
(3, 3, 1, '리뷰3', 3, true, false, now(), now()),
(4, 4, 2, '리뷰4', 2, true, false, now(), now()),
(5, 5, 2, '리뷰5', 1, true, false, now(), now());