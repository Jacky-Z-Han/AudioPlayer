package audioPlayer.filesearch;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestMusic extends TestCase {
	 Music music =new Music();
   public void testGetPath(){
	  
	   music.setPath("C://I/Like");
	   assertEquals(music.getPath(), "C://I/Like");
   }
   public void testGetMusic(){
	   music.setMusic("Love");
	   assertEquals(music.getMusic(), "Love");
	  
   }
 public static Test suite() {
	   return new TestSuite(TestMusic.class);
	   }
	

}
