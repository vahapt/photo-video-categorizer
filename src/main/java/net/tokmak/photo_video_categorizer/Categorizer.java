package net.tokmak.photo_video_categorizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class Categorizer
{
	private static final List<DateTimeFormatter> fmt = Arrays.asList(

			DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"),

			DateTimeFormatter.ofPattern("yyyy:MM:dd"),

			// Add new format for "Sun Mar 15 16:38:10 EET 2015"
			DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

	);

	public static void main(String[] args)
	{
		// Check if the correct number of arguments are provided
		if (args.length < 2)
		{
			System.out.println("Usage: java YourMainClass <source_directory> <destination_directory>");
			System.out.println(
					"Example: java YourMainClass G:\\Photos_Videos\\WorkDir G:\\Photos_Videos\\OutDir");
			return;
		}

		// Assigning arguments to variables
		String directoryPath = args[0];
		String destinationDirectory = args[1];
		Categorizer categorizer = new Categorizer();
		categorizer.categorizeFilesInDirectory(directoryPath, destinationDirectory);
	}

	public void categorizeFilesInDirectory(String directoryPath, String destinationDirectory)
	{

		try (Stream<Path> paths = Files.walk(Paths.get(directoryPath)))
		{
			paths.filter(Files::isRegularFile).forEach(file ->
			{
				try
				{
					processFile(file.toFile(), new File(destinationDirectory));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void processFile(File file, File destinationDirectory) throws IOException, ImageProcessingException
	{
		Tag tag = findTagByName(file, "Date/Time Original");
		if (tag == null)
		{
			tag = findTagByName(file, "Tag:Date/Time");
		}
		if (tag == null)
		{
			tag = findTagByName(file, "Date Taken");
		}
		if (tag == null)
		{
			tag = findTagByName(file, "Date/Time");
		}
		if (tag == null)
		{
			tag = findTagByName(file, "Creation Time");
		}

		if (tag == null)
		{
			System.err.println("File: " + file.getName() + " - No valid tags found, skipping.");
			return;
		}

		LocalDateTime extractedDateTime = parseDate(tag.getDescription());
		if (extractedDateTime == null)
		{
			System.err.println("File: " + file.getName() + " - No valid date info found, skipping.");
			return;
		}
		String year = new Integer(extractedDateTime.getYear()).toString();
		String year_month_day = extractedDateTime.getYear() + "_"
				+ StringUtils.leftPad(new Integer(extractedDateTime.getMonthValue()).toString(), 2, '0') + "_"
				+ StringUtils.leftPad(new Integer(extractedDateTime.getDayOfMonth()).toString(), 2, '0');
		String subDir = year + File.separator + year_month_day;

		destinationDirectory = new File(destinationDirectory, subDir);
		if (!destinationDirectory.exists())
		{
			boolean success = destinationDirectory.mkdirs();
			if (!success)
			{
				throw new IllegalStateException(
						"Could not create directory " + destinationDirectory.getAbsolutePath() + "!");
			}
		}

		File destinationFile = new File(destinationDirectory, file.getName());
		Files.move(file.toPath(), destinationFile.toPath());

		System.out.println("File " + file.getName() + " -> " + destinationFile);
	}

	/**
	 * This method is handy while debugging and extracting what tags are available within the file
	 * @throws ImageProcessingException If metadata cannot be read
	 * @throws IOException If an OS level problem occurs while reading the file
	 */
	@SuppressWarnings("unused")
	private String dumpTags(File file) throws ImageProcessingException, IOException
	{
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		StringBuilder sb = new StringBuilder();
		for (Directory directory : metadata.getDirectories())
		{
			for (Tag tag : directory.getTags())
			{
				sb.append(directory.getName() + "/" + tag.getTagName() + " -> " + tag.getDescription());
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	private Tag findTagByName(File file, String tagName) throws IOException
	{
		Metadata metadata;
		try
		{
			metadata = ImageMetadataReader.readMetadata(file);
		}
		catch (ImageProcessingException e)
		{
			return null;
		}
		for (Directory directory : metadata.getDirectories())
		{
			for (Tag tag : directory.getTags())
			{
				if (tag.getTagName().equals(tagName))
					return tag;
			}
		}
		return null;
	}

	public static LocalDateTime parseDate(String dateString)
	{
		for (DateTimeFormatter formatter : fmt)
		{
			try
			{
				// Try to parse the date string using the formatter
				LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
				return dateTime;
			}
			catch (DateTimeParseException e)
			{
				// If parsing fails, try the next format
			}
		}
		// None of the formatters succeeded
		return null;
	}
}
