package Utils;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.util.ArrayList;

public class ExcelTestDataReader extends ExcelReader {

    private String excelDataLocation;

    private ArrayList<String> cardNumber;
    private ArrayList<String> expDate;
    private ArrayList<String> cvc;
    private ArrayList<String> expectedAlertMessage;

    public ExcelTestDataReader(String excelDataLocation) throws IOException, BiffException {
        super(excelDataLocation);
        this.excelDataLocation = excelDataLocation;
    }

    void loadExcelData() {
        columnDictionary();
        cardNumber = new ArrayList<>();
        expDate = new ArrayList<>();
        cvc = new ArrayList<>();
        expectedAlertMessage = new ArrayList<>();

        // Get the data from excel file
        for (int row = 1; row < ExcelTestDataReader.rowCount(); row++) {
            cardNumber.add(ExcelTestDataReader.readCell(ExcelTestDataReader.getColumn("CardNumber"), row));
            expDate.add(ExcelTestDataReader.readCell(ExcelTestDataReader.getColumn("ExpDate"), row));
            cvc.add(ExcelTestDataReader.readCell(ExcelTestDataReader.getColumn("CVC"), row));
            expectedAlertMessage.add(ExcelTestDataReader.readCell(ExcelTestDataReader.getColumn("ExpectedAlertMessage"), row));
        }
    }

    public ArrayList<String> getCardNumber() {
        return cardNumber;
    }

    public ArrayList<String> getExpDate() {
        return expDate;
    }

    public ArrayList<String> getCvc() {
        return cvc;
    }

    public ArrayList<String> getExpectedAlertMessage() {
        return expectedAlertMessage;
    }
}
