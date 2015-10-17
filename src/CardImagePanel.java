import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CardImagePanel extends JPanel implements MouseListener, MouseMotionListener
{
	private boolean eventStarted = false;
	private BufferedImage picture;	
	private int cardNum;
	private boolean allowDrag = false;
	private int currentXPosition = 0,
	            currentYPosition = 0;

	public CardImagePanel(File newImageFile)// FreeCellGame parent)
	{
		this.setLayout(null);
		try {
			picture = ImageIO.read(newImageFile);
	
		} catch (IOException e){
			e.printStackTrace();
		}

		String tempString = newImageFile.getName();
		cardNum = Integer.parseInt(tempString.substring(0,tempString.lastIndexOf('.')));
	}
	
	public int getCardNum()
	{
		return this.cardNum;
	}
	
	public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(picture, 1, 0, 148, 150, this);
    }
	
	public BufferedImage getPicture() {
		return picture;
	}
	public void setDraggable(boolean state) {
		this.allowDrag = state;
	}
	public void setPosition(int x, int y) {
		this.currentXPosition = x;
		this.currentYPosition = y;
	}
	public void mouseDragged(MouseEvent e) {
		if(allowDrag) 
		{
			if(!eventStarted)
			{
				this.getParent().setComponentZOrder(this, 0);
				eventStarted = true;
			}
			this.setBounds(e.getX() + this.getX()-75, e.getY() + this.getY()-75, 148, 150); //move center of card to mouse
		}
	}
	public void mouseReleased(MouseEvent e) {
		if(allowDrag)
		{
			checkDropLocation(this.getX(), this.getY());
			eventStarted = false;
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
		
	public void checkDropLocation(int x, int y)
	{	
		x +=75;
		y +=75;
		if(x < 600 && y > 50 && y < 200) {//FREE CELLS
			int freeCellColumn = getColumn(x);
			if(((FreeCellGame) this.getParent()).checkFreeCell(freeCellColumn))
			{
				((FreeCellGame) this.getParent()).removeOldspots(this.getCardNum());
				((FreeCellGame) this.getParent()).setNewDraggables();
				((FreeCellGame) this.getParent()).fillFreeCell(freeCellColumn, this.cardNum);
				this.setBounds(freeCellColumn*150, 50, 150, 150);
				setPosition(freeCellColumn*150, 50);
			}
			else
			{
				this.setBounds(currentXPosition, currentYPosition, 150, 150);
			}
		}
		else if(x > 600 && y > 100 && y < 250) {//FOUNDATIONS
			int foundationColumn = getColumn(x);
			if(((FreeCellGame) this.getParent()).checkFoundationInsert(foundationColumn-4, this.cardNum))
			{
				((FreeCellGame) this.getParent()).removeOldspots(this.getCardNum());
				((FreeCellGame) this.getParent()).setNewDraggables();
				this.setBounds(foundationColumn*150, 100, 150, 150);
				setPosition(foundationColumn*150, 100);
			}
			else
			{
				this.setBounds(currentXPosition, currentYPosition, 150, 150);
			}
		}
		else if(y > 300) { //CARD STACKS
			int columnIndex = getColumn(x);
			int currentColumnHeight = ((FreeCellGame) this.getParent()).getColumnHeight(columnIndex);
			if(y > currentColumnHeight && y < (currentColumnHeight + 150) &&
					((FreeCellGame) this.getParent()).checkColumnInsert(columnIndex, this.getCardNum()))
			{
				((FreeCellGame) this.getParent()).removeOldspots(this.getCardNum());
				((FreeCellGame) this.getParent()).updateCardStack(columnIndex, this.getCardNum());
				int updatedHeight = ((FreeCellGame) this.getParent()).getColumnHeight(columnIndex);
				this.setBounds(columnIndex*150, updatedHeight, 150, 150);
				setPosition(columnIndex*150, updatedHeight);
				((FreeCellGame) this.getParent()).setNewDraggables();
			}
			else
			{
				this.setBounds(currentXPosition, currentYPosition, 150, 150);
			}
		}
		else 
		{ 
			this.setBounds(currentXPosition, currentYPosition, 150, 150);
		}
	}
	public int getColumn(int x)
	{
		if(x < 150)
			return 0;
		else if(x < 300)
			return 1;
		else if(x < 450)
			return 2;
		else if(x < 600)
			return 3;
		else if(x < 750)
			return 4;
		else if(x < 900)
			return 5;
		else if(x < 1050)
			return 6;
		else if(x < 1200)
			return 7;
		else return -1;
	}
}