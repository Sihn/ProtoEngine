package level;

import core.tools.ZIndex;

public class TileLayer {
	public enum Position {
		Background(ZIndex.behindEntities), Foreground(ZIndex.inFrontOfEntities);
		int z;

		Position(int z) {
			this.z = z;
		}

		public int getZ() {
			return z;
		}
	}

	public Position position = Position.Background;
	public int line = 0;
	public int column = 0;
	public int animations = 0;
}
