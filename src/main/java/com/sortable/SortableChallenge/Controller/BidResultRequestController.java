package com.sortable.SortableChallenge.Controller;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sortable.SortableChallenge.Service.BidResultRequestService;

@RestController
public class BidResultRequestController {
	
	@Autowired
	private BidResultRequestService bidResultRequestService;

	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, value="/")
	public ResponseEntity<JSONArray> getAuctionResults() throws JsonParseException, JsonMappingException, IOException {
		JSONArray bidResultData = bidResultRequestService.getBidResult();
		return new ResponseEntity<JSONArray>(bidResultData,HttpStatus.OK);
				
	}

}
