package app.services;

import app.controllers.GameCtrl;
import app.utils.Position;

public class AIService {
    private GameCtrl.Chess [][] game;

    public AIService(){

    }

    public void setGame(GameCtrl.Chess[][] game) {
        this.game = game;
    }

    public Position chooseChess(GameCtrl.Chess[][] game)  {
        this.game=game;
        for(int i=0;i<21;i++){
            for (int k=0;k<21;k++){
                if(game[i][k]==null){
                    return new Position(i,k);
                }
            }
        }
        return new Position(19,19);
    }


}
