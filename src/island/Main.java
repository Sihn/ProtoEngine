package island;

public class Main {
	public static int getRandomCoordinate() {
		return (int) (Math.random() * 10000);
	};

	public static void main(String[] args) {
		System.out.println(Name.getName(31, 62, 83));
		System.out.println(Name.getName(27, 46, 50));
		System.out.println(Name.getName(74, 95, 18));
		for (int i = 0; i < 5; i++)
			System.out.println(Name.getName(getRandomCoordinate(), getRandomCoordinate(), getRandomCoordinate()));
	}
}
