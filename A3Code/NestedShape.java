/*
 *	===============================================================================
 *	RectangleShape.java : A shape that is a nested shape.
 *  YOUR UPI: GRAM658
 *	=============================================================================== */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.nio.file.Path;
import java.util.ArrayList;

class NestedShape extends RectangleShape{
    private ArrayList<Shape> innerShapes = new ArrayList<Shape>();
    public NestedShape(){
        super();
        createInnerShape(PathType.BOUNCING, ShapeType.RECTANGLE);
    }
     
    public NestedShape(int x, int y, int width, int height, int panelWidth, int panelHeight, Color color, Color borderColor, PathType pt){
        super(x, y, width, height, panelWidth, panelHeight, color, borderColor, pt);
        panelHeight = height;
        panelWidth = width;
        createInnerShape(0, 0, width/2, height/2, color, borderColor, PathType.BOUNCING, ShapeType.RECTANGLE);
    }
    public NestedShape(int width, int height){
        super(0, 0, width, height, DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT, Color.black, Color.black, PathType.BOUNCING);
    }
    public Shape createInnerShape(int x, int y, int w, int h, Color c, Color bc, PathType pt, ShapeType st){
        Shape innerShape = null;
        if (st ==  ShapeType.RECTANGLE)
            innerShape = new RectangleShape(x, y, w, h, width, height, c, bc, pt);
        if (st == ShapeType.OVAL)
            innerShape = new OvalShape(x, y, w, h, width, height, c, bc, pt);
        if (st == ShapeType.NESTED)
            innerShape = new NestedShape(x, y, w, h, width, height, c, bc, pt);
        innerShape.parent = this;
        innerShapes.add(innerShape);
        return innerShape;
    }
    public Shape createInnerShape(PathType pt, ShapeType st){
        Shape inner = null;
        if (st == ShapeType.RECTANGLE)
            inner = new RectangleShape(0, 0, width/2, height/2, width, height, color, borderColor, pt);
        if(st == ShapeType.OVAL)
            inner = new OvalShape(0, 0, width/2, height/2, width, height, color, borderColor, pt);
        if (st == ShapeType.NESTED)
            inner = new NestedShape(0, 0, width/2, height/2, width, height, color, borderColor, pt);
        inner.parent = this;
        innerShapes.add(inner);
        return inner;
    }
    public Shape getInnerShapeAt(int index){
        return innerShapes.get(index);
    }
    public int getSize(){
        return innerShapes.size();
    }
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        g.translate(x, y);
        for(Shape innerShape: innerShapes){
            innerShape.draw(g);
            if (innerShape.isSelected())
                innerShape.drawHandles(g);
        innerShape.drawString(g);
        }
        g.translate(-x, -y);
    }
    @Override
    public void move(){
        super.move();
        for(Shape s: innerShapes){
            s.move();
        }
    }
    public int indexOf(Shape s){
        return innerShapes.indexOf(s);
    }
    public void addInnerShape(Shape s){
        innerShapes.add(s);
        s.setParent(this);
    }
    public void removeInnerShape(Shape s){
        innerShapes.remove(s);
        s.setParent(null);
    }
    public void removeInnerShapeAt(int index){
        Shape s = innerShapes.get(index);
        innerShapes.remove(index);
        s.setParent(null);
    }
    public ArrayList<Shape> getAllInnerShapes(){
        return innerShapes;
    }
}

 