package app.controllers;

import app.services.AIService;
import app.services.NetService;
import app.utils.Position;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import static app.utils.kit.buildMessage;

public class OfflineGameCtrl extends GameCtrl {
    private AIService AI = null;
    private Boolean isOtherOK = false;


    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        gapX = (canvas.getWidth() - broadPadding * 2) / 20;
        gapY = (canvas.getWidth() - broadPadding * 2) / 20;
        chessSize = gapX * 0.8;
        AI = new AIService();
        AI.setGame(game);

        isOtherOK = true;

        cleanChessBoard();
    }

    @FXML
    protected void handleCanvasClicked(MouseEvent event) throws Exception {
        Position p = convertPosition(event.getX() - broadPadding, event.getY() - broadPadding);
        //判断棋子落点是否超出棋盘，以及是否已经落子
        if (p.x < 0 || p.y < 0 || p.x > 20 || p.y > 20 || game[p.x][p.y] != null) {
            return;
        }
        //如果为服务器

        if (isOtherOK) {
            System.out.println("黑棋走子:" + p.toString());
            drawChess(currentChess, p);

            isOtherOK = false;
            game[p.x][p.y] = currentChess;
            checkWinner(p.x, p.y);
            currentChess = Chess.WHITE;

            if (isOtherOK) {
                Position AIp = AI.chooseChess(game);
                System.out.println("白棋走子:" + AIp.toString());
                drawChess(currentChess, AIp);
                isOtherOK = false;
                game[AIp.x][AIp.y] = currentChess;
                checkWinner(AIp.x, AIp.y);
                currentChess = Chess.BLACK;
            }

        } else {
            System.out.println("请等待对方下棋");
        }
        //悔棋暂定
    }

    @FXML
    void back() {
        super.stageController.back();
    }

    @FXML
    protected void again() {
        cleanChessBoard();

        System.out.println("重新开始");
    }

    private void checkWinner(int x, int y) {
        isOtherOK = true;
        Chess thisChess = game[x][y];
        if (thisChess == null) {
            return;
        }

        int left2Right = 1 + countChessNum(Direction.LEFT, thisChess, x, y) + countChessNum(Direction.RIGHT, thisChess, x, y);
        //System.out.println("--" + left2Right);
        if (left2Right >= 5) {
            win(thisChess);
            return;
        }

        int top2Down = 1 + countChessNum(Direction.TOP, thisChess, x, y) + countChessNum(Direction.DOWN, thisChess, x, y);
        //System.out.println("|" + top2Down);
        if (top2Down >= 5) {
            win(thisChess);
            return;
        }

        int topLeft2RightDown = 1 + countChessNum(Direction.TOP_LEFT, thisChess, x, y) + countChessNum(Direction.RIGHT_DOWN, thisChess, x, y);
        //System.out.println("\\" + topLeft2RightDown);
        if (topLeft2RightDown >= 5) {
            win(thisChess);
            return;
        }

        int topRight2DownLeft = 1 + countChessNum(Direction.TOP_RIGHT, thisChess, x, y) + countChessNum(Direction.DOWN_LEFT, thisChess, x, y);
        //System.out.println("/" + topRight2DownLeft);
        if (topRight2DownLeft >= 5) {
            win(thisChess);
        }
    }

    private void win(Chess thisChess) {
        isOtherOK = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, thisChess == Chess.BLACK ? "黑棋获胜！" : "白棋获胜", ButtonType.OK);
        alert.show();
        currentChess = Chess.BLACK;
        alert.setOnCloseRequest(event -> {
            cleanChessBoard();
            game = new Chess[21][21];
            System.out.println("新的一局开始了");
            currentChess = Chess.BLACK;
            isOtherOK = true;

        });

    }

    private int countChessNum(Direction direction, Chess thisChess, int x, int y) {
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
}
