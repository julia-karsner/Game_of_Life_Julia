import processing.core.PApplet;
public class Main extends PApplet {
    private static final int CELL_SIZE = 15; //static
    private Cell[][] cells;
    private boolean doEvolve = false;
    public static Main app;

    public static void main (String[] args){
        PApplet.main("Main");
    }

    public Main(){
        super();
        app = this;
    }

    public void settings(){
        size(600,600);
    }

    public void setup(){
        cells = new Cell[height/CELL_SIZE][width/CELL_SIZE];
        Rules rules = new MooreRules(new int []{3},new int []{2,3});
        frameRate(10);
        CellState cellState;
        for(int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;

                //randomizing cell state
                double i = Math.random();
                if(i <= 0.5 || row == 0 || col == 0 || row == cells.length-1 || col == cells[row].length-1){
                    cellState = CellState.DEAD;
                }
                else{
                    cellState = CellState.ALIVE;
                }

                Cell c = new Cell(x, y, CELL_SIZE, row, col, cellState, rules);
                cells[row][col] = c;
            }
        }
    }

    public void draw(){
        if(doEvolve == true) {
            applyRules();
            evolve();
        }
        for(int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col].display();
            }
        }
    }

    public void mouseClicked(){
        int row = mouseY/CELL_SIZE;
        int col = mouseX/CELL_SIZE;
        cells[row][col].handleClick();
    }

    public void keyPressed(){
        doEvolve = !doEvolve;
    }

    private void applyRules(){
        for(int row = 1; row < cells.length-1; row++) {
            for (int col = 1; col < cells[row].length-1; col++) {
                cells[row][col].applyRules(cells);
            }
        }
    }

    private void evolve(){
        for(int row = 1; row < cells.length-1; row++) {
            for (int col = 1; col < cells[row].length-1; col++) {
                cells[row][col].evolve();
            }
        }
    }
}