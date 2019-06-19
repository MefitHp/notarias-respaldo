package victor.utils.firebase;

import java.io.File;
import java.io.InputStream;

import com.google.common.reflect.ClassPath;

/**
 * Hello world!
 *
 */
public class App 
{
	public static InputStream getFile(){
		App app = new App();
		return app.getClass().getResourceAsStream("google-services.json");
	}
	
    public static void main( String[] args )
    {
        
    }
}
