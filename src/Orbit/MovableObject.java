package Orbit;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class MovableObject
{
	// direction to move
	// 0 to 360 
	private int direction;
	// current location of object
	private double x;
	private double y;
	private int width;
	private int height;
	private int mass;
	private int r,g,b;
	private boolean detonate;
	private double originX, originY;
	private double centerX, centerY;
	public String oldFile;
	private double vectorX, vectorY;
	ArrayList <Integer> points;

	// amount of time on the screen
	private float time;
	
	// speed of object
	private double speedX;
	private double speedY;
	
	// list of possible images to use
	private ArrayList <Image> imageList;
	
	// current image being displayed
	private Image currentImage;

	// filename of current image being displayed
	public String currentFilename;
	
	// list of filenames for the images (jpg, ...)
	private String [] imageFilenames;
		
	// the JPanel that this object is being displayed on
	private JPanel mainPanel;


    public MovableObject(JPanel mainPanel, String filename, double x, double y, int width, int height)
    {
		direction = Location.NORTH;
		this.mainPanel = null; // make sure you call setPanel
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		time = 0;
		speedX = 3;
		speedY = 3;
		imageList = null;
		currentImage = null;
		imageFilenames = null;
		currentFilename = "";
		setPanel(mainPanel);
		setCurrentFilename(filename);
		centerX = x - (width/2);
		centerY = y - (height/2);
		vectorX = 0;
		vectorY=0;
		mass = 1000;
		points = new ArrayList<Integer>(20);
 	}
 	

	public void setPanel(JPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}

	public JPanel getPanel()
	{
		return mainPanel;
	}

	public void setImageFilenames(String [] filenames)
	{
		imageFilenames = filenames;
		imageList = new ArrayList<Image>();
		for (int i=0; i < filenames.length; i++)
		{
			try
			{
				Image pic = Toolkit.getDefaultToolkit().getImage(filenames[i]);
				imageList.add(pic);
			}
			catch (Exception e)
			{
			}
		}
		if (filenames.length > 0)
		{
			currentFilename = filenames[0];
			setImage();
		}
	}

	private void setImage()
	{
		if (currentFilename != null && currentFilename != "")
		{
			try
			{
				currentImage = Toolkit.getDefaultToolkit().getImage(currentFilename);
				//System.out.println("toolkit ok");
			}
			catch (Exception e)
			{
				currentImage = null;
				currentFilename = "";
				System.out.println("error getImage with toolkit");
			}
		}
	}

	public void setCurrentFilename(String filename)
	{
		currentFilename = filename;
		setImage();
	}

	public void setCurrentFilenamePosition(int position)
	{
		if (imageFilenames == null)
			return;
		if (position < imageFilenames.length && position > -1)
			{
				currentFilename = imageFilenames[position];
				setImage();
			}
		else
			{
				currentFilename = "";
				currentImage = null;
			}
	}

 

    public int getDirection()
    {
        return direction;
    }
	public double getOriginX()
	{
		return originX;
	}
	public double getOriginY()
	{
		return originY;
	}
	public double getVectorX()
	{
		return vectorX;
	}
	public double getVectorY()
	{
		return vectorY;
	}
	public void setVectorX(double a)
	{
		vectorX =a;	
	}
	public void setVectorY(double a)
	{
		vectorY =a;
	}
	public void setMass(int a)
	{
		mass=a;	
	}
	public int getMass()
	{
		return mass;
	}
	public void adjustVectorX(double a)
	{
		this.setVectorX(this.getVectorX()+a);	
	}
	public void adjustVectorY(double a)
	{
		this.setVectorY(this.getVectorY()+a);
	}
	public void setOrigin(int a, int b)
	{
		originX =a;
		originY =b;
	}
	public double getCenterX()
	{
		return centerX;
	}
	public double getCenterY()
	{
		return centerY;
	}
	public int getRed()
	{
		return r ;
	}
	public int getGreen()
	{
		return g;
	}
	public int getBlue()
	{
		return b;
	}
	public void setRed(int re)
	{
		r =re;
	}
	public void setGreen(int gr)
	{
		g =gr;
	}
	public void setBlue(int bl)
	{
		b =bl;
	}
	public void orientCenter(double a, double b)
	{
		centerX = a - (width/2);
		centerY = b - (height/2);
	}
	public void updateParticleImage()
	{
		if(this.getMass()>=1000000)
  	{
  		this.setCurrentFilename("Size5.png");		
  	}
  	else if(this.getMass() <1000000 && this.getMass() >=100000   )
  	{
  		this.setCurrentFilename("Size4.png");
 	}
 	else if(this.getMass() <100000 && this.getMass() >=10000   )
  	{	
 		this.setCurrentFilename("size3.png");
 	}
 	else if(this.getMass() <10000 && this.getMass() >=1000  )
  	{
  		this.setCurrentFilename("size2.png");
 	}
 	else if(this.getMass() <1000  )
  	{
  		this.setCurrentFilename("Size1.png");
  	}
	}
    public void setDirection(int direction)
    {
        this.direction = direction;
        this.direction = this.direction % 360;
    }
    public double getX()
    {
        return x;
    }
	
    public void setX(double x)
    {
        this.x = (x);
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = (y);
    }

    public void setXY(double x, double y)
    {
        this.x = (x);
        this.y = (y);
    }
	
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public double getSpeedX()
    {
        return speedX;
    }

    public void setSpeedX(double speedX)
    {
        this.speedX = speedX;
    }

    public double getSpeedY()
    {
        return speedY;
    }

    public void setSpeedXY(double speedX, double speedY)
    {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setSpeedY(double speedY)
    {
        this.speedY = speedY;
    }

    public float getTime()
    {
        return time;
    }
	public int getSpeed()
	{
		return (int)Math.sqrt((int)(getSpeedX()*getSpeedX()+getSpeedY()*getSpeedY()));
	}
	
		
    public void setTime(float time)
    {
        this.time = time;
    }


	public void moveUp()
	{
		setY(getY() - speedY);
	}

	public void moveDown()
	{
		setY(getY() + speedY);
	}

	public void moveLeft()
	{
		setX(getX() - speedX);
	}

	public void moveRight()
	{
		setX(getX() + speedX);
	}

	public Rectangle getRect()
	{
		return new Rectangle((int)Math.round(getX()),(int)Math.round(getY()),getWidth(),getHeight());
	}

	public boolean intersects(Rectangle rect)
	{
		if (getRect().intersects(rect))
			return true;
		return false;
	}
	
	public Point getPoint()
	{
		return new Point((int)Math.round(getX()),(int)Math.round(getY()));
	}
	public void moveTowards(MovableObject other)
	{
		double sideX = Math.abs(other.getX() - getX());
		double sideY = Math.abs(other.getY() - getY());
		double d = Math.sqrt(sideX*sideX + sideY*sideY);
		
		int speedX = (int)Math.round((getSpeed()*sideX)/d);
		int speedY = (int)Math.round((getSpeed()*sideY)/d);
		
		if (getX() < other.getX())
			setX(getX() + speedX);
		else if (getX() > other.getX())
			setX(getX() - speedX);
		
		if (getY() < other.getY())
			setY(getY() + speedY);
		else if (getY() > other.getY())
			setY(getY() - speedY);			
	
	}

	public boolean containsPoint(Point point)
	{
		if (getRect().contains(point))
			return true;
		return false;
	}
	public void draw2(Graphics g)
	{
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(getX(), getY());
        //transform.rotate(degreesToRadians(getDirection()),24,24);
        transform.rotate(degreesToRadians(getDirection()),getWidth()/2,getHeight()/2); //this does not work
        
        g2.drawImage(currentImage, transform, mainPanel); 
            
	}
    
	public double degreesToRadians(double degrees)
	{
     	return degrees*Math.PI/180;
	}
	

	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.black);

		g2.drawImage(currentImage, (int)(Math.round(x)), (int)(Math.round(y)), getWidth(),getHeight(), mainPanel);
	}
	public String toString()
	{
		return "Width: "+getWidth()+"  Height: "+getHeight()+ "  X: "+getX()+"  Y: "+getY();
	}
	
}  // end of class MovableObject

/*
boolean drawImage(Image img, int x, int y, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer)
*/

