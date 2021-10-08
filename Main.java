import java.util.Random;
/**
 *  See Readme.md for instructions.
 *  Use left and right arrow keys to see it when running main()
 */
public class Main implements Directions {

    /**
     * Do NOT edit this.  Put your code inside the runJerooCode method below.
     */
    public static void main(String[] args) {
        Map.getInstance().loadMap("maps/map1.jev");
        new JerooGUI();
        Main m = new Main();
        m.runJerooCode();

    }

    /**
     * Main program where castle inhabitants, bullets, pilots, and bombs all take a turn trying to defeat their enemies.
     */
    public void runJerooCode() {
      Jeroo castleL = new Jeroo(19,3,EAST,0);
      castleL.setImage("Jeroo_0_East14.gif","Jeroo_0_North14.gif","Jeroo_0_South14.gif","Jeroo_0_West14.gif");
      Jeroo castleR = new Jeroo(19,28,EAST,0);
      castleR.setImage("Jeroo_1_East14.gif","Jeroo_1_North14.gif","Jeroo_1_South14.gif","Jeroo_1_West14.gif");
      Jeroo bulletL = new Jeroo(23,0,NORTH,999);
      bulletL.setImage("","","","");
      Jeroo bulletR = new Jeroo(23,31,NORTH,999);
      bulletR.setImage("","","","");
      Jeroo pilotL = new Jeroo(2,4,SOUTH,999);
      pilotL.setImage("","","Jeroo_1_South14.gif","");
      Jeroo pilotR = new Jeroo(2,27,SOUTH,999);
      pilotR.setImage("","","Jeroo_0_South14.gif","");
      Jeroo bombL = new Jeroo(3,0,EAST,999);
      bombL.setImage("","","nuke.jpg","");
      Jeroo bombR = new Jeroo(3,31,WEST,999);
      bombR.setImage("","","nuke.jpg","");
      while (!castleL.gameOver()) {
        if (!castleR.gameOver()) {
          if (!castleL.gameOver()) {
            //castleL moves and shoots
            castleL.castleWanderL();
            castleL.castleWanderL();
            bulletL.bulletTurn();
            castleR.checkDead();
          } else {
            castleL.isDead();
          }
          if (!castleR.gameOver()) {
            //castleR moves and shoots
            castleR.castleWanderR();
            castleR.castleWanderR();
            bulletR.bulletTurn();
            castleL.checkDead();
          } else {
            castleL.isDead();
          }
          if (!castleL.gameOver()) {
            //ship on left moves and drops bomb
            pilotL.shipWanderL();
            bombL.bombTurn();
            castleL.checkDead();
          }
          if (!castleL.gameOver()) {
            //ship on right moves and drops bomb
            pilotR.shipWanderR();
            bombR.bombTurn();
            castleR.checkDead();
          }
        } else {
          castleL.isDead();
        }
      }
      System.out.println("GAME OVER");
    }
}
