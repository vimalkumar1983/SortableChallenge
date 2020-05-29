package com.sortable.SortableChallenge.Models;

public class BidResult {

	private String site;
	private String unit;
	private String bidder;
	private float bid;
	
	
	public BidResult(String site, String unit, String bidder, float bid){
		this.site = site;
		this.unit = unit;
		this.bidder = bidder;
		this.bid = bid;
		
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getBidder() {
		return bidder;
	}


	public void setBidder(String bidder) {
		this.bidder = bidder;
	}


	public float getBid() {
		return bid;
	}


	public void setBid(float bid) {
		this.bid = bid;
	}
	
	
}
