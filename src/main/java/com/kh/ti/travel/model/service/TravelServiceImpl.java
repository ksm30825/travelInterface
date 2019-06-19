package com.kh.ti.travel.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.ti.member.model.vo.Member;
import com.kh.ti.travel.model.dao.TravelDao;
import com.kh.ti.travel.model.vo.Place;
import com.kh.ti.travel.model.vo.SchFile;
import com.kh.ti.travel.model.vo.Tag;
import com.kh.ti.travel.model.vo.Travel;
import com.kh.ti.travel.model.vo.TrvCost;
import com.kh.ti.travel.model.vo.TrvDay;
import com.kh.ti.travel.model.vo.TrvSchedule;

@Service
public class TravelServiceImpl implements TravelService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private TravelDao td;
	
	@Override
	public int insertTravel(Travel trv) {

		int result1 = td.insertTravel(sqlSession, trv);
		int result2 = td.insertTrvCity(sqlSession, trv);
		int result3 = td.insertTrvDay(sqlSession, trv);
		return 0;
	}

	@Override
	public int insertTrvCompany(Travel trv, Member m) {
		return td.insertTrvCompany(sqlSession, trv, m);
	}

	@Override
	public int insertTrvTag(Travel trv, Tag tag) {
		return td.insertTag(sqlSession, trv, tag);
	}

	@Override
	public int insertTrvPlace(Travel trv, Place plc) {
		TrvSchedule sch = null;
		int result2 = td.insertTrvSchedule(sqlSession, sch);
		return 0;
	}

	@Override
	public int insertTrvSchedule(TrvSchedule sch, TrvCost cost, Place plc) {
		int result1 = td.insertTrvSchedule(sqlSession, sch);
		int result2 = td.insertTrvCost(sqlSession, cost);
		int result3 = td.insertTrvPlace(sqlSession, plc);
		return 0;
	}

	@Override
	public int insertTrvCost(TrvDay day, TrvCost cost) {
		return td.insertTrvCost(sqlSession, cost);
	}

	@Override
	public int insertTrvCost(TrvSchedule sch, TrvCost cost) {
		return td.insertTrvCost(sqlSession, cost);
	}

	@Override
	public int updateTrvSchedule(TrvSchedule sch, Place plc) {
		int result1 = td.updateTrvSchedule(sqlSession, sch);
		int result2 = td.insertTrvPlace(sqlSession, plc);
		return 0;
	}
	
	@Override
	public int insertSchFile(SchFile schFile) {
		int result = td.insertSchFile(sqlSession, schFile);
		return 0;
	}

	@Override
	public HashMap selectAllSchList(Travel trv) {
		HashMap schMap = null;
		int day = 0;
		ArrayList schList = td.selectAllSchList(sqlSession, trv, day);
		return schMap;
	}

	@Override
	public HashMap selectSpotList(Travel trv) {
		HashMap spotMap = null;
		int cityId = 0;
		HashMap hmap = td.selectSpotList(sqlSession, trv, cityId);
		return spotMap;
	}

	@Override
	public int updateTravel(Travel trv) {
		int result = td.updateTravel(sqlSession, trv);
		return 0;
	}

	@Override
	public int completeTravel(Travel trv) {
		int result = td.completeTravel(sqlSession, trv);
		return 0;
	}

	@Override
	public int deleteTravel(Travel trv) {
		int result = td.deleteTravel(sqlSession, trv);
		return 0;
	}

	@Override
	public int deleteTrvCity(Travel trv, int cityId) {
		int result = td.deleteTrvCity(sqlSession, trv, cityId);
		return 0;
	}

	@Override
	public int deleteTrvComp(Travel trv, int memberId) {
		int result = td.deleteTrvComp(sqlSession, trv, memberId);
		return 0;
	}

	@Override
	public int deleteTrvTag(Travel trv, int tagId) {
		int result = td.deleteTrvTag(sqlSession, trv, tagId);
		return 0;
	}
	
	@Override
	public int updateTrvCost(TrvCost cost) {
		int result = td.updateTrvCost(sqlSession, cost);
		return 0;
	}

	@Override
	public int deleteTrvCost(TrvCost cost) {
		int result = td.deleteTrvCost(sqlSession, cost);
		return 0;
	}

	@Override
	public int deleteSchFile(SchFile file) {
		int result = td.deleteSchFile(sqlSession, file);
		return 0;
	}

	@Override
	public int deleteTrvSchedule(TrvSchedule sch) {
		int result = td.deleteTrvSchedule(sqlSession, sch);
		return 0;
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}