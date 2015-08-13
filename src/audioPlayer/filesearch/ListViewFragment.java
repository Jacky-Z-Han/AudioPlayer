package audioPlayer.filesearch;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.SimpleFormatter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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
	 String tag="ListViewFragment";
	 
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
		 //  Log.i("ListViewFragment.onCreateView", "out");
		   show(1);
		   save(1);
		   show(1);
		return view;
	}
	public void show(){
		SharedPreferences sharedPrefer=this.getActivity().getSharedPreferences("save", Activity.MODE_PRIVATE);
		Set<String> set=new HashSet<String>();
		Map<String,?> map=sharedPrefer.getAll();
		set=(Set<String>)map.get("name");
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			Log.i(tag+"Show",(String)iterator.next());	
		}
		Log.i(tag+"Show", map.get("time").toString());
	}
	public void save(){
			SharedPreferences sharedPrefer=this.getActivity().getSharedPreferences("save", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor=sharedPrefer.edit();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=new Date();
			String dateStr=simpleDateFormat.format(date);
			Set<String> set=new HashSet<String>();
			set.add("hzj");set.add("han");set.add("zhen");set.add("jiang");
			editor.putStringSet("name", set);
			editor.putString("time", dateStr);
			editor.commit();
			Map<String,?> map=sharedPrefer.getAll();
			set=(Set<String>)map.get("name");
			Iterator iterator=set.iterator();
			while(iterator.hasNext())
				Log.i(tag+"save",(String)iterator.next());
	}
	public void save(int i){
		FileOutputStream fos=null;
		try {
			fos=this.getActivity().openFileOutput("hzj", Activity.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i(tag, "no file");
		}
		FileDescriptor fd=null;
		try {
	    fd= 	fos.getFD();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter fw=new FileWriter(fd);
		try {
			String str="从根本解决问题，不要着急多学类，类是对象的代表\n题，不要着急多学类，类是对象的代表";
			fw.write(str);
			fw.flush();
			fw.close();
			fos.close();
			Log.i(tag+"save(int),str=",str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	public void show(int i){
		try {
			FileInputStream fis=this.getActivity().openFileInput("hzj");
			FileDescriptor fd=fis.getFD();
		
			FileReader fr=new FileReader(fd);
			BufferedReader bf=new BufferedReader(fr);
			String str="";
			Log.i(tag+"show(int),str=", str);
		while(bf.ready()){
			str+=bf.readLine();
		}
			Log.i(tag+"show(int),str=", str);
			fis.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
