package com.sortable.SortableChallenge.Service;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;


@Service
public class BidResultRequestService {
	
	public JSONArray getBidResult() {
		JSONArray bidList = new JSONArray();
		JSONParser parser = new JSONParser();
		try  {
				Object obj = parser.parse(new FileReader("/files/AuctionResult.json"));
				bidList = (JSONArray)obj;
			} catch (Exception e) {
			e.printStackTrace();
			}
		
		return bidList;
		
	}

}
