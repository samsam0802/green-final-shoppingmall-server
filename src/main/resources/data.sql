
-- 1. categories 테이블에 데이터 삽입
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('스킨케어', 1, 0, NOW(), NOW(), FALSE, NULL),
('마스크팩', 1, 1, NOW(), NOW(), FALSE, NULL),
('클렌징', 1, 2, NOW(), NOW(), FALSE, NULL),
('선케어', 1, 3, NOW(), NOW(), FALSE, NULL),
('클렌징', 1, 4, NOW(), NOW(), FALSE, NULL),
('메이크업', 1, 5, NOW(), NOW(), FALSE, NULL),
('메이크업 툴', 1, 6, NOW(), NOW(), FALSE, NULL),
('헤어케어', 1, 7, NOW(), NOW(), FALSE, NULL),
('바디케어', 1, 8, NOW(), NOW(), FALSE, NULL),
('향수/디퓨저', 1, 9, NOW(), NOW(), FALSE, NULL);

-- depth:2 스킨케어 하위 카테고리
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('스킨/토너', 2, 0, NOW(), NOW(), FALSE, 1),
('에센스/세럼/앰플', 2, 1, NOW(), NOW(), FALSE, 1),
('크림/아이크림', 2, 2, NOW(), NOW(), FALSE, 1),
('로션', 2, 3, NOW(), NOW(), FALSE, 1),
('미스트/오일', 2, 4, NOW(), NOW(), FALSE, 1),
('스킨케어세트', 2, 5, NOW(), NOW(), FALSE, 1),
('스킨케어 디바이스', 2, 6, NOW(), NOW(), FALSE, 1);

-- depth:2 마스크팩 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('시트팩', 2, 0, NOW(), NOW(), FALSE, 2),
('패드', 2, 1, NOW(), NOW(), FALSE, 2),
('페이셜팩', 2, 2, NOW(), NOW(), FALSE, 2),
('코팩', 2, 3, NOW(), NOW(), FALSE, 2),
('패치', 2, 4, NOW(), NOW(), FALSE, 2);

-- depth:2 클렌징 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('클렌징폼/젤', 2, 0, NOW(), NOW(), FALSE, 3),
('오일/밤', 2, 1, NOW(), NOW(), FALSE, 3),
('워터/밀크', 2, 2, NOW(), NOW(), FALSE, 3),
('필링&스크럽', 2, 3, NOW(), NOW(), FALSE, 3),
('티슈/패드', 2, 4, NOW(), NOW(), FALSE, 3),
('립&아이리무버', 2, 5, NOW(), NOW(), FALSE, 3),
('클렌징 디바이스', 2, 6, NOW(), NOW(), FALSE, 3);

-- depth:2 선케어 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('선크림', 2, 0, NOW(), NOW(), FALSE, 4),
('선스틱', 2, 1, NOW(), NOW(), FALSE, 4),
('선쿠션', 2, 2, NOW(), NOW(), FALSE, 4),
('선스프레이/선패치', 2, 3, NOW(), NOW(), FALSE, 4),
('태닝/애프터선', 2, 4, NOW(), NOW(), FALSE, 4);

-- depth:2 메이크업 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('립메이크업', 2, 0, NOW(), NOW(), FALSE, 5),
('베이스메이크업', 2, 1, NOW(), NOW(), FALSE, 5),
('아이메이크업', 2, 2, NOW(), NOW(), FALSE, 5);

-- depth:2 메이크업 툴 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('메이크업 툴', 2, 0, NOW(), NOW(), FALSE, 6),
('아이래쉬 툴', 2, 1, NOW(), NOW(), FALSE, 6),
('페이스 툴', 2, 2, NOW(), NOW(), FALSE, 6),
('헤어/바디 툴', 2, 3, NOW(), NOW(), FALSE, 6),
('데일리 툴', 2, 4, NOW(), NOW(), FALSE, 6);

-- depth:2 헤어케어 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('샴푸/린스', 2, 0, NOW(), NOW(), FALSE, 7),
('트리트먼트/팩', 2, 1, NOW(), NOW(), FALSE, 7),
('두피앰플/토닉', 2, 2, NOW(), NOW(), FALSE, 7),
('헤어에센스', 2, 3, NOW(), NOW(), FALSE, 7),
('염색약/펌', 2, 4, NOW(), NOW(), FALSE, 7),
('헤어기기/브러시', 2, 5, NOW(), NOW(), FALSE, 7),
('스타일링', 2, 6, NOW(), NOW(), FALSE, 7);

