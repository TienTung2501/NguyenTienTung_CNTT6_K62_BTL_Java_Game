package Controllers;

import Main.Game;

public class Controller {
	public static final float GRAVITY=0.04f*Game.SCALE;
	public static final int STATUSSPEED=25;
	
	public static class ItemsInGame {

		public static final int RED_LOAD_ITEM = 0;
		public static final int BLUE_LOAD_ITEM = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;

		public static final int RED_ITEM_VALUE = 15;
		public static final int BLUE_ITEM_VALUE = 10;

		public static final int ITEM_WIDTH_DEFAULT = 40;
		public static final int ITEM_HEIGHT_DEFAULT = 30;
		public static final int ITEM_WIDTH = (int) (Game.SCALE * ITEM_WIDTH_DEFAULT);
		public static final int ITEM_HEIGHT = (int) (Game.SCALE * ITEM_HEIGHT_DEFAULT);

		public static final int LOAD_ITEM_WIDTH_DEFAULT = 12;
		public static final int LOAD_ITEM_HEIGHT_DEFAULT = 16;
		public static final int LOAD_ITEM_WIDTH = (int) (Game.SCALE * LOAD_ITEM_WIDTH_DEFAULT);
		public static final int LOAD_ITEM_HEIGHT = (int) (Game.SCALE * LOAD_ITEM_HEIGHT_DEFAULT);

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case RED_ITEM_VALUE, BLUE_ITEM_VALUE:
				return 7;
			case BARREL, BOX:
				return 8;
			}
			return 1;
		}
	}

	
	public static class Gui{
		public static class Cloud	{
			public static final int BIG_CLOUDS_DEFAUL_WIDTH=448;
			public static final int BIG_CLOUDS_DEFAUL_HEIGHT=101;
			public static final int SMALL_CLOUDS_DEFAUL_WIDTH=74;
			public static final int SMALL_CLOUDS_DEFAUL_HEIGHT=24;
			
			public static final int BIG_CLOUDS_WIDTH=(int) (BIG_CLOUDS_DEFAUL_WIDTH*Game.SCALE);
			public static final int BIG_CLOUDS_HEIGHT=(int) (BIG_CLOUDS_DEFAUL_HEIGHT*Game.SCALE);
			public static final int SMALL_CLOUDS_WIDTH=(int) (SMALL_CLOUDS_DEFAUL_WIDTH*Game.SCALE);
			public static final int SMALL_CLOUDS_HEIGHT=(int) (SMALL_CLOUDS_DEFAUL_HEIGHT*Game.SCALE);
			
		}
		public static class Buttons{// khai báo các kích thước của nut trong menu
			public static final int BTN_WIDTH_DEFAULT=140;
			public static final int BTN_HEIGHT_DEFAULT=56;
			public static final int BTN_WIDTH=(int) (BTN_WIDTH_DEFAULT*Game.SCALE);
			public static final int BTN_HEIGHT=(int) (BTN_HEIGHT_DEFAULT*Game.SCALE);
			
		}
		public static class ButtonSound{
			public static final int BTN_SOUND_DEFAULT = 42;
			public static final int BTN_SOUND_SIZE = (int) (BTN_SOUND_DEFAULT*Game.SCALE);
			
		}
		public static class UrmButtons{
			public static final int URM_SOUND_DEFAULT = 56;
			public static final int URM_SOUND_SIZE = (int) (URM_SOUND_DEFAULT*Game.SCALE);
		}
		public static class VolumeButton{
			public static final int VOLUME_WIDTH_DEFAULT = 28;
			public static final int VOLUME_HEIGHT_DEFAULT = 44;
			public static final int VOLUME_WIDTH_SIZE = (int) (VOLUME_WIDTH_DEFAULT*Game.SCALE);
			public static final int VOLUME_HEIGHT_SIZE = (int) (VOLUME_HEIGHT_DEFAULT*Game.SCALE);
			public static final int CONTAINER_VOL_WIDTH_DEFAULT = 215;
			public static final int CONTAINER_VOL_WIDTH_SIZE = (int) (CONTAINER_VOL_WIDTH_DEFAULT*Game.SCALE);
			
		}
	}
	public static class Directions{//hướng
		public static final int LEFT=0;
		public static final int UP=1;
		public static final int RIGHT=2;
		public static final int DOWN=3;
		
	}
	public static class ControllerPlayer{// hành động của nhân vật đây cho dòng hoạt ảnh của nhân vật là dòng bao nhiêu
		public static final int RUNNING=1;
		public static final int IDLE=0;
		public static final int JUMP=2;
		public static final int FALLING=3;
		public static final int HIT=5;
		public static final int DEAD=6;
		public static final int ACTTACK=4;
		public static int GetSpriteAmount(int playerAction) {//số lượng hoạt ảnh của các trạng thái
			switch(playerAction) {// đây là số hoạt ảnh của 1 mảng trạng thái của nhân vật
			case DEAD:
				return 8;
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ACTTACK:
				return 3;
			case FALLING:
			default:
				return 1;
			}
		}
	}
	public static class ControllerEnemy{
		public static final int CRABBY=0;
		public static final int IDLE=0;
		public static final int RUNNING=1;
		public static final int ACTTACK=2;
		public static final int HIT=3;
		public static final int DEAD=4;
		
		public static final int CRABBY_WIDTH_DEFAULT=72;
		public static final int CRABBY_HEIGHT_DEFAULT=32;
		public static final int CRABBY_WIDTH=(int) (Game.SCALE*CRABBY_WIDTH_DEFAULT);
		public static final int CRABBY_HEIGHT=(int) (Game.SCALE*CRABBY_HEIGHT_DEFAULT);
		
		public static final int CRABBY_DRAW_OFFSET_X=(int) (Game.SCALE*26);
		public static final int CRABBY_DRAW_OFFSET_Y=(int) (Game.SCALE*9);

		
		public static int GetSpriteAmount(int enemyType,int enemyStatus) {//loại kẻ thù và số lượng hoạt ảnh của các trạng thái
			switch(enemyType) {
			case CRABBY:
				switch(enemyStatus) {
					case IDLE:
						return 9;
					case RUNNING:
						return 6;
					case ACTTACK:
						return 7;
					case HIT:
						return 4;
					case DEAD:
						return 5;
				}
			} 
			return 0;
		}
		public static int GetMaxHealth(int enemyType) {
			switch(enemyType) {
			case CRABBY:
				return 10;
				default:
					return 0;
			}
		}
		public static int GetEnemyDamage(int enemyType) {
			switch(enemyType) {
			case CRABBY:
				return 50;
				default:
					return 0;
			}
		}
	}
	
}
