package audioPlayer.filesearch;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AudioPalyer extends Activity {
	private final String begin="开始";
	private final String pause="暂停";
	 ArrayList<Music> musicList=null;
	int position=-1;
	Menu menuQuit;
	String path,music	,singer;
Button butBefore,butBegin,butNext;
TextView songTv,artistTv;
MediaPlayer mp=new MediaPlayer();
AudioSQLUtil audioSQLUtil;//��MainActivity�ж�����ͬһ���ˣ���Ҫ����ģʽ
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		audioSQLUtil=AudioSQLUtil.getInstance(this);
		if(audioSQLUtil.getMusicList().size()==0)//�Դ�Ϊ�����ж���������һ��������
		 new Thread(audioSQLUtil).start();//�߳�
		setContentView(R.layout.activity_audioplayer);
		super.onCreate(savedInstanceState);
		
		musicList=audioSQLUtil.getMusicList();
		int i=0;
	    while(musicList==null){	//�߳�Э��
	    	Log.i("AudioPlayer", i+++"");
	    	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	musicList=audioSQLUtil.getMusicList();
	       }
	    menuQuit=(Menu)findViewById(R.menu.menu_quit);
		butBefore=(Button)findViewById(R.id.button1);
		butBegin=(Button)findViewById(R.id.button2);
		butNext=(Button)findViewById(R.id.button3);
		songTv=(TextView)findViewById(R.id.textView1);
		artistTv=(TextView)findViewById(R.id.textView2);
		Intent getIntent=getIntent();
		path=getIntent.getStringExtra("path");
		music=getIntent.getStringExtra("music");
		singer=getIntent.getStringExtra("singer");
		//adapter=(MusicAdapter)getIntent.getSerializableExtra("adapter");
		position=getIntent.getIntExtra("position", -1);
		songTv.setText(music);
		artistTv.setText(singer);
		play(path);
		control();
		playerControl();
	} 
	private void playerControl() {
		mp.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				playNext();
			}
		});
	}
	private void control() {
		butBegin.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
			if(((Button)v).getText().equals(begin)){
		     rePlay();
			}else{playPause();	}
			}
		});
		butBefore.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
			playForward();
			}
		});
		butNext.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				playNext();	
			}
		});
	}
	public void playPause(){
		mp.pause();
		butBegin.setText(begin);
	}
	public void playNext(){
		
		if(position==-1) Toast.makeText(this, "错误Position", Toast.LENGTH_SHORT).show();
		else if(position>=musicList.size()-1){
		position=0;
		Music music=musicList.get(position);
		player(music);
		}
		else{
			position+=1;
			Music music=musicList.get(position);
			player(music);
		}
	}
	private void player(Music music2) {
		play(music2.getPath().toString());
		songTv.setText(music2.getMusic().toString());
		artistTv.setText(music2.getSinger().toString());	
	}
	public void playForward(){
	
		if(position==-1) Toast.makeText(this, "错误Position", Toast.LENGTH_SHORT).show();
		else if(position<=0){
			position=musicList.size()-1;
			Music music=musicList.get(position);
			player(music);}
		else{
			position-=1;
			Music music=musicList.get(position);
			player(music);}}
	public void rePlay(){
	
		mp.start();
		butBegin.setText(pause);
	}
   public void play(String path){
	   mp.reset();
	   try {
		   mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mp.setDataSource(path);
		mp.prepare();
		mp.start();
	    butBegin.setText(pause);
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		  if (mp != null) {
	            mp.release();
	            mp = null;
		  }
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   getMenuInflater().inflate(R.menu.menu_quit, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
			mp.stop();
			mp.release();
			this.onPause();
			this.finish();
			Intent intent=new Intent(this,MainActivity.class);
			this.startActivity(intent);		
			
		
		return super.onOptionsItemSelected(item);
	}

}
