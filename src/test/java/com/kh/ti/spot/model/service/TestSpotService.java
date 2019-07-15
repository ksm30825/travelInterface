package com.kh.ti.spot.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.ti.member.model.service.MemberServiceTest;
import com.kh.ti.spot.model.vo.Likey;
import com.kh.ti.spot.model.vo.SpotFile;
import com.kh.ti.spot.model.vo.SpotList;
import com.kh.ti.spot.model.vo.SpotReviews;
import com.kh.ti.travel.model.vo.City;
import com.kh.ti.travel.model.vo.Country;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/config/spot-servlet.xml",
					  "file:src/main/resources/root-context.xml",
					  "file:src/main/webapp/WEB-INF/config/spring-security.xml"})
@WebAppConfiguration
public class TestSpotService {

	private static final Logger log = LoggerFactory.getLogger(MemberServiceTest.class);
	
	@Autowired
	private SpotService ss;
	
	Likey likey;
	SpotReviews spotReviews;
	String continent;
	int countryId;
	
	@Before
	public void setup() {
		likey = new Likey();
		likey.setMemberId(1);
		likey.setSpotId(2);
		
		spotReviews = new SpotReviews();
		spotReviews.setGrade(5);
		spotReviews.setMemberId(20);
		spotReviews.setReviewContent("장관이구요~ 신이주신 선물이네요~");
		spotReviews.setSpotId(1);
		spotReviews.setSpotReviewId(1);
		
		continent = "아시아";
		countryId = 2077456;
		System.out.println("셋팅 완료!");
	}
	
	@Ignore
	@Test
	public void selectSpotListTest() {
		ArrayList<HashMap> spotList = ss.selectSpotList(11);
		log.info("여행시 - 도시 상세보기를 위한 명소 조회 성공! size : " + spotList.size());
		for(int i = 0; i < spotList.size(); i++) {
			log.info(spotList.toString());
		}
	}
	
	@Ignore
	@Test
	public void insertSpotLikey() {
		String msg = ss.insertSpotLikey(likey);
		log.info(msg);
	}
	
//	@Ignore
	@Test
	public void selectMyLikeySpotListTest() {
		ArrayList<HashMap> spotList = ss.selectMyLikeySpotList(20);
		log.info("마이페이지 좋아요 명소 조회 성공!" + spotList.size());
		log.info(spotList.toString());
	}
	
	@Ignore
	@Test
	public void selectCountryListTest() {
		//전체 조회
		ArrayList<Country> countryList = null;
		//countryList = ss.selectCountryList();
		//조건 조회
		countryList = ss.selectCountryList(continent);
		
		log.info("여행지 국가만 조회 성공!" + countryList.size());
		log.info(countryList.toString());
	}
	
	@Ignore
	@Test
	public void selectCityMap() {
		ArrayList<HashMap> cityMap = null;
		//전체조회
		//cityMap = ss.selectCityMap();
		//조건 조회
		cityMap = ss.selectCityMap(continent);
		log.info("여행지 도시 조회 성공!" + cityMap.size());
		log.info(cityMap.toString());
	}
	
	@Ignore
	@Test
	public void selectCountryOneInfoTest() {
		Country country = ss.selectCountryOneInfo(11);
		log.info("cityId로 도시정보 조회 성공!");
		log.info(country.toString());
	}
	
	@Ignore
	@Test
	public void selectCityFileTest() {
		ArrayList<SpotFile> spotFile = ss.selectCityFile(11);
		log.info("도시 파일 조회 성공! spotFile size : " + spotFile.size());
		log.info(spotFile.toString());
	}
	
	@Ignore
	@Test
	public void selectCityOne() {
		City city = ss.selectCityOne(1);
		log.info("spot id로 도시 조회 성공!");
		log.info(city.toString());
	}
	
	@Ignore
	@Test
	public void selectSpotListOneTest() {
		SpotList spotList = ss.selectSpotListOne(1);
		log.info("spot info 조회 성공!");
		log.info(spotList.toString());
	}
	
	@Ignore
	@Test
	public void insertSpotReviewsTest() {
		int result = ss.insertSpotReviews(spotReviews);
		log.info("명소 리뷰 등록에 성공!");
		log.info("현재 시퀀스 : " + result);
	}
	
	@Ignore
	@Test
	public void selectSpotReviews() {
		ArrayList<HashMap> spotReviews = ss.selectSpotReviews(1);
		log.info("명소 리뷰 조회 성공!" + spotReviews.size());
		log.info(spotReviews.toString());
	}
	
	@Ignore
	@Test
	public void selectSpotFileTest() {
		ArrayList<SpotFile> spotFile = ss.selectSpotFile(1);
		log.info("명소 파일 조회 성공!" + spotFile.size());
		log.info(spotFile.toString());
	}
	
	@Ignore
	@Test
	public void selectConditionSpotFromUserTest() {
		ArrayList<HashMap> cityMap = ss.selectConditionSpotFromUser(countryId);
		log.info("여행지 도시 조회 성공!" + cityMap.size());
		log.info(cityMap.toString());
	}
	
	@Ignore
	@Test
	public void updateSpotReviewTest() {
		String msg = ss.updateSpotReview(spotReviews);
		log.info(msg);
	}
	
	@Ignore
	@Test
	public void deleteSpotReviewTest() {
		String msg = ss.deleteSpotReview(2);
		log.info(msg);
	}
	
	@Ignore
	@Test
	public void selectMyLikeyCountryListTest() {
		ArrayList<HashMap> countryList = ss.selectMyLikeyCountryList(20);
		log.info("마이페이지 국가/도시 정보 조회 성공! : " + countryList.size());
		log.info(countryList.toString());
	}
}
