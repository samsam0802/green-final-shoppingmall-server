
-- 1. categories 테이블에 데이터 삽입
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('스킨케어', 1, 0, NOW(), NOW(), FALSE, NULL),
('마스크팩', 1, 1, NOW(), NOW(), FALSE, NULL),
('클렌징', 1, 2, NOW(), NOW(), FALSE, NULL),
('선케어', 1, 3, NOW(), NOW(), FALSE, NULL),
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
('스킨/토너', 3, 0, NOW(), NOW(), FALSE, 10),
('에센스/세럼/앰플', 3, 0, NOW(), NOW(), FALSE, 11),
('크림', 3, 0, NOW(), NOW(), FALSE, 12),
('아이크림', 3, 1, NOW(), NOW(), FALSE, 12),
('로션', 3, 0, NOW(), NOW(), FALSE, 13),
('올인원', 3, 1, NOW(), NOW(), FALSE, 13),
('미스트/픽서', 3, 0, NOW(), NOW(), FALSE, 14),
('페이스오일', 3, 1, NOW(), NOW(), FALSE, 14),
('스킨케어세트', 3, 0, NOW(), NOW(), FALSE, 15),
('스킨케어 디바이스', 3, 0, NOW(), NOW(), FALSE, 16);

-- depth:3 마스크팩 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('시트 마스크', 3, 0, NOW(), NOW(), FALSE, 17),
('겔 마스크', 3, 1, NOW(), NOW(), FALSE, 17),
('패드', 3, 0, NOW(), NOW(), FALSE, 18),
('워시오프팩', 3, 0, NOW(), NOW(), FALSE, 19),
('모델링팩', 3, 1, NOW(), NOW(), FALSE, 19),
('필오프팩', 3, 2, NOW(), NOW(), FALSE, 19),
('슬리핑/앰플팩', 3, 3, NOW(), NOW(), FALSE, 19),
('코팩', 3, 0, NOW(), NOW(), FALSE, 20),
('패치', 3, 0, NOW(), NOW(), FALSE, 21);

---- depth:3 클렌징 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('클렌징폼/젤', 3, 0, NOW(), NOW(), FALSE, 22),
('팩클렌저', 3, 1, NOW(), NOW(), FALSE, 22),
('클렌징 비누', 3, 2, NOW(), NOW(), FALSE, 22),
('클렌징오일', 3, 0, NOW(), NOW(), FALSE, 23),
('클렌징밤', 3, 1, NOW(), NOW(), FALSE, 23),
('클렌징워터', 3, 2, NOW(), NOW(), FALSE, 24),
('클렌징밀크/크림', 3, 3, NOW(), NOW(), FALSE, 24),
('페이셜스크럽', 3, 0, NOW(), NOW(), FALSE, 25),
('피지클리너', 3, 1, NOW(), NOW(), FALSE, 25),
('파우더워시', 3, 2, NOW(), NOW(), FALSE, 25),
('클렌징티슈/패드', 3, 0, NOW(), NOW(), FALSE, 26),
('립&아이리무버', 3, 0, NOW(), NOW(), FALSE, 27),
('클렌징 디바이스', 3, 0, NOW(), NOW(), FALSE, 28);

---- depth:3 선케어 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('선크림', 3, 0, NOW(), NOW(), FALSE, 29),
('선스틱', 3, 0, NOW(), NOW(), FALSE, 30),
('선쿠션', 3, 0, NOW(), NOW(), FALSE, 31),
('선파우더', 3, 1, NOW(), NOW(), FALSE, 31),
('선스프레이', 3, 0, NOW(), NOW(), FALSE, 32),
('선패치', 3, 1, NOW(), NOW(), FALSE, 32),
('태닝', 3, 0, NOW(), NOW(), FALSE, 33),
('애프터선', 3, 1, NOW(), NOW(), FALSE, 33);

---- depth:3 메이크업 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('립틴트', 3, 0, NOW(), NOW(), FALSE, 34),
('립스틱', 3, 1, NOW(), NOW(), FALSE, 34),
('립라이너', 3, 2, NOW(), NOW(), FALSE, 34),
('립케어', 3, 3, NOW(), NOW(), FALSE, 34),
('컬러립밤', 3, 4, NOW(), NOW(), FALSE, 34),
('립글로스', 3, 5, NOW(), NOW(), FALSE, 34),
('쿠션', 3, 0, NOW(), NOW(), FALSE, 35),
('파운데이션', 3, 1, NOW(), NOW(), FALSE, 35),
('블러셔', 3, 2, NOW(), NOW(), FALSE, 35),
('립케어', 3, 3, NOW(), NOW(), FALSE, 35),
('파우더/팩트', 3, 4, NOW(), NOW(), FALSE, 35),
('컨실러', 3, 5, NOW(), NOW(), FALSE, 35),
('프라이머/베이스', 3, 6, NOW(), NOW(), FALSE, 35),
('쉐이딩', 3, 7, NOW(), NOW(), FALSE, 35),
('하이라이터', 3, 8, NOW(), NOW(), FALSE, 35),
('메이크업 픽서', 3, 9, NOW(), NOW(), FALSE, 35),
('BB/CC', 3, 10, NOW(), NOW(), FALSE, 35),
('아이라이너', 3, 7, NOW(), NOW(), FALSE, 36),
('마스카라', 3, 8, NOW(), NOW(), FALSE, 36),
('아이브로우', 3, 9, NOW(), NOW(), FALSE, 36),
('아이섀도우', 3, 10, NOW(), NOW(), FALSE, 36),
('아이래쉬 케어', 3, 3, NOW(), NOW(), FALSE, 36),
('아이 픽서', 3, 0, NOW(), NOW(), FALSE, 36);

---- depth:3 헤어케어 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('샴푸', 3, 0, NOW(), NOW(), FALSE, 42),
('린스/컨디셔너', 3, 1, NOW(), NOW(), FALSE, 42),
('드라이샴푸', 3, 2, NOW(), NOW(), FALSE, 42),
('스케일러', 3, 3, NOW(), NOW(), FALSE, 42),
('헤어 트리트먼트', 3, 0, NOW(), NOW(), FALSE, 43),
('노워시 트리트먼트', 3, 1, NOW(), NOW(), FALSE, 43),
('헤어토닉/두피토닉', 3, 0, NOW(), NOW(), FALSE, 44),
('헤어세럼', 3, 0, NOW(), NOW(), FALSE, 45),
('헤어오일', 3, 1, NOW(), NOW(), FALSE, 45),
('컬러염색/탈색', 3, 0, NOW(), NOW(), FALSE, 46),
('새치염색', 3, 1, NOW(), NOW(), FALSE, 46),
('헤어메이크업', 3, 2, NOW(), NOW(), FALSE, 46),
('파마', 3, 3, NOW(), NOW(), FALSE, 46),
('컬크림/컬링에센스', 3, 0, NOW(), NOW(), FALSE, 48),
('왁스/젤/무스/토닉', 3, 1, NOW(), NOW(), FALSE, 48),
('스프레이/픽서', 3, 2, NOW(), NOW(), FALSE, 48);


