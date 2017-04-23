package cool.auto.imageupload;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

public class App4
{

	public static void main(String[] args) throws IOException
	{
		String sourceDirStr = "C:\\Users\\kulee\\Downloads\\IDAR - POLO";
		File sourceDir = new File(sourceDirStr);
		File destDir = new File(sourceDirStr + "-imageupload");

		if (destDir.exists())
		{
			return;
		}

		destDir.mkdirs();
		
		List <File> subDirs = listSubDirectories(sourceDir);
		
		for(File file : subDirs)
		{
			processDir(file, sourceDir, destDir);
		}
		
		
	}

	private static void processDir(File sourceDir, File sourceBaseDir, File destBaseDir) throws IOException
	{
		String destDirName = sourceDir.getAbsolutePath().substring(sourceBaseDir.getAbsolutePath().length());
		File destDir = new File(destBaseDir.toString() + destDirName);
		if(!destDir.exists())
		{
			destDir.mkdirs();
		}
		
		File [] jpegFiles = sourceDir.listFiles(new FilenameFilter()
		{
			public boolean accept(File arg0, String arg1)
			{
				String fileName = arg1.toLowerCase(); 
				return fileName.endsWith(".jpeg") || fileName.endsWith("jpg");
			}
		});

		long totalFileLength = 0;
		long allowableFileLength = 1024 * 1024 * 200;
		String albumStr = "album";
		int albumCtr = 0;
		
		for(File jpegFile : jpegFiles)
		{
			totalFileLength += jpegFile.length();
			
			if(totalFileLength >= allowableFileLength)
			{
				albumCtr++;
				totalFileLength = jpegFile.length();
			}

			String destAlbumStr = destDir.getAbsolutePath() + "\\" + albumStr + albumCtr;
			File destAlbum = new File(destAlbumStr);
			destAlbum.mkdirs();
			
			String destFileStr = destAlbumStr+ "\\" + jpegFile.getName();
			File destFile = new File (destFileStr);
			
			System.out.println("Copying File : " + " : " + jpegFile + " -> " + destFile);
			
			Files.copy(jpegFile, destFile);
		}
	}

	private static List <File> listSubDirectories(File sourceDir) throws IOException
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
