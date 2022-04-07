
import javax.swing.JFrame;


public class RecursiveListerRunner 
{
    public static void main(String[] args) 
    {
        RecursiveListerFrame MyFileListerFrame = new RecursiveListerFrame();

        MyFileListerFrame.SetRecursiveListerFrameDisplay();

        MyFileListerFrame.setSize(900, 600);

        MyFileListerFrame.setResizable(false);

        MyFileListerFrame.setLocationRelativeTo(null);
        
        MyFileListerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyFileListerFrame.setVisible(true);
    }
}
