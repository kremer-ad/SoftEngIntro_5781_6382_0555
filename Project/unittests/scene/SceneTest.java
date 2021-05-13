package scene;

import elements.lights.AmbientLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static org.junit.Assert.assertEquals;
import static primitives.Util.BASE_FILES_PATH;


public class SceneTest {
    private static final String FOLDER_PATH = BASE_FILES_PATH + "/json";

    @Test
    public void saveJSON() throws IOException {
        //TC01: write basic scene to file
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50D),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down right

        scene.SaveJSON("data.json");
        String expectedResult = "{\"ambient-light\":{\"intensity\":{\"r\":255.0,\"b\":191.0,\"g\":191.0,\"type\":\"color\"},\"type\":\"ambient-light\"},\"background\":{\"r\":75.0,\"b\":90.0,\"g\":127.0,\"type\":\"color\"},\"geometries\":{\"data\":[{\"center\":{\"x\":0.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"},\"type\":\"sphere\",\"radius\":50.0},{\"plane\":{\"normal\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"type\":\"vector\"},\"type\":\"plane\",\"q0\":{\"x\":-100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"}},\"vertices\":[{\"x\":-100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":0.0,\"y\":100.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":-100.0,\"y\":100.0,\"z\":-100.0,\"type\":\"point3d\"}],\"type\":\"triangle\"},{\"plane\":{\"normal\":{\"x\":0.0,\"y\":0.0,\"z\":-1.0,\"type\":\"vector\"},\"type\":\"plane\",\"q0\":{\"x\":100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"}},\"vertices\":[{\"x\":100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":0.0,\"y\":100.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":100.0,\"y\":100.0,\"z\":-100.0,\"type\":\"point3d\"}],\"type\":\"triangle\"},{\"plane\":{\"normal\":{\"x\":0.0,\"y\":0.0,\"z\":-1.0,\"type\":\"vector\"},\"type\":\"plane\",\"q0\":{\"x\":-100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"}},\"vertices\":[{\"x\":-100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":0.0,\"y\":-100.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":-100.0,\"y\":-100.0,\"z\":-100.0,\"type\":\"point3d\"}],\"type\":\"triangle\"},{\"plane\":{\"normal\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"type\":\"vector\"},\"type\":\"plane\",\"q0\":{\"x\":100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"}},\"vertices\":[{\"x\":100.0,\"y\":0.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":0.0,\"y\":-100.0,\"z\":-100.0,\"type\":\"point3d\"},{\"x\":100.0,\"y\":-100.0,\"z\":-100.0,\"type\":\"point3d\"}],\"type\":\"triangle\"}],\"type\":\"geometries\"},\"name\":\"Test scene\",\"type\":\"scene\"}";
        String result = Files.readString(Paths.get(FOLDER_PATH + "/data.json"));
        assertEquals("write basic scene to json", expectedResult, result);
    }
}