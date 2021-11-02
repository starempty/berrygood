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
-- Table structure for table `volunteer`
--

DROP TABLE IF EXISTS `volunteer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `date` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `num` int NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_no` bigint DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `FKis8bgwoqedrg41j6lbumo4nio` (`user_no`),
  CONSTRAINT `FKis8bgwoqedrg41j6lbumo4nio` FOREIGN KEY (`user_no`) REFERENCES `user` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer`
--

LOCK TABLES `volunteer` WRITE;
/*!40000 ALTER TABLE `volunteer` DISABLE KEYS */;
INSERT INTO `volunteer` VALUES (8,'<pre>코로나19(COVID-19) 확산방지를 위해 예방접종을 실시합니다.\n체온측정 및 안내를 도와 주실자원봉사자를 모집합니다. \n\n- 봉사일시 : 10월 8일(12:40 ~ 17:00 )\n- 봉사장소 : 백제체육관(충남 공주시 고마나루길 51-14)\n- 봉사내용 : 백신접종 접종 보조지원 (백신접종 동선안내, 예진표안내, 체온측정 등)\n- 참고사항\n  1. 운동화 착용\n  2. 봉사당일 또는 봉사일전 미열이 있으신 분들께서는 꼭 연락 부탁드립니다. (봉사참여 불가)\n       - 봉사문의 :공주시자원봉사센터 852-1365\n\n - 주의사항\n   - 기침, 발열 등의 증상이 있거나, 기저질환이 있으신분은 신청 불가하오니 양해바랍니다.\n   - 손소독, 마스크 착용 필수 \n\n* 백신 공급이 중단된 경우에 봉사가 취소 될수도 있는점 양해 부탁드리겠습니다.\n* 1365포털에 연락가능한 전화번호로 꼭!! 수정 바랍니다.(번호가 달라서 안내를 못받을 경우 책임지지 않습니다!!)\n* 무단불참 및 당일불참시 별도의 연락없이 사전에 접수한 모든 봉사활동 신청내역이 반려(봉사신청 취소)가 됩니다.<br /><br />출처 : https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do?type=show&amp;progrmRegistNo=2772046</pre>','2021-10-08 21:00:00.000000','코로나19(COVID-19) 예방접종 보조지원(안내 및 체온측정) ',5,NULL,'공공행정',5),(9,'<pre>송파구 가락본동 자원봉사캠프에서 활동가를 모집합니다!\n\n○ 대상 : 가락본동에 거주하는 30세 이상 누구나(*1년 이상 장기활동 가능하신 분)\n○ 장소 : 가락본동 주민센터 자원봉사캠프\n○ 내용 : 자원봉사 안내, 활동처 연계, 프로그램 운영 등\n○ 시간 : 평일 주 1회 이상, 14:00~17:00 *시간조율가능\n○ 문의 : 협력사업팀 안혜진 팀장 02-3431-9414 / 주현경 팀원 02-2202-1362\n\n※신청하시면 유선으로 연락 드리겠습니다.\n※자원봉사캠프 활동가 양성교육은 9월 말 ~ 10월 중순 중 온라인으로 진행 될 예정입니다.<br /><br />출처 : https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do?type=show&amp;progrmRegistNo=2761355</pre>','2021-10-08 23:00:00.000000','가락본동 자원봉사캠프 활동가 모집',1,NULL,'기타',10),(10,'<p>* 본 활동은 비대면으로 진행되며 봉사키트 재료 수령 후 모스액자를 완성하고 다시 세상아이로 기부해주시는 활동입니다.<br /><br />- 활동 시간: 1시간<br />- 참가 대상: 11세 이상~성인<br />- 참가 인원: 회당 50명<br />- 참가비용: 키트 1개당 20,000원(*왕복 배송료 포함)<br />- 키트발송: 예약 신청한 날짜에 배송시작(평일 기준 1~2일 소요)<br /><br />*예약 신청일은 택배 발송일이며 재택봉사니 활동일은 자유롭게 진행하시면 됩니다.<br />*단체의 경우 활동일/ 인원 협의가능하니 세상아이로 연락주시기 바랍니다.<br />- 유선: 02-352-3511, 이메일: sesangi@sesangi.org<br />*봉사시간 실적을 요청하시면 1365자원봉사포털사이트에 등록합니다. 자세한 내용은 하단 \'기타안내\'를 참조해주시기 바랍니다.</p>\n<p>&nbsp;</p>\n<p>출처 : https://booking.naver.com/booking/12/bizes/366992/items/4054162</p>','2021-10-12 19:00:00.000000','친환경 모스액자 봉사키트',50,NULL,'공익사업',7),(11,'<p>Here comes All Heroes!<br />전국 어디든, 개별 자율 코스로 진행되는 비대면 러닝<br /><br />[참여기간] 2021. 11. 06 (토) ~ 11. 09 (화)<br />[참여장소] 전국<br />[참여비용] 1인 33,000원<br />[참여거리]<br />- 풀코스 11.9KM<br />- 하프코스 6KM<br /><br />※거리는 각자 러닝 앱을 통해 측정한 후, SNS에 인증해주세요.<br />※참가비 포함 사항 : 티셔츠 + 완주메달 + 스포츠 방역마스크 + 에너지음료 + 119 히어로즈 기부증 (참가비 중 11,900원은 소방기금으로 기부됩니다.)<br />※공식 기념품은 10월 18일(월)~10월 29일(금) 2주간 신청 순서대로 일괄 배송됩니다.<br />※ 예약신청 후, 원활한 배송을 위해 10월 2일(토) 중 이용완료 처리되며 이후에는 취소/환불 불가합니다.</p>\n<p>&nbsp;</p>\n<p>출처 : https://booking.naver.com/booking/12/bizes/581350/items/4085980</p>','2021-11-06 21:00:00.000000','루게릭병 환우를 위해 산을 타는 6회 산타클라쓰 캠페인',100,NULL,'공익사업',11),(12,'<pre>- 일시 : 2021. 10.07.(목) 오후 15:00~17:00\n- 대상 : 심폐소생술에 관심 많은 청소년 및 대학생, 의료봉사단체 등(학생,대학생) \n- 내용 : 심폐소생술 및 자동심장충격기 사용법 이론 및 실습, 심폐소생술 교육 후 홍보 활동 \n- 접수방법 : 1365 홈페이지 접수 \n- 문의사항 : 02)2091-4507~9\n- 자원봉사시간 2시간 인정 / 교육시간 미 준수시에는 봉사시간이 인정되지 않습니다.교육후 홍보활동에 꼭 참석해야 봉사시간이 인정됩니다. \n- 주의사항 : 봉사신청은 본인 이름으로 하루만 가능합니다. 이 봉사는 한학기에 한번만 인정됩니다.(학교별로 상이할 수 있으시니 각 학교로 확인해주시기 바랍니다) <br /><br />출처 : https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do?type=show&amp;progrmRegistNo=2766575</pre>','2021-10-09 00:00:00.000000','심폐소생술교육 및 홍보 활동',7,NULL,'기타',13),(13,'<pre>청년 환경파수꾼 길라잡이 청년단체와 함께 하는 환경축제 도우미 봉사자 모집\n행사 기간 : 2021년 10월 9일 , 10일\n봉사 시간 : 13시 - 18시(5시간)\n인원 : 1일 5명씩(고등학생 및 대학생)\n장소 : 거창하천환경교육센터 야외광장 및 하천환경교육센터일대\n내용 : 환경축제간 안내 및 도우미 봉사활동\n문의 : 055-940-3904, 3906(거창군자원봉사센터)<br /><br />출처 : https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do?type=show&amp;progrmRegistNo=2766492</pre>','2021-10-09 22:00:00.000000','환경축제 도우미 봉사자 모집',9,NULL,'예체능 진흥',14),(14,'<pre>- 장소 : 도봉구청 지하1층 심폐소생술 교육장(보건소 아님) \n  (단, 비대면 줌 교육으로 진행되니 먼저 교육장에서 실습장비 수령후 반냡과 동시에 이수증 수령 확인과 봉사 점수 부여됩니다. 접수에 제한은 없으나, 장비 수령, 반납을 해야하니 원거리는 부담스러울 수 있으니 참고 바랍니다.)\n- 장비수령 : 교육 하루전 장비 필히 수령, 교육후 반납 (장비수령 및 장비 반납만 대리가능)\n- 일시 : 2021. 10.08.(금) 오전 10:00~12:00\n- 대상 : 심폐소생술에 관심 많은 청소년 및 대학생, 의료봉사단체 등(학생,대학생)\n- 내용 : 심폐소생술 및 자동심장충격기 사용법 이론 및 실습, 심폐소생술 교육 후 홍보 활동 \n- 접수방법 : 1365 홈페이지 접수 \n- 문의사항 : 02)2091-4507~9\n- 자원봉사시간 2시간 인정 / 교육시간 미 준수시에는 봉사시간이 인정되지 않습니다.교육후 홍보활동에 꼭 참석해야 봉사시간이 인정됩니다. \n- 주의사항 : 봉사신청은 본인 이름으로 하루만 가능합니다. 이 봉사는 한학기에 한번만 인정됩니다.(학교별로 상이할 수 있으시니 각 학교로 확인해주시기 바랍니다) <br /><br /><br /></pre>','2021-10-06 21:00:00.000000','(오전)심폐소생술교육 및 홍보 활동',15,NULL,'사회복지 및 보건증진',15),(15,'<pre>●일시: 2021.10.10(일) 오전9시~11시\n●장소: 임동 119안전센터 옆 정자\n●준비물: 마스크 必, 모자 必, 활동하기 편한복장, 마실 물 등등\n●활동내용: EM흙공 던지기, 줍깅 등\n   -.활동내용은 상황에 따라 변경될 수 있음\n●연락처: 류옥분(010-6790-8690)<br /><br />출처 : <a href=\"https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do?type=show&amp;progrmRegistNo=2770089\">https://www.1365.go.kr/vols/1572247904127/partcptn/timeCptn.do?type=show&amp;progrmRegistNo=2770089</a><br />사진 출처 : http://gjarchive.kr/cp01/22889</pre>','2021-10-10 18:00:00.000000','임동 용봉천 환경정화 활동',1,NULL,'환경보전',16);
/*!40000 ALTER TABLE `volunteer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:15
