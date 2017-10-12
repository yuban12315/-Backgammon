package app.services;

import app.controllers.GameCtrl;
import app.utils.Position;

public class AIService {
    private GameCtrl.Chess[][] game;
    private GameCtrl.Chess thisChess;

    private enum Direction {
        TOP_LEFT, TOP, TOP_RIGHT, RIGHT, RIGHT_DOWN, DOWN, DOWN_LEFT, LEFT
    }

    public AIService() {

    }

    public void setGame(GameCtrl.Chess[][] game) {
        this.game = game;
    }



    public Position chooseChess(int x, int y) {
//  version 0.1
//        for(int i=0;i<21;i++){
//            for (int k=0;k<21;k++){
//                if(game[i][k]==null){
//                    return new Position(i,k);
//                }
//            }
//        }

        ;
//attack 2.0 如果有致胜棋(>=4)，直接攻击，然后进行围堵，如果没有需要围堵的棋，则考虑进攻（填三）
//left2right

//version 1.0
        ;
        //如果有致胜棋(>=4)，直接攻击
        Position position = this.findChessByType(4);
        if (position != null) return position;

        //defence 围堵,超过3进行堵
        thisChess = GameCtrl.Chess.BLACK;

        int left2Right = 1 + countChessNum(Direction.LEFT, thisChess, x, y) + countChessNum(Direction.RIGHT, thisChess, x, y);
        int top2Down = 1 + countChessNum(Direction.TOP, thisChess, x, y) + countChessNum(Direction.DOWN, thisChess, x, y);
        int topLeft2RightDown = 1 + countChessNum(Direction.TOP_LEFT, thisChess, x, y) + countChessNum(Direction.RIGHT_DOWN, thisChess, x, y);
        int topRight2DownLeft = 1 + countChessNum(Direction.TOP_RIGHT, thisChess, x, y) + countChessNum(Direction.DOWN_LEFT, thisChess, x, y);
        int temp = 0;

        if (left2Right >= 3) {
            for (int i = x; i >= 0; i--) {
                if (game[i][y] == GameCtrl.Chess.WHITE) break;
                if (game[i][y] == null) {
                    return new Position(i, y);
                }
            }
            for (int i = x; i <= 20; i++) {
                if (game[i][y] == GameCtrl.Chess.WHITE) break;
                if (game[i][y] == null) {
                    return new Position(i, y);
                }
            }
        } else if (top2Down >= 3) {
            for (int i = y; i >= 0; i--) {
                if (game[x][i] == GameCtrl.Chess.WHITE) break;
                if (game[x][i] == null) {
                    return new Position(x, i);
                }
            }
            for (int i = y; i <= 20; i++) {
                if (game[x][i] == GameCtrl.Chess.WHITE) break;
                if (game[x][i] == null) {
                    return new Position(x, i);
                }
            }
        } else if (topLeft2RightDown >= 3) {
            temp = y;
            for (int i = x - 1; i >= 0; i--) {
                temp--;
                if (temp < 0) break;
                if (game[i][temp] == GameCtrl.Chess.WHITE) break;
                if (game[i][temp] == null) return new Position(i, temp);
            }
            temp = y;
            for (int i = x + 1; i <= 20; i++) {
                temp++;
                if (temp > 20) break;
                if (game[i][temp] == GameCtrl.Chess.WHITE) break;
                if (game[i][temp] == null) return new Position(i, temp);
            }
        } else if (topRight2DownLeft >= 3) {
            temp = y;
            for (int i = x - 1; i >= 0; i--) {
                temp++;
                if (temp > 20) break;
                if (game[i][temp] == GameCtrl.Chess.WHITE) break;
                if (game[i][temp] == null) return new Position(i, temp);
            }
            temp = y;
            for (int i = x + 1; i <= 20; i++) {
                temp--;
                if (temp < 0) break;
                if (game[i][temp] == GameCtrl.Chess.WHITE) break;
                if (game[i][temp] == null) return new Position(i, temp);
            }
        }

        //攻击

        position = this.findChessByType(3);
        if (position != null) return position;

        position = this.findChessByType(2);
        if (position != null) return position;

        position = this.findChessByType(1);
        if (position != null) return position;


        //default
        for (int i = 10; i >= 0; i--) {
            for (int k = 10; k >= 0; k--) {
                if (game[i][k] == null)
                    return new Position(i, k);
            }
        }
        return new Position(19, 1);
    }

