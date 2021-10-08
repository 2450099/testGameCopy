/**
 * Put your Jeroo methods in this class.
 * @author Steve Aronson
 * @author Connor Koefelda added methods and variables found in JerooBase, and code found in runJerooCode (main.java)
 */
public class Jeroo extends JerooBase {
  int yLC = 0; //for left side castle Y values
  int yRC = 0; //for right side castle Y values
  int xLC = 0; // for left side castle X values
  int xRC = 0; // for right side castle X values
  int rowCheck = 0; //for bullet locations
  int bulletStatus = 0; //to know what the bullet hit
  int gameStatus = 0; //to know if the game has ended
  int xLS = 0; //for left ship X values
  int xRS = 0; //for right ship X values
  int bombStatus = 0; //to know what the bomb hit
  /** 
  * track y value of a castle jeroo's hops
  * for left side jeroo
  */
  public void lCHop() {
    hop();
    if (isFacing(NORTH)) {
      yLC++;
    } else {
      if (isFacing(SOUTH)) {
        yLC--;
      } else {
        if (isFacing(EAST)) {
          xLC++;
        } else {
          if (isFacing(WEST)) {
            xLC--;
          }
        }
      }
    }
  }
  /**
  * track y value of a castle jeroo's hops
  * for right side
  */
  public void rCHop() {
    hop();
    if (isFacing(NORTH)) {
      yRC++;
    } else {
      if (isFacing(SOUTH)) {
        yRC--;
      } else {
        if (isFacing(EAST)) {
          xRC++;
        } else {
          if (isFacing(WEST)) {
            xRC--;
          }
        }
      }
    }
  }
  /**
  * wander in a somewhat random direction, though heading towards mid of castle in terms of up and down
  * for left side jeroo
  */
  public void castleWanderL() {
    hideMovement();
    if (yLC < -3) {
      turnNorth();
      if (!isFlower(AHEAD)) {
        showMovement();
        lCHop();
      } else {
        randomDirection();
        if (isFacing(NORTH)) {
          randomDirectionEW();
        }
        if (!isFlower(AHEAD)) {
          if (!isWater(AHEAD)) {
            showMovement();
            lCHop();
          }
        }
      }
    } else {
      if (yLC > 4) {
        turnSouth();
        if (!isFlower(AHEAD)) {
          showMovement();
          lCHop();
        } else {
          randomDirection();
          if (isFacing(SOUTH)) {
            turn(LEFT);
          }
          if (!isFlower(AHEAD)) {
            if (!isWater(AHEAD)) {
              showMovement();
              lCHop();
            }
          }
        }
      } else {
        randomDirection();
        if(!isFlower(AHEAD)) {
          lCHop();
        }
      }
    }
    if (xLC == -3) {
      turnEast();
      lCHop();
    }
    showMovement();
    turn(AHEAD);
  }
  /**
  * wander in a somewhat random direction, though heading towards mid of castle in terms of up and down
  * for right side jeroo
  */
  public void castleWanderR() {
    hideMovement();
    if (yRC < -3) {
      turnNorth();
      if (!isFlower(AHEAD)) {
        showMovement();
        rCHop();
      } else {
        randomDirection();
        if (isFacing(NORTH)) {
          randomDirectionEW();
        }
        if (!isFlower(AHEAD)) {
          if (!isWater(AHEAD)) {
            showMovement();
            rCHop();
          }
        }
      }
    } else {
      if (yRC > 4) {
        turnSouth();
        if (!isFlower(AHEAD)) {
          showMovement();
          rCHop();
        } else {
          randomDirection();
          if (isFacing(SOUTH)) {
            turn(LEFT);
          }
          if (!isFlower(AHEAD)) {
            if (!isWater(AHEAD)) {
              showMovement();
              rCHop();
            }
          }
        }
      } else {
        randomDirection();
        if(!isFlower(AHEAD)) {
          rCHop();
        }
      }
    }
    if (xRC == 3) {
      turnWest();
      rCHop();
    }
    showMovement();
    turn(AHEAD);
  }
  /**
  * pick a random cardinal direction
  */
  public void randomDirection() {
    if (getRandom()) {
      if(getRandom()) {
        turnNorth();
      } else {
        turnSouth();
      }
    } else {
      if (getRandom()) {
        turnEast();
      } else {
        turnWest();
      }
    }
  }
  /**
  * picks randomly between east and WEST
  */
  public void randomDirectionEW() {
    if (getRandom()) {
      turnEast();
    } else {
      turnWest();
    }
  }
  /**
  *checks a single row of a castle for a jeroo
  */
  public void checkRow() {
    if (isWater(LEFT)) {
      turn(RIGHT);
    } else {
      turn(LEFT);
    }
    rowCheck = 0;
    while (rowCheck < 7) {
      if (!isJeroo(AHEAD)) {
        hop();
        rowCheck++;
      } else {
        aroundObstacle();
        rowCheck += 2;
        while (rowCheck < 9) {
          hop();
          rowCheck++;
        }
      }
    }
    if (rowCheck == 7) {
      turnAround();
      while (!isWater(AHEAD)) {
        hop();
      }
    }
  }
  /**
  *finds a jeroo in the castle (to shoot from)
  */
  public void bulletLocate() {
    rowCheck = 0;
    while (rowCheck != 9) {
      turnNorth();
      hop();
      checkRow();
    }
  }
  /**
  * moves bullet forward until it hits something
  * finds what material the bullet has hit and changes status accordingly
  */
  public void bulletAdvanceTarget() {
    while (bulletStatus == 0) {
      if (isWater(AHEAD)) {
        bulletStatus = 1;
      } else {
        if (isJeroo(AHEAD)) {
          bulletStatus = 2;
        } else {
          if (isFlower(AHEAD)) {
            bulletStatus = 3;
          } else {
            bulletMove();
          }
        }
      }
    }
  }
  /**
  * acts according to material bullet has hit, then returns
  */
  public void bulletAction() {
    if (bulletStatus == 3) {
      hopPick();
    } else {
      if (bulletStatus == 2) {
        give(AHEAD);
      }
    }
    bulletReturn();
    bulletStatus = 0;
  }
  /**
  * one phase of a bullet's movements
  */
  public void bulletMove() {
    hopPlant();
    turnAround();
    hop(2);
    turnAround();
    pick();
    hop(2);
  }
  /**
  * returns bullet to beginning position (bottom corner of map)
  */
  public void bulletReturn() {
    turnAround();
    pick();
    hopPick();
    hopPick();
    while (!isWater(AHEAD)) {
      if (isJeroo(AHEAD)) {
        aroundObstacle();
      } else {
        hop();
      }
    }
    turnSouth();
    while (!isWater(AHEAD)) {
      hop();
    }
    turnNorth();
  }
  /** 
  * one full turn of a bullet (will shoot from jeroo, hit something, act according to what it hits, then return)
  */
  public void bulletTurn() {
    hideMovement();
    bulletLocate();
    bulletAdvanceTarget();
    bulletAction();
    showMovement();
  }
  /**
  * checks if a jeroo is dead and updates gameStatus accordingly
  */
  public void checkDead() {
    showMovement();
    if (hasFlower()) {
      gameStatus = 1;
    }
  }
  /**
  * sets the end game status to a given character, to ensure the game actually ends
  */
  public void isDead() {
    gameStatus = 1;
  }
  /**
  * moves a single piece of the spaceship one block
  */
  public void moveBrick() {
    pickHop();
    plantHop();
  }
  /**
  * moves the bottom row of a spaceship one block
  */
  public void moveRowBot() {
    moveBrick();
    moveBrick();
    pickHop();
    plant();
  }
  /**
  * changes between the bottom and top row of a spaceship
  */
  public void rowChange() {
    if (isFacing(EAST)) {
      turn(LEFT);
      hop();
      turn(LEFT);
      hop(4);
      turnAround();
    } else {
      turn(RIGHT);
      hop();
      turn(RIGHT);
      hop(4);
      turnAround();
    }
  }
  /**
  * moves the top row of a spaceship one block
  */
  public void moveRowTop() {
    moveBrick();
    pickHop();
    plant();
  }
  /**
  * moves pilot jeroo to position for movement of spaceship to the right
  */
  public void moveRowResetR() {
    hop();
    turn(RIGHT);
    hop(2);
    turnAround();
  }
  /**
  * moves pilot jeroo to position for movmenet of spaceship to the left
  */
  public void moveRowResetL() {
    hop();
    turn(LEFT);
    hop(2);
    turnAround();
  }
  /**
  * moves pilot jeroo to pilot seat (middle of top row)
  */
  public void toSeat() {
    turnAround();
    hop();
    if (isFacing(EAST)) {
      turn(RIGHT);
    } else {
      turn(LEFT);
    }
    showMovement();
  }
  /**
  * moves entire ship, but does not specify direction
  */
  public void moveShip() {
    moveRowBot();
    rowChange();
    moveRowTop();
  }
  /**
  * moves entire ship to the right, with pilot returning to seat
  * turn(AHEAD) activates the showMovement() in toSeat()
  */
  public void moveShipR() {
    hideMovement();
    moveRowResetR();
    moveShip();
    toSeat();
    turn(AHEAD);
  }
  /**
  * moves entire ship to the left, with pilot returning to seat
  * turn(AHEAD) activates the showMovement() in toSeat()
  */
  public void moveShipL() {
    hideMovement();
    moveRowResetL();
    moveShip();
    toSeat();
    turn(AHEAD);
  }
  /**
  *moves the left ship randomly left or right, while keeping it from flying off the edge or too far into the middle
  */
  public void shipWanderL() {
    if (xLS > 7) {
      moveShipL();
      xLS--;
    } else {
      if (xLS < 0) {
        moveShipR();
        xLS++;
      } else {
        if (getRandom()) {
          moveShipL();
          xLS--;
        } else {
          moveShipR();
          xLS++;
        }
      }
    }
  }
  /**
  *moves the right ship randomly left or right, while keeping it from flying off the edge or too far into the middle
  */
  public void shipWanderR() {
    if (xRS < -7) {
      moveShipR();
      xRS++;
    } else {
      if (xRS > 0) {
        moveShipL();
        xRS--;
      } else {
        if (getRandom()) {
          moveShipL();
          xRS--;
        } else {
          moveShipR();
          xRS++;
        }
      }
    }
  }
  /** 
  * bomb jeroo locates pilot jeroo
  */
  public void bombLocate() {
    while (!isFlower(AHEAD)) {
      hop();
    }
    hop(3);
  }
  /**
  * bomb falls until it hits something
  * bomb checks what it hit and updates bombStatus accordingly
  */
  public void bombAdvanceTarget() {
    turnSouth();
    hop(2);
    while (bombStatus == 0) {
      if (isFlower(AHEAD)) {
        bombStatus = 1;
      } else {
        if (isJeroo(AHEAD)) {
          bombStatus = 2;
        } else {
          if (isWater(AHEAD)) {
            bombStatus = 3;
          } else {
            bulletMove();
          }
        }
      }
    }
  }
  /**
  * bomb acts according to what it hit
  */
  public void bombAction() {
    if (bombStatus == 1) {
      hopPick();
    } else {
      if (bombStatus == 2) {
        give(AHEAD);
      }
    }
    bombReturn();
    bombStatus = 0;
  }
  /**
  * returns bomb to resting place
  */
  public void bombReturn() {
    turnAround();
    pick();
    hopPick();
    hopPick();
    while (!isJeroo(AHEAD)) {
      hop();
    }
    bombSideCheck();
    while (!isWater(AHEAD)) {
      hop();
    }
    turnAround();
  }
  /**
  * checks which ship a bomber belongs to and turns to the water on the side of the ship
  */
  public void bombSideCheck() {
    turn(RIGHT);
    if (checkWaterFourteen()) {
    } else {
      turnAround();
    }
  }
  /**
  * checks if there is water within 14 blocks in a certain direction
  * used to check which ship a bomber belongs to
  */
  public boolean checkWaterFourteen() {
    if (!isWater(AHEAD)) {
      hop();
      if (!isWater(AHEAD)) {
        hop();
        if (!isWater(AHEAD)) {
          hop();
          if (!isWater(AHEAD)) {
            hop();
            if (!isWater(AHEAD)) {
              hop();
              if (!isWater(AHEAD)) {
                hop();
                if (!isWater(AHEAD)) {
                 hop();
                  if (!isWater(AHEAD)) {
                    hop();
                    if (!isWater(AHEAD)) {
                      hop();
                      if (!isWater(AHEAD)) {
                        hop();
                        if (!isWater(AHEAD)) {
                          hop();
                          if (!isWater(AHEAD)) {
                            hop();
                            if (!isWater(AHEAD)) {
                              hop();
                              turnAround();
                              hop(13);
                              turnAround();
                              return false;
                            } else {
                              turnAround();
                              hop(12);
                              turnAround();
                              return true;
                            }
                          } else {
                            turnAround();
                            hop(11);
                            turnAround();
                            return true;
                          }
                        } else {
                          turnAround();
                          hop(10);
                          turnAround();
                          return true;
                        }
                      } else {
                        turnAround();
                        hop(9);
                        turnAround();
                        return true;
                      }
                    } else {
                      turnAround();
                      hop(8);
                      turnAround();
                      return true;
                    }
                  } else {
                    turnAround();
                    hop(7);
                    turnAround();
                    return true;
                  }
                } else {
                  turnAround();
                  hop(6);
                  turnAround();
                  return true;
                }
              } else {
                turnAround();
                hop(5);
                turnAround();
                return true;
              }
            } else {
              turnAround();
              hop(4);
              turnAround();
              return true;
            }
          } else {
            turnAround();
            hop(3);
            turnAround();
            return true;
          }
        } else {
          turnAround();
          hop(2);
          turnAround();
          return true;
        }
      } else {
        turnAround();
        hop(1);
        turnAround();
        return true;
      }
    } else {
      return true;
    }
  }
  /**
  * one full turn of the bomb
  */
  public void bombTurn() {
    hideMovement();
    bombLocate();
    bombAdvanceTarget();
    bombAction();
    showMovement();
  }
  /**
  * turns jeroo 180 degrees
  */
  public void turnAround() {
      turn(LEFT);
      turn(LEFT);
  }
  /**
  * turns jeroo to the NORTH
  */
  public void turnNorth() {
    while (!isFacing(NORTH)) {
      turn(LEFT);
    }
  }
  /**
  * turns jeroo to the EAST 
  */
  public void turnEast() {
    while (!isFacing(EAST)) {
      turn(LEFT);
    }
  }
  /**
  * turns jeroo to the SOUTH 
  */
  public void turnSouth() {
    while (!isFacing(SOUTH)) {
      turn(LEFT);
    }
  }
  /**
  * turns jeroo to the WEST 
  */
  public void turnWest() {
      while (!isFacing(WEST)) {
        turn(LEFT);
      }
  }
  /**
  * hop followed by a plant
  */
  public void hopPlant() {
    hop();
    plant();
  }
  /**
  * plant followed by a hop
  */
  public void plantHop() {
    plant();
    hop();
  }
  /**
  * hop followed by a pick 
  */
  public void hopPick() {
    hop();
    pick();
  }
  /**
  * pick followed by a hop
  */
  public void pickHop() {
    pick();
    hop();
  }
  /**
  * move around an obstacle but face same direction
  * must be only obstacle in 3x3 area
  */
  public void aroundObstacle() {
    turn(RIGHT);
    hop();
    turn(LEFT);
    hop(2);
    turn(LEFT);
    hop();
    turn(RIGHT);
  }
  /**
  * a boolean to see if the game is over 
  */
  public boolean gameOver() {
    if (gameStatus == 0) {
      return false;
    } else {
      return true;
    }
  }
  
    // Do NOT touch the code below here
    public Jeroo() {super();}

    public Jeroo(int flowers) {super(flowers); }

    public Jeroo(int y, int x) { super(y, x); }

    public Jeroo(int y, int x, CompassDirection direction) { super (y, x, direction);}

    public Jeroo(int y, int x, int flowers) { super (y, x, flowers);}

    public Jeroo(int y, int x, CompassDirection direction, int flowers) { super(y, x, direction, flowers);}
}
