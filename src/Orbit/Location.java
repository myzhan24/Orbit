package Orbit;

public class Location
{
    public static final int NORTH = 0;
    public static final int NORTHEAST = 45;
    public static final int NORTHWEST = 305;
    public static final int EAST  = 90;
    public static final int SOUTH = 180;
    public static final int SOUTHEAST = 135;
    public static final int SOUTHWEST = 225;
    public static final int WEST  = 270;
    public static final int HALF_LEFT  = -45;
    public static final int HALF_RIGHT  = 45;
    public static final int HALF_CIRCLE  = 180;
    public static final int LEFT   = -90;
    public static final int RIGHT  = 90;

    int row;
    int col;

    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public Location getAdjacentLocation(int direction)
    {
        int r = getRow();
        int c = getCol();
        if (direction == Location.NORTH)
        {
            r--;
        }
        else if (direction == Location.NORTHEAST)
        {
            r--;
            c++;
        }
        else if (direction == Location.NORTHWEST)
        {
            r--;
            c--;
        }
        else if (direction == Location.EAST)
        {
            c++;
        }
        else if (direction == Location.SOUTH)
        {
            r++;
        }
        else if (direction == Location.SOUTHEAST)
        {
            r++;
            c++;
        }
        else if (direction == Location.SOUTHWEST)
        {
            r++;
            c--;
        }
        else if (direction == Location.WEST)
        {
            c--;
        }
        return new Location(r,c);
    }
    
    public String toString()
    {
    	return "("+getRow()+", "+getCol()+")";
    }

}  // end of class Location

