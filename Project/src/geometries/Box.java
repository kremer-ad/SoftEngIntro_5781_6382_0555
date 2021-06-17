package geometries;

import primitives.*;

import java.util.List;

public class Box implements Intersectable {
    private Point3D scale;
    private Point3D position;
    private Geometries polygons;
    private BoxCollider collider;
    private Material material;
    private Color emission;

    public Box(Point3D scale, Point3D position) {
        this.scale = scale;
        this.position = position;
        this.generateBox();
    }


    /**
     * get the vertex according to the sign of the axis
     *
     * @param x is the x axis positive
     * @param y is the x axis positive
     * @param z is the x axis positive
     * @return the vertex according to the shape scale
     */
    private Point3D generateVertex(boolean x, boolean y, boolean z) {
        return new Point3D(
                this.scale.getX() * (x ? 0.5 : -0.5) + this.position.getX(),
                this.scale.getY() * (y ? 0.5 : -0.5) + this.position.getY(),
                this.scale.getZ() * (z ? 0.5 : -0.5) + this.position.getZ()
        );
    }

    private void generateBox() {
        this.polygons = new Geometries();
        //set the collider box
        this.polygons.add(

                //right
                new Polygon(
                        generateVertex(true, true, true),
                        generateVertex(true, false, true),
                        generateVertex(true, false, false),
                        generateVertex(true, true, false)
                ).setMaterial(material).setEmission(emission),
                //top
                new Polygon(
                        generateVertex(true, true, true),
                        generateVertex(false, true, true),
                        generateVertex(false, true, false),
                        generateVertex(true, true, false)
                ).setMaterial(material).setEmission(emission),
                //back
                new Polygon(
                        generateVertex(true, true, true),
                        generateVertex(false, true, true),
                        generateVertex(false, false, true),
                        generateVertex(true, false, true)
                ).setMaterial(material).setEmission(emission),
                //front
                new Polygon(
                        generateVertex(true, true, false),
                        generateVertex(false, true, false),
                        generateVertex(false, false, false),
                        generateVertex(true, false, false)
                ).setMaterial(material).setEmission(emission),
                //left
                new Polygon(
                        generateVertex(false, false, false),
                        generateVertex(false, false, true),
                        generateVertex(false, true, true),
                        generateVertex(false, true, false)
                ).setMaterial(material).setEmission(emission),
                //bottom
                new Polygon(
                        generateVertex(false, false, false),
                        generateVertex(false, false, true),
                        generateVertex(true, false, true),
                        generateVertex(true, false, false)

                ).setMaterial(material).setEmission(emission)
        );
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (!this.isIntersectingCollider(ray, maxDistance)) {
            return null;
        }
        return polygons.findGeoIntersections(ray, maxDistance);
    }

    @Override
    public Intersectable move(Vector x) {
        if (this.collider != null) {
            //this.polygons.move(x);
            this.collider.move(x);
        }
        this.position = this.position.add(x);
        this.polygons.move(x);
        return this;
    }

    @Override
    public Intersectable rotate(Vector euler) {
        return this;
    }

    @Override
    public BoxCollider getCollider() {
        return collider;
    }

    public Box setCollider(BoxCollider collider) {
        this.collider = collider;
        return this;
    }

    public Box setMaterial(Material material) {
        this.material = material;
        generateBox();
        return this;
    }

    public Box setEmission(Color emission) {
        this.emission = emission;
        generateBox();
        return this;
    }
}
