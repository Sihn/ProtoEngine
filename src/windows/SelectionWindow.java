package windows;

public class SelectionWindow {
	private String[] values;
	private int currentValue;

	public SelectionWindow(String[] values) {
		this.values = values;
		currentValue = 0;
	}

	public String[] getValues() {
		return values;
	}

	public String getValue() {
		return values[currentValue];
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void previous() {
		currentValue--;
		while (currentValue < 0)
			currentValue += values.length;
	}

	public void next() {
		currentValue++;
		while (currentValue >= values.length)
			currentValue -= values.length;
	}
}
