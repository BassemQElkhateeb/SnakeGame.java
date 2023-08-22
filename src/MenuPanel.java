import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener {
    int gameWidth = GamePanel.WIDTH;
    int gameHeight = GamePanel.HEIGHT;
    JButton button = new JButton("Start Game");


    public MenuPanel(){
        this.setPreferredSize(new Dimension(600,600));
        this.setBackground(Color.ORANGE);
        this.setFocusable(true);
        button.setSize(new Dimension(100,75));
        this.add(button);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
