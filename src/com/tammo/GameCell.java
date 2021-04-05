package com.tammo;

class GameCell {
    private final GameBoardCoordinate position;
    private Boolean alive;

    GameCell(GameBoardCoordinate position) {
        this.position = position;
        this.alive = false;
    }
    
    GameBoardCoordinate getPosition() {
        return position;
    }
    
    Boolean isAlive() {
        return this.alive;
    }
    
    void toggleAlive() {
        this.alive = !this.alive;
    }
    
    void updateAliveCell(int aliveNeighbors) {
        if(aliveNeighbors < 2 || 3 < aliveNeighbors) {
            toggleAlive();
        }
    }
    
    void updateDeadCell(int aliveNeighbors) {
        if(aliveNeighbors == 3) {
            toggleAlive();
        }
    }
    
    void updateCell(int aliveNeighbors) {
        if(this.alive) {
            updateAliveCell(aliveNeighbors);
        } else {
            updateDeadCell(aliveNeighbors);
        }
    }

    @Override
    public String toString() {
        return this.isAlive()? "X" : " ";
    }
}