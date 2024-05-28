package com.vojat.DataStructures;

public abstract class Geometry {
    public int offsetX = 0;
    public int offsetY = 0;

    public abstract boolean isSelected();

    public abstract boolean select(boolean val);
}
