package app.controllers;


import app.services.NetService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import static app.utils.kit.buildMessage;

public class OnlineGameCtrl extends GameCtrl {
    public static final String HEAD_NET = "net";
    public static final String HEAD_MSG = "msg";
    public static final String HEAD_CHESS = "chess";
    public static final String HEAD_GAME = "game";
    public static final String HEAD_UNDO = "undo";
    public static final String BODY_OK = "ok";
    public static final String BODY_NO = "no";

    private NetService server;
    private NetService client;


    public static NetType netType;

    public enum NetType {
        SERVER, CLIENT
    }

    @FXML
    void back() {
        super.stageController.back();
    }

    @FXML
    protected void handleCanvasClicked(MouseEvent event) {
        Position p = convertPosition(event.getX() - broadPadding, event.getY() - broadPadding);
//        System.out.println(p.toString());
        //判断棋子落点是否超出棋盘，以及是否已经落子
        if (p.x < 0 || p.y < 0 || p.x > 20 || p.y > 20 || game[p.x][p.y] != null) {
            return;
        }
        if (netType == NetType.SERVER) {
            //如果为服务器
            if (isOtherOK) {
                if (currentChess == Chess.BLACK) {
                    System.out.println("黑棋走子:" + p.toString());
                    drawChess(currentChess, p);
                    server.sendMessage(buildMessage(HEAD_CHESS, p.toString()));
                    isOtherOK = false;
                    currentChess = Chess.WHITE;
                    game[p.x][p.y] = currentChess;
                    //悔棋暂定
                    //checkWiner(p.x,p.y);
                } else {
                    System.out.println("请等待对方下棋");
                }
            } else {
                System.out.println("对方还未准备好");
            }
        } else if (netType == NetType.CLIENT) {
            //如果为客户端
            if (isOtherOK) {
                if (currentChess == Chess.WHITE) {
                    System.out.println("白棋走子:" + p.toString());
                    drawChess(currentChess,p);
                    client.sendMessage(buildMessage(HEAD_CHESS,p.toString()));
                    isOtherOK=false;
                    currentChess=Chess.BLACK;
                    game[p.x][p.y]=currentChess;

                    //checkWiner(p.x,p.y);
                } else {
                    System.out.println("请等待对方下棋");

                }
            } else {
                System.out.println("对方还未准备好");
            }
        }
        else {
            System.out.println("游戏尚未开始！");
        }
    }

    @FXML
    protected void again() {
        cleanChessBoard();
        System.out.println("重新开始");
    }

//    protected void handleCanvasClicked(MouseEvent event) {
//        Position p = convertPosition(event.getX() - broadPadding, event.getY() - broadPadding);
//        if (p.x < 0 || p.y < 0 || p.x > 20 || p.y > 20 || game[p.x][p.y] != null) {
//            return;
//        }
//        if (netType == NetType.SERVER) {
//            if (isOtherOK) {
//                if (currentChess == Chess.BLACK) {
//                    drawChess(currentChess, p);
//                    server.sendMessage(buildMessage(HEAD_CHESS, p.toString()));
//                    taContent.appendText("[主机]走子：" + markX[p.x] + "," + markY[p.y] + "\n");
//                    currentChess = Chess.WHITE;
//                    game[p.x][p.y] = Chess.BLACK;
//                    lastPostion = p;
//                    btnUndo.setDisable(false);
//                    checkWinner(p.x, p.y);
//                } else {
//                    taContent.appendText("[系统]请等待客户端走棋！\n");
//                }
//            } else {
//                taContent.appendText("[系统]客户端还没有准备好！\n");
//            }
//        } else if (netType == NetType.CLIENT) {
//            if (isOtherOK) {
//                if (currentChess == Chess.WHITE) {
//                    drawChess(currentChess, p);
//                    client.sendMessage(buildMessage(HEAD_CHESS, p.toString()));
//                    taContent.appendText("[客户端]走子：" + markX[p.x] + "," + markY[p.y] + "\n");
//                    currentChess = Chess.BLACK;
//                    game[p.x][p.y] = Chess.WHITE;
//                    lastPostion = p;
//                    btnUndo.setDisable(false);
//                    checkWinner(p.x, p.y);
//                } else {
//                    taContent.appendText("[系统]请等待主机走棋！\n");
//                }
//            } else {
//                taContent.appendText("[系统]主机还没有准备好！\n");
//            }
//        }
//
//    }

    public void initialize(){
        gc = canvas.getGraphicsContext2D();
        gapX = (canvas.getWidth() - broadPadding * 2) / 20;
        gapY = (canvas.getWidth() - broadPadding * 2) / 20;
        cleanChessBoard();
    }
}

