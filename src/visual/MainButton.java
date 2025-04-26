package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MainButton {

    private JButton _button;

    public MainButton(){
        _button = new JButton();
        _button.setFocusable(false);
        _button.setBackground(new Color(0xF5F5F5));
        _button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        _button.setMargin(new Insets(10, 10, 10, 10));
        _button.setFont(new Font("Arial", Font.PLAIN, 24));
        _button.doClick();
        _button.setPreferredSize(new Dimension(200, 30));
        _button.setMaximumSize(new Dimension(200, 30));
    }

    public MainButton(String name){
        this();
        _button.setText(name);
        _button.setToolTipText("Serve para iniciar um jogo com dificuldade " + name + ".");
        _button.setVerticalTextPosition(SwingConstants.CENTER);
        _button.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public MainButton(String name, ImageIcon image){
        this(name);
        Image resizedImage = image.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        image= new ImageIcon(resizedImage);

        _button.setIcon(image);
        _button.setIconTextGap(10);
    }   

    public JButton getButton(){ return _button;}

}