package in.sachinshinde.cloudconfig.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIController {
	HashMap<String, String> randomImagesHits = new HashMap<String, String>();
	
	@GetMapping(value="/" , produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageUrl() throws IOException {
		String short_url = null;
		Random rand = new Random();
		Integer randomInt = rand.nextInt(1000);
		
		String BASE_URL = "https://picsum.photos/";
		short_url = BASE_URL + randomInt;
		
	    URL url = new URL(short_url);    
	    
	    final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    urlConnection.setInstanceFollowRedirects(false);
	    final String location = urlConnection.getHeaderField("location");
	    randomImagesHits.put(randomInt.toString(), location);
	    System.out.println("Random ID :"+randomInt);
	    System.out.println("Location :"+location);
	    url = new URL(location);
	    
	    InputStream is = url.openStream();
	    byte[] imageBytes = IOUtils.toByteArray(is);
	    return imageBytes;
	}

	
	@GetMapping(value="/{id}" , produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageById(@PathVariable String id) throws IOException {
		String short_url = null;
		String BASE_URL = "https://picsum.photos/";
		
		if(randomImagesHits.containsKey(id))
			short_url = randomImagesHits.get(id);
		else
			short_url = BASE_URL + id;
		
	    URL url = new URL(short_url);    
	    InputStream is = url.openStream ();
	    byte[] imageBytes = IOUtils.toByteArray(is);
	    return imageBytes;
	    
	}
	
}

