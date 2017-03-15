package EID_EXPORT;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excell {

	public void DumpToExcell(String Path, String filename, ArrayList<MyeID> myeIDArr, InputStream template) throws IOException
	{
		XSSFWorkbook wb = new XSSFWorkbook(template);
	    XSSFSheet sheet = wb.getSheet("ACCESSPOINT - Import personnes");
	    String photoFilename = "";
	    int y = 2;
	    for( MyeID myeID: myeIDArr) {
	    	XSSFRow row = sheet.createRow(y);
	    	y++;
	    	row.createCell(0).setCellValue(myeID.getLastName());
	    	row.createCell(1).setCellValue(myeID.getFirstName());
	    
	    	if(myeID.getGender().equals("MALE")) { 
	    		row.createCell(2).setCellValue("Homme"); 
	    	} else { 
	    		row.createCell(2).setCellValue("Femme");
	    	}
	    	// Date Of Birth
	    	row.createCell(4).setCellValue(myeID.getDob());
	    
	    	// Photo filename
	    	photoFilename = myeID.getLastName() + "_" + myeID.getDob();
	    	row.createCell(6).setCellValue(photoFilename + ".jpg");
	    }
	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream(Path + filename + ".xlsx");
	    wb.write(fileOut);
	    fileOut.close(); 
	    wb.close();
	    // */
	}

}
