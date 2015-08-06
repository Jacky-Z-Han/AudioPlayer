package audioPlayer.filesearch;

import java.util.ArrayList;



import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import android.util.Log;

public class AudioSQLUtil implements Runnable {
	private static final String tag="audioPlayer.filesearch/AudioSQLUtil";
	private  Cursor musicCursor=null;
   private static	Context context=null;
    ArrayList<Music> musicList=new ArrayList<Music>();
    private static AudioSQLUtil audioSQLUtil=null;
    public static AudioSQLUtil getInstance(Context context){
    	if(audioSQLUtil==null){
    		AudioSQLUtil.context=context;
    		audioSQLUtil=new AudioSQLUtil(context);
    	}
    	return audioSQLUtil;
    }
	private AudioSQLUtil(){}
	private AudioSQLUtil(Context context){
		AudioSQLUtil.context=context;
	}
private static final Uri tableStr=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
private static final String orderStr=MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
public void run(){
	getAudio();
	getMusicListMethod();
}

public  ArrayList<Music> getMusicListMethod(){
	Log.i("AudioSQLUtil",musicCursor.getCount()+"");
	if(musicCursor==null)return null;
	else{
		for(musicCursor.moveToFirst();!musicCursor.isAfterLast();musicCursor.moveToNext()){
			   Music tempMusic=new Music();
			tempMusic.setPath(musicCursor.getString(1).toString());
			tempMusic.setMusic(musicCursor.getString(0).toString());
			tempMusic.setSinger(musicCursor.getString(2).toString());
			musicList.add(tempMusic);
		}
		return musicList;
	}
}
public   Cursor getAudio(){
	ContentResolver cr=context.getContentResolver();
	String[] strLie={"title",MediaStore.Audio.Media.DATA,"artist"};
	Cursor cursor=cr.query(tableStr, strLie, null, null, orderStr);
	musicCursor=cursor;
	return musicCursor;
}
public String printCursor(Cursor cursor){
	String str="";
	int columnNum=cursor.getColumnCount();
	cursor.moveToFirst();
	System.out.println(columnNum+"");
	Log.i(tag+":columnNum", columnNum+"");
	if(!cursor.isAfterLast()){
		
		for(int i=0;i<columnNum;i++){
			str+=i+":"+cursor.getColumnName(i)+";";
			
		}
	}
	return str;
}
public Cursor getMusicCursor() {
	return musicCursor;
}
public ArrayList<Music> getMusicList() {
	return musicList;
}
}
