package audioPlayer.filesearch;




import audioPlayer.filesearch.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;


public class MainActivity extends Activity {
	//private final Uri tableStr=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	//private final String orderStr=MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
	
private static MediaPlayer mediaPlayer=null;

 Fragment fragmentListView;
 Fragment fragmentPalyer;
 ViewGroup layout_fragment;
 public static MediaPlayer getMediaPlayerInstance(){
	 if(mediaPlayer==null){
		 mediaPlayer=new MediaPlayer();
	 }
	 return mediaPlayer;
 }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MainActivity.onCreate()", "in");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		fragmentListView=new ListViewFragment();
		fragmentPalyer=new AudioPalyer();
		layout_fragment=(ViewGroup)findViewById(R.id.fragmentcontent);
		
		control();
	}
	private void control() {
    //fagement初始化
		FragmentManager fm=getFragmentManager();
		FragmentTransaction fragmentTransaction=fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragmentcontent, fragmentListView);
		fragmentTransaction.commit();
		Log.i("MainActivity.onCreate()", "out");
		//其它

	}
	@Override
	protected void onStart() {
	Log.i("MainActivity.onstart", "in");
		super.onStart();
	}
	@Override
	protected void onResume() {
		Log.i("MainActivity.onResume()", "in");
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   getMenuInflater().inflate(R.menu.menu_quit, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
			
			this.control();		
			
		
		return super.onOptionsItemSelected(item);
	}
}
