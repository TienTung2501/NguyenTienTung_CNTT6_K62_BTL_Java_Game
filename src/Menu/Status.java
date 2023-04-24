package Menu;

import java.awt.event.MouseEvent;

import Gui.MenuButton;
import Main.Game;
import audioPlayer.AudioPlayer;

public class Status {
	protected Game game;// ta se phai goi trang thai cua game ra
	public Status(Game game) {// khoi tao game cho trang thai
		this.game=game;
	}
	//Phương thức isIn sử dụng tham số e là một sự kiện chuột MouseEvent, và tham số mb là một đối tượng MenuButton. 
	//Nó sẽ kiểm tra xem tọa độ của chuột được đưa vào thông qua tham số e có nằm trong vùng bounds của MenuButton hay không. 
	//Phương thức này sẽ trả về true nếu tọa độ chuột nằm trong vùng bounds của MenuButton, ngược lại trả về false.
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	public Game getGame() {// lay game cua tung trang thai
		return game;
	}
	public void setGameStatus(GameStatus status) {
		switch(status) {
		case MENU:
			game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
		case CONTINUE:
			game.getAudioPlayer().setLevelSong(game.getContinue().getLevelManager().getLevelIndex());;
		}
		GameStatus.status = status;
	}
}
