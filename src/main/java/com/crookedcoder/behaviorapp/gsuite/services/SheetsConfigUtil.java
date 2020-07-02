package com.crookedcoder.behaviorapp.gsuite.services;

import java.security.GeneralSecurityException;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ColorStyle;
import com.google.api.services.sheets.v4.model.GridData;
import com.google.api.services.sheets.v4.model.GridProperties;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SheetsConfigUtil {

    @Value("${gsuite.credentials.file}")
    private String CREDENTIALS;


    public static Spreadsheet initializeSpreadSheet() throws Exception, GeneralSecurityException {
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties().setTitle("Behavior App"));
        Sheets service = new SheetsService().getSheetsService();
        spreadsheet = service.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
        System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId() + " successfully created.");
        return spreadsheet;
    }

    // I hate this and want it as a map or something.
    /**
     * 
     * @param sheetID
     * @param title
     * @param index
     * @param grid
     * @param colorStyle
     * @return SheetProperties for initializing a new sheet
     */
    public static SheetProperties initializeSheetProperties(int sheetID, 
                                                    String title, 
                                                    int index, 
                                                    GridProperties grid,
                                                    ColorStyle colorStyle) 
    {
        
        return new SheetProperties()
                        .set("sheetId", sheetID)
                        .set("title", title)
                        .set("index", index)
                        .set("hidden", false)
                        .set("tabColorStyle", colorStyle)
                        .set("rightToLeft", false);

    }

    /**
     * 
     * @param rowCount
     * @param colCount
     * @return GridProperties for initializing a new Sheet
     */

    public static GridProperties initializeGridProperties(int rowCount, int colCount) {
        
        return new GridProperties()
                            .set("rowCount", rowCount)
                            .set("columnCount", colCount)
                            .set("frozenRowCount", 0)
                            .set("frozenColumnCount", 0)
                            .set("hideGridlines", false)
                            .set("rowGroupControlAfter", false)
                            .set("columnGroupControlAfter", false);

    }

    /**
     * 
     * @param startRow
     * @param startCol
     * @param rowData
     * @return GridData for sheet updates. Seems like another class would be better fit.
     */
    public static GridData initializeGridData(int startRow, int startCol, RowData rowData) {
        return new GridData()
                        .set("startRow", startRow)
                        .set("startColumn", startCol)
                        .set("rowData", rowData);
    }
}