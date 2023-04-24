package objects;
import static Controllers.Controller.ControllerEnemy.*;
import static Controllers.Controller.Directions.*;
import java.awt.geom.Rectangle2D;

import Main.Game;
public class Crabby extends Enemy{
//	private Rectangle2D.Float acttackBox;// tạo một vùng bao tấn công cho kẻ thù
		private int acttackBoxOffset;
		
		public Crabby(float x, float y) {
			super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT,CRABBY);
			initHitBox((int)(22),(int) (19));
			initActtackBox();
		}
		private void initActtackBox() {
			acttackBox = new Rectangle2D.Float(x,y,(int)(82*Game.SCALE),(int)(19*Game.SCALE));
			acttackBoxOffset=(int)(Game.SCALE*30);// phần bù của hộp sát thương của kẻ thù giúp ta căn giữa// nếu ta sét một hitbox có chiều rộng là 40 mà chiều rộng nhân vật chỉ là 20 suy ra phần bù của mỗi bên là 10px
			// khi kẻ thù di truyển cũng cần di truyển hitbox theo
		}
		private void updateActtackBox() {
			acttackBox.x=hitBox.x-acttackBoxOffset;
			acttackBox.y=hitBox.y;
		}
		public void update(int[][] levelData,Player player) {
			updateBehavior(levelData,player);
			updateStatusEnemy();
			updateActtackBox();
		}
		private void updateBehavior(int[][] levelData ,Player player) {
			if(firstUpdate)
				firstUpdateCheck(levelData);
				if(inAir) {// nếu kẻ thù đang trong kông khí
					updateInAir(levelData);			
				}
				else {// kẻ thù di chuyển
					switch (status) {
					case IDLE:
						newStatus(RUNNING);
						break;
					case RUNNING:
						if(canSeePlayer(levelData, player)){// nếu nhìn thấy nhân vật thì kẻ thù hướng về nhân vật
							turnTowardsPlayer(player);// bật hướng về nhân vật bằng cách thay đổi hướng
							if(isPlayerCloseForActtack(player))// nếu nhân vật vào phạm vi tấn công của kẻ thù
								newStatus(ACTTACK);
					}
						move(levelData);
						break;	
					case ACTTACK:
						if (statusIndex == 0)// chú ý mỗi lần hoạt ảnh của kẻ thù chạy đến đầu mảng cần phải reset trạng thái đã tấn công về false
							acttackChecked = false;
						if(statusIndex==3 && !acttackChecked)//kiểm tra xem thằng kẻ thù đang ở hoạt ảnh tấn công và đã tấn công chưa 
							//sau đó sẽ xết đến việc hai hộp hitbox đó là hột sát thương và hộp nhân vật có giao nhau không nếu giao nhau thì trừ máu người						
							checkEnemyHit(acttackBox, player);
						break;
					case HIT:
						break;
				}
			}
		}
		public int flipX() {
			if(walkDirection==RIGHT)
				return width;
			else return 0;
		}
		public int flipY() {
			if(walkDirection==RIGHT)
				return -1;
			else return 1;
		}
		public void resetEnemy() {
			hitBox.x=x;
			hitBox.y=y;
			firstUpdate=true;
			currentHealth=maxHealth;
			newStatus(IDLE);
			active=true;
			airSpeed=0;	
		}
}
