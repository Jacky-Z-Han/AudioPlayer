package audioPlayer.filesearch;




import audioPlayer.filesearch.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

//音乐播放器主类
public class MainActivity extends Activity {
	//private final Uri tableStr=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	//private final String orderStr=MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
	
private static MediaPlayer mediaPlayer=null;//单例模式MediaPlayer
public static MediaPlayer getMediaPlayerInstance(){
	 if(mediaPlayer==null){
		 mediaPlayer=new MediaPlayer();
	 }
	 return mediaPlayer;
}
 Fragment fragmentListView;//播放列表和播放Fragment界面
 Fragment fragmentPalyer;
 ViewGroup layout_fragment;//替换Fragment的容器
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MainActivity.onCreate()", "in");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		fragmentListView=new ListViewFragment();
		fragmentPalyer=new AudioPlayer();
		layout_fragment=(ViewGroup)findViewById(R.id.fragmentcontent);
		
		control();
	}
	//初始化逻辑之Fragment初始化
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
	//加载菜单项<Item>
	public boolean onCreateOptionsMenu(Menu menu) {
	   getMenuInflater().inflate(R.menu.menu_quit, menu);
		return true;
	}
	@Override
	//只有一个菜单项回退到播放列表界面
	public boolean onOptionsItemSelected(MenuItem item) {
			this.control();		
		return super.onOptionsItemSelected(item);
	}
	@Override
	//测试修改回退事件，先onKeyDown后onBackPressed
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Log.i("onBack", "backPressed");
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("onKeyDown", "in");
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			Log.i("onKeyDown", "back");
			break;
		case KeyEvent.KEYCODE_MENU:
			Log.i("onkeyDown", "menu");
			break;
		case KeyEvent.KEYCODE_HOME:
			Log.i("onkeyDown", "home");
			default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
