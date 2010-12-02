package net.jss.js.adapters;

import java.io.File;
import java.util.List;

 import net.jss.controller.FrontController;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author Julien Dreux
 * Small wrapper around Apache's Fileupload library. This library is simple 
 * enough so that most of the API is left to the FileItem object.
 * See http://commons.apache.org/fileupload/using.html for more information
 * on how to use the FileItem object.
 */
public class FormHelperAdapter {
	
	
	
	private static FormHelperAdapter instance = new FormHelperAdapter(); 
	
	public static FormHelperAdapter getInstance(){
		if(instance == null)
			instance = new FormHelperAdapter();
		
		
		return instance;
	}
	
	public boolean isMultipartContent(){
		// Check that we have a file upload request
		return ServletFileUpload.isMultipartContent(FrontController.context.getRequest());
	}
	
	public List<FileItem> getFormItems() throws FileUploadException{
		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = upload.parseRequest(FrontController.context.getRequest());
			
		return items;
	}
	
	public File writeItemToFile(FileItem item, String path) throws Exception{
		if(!item.isFormField()){
			File uploadedFile = new File(path);
		    item.write(uploadedFile);
		    return uploadedFile;
		}
		
		return null;
	}
}
