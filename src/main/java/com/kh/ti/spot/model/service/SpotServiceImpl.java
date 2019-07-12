package com.kh.ti.spot.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.ti.spot.model.dao.SpotDao;
import com.kh.ti.spot.model.vo.Likey;
import com.kh.ti.spot.model.vo.SpotFile;
import com.kh.ti.spot.model.vo.SpotList;
import com.kh.ti.spot.model.vo.SpotReviews;
import com.kh.ti.travel.model.vo.City;
import com.kh.ti.travel.model.vo.Country;

@Service
public class SpotServiceImpl implements SpotService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private SpotDao sd;


	//도시 상세보기용 - 세령
	@Override
	public ArrayList<HashMap> selectSpotList(int cityId) {
		return sd.selectSpotList(sqlSession, cityId);
	}
	
	//명소 좋아요 추가용 - 세령
	@Override
	public int insertSpotLikey(Likey likey) {
		return sd.insertSpotLikey(sqlSession, likey);
	}

	//마이페이지 명조 좋아요 조회용 - 세령
	@Override
	public ArrayList<SpotList> selectMyLikeySpotList(int memberId) {
		return sd.selectMyLikeySpotList(sqlSession, memberId);
	}
	
	//-----------------------------

	//여행지의 국가명만 조회 - 세령
	@Override
	public ArrayList<Country> selectCountryList() {
		return sd.selectCountryList(sqlSession);
	}

	//여행지의 도시정보 조회(전체) - 세령
	@Override
	public ArrayList<HashMap> selectCityMap() {
		return sd.selectCityMap(sqlSession);
	}

	//CITY ID로 도시정보 조회 - 세령
	@Override
	public Country selectCountryOneInfo(int cityId) {
		return sd.selectCountryOneInfo(sqlSession, cityId);
	}

	//CITY ID로 도시 사진 조회 - 세령
	@Override
	public ArrayList<SpotFile> selectCityFile(int cityId) {
		return sd.selectCityFile(sqlSession, cityId);
	}

	//spot id로 city 조회 - 세령
	@Override
	public City selectCityOne(int spotId) {
		return sd.selectCityOne(sqlSession, spotId);
	}

	//spot info 조회 - 세령
	@Override
	public SpotList selectSpotListOne(int spotId) {
		return sd.selectSpotListOne(sqlSession, spotId);
	}

	//명소 리뷰 등록 - 세령
	@Override
	public int insertSpotReviews(SpotReviews spotReviews) {
		int result = sd.insertSpotReviews(sqlSession, spotReviews);
		int spotReviewIdCurrval = 0;
		if(result > 0) {
			spotReviewIdCurrval = sd.getSpotReviewIdCurrval(sqlSession);
		}
		return spotReviewIdCurrval;
	}

	//명소 리뷰 조회 - 세령
	@Override
	public ArrayList<HashMap> selectSpotReviews(int spotId) {
		return sd.selectSpotReviews(sqlSession, spotId);
	}

	//명소 사진 조회 - 세령
	@Override
	public ArrayList<SpotFile> selectSpotFile(int spotId) {
		return sd.selectSpotFile(sqlSession, spotId);
	}


}
