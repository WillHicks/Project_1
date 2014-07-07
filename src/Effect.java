
public class Effect {
	public String name = "";
	public int intensity = 10;
	public Object value;

	public Effect(String name, int intensity, Object value) {
		this.name = name;
		this.intensity = intensity;
		this.value = value;
	}
	
	public void go(Element elem, int intensity, Object newVal) {
		System.out.println("You should never see this sentence.");
	}
	

}
