package com.sortable.SortableChallenge.Models;

public class Auction {

	
	private String site;
	private String[] units;
	private Bid[] bids;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String[] getUnits() {
		return units;
	}
	public void setUnits(String[] units) {
		this.units = units;
	}
	public Bid[] getBids() {
		return bids;
	}
	public void setBids(Bid[] bids) {
		this.bids = bids;
	}
	
		
}
