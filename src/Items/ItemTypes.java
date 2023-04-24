package Items;

import Main.Game;

public class ItemTypes extends GameItems {

	public ItemTypes(int x, int y, int itemType) {
		super(x, y, itemType);
		doItem=true;
		initHitBox(7, 14);
		xDrawOffset=(int)(3*Game.SCALE);
		yDrawOffset=(int)(2*Game.SCALE);
	}
	public void update() {
		updateStatusItem();
	}
}
