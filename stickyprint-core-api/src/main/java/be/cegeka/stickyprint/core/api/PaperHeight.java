package be.cegeka.stickyprint.core.api;

public enum PaperHeight {

    HEIGHT_58MM(372), HEIGHT_80MM(512);


    private int sizeInPixels;

    PaperHeight(int sizeInPixels) {
        this.sizeInPixels = sizeInPixels;
    }

    public int getSizeInPixels() {
        return sizeInPixels;
    }
}
