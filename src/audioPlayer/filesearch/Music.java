package audioPlayer.filesearch;

public class Music {
private String path;
private String music;
private String singer;
public Music(){}
public Music(String path,String music,String singer){
	this.path=path;
this.music=music;
this.singer=singer;}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public String getMusic() {
	return music;
}
public void setMusic(String music) {
	this.music = music;
}
public String getSinger() {
	return singer;
}
public void setSinger(String singer) {
	this.singer = singer;
}

}
