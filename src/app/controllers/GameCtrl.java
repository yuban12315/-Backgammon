package app.controllers;

import com.sun.deploy.util.BlackList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class GameCtrl {
    @FXML
    Canvas canvas;
    @FXML
    TextField tfIP;
    @FXML
    TextField tfMessage;
    @FXML
    TextArea taContent;
    @FXML
    Label lbIP;
    @FXML
    Button btnConnect;
    @FXML
    Button btnStart;
    @FXML
    Button btnSend;
    @FXML
    Button btnUndo;

    protected Color colorChessboard = Color.valueOf("#FBE39B");
    protected Color colorLine = Color.valueOf("#884B09");
//    protected Color colorMark = Color.valueOf("#FF7F27");
    protected Color colorMark=Color.valueOf("000");
    protected GraphicsContext gc;
    protected double gapX, gapY;
    protected double chessSize;
    protected double broadPadding = 27;
    //棋盘外的标识
    protected String[] markX = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"};
    protected String[] markY = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
    protected StageController stageController;
    protected Boolean isOtherOK=false; //他人（其他玩家|电脑）是否已经准备好
    protected Chess[][] game = new Chess[21][21]; //棋盘，成员为Chess的enum
    protected Chess currentChess= Chess.BLACK;//黑棋先下

    public enum Chess {
        BLACK, WHITE
    }

    GameCtrl() {
        stageController = StageController.getInstance();
    }


    protected class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }
    }

    protected void drawChess(Chess chess,Position p){
        double x = p.x * gapX + broadPadding;
        double y = p.y * gapY + broadPadding;
        switch (chess){
            case BLACK:
                gc.setFill(Color.BLACK);
                gc.fillOval(x - chessSize / 2, y - chessSize / 2, chessSize, chessSize);
                break;
            case WHITE:
                gc.setFill(Color.WHITE);
                gc.fillOval(x - chessSize / 2, y - chessSize / 2, chessSize, chessSize);
                break;
        }
    }

    //初始化
    public abstract void initialize() ;

    //绘制棋盘
    void cleanChessBoard() {
        gc.setFill(colorChessboard);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(colorLine);
        for (int i = 0; i <= 20; i++) {
            gc.strokeLine(i * gapX + broadPadding, broadPadding, i * gapX + broadPadding, canvas.getHeight() - broadPadding);
            gc.strokeLine(broadPadding, i * gapY + broadPadding, canvas.getWidth() - broadPadding, i * gapY + broadPadding);
        }

        gc.setFill(colorMark);
        gc.setFont(Font.font(broadPadding / 2));
        for (int i = 0; i <= 20; i++) {
            gc.fillText(markX[i], i * gapX + broadPadding - 5, broadPadding - 5);
            //gc.fillText(markX[i], i * gapX + broadPadding - 5, canvas.getHeight() - 5);
            gc.fillText(markY[i], 5, gapY * i + broadPadding + 5);
            //gc.fillText(markY[i], canvas.getWidth() - broadPadding + 5, gapY * i + broadPadding + 5);
        }
    }

    Position convertPosition(double x, double y) {
        int pX = (int) ((x + gapX / 2) / gapX);
        int pY = (int) ((y + gapY / 2) / gapY);
        return new Position(pX, pY);
    }
}