---- depth:3 바디케어 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('바디로션', 3, 0, NOW(), NOW(), FALSE, 49),
('바디크림', 3, 1, NOW(), NOW(), FALSE, 49),
('바디미스트', 3, 0, NOW(), NOW(), FALSE, 50),
('바디오일', 3, 1, NOW(), NOW(), FALSE, 50),
('핸드크림', 3, 0, NOW(), NOW(), FALSE, 51),
('핸드워시', 3, 1, NOW(), NOW(), FALSE, 51),
('풋크림', 3, 0, NOW(), NOW(), FALSE, 52),
('풋샴푸', 3, 1, NOW(), NOW(), FALSE, 52),
('발냄새제거제', 3, 2, NOW(), NOW(), FALSE, 52),
('바디워시', 3, 0, NOW(), NOW(), FALSE, 53),
('바디스크럽', 3, 1, NOW(), NOW(), FALSE, 53),
('입욕제', 3, 2, NOW(), NOW(), FALSE, 53),
('비누', 3, 3, NOW(), NOW(), FALSE, 53),
('제모크림', 3, 0, NOW(), NOW(), FALSE, 54),
('스트립/왁스', 3, 1, NOW(), NOW(), FALSE, 54),
('데오스틱', 3, 0, NOW(), NOW(), FALSE, 55),
('데오롤온', 3, 1, NOW(), NOW(), FALSE, 55),
('데오스프레이', 3, 2, NOW(), NOW(), FALSE, 55),
('쿨링/데오시트', 3, 3, NOW(), NOW(), FALSE, 55),
('보습', 3, 0, NOW(), NOW(), FALSE, 56),
('세정', 3, 1, NOW(), NOW(), FALSE, 56),
('선케어', 3, 2, NOW(), NOW(), FALSE, 56);

---- depth:3 향수/디퓨터 하위의 하위
INSERT INTO categories (name, depth, display_order, created_at, updated_at, is_deleted, parent_id) VALUES
('여성향수', 3, 0, NOW(), NOW(), FALSE, 57),
('남성향수', 3, 1, NOW(), NOW(), FALSE, 57),
('유니섹스향수', 3, 2, NOW(), NOW(), FALSE, 57),
('헤어퍼퓸', 3, 3, NOW(), NOW(), FALSE, 57),
('고체향수', 3, 0, NOW(), NOW(), FALSE, 58),
('소용량향수', 3, 1, NOW(), NOW(), FALSE, 58),
('디스커버리세트', 3, 2, NOW(), NOW(), FALSE, 58),
('디퓨저/캔들/인센스', 3, 0, NOW(), NOW(), FALSE, 59),
('룸스프레이/탈취제', 3, 1, NOW(), NOW(), FALSE, 59),
('차량용방향제/샤셰', 3, 2, NOW(), NOW(), FALSE, 59);

-- 2. brands 테이블에 데이터 삽입 (임시)
INSERT INTO brands (name, is_deleted, created_at, updated_at) VALUES
('바이오더마', FALSE, NOW(), NOW()),
('달바', FALSE, NOW(), NOW()),
('에스트라', FALSE, NOW(), NOW()),
('구달', FALSE, NOW(), NOW()),
('메디힐', FALSE, NOW(), NOW()),
('토리든', FALSE, NOW(), NOW()),
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


