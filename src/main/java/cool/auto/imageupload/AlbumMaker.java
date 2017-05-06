package cool.auto.imageupload;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.io.Files;

import cool.auto.imageupload.properties.AppProperties;

public class AlbumMaker
{
	private static final String PATH_SEPRATOR = "\\";
	Logger log = Logger.getLogger(AlbumMaker.class);
	AppProperties appProps = new AppProperties();
	
	public void createAlbum(String originalPath)
	{
		File sourceDir = new File(originalPath);
		File destDir = new File(originalPath + appProps.getImageUploadText());
		
		if (destDir.exists())
		{
			return;
		}

		destDir.mkdirs();
		
		List <File> subDirs = listSubDirectories(sourceDir);
		log.info("List of Directories to process : ");
		
		for(File dir : subDirs)
		{
			log.info(dir.toString());
		}
		
		for(File dir : subDirs)
		{
			processDir(dir, sourceDir, destDir);
		}
	}
	
	private void processDir(File sourceDir, File sourceBaseDir, File destBaseDir)
	{
		log.info("Processing Dir : " + sourceDir);
		
		String destDirName = sourceDir.getAbsolutePath().substring(sourceBaseDir.getAbsolutePath().length());
		File destDir = new File(destBaseDir.toString() + destDirName);
		if(!destDir.exists())
		{
			destDir.mkdirs();
		}
		
		File [] jpegFiles = sourceDir.listFiles(new FilenameFilter()
		{
			public boolean accept(File file, String fileName)
			{
				
				String exts[] = appProps.getSupportedExt().split("\\|");
				
				for(String ext : exts)
				{
					if(fileName.toLowerCase().endsWith(ext))
					{
						return true;
					}
				}
				
				return false;
			}
		});

		long totalFileLength = 0;
		long allowableFileLength = 1024 * 1024 * 200;
		String albumTxt = appProps.getAlbumText();
		int albumCtr = 0;
		
		for(File jpegFile : jpegFiles)
		{
			totalFileLength += jpegFile.length();
			
			if(totalFileLength >= allowableFileLength)
			{
				albumCtr++;
				totalFileLength = jpegFile.length();
			}

			String destAlbumStr = destDir.getAbsolutePath() + PATH_SEPRATOR + albumTxt + albumCtr;
			File destAlbum = new File(destAlbumStr);
			destAlbum.mkdirs();
			
			String destFileStr = destAlbumStr+ PATH_SEPRATOR + jpegFile.getName();
			File destFile = new File (destFileStr);
			
			log.info("Copying File : " + jpegFile + " -> " + destFile);
			
			try
			{

				Files.copy(jpegFile, destFile);
			}
			catch (IOException e)
			{
				log.error("Error while copying file.", e);
			}
		}
	}

	private List <File> listSubDirectories(File sourceDir)
	{
		final List <File> listSourceDirs  = new ArrayList<File>();
		File[] subDirs = sourceDir.listFiles();
		
		for(File file : subDirs)
		{
			if(file.isDirectory())
			{
				listSourceDirs.add(file);
				listSourceDirs.addAll(listSubDirectories(file));
			}
		}
		
		return listSourceDirs;
	}
}
