package renderer;

import org.junit.Test;
import primitives.Color;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void writeToImage() {
        //TC01: write simple image
        ImageWriter writer=new ImageWriter("simpleImage",801,501);
        fillImage(writer,new Color(0,0,0));
        writeStokes(writer,50,new Color(255,255,255));
        writer.writeToImage();

    }

    @Test
    public void writePixel() {

    }

    private void writeStokes(ImageWriter writer,int distance,Color color){
        //print horizontal lines:
        for(int i=0;i< writer.getNy();i+=distance){
            for(int j=0;j<writer.getNx();j++){
                writer.writePixel(j,i,color);
            }
        }

        //print vertical lines
        for(int i=0;i< writer.getNx();i+=distance){
            for(int j=0;j<writer.getNy();j++){
                writer.writePixel(i,j,color);
            }
        }
    }

    private void fillImage(ImageWriter writer,Color color){
        for(int i=0;i<writer.getNx();i++){
            for(int j=0;j< writer.getNy();j++){
                writer.writePixel(i,j,color);
            }
        }
    }

}