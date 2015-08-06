package audioPlayer.filesearch;



import java.io.Serializable;

import audioPlayer.filesearch.R;
import android.app.Activity;
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
	
	static AudioSQLUtil au;
 EditText tv;
 ListView lv;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(EditText)findViewById(R.id.EditText);
		lv=(ListView)findViewById(R.id.listView);
		au=AudioSQLUtil.getInstance(this);
		new Thread(au).start();//�̲߳���ȫ ,�ļ���ȡ��������Ҫһ�Σ���������Ӧ���Ƕ�Ӧ�и�������
		control();
	}
	private void control() {

		MusicAdapter adapter=new MusicAdapter(this);
	   lv.setAdapter(adapter);
	  
	   lv.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		   TextView tv=(TextView)view.findViewById(R.id.tvPah);
		   TextView tv2=(TextView)view.findViewById(R.id.tvMusic);
		   TextView tv3=(TextView)view.findViewById(R.id.tvSinger);
		//   MusicAdapter adapter=(MusicAdapter)lv.getAdapter();
		   Intent intent=new Intent(MainActivity.this,AudioPalyer.class);
		   intent.putExtra("path", tv.getText());
		   intent.putExtra("music",tv2.getText());
		   intent.putExtra("singer", tv3.getText());
		   intent.putExtra("position", position);
		  MainActivity.this.startActivity(intent);
		   Toast.makeText(MainActivity.this, tv.getText(), Toast.LENGTH_LONG).show();
		//   MediaPlayer mediaPlayer=new MediaPlayer();	 
		}
	});	   
	}
	 class MusicAdapter extends BaseAdapter implements Serializable{
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	//	private static final long id= 1L;
		private Context context;	
       Cursor cursor=au.getMusicCursor();
       public MusicAdapter(){
    	   while(cursor==null){
    	    	try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	cursor=au.getMusicCursor();
    	       }
       }
       public MusicAdapter(Context context){this.context=context;
       while(cursor==null){
       	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	cursor=au.getMusicCursor();
          }}
		@Override
		public int getCount() {	
			return cursor.getCount();
		}

		@Override
		public Object getItem(int position) {
		cursor.moveToFirst();
		cursor.move(position);
			return cursor;
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
		    LayoutInflater inflater=LayoutInflater.from((context!=null)?context:MainActivity.this.getApplicationContext());
		     convertView=inflater.inflate(R.layout.item_music	, parent,false);//不知其所以然
			}
		    TextView tvMusic=(TextView)convertView.findViewById(R.id.tvMusic);
		    TextView tvPath=(TextView)convertView.findViewById(R.id.tvPah);
		    TextView tvSinger=(TextView)convertView.findViewById(R.id.tvSinger);
		    if(position<cursor.getCount()){
		    	Log.i("Adapter.getView", "position="+position);
		    	cursor.moveToFirst();
		    cursor.move(position);
		    }
		    tvMusic.setText(cursor.getString(0));
		    tvPath.setText(cursor.getString(1));
		    tvSinger.setText(cursor.getString(2));
		    convertView.setTag(position);
			return convertView;
		    
		}
		public Cursor getCursor() {
			return cursor;
		}
		public void setCursor(Cursor cursor) {
			this.cursor = cursor;
		}
		
	}
}
