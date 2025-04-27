package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class MainWindow {
    
    // Visual atributes

    public static Font _font = new Font("Arial", Font.BOLD, 36);
    public static Font _mineCounterFont;
    private JFrame _frame;

    private JPanel _main_panel;
    private JPanel _title_panel;
    private JPanel _side_panel;

    private JLabel _title;
    private JLabel _mines;
    private JLabel _winningLabel;
    
    private MainButton _easyButton;
    private MainButton _mediumButton;
    private MainButton _hardButton;
    private MainButton _restartButton;

    private Grid _grid;

    /**
     * Creates the main window of the program
     * @throws IOException
     */
    public MainWindow() throws IOException{

        /* JFRAME CONFIGURATION */

        _frame = new JFrame();
        _frame.setTitle("MineSweeper");
        _frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _frame.setSize(1500, 1000);
        _frame.setResizable(false);
        _frame.setLocationRelativeTo(null);

        /* JPANELS CONFIGURATION */

        _title_panel = new JPanel();
        _title_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        _title_panel.setBackground(Color.ORANGE);
        _title_panel.setLayout(new GridLayout(1, 4, 100, 10));

        _side_panel = new JPanel();
        _side_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        _side_panel.setLayout(new GridLayout(3, 1, 0, 10));
        _side_panel.setBackground(Color.ORANGE);

        _main_panel = new JPanel();
        _main_panel.setBackground(Color.DARK_GRAY);
        _main_panel.setOpaque(true);
        _main_panel.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY));

        _frame.add(_title_panel, BorderLayout.NORTH);
        _frame.add(_main_panel, BorderLayout.CENTER);
        _frame.add(_side_panel, BorderLayout.WEST);

        /* JBUTTON CONFIGURATION */

        _restartButton = new MainButton("Restart", "images/restart.png");
        _easyButton = new MainButton("Easy", "images/easy.png");
        _mediumButton = new MainButton("Medium", "images/medium.png");
        _hardButton = new MainButton("Hard", "images/hard.png");

        setupDifficultyButton(_restartButton.getButton(), "EASY");
        setupDifficultyButton(_easyButton.getButton(), "EASY");
        setupDifficultyButton(_mediumButton.getButton(), "MEDIUM");
        setupDifficultyButton(_hardButton.getButton(), "HARD");

        _side_panel.add(_easyButton.getButton());
        _side_panel.add(_mediumButton.getButton());
        _side_panel.add(_hardButton.getButton());

        /* GAME CONFIGURATIONS */

        // TITLE
        _title = new JLabel("Minesweeper");
        _title.setFont(_font);
        
        BufferedImage imageStream = ResourceLoader.loadImage("images/shovel.png");
        Image resizedImage = imageStream.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(resizedImage);
        _title.setIconTextGap(10);
        _title.setHorizontalTextPosition(SwingConstants.LEFT);
        _title.setVerticalTextPosition(SwingConstants.CENTER);

        _title.setIcon(image);

        _title_panel.add(_title);

        // MINE COUNTER
        _mines = new JLabel();
        try{
            InputStream fontStream = getClass().getResourceAsStream("Fonts/digital_7/digital-7.ttf");
            if(fontStream == null){
                System.out.println("Font not found!");
            }
            else{
                _mineCounterFont = 
                Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(44f);
            }

        }catch(IOException e){
            System.out.println("Failed to read the font.");
            e.printStackTrace();

        } catch(FontFormatException e){
            System.out.println("Incorrect Font Format.");
            e.printStackTrace();
        }
        
        _mines.setFont(_mineCounterFont);
        _mines.setForeground(new Color(0xBF0802));
        _mines.setOpaque(true);
        _mines.setBackground(Color.DARK_GRAY);
        _mines.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 4, true),
            BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        imageStream = ResourceLoader.loadImage("images/mine.png");
        resizedImage = imageStream.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
        image= new ImageIcon(resizedImage);

        _mines.setIconTextGap(10);
        _mines.setHorizontalTextPosition(SwingConstants.LEFT);
        _mines.setVerticalTextPosition(SwingConstants.CENTER);
        _mines.setIcon(image);

        _title_panel.add(_mines);


        // WINNING PANEL

        _winningLabel = new JLabel("...");
        _winningLabel.setFont(_font);
        _winningLabel.setOpaque(true);
        _winningLabel.setBackground(new Color(0xEAEAEA));
        _winningLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 4, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        _winningLabel.setIconTextGap(10);
        _winningLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        _winningLabel.setVerticalTextPosition(SwingConstants.CENTER);

        _title_panel.add(_winningLabel);

        _title_panel.add(_restartButton.getButton());
        

        // INTIAL GRID
        _grid = new Grid("EASY", _mines, _winningLabel);
        _main_panel.setLayout(new GridLayout(_grid.getGameSize(),_grid.getGameSize(),0,0));
        
        for(int i = 0; i < _grid.getGameSize(); i++){
            for(int j = 0; j < _grid.getGameSize(); j++){
                _main_panel.add(_grid.getCellAt(i, j).getJButton());
            }
        }
        
    }

    /**
     * Shows the aplication 
     */
    public void show(){
        _frame.setVisible(true);
    }

    /**
     * Starts a new game of the indicated difficulty of the button
     * @param button - button with a certain difficulty written
     * @param difficulty - difficulty of the game
     */
    private void setupDifficultyButton(JButton button, String difficulty){

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _frame.remove(_main_panel);
                _main_panel = new JPanel();
                _main_panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 10, 10));

                try {
                    _grid = new Grid(difficulty, _mines, _winningLabel);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                _main_panel.setLayout(new GridLayout(_grid.getGameSize(),_grid.getGameSize(),0,0));


                if(!button.getText().equals("Restart")){
                    JButton _restartJButton = _restartButton.getButton();
                    
                    for(ActionListener aux : _restartJButton.getActionListeners())
                        _restartJButton.removeActionListener(aux);

                    setupDifficultyButton(_restartJButton, difficulty);
                }
                
                for(int i = 0; i < _grid.getGameSize(); i++){
                    for(int j = 0; j < _grid.getGameSize(); j++){
                    _main_panel.add(_grid.getCellAt(i, j).getJButton());
                    }
                }
                _frame.add(_main_panel);
                _frame.revalidate();
                _frame.repaint();
            }
            
        });
    }
}
