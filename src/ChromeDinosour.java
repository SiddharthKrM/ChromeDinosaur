import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
public class ChromeDinosour extends JPanel implements ActionListener,KeyListener{
int boardwidth=750;
int boardheigth=250;
//images
   Image dinosourImg;
   Image dinosourDeadImg;
   Image dinosourJumpImg;
   Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;



    class Block{
        int x;
        int y;
        int width;
        int height;
        Image img;


        public Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }

    }

    //dinosour
    int dinosourWidth=88;
    int dinosourHeight=94;
    int dinosourX=50;
    int dinosourY=boardheigth-dinosourHeight;
    Block dinosour;
    //CACTUS
    int cactus1Width=34;
    int cactus2Width=69;
    int cactus3Width=102;
    int cactusX=700;
    int cactusHeight=70;
    int cactusY=boardheigth-cactusHeight;
    ArrayList<Block> cactusArray;
    //physics
    int velocityY=0;//dinosour jump speed
    int gravity=1;
    int velocityX=-12;//cactus moving left
    Timer placeCactusTimer;
    Timer gameLoop;
    boolean gameOver =false;
    int score=0;
public ChromeDinosour(){
    setPreferredSize(new Dimension(boardwidth,boardheigth));
    setBackground(Color.lightGray);
    setFocusable(true);
    addKeyListener(this);

    dinosourImg=new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
    dinosourDeadImg=new ImageIcon(getClass().getResource("./img/dino-dead.png")).getImage();
    dinosourJumpImg=new ImageIcon(getClass().getResource("./img/dino-jump.png")).getImage();
    cactus1Img=new ImageIcon(getClass().getResource("./img/cactus1.png")).getImage();
    cactus2Img=new ImageIcon(getClass().getResource("./img/cactus2.png")).getImage();
    cactus3Img=new ImageIcon(getClass().getResource("./img/cactus3.png")).getImage();

    //dinosour
    dinosour=new Block(dinosourX,dinosourY,dinosourWidth,dinosourHeight,dinosourImg);
    //cactus
    cactusArray= new ArrayList<Block>();
    //game timer
    gameLoop=new Timer(1000/60,this);
    gameLoop.start();
    //PLACE CACTUS TIMER
    placeCactusTimer=new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            placeCactus();
        }
    });
    placeCactusTimer.start();
 }
void placeCactus(){
    if(gameOver){
        return;
    }
    double placeCactusChance=Math.random();//0-.999999
    if(placeCactusChance>0.90){
        Block cactus=new Block(cactusX,cactusY,cactus3Width,cactusHeight,cactus3Img);
        cactusArray.add(cactus);
    }
    else if(placeCactusChance>0.70){
        Block cactus=new Block(cactusX,cactusY,cactus2Width,cactusHeight,cactus2Img);
        cactusArray.add(cactus);
    }
    else if (placeCactusChance>0.50){
        Block cactus=new Block(cactusX,cactusY,cactus1Width,cactusHeight,cactus1Img);
        cactusArray.add(cactus);
    }
    if(cactusArray.size()>10){
        cactusArray.remove(0);
    }
}
 public void paintComponent(Graphics g){
     super.paintComponent(g);
     draw(g);
 }

 public void draw(Graphics g){
    //dinosour
    g.drawImage(dinosour.img,dinosour.x,dinosour.y,dinosour.width,dinosour.height,null);
    //cactus
     for(int i=0;i<cactusArray.size();i++){
         Block cactus=cactusArray.get(i);
         g.drawImage(cactus.img,cactus.x,cactus.y,cactus.width,cactus.height,null);
     }
     //score
     g.setColor(Color.black);
     g.setFont(new Font("Courier",Font.PLAIN,32));
     if(gameOver){
         g.drawString("Game Over"+String.valueOf(score),10,35);
     }
     else {
         g.drawString(String.valueOf(score),10,35);
     }

 }
 public void move(){
    velocityY+=gravity;
    dinosour.y+=velocityY;

    if(dinosour.y>dinosourY){//stop the dinosour from falling past the ground
        dinosour.y=dinosourY;
        velocityY=0;
        dinosour.img=dinosourImg;
    }
    //cactus
     for(int i=0;i<cactusArray.size();i++){
         Block cactus= cactusArray.get(i);
         cactus.x+=velocityX;

         if(collision(dinosour,cactus)){
             gameOver=true;
             dinosour.img=dinosourDeadImg;
         }
     }
     score++;//score
 }
 boolean collision(Block a,Block b){
    return a.x<b.x+b.width&&
            a.x+a.width>b.x&&
            a.y<b.y+b.height&&
            a.y+a.height>b.y;
 }
    @Override
    public void actionPerformed(ActionEvent e) {
               move();
               repaint();
               if(gameOver){
                  placeCactusTimer.stop();
                  gameLoop.stop();
               }
    }
    @Override
    public void keyPressed(KeyEvent e) {
               if(e.getKeyCode()==KeyEvent.VK_SPACE){
                   //System.out.println("sid dino");
                   if(dinosour.y==dinosourY)
                   {
                       velocityY=-17;
                       dinosour.img=dinosourJumpImg;
                   }
                   if(gameOver){
                       //restart by resetting condition
                       dinosour.y=dinosourY;
                       dinosour.img=dinosourImg;
                       velocityY=0;
                       cactusArray.clear();
                       score=0;
                       gameOver=false;
                       gameLoop.start();
                       placeCactusTimer.start();
                   }
               }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
