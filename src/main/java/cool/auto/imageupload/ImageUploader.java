package cool.auto.imageupload;

public class ImageUploader
{
	public static void start()
	{
		String originalPath = "C:\\Users\\kulee\\Downloads\\IDAR - POLO";
		
		AlbumMaker am = new AlbumMaker();
		am.createAlbum(originalPath);
	}
}
