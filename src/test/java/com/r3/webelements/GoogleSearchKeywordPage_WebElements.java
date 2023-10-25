package com.r3.webelements;

import java.util.List;

public class GoogleSearchKeywordPage_WebElements {
	public static final String Const_googleSearchTextFiled ="//textarea[@id='APjFqb']";
	public static final String Const_googleSearchResultsList ="//div[@id='res']//child::a//child::div/div/span";
	public static final String getORGPROVIDERNameURL(int urlIdIndex){
		return "(//div[@id='res']//child::a)["+(urlIdIndex+1)+"]";
	}
}
