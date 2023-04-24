package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.GamePanel;
import Menu.GameStatus;

public class MouseInputs implements MouseListener, MouseMotionListener{
	private GamePanel gamePanel;
	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel=gamePanel;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameStatus.status) {
		case MENU:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case CONTINUE:
			gamePanel.getGame().getContinue().mousePressed(e);
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().mousePressed(e);
			break;	
		default:
			break;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameStatus.status) {
		case MENU:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case CONTINUE:
			gamePanel.getGame().getContinue().mouseReleased(e);
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().mouseReleased(e);
			break;
		default:
			break;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (GameStatus.status) {
		case CONTINUE:
			gamePanel.getGame().getContinue().mouseDragged(e);
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().mouseDragged(e);
			break;
		default:
			break;
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameStatus.status) {
		case MENU:
			gamePanel.getGame().getMenu().mouseMoved(e);
			break;
		case CONTINUE:
			gamePanel.getGame().getContinue().mouseMoved(e);
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().mouseMoved(e);
			break;
		default:
			break;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (GameStatus.status) {
		case CONTINUE:
			gamePanel.getGame().getContinue().mouseClicked(e);
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().mouseClicked(e);
			break;
		default:
			break;

		}
			
	}
}
