package Utils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * This is a utility class created to read the excel test data file before performing the test steps.
 * This class loads excel file from given location and implements basic methods needed to load the data from the inside of the sheet.
 */

public abstract class ExcelReader {

    /**
     * The worksheet to read in Excel file
     */
    public static Sheet worksheet;

    /**
     * The Excel file to read
     */
    public static Workbook workbook = null;

    /**
     * Store the column data
     */
    public static Hashtable<String, Integer> dictionary = new Hashtable<>();

    /**
     * Create a Constructor
     * The method responsible for loading the workbook from the path given as an argument, and loading the sheet
     * will be implemented in this class.
     * The method responsible to load the data from this sheet in only abstract here, needs to be implemented in a class
     * that will extend this one.
     */
    protected ExcelReader(String excelDataLocation) throws IOException, BiffException {
        loadExcelFile(excelDataLocation);
        loadExcelData();
    }

    void loadExcelFile(String excelDataLocation) throws IOException {
        try {
            workbook = Workbook.getWorkbook(new File(excelDataLocation));
            worksheet = workbook.getSheet("Sheet1");
        } catch (IOException | BiffException e) {
            throw new IOException();
        }
    }

    /**
     * Returns the Number of Rows
     */
    public static int rowCount() {
        return worksheet.getRows();
    }

    /**
     * Create Column Dictionary to hold all the Column Names with integers assigned to them.
     * Hashtable is needed to store column names with integers assigned to them.
     * It will be needed to switch from column name to int assigned to it (getColumn method).
     * int column and in row will be used to define the cell from which the content will be read.
     */
    public static void columnDictionary() {
        // Iterate through all the columns in the Excel sheet and store the
        // value in Hashtable
        for (int col = 0; col < worksheet.getColumns(); col++) {
            dictionary.put(readCell(col, 0), col);
        }
    }

    /**
     * Read Column Names
     * Get a column to read from
     * Take an int value associated with key which is column name.
     * This int is needed as a column parameter to ReadCell method
     * @param colName
     * @return value
     */
    public static int getColumn(String colName) {
        try {
            int value;
            value = dictionary.get(colName);
            return value;
        } catch (NullPointerException e) {
            return (0);
        }
    }

    /**
     * Returns the Cell value by taking row and column int values as arguments
     * @param column
     * @param row
     * @return Cell contents
     */
    public static String readCell(int column, int row) {
        return worksheet.getCell(column, row).getContents();
    }

    /**
     * Read the test data from excel file
     * This has to be implemented in another specific class that will be defined to read given data from Excel,
     * using basic methods from ExcelReader class.
     * Only column names, ArrayList names and amount of columns will differ, the way of reading the data will stay the same.
     */

    abstract void loadExcelData();
}