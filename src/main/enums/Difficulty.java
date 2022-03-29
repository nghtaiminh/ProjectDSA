package main.enums;

public enum Difficulty {
    EASY {
        @Override
        public int getNumberOfMines() {
            return 10;
        }
        @Override
        public int getGridWidth() {
            return 9;
        }
        @Override
        public int getGridHeight() {
            return 9;
        }
    },
    MEDIUM {
        @Override
        public int getNumberOfMines() {
            return 30;
        }
        @Override
        public int getGridWidth() {
            return 16;
        }
        @Override
        public int getGridHeight() {
            return 16;
        }
    },
    HARD {
        @Override
        public int getNumberOfMines() {
            return 50;
        }
        @Override
        public int getGridWidth() {
            return 30;
        }
        @Override
        public int getGridHeight() {
            return 16;
        }
    };

    public abstract int getNumberOfMines();
    public abstract int getGridWidth();
    public abstract int getGridHeight();

}
