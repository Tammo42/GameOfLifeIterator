package com.tammo;

import java.util.ArrayList;
import java.util.List;

class GameBoardCoordinate {
    int x;
    int y;
    
    GameBoardCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    List<GameBoardCoordinate> getNeighboringCoordinates() {
        List<GameBoardCoordinate> neighboringCoordinates = new ArrayList<>(8);
        neighboringCoordinates.add(new GameBoardCoordinate(this.x-1, this.y-1));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x  , this.y-1));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x+1, this.y-1));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x-1, this.y  ));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x+1, this.y  ));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x-1, this.y+1));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x  , this.y+1));
        neighboringCoordinates.add(new GameBoardCoordinate(this.x+1, this.y+1));
        return neighboringCoordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoardCoordinate that = (GameBoardCoordinate) o;
        return x == that.x && y == that.y;
    }
}