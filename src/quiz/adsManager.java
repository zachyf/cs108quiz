package quiz;

import java.util.ArrayList;
import java.util.Random;

public class adsManager {
	private ArrayList<advertisement> ads;
	
	public class advertisement{
		private String url;
		private String pic;
		public advertisement(String url, String pic){
			this.url=url;
			this.pic=pic;
		}
		public String getPic(){
			return this.pic;
		}
		
		public String getUrl(){
			return this.url;
		}
	}
	
	public adsManager(){
		ads=new ArrayList<advertisement>();
		advertisement a1 = new advertisement("http://www.nike.com/us/en_us/","Nike.jpg");
		advertisement a2 = new advertisement("http://www.mcdonalds.com/us/en/home.html","McD.jpg");
		advertisement a3 = new advertisement("http://www.armani.com/us/shoponline?gclid=CM7RuPjMjr0CFUJqfgod-rIA1w&tp=39281","armani.jpg");
		advertisement a4 = new advertisement("http://www.apple.com","apple.jpg");
		advertisement a5 = new advertisement("http://www.gap.com","Gap.jpg");
		advertisement a6 = new advertisement("http://www.ihop.com","ihop.jpg");
		advertisement a7 = new advertisement("http://www.canon.com","canon.jpg");
		ads.add(a1);
		ads.add(a2);
		ads.add(a3);
		ads.add(a4);
		ads.add(a5);
		ads.add(a6);
		ads.add(a7);
	}
	
	public advertisement getRandomAd(){
		Random generator = new Random();
		int i = generator.nextInt(ads.size());
		return ads.get(i);
	}
}
