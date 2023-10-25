package com.r3.providerUtility;

import com.aventstack.extentreports.Status;
import com.r3.pageobjects.GoogleSearchKeywordPageObjects;
import com.r3.utility.ExtentManager;
import com.r3.utility.R3ExcelReader;
import com.r3.utility.TestBaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class R3Phone extends TestBaseClass {
    WebDriver driver;
    List<Map<String, Map<String,String>>> OrgProvList;
    R3ExcelReader objR3ExcelReader;
    public R3Phone(WebDriver TestBaseClassDriver) throws IOException {
        driver = TestBaseClassDriver;
        PageFactory.initElements(TestBaseClassDriver, this);
        objR3ExcelReader = new R3ExcelReader();
    }
    public void Verify_Accurate_Bucket_ORG_PROV_Phone(int executedR3ExcelRowCount, String priority) throws IOException {
        OrgProvList = objR3ExcelReader.excelDataToList(executedR3ExcelRowCount);
        for(Map<String,Map<String,String>> eachOrgList:OrgProvList){
            for(Map.Entry<String,Map<String,String>> eachOrgMapEntry:eachOrgList.entrySet()){
                String orgNameKey = eachOrgMapEntry.getKey();
                Map<String,String> provDetail = eachOrgMapEntry.getValue();
                String phoneBasicFormat = provDetail.get("Phone");
                String areaCode = phoneBasicFormat.substring(0,3);
                String exchangeCode = phoneBasicFormat.substring(3,6);
                String lineNumber = phoneBasicFormat.substring(6);
                if(provDetail.get("Organization_Phone_Validation").contains("Accurate") && provDetail.get("Provider_Phone_Validation").contains("Accurate")) {
                    if(Integer.parseInt(provDetail.get("Phone_Validation_Priority"))>=1 && Integer.parseInt(provDetail.get("Phone_Validation_Priority"))<=3 && priority.equalsIgnoreCase("1 to 3")){
                        ExtentManager.getExtentTest().log(Status.INFO,""+eachOrgList);
                        String combinedSearchKeyword_OrgProvPhone = orgNameKey+" "+provDetail.get("First Name")+" "+provDetail.get("Last_Name")+" "+provDetail.get("Credentials")+" "+areaCode+" "+exchangeCode+" "+lineNumber;
                        GoogleSearchKeywordPageObjects objGoogleSearchKeywordPageObjects = new GoogleSearchKeywordPageObjects(driver);
                        objGoogleSearchKeywordPageObjects.validateORGPROVInfo_WithPriority1to3(combinedSearchKeyword_OrgProvPhone,orgNameKey,areaCode,exchangeCode,lineNumber);
                        break;
                    }
                    else if(Integer.parseInt(provDetail.get("Phone_Validation_Priority"))>=4 && Integer.parseInt(provDetail.get("Phone_Validation_Priority"))<=6){
                        // ExtentManager.getExtentTest().log(Status.INFO,(""+eachOrgList));

                    }else if(Integer.parseInt(provDetail.get("Phone_Validation_Priority"))>=7 && Integer.parseInt(provDetail.get("Phone_Validation_Priority"))<=9){
                        // ExtentManager.getExtentTest().log(Status.INFO,(""+eachOrgList));

                    }

                }






            }

        }

        driver.quit();
    }
}












