package com.sortable.SortableChallenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sortable.SortableChallenge.Models.Auction;
import com.sortable.SortableChallenge.Models.Bid;
import com.sortable.SortableChallenge.Models.BidResult;
import com.sortable.SortableChallenge.Models.Bidder;
import com.sortable.SortableChallenge.Models.ConfigFile;
import com.sortable.SortableChallenge.Models.Site;

@SpringBootApplication
public class SortableChallenge {

	public static void main(String[] args) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Site> auctionSites = new ArrayList<Site>();
		ArrayList<Bidder> bidders = new ArrayList<Bidder>();
		ArrayList<Auction> auctionList = new ArrayList<Auction>();
		ArrayList<BidResult> auctionResult = new ArrayList<BidResult>();
		
		try
        {
			ConfigFile configFile = objectMapper.readValue(new File("/files/Config.json"), ConfigFile.class);
			auctionSites = new ArrayList<Site>(Arrays.asList(configFile.getSites()));
			bidders = new ArrayList<Bidder>(Arrays.asList(configFile.getBidders()));
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		try
        {
			auctionList = objectMapper.readValue(new File("/files/Input.json"), new TypeReference<ArrayList<Auction>>(){});
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
	
		
		 for(Auction auctionItem : auctionList) {
			 int siteIndex = -1;
			 
			 for (int i = 0 ; i < auctionSites.size(); i ++) {
				    Site tempSite =	auctionSites.get(i);
				    if (tempSite.getName().equals(auctionItem.getSite().toString())) {
				    	siteIndex = i;
				    }
				}
			 
			 if (siteIndex > -1 ) {
				 	float siteFloor = auctionSites.get(siteIndex).getFloor();
					ArrayList<String> unitList = new ArrayList<String>(Arrays.asList(auctionItem.getUnits()));
					ArrayList<Bid> bidList = new ArrayList<Bid>();
					bidList = new ArrayList<Bid>(Arrays.asList(auctionItem.getBids()));
					Collections.sort(bidList, new sortByUnit());
					float highestBidAmount = 0;
					String highestBidderName = null;
					float bidderAmount = 0;
					String unitName = null;
					String currentUnit = "";
					int bidListIndex = 0;
					for(Bid bid : bidList) 
					{	bidListIndex++;
						if (!currentUnit.equalsIgnoreCase(bid.getUnit().toString())) 
						{		
							if (unitName != null && highestBidderName !=null && bidderAmount != 0) {
								BidResult bidResult = new BidResult(auctionItem.getSite(), unitName, highestBidderName, bidderAmount);
								auctionResult.add(bidResult);
								highestBidderName = null;
								highestBidAmount = 0;
								unitName = null;
							}
							
							if (unitList.contains(bid.getUnit().toString())) 
							{	
								unitName = bid.getUnit().toString();
								currentUnit = bid.getUnit().toString();
								Boolean isBidderAllowed = false;
								for (int i = 0 ; i < auctionSites.size(); i ++) {
								    Site tempSite =	auctionSites.get(i);
								    if (tempSite.getName().equals(auctionItem.getSite().toString())) {
								    	for(int j=0;j < tempSite.getBidders().length ; j++) {
								    		if (tempSite.getBidders()[j].equals(bid.getBidder())) {
								    			isBidderAllowed = true;
								    		}
								    	}
								    }
								}
								if (isBidderAllowed) 
								{
									int bidderIndex = -1;
									for (int i = 0 ; i < bidders.size() ; i ++) {
									    Bidder tempBidder =	bidders.get(i);
									    if (tempBidder.getName().equals(bid.getBidder().toString())) {
									    	bidderIndex = i;
									    }
									}
									float bidderAdjustmentFactor = 0;
									float bidAmount = 0;
									if (bidderIndex >= 0) 
									{	
										bidderAdjustmentFactor = bidders.get(bidderIndex).getAdjustment();
										bidAmount = bid.getBid() * (1 +  bidderAdjustmentFactor);
									}
									else 
									{
										bidAmount = bid.getBid();
									}
									if (bidAmount > siteFloor)
									{
										if (bidAmount > highestBidAmount)
											{
												highestBidAmount = bidAmount;
												bidderAmount = bid.getBid();
												highestBidderName = bid.getBidder();
											}
									}
								}
							}
							if (bidListIndex == bidList.size()) 
							{
								if (unitName != null && highestBidderName !=null && bidderAmount != 0) 
								{
									BidResult bidResult = new BidResult(auctionItem.getSite(), unitName, highestBidderName, bidderAmount);
									auctionResult.add(bidResult);
									highestBidderName = null;
									highestBidAmount = 0;
									unitName = null;
								}
							}
						}
						else {
							Boolean isBidderAllowed = false;
							for (int i = 0 ; i < auctionSites.size(); i ++) {
							    Site tempSite =	auctionSites.get(i);
							    if (tempSite.getName().equals(auctionItem.getSite().toString())) {
							    	for(int j=0;j < tempSite.getBidders().length ; j++) {
							    		if (tempSite.getBidders()[j].equals(bid.getBidder())) {
							    			isBidderAllowed = true;
							    		}
							    	}
							    }
							}
							if (isBidderAllowed) 
							{
								int bidderIndex = -1;
								for (int i = 0 ; i < bidders.size() ; i ++) {
								    Bidder tempBidder =	bidders.get(i);
								    if (tempBidder.getName().equals(bid.getBidder().toString())) {
								    	bidderIndex = i;
								    }
								}
								float bidderAdjustmentFactor = 0;
								float bidAmount = 0;
								if (bidderIndex >= 0) 
								{	
									bidderAdjustmentFactor = bidders.get(bidderIndex).getAdjustment();
									bidAmount = bid.getBid() * (1 +  bidderAdjustmentFactor);
								}
								else 
								{
									bidAmount = bid.getBid();
								}
								if (bidAmount > siteFloor)
								{
									if (bidAmount > highestBidAmount)
										{
											highestBidAmount = bidAmount;
											bidderAmount = bid.getBid();
											highestBidderName = bid.getBidder();
										}
								}
							}
							if (bidListIndex == bidList.size()) 
							{
								if (unitName != null && highestBidderName !=null && bidderAmount != 0) 
								{
									BidResult bidResult = new BidResult(auctionItem.getSite(), unitName, highestBidderName, bidderAmount);
									auctionResult.add(bidResult);
									highestBidderName = null;
									highestBidAmount = 0;
									unitName = null;
									
								}
							}
						
						}
					}
			 	}
			
			}
		 printResulttoFile(auctionResult);
		 SpringApplication.run(SortableChallenge.class, args);
		 
	
	}
			
	@SuppressWarnings("unchecked")
	public static void printResulttoFile(ArrayList<BidResult> auctionResult) {
		
		String finalArray = "";
		String previousSiteName = null;
		String newArray = "[[";
		for(BidResult bid: auctionResult) {
			if (bid.getSite() != previousSiteName) {
				 if (previousSiteName != null)
				 {   newArray += "]";
				 		finalArray += newArray;
				 		newArray = "[";
				 		finalArray+= ",";
				 }
				 
				 JSONObject bidEntry = new JSONObject();
				 bidEntry.put("site", bid.getSite());
				 bidEntry.put("unit", bid.getUnit());
				 bidEntry.put("bidder", bid.getBidder());
				 bidEntry.put("bid", bid.getBid());
				 newArray += bidEntry.toString();
				 previousSiteName = bid.getSite();
			}
			else {
				 newArray +=",";	
			     JSONObject bidEntry = new JSONObject();
				 bidEntry.put("site", bid.getSite());
				 bidEntry.put("unit", bid.getUnit());
				 bidEntry.put("bidder", bid.getBidder());
				 bidEntry.put("bid", bid.getBid());
				 newArray += bidEntry.toString();
			}
			
		 };
		 if (previousSiteName != null)
		 {
			 newArray += "]]";
		 	 finalArray += newArray;
		 }
		 
		 
		 try (FileWriter file = new FileWriter("/files/AuctionResult.json")) {
			 	
			 
			 file.write(finalArray);
	            file.flush();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	}
	
}

class sortByUnit implements Comparator<Bid> 
{ 
  @Override
    public int compare(Bid a, Bid b) 
    { 
		return a.getUnit().compareTo(b.getUnit());
    } 
} 
	



