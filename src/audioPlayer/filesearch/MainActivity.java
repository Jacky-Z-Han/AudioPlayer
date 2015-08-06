package audioPlayer.filesearch;



import java.io.Serializable;

import audioPlayer.filesearch.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//private final Uri tableStr=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	//private final String orderStr=MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
	


 Fragment fragmentListView;
 ViewGroup layout_fragment;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MainActivity.onCreate()", "in");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		fragmentListView=new ListViewFragment();
		
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
	
}
