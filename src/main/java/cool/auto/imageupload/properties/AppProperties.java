package cool.auto.imageupload.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import cool.auto.imageupload.security.AESCrypto;

public class AppProperties
{
	private Properties properties;
	
	private Logger log = Logger.getLogger(getClass());
	
	public AppProperties()
	{
		properties = new Properties();

		log.info("Loading properties");
		
		InputStream is = this.getClass().getResourceAsStream("app.properties");
		try
		{
			properties.load(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		log.info("Initializing Crypto");
	}
	
	public String getDecKey()
	{
		return properties.getProperty("DEC-KEY");
	}
	
	public String getShrinkPhotos()
	{
		String shrinkPhotos = null;
		try
		{
			shrinkPhotos = AESCrypto.decrypt(getDecKey(), properties.getProperty("SHRINK-PHOTOS"));
		}
		catch (Exception e)
		{
			log.error("Exception while getting Shrink Photos", e);
		}
		
		return shrinkPhotos;
	}
	
	public String getMyAlbum()
	{
		String myAlbum = null;
		
		try
		{
			myAlbum = AESCrypto.decrypt(getDecKey(), properties.getProperty("MY-ALUBMS"));
		}
		catch (Exception e)
		{
			log.error("Exception while getting Shrink Photos", e);
		}
		
		return myAlbum;
	}
	
	public String getImageUploadPwd()
	{
		String imageUploadPwd = null;
		try
		{
			imageUploadPwd = AESCrypto.decrypt(getDecKey(), properties.getProperty("IMAGE-UPLOAD-PWD"));
		}
		catch (Exception e)
		{
			log.error("Exception while getting Shrink Photos", e);
		}
		
		return imageUploadPwd;
	}
}
