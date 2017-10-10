package app.controllers;

import app.services.NetService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import static app.utils.kit.buildMessage;

public class OfflineGameCtrl extends GameCtrl {


    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        gapX = (canvas.getWidth() - broadPadding * 2) / 20;
        gapY = (canvas.getWidth() - broadPadding * 2) / 20;
        chessSize = gapX * 0.8;
        cleanChessBoard();
    }

    @FXML
    protected void handleCanvasClicked(MouseEvent event) {
        Position p = convertPosition(event.getX() - broadPadding, event.getY() - broadPadding);
//        System.out.println(p.toString());
        //判断棋子落点是否超出棋盘，以及是否已经落子
        if (p.x < 0 || p.y < 0 || p.x > 20 || p.y > 20 || game[p.x][p.y] != null) {
            return;
        }
        //如果为服务器

        System.out.println("黑棋走子:" + p.toString());
        drawChess(currentChess, p);

        isOtherOK = false;
        currentChess = Chess.WHITE;
        game[p.x][p.y] = currentChess;
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
}