--복사용 주석 ('용량', '피부타입', '사용기간', '사용방법', '업자', '제조국', '성분' , '심사필', '주의사항', '품질보증기준', '번호'),
INSERT INTO product_detail_info (capacity, skin_type, usage_period, usage_method, manufacturer, made_in_country, ingredients, functional_certification, caution, quality_guarantee, customer_service_number) VALUES
('500ml', '수분 부족, 건성 피부 및 모든 피부', '제조일로부터 3년 이내의 상품을 순차적으로 발송, 개봉 후 6개월이내 사용 권장', '1. 세안 후, 손 혹은 화장솜에 적당량을 적셔 피부결을 따라 부드럽게 닦아줍니다. 2. 화장솜에 듬뿍 묻혀 수분 팩으로도 활용 가능합니다.', '해외제조원 : 나오스 (NAOS) / 355, 휘 피에르-시몬 라플라스 13290, 엑상프로방스, 프랑스 화장품책임판매업자 : 나오스코리아 (유) 서울시 강남구 테헤란로 137, 11층', '프랑스', '정제수, 글리세린, 폴리솔베이트20, 다이소듐이디티에이, 세트리모늄브로마이드, 향료, 나이아신아마이드, 자일리톨, 알란토인, 프룩토올리고사카라이드, 만니톨, 헥실데칸올, 소듐하이드록사이드, 시트릭애씨드, 람노오스, 사과씨추출물, 유채스테롤, 토코페롤', '해당사항 없음', '1) 화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점, 부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우 전문의 등과 상담할 것 2) 상처가 있는 부위 등에는 사용을 자제할 것 3) 보관 및 취급 시의 주의사항 가. 어린이의 손이 닿지 않는 곳에 보관할 것 나. 직사광선을 피해서 보관할 것', '본 제품에 이상이 있을 경우 공정거래위원회 고시「품목별 소비자 분쟁해결기준」에 의해 보상해 드립니다.', '02-523-7676'),
('100ml+60ml', '모든 피부용', '제조일로부터 36개월', '세안 직후 제품을 상하로 3~5회 흔들어 내용물이 충분히 섞이게 한 뒤, 고개를 살짝 든 상태에서 10~20cm 떨어져 눈을 감고 얼굴 전체에 4~5회 골고루 분사한 후 가볍게 두드려 피부에 흡수시켜 줍니다.', '(주)비앤비코리아 / (주)비모뉴먼트', '대한민국', '달바 화이트 트러플 퍼스트 스프레이 세럼 100ml: 정제수, 다이프로필렌글라이콜, 네오펜틸글라이콜다이헵타노에이트, 1,2-헥산다이올, 나이아신아마이드, 솔비톨, 하이드록시에틸우레아, 아보카도오일, 멕시칸치아씨추출물, 바질꽃/잎/줄기추출물, 베타인, 귀리커넬추출물, 부틸렌글라이콜, 흰서양송로추출물(0.174%), 글리세린, 해바라기씨오일, 토코페릴아세테이트, 다이소듐이디티에이, 다이포타슘글리시리제이트, 아데노신, 소듐팔미토일프롤린, 비피다발효용해물, 알바수련꽃추출물, 소듐하이알루로네이트, 에탄올, 돌콩오일, 스피드웰추출물, 황산앵초추출물, 페퍼민트잎추출물, 레몬밤잎추출물, 당아욱추출물, 레이디스맨틀추출물, 서양톱풀추출물, 사우수레아인볼루크라타추출물, 인삼추출물, 연꽃추출물, 뽕나무껍질추출물, 마돈나백합꽃추출물, 에델바이스추출물, 약모밀추출물, 프리지어추출물, 카보머, 데이지꽃추출물, 알지닌, 포타슘솔베이트, 아나토씨오일, 소듐하이드록사이드, 토코페롤, 향료, 부틸페닐메틸프로피오날, 리날룰, 헥실신남알, 리모넨 달바 화이트 트러플 퍼스트 아로마틱 스프레이 세럼 60ml: 다마스크장미꽃수(770,000ppm), 다이프로필렌글라이콜, 네오펜틸글라이콜다이헵타노에이트, 정제수, 글리세레스-26, 1,2-헥산다이올, 나이아신아마이드, 흰서양송로추출물(2,000ppm), 토코페롤, 하이드록시에틸우레아, 부틸렌글라이콜, 아보카도오일, 베타인, 글리세린, 판테놀, 해바라기씨오일, 솔비톨, 다이포타슘글리시리제이트, 다이소듐이디티에이, 토코페릴아세테이트, 아데노신, 비피다발효용해물, 동백나무씨오일, 오크라열매추출물, 마카다미아씨오일, 달맞이꽃오일, 올리브오일, 돌콩오일, 알지닌, 데이지꽃추출물, 카보머, 프리지어추출물, 약모밀추출물, 에델바이스추출물, 마돈나백합꽃추출물, 뽕나무껍질추출물, 연꽃꽃추출물, 바질꽃/잎/줄기추출물, 인삼추출물, 사우수레아인볼루크라타추출물, 마트리카리아꽃추출물, 히비스커스꽃추출물, 라벤더꽃추출물, 레몬밤잎추출물, 페퍼민트잎추출물, 애플민트잎추출물, 베르가못잎추출물, 프로방스장미꽃추출물, 로즈마리잎추출물, 살비아잎추출물, 꽃송이버섯추출물, 소듐팔미토일프롤린, 포타슘솔베이트, 귀리커넬추출물, 비타민나무열매오일, 하이드롤라이즈드하이알루로닉애씨드, 멕시칸치아씨추출물, 병풀추출물, 버지니아풍년화추출물, 쇠비름추출물, 황금추출물, 아나토씨오일, 알바수련꽃추출물, 향료, 헥실신남알, 리모넨, 리날룰, 시트로넬올', '화장품법에 따른 기능성 화장품 심사(또는 보고)를 필함', '1) 화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점, 부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우 전문의 등과 상담할 것 2) 상처가 있는 부위 등에는 사용을 자제할 것 3) 보관 및 취급 시의 주의사항 가. 어린이의 손이 닿지 않는 곳에 보관할 것 나. 직사광선을 피해서 보관할 것', '본 상품에 이상이 있을 경우 공정거래위원회 고시 "소비자 분쟁 해결기준"에 의해 교환 또는 보상해 드립니다.', '02-332-7727'),
('80ml', '모든 피부용', '36개월(개봉 후 1년)', '아침, 저녁 적당량의 내용물을 덜어 얼굴에 부드럽게 펴 발라 흡수시켜줍니다.', '(주)코스비전/(주)아모레퍼시픽', '대한민국', '정제수, 부틸렌글라이콜, 글리세린, 부틸렌글라이콜다이카프릴레이트/다이카프레이트, 세틸에틸헥사노에이트, 스쿠알란, 펜타에리스리틸테트라아이소스테아레이트, 다이카프릴릴카보네이트, 베헤닐알코올, 다이메티콘, 하이드록시프로필비스팔미타마이드엠이에이, 스테아릭애씨드, 베타인, 만니톨, C14-22알코올, 팔미틱애씨드, 하이드록시프로필비스라우라마이드엠이에이, 아라키딜알코올, 콜레스테롤, 폴리아크릴레이트-13, C12-20알킬글루코사이드, 알란토인, 아라키딜글루코사이드, 나이아신아마이드, 세라마이드엔피, 글리세릴카프릴레이트, 에틸헥실글리세린, 하이드로제네이티드폴리아이소부텐, 카보머, 트로메타민, 다이메티콘올, 폴리글리세릴-10라우레이트, 하이드로제네이티드레시틴, 에틸헥실팔미테이트, 아크릴레이트/암모늄메타크릴레이트코폴리머, 솔비탄아이소스테아레이트, 실리카, 피토스핑고신, 스핑고리피드, 아라키딕애씨드, 토코페롤, 올레익애씨드', '해당사항 없음', '1) 화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점, 부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우 전문의 등과 상담할 것 2) 상처가 있는 부위 등에는 사용을 자제할 것 3) 보관 및 취급 시의 주의사항 가. 어린이의 손이 닿지 않는 곳에 보관할 것 나. 직사광선을 피해서 보관할 것', '본 제품에 이상이 있을 경우, 공정거래위원회 고시 소비자분쟁해결기준에 의거 보상해 드립니다.', '고객상담실 : 080-023-3900'),
('30+30ml', '모든 피부', '- 개봉전 : 36개월', '토너패드 -> 세럼 -> 아이크림 -> 크림 순서로 사용하시면 됩니다. 아이패치와 함께 사용하실 경우, 아이패치를 먼저 사용하신 후 아이크림을 사용하시길 바랍니다. 얼굴 전체에 사용하기에 부담이 없는 실키한 제형으로, 얼굴 전체나 팔자주름, 이마, 목 등 다양한 부위에 사용하셔도 됩니다.', '코스맥스㈜/㈜클리오', '대한민국', '탄제린추출물(54%),부틸렌글라이콜,글리세린,프로판다이올,정제수,펜타에리스리틸테트라에틸헥사노에이트,펜틸렌글라이콜,나이아신아마이드,알부틴,1,2-헥산다이올,폴리메틸실세스퀴옥세인,다이아이소스테아릴말레이트,비닐다이메티콘,에칠아스코빌에텔(2,000ppm),아스코빅애씨드폴리펩타이드,클로렐라불가리스추출물,인도멀구슬나무꽃추출물,인도멀구슬나무잎추출물,울금뿌리추출물,홀리바질잎추출물,참산호말추출물,다이글리세린,카프릴릭/카프릭/미리스틱/스테아릭트라이글리세라이드,메틸메타크릴레이트크로스폴리머,소듐폴리아크릴로일다이메틸타우레이트,암모늄아크릴로일다이메틸타우레이트/베헤네스-25메타크라일레이트크로스폴리머,비스-다이글리세릴폴리아실아디페이트-2,글루코오스,다이메티콘올,아크릴레이트/C10-30알킬아크릴레이트크로스폴리머,하이드록시아세토페논,트로메타민,프룩토올리고사카라이드,프룩토오스,글루코실헤스페리딘,에틸헥실글리세린,글리세릴아크릴레이트/아크릴릭애씨드코폴리머,알란토인,다이포타슘글리시리제이트,아데노신,토코페롤,다이소듐이디티에이' , '화장품법에 따른 기능성 화장품 심사(또는 보고)를 필함', '1) 화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점, 부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우 전문의 등과 상담할 것 2) 상처가 있는 부위 등에는 사용을 자제할 것 3) 보관 및 취급 시의 주의사항 가) 어린이의 손이 닿지 않는 곳에 보관 할 것 나) 직사광선을 피해서 보관할 것', '본 제품에 이상이 있을 경우 공정거래위원회 고시 "소비자분쟁해결기준"에 의거 보상해드립니다.', '080-080-1510'),
('24ml', '모든타입', '제조일로부터 36개월 (개봉 후 즉시)', '1. 세안 후 화장수로 피부를 정돈해 줍니다. 마스크 시트를 꺼내 얼굴 모양에 맞춰 붙여 줍니다. 2. 약 10~20분 후 시트를 떼어내고 피부에 남아있는 에센스는 가볍게 두드려 흡수시켜 줍니다.', '㈜엘에스화장품/ 엘앤피코스메틱㈜', '대한민국', '※ 메디힐 마데카소사이드 에센셜 마스크 흔적 리페어 정제수, 프로판다이올, 다이프로필렌글라이콜, 나이아신아마이드, 글리세린, 1,2-헥산다이올, 트레할로오스, 하이드록시아세토페논, 알지닌, 카보머, 폴리글리세릴-10라우레이트, 에틸헥실글리세린, 폴리글리세릴-4라우레이트, 잔탄검, 다이소듐이디티에이, 옥틸도데세스-16, 연필향나무오일, 마데카소사이드(50 ppm), 서양배추출물, 호호바씨오일, 다마스크장미꽃수, 광곽향오일, 글리세릴아크릴레이트/아크릴릭애씨드코폴리머, 오리스뿌리추출물, 멜론추출물, 불가리스쑥오일, 서양송악잎/줄기추출물, 로즈마리잎오일, 부틸렌글라이콜, 해수, 소듐하이알루로네이트, 병풀추출물, 소듐하이알루로네이트크로스폴리머, 하이드롤라이즈드글라이코사미노글리칸, 하이드로제네이티드레시틴, 향부자추출물, 벤질글라이콜, 하이드롤라이즈드하이알루로닉애씨드, 하이알루로닉애씨드, 판테놀, 세라마이드엔피 ※ 메디힐 티트리 에센셜 마스크 진정 수분 정제수, 글리세린, 다이프로필렌글라이콜, 1,2-헥산다이올, 티트리추출물(12,000 ppm), 메틸프로판다이올, 프로판다이올, 하이드록시아세토페논, 알지닌, 카보머, 아미노부티릭애씨드, 부틸렌글라이콜, 잔탄검, 폴리글리세릴-10라우레이트, 아데노신, 폴리글리세릴-4라우레이트, 해수, 다이소듐이디티에이, 트루로즈오브예리코추출물, 티트리잎오일(90 ppm), 폴리쿼터늄-51, 하이드로제네이티드레시틴, 베르가모트오일, 티트리잎추출물(10 ppm), 4-터피네올(10 ppm), 글리세릴글루코사이드, 화이트티트리잎추출물(10 ppm), 티트리꽃/잎/줄기추출물(10 ppm), 티트리잎수(10 ppm), 트레할로오스, 소엽잎추출물, 서양민들레뿌리줄기/뿌리추출물, 향부자추출물, 소두구씨오일, 판테놀, 세라마이드엔피 ※ 메디힐 콜라겐 에센셜 마스크 코어 탄력 정제수, 글리세린, 메틸프로판다이올, 프로판다이올, 1,2-헥산다이올, 하이드롤라이즈드익스텐신(비건콜라겐 2,000 ppm), 하이드록시아세토페논, 카보머, 알지닌, 잔탄검, 베타인, 트레할로오스, 아데노신, 폴리글리세릴-10라우레이트, 해수, 다이프로필렌글라이콜, 폴리글리세릴-4라우레이트, 다이소듐이디티에이, 옥틸도데세스-16, 소듐하이알루로네이트, 판테놀, 아미노부티릭애씨드, 부틸렌글라이콜, 스위트아몬드오일, 글리세릴글루코사이드, 소듐폴리아크릴레이트, 센티드제라늄꽃오일, 라벤더오일, 하이드로제네이티드레시틴, 흰목이버섯폴리사카라이드, 캐모마일꽃오일, 오렌지껍질오일, 소엽잎추출물, 서양민들레뿌리줄기/뿌리추출물, 멕시칸주니퍼오일, 엘라스틴, 향부자추출물, 카프릴릭/카프릭트라이글리세라이드, 아세틸헥사펩타이드-8, 카퍼트라이펩타이드-1, 헥사펩타이드-9, 노나펩타이드-1, 팔미토일펜타펩타이드-4, 팔미토일테트라펩타이드-7, 트라이펩타이드-1, 세라마이드엔피 ※ 메디힐 비타민씨 에센셜 마스크 잡티 토닝 정제수, 프로판다이올, 나이아신아마이드, 글리세린, 1,2-헥산다이올, 다이프로필렌글라이콜, 귤껍질추출물, 비타민나무추출물, 하이드록시아세토페논, 글리세릴글루코사이드, 알지닌, 카보머, 판테놀, 아미노부티릭애씨드, 하이드록시에틸우레아, 폴리글리세릴-10라우레이트, 잔탄검, 다이소듐이디티에이, 부틸렌글라이콜, 글리세릴아크릴레이트/아크릴릭애씨드코폴리머, 3-O-에틸아스코빅애씨드, 센티드제라늄꽃오일, 글루코오스, 시트로넬올, 해수, 바이오틴, 카르니틴에이치씨엘, 폴릭애씨드, 헤스페리딘, 메나다이온, 피리독신에이치씨엘, 리보플라빈, 티아민에이치씨엘, 토코페릴아세테이트, 제라니올, 황련추출물, 하이드로제네이티드레시틴, 아스코빅애씨드, 밤부사 불가리스잎추출물, 아마씨추출물, 향부자추출물, 에틸헥실글리세린, 소듐하이알루로네이트, 세라마이드엔피 ※ 메디힐 히알루론산 에센셜 마스크 고밀도 수분 정제수, 글리세린, 다이프로필렌글라이콜, 부틸렌글라이콜, 나이아신아마이드, 1,2-헥산다이올, 판테놀, 소듐하이알루로네이트(2,000 ppm), 알지닌, 카보머, 하이드록시아세토페논, 아미노부티릭애씨드, 잔탄검, 아데노신, 해수, 폴리글리세릴-10라우레이트, 다이소듐이디티에이, 서양배추출물, 폴리글리세릴-4라우레이트, 다마스크장미꽃수, 옥틸도데세스-16, 오리스뿌리추출물, 폴리쿼터늄-51, 멜론추출물, 서양송악잎/줄기추출물, 하이드로제네이티드레시틴, 향부자추출물, 소엽잎추출물, 서양민들레뿌리줄기/뿌리추출물, 스쿠알란, 카프릴릭/카프릭트라이글리세라이드, 다이메틸실란올하이알루로네이트(10 ppb), 글루코노락톤, 글루타티온, 하이알루로닉애씨드(10 ppb), 하이드롤라이즈드하이알루로닉애씨드(10 ppb), 하이드롤라이즈드소듐하이알루로네이트(10 ppb), 하이드록시프로필트라이모늄하이알루로네이트(10 ppb), 포타슘하이알루로네이트(10 ppb), 소듐아세틸레이티드하이알루로네이트(10 ppb), 소듐하이알루로네이트크로스폴리머(10 ppb), 소듐하이알루로네이트다이메틸실란올(10 ppb), 피토스테롤, 세라마이드엔피 ※ 메디힐 세라마이드 에센셜 마스크 보습장벽 정제수, 글리세린, 다이프로필렌글라이콜, 트라이에틸헥사노인, 프로판다이올, 시어버터, 글리세릴스테아레이트, 1,2-헥산다이올, 폴리글리세릴-3메틸글루코오스다이스테아레이트, 하이드록시아세토페논, 알지닌, 폴리글리세릴-10라우레이트, 아크릴레이트/C10-30알킬아크릴레이트크로스폴리머, 폴리글리세릴-4라우레이트, 잔탄검, 에틸헥실글리세린, 팔미틱애씨드, 카보머, 스테아릭애씨드, 세라마이드엔피(400 ppm), 아데노신, 다이소듐이디티에이, 향료, 부틸렌글라이콜, 판테놀, 서양배추출물, 다마스크장미꽃수, 오리스뿌리추출물, 멜론추출물, 글라이신, 글루타믹애씨드, 서양송악잎/줄기추출물, 아스파틱애씨드, 소듐폴리아크릴레이트, 소듐클로라이드, 향부자추출물, 엑토인, 히스티딘, 세린, 시트릭애씨드, 알라닌, 프롤린, 트레오닌, 마그네슘클로라이드, 칼슘클로라이드, 세라마이드에이피(1 ppb), 세라마이드이오피(1 ppb), 세라마이드엔에스(1 ppb) ※ 메디힐 로제 PDRN 에센셜 마스크 모공 결광 정제수, 글리세린, 프로판다이올, 부틸렌글라이콜, 글리세레스-26, 1,2-헥산다이올, 다마스크장미추출물, 잔탄검, 카보머, 판테놀, 알지닌, 하이드록시아세토페논, 카놀라오일, 폴리글리세릴-10라우레이트, 아데노신, 해바라기씨오일, 메도우폼씨오일, 다이프로필렌글라이콜, 폴리글리세릴-4라우레이트, 폴리글리세릴-10스테아레이트, 다이소듐이디티에이, 하이드록시에틸셀룰로오스, 아르간커넬오일, 다마스크장미꽃수, 치자꽃추출물, 락토바실러스발효물, 양까막까치밥나무잎추출물, 첨차잎추출물, 해수, 서양송악잎/줄기추출물, 센티드제라늄꽃오일, 스쿠알란, 밤껍질추출물, 무화과추출물, 검정콩추출물, 버지니아풍년화잎수, 퀴노아씨추출물, 향부자추출물, 하이드로제네이티드레시틴, 밤부사 불가리스잎추출물, 아마씨추출물, 에틸헥실글리세린, 소듐디엔에이, 글루코노락톤, 세라마이드엔피' , '화장품법에 따른 기능성 화장품 심사(또는 보고)를 필함', '※사용 전 반드시 사용법 및 사용 시의 주의사항을 숙지하신 후 사용하십시오. 1) 화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점, 부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우 전문의 등과 상담할 것 2) 상처가 있는 부위 등에는 사용을 자제할 것 3) 보관 및 취급 시의 주의사항 가) 어린이의 손이 닿지 않는 곳에 보관할 것 나) 직사광선을 피해서 보관할 것 4) 눈 주위를 피하여 사용할 것 ※ 붙이는 반창고나 습포에 자극이 약한 분은 사용을 주의해 줄 것 ※ 개봉 후에는 제품이 건조되므로 바로 사용하시고 한번 사용한 마스크는 재사용을 삼가해 줄 것', '본 제품에 이상이 있을 경우 공정거래위원회 고시 “소비자 분쟁해결 기준”에 의해 보상해 드립니다.', '080-860-8400'),
('44g*4ea', '모든피부', '개봉전 24개월, 개봉후 즉시', '1. 세안 후 토너 또는 미스트로 피부 결을 정돈해 준줍니다. 2. 파우치를 개봉하여 접혀져 있는 마스크팩을 펼쳐준 뒤, 마스크팩의 필름을 제거합니다. ① 아이패치를 떼어내 한 장씩 양쪽 눈 밑에 붙여줍니다. ② 넥 패치를 떼어내 목 라인에 맞추어 붙여줍니다. ③ 입 부분 패치를 떼어내 미간에 부착해 줍니다. ④ 양쪽 눈 부분 패치를 떼어내 팔자 부위에 부착해 줍니다. ⑤ 마스크의 하단을 입 중심으로 얼굴에 맞추어 붙여줍니다. ⑤ 마스크의 상단을 눈 중심으로 얼굴에 맞추어 붙여줍니다. 3. 피부 상태에 따라 약 40-50분부터 유효성분이 흡수되며 투명해지기 시작합니다. 4. 마스크 전체가 투명해지면 떼어낸 후 남은 에센스를 손으로 두드려 흡수시켜줍니다. TIP. 집중 케어가 필요한 날에는 스킨케어 마지막 단계에서 수면팩으로 사용해 주세요.', '코스맥스(주)/(주)달바글로벌', '대한민국', '콜라겐수, 글리세린, 프로판다이올, 세틸에틸헥사노에이트, 부틸렌글라이콜, 나이아신아마이드(20,000ppm), 1,2-헥산다이올, 아이리쉬모스가루, 비타민나무수(12,000ppm), 베타인, 정제수, 캐롭검, 시어버터, 인도멀구슬나무꽃추출물, 홀리바질잎추출물, 인도멀구슬나무잎추출물, 울금뿌리추출물, 참산호말추출물, 감나무잎추출물, 포도추출물, 잇꽃꽃추출물, 커피콩추출물, 호장근뿌리추출물, 녹차추출물, 밤껍질추출물, 초피나무열매추출물, 흰서양송로추출물, 하이드롤라이즈드하이알루로닉애씨드, 하이알루로닉애씨드, 소듐하이알루로네이트, 하이드롤라이즈드완두콩단백질, 하이드롤라이즈드감자단백질, 하이드롤라이즈드쌀단백질, 하이드롤라이즈드콩단백질, 하이드롤라이즈드옥수수단백질, 하이드롤라이즈드귀리단백질, 하이드롤라이즈드스위트아몬드단백질, 하이드롤라이즈드밀단백질, 콜라겐추출물, 하이드롤라이즈드호호바단백질, 하이드롤라이즈드식물성단백질, 카프릴릭/카프릭트라이글리세라이드, 하이드록시에틸우레아, 하이드록시아세토페논, 판테놀(2,000ppm), 알란토인, 폴리아크릴레이트-13, 글루코만난, 폴리글리세릴-10라우레이트, 글리세릴스테아레이트, 폴리글리세릴-3메틸글루코오스다이스테아레이트, 포타슘클로라이드, 소듐폴리아크릴로일다이메틸타우레이트, 하이드로제네이티드폴리아이소부텐, 수크로오스, 아데노신, 구아검, 셀룰로오스검, 에틸헥실글리세린, 다이포타슘글리시리제이트, 유비퀴논(175ppm), 젤란검, 아가, 에틸헥실팔미테이트, 소듐폴리아크릴레이트, 솔비탄아이소스테아레이트, 글루코오스, 만노오스, 폴리솔베이트80, 아스코빌테트라이소팔미테이트(7ppm), 아스타잔틴, 아스코빅애씨드(170ppb), 세라마이드엔피, 글루타티온, 하이드로제네이티드레시틴, 토코페롤(0.09ppm), 소듐파이테이트, 다이메틸실란올하이알루로네이트, 하이드롤라이즈드소듐하이알루로네이트, 포타슘하이알루로네이트, 하이드록시프로필트라이모늄하이알루로네이트, 소듐하이알루로네이트크로스폴리머, 소듐디엔에이, 소듐하이알루로네이트다이메틸실란올, 소듐아세틸레이티드하이알루로네이트, 리놀레익애씨드, 하이드롤라이즈드루핀단백질, 레티놀(0.00045ppm), 하이드롤라이즈드보리단백질, 글라이코프로테인, 잔탄검, 향료' , '화장품법에 따른 기능성 화장품 심사(또는 보고)를 필함', '가.화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점,부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우에는 전문의 등과 상담할 것 나.상처가 있는 부위 등에는 사용을 자제할 것 다.보관 및 취급 시 주의사항 1) 어린이의 손이 닿지 않는 곳에 보관할 것 2) 직사광선을 피해서 보관할 것 *화장품이 눈에 들어갔을 때에는 물로 씻어내고, 이상이 있는 경우에는 전문의와 상담할 것 * 마스크가 투명해지는 시간은 개인차가 있을 수 있으며, 피부 컨디션 및 부착 시간에 따라 투명도가 달라질 수 있습니다. * 지지체 없이 세럼 자체를 굳혀 만든 겔 마스크 특성상 찢어지거나 손상될 수 있으나 제품 사용에는 이상이 없습니다. * 캡슐은 피부에 흡수되지 않습니다.', '본 상품에 이상이 있을 경우 공정거래 위원회 고시 "소비자 분쟁 해결기준"에 의해 교환 또는 보상해 드립니다.', '02-332-7727');


