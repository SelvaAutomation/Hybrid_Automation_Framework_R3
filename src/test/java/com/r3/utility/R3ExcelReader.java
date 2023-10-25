package com.r3.utility;

import com.aventstack.extentreports.Status;
import com.r3.datareader.PropertiesFileReader;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class R3ExcelReader extends TestBaseClass {
    FileInputStream fis;
    XSSFWorkbook workbook;
    String FirstNameSearchKeyword;
    String LastNameSearchKeyword;
    String CredentialsSearchKeyword;
    String AddressSearchKeyword;
    String StateSearchKeyword;
    String PhoneSearchKeyword;
    String ProvOrgNameSearchKeyword;
    String PhoneValidationPriority;
    String OrgPhoneValidationResult;
    String ProvPhoneValidationResult;
    String OrgNameSearchKeyword;
    Map<String, String> ProvMap;
    Map<String, Map<String, String>> OrgMap;
    List<Map<String, Map<String,String>>> OrgProvList;

    public List<Map<String, Map<String,String>>> excelDataToList(int executedR3ExcelRowsCount) throws IOException {
        ProvMap  = new LinkedHashMap<>();
        OrgMap  = new LinkedHashMap<>();
        OrgProvList = new ArrayList<>();

        fis = new FileInputStream(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
        workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("R3_Phone");
        for (int i = 0; i <executedR3ExcelRowsCount; i++) {
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++){
                //Fetch the First Name from R3 excel
                try {
                    String FirstNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(FirstNameColHead.equalsIgnoreCase("First Name")){
                        FirstNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("First Name",FirstNameSearchKeyword);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    FirstNameSearchKeyword = "null";
                    ProvMap.put("First Name",FirstNameSearchKeyword);
                    continue;
                }

                //Fetch the Last Name from R3 excel
                try {
                    String LastNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(LastNameColHead.equalsIgnoreCase("Last_Name")){
                        LastNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("Last_Name",LastNameSearchKeyword);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    LastNameSearchKeyword = "null";
                    ProvMap.put("Last_Name",LastNameSearchKeyword);
                    continue;
                }

                //Fetch the Credential from R3 excel
                try {
                    String CredentialsColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(CredentialsColHead.equalsIgnoreCase("Credentials")){
                        CredentialsSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("Credentials",CredentialsSearchKeyword);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    CredentialsSearchKeyword = "null";
                    ProvMap.put("Credentials",CredentialsSearchKeyword);
                    continue;
                }

                //Fetch the Address from R3 excel
                try {
                    String AddressColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(AddressColHead.equalsIgnoreCase("Address")){
                        AddressSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("Address",AddressSearchKeyword);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    AddressSearchKeyword = "null";
                    ProvMap.put("Address",AddressSearchKeyword);
                    continue;
                }

                //Fetch the State from R3 excel
                try {
                    String StateColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(StateColHead.equalsIgnoreCase("State")){
                        StateSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("State",StateSearchKeyword);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    StateSearchKeyword = "null";
                    ProvMap.put("Address",StateSearchKeyword);
                    continue;
                }

                //Fetch the Phone from R3 excel
                try {
                    String PhoneColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(PhoneColHead.equalsIgnoreCase("Phone")){
                        PhoneSearchKeyword = sheet.getRow(i + 1).getCell(j).getRawValue().trim();
                        ProvMap.put("Phone",PhoneSearchKeyword);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    PhoneSearchKeyword = "null";
                    ProvMap.put("Phone",PhoneSearchKeyword);
                    continue;
                }

                //Fetch the Provider_Organization_Name from R3 excel
                try {
                    String ProvOrgNameColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(ProvOrgNameColHead.equalsIgnoreCase("Provider_Organization_Name")){
                        ProvOrgNameSearchKeyword = sheet.getRow(i + 1).getCell(j).toString().trim();;
                        continue;
                    }
                } catch (NullPointerException ignored1) {
                    ProvOrgNameSearchKeyword = "null";
                    //Fetch the Organization_Name from R3 excel
                    try {
                        String OrgNameColHead = sheet.getRow(0).getCell(j-1).toString().trim();
                        if(OrgNameColHead.equalsIgnoreCase("Organization_Name")){
                            OrgNameSearchKeyword = sheet.getRow(i + 1).getCell(j-1).toString().trim();
                            ProvOrgNameSearchKeyword = OrgNameSearchKeyword.split("\\|\\|?")[0];
                            continue;
                        }
                    } catch (NullPointerException ignored2) {
                        ExtentManager.getExtentTest().log(Status.INFO,("Provider_Organization_Name and Organization_Name column values are NULL in the R3 excel"));
                    }
                }

                //Fetch the Phone_Validation_Priority from R3 excel
                try {
                    String PhoneValidationPriorityColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(PhoneValidationPriorityColHead.equalsIgnoreCase("Phone_Validation_Priority")){
                        PhoneValidationPriority = sheet.getRow(i + 1).getCell(j).getRawValue().trim();
                        ProvMap.put("Phone_Validation_Priority",PhoneValidationPriority);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    PhoneValidationPriority = "null";
                    ProvMap.put("Phone_Validation_Priority",PhoneValidationPriority);
                    continue;
                }

                //Fetch the Organization_Phone_Validation from R3 excel
                try {
                    String OrgPhoneValidationColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(OrgPhoneValidationColHead.equalsIgnoreCase("Organization_Phone_Validation")){
                        OrgPhoneValidationResult= sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("Organization_Phone_Validation",OrgPhoneValidationResult);
                        continue;
                    }
                } catch (NullPointerException ignored) {
                    OrgPhoneValidationResult = "null";
                    ProvMap.put("Organization_Phone_Validation",OrgPhoneValidationResult);
                    continue;
                }

                //Fetch the Provider_Phone_Validation from R3 excel
                try {
                    String ProvPhoneValidationColHead = sheet.getRow(0).getCell(j).toString().trim();
                    if(ProvPhoneValidationColHead.equalsIgnoreCase("Provider_Phone_Validation")){
                        ProvPhoneValidationResult= sheet.getRow(i + 1).getCell(j).toString().trim();
                        ProvMap.put("Provider_Phone_Validation",ProvPhoneValidationResult);
                        OrgMap.put(ProvOrgNameSearchKeyword, new LinkedHashMap<>(ProvMap));
                        OrgProvList.add(new LinkedHashMap<>(OrgMap));
                        OrgMap.clear();
                        ProvMap.clear();
                        break;
                    }
                } catch (NullPointerException ignored) {
                    ProvPhoneValidationResult = "null";
                    ProvMap.put("Provider_Phone_Validation",ProvPhoneValidationResult);
                    OrgMap.put(ProvOrgNameSearchKeyword, new LinkedHashMap<>(ProvMap));
                    OrgProvList.add(new LinkedHashMap<>(OrgMap));
                    OrgMap.clear();
                    ProvMap.clear();
                    break;
                }
            }
        }
        //ExtentManager.getExtentTest().log(Status.INFO,("R3 Test report excel details converted as test data list below >>"));
        //ExtentManager.getExtentTest().log(Status.INFO,("" + OrgProvList));
        return OrgProvList;
        /*ObjectMapper objMap = new ObjectMapper();
        String listToJsonFormat = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(OrgProvList);
        ExtentManager.getExtentTest().log(Status.FAIL,("R3 Test report excel details converted as test data JSON below >>"));
        ExtentManager.getExtentTest().log(Status.FAIL,("" + listToJsonFormat));*/
    }

    public void clearList(){
        OrgProvList.clear();
        ExtentManager.getExtentTest().log(Status.INFO,("Cleared the List"));
    }
}













