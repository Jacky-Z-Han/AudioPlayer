package audioPlayer.filesearch;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewFragment extends Fragment {
	 EditText tv;
	 ListView lv;
	 AudioSQLUtil au;
	 ArrayList<Music> musicList;
	 
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		au=AudioSQLUtil.getInstance(getActivity().getApplicationContext());
		new Thread(au).start();
		musicList=au.getMusicList();
       Log.i("ListViewFragment.onCreateView", "in");
		View view=inflater.inflate(R.layout.fragment_listview, container,false);
		tv=(EditText)view.findViewById(R.id.EditText);
		lv=(ListView)view.findViewById(R.id.listView);
		MusicAdapter adapter=new MusicAdapter(getActivity().getApplicationContext());
		   lv.setAdapter(adapter);
		  
		   lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			   TextView tv=(TextView)view.findViewById(R.id.tvPah);
			   TextView tv2=(TextView)view.findViewById(R.id.tvMusic);
			   TextView tv3=(TextView)view.findViewById(R.id.tvSinger);
			   Activity mainAt=ListViewFragment.this.getActivity();
			   FragmentManager fm=mainAt.getFragmentManager();
			   FragmentTransaction ft=fm.beginTransaction();
			   AudioPalyer f2=new AudioPalyer();
			   //这个替代替代谁
			   ft.replace(R.id.fragmentcontent	, f2);
			   ft.commit();
			   Music music=new Music();
			   music.setMusic(tv2.getText().toString());
			   music.setPath(tv.getText().toString());
			   music.setSinger(tv3.getText().toString());
			   
			   f2.getData(music,position);
			  /* Intent intent=new Intent(container.getContext(),AudioPalyer.class);
			   intent.putExtra("path", tv.getText());
			   intent.putExtra("music",tv2.getText());
			   intent.putExtra("singer", tv3.getText());
			   intent.putExtra("position", position);
			  MainActivity.this.startActivity(intent);*/
//			   Toast.makeText(container.getContext(), tv.getText(), Toast.LENGTH_LONG).show();
			//   	 
			}
		});	   
		   //如果输入框为null，就像没有执行事件之前一样
		   tv.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			String str=s.toString();
			Log.i("study","s:start:before:count="+s.toString()+":"+start+":"+before+":"+count);
			ArrayList<Music> tempList=new ArrayList<Music>();
			for(Music music: musicList){
				if(music.getMusic().contains(str)||music.getSinger().contains(str))
					tempList.add(music);
			}
			MusicAdapter tempAdapter=new MusicAdapter();
			tempAdapter.setAdapterMusiclist(tempList);
			ListViewFragment.this.lv.setAdapter(tempAdapter);
			//重新添加ListView adapter
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			
				
			}
		});
		   Log.i("ListViewFragment.onCreateView", "out");
		return view;
	}
	 class MusicAdapter extends BaseAdapter implements Serializable{
		 
		   private ArrayList<Music> adapterMusicList=AudioSQLUtil.getMusicList();
			private static final long serialVersionUID = 1L;//不知道有啥用
			private Context context;	
	    //   Cursor cursor=AudioSQLUtil.getMusicCursor();
	    
	       
	       public MusicAdapter(){
	    	   if(adapterMusicList==null){
	    	   new Thread(au).start();
	    	   while(adapterMusicList==null){
	    	    	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    	adapterMusicList=AudioSQLUtil.getMusicList();
	    	       }
	    	   }
	       }
	      
	       public MusicAdapter(Context context){
	    	  this();
	    	   this.context=context;	     	    
	          }
			@Override
			public int getCount() {	
				return adapterMusicList.size();
			}

			@Override
			public Object getItem(int position) {
			
				return adapterMusicList.get(position);
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
			    Music music=null;
			    if(position<getCount()){
			    	Log.i("Adapter.getView", "position="+position);
			  music=(Music) getItem(position);
			    }
			    if(music!=null){
			    tvMusic.setText(music.getMusic());
			    tvPath.setText(music.getPath());
			    tvSinger.setText(music.getSinger());
			    }
			    convertView.setTag(position);
				return convertView;
			    
			}
		
			public ArrayList<Music> getAdapterMusiclist() {
				return adapterMusicList;
			}

			public void setAdapterMusiclist(ArrayList<Music> adapterMuisclist) {
				this.adapterMusicList = adapterMuisclist;
			}
			
		}
}