-- 3. products 테이블에 데이터 삽입 (임시)
INSERT INTO products (product_detail_info_id, brand_id, category_id, delivery_policy_id, use_restock_noti, product_name, product_code, search_keywords, exposure_status, sale_status, description, is_cancelable, is_deleted, created_at, updated_at) VALUES
(1, 1, 60, 2, TRUE, '바이오더마 하이드라비오 토너 500ml 기획(+화장솜 20매 증정)', 'SHS_001', '바이오더마 스킨 토너', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, '2025-10-26 09:00:00', '2025-10-26 09:00:00'),
(2, 2, 61, 2, TRUE, '[NO.1 미스트세럼] 달바 퍼스트 스프레이 세럼 100ml 2개 기획', 'WHOO_001', '달바 세럼', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, '2025-09-29 09:31:20', '2025-09-29 09:31:20'),
(3, 3, 62, 2, FALSE, '에스트라 아토베리어365 크림 80ml 기획', 'LANEIGE_001', '에스트라 크림', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, '2025-11-07 09:24:50', '2025-11-07 09:24:50'),
(4, 4, 63, 2, FALSE, '구달 청귤 비타C 잡티케어 아이크림 30ml 1+1 기획', 'INNIS_001', '구달 아이크림', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, '2025-11-08 09:12:24', '2025-11-08 09:12:24'),
(5, 5, 70, 2, FALSE, '메디힐 에센셜 마스크팩 1매 고기능 7종 택1', 'TFS_001', '메디힐 마스크팩', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, '2025-11-11 09:08:31', '2025-11-11 09:08:31'),
(6, 2, 71, 2, FALSE, '[4세대발효콜라겐/4매입] 달바 비타 하이드로겔 마스크 4매입', 'TFS_001', '달바 겔마스크', 'EXPOSURE', 'ON_SALE', '설명 없음', true, false, '2025-11-12 09:18:25', '2025-11-12 09:18:25');


