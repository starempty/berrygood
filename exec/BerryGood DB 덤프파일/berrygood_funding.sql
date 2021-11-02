-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: j5d101.p.ssafy.io    Database: berrygood
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `funding`
--

DROP TABLE IF EXISTS `funding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funding` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `due` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `comp_no` bigint DEFAULT NULL,
  `calc` int NOT NULL,
  PRIMARY KEY (`no`),
  KEY `FK513cetk8tvsalmyfhynh27exr` (`comp_no`),
  CONSTRAINT `FK513cetk8tvsalmyfhynh27exr` FOREIGN KEY (`comp_no`) REFERENCES `comp` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding`
--

LOCK TABLES `funding` WRITE;
/*!40000 ALTER TABLE `funding` DISABLE KEYS */;
INSERT INTO `funding` VALUES (6,1000,'<p><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">누군가에게는 소중한 가족이지만&hellip;</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">반려동물과 동반 입장 가능한 장소들이 점차 확대되고 반려동물만을 위한 공간이 많아지고 있는 요즘, 우리는 길거리와 상점, 레스토랑 등 다양한 일상 속에서 주인과 함께 있는 행복한 반려동물들의 모습을 심심치 않게 만나볼 수 있습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">하지만 아이러니하게도 유기되는 동물의 수 역시 지난 몇 년간 꾸준히 증가해 왔다는 사실, 여러분은 알고 계셨나요? 작년에는 역대 최대치인 13만 6천 마리의 동물들이 유기되어 많은 분들의 공분과 안타까움을 자아내기도 했습니다. 이는 반려동물의 생활 환경은 조금씩 개선되고 있지만, 반려동물에 대한 사회 전반의 인식은 아직도 많은 변화와 노력이 필요하다는 의미이기도 합니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">모든 동물 친구들의 행복을 바라는 프로젝트</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">메르꽁떼는 더 많은 반려동물이 그들의 삶을 기꺼이 누리고, 또 같은 마음을 가진 사람들이 변화를 위해 연대할 수 있도록 작지만 선명한 도전을 시작해보려 합니다. 우리에게 웃음과 행복을 가져다주는 모든 동물들은 건강하고 행복할 자격이 있다는 것, 단순히 소비 대상이 아닌 존중받아야 하는 가족같은 존재라는 것, 책임감을 가지고 평생 함께 살아갈 준비가 되어 있지 않다면 쉽게 입양하고 파양하면 안된다는 것을 함께 외쳐주세요.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">우리는 분명 함께 행복해질 수 있어요</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">메르꽁떼는 동물 친구들이 행복한 하루를 보내기를 바라는 마음을 담아 정성스레 제품을 만들어 나가는 펫 라이프 스타일 브랜드로, [꽁데 드 매직보울]은 반려동물뿐만 아니라 보호가 필요한 유기견 역시 따뜻하고 행복한 하루를 보내길 바라는 마음에서 시작되었습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">[꽁데 드 매직보울]의 수익금은 보호소의 유기견 친구들에게 후원될 예정이며, 이번 프로젝트를 통해 지속적인 나눔의 기회를 마련해나갈 예정입니다. 매직보울에 더 많은 희망이 담길 수 있도록 여러분의 꼬마 친구들과 함께 해주세요!.</p>\n<div class=\"FundingDetailEditorTitle_wrap__3316t FundingDetailEditorTitle_type_b__3scOh\">출처 : https://happybean.naver.com/fundings/detail/F75</div>','2021-10-06 09:00:00.000000','모든 동물친구들의 행복을 바라는 매직 보울','2021-10-07 15:19:36.090000',7,0),(7,1000,'<p><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">\'고체 바\'로 함께 만드는 숲</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">대량생산을 위한 벌목으로 파괴되는 열대우림과 지구 온난화로 인한 기후 위기로 기상 이변이 끊이지 않고 있습니다. 이산화탄소를 흡수하는 나무를 심어 숲을 만드는 일은 지구의 회복을 위해 우리가 꼭 해야 할 일입니다. 모레상점은 친환경 고체 바 한 개가 팔릴 때마다 한 그루의 나무를 심습니다. 지구의 허파라 불리는 아마존, 큰 산불의 흉터가 남아있는 호주, 팜유 생산으로 열대우림이 사라지는 인도네시아 중 고객이 직접 나무를 심을 지역을 선택할 수 있습니다. 나무 심기는 글로벌 비영리단체 &lsquo;원트리플랜티드&rsquo;와 함께 합니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">일상에서 실천하는 제로 웨이스트</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">제로 웨이스트란 갈수록 심각해지는 쓰레기 문제를 해결하기 위한 이상적인 목표와 원칙을 의미합니다. 쓰레기를 아예 만들지 말자는 뜻보단 재활용과 재사용을 통해 폐기되는 쓰레기의 양을 줄이는 구체적인 방법을 제안하는 것에 더 가깝습니다. 고체 바를 사용하는 것은 대표적인 일상 속 제로 웨이스트 실천법입니다. 재활용이 거의 불가능한 플라스틱 용기에 든 샴푸나 컨디셔너 대신에 사탕수수 부산물로 만든 생분해성 소재로 포장한 고체 바를 사용하면 평생 동안 배출하는 플라스틱 쓰레기의 양을 크게 줄일 수 있습니다. 재활용이 어려운 플라스틱 병뚜껑으로 나무 키링을 제작해, 자원 순환의 중요성도 함께 강조했습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">건강한 피부를 위해 가득 채운 천연 성분</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">일반 모공에 비해 3배 큰 두피 모공은 각종 유해 화학 물질을 쉽게 흡수합니다. 그런 두피에 매일 사용하는 샴푸, 컨디셔너를 액상으로 만들기 위해 많은 화학 물질이 들어가는데 설페이트 계면활성제, 실리콘, 인공향료, 착향제, 착색제, 보존제 등이 대표적입니다. 모어포모레 리프레싱 고체 바는 이러한 화학 물질 대신 코코넛 유래 천연 계면활성제와 천연 보습 오일, EWG 그린 등급 천연 에센셜 오일 등 천연 및 천연 유래 성분으로 채웠습니다. 더불어 동물성 원료를 배제하고, 식물성 천연 원료만을 사용해 비건이 사용하기 적합하게 제조했습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">환경을 지키는 작은 실천 함께해요!</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">\'모레\'상점은 내일보다 한 발짝 나아가지만, 아주 멀지 않은 미래를 뜻합니다. &lsquo;MORE FOR MORE&rsquo;는 지속가능한 책임소비를 위한 플랫폼 모레상점이 환경문제 해결에 보다 더 적극적으로 기여하기 위해 시작한 기부 프로젝트입니다. 모어포모레 고체바는 광대한 열대우림을 훼손해 지구온난화를 가속화시키고 야생동물의 서식지를 파괴하는 &lsquo;팜유&rsquo;를 배제하고 만들었습니다. 많은 분들이 친환경 고체 바를 통해 나무도 심고 일상 속에서 제로 웨이스트를 실천하는 기쁨도 함께 누리시기 바랍니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">&nbsp;</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">출처 : https://happybean.naver.com/fundings/detail/F273</p>','2021-10-06 09:00:00.000000','한 그루 나무를 심는 친환경 고체 바','2021-10-07 14:32:05.305000',4,0),(8,1500,'<p><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">고향을 떠나 한국에 온 결혼이주여성들</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">다문화카페우리는 결혼이주여성들에게 일자리를 제공하는 사회적경제 기업입니다. 한국 사회에서 주체적으로 살아갈수 있도록 경제적, 정서적 지원을 하는 것을 목적으로 바리스타 교육, 카페 운영 등 결혼이주여성 일자리 창출과 고용 안정 및 사회적 가치 창출 및 실현을 위해 운영하고 있습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">코로나19로 어려움을 겪는 결혼이주여성들</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">\"처음 한국에 왔을 때는 언어, 문화, 음식 등 모든 것이 달라서 적응하는데 몸과 마음이 힘들었습니다. 카페에서 일하는 동안 저는 한국어를 열심히 공부하여 사회통합프로그램의 마지막 단계까지 합격하여 국적을 신청하였고 대한민국의 국적과 바리스타 자격증을 취득하였습니다.\" 결혼이주여성분들의 대부분은 카페에서 받은 월급으로 고국 가족의 경제문제를 해결하기 위해 일하고 있는데 코로나19 사회적 거리두기로 카페 이용 고객들은 줄어 들어 재정적 부담이 커지는 어려움 속에서도 근무시간을 줄여가며 근무하고 휴업으로 받은 지원금으로 버티며 이겨내고 있습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">맛에 가치까지 더한 \'다문화카페우리\' 커피</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">다문화카페우리의 커피는 공정무역 커피로, 맛에 가치까지 더한 커피입니다. 진하고 묵직한 블렌딩으로 바디가 풍부하고 단맛과 스모키향이 일품인 드립백 커피는 컵과 따뜻한 물만 있으면 언제 어디서나 간편하고 맛있게 드실 수 있습니다. 또 하나의 인기제품인 라즈베리, 베리청도 이번 펀딩을 통해서 선보입니다. 청 또한 직접 만들어 판매하고 있는데요. 무방부제, 무색소, 무첨가물로 만들었습니다.</p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\"><strong class=\"FundingDetailEditorParagraph_title__2o5M9\">마음을 담아 정성스럽게 만듭니다.</strong></p>\n<p class=\"FundingDetailEditorParagraph_content__3l1rd\">다문화카페우리의 상품은 결혼이주여성들이 정성스럽게 마음을 담아 제조합니다. 직접 만들어 신선하게 드실 수 있습니다. 수익금은 취업을 원하는 결혼이주여성들을 대상으로 자립능력을 향상시키고 안정적인 일자리를 찾을 수 있는 기반을 조성하도록 바리스타 교육에 사용됩니다. 가치 있는 소비로 한국사회에서 결혼이주여성들이 행복하게 주체적으로 살아갈 수 있는 일에 동참해주세요!</p>','2021-10-06 09:00:00.000000','이주여성의 일자리 창출을 돕는 착한 커피','2021-10-07 14:49:51.252000',8,0),(9,15000,'<h5>고통 속에 죽어가는 지리산 반달곰</h5>\n<p>UN이 정한 올해 환경의 날 주제는 생물다양성(Biodiversity)입니다. 생물다양성은 종 다양성, 유전자 다양성, 생태계 다양성을 통틀어 이르는 말로 여러 생물이 사는 주변 환경을 함께 보존하는 것을 의미 합니다.<br />SMile은 지리산에 있는 많은 반달곰들이 올무에 걸려 고통 속에 죽어가고 있다는 소식을 듣게 되었습니다. 천연기념물이자 멸종위기야생생물인 반달가슴곰이 안전하게 살 수 있도록 돕고자 쌤베어(SSAMBEAR) 담요 펀딩을 진행하게 되었습니다.</p>\n<h5>페트병을 재활용해 만든 업사이클 담요</h5>\n<p>전 세계에서 한 해 동안 버려지는 플라스틱 페트병은 약 2,000억개 정도이며, 그 중 20% 정도만 재활용되고 있습니다. 나머지 페트병들은 매립되거나 소각되지만 이 때 발생하는 이산화탄소가 기후변화를 가속화 시켰다는 사실, 알고 계셨나요?<br />쓰임을 다한 페트병을 업사이클한 원단으로 만들어진 쌤베어 담요 한 장은 페트병 약 17개의 폐기를 방지하며 환경오염을 줄였습니다.</p>\n<h5>SMile x SSAMBEAR</h5>\n<p>SM엔터테인먼트의 대표 사회공헌 브랜드인 &lsquo;SMile&rsquo;은 코로나19 시대를 살고 있는 지금 인간과 동식물, 환경의 건강을 고민하며, 아티스트와 함께하는 환경 챌린지 캠페인을 진행했습니다. 또한 자원의 지속가능을 넘어 되살림의 의미를 많은 분들에게 전하고 함께 실천하고자 SSAMBEAR 담요를 제작하게 되었습니다. SM 아티스트와 함께 버려지는 자원도 줄이며 SSAMBEAR 담요로 따뜻한 겨울 보내세요.</p>\n<h5>반달곰을 지키는데 함께 해주세요!</h5>\n<p>쌤베어(SSAMBEAR) 담요 판매 수익금은 반달곰 서식지 복원에 힘써온 단체에 전달되어, 올무제거 활동 및 반달곰과 사람이 함께 살기 좋은 환경을 만드는데 사용됩니다.</p>\n<h5>[리워드 안내]</h5>\n<p>■ 29,000원 이상 참여 시, 쌤베어(SSAMBEAR) 담요 1장<br /><br />- 제조국 : 중국<br />- 소재 : 플란넬(융) 친환경 원단 소재<br />- 사이즈 : 70*100cm<br />- 사용안내<br />&middot; 사이즈는 재는 방법에 따라 약간의 차이가 있을 수 있습니다.<br />&middot; 모니터와 그래픽카드에 따라 실제 색상과 약간의 차이가 있을 수 있습니다.<br />&middot; 본 제품은 신축성이 있는 제품으로 사진과 약간의 차이가 있을 수 있습니다.</p>\n<p>&nbsp;</p>\n<p>출처 : https://happybean.naver.com/crowdFunding/Intro/H000000175464</p>','2021-11-06 09:00:00.000000','지리산 반달곰을 살리는 쌤베어 담요','2021-10-07 14:58:02.455000',6,0);
/*!40000 ALTER TABLE `funding` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:10
