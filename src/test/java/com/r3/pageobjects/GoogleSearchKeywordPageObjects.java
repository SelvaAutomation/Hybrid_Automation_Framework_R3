package com.r3.pageobjects;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.r3.utility.ExtentManager;
import com.r3.utility.HTMLCode;
import com.r3.utility.Screenshot;
import com.r3.webelements.GoogleSearchKeywordPage_WebElements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleSearchKeywordPageObjects {
	WebDriver driver;
	WebDriverWait wait;
	public GoogleSearchKeywordPageObjects(WebDriver TestBaseClassDriver) {
		driver = TestBaseClassDriver;
		PageFactory.initElements(TestBaseClassDriver, this);
	}

	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchTextFiled)
	public WebElement googleSearchTextFiled;
	@FindBy(xpath= GoogleSearchKeywordPage_WebElements.Const_googleSearchResultsList)
	public List<WebElement> googleSearchResultsList;


	public void validateORGPROVInfo_WithPriority1to3(String combinedSearchKeyword_OrgProvPhone,String orgNameKey,
													 String areaCode,String exchangeCode, String lineNumber ) throws IOException {
		try {
			driver.get("https://www.google.com");
			googleSearchTextFiled.sendKeys(combinedSearchKeyword_OrgProvPhone, Keys.ENTER);
			List<WebElement> linkHeaderList = googleSearchResultsList;
			int beforeOrgMatchedNameCount=0;
			for (WebElement webElement : linkHeaderList) {
				String orgNameKeyWithoutSpace = orgNameKey.toLowerCase().replace(" ", "");
				String eachSearchLinkHeaderWithoutSpace = webElement.getText().toLowerCase().replace(" ", "");
				if ((identifyCorrectSearchLinkHeader(orgNameKeyWithoutSpace, eachSearchLinkHeaderWithoutSpace, 40.0) ||
						identifyCorrectSearchLinkHeader(eachSearchLinkHeaderWithoutSpace, orgNameKeyWithoutSpace, 40.0))
						&& eachSearchLinkHeaderWithoutSpace.length() != 0) {
					beforeOrgMatchedNameCount++;
				}
			}
			int afterOrgMatchedNameCount=0;
			for(int i=0; i<linkHeaderList.size(); i++){
				boolean isNeedToCheckOtherSearchLinkHead=false;
				String orgNameKeyWithoutSpace = orgNameKey.toLowerCase().replace(" ", "");
				String eachSearchLinkHeaderWithoutSpace = linkHeaderList.get(i).getText().toLowerCase().replace(" ", "");
				if((identifyCorrectSearchLinkHeader(orgNameKeyWithoutSpace, eachSearchLinkHeaderWithoutSpace, 40.0) ||
						identifyCorrectSearchLinkHeader(eachSearchLinkHeaderWithoutSpace,orgNameKeyWithoutSpace, 40.0))
						&& eachSearchLinkHeaderWithoutSpace.length()!=0){
					afterOrgMatchedNameCount++;
					String Url = driver.findElement(By.xpath(GoogleSearchKeywordPage_WebElements.getORGPROVIDERNameURL(i))).getAttribute("href");
					String webContent=null;
					webContent = HTMLCode.get(Url);

					String phoneFormat1 = areaCode+"-"+exchangeCode+"-"+lineNumber;
					String phoneFormat2 = "("+areaCode+")"+""+exchangeCode+"-"+lineNumber;
					String phoneFormat3 = "("+areaCode+")"+" "+exchangeCode+"-"+lineNumber;
					String phoneFormat4 = "("+areaCode+")"+"-"+exchangeCode+"-"+lineNumber;
					String phoneFormat5 = areaCode+"."+exchangeCode+"."+lineNumber;
					String phoneFormat6 = "("+areaCode+")"+"."+exchangeCode+"."+lineNumber;
					String phoneFormat7 = areaCode+" "+exchangeCode+" "+lineNumber;
					String phoneFormat8 = "+1"+areaCode+""+exchangeCode+""+lineNumber;
					String phoneFormat9 = "+1 "+areaCode+"."+exchangeCode+"."+lineNumber;
					String phoneFormat10 = "+1 "+areaCode+"-"+exchangeCode+"-"+lineNumber;
					String phoneFormat11 = "+"+areaCode+"-"+exchangeCode+"-"+lineNumber;
					String phoneFormat12 = "1-"+areaCode+"-"+exchangeCode+"-"+lineNumber;
					String phoneFormat13 = "+1-"+areaCode+"-"+exchangeCode+"-"+lineNumber;
					int count=0;
					List<String> phoneFormatList = new ArrayList<>(
							Arrays.asList(phoneFormat1,phoneFormat2,phoneFormat3,phoneFormat4,phoneFormat5,
									phoneFormat6,phoneFormat7,phoneFormat8,phoneFormat9,phoneFormat10,phoneFormat11,phoneFormat12,phoneFormat13));
					boolean foundPhoneNumber = false;
					for(String eachPhoneFormat:phoneFormatList) {
						count++;
						if(webContent.contains(eachPhoneFormat)) {
							ExtentManager.getExtentTest().log(Status.PASS,("Phone Number is Matching in R3 excel and Web site "+ Url +" >>> "+eachPhoneFormat ),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							foundPhoneNumber=true;
							break;
						}else if(count==13 && afterOrgMatchedNameCount==beforeOrgMatchedNameCount){
							ExtentManager.getExtentTest().log(Status.FAIL,("Phone Number is NOT found in the web content of web site "+ Url ),
									MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
							isNeedToCheckOtherSearchLinkHead=true;
						}
					}
					if(foundPhoneNumber){
						break;
					} else if (!isNeedToCheckOtherSearchLinkHead && afterOrgMatchedNameCount==beforeOrgMatchedNameCount) {
						break;
					}
				}else if (i==linkHeaderList.size()-1){
					ExtentManager.getExtentTest().log(Status.FAIL,("We are not able to find the ORG Web Site link with this search keyword >> "+ combinedSearchKeyword_OrgProvPhone),
							MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getScreenshot()).build());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean identifyCorrectSearchLinkHeader(String str1, String str2, double requiredMatchPercentage) {
		String normalizedStr1 = str1.replaceAll(" ", "").toLowerCase();
		String normalizedStr2 = str2.replaceAll(" ", "").toLowerCase();
		return containsAtLeastPercentMatch(normalizedStr1, normalizedStr2, requiredMatchPercentage);
	}
	private static boolean containsAtLeastPercentMatch(String str1, String str2, double minMatchPercentage) {
		int minLength = str1.length();
		int substringLength = str2.length();
		for (int i = 0; i <= minLength - substringLength; i++) {
			String sub = str1.substring(i, i + substringLength);
			int commonCharCount = 0;
			for (int j = 0; j < substringLength; j++) {
				if (sub.charAt(j) == str2.charAt(j)) {
					commonCharCount++;
				}
			}
			double matchPercentage = (commonCharCount * 100.0) / substringLength;
			if (matchPercentage >= minMatchPercentage) {
				return true;
			}
		}
		return false;
	}
}






