-- product_detail_images 데이터 // 복사용 (1, 0, ''),
INSERT INTO product_detail_images (product_id, display_order, image_url) VALUES
(1, 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000184916/202510312311/crop0/image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C14207/image/20230830141432/qc14_20230830141432.jpg?created=202511070919'),
(1, 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000184916/202510312311/crop0/www.bioderma.co.kr/img/duty/img_hologram.jpg?created=202511070919'),
(1, 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000184916/202510312311/crop0/image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C14207/image/20230830141432/qc14_20230830141432.jpg?created=202511070919'),
(2, 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000165738/202509041413/crop0/image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C14647/image/20240513000442/qc14_20240513000442.jpg?created=202511112116'),
(2, 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000165738/202509041413/crop1/image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C14647/image/20240513000442/qc14_20240513000442.jpg?created=202511112116'),
(2, 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000165738/202509041413/crop2/image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C14647/image/20240513000442/qc14_20240513000442.jpg?created=202511112116'),
(3, 0, 'https://amc.apglobal.com/asset/384224417642/image_14eq23s8sp4n5durfop1olb06s?content-disposition=inline'),
(4, 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000229776/202509031344/crop0/www.clubclioimg.co.kr/images/OY/%EB%A6%AC%EB%89%B4%EC%96%BC_%EC%95%84%EC%9D%B4%ED%81%AC%EB%A6%BC%20(1).jpg?created=202509031348'),
(4, 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000229776/202509031344/crop1/www.clubclioimg.co.kr/images/OY/%EB%A6%AC%EB%89%B4%EC%96%BC_%EC%95%84%EC%9D%B4%ED%81%AC%EB%A6%BC%20(1).jpg?created=202509031348'),
(5, 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000217620/202511041821/crop0/dk6avxcbz8hlj.cloudfront.net/assets/NpsvafDnenyUCANlpZlUVgur/DBe1xYRrzKQeo9DGDdUaTU1K1gZCchYt.png?created=202511041822'),
(5, 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000217620/202511041821/crop1/dk6avxcbz8hlj.cloudfront.net/assets/NpsvafDnenyUCANlpZlUVgur/DBe1xYRrzKQeo9DGDdUaTU1K1gZCchYt.png?created=202511041822'),
(5, 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000217620/202511041821/crop0/dk6avxcbz8hlj.cloudfront.net/assets/XMEeFFpsNgULuDZ0ah8ZysIF/NHNNtcx60RSpQAKTbCK3PtbqqSx0QzYm.jpeg?created=202511041822'),
(5, 3, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000217620/202511041821/crop1/dk6avxcbz8hlj.cloudfront.net/assets/XMEeFFpsNgULuDZ0ah8ZysIF/NHNNtcx60RSpQAKTbCK3PtbqqSx0QzYm.jpeg?created=202511041822'),
(6, 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000228799/202511161428/crop0/gi.esmplus.com/dalbaonly/%ED%95%98%EC%9D%B4%EB%93%9C%EB%A1%9C%EA%B2%94/0.jpg?created=202511161439'),
(6, 1, 'https://gi.esmplus.com/dalbaonly/%ED%95%98%EC%9D%B4%EB%93%9C%EB%A1%9C%EA%B2%94/0-1-1.gif'),
(6, 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000228799/202511161428/crop0/gi.esmplus.com/dalbaonly/%ED%95%98%EC%9D%B4%EB%93%9C%EB%A1%9C%EA%B2%94/1.jpg?created=202511161439'),
(6, 3, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000228799/202511161428/crop1/gi.esmplus.com/dalbaonly/%ED%95%98%EC%9D%B4%EB%93%9C%EB%A1%9C%EA%B2%94/1.jpg?created=202511161439');

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
(1, '바이오더마 토너 500ml 기획', 10000, 30000, 80, 100, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0018/A00000018491610ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW()),
(2, '달바 퍼스트 스프레이 세럼 100ml 2개 기획', 20000, 59800, 80, 100, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0023/A00000023272408ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW()),
(3, '에스트라 아토베리어365 크림 80ml 기획', 15000, 33000, 120, 120, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022283315ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW()),
(4, '구달 청귤 비타C 잡티케어 아이크림 30ml', 15000, 24000, 90, 90, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022977610ko.jpg?l=ko&QT=85&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW()),
(5, '티트리 진정수분 1매', 500, 1000, 80, 100, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/2095773322051515522.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW()),
(5, '콜라겐 코어탄력 1매', 500, 1000, 80, 100, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/4292023589387008731.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 1, false, NOW(), NOW()),
(5, '비타민씨 잡티토닝 1매', 500, 1000, 120, 120, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/6333511698168958736.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 2, false, NOW(), NOW()),
(5, '세라마이드 보습장벽 1매', 500, 1000, 90, 90, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/5177396663963625881.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 3, false, NOW(), NOW()),
(5, '마데카소사이드 흔적리페어 1매', 800, 2000, 70, 70, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/1/1550813712772282449.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 4, false, NOW(), NOW()),
(5, '로제 PDRN 모공결광 1매', 800, 2000, 90, 90, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/3/927811821371366876.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 5, false, NOW(), NOW()),
(5, '히알루론산 고밀도 수분 1매', 800, 2000, 70, 70, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/options/item/2025/2/7886183073217244310.jpg?RS=108x0&QT=85&SF=webp&sharpen=1x0.5', 6, false, NOW(), NOW()),
(6, '달바 비타 하이드로겔 마스크 4매입', 14000, 28000, 70, 70, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0022/A00000022879929ko.png?l=ko&QT=100&SF=webp&sharpen=1x0.5', 0, false, NOW(), NOW());

---- coupons 테이블 데이터 삽임
INSERT INTO coupons (name, issue_type, auto_issue_type, auto_issue_trigger, coupon_code, total_quantity, issuable_start_date, issuable_end_date, availability, has_limit_usage_period, valid_from, valid_to, has_limit_min_order_amount, min_order_amount, discount_type, fixed_discount_amount, discount_percentage, has_limit_max_discount_amount, max_discount_amount, is_deleted, created_at, updated_at)
VALUES
('신규회원 환영 쿠폰', 'AUTO', 'NEWUSER', 'ALL_USER', NULL, 9999999999, NULL, NULL, 'USABLE', FALSE, NULL, NULL, TRUE, 20000, 'FIXED', 3000, NULL, NULL, NULL, FALSE, NOW(), NOW());

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

-- 발급받은 유저 쿠폰
INSERT INTO user_coupons (user_id, coupon_id, usage_status, used_at, is_deleted, created_at, updated_at)
VALUES
(1, 1, 'ACTIVE', NULL, FALSE, NOW(), NOW()),
(2, 1, 'ACTIVE', NULL, FALSE, NOW(), NOW()),
(3, 1, 'ACTIVE', NULL, FALSE, NOW(), NOW()),
(4, 1, 'ACTIVE', NULL, FALSE, NOW(), NOW()),
(5, 1, 'ACTIVE', NULL, FALSE, NOW(), NOW());

-- 민석 carts 테이블 데이터(임시)
INSERT INTO carts (id, user_id, created_at, updated_at)
VALUES
(1, 1, NOW(), NOW()),
(2, 2, NOW(), NOW()),
(3, 3, NOW(), NOW()),
(4, 4, NOW(), NOW()),
(5, 5, NOW(), NOW());

-- 민석 cart_products 테이블 데이터
INSERT INTO cart_products (id, cart_id, product_option_id, quantity, added_at, updated_at)
VALUES
(1, 1, 1, 5, NOW(), NOW()),
(2, 1, 2, 5, NOW(), NOW()),
(3, 1, 3, 5, NOW(), NOW());

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

-- 민석 order 테이블 더미 데이터
INSERT INTO orders
(id, user_id, order_number, payment_method, expected_delivery_date, total_product_amount, delivery_fee,
discount_amount, used_points, final_amount, receiver_name, receiver_phone, postal_code, street_address,
detailed_address, delivery_request, is_deleted, created_at, updated_at)
VALUES
-- 1. 1개월 내 주문 (2025-11-05 생성)
(1, 1, '20251105-A1B2C3', 'KAKAO', '2025-11-07', 55000, 3000, 5000, 0, 53000, '홍길동',
'010-1234-5678', '01234', '서울특별시 강남구 테헤란로 123', '삼성타워빌딩 10층', '문 앞에 놓아주세요',
false, '2025-11-05 10:30:00', '2025-11-05 10:30:00'),
-- 2. 3개월 내 주문 (2025-09-15 생성)
(2, 1, '20250915-D4E5F6', 'CARD', '2025-09-17', 120000, 0, 0, 2000, 118000, '홍길동',
'010-1234-5678', '01234', '서울특별시 강남구 테헤란로 123', '삼성타워빌딩 10층', '문 앞에 놓아주세요',
false, '2025-09-15 15:45:00', '2025-09-15 15:45:00'),
-- 3. 6개월 내 주문 (2025-06-20 생성)
(3, 1, '20250620-G7H8I9', 'NAVER', '2025-06-22', 35000, 3000, 0, 0, 38000, '홍길동',
'010-1234-5678', '01234', '서울특별시 강남구 테헤란로 123', '삼성타워빌딩 10층', '문 앞에 놓아주세요',
FALSE, '2025-06-20 09:00:00', '2025-06-20 09:00:00'),
-- 4. 12개월 내 주문 (2024-12-10 생성)
(4, 1, '20241210-J0K1L2', 'PAYCO', '2024-12-12', 78000, 2500, 10000, 0, 70500, '홍길동',
'010-1234-5678', '01234', '서울특별시 강남구 테헤란로 123', '삼성타워빌딩 10층', '문 앞에 놓아주세요',
FALSE, '2024-12-10 09:00:00', '2024-12-10 09:00:00'),
-- 5. 1년 이전 주문 (조회 범위 초과 확인용 - 2024-05-01 생성)
(5, 1, '20240501-M3N4O5', 'BANK', '2024-05-03', 22000, 3000, 0, 0, 25000, '홍길동',
'010-1234-5678', '01234', '서울특별시 강남구 테헤란로 123', '삼성타워빌딩 10층', '문 앞에 놓아주세요',
FALSE, '2024-05-01 09:00:00', '2024-05-01 09:00:00');

-- 민석 order_products 테이블 더미 데이터
INSERT INTO order_products
(id, order_id, product_option_id, quantity, purchased_price, order_product_status, created_at, updated_at)
VALUES
(1, 1, 1, 5, 5000, 'PAID', '2025-11-05 10:30:00', '2025-11-05 10:30:00'),
(2, 1, 2, 5, 10000, 'PAID', '2025-11-05 10:30:00', '2025-11-05 10:30:00'),
(3, 2, 3, 3, 15000, 'PREPARING', '2025-09-15 15:45:00', '2025-09-15 15:45:00'),
(4, 2, 4, 2, 8000, 'PREPARING', '2025-09-15 15:45:00', '2025-09-15 15:45:00'),
(5, 3, 5, 1, 6000, 'SHIPPING', '2025-06-20 09:00:00', '2025-06-20 09:00:00'),
(6, 3, 6, 4, 8500, 'SHIPPING', '2025-06-20 09:00:00', '2025-06-20 09:00:00'),
(7, 4, 7, 10, 6000, 'DELIVERED', '2025-12-10 09:00:00', '2025-12-10 09:00:00'),
(8, 4, 8, 5, 5000, 'DELIVERED', '2025-12-10 09:00:00', '2025-12-10 09:00:00'),
(9, 5, 9, 2, 1500, 'DELIVERED', '2024-05-01 09:00:00', '2024-05-01 09:00:00'),
(10, 5, 10, 3, 4500, 'DELIVERED', '2024-05-01 09:00:00', '2024-05-01 09:00:00');

-- review 테이블 더미 데이터
INSERT INTO reviews
(id, user_id, product_id, order_id, content, rating, is_visible, is_deleted, created_at, updated_at)
VALUES
(1, 1, 1, 1, '리뷰1', 5, true, false, '2025-11-10 10:00:00', '2025-11-10 10:00:00'),
(2, 1, 2, 2, '리뷰2', 4, true, false, '2025-09-20 11:30:00', '2025-09-20 11:30:00'),
(3, 2, 3, 3, '리뷰3', 3, true, false, '2025-06-23 09:15:00', '2025-06-23 09:15:00'),
(4, 2, 4, 4, '리뷰4', 5, true, false, '2024-12-17 14:45:00', '2024-12-17 14:45:00'),
(5, 3, 5, 5, '리뷰5', 5, true, false, '2024-05-05 18:00:00', '2025-05-05 18:00:00');

-- 민석 데이터 크롤링
-- ========================================
-- 토리든 다이브인 저분자 히알루론산 토너
-- ========================================

-- 1. 상품 상세정보 INSERT문
INSERT INTO product_detail_info (
    capacity,
    skin_type,
    usage_period,
    usage_method,
    manufacturer,
    made_in_country,
    ingredients,
    functional_certification,
    caution,
    quality_guarantee,
    customer_service_number
) VALUES
(
    '[본품] 다이브인 토너 300ml [증정] 다이브인 토너 100ml',
    '모든 피부 타입',
    '제조일로부터 3년 / 개봉 후 12개월',
    '세안 후 화장솜 혹은 손에 적당량을 덜어 피부결을 따라 부드럽게 흡수시켜 줍니다.',
    '리봄화장품(주) / (주)토리든',
    '대한민국',
    '정제수, 부틸렌글라이콜, 다이프로필렌글라이콜, 1,2-헥산다이올, 글리세린, 베타인, 알란토인, 판테놀, 쇠비름추출물, 트레할로오스, 소듐하이알루로네이트, 하이드롤라이즈드하이알루로닉애씨드(100ppm), 소듐하이알루로네이트크로스폴리머, 하이드롤라이즈드소듐하이알루로네이트, 소듐아세틸레이티드하이알루로네이트, 2,3-부탄다이올, 펜틸렌글라이콜, 말라카이트추출물, 다이소듐이디티에이, 에틸헥실글리세린',
    '해당사항 없음',
    '1) 화장품 사용 시 또는 사용 후 직사광선에 의하여 사용부위가 붉은 반점, 부어오름 또는 가려움증 등의 이상 증상이나 부작용이 있는 경우에는 전문의 등과 상담할 것 2) 상처가 있는 부위 등에는 사용을 자제할 것 3) 보관 및 취급 시 주의사항 가) 어린이의 손이 닿지 않는 곳에 보관할 것 나) 직사광선을 피해서 보관할 것',
    '본 제품은 공정거래위원회고시 소비자분쟁해결 기준에 의거 교환 또는 보상 받을 수 있습니다.',
    '1600-3584'
);

-- 2. 상품 정보 INSERT문
INSERT INTO products (
    product_detail_info_id,
    brand_id,
    category_id,
    delivery_policy_id,
    use_restock_noti,
    product_name,
    product_code,
    search_keywords,
    exposure_status,
    sale_status,
    description,
    is_cancelable,
    is_deleted,
    created_at,
    updated_at
) VALUES
(7, 6, 60, 2, FALSE, '[증량기획] 토리든 다이브인 저분자 히알루론산 토너 300ml 기획(+100ml 추가 증정)', 'TRD_001', '토리든 다이브인 히알루론산 토너 저분자', 'EXPOSURE', 'ON_SALE', '설명 없음', TRUE, FALSE, NOW(), NOW());

-- 3. 상품 상세 이미지 INSERT문 (47개)
INSERT INTO product_detail_images (product_id, display_order, image_url) VALUES
(7, 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/ifh.cc/g/LsFMgn.jpg?created=202511051200'),
(7, 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/01.jpg?created=202511051200'),
(7, 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/01.jpg?created=202511051200'),
(7, 3, 'http://torriden.jpg1.kr/torriden/product/DI/toner/02.gif'),
(7, 4, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/03-1.jpg?created=202511051200'),
(7, 5, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/03-1.jpg?created=202511051200'),
(7, 6, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/03-2.jpg?created=202511051200'),
(7, 7, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/03-2.jpg?created=202511051200'),
(7, 8, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/04.jpg?created=202511051200'),
(7, 9, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/05.jpg?created=202511051200'),
(7, 10, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/05.jpg?created=202511051200'),
(7, 11, 'http://torriden.jpg1.kr/torriden/product/DI/toner/06.gif'),
(7, 12, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/07.jpg?created=202511051200'),
(7, 13, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/08-1.jpg?created=202511051200'),
(7, 14, 'http://torriden.jpg1.kr/torriden/product/DI/toner/08-2.gif'),
(7, 15, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/08-3.jpg?created=202511051200'),
(7, 16, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/09.jpg?created=202511051200'),
(7, 17, 'http://torriden.jpg1.kr/torriden/product/DI/toner/10-1.gif'),
(7, 18, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/10-2.jpg?created=202511051200'),
(7, 19, 'http://torriden.jpg1.kr/torriden/product/DI/toner/11-1.gif'),
(7, 20, 'http://torriden.jpg1.kr/torriden/product/DI/toner/11-2.gif'),
(7, 21, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/12.jpg?created=202511051200'),
(7, 22, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/13.jpg?created=202511051200'),
(7, 23, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/13.jpg?created=202511051200'),
(7, 24, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/14.jpg?created=202511051200'),
(7, 25, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/14.jpg?created=202511051200'),
(7, 26, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/15-1.jpg?created=202511051200'),
(7, 27, 'http://torriden.jpg1.kr/torriden/product/DI/toner/15-2.gif'),
(7, 28, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/15-3.jpg?created=202511051200'),
(7, 29, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/16.jpg?created=202511051200'),
(7, 30, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/17.jpg?created=202511051200'),
(7, 31, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/18.jpg?created=202511051200'),
(7, 32, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/18.jpg?created=202511051200'),
(7, 33, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/19.jpg?created=202511051200'),
(7, 34, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/19.jpg?created=202511051200'),
(7, 35, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/20.jpg?created=202511051200'),
(7, 36, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/20.jpg?created=202511051200'),
(7, 37, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/21.jpg?created=202511051200'),
(7, 38, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/21.jpg?created=202511051200'),
(7, 39, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/22.jpg?created=202511051200'),
(7, 40, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/22.jpg?created=202511051200'),
(7, 41, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop2/torriden.jpg1.kr/torriden/product/DI/toner/22.jpg?created=202511051200'),
(7, 42, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop0/torriden.jpg1.kr/torriden/product/DI/toner/99.jpg?created=202511051200'),
(7, 43, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop1/torriden.jpg1.kr/torriden/product/DI/toner/99.jpg?created=202511051200'),
(7, 44, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/html/crop/A000000170266/202511051159/crop2/torriden.jpg1.kr/torriden/product/DI/toner/99.jpg?created=202511051200');

-- 4. 제품 메인 이미지 INSERT문 (3개)
INSERT INTO product_main_images (product_id, image_type, display_order, image_url) VALUES
(7, 'THUMBNAIL', 0, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0017/A00000017026613ko.jpg?l=ko'),
(7, 'GALLERY', 1, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0017/A00000017026609ko.jpg?l=ko'),
(7, 'GALLERY', 2, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0017/A00000017026611ko.jpg?l=ko');

-- 5. 상품 옵션 INSERT문 (옵션 이미지 없음)
INSERT INTO product_options (
    product_id,
    option_name,
    purchase_price,
    selling_price,
    current_stock,
    initial_stock,
    safety_stock,
    image_url,
    display_order,
    is_deleted,
    created_at,
    updated_at
) VALUES
(7, '[증량기획] 토리든 다이브인 저분자 히알루론산 토너 300ml 기획(+100ml 추가 증정)', 12000, 15700, 150, 200, 20, 'https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/10/0000/0017/A00000017026613ko.jpg?l=ko', 0, FALSE, NOW(), NOW());