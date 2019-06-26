package com.kh.ti.travelRequest.model.vo;

import java.io.Serializable;

//설계일자 장소 - 선우
public class PlanPlace implements Serializable {
	private int PplaceId;	//장소코드
	private String PplaceTitle;		//장소명
	private String PplaceAddress;	//주소
	private String PplaceLat;	//위도
	private String PplaceLng;	//경도
	private int PdayId;		//설계일자 코드
	
	public PlanPlace() {}

	public PlanPlace(int pplaceId, String pplaceTitle, String pplaceAddress, String pplaceLat, String pplaceLng,
			int pdayId) {
		super();
		PplaceId = pplaceId;
		PplaceTitle = pplaceTitle;
		PplaceAddress = pplaceAddress;
		PplaceLat = pplaceLat;
		PplaceLng = pplaceLng;
		PdayId = pdayId;
	}

	public int getPplaceId() {
		return PplaceId;
	}

	public void setPplaceId(int pplaceId) {
		PplaceId = pplaceId;
	}

	public String getPplaceTitle() {
		return PplaceTitle;
	}

	public void setPplaceTitle(String pplaceTitle) {
		PplaceTitle = pplaceTitle;
	}

	public String getPplaceAddress() {
		return PplaceAddress;
	}

	public void setPplaceAddress(String pplaceAddress) {
		PplaceAddress = pplaceAddress;
	}

	public String getPplaceLat() {
		return PplaceLat;
	}

	public void setPplaceLat(String pplaceLat) {
		PplaceLat = pplaceLat;
	}

	public String getPplaceLng() {
		return PplaceLng;
	}

	public void setPplaceLng(String pplaceLng) {
		PplaceLng = pplaceLng;
	}

	public int getPdayId() {
		return PdayId;
	}

	public void setPdayId(int pdayId) {
		PdayId = pdayId;
	}

	@Override
	public String toString() {
		return "PPlace [PplaceId=" + PplaceId + ", PplaceTitle=" + PplaceTitle + ", PplaceAddress=" + PplaceAddress
				+ ", PplaceLat=" + PplaceLat + ", PplaceLng=" + PplaceLng + ", PdayId=" + PdayId + "]";
	}
}