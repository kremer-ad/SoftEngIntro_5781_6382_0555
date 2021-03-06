package renderer.imageRenderer;

import primitives.Color;
import primitives.Ray;

public class AntialiasingRenderer extends Render {
    private static final int RAYS_IN_BEAM = 81;

    public AntialiasingRenderer() {
        super();
    }

    @Override
    public void renderImage() {
        checkForVariables();

        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
//<<<<<<< HEAD
                Ray[] rays = camera.constructRaysThroughPixel(writer.getNx(), writer.getNy(), i, j, RAYS_IN_BEAM);
                Color sum = Color.BLACK;
                for (var ray : rays) {
                    sum = sum.add(rayTracer.traceRay(ray));
                }
                writer.writePixel(i, j, sum.reduce(RAYS_IN_BEAM));
//=======
                renderPixel(i,j);
//>>>>>>> 325714daf4b198e202dc63e1ff6642e8579a8e59
            }
        }
    }

    /**
     * render one pixel to the image writer
     * @param x the pixel number from the right
     * @param y the pixel number from the top
     */
    @Override
    protected void renderPixel(int x,int y){
        Ray[] rays = camera.constructRaysThroughPixel(writer.getNx(), writer.getNy(), x, y, RAYS_IN_BEAM);
        Color sum = Color.BLACK;
        for (var ray : rays) {
            sum = sum.add(rayTracer.traceRay(ray));
        }
        writer.writePixel(x, y, sum.reduce( RAYS_IN_BEAM));
    }
}
