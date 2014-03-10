package quiz;

public class AnimalManager {
	private String animal;
	public AnimalManager(){
		this.animal="Cow";
	}
	public String getAnimal(){
		return this.animal;
	}
	
	public void nextAnimal(){
		if(this.animal.equals("Cow")){
			this.animal="Owl";
		}else if(this.animal.equals("Owl")){
			this.animal="Sheep";
		}else if(this.animal.equals("Sheep")){
			this.animal="Elephant";
		}else{
			this.animal="Cow";
		}
	}

}
