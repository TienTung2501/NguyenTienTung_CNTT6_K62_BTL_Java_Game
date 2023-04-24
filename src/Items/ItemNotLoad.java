package Items;
import static Controllers.Controller.ItemsInGame.*;

import Main.Game;
public class ItemNotLoad extends GameItems {

	public ItemNotLoad(int x, int y, int itemType) {
		super(x, y, itemType);
		createHitbox();
	}

	private void createHitbox() {
		if(itemType==BOX) {
			initHitBox(25, 18);
			xDrawOffset=(int)(7*Game.SCALE);
			yDrawOffset=(int)(12*Game.SCALE);
		}
		else {
			initHitBox(23,25);
			xDrawOffset=(int)(8*Game.SCALE);
			yDrawOffset=(int)(5*Game.SCALE);
		}
		
	}
	public void update() {
		updateStatusItem();
	}
	
}
