package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.GamePanel;
import Menu.GameStatus;

import static Menu.GameStatus.*;
public class KeyBoardInputs implements KeyListener{
	private GamePanel gamePanel;
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel=gamePanel;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameStatus.status) {
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case CONTINUE:
			gamePanel.getGame().getContinue().keyPressed(e);;
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().keyPressed(e);
			break;
		default:
			break;
//		case KeyEvent.VK_J:
//			gamePanel.getGame().getPlayer().setActtack(true);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameStatus.status) {
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);;
			break;
		case CONTINUE:
			gamePanel.getGame().getContinue().keyReleased(e);;
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().keyReleased(e);
			break;
		default:
			break;
		}
	}
}
