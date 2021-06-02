package renderer;

import primitives.Color;
import primitives.Ray;

public class AntialiasingRenderer extends Render{
    public AntialiasingRenderer() {
        super();
    }

    @Override
    public void renderImage() {
        super.checkForVariables();

        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
                Ray ray = camera.constructRayThroughPixel(writer.getNx(), writer.getNy(), i, j);
                Color colorToWrite = rayTracer.traceRay(ray);
                writer.writePixel(i, j, colorToWrite);
            }
        }
    }
}
