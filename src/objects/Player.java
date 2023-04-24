package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Controllers.Map;
import Main.Game;
import Menu.Continue;
import static Controllers.LogicChangePos.*;
import static Controllers.Controller.STATUSSPEED;
import static Controllers.Controller.GRAVITY;
import static Controllers.Controller.ControllerPlayer.*;
public class Player extends Object{
	private BufferedImage imgMainer;
	private BufferedImage[][] statusOfMainer;
	private int statusTick,statusIndex;
	private int playerDirection =-1;
	private boolean moving = false, isActtack=false;
	private boolean left,right,jump;
	private int[][] levelData;
	private float xDrawOffset=21*Game.SCALE,yDrawOffset=4*Game.SCALE;//vi tri bat dau hop moi
	// nhay
	private float jumpSpeed = -2.25f * Game.SCALE;// tốc độ nhảy
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;// tốc độ rơi
	// Thanh máu của nhân vật:
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);// chiều rộng của thanh máu bênh trong
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
//	private int maxHealth=100;
//	private int currentHealth=maxHealth;
	private int healthWidth=healthBarWidth;
	// hộp tấn công của nhân vật:
//	private Rectangle2D.Float acttackBox;// khi người chơi thực hiện nhấn sự kiện tấn công thì chỉ cần một đối tượng nào đó ở trong vùng này sẽ bị dính sát thương
	
	private int flipX=0;// đây là 2 biến giúp xoay chiều nhân vật
	private int flipW=1;// vì lúc lấy hình ảnh trạng thái của nhân vật ta chỉ lấy được một chiều khi dùng phương thức draw thì sẽ vẽ được với chiều đó, để đảo ngược chiều t chỉ cần set thuộc tính width hoặc height âm tuỳ thuộc vào cách ta set
	// khởi tạo nhân vật
	private boolean acttackChecked;
	private Continue continuee;
	public Player(float x,float y,int width,int height,Continue continuee) {
		super(x,y,width,height);// khởi tạo đối tượng
		this.continuee=continuee;
		this.maxHealth=100;
		this.currentHealth=maxHealth;
		this.status=IDLE;
		this.speed= Game.SCALE*1.0f;
		loadStatus();// tải trạng thái của nhân vật
		initHitBox(20, 27);// khởi tạo hộp bao quanh nhân vật để check toạ độ với các thực thể khác trong game phục vụ cho việc di truyển
		initActtackBox();
	}
	public void setSpawn(Point spawn) {
		this.x=spawn.x;
		this.y=spawn.y;
		hitBox.x=x;
		hitBox.y=y;
	}
	private void initActtackBox() {
		acttackBox = new Rectangle2D.Float(x,y,(int)(20*Game.SCALE),(int)(20*Game.SCALE));// mặc định hitbox tấn công trùng toạ độ với người
		
	}

	public void loadStatus() {//tải trạng thái của nhân vật
			BufferedImage imgMainer = Map.GetMap(Map.PLAYER_STATUS);// lấy một ma trận trong đó mỗi mảng 1 chiều chứa các hoạt ảnh của các trạng thái của nhân vật
			statusOfMainer = new BufferedImage[7][8];// khai báo một ma trận để chứa tất cả các hoạt ảnh của nhân vật
			for(int j=0;j<statusOfMainer.length;j++)
				for(int i=0;i<statusOfMainer[j].length;i++){
				statusOfMainer[j][i] = imgMainer.getSubimage(i*64,j* 40,64,40);// lưu hoạt ảnh vào ma trận
				}
			statusBarImg =Map.GetMap(Map.BAR_HEALTH_POWER);
	}
	public void setLevelData(int[][] levelData) {// đánh dấu toạ độ của các hoạt ảnh của map bản đồ
		this.levelData=levelData;
		if (!IsEntityOnFloor(hitBox, levelData))// kiểm tra xem cái ô hiện tại có hoạt ảnh bản đồ nào không nếu không có thì nhân vật đang trong không khí
			inAir = true;
	}
	private void checkActtack() {
		if( acttackChecked || statusIndex!=1)
			return;
		acttackChecked=true;
		continuee.checkEnemyDamage(acttackBox);
		
	}
	public void update() {
		updateHealthBar();
		if(currentHealth<=0) {// khi nhân vật chết thì trạng thái của nhân vật sẽ là dead và hoạt ảnh của nhân vật sẽ được chuyển xuống mảng hoạt ảnh và khung hoạt ảnh ở đầu tiên trong mảng
			if(status!=DEAD) {
				status=DEAD;
				statusTick=0;
				statusIndex=0;
				continuee.setPlayerDying(true);
			}// trong trường hợp khung hoạt ảnh đang chạy đến cuối mảng hoạt ảnh rồi thì mới gọi giao diện gameOver
			else if(statusIndex== GetSpriteAmount(DEAD)-1 && statusTick>= STATUSSPEED-1) {
				continuee.setGameOver(true);
			}
			else {
				updateStatusOfMainer();
			}	
			return;
		}
		
		updateActtackBox();// cập nhật vị trí của hộp tấn công
		updatePosition();// cập nhật vị trí của nhân vật
		if(isActtack)
			checkActtack();
		updateStatusOfMainer();// cập nhật trạng thái của nhân vật sau mỗi lần thanh đổi vị trí
		setStatus();
	}
	private void updateActtackBox() {
		if(right) {
		
			acttackBox.x=hitBox.x+hitBox.width+(int)(Game.SCALE*10);// hộp chứa nhân vật chuyển sang bên phải
		}
		else if(left) {
			acttackBox.x=hitBox.x-hitBox.width-(int)(Game.SCALE*10);
		}
		acttackBox.y=hitBox.y +(int)(Game.SCALE);
		
	}
	private void updateHealthBar() {
		healthWidth=(int) ((currentHealth /(float)maxHealth)*healthBarWidth);
		
	}

	public void render(Graphics g, int levelOffset) {// reset lại hộp hitbox
		g.drawImage(statusOfMainer[status][statusIndex] ,
				(int) (hitBox.x-xDrawOffset)-levelOffset+flipX,// lí do cần cộng thêm flipX vì khi ta chuyển hướng thì toạ độ của x cần phải ở vị trí right lúc trước nếu không nhân vật sẽ nhảy đi một đoạn
				(int)( hitBox.y-yDrawOffset),
				width*flipW,height, null);//getFocus is image of server
		// tại sao cần nhân thêm flipW 
				drawStatusPower(g);
//				drawActtackBox(g,levelOffset);
//				drawHitBox(g, levelOffset);
	}

	private void drawStatusPower(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart+statusBarX, healthBarYStart+statusBarY, healthWidth, healthBarHeight);
	}
	private void updateStatusOfMainer() {
		statusTick++;// biến này là biến thời gian để chạy đến khí biến này bằng thời gian tồn tại 1 khung hình thì chuyển sang hoạt ảnh mới
		if(statusTick>=STATUSSPEED) {
			statusTick=0;
			statusIndex++;
			if(statusIndex>=GetSpriteAmount(status)) {// khi tấn công thì ta phải cho dừng trạng thái tấn công và bắt đầu chạy khung hoạt ảnh tan cong
				statusIndex=0;
				isActtack=false;// khi chạy hết hoạt ảnh tấn công rồi thì cần reset lại trạng thái
				acttackChecked=false;// khi đã tấn công xong rồi thì cần reset lại trạng thái
			}
		}
		
	}
	public void setDirection(int value) {
		this.playerDirection=value;
		this.moving = true;
	}
	public void setActtack(boolean isActtack) {
		this.isActtack=isActtack;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	private void setStatus() {
		
		int startStatus=status;
		if(moving) 
			status = RUNNING;
		else 	status = IDLE;
		
		if (inAir) {
			if (airSpeed < 0)
				status = JUMP;
			else
				status = FALLING;
		}
		
		if(isActtack) {// truyền thông điện tấn công
			status=ACTTACK;// hành động của nhân vật đang được gán bằng acttack để lấy được số phần từ của mảng chứa hoạt ảnh acttack
			if(startStatus !=ACTTACK) {//trước khi nhấn ACTTACK thì có thể nhân vật đang ở 
				statusIndex=1;
				statusTick=0;
				return;
			}
		}
		if(startStatus!=status) {// nếu trạng thái của nhân vật thay đổi thì cần reset hết chỉ số mảng hoạt ảnh của nhân vật
			resetStatus();
		}
		
	}
	private void resetStatus() {
		statusTick=0;
		statusIndex=0;
		
	}
	public void changeHealth(int value) {
		currentHealth-=value;
//		System.out.println("Player Health:"+currentHealth);
		if(currentHealth<=0) {
			currentHealth=0;
			//game over
		}
		else if(currentHealth>=maxHealth) {
			currentHealth=maxHealth;
		}
	
			
	}

	private void updatePosition() {
		moving = false;// hiện tại nhân vật không di truyển
		if (jump)// nếu nhân vật đang nhảy
			jump();// set trạng thái nhảy cho nhân vật như là tốc độ nhay
//		if (!left && !right && !inAir)// không thực hiện gì
//			return;
		if(!inAir)
			if((!left&& !right)||(left&&right))
				return;
		float xSpeed = 0; // tốc đọ hiện tại bằng 0
		if (left){// di truyển trái: tốc độ di truyển bằng tốc độ của người
			xSpeed -=speed;
			flipX =width;// chiều rộng thực tế của biến x là chiều rộng
			flipW=-1;
		}
		if (right) {
			xSpeed +=speed;// di truyển phải: tốc độ di truyển của 
			flipX=0;
			flipW=1;
		}
		if (!inAir)// nếu không ở trong không khí
			if (!IsEntityOnFloor(hitBox, levelData))// check chướng ngại vật ở ở trên đường đi
				inAir = true;

		if (inAir) {// nếu ở trong không khí
			if (canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {// check chướng ngại vật ở xung quanh ở đây nếu không có gì
				hitBox.y += airSpeed;// toạ độ của hộp sẽ thay đổi đến đó
				airSpeed += GRAVITY; // tốc độ trong không khí bằng trọng lực
				updateXPos(xSpeed);// thay đổi toạ độ x
			} else {// nếu có chướng ngoại vật
				hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);// lấy toạ độ của chướng ngoại vật
				if (airSpeed > 0) // có chướng ngoại vật mà đang chuyển động thì sẽ dừng lại
					resetInAir();// đã rơi hết
				else//có chướng ngoại vật mà không chuyển động rơi xuống
					airSpeed = fallSpeedAfterCollision; // tiếp tục rơi
				updateXPos(xSpeed);// cập nhập vị trí x
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}
	private void jump() {// thiết lập trạng thái nhảy cho nhân vật
		if (inAir)// nếu trạng thái của nhân vật lúc nhảy mà vẫn trong không khí thì không làm gì
			return;
		inAir = true;
		airSpeed = jumpSpeed;// tốc độ trong không khí bằng tốc độ nhảy
	}
	private void resetInAir() {// reset lại trạng thái nếu nhảy xong hoặc dưới chân có chướng ngoại vật
		inAir = false;
		airSpeed = 0;

	}
	private void updateXPos(float xSpeed) {
		if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {// kiểm tra xem vị trí tiếp theo có thể đi được không
			hitBox.x += xSpeed; // nếu được thì cập nhật
//			System.out.println("x="+x);
		} else {
			hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);// nếu không thấy vị trí của thằng cản trở
		}

	}
	public void resetDirectionBoolean() {// reset hướng di chuyển của nhân vật thành false tức là cho nhân vận không di truyển nữa
		left=false;
		right=false;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	public void setJump(boolean jump) {
		this.jump=jump;
	}
	public void resetAll() {
		resetDirectionBoolean();
		inAir=false;
		isActtack =false;
		moving=false;
		status=IDLE;
		currentHealth=maxHealth;
		hitBox.x=x;
		hitBox.y=y;
		flipW =1;
		flipX=0;
		if (!IsEntityOnFloor(hitBox, levelData))// kiểm tra xem cái ô hiện tại có hoạt ảnh bản đồ nào không nếu không có thì nhân vật đang trong không khí
			inAir = true;
	}
	//khởit tạo bằng set
	
}
