package audioPlayer.filesearch;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AudioPalyer extends Fragment {
	private final String begin = "开始";
	private final String pause = "暂停";
	ArrayList<Music> musicList = null;
	int position = -1;
	Menu menuQuit;
	// String path,music ,singer;
	Button butBefore, butBegin, butNext;
	TextView songTv, artistTv;
	MediaPlayer mp = MainActivity.getMediaPlayerInstance();
	AudioSQLUtil audioSQLUtil;//
	Music rtMusic;

	void onCreate() {
		audioSQLUtil = AudioSQLUtil.getInstance(this.getActivity()
				.getApplicationContext());
		if (audioSQLUtil.getMusicList().size() == 0)//
			new Thread(audioSQLUtil).start();
		musicList = audioSQLUtil.getMusicList();
		int i = 0;
		while (musicList == null) {
			Log.i("AudioPlayer", i++ + "");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			musicList = audioSQLUtil.getMusicList();
		}
		// 需要替代，数据传递
		songTv.setText(rtMusic.getMusic());
		artistTv.setText(rtMusic.getSinger());
		play(rtMusic.getPath());
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
				if (((Button) v).getText().equals(begin)) {
					rePlay();
				} else {
					playPause();
				}
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

	public void playPause() {
		mp.pause();
		butBegin.setText(begin);
	}

	public void playNext() {

		if (position == -1)
			Toast.makeText(this.getActivity().getApplicationContext(),
					"错误Position", Toast.LENGTH_SHORT).show();
		else if (position >= musicList.size() - 1) {
			position = 0;
			Music music = musicList.get(position);
			player(music);
		} else {
			position += 1;
			Music music = musicList.get(position);
			player(music);
		}
	}

	private void player(Music music2) {
		play(music2.getPath().toString());
		songTv.setText(music2.getMusic().toString());
		artistTv.setText(music2.getSinger().toString());
	}

	public void playForward() {

		if (position == -1)
			Toast.makeText(this.getActivity().getApplicationContext(),
					"错误Position", Toast.LENGTH_SHORT).show();
		else if (position <= 0) {
			position = musicList.size() - 1;
			Music music = musicList.get(position);
			player(music);
		} else {
			position -= 1;
			Music music = musicList.get(position);
			player(music);
		}
	}

	public void rePlay() {

		mp.start();
		butBegin.setText(pause);
	}

	public void play(String path) {
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_audioplayer, container,
				false);
		menuQuit = (Menu) view.findViewById(R.menu.menu_quit);
		butBefore = (Button) view.findViewById(R.id.button1);
		butBegin = (Button) view.findViewById(R.id.button2);
		butNext = (Button) view.findViewById(R.id.button3);
		songTv = (TextView) view.findViewById(R.id.textView1);
		artistTv = (TextView) view.findViewById(R.id.textView2);
		return view;
	}

	// 回调方法喽
	public void getData(Music music, int position) {
		this.rtMusic = music;
		this.position = position;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		onCreate();
	}
}
