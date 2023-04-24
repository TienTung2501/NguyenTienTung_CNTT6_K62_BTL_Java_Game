package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Main.Game;

import static Controllers.Controller.Directions.*;
import static Controllers.Controller.STATUSSPEED;
import static Controllers.Controller.GRAVITY;
import static Controllers.Controller.ControllerPlayer.*;
import static Controllers.LogicChangePos.*;
import static Controllers.Controller.ControllerEnemy.DEAD;
import static Controllers.Controller.ControllerEnemy.HIT;
import static Controllers.Controller.ControllerEnemy.IDLE;
import static Controllers.Controller.ControllerEnemy.ACTTACK;
import static Controllers.Controller.ControllerEnemy.GetEnemyDamage;
import static Controllers.Controller.ControllerEnemy.GetMaxHealth;
import static Controllers.Controller.ControllerEnemy.GetSpriteAmount;;

public class Enemy extends Object{
	// khởi tạo lớp kẻ thù kế thừa từ lớp Object tại sao cần phải dùng lớp trừu tượng vì có thể có nhiều loại kẻ thù
		protected int enemyType;// enemyType là loại kẻ thù
		protected BufferedImage imgEnemy;
		protected BufferedImage[][] statusEnemy;
		protected boolean firstUpdate=true; // biến check kiểm tra update lần đầu khi vào game
//		protected boolean inAir;
//		protected float fallSpeed;
//		protected float walkSpeep=0.35f*Game.SCALE;// tốc độ đi bộ của kẻ thù
		protected int walkDirection=LEFT;// hướng di truyển
		private int distanOffEnemy=(int) (200*Game.SCALE);
		
		protected int enemyTileY;
		protected float acttackDistaince=Game.TILES_SIZE;// khoảng cách để kẻ thù tấn công người chơi
		
		//protected int maxHealth;
		//protected int currenthealth;
		
		protected boolean active=true;// biến check xem kẻ thù có đang hoạt động hay không nếu đã chết rồi thì thôi không cần vẽ nó nữa
		protected boolean acttackChecked;
		public Enemy(float x, float y, int width, int height, int enemyType) {
			super(x, y, width, height);
			this.enemyType=enemyType;
			//this.maxHealth=10;
			//this.currentHealth=maxHealth;
			this.speed=0.3f*Game.SCALE;
			maxHealth=GetMaxHealth(enemyType);
			currentHealth=maxHealth;
		}
		
		protected void updateStatusEnemy() {
			statusTick++;
			if(statusTick>=STATUSSPEED) {
				statusTick=0;
				statusIndex++;
				if(statusIndex>=GetSpriteAmount(enemyType, status)) {
					statusIndex=0;
					switch(status) {
					case ACTTACK,HIT -> status=IDLE;
					case DEAD -> active=false;
					}
				}
			}
		}
		protected void firstUpdateCheck(int[][] levelData) {
			
			if(firstUpdate) {// lúc vào game kẻ thù nếu kẻ thù đang trong không trung thì cần phải cho kẻ thù rơi xuống
				if(!IsEntityOnFloor(hitBox, levelData)) {// nếu kẻ thù đang trong không trung
					inAir=true;// 
					firstUpdate=false;// đã xong phần update vị trí cho kẻ thù đầu game
				}
					
				
			}
		}
		protected void updateInAir(int[][] levelData) {
			if(canMoveHere(hitBox.x, hitBox.y+airSpeed, hitBox.width,hitBox.height, levelData)) {// check xem vị trí theo phương y của kẻ thù tiếp theo có di truyển được không
				hitBox.y+=airSpeed;// nếu di truyển được thì toạ độ của y bằng toạ độ tiếp theo
				airSpeed+= GRAVITY;// tốc độ rơ bằng gia tốc của trọng trường
			}
			else {// nếu như nhân vật đang rơi trên sàn thì phải reset trạng thái của nhân vật trong không khí bằng false tức là nhân vật đang trên sàn
				inAir=false;
				hitBox.y=GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);// lấy vị trí của nhân vật theo trục y
				enemyTileY= (int) (hitBox.y/Game.TILES_SIZE);// sau khi kẻ thù rơi trên sàn rồi thì cần lấy toạ độ theo trục y của ô chứa kẻ thù
			}
		}
		protected void move(int[][] levelData) {// thiết lạp  phương thức di truyển cho kẻ thù
			switch (status) {
			case IDLE:
				status=RUNNING;
				break;
			case RUNNING:
				float xSpeed=0;
				if(walkDirection==LEFT)
					xSpeed=-speed;
				else 
					xSpeed=speed;
				if(canMoveHere(hitBox.x+xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData))
					if(IsFloor(hitBox,xSpeed, levelData)) {
						hitBox.x+=xSpeed;
						return;
					}
				changeWalkDirection();
				break;			
			}
		}
		protected void turnTowardsPlayer(Player player) {// điều chỉnh di truyển của kẻ thù so với người chơi
			if(player.hitBox.x>hitBox.x)// người chơi đã đi ra sau kẻ thù
				walkDirection=RIGHT;
			else // người chơi đi ra trước kẻ thù
				walkDirection=LEFT;
		}
		protected boolean canSeePlayer(int [][] levelData,Player player) {
			int playerTileY = (int) (player.getHitBox().y/Game.TILES_SIZE);//lấy toạ độ Y ô đang chứa nhân vật;
			
			if(playerTileY==enemyTileY) 
				if(isPlayerInRagne(player))// player đang ở đủ gần kẻ thù
					if(IsSightClear(levelData,hitBox,player.hitBox,enemyTileY))// kiểm tra xem giữa nhân vật và kẻ thù có chướng ngoại vật nào không
						return true;
			return false;
		}
		
		private boolean isPlayerInRagne(Player player) {
			int distance= (int) Math.abs(hitBox.x-player.hitBox.x);
			return distance<=acttackDistaince*5;
		}
		
		protected boolean isPlayerCloseForActtack(Player player) {// kiểm tra xem nhân vật có nằm trong phạm vi tấn công của kẻ thù không
			int distance= (int) Math.abs(hitBox.x-player.hitBox.x);
			return distance<=acttackDistaince;
		}
		
		public void hurt(int damage) {// ker thù đang bị tấn công
			currentHealth-=damage;
//			System.out.println("Mau Quái:"+currenthealth);
			if(currentHealth<=0)
				newStatus(DEAD);
			else 
				newStatus(HIT);
			
		}
		
		protected void checkEnemyHit(Rectangle2D.Float acttackBox, Player player) {// kiểm tra quái đánh
			if(acttackBox.intersects(player.hitBox)) 
				player.changeHealth(GetEnemyDamage(enemyType));		
			acttackChecked =true;
				
		}
		protected void newStatus(int enemyStatus) {
			this.status=enemyStatus;
			statusTick=0;
			statusIndex=0;	
		}
		
		

		
		protected void changeWalkDirection() {
			if(walkDirection==LEFT) {
				walkDirection=RIGHT;
			}
			else
				walkDirection=LEFT;
			
		}
		public int getEnemyIndex() {
			return statusIndex;
		}
		public int getEnemyStatus() {
			return status;
		}
		public boolean isAcitve() {
			return active;
		}
}
