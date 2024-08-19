package com.tramshedtech.eventmanagement.config;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.Base64;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


public class ExportExcel {
    /**
     * @param sheetName
     * @param column
     * @param data  The data to be exported ( the key of the map is defined as the name of the column, which must be the same as the name of the column in the column).
     * @param response
     */
    public static void exportExcel(String sheetName, List<String> column, List<Map<String,Object>> data, HttpServletRequest request, HttpServletResponse response){
        // Create a workbook
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // Create the sheet
        HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);
        // Table header
        HSSFRow headRow = sheet.createRow(0);
        for (int i = 0; i < column.size(); i++){
            headRow.createCell(i).setCellValue(column.get(i));
        }

        for (int i = 0; i < data.size(); i++) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int x = 0; x < column.size(); x++) {
                dataRow.createCell(x).setCellValue(data.get(i).get(column.get(x)) == null ? "" : data.get(i).get(column.get(x)).toString());
            }
        }

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        try {
            // Get the browser name
            String agent = request.getHeader("user-agent");
            String filename = sheetName + ".xls";
            // Different browsers require special handling of filenames.
            if (agent.contains("Firefox")) { // firefox browser
                filename = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(filename.getBytes("utf-8")) + "?=";
                filename = filename.replaceAll("\r\n", "");
            } else { // IE and other browsers
                filename = URLEncoder.encode(filename, "utf-8");
                filename = filename.replace("+", " ");
            }
            // Push Browser
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO-8859-1"));
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            hssfWorkbook.write(response.getOutputStream());
            hssfWorkbook.close(); // Ensure resources are released
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

