package com.epps.framework.application.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;


public class FileOperationUtility {

	public static Path create(String fileLocation, MultipartFile file, String name) {
		Path savedPath = null;
		try {
			File directory = new File(fileLocation);
			System.out.println("directory " + directory);
			if (!directory.exists()) {
				// directory.mkdir();
				directory.mkdirs();
				System.out.println("Directory Created");
			}
			//myObj = new File(directory + File.separator + FilenameUtils.getBaseName(file.getOriginalFilename()) + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
			
			byte[] bytes = file.getBytes();
            Path path = Paths.get((directory.getAbsolutePath() + File.separator)  + file.getOriginalFilename());
            savedPath = Files.write(path, bytes);
            System.out.println("Saved Path   " + savedPath);
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return savedPath; 
	}

	public static String getFileExtension(File file) {
		if (file.exists()) {
			String fileName = file.getName();
			if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
				return fileName.substring(fileName.lastIndexOf(".") + 1);
			}
		}
		return null;
	}

	/*
	 * public <T> void writeDataIntoJsonFile(WebSrvcResponseDTO<T> webSrvcData, File
	 * file) throws IOException, JsonGenerationException, JsonMappingException {
	 * ObjectMapper mapper = new ObjectMapper(); mapper.writeValue(file,
	 * webSrvcData); }
	 * 
	 * public <T> void writeDataIntoXMLFile(WebSrvcResponseDTO<T> webSrvcData, File
	 * file)throws JAXBException, PropertyException { JAXBContext jaxbContext =
	 * JAXBContext.newInstance(WebSrvcResponseDTO.class); Marshaller jaxbMarshaller
	 * = jaxbContext.createMarshaller();
	 * jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 * jaxbMarshaller.marshal(webSrvcData, file); }
	 * 
	 * 
	 * 
	 */

}
