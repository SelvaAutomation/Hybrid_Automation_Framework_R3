package com.r3.utility;

import com.r3.datareader.PropertiesFileReader;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteR3TestResult {
    FileOutputStream outFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    public void writePhoneValidationPriority(int executingRowIndex, String phoneValidationPriority,
                                             String ProviderAndOrgPhoneValidation, String OrganizationPhoneValidation,
                                             String ProviderPhoneValidation, String Url) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet("R3_Phone");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                try {
                    String columnHead = sheet.getRow(0).getCell(j).toString().trim();
                    //Write the priority to Priority_Type column
                    if (columnHead.equalsIgnoreCase("Priority_Type")) {
                        XSSFCell PriorityTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (PriorityTypeCellValue.getCellType() != null || PriorityTypeCellValue.getCellType() != CellType.BLANK) {
                                PriorityTypeCellValue.setCellValue("");
                                PriorityTypeCellValue.setCellValue(phoneValidationPriority);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(phoneValidationPriority);
                        }
                    }
                    //Write the Provider_and_Org_Phone_Validation value to Provider_and_Org_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Provider_and_Org_Phone_Validation_Type")) {
                        XSSFCell ProviderAndOrgPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (ProviderAndOrgPhoneValidationTypeCellValue.getCellType() != null || ProviderAndOrgPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                ProviderAndOrgPhoneValidationTypeCellValue.setCellValue("");
                                ProviderAndOrgPhoneValidationTypeCellValue.setCellValue(ProviderAndOrgPhoneValidation);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(ProviderAndOrgPhoneValidation);
                        }
                    }
                    //Write the Organization_Phone_Validation value to Organization_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Organization_Phone_Validation_Type")) {
                        XSSFCell OrganizationPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (OrganizationPhoneValidationTypeCellValue.getCellType() != null || OrganizationPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                OrganizationPhoneValidationTypeCellValue.setCellValue("");
                                OrganizationPhoneValidationTypeCellValue.setCellValue(OrganizationPhoneValidation);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(OrganizationPhoneValidation);
                        }
                    }
                    //Write the Provider_Phone_Validation value  to Provider_Phone_Validation_Type column
                    if (columnHead.equalsIgnoreCase("Provider_Phone_Validation_Type")) {
                        XSSFCell ProviderPhoneValidationTypeCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        try {
                            if (ProviderPhoneValidationTypeCellValue.getCellType() != null || ProviderPhoneValidationTypeCellValue.getCellType() != CellType.BLANK) {
                                ProviderPhoneValidationTypeCellValue.setCellValue("");
                                ProviderPhoneValidationTypeCellValue.setCellValue(ProviderPhoneValidation);
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            sheet.getRow(executingRowIndex).getCell(j).setCellValue(ProviderPhoneValidation);
                        }
                    }
                    //Write the currently Executed URL to WebSite_URL column
                    if (columnHead.equalsIgnoreCase("WebSite_URL")) {
                        XSSFCell WebSiteURLCellValue = sheet.getRow(executingRowIndex).getCell(j);
                        boolean oneToThree = Integer.parseInt(phoneValidationPriority)>=1 && Integer.parseInt(phoneValidationPriority)<=3;
                        boolean fourToSix = Integer.parseInt(phoneValidationPriority)>=4 && Integer.parseInt(phoneValidationPriority)<=6;
                        boolean sevenToNine = Integer.parseInt(phoneValidationPriority)>=7 && Integer.parseInt(phoneValidationPriority)<=9;
                        try {
                            if (WebSiteURLCellValue.getCellType() != null || WebSiteURLCellValue.getCellType() != CellType.BLANK) {
                                WebSiteURLCellValue.setCellValue("");
                                if(oneToThree){
                                    WebSiteURLCellValue.setCellValue("ORG_WebSite: "+ Url);
                                }else if(fourToSix){
                                    WebSiteURLCellValue.setCellValue("Aggregated_WebSite: "+ Url);
                                }else if(sevenToNine){
                                    WebSiteURLCellValue.setCellValue("Other_WebSite: "+ Url);
                                }
                                outFile = new FileOutputStream(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
                                workbook.write(outFile);
                                outFile.close();
                            }
                        } catch (NullPointerException e) {
                            sheet.getRow(executingRowIndex).createCell(j);
                            if(oneToThree){
                                sheet.getRow(executingRowIndex).getCell(j).setCellValue(Url==null ? "NO ORG Website was found!!!": Url);
                            }else if(fourToSix){
                                sheet.getRow(executingRowIndex).getCell(j).setCellValue("Aggregated_WebSite: "+ Url);
                            }else if(sevenToNine) {
                                sheet.getRow(executingRowIndex).getCell(j).setCellValue("Other_WebSite: " + Url);
                            }
                            outFile = new FileOutputStream(PropertiesFileReader.getProperty("R3TestReportExcelPath"));
                            workbook.write(outFile);
                            outFile.close();
                        }
                    }
                } catch (Exception e) {

            }
        }
    }
}
// Priority_Type
// Organization_Phone_Validation_Type
// Provider_Phone_Validation_Type
// ORG_Name_Matching_Status
// Provider_Name_Matching_Status
// Phone_Number_Matching_Status
// Test_Result
// Remarks