-- depth:2 바디케어 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('바디로션/크림', 2, 0, NOW(), NOW(), FALSE, 8),
('오일/미스트', 2, 1, NOW(), NOW(), FALSE, 8),
('핸드케어', 2, 2, NOW(), NOW(), FALSE, 8),
('풋케어', 2, 3, NOW(), NOW(), FALSE, 8),
('샤워/입욕', 2, 4, NOW(), NOW(), FALSE, 8),
('제모/왁싱', 2, 5, NOW(), NOW(), FALSE, 8),
('데오드란트', 2, 6, NOW(), NOW(), FALSE, 8),
('베이비', 2, 7, NOW(), NOW(), FALSE, 8);

-- depth:2 향수/디퓨저 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('향수', 2, 0, NOW(), NOW(), FALSE, 9),
('미니/고체향수', 2, 1, NOW(), NOW(), FALSE, 9),
('홈프래그런스', 2, 2, NOW(), NOW(), FALSE, 9);

-- depth:3 스킨케어 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('스킨/토너', 3, 0, NOW(), NOW(), FALSE, 11),
('에센스/세럼/앰플', 3, 0, NOW(), NOW(), FALSE, 12),
('크림', 3, 0, NOW(), NOW(), FALSE, 13),
('아이크림', 3, 1, NOW(), NOW(), FALSE, 13),
('로션', 3, 0, NOW(), NOW(), FALSE, 14),
('올인원', 3, 1, NOW(), NOW(), FALSE, 14),
('미스트/픽서', 3, 0, NOW(), NOW(), FALSE, 15),
('페이스오일', 3, 1, NOW(), NOW(), FALSE, 15),
('스킨케어세트', 3, 0, NOW(), NOW(), FALSE, 16),
('스킨케어 디바이스', 3, 0, NOW(), NOW(), FALSE, 17);

-- depth:3 마스크팩 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('시트 마스크', 3, 0, NOW(), NOW(), FALSE, 18),
('겔 마스크', 3, 1, NOW(), NOW(), FALSE, 18),
('패드', 3, 0, NOW(), NOW(), FALSE, 19),
('워시오프팩', 3, 0, NOW(), NOW(), FALSE, 20),
('모델링팩', 3, 1, NOW(), NOW(), FALSE, 20),
('필오프팩', 3, 2, NOW(), NOW(), FALSE, 20),
('슬리핑/앰플팩', 3, 3, NOW(), NOW(), FALSE, 20),
('코팩', 3, 0, NOW(), NOW(), FALSE, 21),
('패치', 3, 0, NOW(), NOW(), FALSE, 22);

-- 2. brands 테이블에 데이터 삽입 (임시)
INSERT INTO brands (name, is_deleted, created_at, updated_at) VALUES
('바이오더마', FALSE, NOW(), NOW()),
('달바', FALSE, NOW(), NOW()),
('에스트라', FALSE, NOW(), NOW()),
('구달', FALSE, NOW(), NOW()),
('메디힐', FALSE, NOW(), NOW()),
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
(1, 61, 2, TRUE, '바이오더마 하이드라비오 토너 500ml 기획(+화장솜 20매 증정)', 'SHS_001', '바이오더마 스킨 토너', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, NOW(), NOW()),
(2, 62, 2, TRUE, '[NO.1 미스트세럼] 달바 퍼스트 스프레이 세럼 100ml 2개 기획', 'WHOO_001', '달바 세럼', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, NOW(), NOW()),
(3, 63, 2, FALSE, '에스트라 아토베리어365 크림 80ml 기획', 'LANEIGE_001', '에스트라 크림', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, NOW(), NOW()),
(4, 64, 2, FALSE, '구달 청귤 비타C 잡티케어 아이크림 30ml 1+1 기획', 'INNIS_001', '구달 아이크림', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, NOW(), NOW()),
(5, 71, 2, FALSE, '메디힐 에센셜 마스크팩 1매 고기능 7종 택1', 'TFS_001', '메디힐 마스크팩', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, NOW(), NOW()),
(2, 72, 2, FALSE, '[4세대발효콜라겐/4매입] 달바 비타 하이드로겔 마스크 4매입', 'TFS_001', '달바 겔마스크', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, NOW(), NOW());