    private int countChessNum(Direction direction, GameCtrl.Chess thisChess, int x, int y) {
        int num = 0;
        switch (direction) {
            case TOP_LEFT:
                if (x - 1 >= 0 && y - 1 >= 0 && thisChess == game[x - 1][y - 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x - 1, y - 1);
                }
                break;
            case TOP:
                if (y - 1 >= 0 && thisChess == game[x][y - 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x, y - 1);
                }
                break;
            case TOP_RIGHT:
                if (x + 1 <= 20 && y - 1 >= 0 && thisChess == game[x + 1][y - 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x + 1, y - 1);
                }
                break;
            case RIGHT:
                if (x + 1 <= 20 && thisChess == game[x + 1][y]) {
                    num++;
                    num += countChessNum(direction, thisChess, x + 1, y);
                }
                break;
            case RIGHT_DOWN:
                if (x + 1 <= 20 && y + 1 <= 20 && thisChess == game[x + 1][y + 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x + 1, y + 1);
                }
                break;
            case DOWN:
                if (y + 1 <= 20 && thisChess == game[x][y + 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x, y + 1);
                }
                break;
            case DOWN_LEFT:
                if (x - 1 >= 0 && y + 1 <= 20 && thisChess == game[x - 1][y + 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x - 1, y + 1);
                }
                break;
            case LEFT:
                if (x - 1 >= 0 && thisChess == game[x - 1][y]) {
                    num++;
                    num += countChessNum(direction, thisChess, x - 1, y);
                }
                break;
        }
        return num;
    }

    Position findChessByType(int type) {
        System.out.println("type:"+type);
        int count = 0;
        boolean flag = false;
        Position firstP = new Position(0, 0);//记录可能的头位置


        //top2down
        for (int k = 0; k <= 20; k++) {
            for (int i = 0; i <= 20; i++) {
                if (game[i][k] == GameCtrl.Chess.WHITE) {
                    if (!flag) {
                        firstP.x = i - 1;
                        firstP.y = k;
                    }
                    flag = true;
                    count++;
                    //System.out.println("white" + i + ":" + k + ",count:" + count);
                    if (count >= type) {
                        System.out.println("find:(" + i + "," + k + ")");
                        if (firstP.x >= 0) {
                            if (game[firstP.x][firstP.y] == null) return firstP;
                        }
                        if (i + 1 <= 20) {
                            if (game[i + 1][k] == null) return new Position(i + 1, k);
                        }
                    }
                } else {
                    flag = false;
                    count = 0;
                }
            }
        }
        //left2right
        for (int i = 0; i <= 20; i++) {
            for (int k = 0; k <= 20; k++) {
                if (game[i][k] == GameCtrl.Chess.WHITE) {
                    if (!flag) {
                        firstP.x = i;
                        firstP.y = k - 1;
                    }
                    flag = true;
                    count++;
                    //System.out.println("white"+i+":"+k+",count:"+count);
                    if (count >= type) {
                        System.out.println("find:(" + i + "," + k + ")");
                        if (firstP.y >= 0) {
                            if (game[firstP.x][firstP.y] == null) return firstP;
                        }
                        if (k + 1 <= 20) {
                            if (game[i][k + 1] == null) return new Position(i, k + 1);
                        }
                    }
                } else {
                    flag = false;
                    count = 0;
                }
            }
        }
        //topLeft2downRight
        int tempX,tempY;

        tempX=0;//
        for (int x=0;x<=20;x++){
            for (int y=0;y<=16;y++){
                if(game[x][y]== GameCtrl.Chess.WHITE){
                    if(!flag){
                        flag=true;
                    }
                    flag=true;
                    count++;
                    if(count>=type){
                        System.out.println("find:(" + x + "," + y + ")");

                    }
                }
                else {
                    flag=false;
                    count=0;
                }
            }
        }
        for(int x=0;x<16;x++){

        }
        return null;
    }


}
