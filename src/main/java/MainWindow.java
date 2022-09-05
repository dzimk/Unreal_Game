import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake Game");  //то что будет отображаться в верхнем окне
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //Добавляет к окну программы крестик завершения программы
        setSize(320, 345);  //Размер окна
        setLocation(400, 400);  //точка координат
        add(new GameField());
        setVisible(true);  //Видимость экрана
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
