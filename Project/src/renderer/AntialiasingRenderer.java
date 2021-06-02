package renderer;

import primitives.Color;
import primitives.Ray;

public class AntialiasingRenderer extends Render {
    private static final int RAYS_IN_BEAM = 60;

    public AntialiasingRenderer() {
        super();
    }

    @Override
    public void renderImage() {
        checkForVariables();

        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
                Ray[] rays = camera.constructRaysThroughPixel(writer.getNx(), writer.getNy(), i, j, RAYS_IN_BEAM);
                Color sum = Color.BLACK;
                for (var ray : rays) {
                    sum = sum.add(rayTracer.traceRay(ray));
                }
                writer.writePixel(i, j, sum/*.scale(1 / RAYS_IN_BEAM)*/);
            }
        }
    }
}
