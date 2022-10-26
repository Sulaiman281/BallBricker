package com.main.code;

import java.awt.*;


public class Brick2 extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(34, 53, 178).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;






    public Brick2(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
