/**
 * 
 */
package generic.processing.framework.subsystem.fileprocessing.springintegration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * This represents the file processor that spring integration will
 * hook into if it needs to process a file for any reason.
 * 
 * @author UP779462
 *
 */
public class FileProcessor {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(FileProcessor.class);
	
	/**
	 * Actual Spring Integration transformation handler.
	 *
	 * @param inputMessage Spring Integration input message
	 * @return New Spring Integration message with updated headers
	 */
	@Transformer
	public Message<String>handleFile(final Message<File> inputMessage) {

		final File inputFile = inputMessage.getPayload();
		final String filename = inputFile.getName();
		final String fileExtension = FilenameUtils.getExtension(filename);

		final String inputAsString;
		
		logger.info("Handling file : " + filename);

		try {
			inputAsString = FileUtils.readFileToString(inputFile);
		} catch (IOException e) {
			logger.info("File [" + filename + "] has an exception [" + e.getMessage() + "]"); 
			throw new IllegalStateException(e);
		}

		final Message<String> message = MessageBuilder.withPayload(inputAsString.toUpperCase(Locale.ENGLISH))
					.setHeader(FileHeaders.FILENAME,      filename)
					.setHeader(FileHeaders.ORIGINAL_FILE, inputFile)
					.setHeader("file_size", inputFile.length())
					.setHeader("file_extension", fileExtension)
					.build();

		return message;
	}	

}
