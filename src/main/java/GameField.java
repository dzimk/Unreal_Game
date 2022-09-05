import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

//JPanel - элемент экрана
public class GameField extends JPanel implements ActionListener { // интерфейс ActionListener привязывает кнопки
    private final int SIZE = 320;
    // 320*320/16 = 400 площадь поля
    private final  int DOT_SIZE = 16;  // размер яблока
    private final int ALL_DOTS = 400; //  количество звеньев змейки

    private Image dot; //картинки
    private  Image apple;

    private int[] x = new int[ALL_DOTS];  //массив координат x 400
    private int[] y = new int[ALL_DOTS];

    private int appleX;
    private int appleY;  //координаты яблока

    private int dots;  //количество звеньев, очков
    private Timer timer;  //Класс который считает время, будет считать количество кадров в секунду

    //движение змейки с помощью кнопок
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true; //когда завершим игру станет false

    //прописываем метод загружающий картинки
    public void loadImage() {
        ImageIcon iia = new ImageIcon("src/main/apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("src/main/dot.png");
        dot = iid.getImage();
    }
    public void createApple() { //метод для генерации координат
        Random random = new Random(); //создаем экземпляр класса рандом
            appleX = random.nextInt(20) * DOT_SIZE;
            appleY = random.nextInt(20) * DOT_SIZE;
    }
    //инициализация игры в начале
    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 48;
            x[i] = 48 - i * DOT_SIZE;
        }
        timer = new Timer(150, this);
        timer.start();
        createApple();
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }
    //переопределяем метод для отрисовки компонентов
    @Override
    protected  void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        else {
                String str = "Game Over";
                g.setColor(Color.RED);
                g.drawString(str, SIZE/6, SIZE/2);
        }
    }
    //проверяем не врезалась ли наша змейка в стенку или в себя
    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) { //если координаты головы змейки совпадают с координатами
                                                //ее тела, то змейка врезалась в себя и конец игры
                inGame = false;
            }
        }
        if (x[0] > SIZE) { //Если голова змейки выходит за пределы игрового экрана вправо,
                           //то она появляется слева
            x[0] = 0;
        }
        if (x[0] < 0) {
            x[0] = SIZE;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }
    //метод который будет обновлять змейку
    @Override
    public void actionPerformed(ActionEvent a) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public GameField() {
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }
    public void move() {
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
    }
    //пишем класс который слушает нажатие наших кнопок
    class FiledKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent k) {
            super.keyPressed(k);
            int key = k.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                right = false;
                up = true;
            }
            if (key == KeyEvent.VK_DOWN &&!up) {
                left = false;
                right = false;
                down = true;
            }
        }
    }
}
