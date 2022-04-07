
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class RecursiveListerFrame extends JFrame
{
    private JPanel mainPanel;
    private JPanel filenameDisplayPanel;
    private JPanel controlPanel;
    
    private JButton directoryDisplayButton;
    private JButton quitButton;
    private JScrollPane filenameDisplayScrollPane;
    private JTextArea filenameDisplayArea;
    
    private String directoryFileList;
    
    public RecursiveListerFrame()
    {
        setTitle("Recursive File Lister");
        directoryFileList = "";
    }
    
    public void SetRecursiveListerFrameDisplay()
    {
        mainPanel = new JPanel();

        createFileNameDisplayPanel();
        createControlPanel();

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(filenameDisplayPanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
    
    private void createFileNameDisplayPanel()
    {
        filenameDisplayPanel = new JPanel();

        Font textAreaFont  = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        
        filenameDisplayArea = new JTextArea(35, 120);
        filenameDisplayArea.setFont(textAreaFont);
        filenameDisplayArea.setEditable(false);
        
        filenameDisplayScrollPane = new JScrollPane(filenameDisplayArea);
        filenameDisplayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        filenameDisplayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        filenameDisplayPanel.add(filenameDisplayScrollPane);
    }
    
    private void createControlPanel()
    {
        controlPanel = new JPanel();

        GridLayout filelisterControlLayout = new GridLayout(1,5);
        filelisterControlLayout.setHgap(10);
        filelisterControlLayout.setVgap(10);

        controlPanel.setLayout(filelisterControlLayout);

        Font buttonFont  = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        directoryDisplayButton = new JButton("Select Directory!");
        directoryDisplayButton.setFont(buttonFont);
		
        quitButton = new JButton("Quit");
        quitButton.setFont(buttonFont);

        JLabel emptyLabel1 = new JLabel("");
        JLabel emptyLabel2 = new JLabel("");
        JLabel emptyLabel3 = new JLabel("");

        controlPanel.add(emptyLabel1);
        controlPanel.add(directoryDisplayButton);
        controlPanel.add(emptyLabel3);
        controlPanel.add(quitButton);
        controlPanel.add(emptyLabel2);

        directoryDisplayButton.addActionListener(ae -> filenameListerButonClicked());

        quitButton.addActionListener(ae -> {
            System.exit(0);
        });
    }
    
    public void filenameListerButonClicked()
    {
        filenameDisplayArea.setText("");
        SelectDirectoryAndListFiles();
    }
    
    private void RecursiveScanDirectory(File[] arr, int index, int level)
    {
        try
        {
            // terminate condition
            if ((arr != null) && (index == arr.length))
                return;

            // tabs for internal levels
            for (int i = 0; i < level; i++)
                filenameDisplayArea.append("\t");

            // for files
            if (arr[index] != null)
            {
                if(arr[index].isFile())
                    filenameDisplayArea.append(arr[index].getName() + "\n");

            // for sub-directories
                else if (arr[index].isDirectory())
                {
                    String directoryName = "[" + arr[index].getName() + "]";

                    filenameDisplayArea.append(directoryName + "\n");

                    // recursion for sub-directories
                    RecursiveScanDirectory(arr[index].listFiles(), 0, level + 1);
                }
            }
        }
        catch(Exception e)
        {
            return;
        }
        
        // recursion for main directory
        RecursiveScanDirectory(arr, ++index, level);
    }
   
    private void SelectDirectoryAndListFiles()
    {
        File maindir;
        JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
        { 
            maindir = chooser.getSelectedFile();
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Please Select Directory");
            return;
        }
 
        if (maindir.exists() && maindir.isDirectory()) 
        {
            // array for files and sub-directories
            // of directory pointed by maindir
            File arr[] = maindir.listFiles();
 
            String starDisplay = new String(new char[110]).replace("\0", "*");
            directoryFileList = starDisplay + "\n";
            directoryFileList += "Files from main directory : " + maindir + "\n";
            directoryFileList += starDisplay + "\n";
 
            filenameDisplayArea.append(directoryFileList);
            
            // Calling recursive method
            RecursiveScanDirectory(arr, 0, 0);
        }
    }
}
