
public class Effect {
	public String name = "";
	public String longDescription = "";
	public int maxIntensity = 10;
	public int minIntensity = 0;

	public Effect(String name) {
		super();
		this.name = name;
	}
	
	public Effect(String name, String longDescription, int maxIntensity,
			int minIntensity) {
		this.name = name;
		this.longDescription = longDescription;
		this.maxIntensity = maxIntensity;
		this.minIntensity = minIntensity;
	}
	
	public void go(Element elem, Object newVal) {
		System.out.println("You should never see this sentence.");
	}
	

}
