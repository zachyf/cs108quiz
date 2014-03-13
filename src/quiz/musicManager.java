package quiz;

import java.util.ArrayList;
import java.util.Random;



public class musicManager {
	private ArrayList<String> songs;
	
	public musicManager(){
		songs=new ArrayList<String>();
		
		songs.add("Flume.mp3");
		songs.add("Blindsided.mp3");
		songs.add("OctopusGarden.mp3");
		songs.add("Memphis.mp3");
		songs.add("Caribbean.mp3");
		songs.add("Heartbreak.mp3");
		
	}
	
	public String getRandomSong(){
		Random generator = new Random();
		int i = generator.nextInt(songs.size());
		return songs.get(i);
	}
}
