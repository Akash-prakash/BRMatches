package com.riversand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class KeyWordMatcher {
    static String Excelfilepath = System.getProperty("user.dir") + "\\src\\main\\resources\\KeyWord.xlsx";
    static String jsonfilepath = System.getProperty("user.dir") + "\\src\\main\\resources\\JsonFiles\\JsonFiles.json";
    static JsonObject jsonObject;

    public static void main(String[] args) throws IOException {
        KeyWordMatcher keyWordMatcher = new KeyWordMatcher();
        //System.out.println(keyWordMatcher.readExcel());
        //System.out.println(keyWordMatcher.readJson());
        keyWordMatcher.matches(keyWordMatcher.readExcel(), keyWordMatcher.readJson());
    }


    /*matches the keyword from excel to json*/
    public void matches(List excelkeyword, Map jsonkeyword) {
        int keywordCount = 0;
        String jsonvalue=jsonkeyword.get("csrfToken").toString();
        for(int i=0;i<excelkeyword.size();i++){
            boolean found = false;
            if(!found) {
                if (excelkeyword.get(i).equals(jsonvalue)) {
                    found = true;
                    keywordCount++;
                    System.out.println(excelkeyword.get(i) + " is Found " + keywordCount + " times");
                }
            }
            found=false;

        }
    }

    /* Reading the keyword from the Excel returns the List of data from Excel*/
    public List readExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(Excelfilepath)));
        Sheet firstsheet = workbook.getSheetAt(0);
        List<String> Exceldata = new ArrayList<String>();
        Iterator<Row> iterator = firstsheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        Exceldata.add(cell.getStringCellValue());
                        break;
                }
            }
        }
        return Exceldata;
    }

    /*Reading the json file and converting into hashmap*/
    public Map readJson() throws FileNotFoundException {
        BufferedReader jsonbuffer = new BufferedReader(new FileReader(jsonfilepath));
        Map<String, Object> jsonData = new Gson().fromJson(jsonbuffer, Map.class);
        return jsonData;
    }
}
