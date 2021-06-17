package renderer.imageRenderer;

/**
 * Pixel is an internal helper class whose objects are associated with a Render
 * object that they are generated in scope of. It is used for multithreading in
 * the Renderer and for follow up its progress.<br/>
 * There is a main follow up object and several secondary objects - one in each
 * thread.
 *
 * @author Dan
 */
class Pixel {
    private long maxRows = 0;
    private long maxCols = 0;
    private long pixels = 0;
    public volatile int row = 0;
    public volatile int col = -1;
    private long counter = 0;
    private int percents = 0;
    private long nextCounter = 0;

    /**
     * The constructor for initializing the main follow up Pixel object
     *
     * @param maxRows the amount of pixel rows
     * @param maxCols the amount of pixel columns
     */
    public Pixel(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.pixels = (long) maxRows * maxCols;
        this.nextCounter = this.pixels / 100;
    }

    /**
     * Default constructor for secondary Pixel objects
     */
    public Pixel() {
    }

    /**
     * Internal function for thread-safe manipulating of main follow up Pixel object
     * - this function is critical section for all the threads, and main Pixel
     * object data is the shared data of this critical section.<br/>
     * The function provides next pixel number each call.
     *
     * @param target target secondary Pixel object to copy the row/column of the
     *               next pixel
     * @return the progress percentage for follow up: if it is 0 - nothing to print,
     * if it is -1 - the task is finished, any other value - the progress
     * percentage (only when it changes)
     */
    private synchronized int nextP(Pixel target) {
        ++col;
        ++this.counter;
        if (col < this.maxCols) {
            target.row = this.row;
            target.col = this.col;
            return 0;
        }
        ++row;
        if (row < this.maxRows) {
            col = 0;
            target.row = this.row;
            target.col = this.col;
            return 0;
        }
        return -1;
    }

    /**
     * Public function for getting next pixel number into secondary Pixel object.
     * The function prints also progress percentage in the console window.
     *
     * @param target target secondary Pixel object to copy the row/column of the
     *               next pixel
     * @return true if the work still in progress, -1 if it's done
     */
    public boolean nextPixel(Pixel target) {
        int percent = nextP(target);
        if (percent >= 0)
            return true;
        return false;
    }
}