-- product_main_images 데이터
INSERT INTO product_main_images (product_id, image_type, display_order, image_url) VALUES
(1, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0018/A00000018491610ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(1, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0018/A00000018491607ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(1, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0018/A00000018491608ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(2, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0023/A00000023272408ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(2, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0023/A00000023272409ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(2, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0023/A00000023272403ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(3, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022283315ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(3, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022283312ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(3, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022283314ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(4, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022977610ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(4, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022977608ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(4, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022977609ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762049ko.png?l=ko&QT=100&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762035ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762045ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 3, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762033ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 4, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762036ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 5, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762041ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 6, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762038ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(5, 'GALLERY', 7, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0021/A00000021762037ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5'),
(6, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022879929ko.png?l=ko&QT=100&SF=webp&sharpen=1x0.5'),
(6, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022879925ko.png?l=ko&QT=100&SF=webp&sharpen=1x0.5'),
(6, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022879903ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5');


-- product_options 테이블 데이터
INSERT INTO product_options
(product_id, option_name, purchase_price, selling_price,
 current_stock, initial_stock, safety_stock,
 image_url, display_order,
 is_deleted, created_at, updated_at)
VALUES
(1, '바이오더마 토너 500ml 기획', 10000, 30000, 80, 100, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt1', 0, false, NOW(), NOW()),
(2, '달바 퍼스트 스프레이 세럼 100ml 2개 기획', 20000, 59800, 80, 100, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt2', 0, false, NOW(), NOW()),
(3, '에스트라 아토베리어365 크림 80ml 기획', 15000, 33000, 120, 120, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt3', 0, false, NOW(), NOW()),
(4, '구달 청귤 비타C 잡티케어 아이크림 30ml', 15000, 24000, 90, 90, 10, 'https://dummyimage.com/300x300/000/fff.png&text=Opt4', 0, false, NOW(), NOW()),
(5, '티트리 진정수분 1매', 500, 1000, 80, 100, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/2095773322051515522.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW()),
(5, '콜라겐 코어탄력 1매', 500, 1000, 80, 100, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/4292023589387008731.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 1, false, NOW(), NOW()),
(5, '비타민씨 잡티토닝 1매', 500, 1000, 120, 120, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/6333511698168958736.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 2, false, NOW(), NOW()),
(5, '세라마이드 보습장벽 1매', 500, 1000, 90, 90, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/5177396663963625881.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 3, false, NOW(), NOW()),
(5, '마데카소사이드 흔적리페어 1매', 800, 2000, 70, 70, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/1550813712772282449.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 4, false, NOW(), NOW()),
(5, '로제 PDRN 모공결광 1매', 800, 2000, 90, 90, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/3/927811821371366876.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 5, false, NOW(), NOW()),
(5, '히알루론산 고밀도 수분 1매', 800, 2000, 70, 70, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/2/7886183073217244310.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 6, false, NOW(), NOW()),
(6, '달바 비타 하이드로겔 마스크 4매입', 14000, 28000, 70, 70, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022879929ko.png?l=ko&QT=100&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW());


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


-- 민석 user_addresses 테이블 데이터
INSERT INTO user_addresses
(id, user_id, address_name, receiver_name, receiver_phone, postal_code,
 street_address, detailed_address, is_default, delivery_request,
 created_at, updated_at)
VALUES
-- 기본 주소
(1, 1, '집', '강민석', '01012345678', '06236',
 '서울특별시 강남구 테헤란로 123', '101동 1203호',
 TRUE, '문 앞에 놓아주세요.', NOW(), NOW()),

-- 일반 주소
(2, 1, '회사', '강민석', '01012345678', '04524',
 '서울특별시 중구 세종대로 110', '20층',
 FALSE, '경비실에 맡겨주세요.', NOW(), NOW()),

(3, 2, '집', '홍길동', '01098765432', '07941',
 '서울특별시 양천구 목동로 210', '302동 502호',
 TRUE, NULL, NOW(), NOW()),

(4, 2, '부모님 댁', '홍길동', '01098765432', '16888',
 '경기도 성남시 분당구 정자동 123', NULL,
 FALSE, '도착 전 연락 부탁드립니다.', NOW(), NOW()),

(5, 1, '기타 배송지', '강민석', '01012345678', '12345',
 '서울특별시 마포구 월드컵북로 400', '3층',
 FALSE, NULL, NOW(), NOW());

-- review 테이블 더미 데이터
INSERT INTO reviews
(id, user_id, product_id, content, rating, is_visible, is_deleted, created_at, updated_at)
VALUES
(1, 1, 1, '리뷰1', 5, true, false, '2025-11-20 10:00:00', '2025-11-20 10:00:00'),
(2, 1, 2, '리뷰2', 4, true, false, '2025-11-20 11:30:00', '2025-11-20 11:30:00'),
(3, 2, 3, '리뷰3', 3, true, false, '2025-11-22 09:15:00', '2025-11-22 09:15:00'),
(4, 2, 1, '리뷰4', 5, true, false, '2025-11-23 14:45:00', '2025-11-23 14:45:00'),
(5, 3, 1, '리뷰5', 3, true, false, '2025-11-26 16:20:00', '2025-11-26 16:20:00');