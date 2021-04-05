package com.tammo;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class GameBoard implements Iterable<GameCell> {
    final private GameBoardCoordinate start = new GameBoardCoordinate(0, 0);
    private final GameBoardCoordinate end;
    private List<List<GameCell>> board;
    
    GameBoard(int width, int height) throws IllegalArgumentException {
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("The width and the height of the board must be bigger than 0.");
        }
        this.end = new GameBoardCoordinate(width, height);
        initBoard();
    }

    int getHeight() {
        return this.end.y;
    }

    int getWidth() {
        return this.end.x;
    }
    
    void initBoard() {
        this.board = new ArrayList<>(this.getHeight());
        for(int h = 0; h < this.getHeight(); h++) {
            List<GameCell> row = new ArrayList<>(this.getWidth());
            for(int w = 0; w < this.getWidth(); w++) {
                row.add(new GameCell(new GameBoardCoordinate(w, h)));
            }
            this.board.add(row);
        }
    }

    Boolean isInsideBoard(GameBoardCoordinate position) {
        return this.start.x <= position.x
                && position.x < this.end.x
                && this.start.y <= position.y
                && position.y < this.end.y;
    }

    List<GameCell> filterAliveCells(List<GameCell> cells) {
        return cells.stream()
                .filter(GameCell::isAlive)
                .collect(Collectors.toList());
    }

    List<GameBoardCoordinate> filterToCoordinatesInBoard(List<GameBoardCoordinate> neighborCoordinates) {
        return neighborCoordinates.stream()
                .filter(this::isInsideBoard)
                .collect(Collectors.toList());
    }

    GameCell getCell(GameBoardCoordinate coordinate) {
        return this.board.get(coordinate.y).get(coordinate.x);
    }
    
    int countAliveNeighbors(GameBoardCoordinate position) {
        List<GameBoardCoordinate> neighborCoordinates = position.getNeighboringCoordinates();
        List<GameBoardCoordinate> neighborCoordinatesInBoard = this.filterToCoordinatesInBoard(neighborCoordinates);
        List<GameCell> neighborCells = neighborCoordinatesInBoard.stream()
                .map(this::getCell)
                .collect(Collectors.toList());
        List<GameCell> aliveNeighborCells = this.filterAliveCells(neighborCells);

        return aliveNeighborCells.size();
    }

    GameBoard updatedBoard() {
        GameBoard nextBoard = new GameBoard(this.getWidth(), this.getHeight());
        for(GameCell cell : this) {
            GameBoardCoordinate position = cell.getPosition();
            GameCell nextBoardCell = nextBoard.board.get(position.y).get(position.x);
            if(cell.isAlive()) { nextBoardCell.toggleAlive(); }
            nextBoardCell.updateCell(countAliveNeighbors(cell.getPosition()));
        }
        return nextBoard;
    }

    void togglePosition(int x, int y) { toggleCoordinate(new GameBoardCoordinate(x, y)); }

    void toggleCoordinate(GameBoardCoordinate coordinate) { getCell(coordinate).toggleAlive(); }

    @Override
    public void forEach(Consumer<? super GameCell> action) {
        for(GameCell cell : this) {
            action.accept(cell);
        }
    }

    @Override
    public Iterator<GameCell> iterator() {
        List<GameCell> list = board.stream()
                .reduce(new ArrayList<>()
                        , (a, b) -> {
                            a.addAll(b);
                            return a;
                        });
        return list.iterator();
    }

    private void toStringAppendFrameStart(StringBuilder stringBuilder) {
        stringBuilder.append('/');
        stringBuilder.append("-".repeat(Math.max(0, this.end.x)));
        stringBuilder.append("\\\n");
    }

    private void toStringAppendRowStart(StringBuilder stringBuilder) {
        stringBuilder.append("|");
    }

    private void toStringAppendRowEnd(StringBuilder stringBuilder) {
        stringBuilder.append("|\n");
    }

    private void toStringAppendFrameEnd(StringBuilder stringBuilder) {
        stringBuilder.append('\\');
        stringBuilder.append("-".repeat(Math.max(0, this.end.x)));
        stringBuilder.append('/');
    }

    @Override
    public String toString() {
        // create a string for the board including a frame and newlines
        StringBuilder returnStringBuilder = new StringBuilder((this.end.y + 2) * (this.end.x + 3));
        toStringAppendFrameStart(returnStringBuilder);

        for(List<GameCell> row : this.board) {
            toStringAppendRowStart(returnStringBuilder);
            for(GameCell cell : row) {
                returnStringBuilder.append(cell);
            }
            toStringAppendRowEnd(returnStringBuilder);
        }

        toStringAppendFrameEnd(returnStringBuilder);
        return returnStringBuilder.toString();
    }
}