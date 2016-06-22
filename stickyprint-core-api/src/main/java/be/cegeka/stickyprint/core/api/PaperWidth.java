package be.cegeka.stickyprint.core.api;

public enum PaperWidth {

    WIDTH_80MM(512), //ongeveer normale post-it
    WIDTH_125MM(800), //ongeveer grote post-it
    WIDTH_160MM(1024), //gigantisch
    ;


    private int sizeInPixels;

    PaperWidth(int sizeInPixels) {
        this.sizeInPixels = sizeInPixels;
    }

    public int getSizeInPixels() {
        return sizeInPixels;
    }
}
