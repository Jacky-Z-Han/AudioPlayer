package audioPlayer.filesearch;

import java.util.ArrayList;



import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import android.util.Log;
//工具音乐文件读取，线程工具类
public class AudioSQLUtil implements Runnable {
	//log tag
	private static final String tag="audioPlayer.filesearch/AudioSQLUtil";
	private static  Cursor musicCursor=null;
   private static	Context context=null;//工具类也需要context
 private static   ArrayList<Music> musicList=new ArrayList<Music>();//单例数据结构
    private static AudioSQLUtil audioSQLUtil=null;
    //在fragment中getActivity().getApplicationContext(),有个静态Activity是不是不错？
    public static AudioSQLUtil getInstance(Context context){//工具类单例模式
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
private static final Uri tableStr=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//获取音乐文件table参数
private static final String orderStr=MediaStore.Audio.Media.DEFAULT_SORT_ORDER;//获取音乐文件参数列表orderStr
public void run(){//线程工具类
	getAudio();
	getMusicListMethod();
}
//通过Cursor获取Music列表
public  static ArrayList<Music> getMusicListMethod(){
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
public static  Cursor getAudio(){
	ContentResolver cr=context.getContentResolver();
	String[] strLie={"title",MediaStore.Audio.Media.DATA,"artist"};
	Cursor cursor=cr.query(tableStr, strLie, null, null, orderStr);
	musicCursor=cursor;
	return musicCursor;
}
//输出Cursor，很好的测试工具
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
public static Cursor getMusicCursor() {
	return musicCursor;
}
public static ArrayList<Music> getMusicList() {
	return musicList;
}
}
