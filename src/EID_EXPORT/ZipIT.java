package EID_EXPORT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipIT {

	public void ZipITNow(String zipfilename,ArrayList<String> fileToZip)
    {
    	byte[] buffer = new byte[1024];

    	try{

    		FileOutputStream fos = new FileOutputStream(zipfilename);
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		
    		
    		for (String s: fileToZip) {     
    			System.out.println(s);
    			ZipEntry ze= new ZipEntry(new File(s).getName());
    			zos.putNextEntry(ze);
    			FileInputStream in = new FileInputStream(s);
    			
    			int len;
        		while ((len = in.read(buffer)) > 0) {
        			zos.write(buffer, 0, len);
        		}

        		in.close();
        		
    		}
    		
    		zos.closeEntry();
    		//remember close it
    		zos.close();

    		System.out.println("Done");

    	} catch(IOException ex){
    	   ex.printStackTrace();
    	}
    }
}
