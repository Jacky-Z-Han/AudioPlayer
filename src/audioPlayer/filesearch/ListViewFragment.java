package audioPlayer.filesearch;

import java.io.Serializable;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewFragment extends Fragment {
	 EditText tv;
	 ListView lv;
	 //AudioSQLUtil au;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("ListViewFragment.onCreate()", "in");
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("ListViewFragment.onActvtiyCreated()", "in");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i("ListViewFragment.onStart()", "in");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i("ListViewFragment.onResume()", "in");
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		au=AudioSQLUtil.getInstance(getActivity().getApplicationContext());
//		new Thread(au).start();
       Log.i("ListViewFragment.onCreateView", "in");
		View view=inflater.inflate(R.layout.fragment_listview, container);
		tv=(EditText)view.findViewById(R.id.EditText);
	/*	lv=(ListView)view.findViewById(R.id.listView);
		MusicAdapter adapter=new MusicAdapter(getActivity().getApplicationContext());
		   lv.setAdapter(adapter);
		  
		   lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			   TextView tv=(TextView)view.findViewById(R.id.tvPah);
			   TextView tv2=(TextView)view.findViewById(R.id.tvMusic);
			   TextView tv3=(TextView)view.findViewById(R.id.tvSinger);
			//   MusicAdapter adapter=(MusicAdapter)lv.getAdapter();
			   Intent intent=new Intent(container.getContext(),AudioPalyer.class);
			   intent.putExtra("path", tv.getText());
			   intent.putExtra("music",tv2.getText());
			   intent.putExtra("singer", tv3.getText());
			   intent.putExtra("position", position);
//			  MainActivity.this.startActivity(intent);
//			   Toast.makeText(container.getContext(), tv.getText(), Toast.LENGTH_LONG).show();
			//   MediaPlayer mediaPlayer=new MediaPlayer();	 
			}
		});	   */
		   Log.i("ListViewFragment.onCreateView", "out");
		return view;
	}
	 class MusicAdapter extends BaseAdapter implements Serializable{
		 AudioSQLUtil au=AudioSQLUtil.getInstance(getActivity());//
			private static final long serialVersionUID = 1L;
		//	private static final long id= 1L;
			private Context context;	
	       Cursor cursor=au.getMusicCursor();
	       public MusicAdapter(){
	    	   new Thread(au).start();
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
	      
	       public MusicAdapter(Context context){
	    	   new Thread(au).start();
	    	   this.context=context;
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
			    LayoutInflater inflater=LayoutInflater.from((context!=null)?context:ListViewFragment.this.getActivity().getApplicationContext());
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
