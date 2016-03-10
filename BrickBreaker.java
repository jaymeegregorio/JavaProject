import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BrickBreaker extends JPanel implements KeyListener,
  ActionListener, Runnable {
 // movement keys..
 static boolean right = false;
 static boolean left = false;
 boolean [] keys = new boolean[256];
 // ..............

 // variables declaration for ball
 int ballx = 160;
 int bally = 420;
 // variables declaration for ball

 // variables declaration for bat
 int batx = 230;
 int baty = 420;
 // variables declaration for bat
 
 // variables declaration for brick
 int brickx = 140;
 int bricky = 50;
 // variables declaration for brick
 
 int score=0;
 int lives=3;
 int GameState=0;
 
 // declaring ball, paddle,bricks
 Rectangle Ball = new Rectangle(ballx, bally, 5, 5);
 Rectangle Bat = new Rectangle(batx, baty, 40, 5);
 // Rectangle Brick;// = new Rectangle(brickx, bricky, 30, 10);
 Rectangle[] Brick = new Rectangle[12];
 
 boolean play = false;

 BrickBreaker() {
 	

 }

 public static void main(String[] args) {
 
  JFrame frame = new JFrame();
  BrickBreaker game = new BrickBreaker();
  JButton button = new JButton("Restart");
  
 
  frame.setTitle("Brick Breaker");
  frame.setSize(750, 500);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  frame.add(game);
  
  frame.add(button, BorderLayout.EAST);
  frame.setLocationRelativeTo(null);
  frame.setResizable(false);
  frame.setVisible(true);
  button.addActionListener(game);
  button.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));

  game.addKeyListener(game);
  game.setFocusable(true);
  Thread t = new Thread(game);
  t.start();

 }
 public void playgame(){
 	//Spacebar.
	 if(keys[32]){
            if(GameState == 0) {
                if(play== false) GameState = 1;
            }
           
        } else if(keys[32] == false){
            play= false;
        }
}
 

 // declaring ball, paddle,bricks
 public void paint(Graphics g) {
 super.paint(g);
 Image img1 = Toolkit.getDefaultToolkit().getImage("bg.jpg");
 
  g.drawImage(img1, -100, -500, this);
  g.setColor(Color.white);
  g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);
  g.setColor(Color.white);
  g.fill3DRect(Bat.x, Bat.y, Bat.width, Bat.height, true);
  //g.setColor(Color.GRAY);
  //g.fillRect(0, 251, 450, 200);
  g.setColor(Color.white);
  g.drawRect(0, 0, 500, 470);
  g.setFont(new Font("Showcard Gothic", Font.PLAIN,15));
  g.drawString("Lives: "+lives, 550, 200);
  
  g.drawString("Score: "+score, 550, 270);
  /*g.setFont(new Font("Showcard Gothic", Font.PLAIN,15));
 //Start screen
        if(GameState == 0){
            Font fnt = new Font("Showcard Gothic", Font.BOLD, 20);
            g.setFont(fnt);
            g.setColor(Color.BLACK);
            g.drawString("TAP TO START", getWidth() / 2 - 60, getHeight() / 2);
        }
  */
  for (int i = 0; i < Brick.length; i++) {
   if (Brick[i] != null) {
    g.fill3DRect(Brick[i].x, Brick[i].y, Brick[i].width,
      Brick[i].height, true);
   }
  }

  if (ballFallDown == true || bricksOver == true) {
   Font f = new Font("Showcard Gothic", Font.BOLD, 20);
   g.setFont(f);
   g.drawString(status, 150, 250);
   g.drawString("Score: "+score, 200, 200);
   ballFallDown = false;
   bricksOver = false;
  }

 }
 

 // /...Game Loop...................

 // /////////////////// When ball strikes borders......... it
 // reverses......==>
 int movex = -1;
 int movey = -1;
 boolean ballFallDown = false;
 boolean bricksOver = false;
 
 int count = 0;
 String status;
 public void run() {

  // //////////// =====Creating bricks for the game===>.....
  for (int i = 0; i < Brick.length; i++) {
   Brick[i] = new Rectangle(brickx, bricky, 30, 10);
   if (i == 5) {
    brickx = 140;
    bricky = 62;
   }
   if (i == 9) {
    brickx = 170;
    bricky = 74;
   }
   brickx += 31;
  }
  // Tapos na yung paggawa ng brick

  //
  // == ball reverses when touches the brick=======
//ballFallDown == false && bricksOver == false
  while (true) {
//   if(gameOver == true){return;}
   for (int i = 0; i < Brick.length; i++) {
    if (Brick[i] != null) {
     if (Brick[i].intersects(Ball)) {
      Brick[i] = null;
      score=score+25;
      
      // movex = -movex;
      movey = -movey;
      count++;
     }// end of 2nd if..
    }// end of 1st if..
   }// end of for loop..

   // /////////// =================================

   if (count == Brick.length) {// check if ball hits all bricks
    bricksOver = true;
    status = "YOU WON THE GAME";
    repaint();
   }
   // /////////// =================================
   repaint();
   Ball.x += movex;
   Ball.y += movey;

   if (left == true) {

    Bat.x -= 3;
    right = false;
   }
   if (right == true) {
    Bat.x += 3;
    left = false;
   }
   if (Bat.x <= 4) {
    Bat.x = 4;
   } else if (Bat.x >= 460) {
    Bat.x = 460;
   }
   // /===== Ball reverses when strikes the bat
   if (Ball.intersects(Bat)) {
   	if(GameState==1) play=true; {
   	
    movey = -movey;
   	}
    // if(Ball.y + Ball.width >=Bat.y)
   }
   // //=====================================
   // ....ball reverses when touches left and right boundary
   if (Ball.x <= 0 || Ball.x + Ball.height >= 500) {
   	if(GameState==1) play=true; {
    movex = -movex;
   	}
   }// if ends here
   if (Ball.y <= 0) {// ////////////////|| bally + Ball.height >= 250
   if(GameState==1) play=true; {
    movey = -movey;
   }
   }// if ends here.....
   if (Ball.y >= 420) {// when ball falls below bat game is over...
   if(GameState==1) play=true; {
    ballFallDown = true;
    status = "YOU LOST THE GAME";
   
    repaint();
//    System.out.print("game");
   }
   }
   
  
   try {
    Thread.sleep(10);
   } catch (Exception ex) {
   }// try catch end

  }// while loop end

 }

 // loop end

 // Keyevents 
 @Override
 public void keyPressed(KeyEvent e) {
 	if(GameState == 1) play = true;{
 		
 	
  int keyCode = e.getKeyCode();
  if (keyCode == KeyEvent.VK_LEFT) {
   left = true;
   // System.out.print("left");
  }

  if (keyCode == KeyEvent.VK_RIGHT) {
   right = true;
   // System.out.print("right");
  }
 }
 }
 @Override
 public void keyReleased(KeyEvent e) {
  int keyCode = e.getKeyCode();
  if (keyCode == KeyEvent.VK_LEFT) {
   left = false;
  }

  if (keyCode == KeyEvent.VK_RIGHT) {
   right = false;
  }
 }

 @Override
 public void keyTyped(KeyEvent arg0) {

 }

 @Override
 public void actionPerformed(ActionEvent e) {
  String str = e.getActionCommand();
  if (str.equals("Restart")) {
  	score = 0;
  	if(lives > 0) lives = lives - 1;
  	{
  		status = "No more lives";
  		repaint();
  	}
  	if(lives == 0)
  	{
  		JOptionPane.showMessageDialog(null,"No more lives. Try again soon.");
  		System.exit(0);
  	}
  	
System.out.print("hi");
   this.restart();

  }
 }

 public void restart() {

  requestFocus(true);
  // ..............
  // variables declaration for ball
  ballx = 160;
  bally = 420;
  // variables declaration for ball
  
  // variables declaration for bat
  batx = 230;
  baty = 420;
  // variables declaration for bat
  
  // variables declaration for brick
  brickx = 140;
  bricky = 50;
  // variables declaration for brick
  
  // declaring ball, paddle,bricks
  Ball = new Rectangle(ballx, bally, 5, 5);
  Bat = new Rectangle(batx, baty, 40, 5);
  // Rectangle Brick;// = new Rectangle(brickx, bricky, 30, 10);
  Brick = new Rectangle[12];

  movex = -1;
  movey = -1;
  ballFallDown = false;
  bricksOver = false;
  count = 0;
  status = null;

  // //////////// =====brikcs===>.....
  /*
   * gawa ulit ng bricks
   * 
   */
  for (int i = 0; i < Brick.length; i++) {
   Brick[i] = new Rectangle(brickx, bricky, 30, 10);
   if (i == 5) {
    brickx = 140;
    bricky = 62;
   }
   if (i == 9) {
    brickx = 170;
    bricky = 74;
   }
   brickx += 31;
  }
  repaint();
 }
}
