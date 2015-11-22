package balloonadventure.world;

public enum CloudType {
	// typ chmurki, szerokosc, wysokosc
	SPIKY(64, 32),
	YUMMYSTRATUS(64, 32),
	;
	
	public final int width;
	public final int height;
	
	private CloudType(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
