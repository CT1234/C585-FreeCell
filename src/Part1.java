import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Part1 {
	
	public static File selectedFile;
		
	  public static void main(String[] args) {
	    JFrame frame = buildFrame();
	    JButton button = new JButton("Select File");
		frame.add(button);
	    	    	    
	    button.addActionListener(new ActionListener() 
	    {
			public void actionPerformed(ActionEvent ae) 
			{
				JFileChooser fileChooser = new JFileChooser();
			    int returnValue = fileChooser.showOpenDialog(null);
			    if (returnValue == JFileChooser.APPROVE_OPTION) 
			    {
			    	selectedFile = fileChooser.getSelectedFile();
			    	CardImagePanel image = new CardImagePanel(selectedFile);
			    	
			    	image.setPreferredSize(new Dimension(500, 500));

			    	frame.remove(button);
					frame.add(image);
					
					frame.repaint();
					frame.revalidate();
			    }
			}
	    });
	  }
	  
	  private static JFrame buildFrame() 
	  {
			JFrame frame = new JFrame();
			frame.setSize(1000,1000);
			frame.setLayout(new FlowLayout());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			return frame;
	  }
}

class CardImagePanel extends JPanel {
	private BufferedImage picture;

	public CardImagePanel(File newImageFile)
	{
	try {
		picture = ImageIO.read(newImageFile);

	} catch (IOException e){
		e.printStackTrace();}
	}
	
	public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(picture, 0, 0, 500, 500, this);
    }
}