package visual;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class Launcher{
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainWindow mainWindow = null;
                try {
                    mainWindow = new MainWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainWindow.show();
            }
            
        });
    }
}
