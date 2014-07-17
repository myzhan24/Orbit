package Orbit;

//A physics simulation of the behavior of masses orbiting, with capabilities of manipulation of the physical
//universe. Not to physical scale, gravitational constant of 6.67.
//Author: Matthew Zhan

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Orbit extends JFrame 
{
  // screen constants
  private int screenWidth  = 1650;
  private int screenHeight = 950;
  private boolean[] keys;
  private int speedScale=25;
  private int mouseX=0;
  private int mouseY=0;
  private int originX =0;
  private int originY= 0;
  private int finalX=0;
  private int finalY=0;
  private int r = 255;
  private int gre = 255;
  private int b = 255;
  private int mass = 10000;
  private int root = 1;
  private int numRandom = 10;
  private int explosionCount = 5;
  private int path2Trail = 20;
  private int path1Trail = 20;
  private int preX;
  private int preY;
  private int max;
  double yDis,xDis;
  private int followIndex = 0;
  private double preVx;
  private double preVy;
  private int preMass;
  private double bounceValue = 1.0;
  private boolean pathing = false;
  private boolean pathing2 =false;
  private boolean showLine = false;
  private boolean absorb = false;
  private boolean noImg = false;
  private boolean lightShow = false;
  private boolean rDir, greDir, bDir = false;
  private boolean bouncing = false;
  private boolean goofy = false;
  // for buffering
  private BufferedImage back;
  // ***** declaration of JFrame variables *****

  // define a mainPanel for components
  JPanel mainPanel;
	String startText="";
  // define JPanels for a BorderLayout
  JPanel     northPanel;   // this is the message panel
  SouthPanel southPanel;   // put your buttons on this panel
  JPanel     westPanel;    // this panel will be empty for now
  boolean    showWestPanel =true;
  JPanel     eastPanel;    // this panel will be empty for now
  boolean    showEastPanel = false;
  boolean help=true;
  boolean follow=false;
  
  DrawPanel  centerPanel;  // this will be the panel with all the drawing of MovableObjects
  // title in northPanel
  JLabel northText;
  JLabel fillerLabel1 = new JLabel("           ");
  // Buttons
  JButton runButton;
  JButton stopButton;
  JButton exitButton;
  JButton clearButton;
  JButton noImgButton;
  JButton mass1;
  JButton mass2;
  JButton bounceButton;
  JTextField massField;
  JTextField rootField;
  JTextField path2Field; 
  JTextField path1Field; 
  JTextField rField;
  JTextField gField;
  JTextField bField;
  JTextField bounceField;
  JTextField heightField;
  JTextField widthField;
  JTextField maxField;
  JButton pathing1Button;
  JButton pathing2Button;
 // JButton pathing4Button;
  JButton absorbButton;
  JButton lightShowButton;
  JButton pauseButton;
  JButton goofyButton;

  // create all of the different array lists here to hold enemys, enemy bullets, player bullets, etc. and the player
  MovableObject player;
  MovableObject dead;
  MovableObject healthbar;
  MovableObject ammobar;
  MovableObject energyBar;
  ArrayList <MovableObject> enemys;
  ArrayList <MovableObject> enemyBullets;
  ArrayList <MovableObject> playerBombs;
  ArrayList <MovableObject> explosions;
  ArrayList <MovableObject> playerBullets;
  ArrayList <MovableObject> paths;
  ArrayList <MovableObject> orbs;
  ArrayList <MovableObject> dminOrbs;
  ArrayList <ArrayList<Integer>> points;
  // thread for the runButton
  Thread runThread = null;
  int threadDelay = 16; //16 = 60fps
  public void initMovableObjects()
  {
	// create all the arrays
	orbs = new ArrayList<MovableObject>(20);
	keys= new boolean[10];
	// create the space player and enemys
	paths =new ArrayList<MovableObject>(20);
	dminOrbs = new ArrayList<MovableObject>(20);
	points = new ArrayList<ArrayList<Integer>>(20);
  }
  // ***** public void initialize *****  
  public void initialize( )
  {
	
	//centerPanel.setCursor( Cursor.getPredefinedCursor(CROSSHAIR_CURSOR));
  	// create the buttons
    runButton  = new JButton("Run");
    stopButton  = new JButton("Stop");
    exitButton  = new JButton("Exit");
    clearButton = new JButton("Clear");
    noImgButton  = new JButton("Stealth");
    pathing1Button  = new JButton("Path 1");
    pathing2Button  = new JButton("Path 2");
    //pathing4Button = new JButton("Path 1+2");
    absorbButton  = new JButton("Absorb");
    pauseButton = new JButton("Pause");
    lightShowButton = new JButton("Lights!");
    bounceButton = new JButton("Ricochet");
    goofyButton = new JButton("+ +");
   // startText="Hi welcome to Orbit Please click anywhere to create an orb, dragging the mouse creates an orb with a starting velocity \nHotkeys\nQ,W,E,R,1,2,A,S,D,F,N,M";
    mass1 =  new JButton("100");
    mass2 =  new JButton("1000");
    massField = new JTextField("10000", 7);
    rootField = new JTextField("1", 2);
    path2Field = new JTextField("20", 3);
    path1Field = new JTextField("40", 3);
    maxField = new JTextField("0",3);
    rField = new JTextField("255",2);
    gField = new JTextField("255",2);
    bField = new JTextField("255",2);
    bounceField = new JTextField("1.0",2);
    massField.setText("10000");
    rootField.setText("1");
    massField.setSize(50,50);
    rField.setSize(50,50);
    gField.setSize(50,50);
    bField.setSize(50,50);
    // create a mainPanel for components
    mainPanel = new JPanel();

    // ***** create JPanels for a BorderLayout *****
    northPanel   = new JPanel();
    southPanel   = new SouthPanel();
	southPanel.setListeners();
	
	if (showWestPanel)
	{
		westPanel    = new JPanel();
		westPanel.setLayout(new GridLayout(20,20));
	}
	if (showEastPanel)
	{
		eastPanel    = new JPanel();
	}
    centerPanel  = new DrawPanel();
    mainPanel.setLayout(new BorderLayout());
	southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));	
	setCenterPanelColor(Color.black);
    northPanel.setBackground(new Color(51,51,51));
    southPanel.setBackground(new Color(51,51,51));
     southPanel.setBackground(new Color(1,1,1));
	if (showWestPanel)
	{
	    westPanel.setBackground(new Color(51,51,51));
	}
	if (showEastPanel)
	{
	    eastPanel.setBackground(new Color(115,205,255));
	}
	// add buttons to southPanel
//	southPanel.add(runButton);
//	southPanel.add(stopButton);
//	southPanel.add(exitButton);
//  southPanel.add(pauseButton);
	southPanel.add(clearButton);
//	southPanel.add(rootField);
	
	southPanel.add(massField);
	pathing1Button.setBackground(Color.gray);
	pathing2Button.setBackground(Color.gray);
//	pathing4Button.setBackground(Color.gray);
	absorbButton.setBackground(Color.gray);
	noImgButton.setBackground(Color.gray);
	lightShowButton.setBackground(Color.gray);
	bounceButton.setBackground(Color.gray);
	goofyButton.setBackground(Color.gray);
	clearButton.setBackground(Color.white);
	southPanel.add(pathing1Button);
	southPanel.add(path1Field);
	//southPanel.add(pathing2Button);
	southPanel.add(pathing2Button);
	
	southPanel.add(path2Field);
//	southPanel.add(pathing4Button);
	southPanel.add(goofyButton);
	southPanel.add(absorbButton);
	southPanel.add(noImgButton);
	southPanel.add(lightShowButton);
	southPanel.add(bounceButton);
	southPanel.add(bounceField);
	southPanel.add(fillerLabel1);
	southPanel.add(rField);
	southPanel.add(gField);
	southPanel.add(bField);
	//southPanel.add(maxField);
	northText = new JLabel("Not drawn to physical scale. Gravitational constant of 6.67. Special thanks to Mr. Rosier and Mrs. Misage.");
	northText.setForeground(Color.LIGHT_GRAY);
	//northPanel.add(northText);
	//centerPanel.setFocusable(true);
	//getContentPane().requestFocus();
	
    // ***** add the panels to the mainPanel 5 areas *****
    mainPanel.add(northPanel,BorderLayout.NORTH);
    mainPanel.add(southPanel,BorderLayout.SOUTH);
	if (showEastPanel)
	{
	    mainPanel.add(eastPanel,BorderLayout.EAST);
	}
	if (showWestPanel)
	{
	    mainPanel.add(westPanel,BorderLayout.WEST);
	}
    mainPanel.add(centerPanel,BorderLayout.CENTER);
	//this.addKeyListener(this);
	//getContentPane().setFocusable(true);
	//getContentPane().requestFocus();
	southPanel.setFocusable(true);
	southPanel.requestFocus();
	initMovableObjects();
    centerPanel.repaint();
    // make the mainPanel be the main content area and show it
    setContentPane(mainPanel);
    setVisible(true);  // always show the screen last
	// focus the southPanel so that we can receive key strokes
	southPanel.setFocusable(true);
	southPanel.requestFocus();

  }   // end of public void initialize

  // ***** default constructor *****
  public Orbit( )
  {
    setSize(screenWidth,screenHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Orbit v2.0 Beta");
    // initialize variables
	initialize( );
	this.startThread();
  }
  public Orbit(int w,int h)
  {
    setSize(w,h); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Orbit v2.0 Beta");
    // initialize variables
	initialize( );
	screenHeight = h;
  	screenWidth = w;
	this.startThread();
  }
  public void setMessage(String message)
  {
  	northText.setText(message);
  }
  public String getMessage()
  {
  	return northText.getText();
  }
  public void setThreadDelay(int threadDelay)
  {
  	this.threadDelay = threadDelay;
  }
  public void setNorthPanelColor(Color color)
  {
		northPanel.setBackground(color);
  }
  public void setSouthPanelColor(Color color)
  {
		southPanel.setBackground(color);
  }
  public void setCenterPanelColor(Color color)
  {
		centerPanel.setBackground(color);
  }
  public void setWestPanelColor(Color color)
  {
	if (showWestPanel)
	{
		westPanel.setBackground(color);
	}
  }
  public void setEastPanelColor(Color color)
  {
	if (showEastPanel)
	{
		eastPanel.setBackground(color);
	}
  }
  // ***** main method *****
  public static void main(String[] arguments)
  {
  	int w =1650;
  	int h=950;
  	try{		
	Scanner res = new Scanner(new File("resolution.dat"));
	w = res.nextInt();
	res.nextLine();
	h = res.nextInt();	
	res.close();
	}
	catch(Exception e)
	{	}
    Orbit myOrbit = new Orbit(w,h);
  }
public void startThread()
{
	    if (runThread != null)
    	{
    		return ;
    	}
    	if (runThread==null)
		{
			runThread = new Thread(southPanel);
		}
		// init the screen
		initialize( );
		// start the game
		runThread.start();
}
class SouthPanel extends JPanel implements KeyListener,ActionListener, Runnable
{
  // start of actionPerformed (ActionListener interface)
  // handle button clicks here
  public SouthPanel()
  {
    // allow buttons to listen for clicks
    super();
  }
  public void setListeners()
  {
	runButton.addActionListener(this);
	stopButton.addActionListener(this);
	exitButton.addActionListener(this);
	clearButton.addActionListener(this);
	clearButton.addKeyListener(this);
	pathing1Button.addActionListener(this);
	pathing1Button.addKeyListener(this);

	pathing2Button.addActionListener(this);
	pathing2Button.addKeyListener(this);
	path2Field.addActionListener(this);
	path1Field.addActionListener(this);
	rootField.addActionListener(this);
	noImgButton.addActionListener(this);
	noImgButton.addKeyListener(this);
	absorbButton.addActionListener(this);
	maxField.addActionListener(this);
	absorbButton.addKeyListener(this);
	pauseButton.addActionListener(this);
	pauseButton.addKeyListener(this);
	lightShowButton.addActionListener(this);
	lightShowButton.addKeyListener(this);
	bounceButton.addActionListener(this);
	bounceButton.addKeyListener(this);
	addKeyListener(this);
	goofyButton.addActionListener(this);
	//remove focus from buttons!
	clearButton.setFocusable(false);
	pathing1Button.setFocusable(false);
	pathing2Button.setFocusable(false);
	absorbButton.setFocusable(false);
	noImgButton.setFocusable(false);
	lightShowButton.setFocusable(false);
	bounceButton.setFocusable(false);
	goofyButton.setFocusable(false);
  }
  @SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e)
  {
  	//System.out.println("actionPerformed");
    Object source = e.getSource();
    if (source==exitButton)
    {
    	if (runThread!=null)
		{
			runThread.stop();
			// runThread.destroy();
			runThread = null;
		}
    	System.exit(0);
    }
    else if (source==runButton)
    {
    	if (runThread != null)
    	{
    		return ;
    	}
    	if (runThread==null)
		{
			runThread = new Thread(this);
		}
		// init the screen
		initialize( );
		// start the game
		runThread.start();
	}
	else if (source==noImgButton)
    {
    	if(!noImg)
    	{
    		noImg = true;
    		noImgButton.setBackground(Color.LIGHT_GRAY);
    	}
    	else if(noImg)
    	{
    		noImg = false;
    		
    		noImgButton.setBackground(Color.gray);
    	}

	}
	else if (source==bounceButton)
    {
    	if(!bouncing)
    	{
    		bouncing = true;
    		bounceButton.setBackground(Color.LIGHT_GRAY);
    	}
    	else if(bouncing)
    	{
    		bouncing = false;		
    		bounceButton.setBackground(Color.gray);
    	}
	}
	else if (source==goofyButton)
    {
    	if(!goofy)
    	{
    		goofy = true;
    	goofyButton.setBackground(Color.LIGHT_GRAY);
    	}
    	else if(goofy)
    	{
    		goofy = false;		
    		goofyButton.setBackground(Color.gray);
    	}
	}
	else if (source==pathing1Button)
    {
    	paths.clear();
    	points.clear();
    	for(int a = 0; a < orbs.size(); a++)
    	{
    		orbs.get(a).points.clear();
    	}
    	if(pathing)
    	{
    		pathing=false;
    		pathing1Button.setBackground(Color.gray);	
    			dminOrbs.clear();	
    	}
    	else if(pathing==false)
    	{
    		pathing = true;
    		pathing1Button.setBackground(Color.LIGHT_GRAY);
    	}
	}
	else if (source==pathing2Button)
    {
    	paths.clear();
    	points.clear();
    	dminOrbs.clear();
    	if(pathing2)
    	{
    		pathing2=false;
    		pathing2Button.setBackground(Color.gray);		
    	}
    	else if(pathing2==false)
    	{
    		pathing2 = true;
    		pathing2Button.setBackground(Color.LIGHT_GRAY);
    	}
	}
	else if (source==lightShowButton)
    {
    	if(lightShow)
    	{
    		lightShow =false;
   			lightShowButton.setBackground(Color.gray);
   			
    	}
    	else if(!lightShow)
    	{
    		lightShow = true;
    		lightShowButton.setBackground(Color.LIGHT_GRAY);
    	}
	}
	else if (source==absorbButton)
    {
    	
    	if(absorb)
    	{
    		absorb=false;
    		absorbButton.setBackground(Color.gray);		
    	}
    	else if(!absorb)
    	{
    		absorb=true;
    		absorbButton.setBackground(Color.LIGHT_GRAY);	
    	}
	}
    else if (source==stopButton)
    {
   	  
    	if (runThread!=null)
		{
			runThread.stop();
			runThread = null;
		}
	}
	 else if (source==clearButton)
    {
   	  	orbs.clear();
   	  	paths.clear();
   	  	points.clear();
   	  	dminOrbs.clear();
	}
  }  // end of actionPerformed

  // thread to delay for the runButton
  // do it all here so we have control of the buttons
  public void run()
  {
   	try
   	{	
   		while(true)
   		{   		   
   		   //this will redraw everything on the centerPanel	   
	       centerPanel.repaint();
 		   setFocusable(true);
		   requestFocus();	   
  		   Thread.currentThread().sleep(threadDelay);
  		   //totalTime+=threadDelay;	   
          }
      }catch(Exception e)
      {
      }
  }

  // start of keyTyped (KeyListener interface)
  public void keyTyped(KeyEvent e)
  {
  }  // end of keyTyped(KeyEvent e)

  // start of keyPressed (KeyListener interface)
  public void keyPressed(KeyEvent e)
  {
  	//System.out.println("keypressed");
  	
  	// char keyChar = e.getKeyChar();
    int key = e.getKeyCode();
    String keyString = e.getKeyText(key);
    keyString = keyString.toUpperCase();
	centerPanel.keyPressed(keyString); // call this to change the player
  }  // end of keyPressed(KeyEvent e)

  // start of keyReleased (KeyListener interface)
 
  public void keyReleased(KeyEvent e)
  {
  	if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W' )
		{
			keys[0] = false;
		}
		if (e.getKeyChar()  == 's' || e.getKeyChar() == 'S')
		{
			keys[1] = false;
		}
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
		{
			keys[4] = false;
		}
		if (e.getKeyChar()== 'd' || e.getKeyChar() == 'D')
		{
			keys[3] = false;	
		}
		if(e.getKeyChar() == ' ')
		{
			keys[4]=false;
		}
		
    repaint();
  }  // end of keyReleased(KeyEvent e) // end of keyReleased(KeyEvent e)

	
	
} // end of centerPanel class



// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
// this is the panel for the game  (this is an inner class)
// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
@SuppressWarnings("serial")
class DrawPanel extends JPanel implements  MouseListener, MouseMotionListener
{
	String testXY="X: Y: ";
	String numOrbs= "Particles: ";
	
	//String waveNum="Wave: ";
	boolean dragging = false;
	
	public DrawPanel()
	{
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		//setCursor(Cursor.HAND_CURSOR);	
	}

    public void update(Graphics window)
    {
	   paintComponent(window);
    }
   
	public void paintComponent(Graphics g)
	{
		updateOrbs();
		super.paintComponent((Graphics2D)g);
		Graphics2D g2 = (Graphics2D) g;
		
		//take a snap shop of the current screen and same it as an image
		//that is the exact same width and height as the current screen
		if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));

		//create a graphics reference to the back ground image
		//we will draw all changes on the background image
		Graphics gMemory = back.createGraphics();
		numOrbs= "Particles: "+ orbs.size();
		// clear the screen
		gMemory.setColor(Color.BLACK);
		gMemory.fillRect(0,0,getWidth(),getHeight());
		//Code for the Light Show
		if(lightShow)
		{
			if(rDir)
			{
				r = r + ((int)(Math.random()*10));
				if(r >=255)
				{
					r = 255;
					rDir =false;
				}
			}
			else if(!rDir)
			{
				r = r - ((int)(Math.random()*10));
				if(r <= 0)
				{
					r = 0;
					rDir =true;
				}
			}
			rField.setText(""+r);
			if(greDir)
			{
				gre = gre +((int)(Math.random()*10));
				if(gre >=255)
				{
					gre = 255;
					greDir =false;
				}
			}
			else if(!greDir)
			{
				gre = gre - ((int)(Math.random()*10));
				if(gre <= 0)
				{
					gre = 0;
					greDir =true;
				}
			}
			gField.setText(""+gre);
			
			if(bDir)
			{
				b = b + ((int)(Math.random()*10));
				if(b >=255)
				{
					b = 255;
					bDir =false;
				}
			}
			else if(!bDir)
			{
				b = b - ((int)(Math.random()*10));
				if(b <= 0)
				{
					b = 0;
					bDir =true;
				}
			}
			bField.setText(""+b);
		}	
			
			
		gMemory.setColor(new Color( r,gre,b ));
		//Convert the text fields' values
		//	gMemory.drawString(testXY,10,15);0
			gMemory.drawString(numOrbs,10,15);
			if(help)
			{
				gMemory.drawString(startText,10,35);
			}
		
				
			try{
				if( Integer.parseInt(rField.getText())< 256 && Integer.parseInt(rField.getText()) > -1)
					r = Integer.parseInt(rField.getText());
				}
			catch(RuntimeException e){}
			try{
				if(Integer.parseInt(gField.getText()) < 256&&Integer.parseInt(gField.getText()) > -1)
				 gre = Integer.parseInt(gField.getText());
				}
			catch(RuntimeException e){}
			try{	
				if(Integer.parseInt(bField.getText()) < 256&& Integer.parseInt(bField.getText())> -1) 
				 b = Integer.parseInt(bField.getText());
			}
				catch(RuntimeException e){}
				try{	
			if(Integer.parseInt(maxField.getText())>-1)
				max=Integer.parseInt(maxField.getText());
			}
			catch(RuntimeException e){}
			
			try{	mass = Integer.parseInt(massField.getText());	}
			catch(RuntimeException e){}
			
			try{root = Integer.parseInt(rootField.getText());	}
			catch(RuntimeException e){}
			
			try{	path2Trail = Integer.parseInt(path2Field.getText());	}
			catch(RuntimeException e){}
			
			try{path1Trail = Integer.parseInt(path1Field.getText());}
			catch(RuntimeException e){}
			
			try{	bounceValue = Double.parseDouble(bounceField.getText()); }
			catch(RuntimeException e){}
		if(follow)
		{
			if(orbs.size()<1)
			{
				follow=false;
			}
		}
		if(bouncing)//Bouncing
		{
			for(int i = 0 ; i < orbs.size(); i++)
			{
				MovableObject orb  = orbs.get(i);
				if(orb.getX()<0)
				{
					if(orb.getVectorX()<0)
						orb.setVectorX(orb.getVectorX()*-bounceValue);
				}
						
				else if(orb.getX()>screenWidth)
					if(orb.getVectorX()>0)
						orb.setVectorX(orb.getVectorX()*-bounceValue);
						
				if(orb.getY()<0)
				{
					if(orb.getVectorY()<0)
						orb.setVectorY(orb.getVectorY()*-bounceValue);			
				}	
				else if(orb.getY()>screenHeight-75)
					if(orb.getVectorY()>0)
						orb.setVectorY(orb.getVectorY()*-bounceValue);	
			}
		}
		if(noImg)	//the Stealth or noImg Button
    	{
    		for(int a = 0; a< orbs.size() ; a++)
    		{					
    			orbs.get(a).setCurrentFilename(" ");
    		}	
    	}
    	else if(!noImg)
    	{	
    		for(int a = 0; a< orbs.size() ; a++)
    		{
    			setOrbImage(orbs.get(a));
    		}
    	}
		if(pathing2)//this is the new Path2. Negative values for the text box will make the lines appear indefinitely.
		{
			if(path2Trail < 0)
			{
				for(int a = 0; a<orbs.size(); a++)
				{
					MovableObject orb = orbs.get(a);
					MovableObject path= new MovableObject(centerPanel, "", orb.getX()+orb.getWidth()/2, orb.getY()+orb.getHeight()/2, 2,2);
					path.setRed(orb.getRed());
					path.setBlue(orb.getBlue());
					path.setGreen(orb.getGreen());
					paths.add(path);
				}
				for(int a = 0; a<paths.size()-1; a++)
				{
					gMemory.setColor(new Color(paths.get(a).getRed(),paths.get(a).getGreen(),paths.get(a).getBlue()));
					gMemory.drawLine(  myRound(paths.get(a).getX()), myRound(paths.get(a).getY()),  myRound(paths.get(a+1).getX()), myRound(paths.get(a+1).getY())  );	
				}	
			}
			else
			{
				for(int a = 0; a<orbs.size(); a++)
				{
					MovableObject orb = orbs.get(a);
					MovableObject path= new MovableObject(centerPanel, "", orbs.get(a).getX()+orbs.get(a).getWidth()/2, orbs.get(a).getY()+orbs.get(a).getHeight()/2, 2,2);
					path.setRed(orb.getRed());
					path.setBlue(orb.getBlue());
					path.setGreen(orb.getGreen());
					paths.add(path);
				}
				for(int a = 0; a<paths.size()-1; a++)
				{
					gMemory.setColor(new Color(paths.get(a).getRed(),paths.get(a).getGreen(),paths.get(a).getBlue()));
					gMemory.drawLine(  myRound(paths.get(a).getX()), myRound(paths.get(a).getY()),  myRound(paths.get(a+1).getX()), myRound(paths.get(a+1).getY())  );	
				}
				while( paths.size() > path2Trail * orbs.size() )
				{
					paths.remove(0);
				}
			}
		}
		if(pathing) //standard tail path, values less than 1 yield indefinite appearance
		{
			if(path1Trail > 0)
			{
				for(int a = 0; a<orbs.size(); a++)
				{
					int test = path1Trail;
					if(path1Trail % 2 !=0)
						test++;
					while( orbs.get(a).points.size() > test && test % 2 ==0 )
					{
						orbs.get(a).points.remove(0);
					}
				}
				for(int i =0; i<points.size();i++)
				{	
					if(points.get(i).size() <1)
					{	
						points.remove(i);
						i--;
					}
				}
				for(int i =0; i<points.size();i++)
				{
					points.get(i).remove(0);
					points.get(i).remove(0);
				}
				if(absorb)
				{
					for(int a = 0; a<dminOrbs.size(); a++)
					{	
						if(dminOrbs.get(a).points.size() >0)
						{	
							dminOrbs.get(a).points.remove(0);
							dminOrbs.get(a).points.remove(0);
						}	
						else
							dminOrbs.remove(a);
					}
				}
			}
			for(int a = 0; a<orbs.size(); a++)
			{
				orbs.get(a).points.add( myRound(orbs.get(a).getX()+orbs.get(a).getWidth()/2));
				orbs.get(a).points.add( myRound(orbs.get(a).getY()+orbs.get(a).getHeight()/2));
			}
			for(int a = 0; a<orbs.size(); a++)
			{
				for(int b = 0; b<orbs.get(a).points.size()-3; b=b+2)
				{
					if(!lightShow)
					{			
						gMemory.setColor( new Color (orbs.get(a).getRed() , orbs.get(a).getGreen() ,orbs.get(a).getBlue()));
					}
					
						gMemory.drawLine(orbs.get(a).points.get(b),orbs.get(a).points.get(b+1),orbs.get(a).points.get(b+2),orbs.get(a).points.get(b+3)  );
				}
			}
		/*	for(int a = 0; a<points.size(); a++)     
					
					gMemory.drawLine(points.get(a).get(b),points.get(a).get(b+1),points.get(a).get(b+2),points.get(a).get(b+3));
				}
			}	*/
			for(int a = 0; a<dminOrbs.size(); a++)
			{
				for(int b = 0; b<dminOrbs.get(a).points.size()-3; b=b+2)
				{
					if(!lightShow)
					{			
						gMemory.setColor( new Color (dminOrbs.get(a).getRed() , dminOrbs.get(a).getGreen() ,dminOrbs.get(a).getBlue()));
					}
					gMemory.drawLine(dminOrbs.get(a).points.get(b),dminOrbs.get(a).points.get(b+1),dminOrbs.get(a).points.get(b+2),dminOrbs.get(a).points.get(b+3)  );
				}
			}
		}	
		else if(pathing2)//Obsolete path, replaced by path3
		{
			for(int a = 0; a<orbs.size(); a++)
			{
				MovableObject path= new MovableObject(centerPanel, "", orbs.get(a).getX()+orbs.get(a).getWidth()/2, orbs.get(a).getY()+orbs.get(a).getHeight()/2, 2,2);
				paths.add(path);
			}
			for(int a = 0; a<paths.size()-1; a++)
			{
				gMemory.drawLine(  myRound(paths.get(a).getX()), myRound(paths.get(a).getY()),  myRound(paths.get(a+1).getX()), myRound(paths.get(a+1).getY())  );	
			}	
		}
		
		if(showLine)
		{
			gMemory.setColor(new Color(r,gre,b));
			gMemory.drawLine(originX,originY,finalX,finalY);
			//	ball.draw(gMemory);
		//	gMemory.drawOval(originX-3, originY-3, 6, 6);
			gMemory.setColor(Color.WHITE);
			gMemory.fillOval(originX-3, originY-3, 6, 6);
		}
		//Draw the Paths and the orbs.
		for(int a = 0; a<paths.size(); a++)
		{
			paths.get(a).draw(gMemory);
		}
		if(orbs.size()<1)
   		   	follow = false;
   		   	
		if(follow)
		{
			xDis = screenWidth/2.0  - orbs.get(followIndex).getX();
			yDis = screenHeight/2.0 - orbs.get(followIndex).getY();	
			for (int i=0; i<orbs.size(); i++)
	   		{			
   		   		MovableObject orb=orbs.get(i);			
  				orb.setXY(orb.getX()+xDis, orb.getY() +yDis);
   		 //  		orb.draw(gMemory);
  				gMemory.setColor(new Color(orb.getRed(),orb.getGreen(),orb.getBlue()));
  				gMemory.drawOval((int)orb.getX(), (int)orb.getY(), orb.getWidth(), orb.getHeight());
	   		}
		}
   		else
   		for (int i=0; i<orbs.size(); i++)
   		{	
   		   		//orbs.get(i).draw(gMemory);
   			MovableObject orb=orbs.get(i);	
   			if(lightShow)
   				gMemory.setColor(new Color(r,gre,b));

   			else
   				gMemory.setColor(new Color(orb.getRed(),orb.getGreen(),orb.getBlue()));
   			
   			if(!noImg)
			gMemory.drawOval((int)orb.getX(), (int)orb.getY(), orb.getWidth(), orb.getHeight());
   		}	
		// *** show the screen by copying the image to the graphics display ********
   		g2.drawImage(back, null, 0, 0);	
  }  // end of public void paintComponent(Graphics g)
  public void createOrbAbsorbed(MovableObject orb1, MovableObject orb2)
  {
 	MovableObject orb = new MovableObject(centerPanel, "",originX, originY, 10,10);
 	orb.setMass(orb1.getMass() + orb2.getMass());
 	
 	setOrbImage(orb);
  	setOrbColor(orb);
  	orb.setWidth((myRound(Math.log(orb.getMass())) + 2));
 	orb.setHeight( (myRound(Math.log(orb.getMass()))+ 2));
 	if(orb1.getMass() >= orb2.getMass())
 		orb.setXY(orb1.getX() -( orb.getWidth()- (orb1.getWidth()))/2.0,orb1.getY() - (orb.getHeight() - orb1.getHeight())/2.0);	
 	else
 		orb.setXY(orb2.getX() - (orb.getWidth() - orb2.getWidth())/2.0,orb2.getY() - (orb.getHeight() - orb2.getHeight())/2.0);	
 	orb.orientCenter(orb.getX(),orb.getY());
  	//orb.setXY(orb.getCenterX(), orb.getCenterY());
 	//need to calculate Conservation of Power, since they collided.  mv + mv = mv, should have one for x and y
 	orb.setVectorX( ( orb1.getMass()*orb1.getVectorX() +  orb2.getMass()*orb2.getVectorX() )/ (orb1.getMass() + orb2.getMass()));
 	orb.setVectorY( ( orb1.getMass()*orb1.getVectorY() +  orb2.getMass()*orb2.getVectorY() )/ (orb1.getMass() + orb2.getMass()));
 	orbs.add(0,orb);
 	if(follow)
 	{
 		if(followIndex == orbs.indexOf(orb1) || followIndex == orbs.indexOf(orb2))
 			followIndex =0;
 	}
  }
  public void setOrbColor(MovableObject orb)
  {
  	int r=255;
  	int g=255;
  	int b=255;
	if(orb.getMass() <55)
	{	
		r=100;
  		g=0;
  		b=220;
	}
	else if(orb.getMass() <1000 && orb.getMass() >=55 )
	{
		r=100;
  		g=0;
  		b=220;
	}
	else if(orb.getMass() <10000 && orb.getMass() >=1000  )
  	{
  		r=0;
  		g=175;
  		b=255;
 	}
 	else if(orb.getMass() <100000 && orb.getMass() >=10000   )
  	{
  		r=0;
  		g=155;
  		b=20;
 	}
 	else if(orb.getMass() <1000000 && orb.getMass() >=100000   )
  	{
  		r=255;
  		g=255;
  		b=0;
 	}
 	else if(orb.getMass() <10000000 &&orb.getMass() >= 1000000)
  	{
  		r=255;
  		g=155;
  		b=0;
  	}
  	else if(orb.getMass() >= 10000000)
  	{
  		r=255;
  		g=0;
  		b=0;
  	}
	orb.setRed(r);
	orb.setGreen(g);
	orb.setBlue(b);
  }
  public void setOrbImage(MovableObject orb)
  {
  	if(noImg)
  		orb.setCurrentFilename(" ");
  	
  	else
 	if(orb.getMass() <55)
 	{	
 		orb.setCurrentFilename("size1half.png");
 	}
 	else if(orb.getMass() <1000 && orb.getMass() >=55 )
  	{
  		orb.setCurrentFilename("size1v2.png");
 	}
 	else if(orb.getMass() <10000 && orb.getMass() >=1000  )
	{
		orb.setCurrentFilename("size2v2v2.png");
	}
	else if(orb.getMass() <100000 && orb.getMass() >=10000   )
	{
	 	orb.setCurrentFilename("size3v2.png");
	}
	else if(orb.getMass() <1000000 && orb.getMass() >=100000   )
  	{
  		orb.setCurrentFilename("size4v2.png");
 	}
 	else if(orb.getMass() <10000000 &&orb.getMass() >= 1000000 )
  	{
  		orb.setCurrentFilename("size5v2.png");
  	}
  	else if(orb.getMass()>=  10000000)
  	{
  		orb.setCurrentFilename("size6v2.png");
  	}
  }
  public int myRound(double t)
  {
  	int ret = (int)t;
  	if( ret-t < 0.5)
  		return ret;
  	else 
  		return ++ret;
  }
  public void createRandomSystem()
  {
  	for(int i = 0; i < numRandom;i++)
  	{
	  	MovableObject orb = new MovableObject(centerPanel, "size3v2.png",originX, originY, 10,10);
	  	orb.setMass(mass);
	  	setOrbImage(orb);
	 	orb.setWidth((myRound(Math.log(orb.getMass())) + 2));
	 	orb.setHeight( (myRound(Math.log(orb.getMass()))+ 2));
 		double x = Math.random()*screenWidth;
 		double y  = Math.random()*screenHeight;
  		orb.setXY(x,y);
  		setOrbColor(orb);
  		orbs.add(orb);
  	}
  }
  public void createOrb()
  {
  	MovableObject orb = new MovableObject(centerPanel,"size4v2.png",originX, originY,10,10);
  	orb.setMass(mass);
  	setOrbImage(orb);
 	orb.setWidth(  (myRound(Math.log(orb.getMass())) + 2));
 	orb.setHeight(  (myRound(Math.log(orb.getMass()))+ 2));
  	orb.orientCenter(originX,originY);
  	orb.setXY(orb.getCenterX(), orb.getCenterY());	
  	orb.setSpeedXY(0,0);
  	setOrbColor(orb);
  	double sideX = (finalX - originX);
	double sideY = (finalY- originY);
	double cLength = Math.sqrt(sideX*sideX  + sideY*sideY);
   	double sin     = Math.asin(sideY/cLength);
  	double dir     = sin*(180/Math.PI);	
  	if(sideX < 0)
  	{
  		orb.setVectorX(-1*Math.abs(((cLength/speedScale*Math.cos(dir*(Math.PI/180.0))  ))));				
  	}
  	else if(sideX > 0)
  	{
  		orb.setVectorX(Math.abs(((cLength/speedScale* Math.cos(dir*(Math.PI/180.0))  ))));	
  	}
  	if(sideY < 0)
  	{ 	
  		orb.setVectorY(-1*Math.abs(((cLength/speedScale*Math.sin(dir*(Math.PI/180.0)) ))));
  	}		
  	else if(sideY>0)
  	{
  		orb.setVectorY(Math.abs(((cLength/speedScale* Math.sin(dir*(Math.PI/180.0)) )))); 	
  	}		
  	preX=  myRound(orb.getX());
  	preY=  myRound(orb.getY());
  	preVx = orb.getVectorX();
  	preVy = orb.getVectorY();
  	preMass=mass;
 	if(finalX==originX &&  finalY ==originY)
 	{
 		orb.setVectorX(0);
 		orb.setVectorY(0);
 	}
  	orb.setSpeedXY(orb.getVectorX(),orb.getVectorY());
  	orbs.add(orb);
  }
  public void createPreOrb()
  {
  	MovableObject orb = new MovableObject(centerPanel,"size4v2.png",originX, originY,10,10);
  	orb.setMass(preMass);
  	setOrbImage(orb);
 	orb.setWidth(  (myRound(Math.log(orb.getMass())) + 2));
 	orb.setHeight(  (myRound(Math.log(orb.getMass()))+ 2));	
  	orb.setXY(preX, preY);	
  	orb.setVectorX(preVx);
  	orb.setVectorY(preVy);
  	setOrbColor(orb);
  	orbs.add(orb);	
  }
  public int findNearestParticle()
  {
  	int index=0;
  	if(orbs.size()==0)
  	{
  		index=-1;
  	}
  	else if(orbs.size()==1)
  	{	index=0;
  	}
  	else
	{
		double distance = 0.0;
		double lowDistance = 0.0;
		for(int i = 0; i<orbs.size(); i ++)
		{
			distance = Math.sqrt( (orbs.get(i).getX() - mouseX)*(orbs.get(i).getX() - mouseX) +  (orbs.get(i).getY() - mouseY)*(orbs.get(i).getY() - mouseY));
			if(i==0)
				lowDistance=distance;
			
			if(lowDistance > distance)
			{
				lowDistance = distance;
				index = i;
			}
		}
		
	}
	return index;
  }
  /*public void removeNearestParicle(int index)
  {
  	if(index==-1)
  	{
  		orbs.clear();
  	}
  	else
	{
		points.add(orbs.get(index).points);
		orbs.remove(index);
	}
  }*/
  public void removeNearestParicle()
  {
		double distance = 0.0;
		double lowDistance = 0.0;
		int index=0;
		
		for(int i = 0; i<orbs.size(); i ++)
		{
			distance = Math.sqrt( (orbs.get(i).getX() - mouseX)*(orbs.get(i).getX() - mouseX) +  (orbs.get(i).getY() - mouseY)*(orbs.get(i).getY() - mouseY));
			if(i==0)
				lowDistance=distance;
			
			if(lowDistance > distance)
			{
				lowDistance = distance;
				index = i;
			}
		}
		points.add(orbs.get(index).points);
		orbs.remove(index);
	
  }
public void explodeNearestParticle()
{
	if(orbs.size()>0)
	{
		double distance = 0.0;
		double lowDistance = 0.0;
		int index=0;

		for(int i = 0; i<orbs.size(); i ++)
		{
			distance = Math.sqrt( (orbs.get(i).getX() - mouseX)*(orbs.get(i).getX() - mouseX) +  (orbs.get(i).getY() - mouseY)*(orbs.get(i).getY() - mouseY));
			if(lowDistance > distance)
			{
				lowDistance = distance;
				index = i;
			}
		}
		MovableObject orb = new MovableObject(centerPanel, "size4v2.png",originX, originY, 10,10);
		double degree = 0.0;
		for(int i = 0; i < explosionCount; i++)
		{
			degree = ((360.0 / explosionCount)/ 180.0 ) * Math.PI;
			if((orbs.get(index).getMass()/ explosionCount)  > 1)
				orb.setMass((orbs.get(index).getMass()/ explosionCount));
			
			else
				orb.setMass(1);
			
			orb.updateParticleImage();
			orb.setX(orbs.get(index).getX() + 10.0*Math.sin(degree));
		}
		orbs.remove(index);		
	}
}
  public void updateOrbs()
  {
  	if(max>0)
  	while(orbs.size() > max)
  		orbs.remove(0);
  	if(absorb)
	{
		for(int i  = 0 ; i <orbs.size()-1;i++)
		{
			for(int j = i+1; j < orbs.size(); )
			{
				if( (0.0+orbs.get(i).getWidth()+orbs.get(j).getWidth())*1.054> Math.sqrt(  (0.0+orbs.get(i).getY()-orbs.get(j).getY())*(0.0+orbs.get(i).getY()-orbs.get(j).getY())+ (0.0+orbs.get(i).getX()-orbs.get(j).getX())*(0.0+orbs.get(i).getX()-orbs.get(j).getX())))
				{		
					createOrbAbsorbed(orbs.get(i),orbs.get(j));
					
					MovableObject dminOrb1= new MovableObject(centerPanel," ",orbs.get(i).getX(), orbs.get(i).getY(),10,10);
					dminOrb1.setMass(orbs.get(i).getMass());
					setOrbColor(dminOrb1);
					dminOrb1.points=orbs.get(i).points;
					dminOrbs.add(dminOrb1);
					
					dminOrb1= new MovableObject(centerPanel," ",orbs.get(j).getX(), orbs.get(j).getY(),10,10);
					dminOrb1.setMass(orbs.get(j).getMass());
					setOrbColor(dminOrb1);
					dminOrb1.points=orbs.get(j).points;
					dminOrbs.add(dminOrb1);
					
					orbs.remove(j+1);
    				orbs.remove(i+1);
    					i++;
					j=i+1;
				//followIndex =0;
				}
				else
				{
					j++;
				}	
			}			
		}
	}	
  	double sideX = 0;
	double sideY = 0;
	double cLength = 0;
    double angle = 0;
    double Fg = 0;
		int goofyFactor = 1;
		if(goofy)
			goofyFactor=-1;
		
  	if(orbs.size()>1)
    {
    	if(root ==1)
   		{
    		for(int a = 0; a<orbs.size() ; a++)
    		{
    			MovableObject orb = orbs.get(a);
    			for(int b = 0; b<orbs.size(); b++)
    			{
    				MovableObject orb2 = orbs.get(b);
    				if((!(orb.equals(orb2))))
    				{	
    					sideX = (orb.getX() - orb2.getX());
						sideY = (orb.getY() - orb2.getY());
						cLength = Math.sqrt(sideX*sideX + sideY*sideY);
						Fg = ((0.0667)*orb2.getMass())/(cLength*cLength);	
						angle = Math.asin(sideY/cLength);					
  					if(sideX > 0)
  					{
  						orb.adjustVectorX(( goofyFactor* (-1)*(((Math.abs(Math.cos(angle) * Fg ))))     ));	
  					}
  					else if(sideX < 0)
  					{
  						orb.adjustVectorX( goofyFactor*  (((Math.abs(Math.cos(angle) * Fg )))));
  					}	
  					if(sideY > 0)
  					{
  						orb.adjustVectorY((  goofyFactor*  ((-1)*((Math.abs(Math.sin(angle) * Fg ))))));		
  					}
  					else if(sideY < 0)
  					{
  						orb.adjustVectorY((  goofyFactor* ((Math.abs(Math.sin(angle) * Fg )))));	
  					}	
    			}		
    		}
    	}
    	}
    	else if(root >1)
   		{
   			for(int a = 0; a<orbs.size() ; a++)
    		{
    		MovableObject orb = orbs.get(a);
    		for(int b = 0; b<orbs.size(); b++)
    		{
    			MovableObject orb2 = orbs.get(b);
    			if((!(orb.equals(orb2))))
    			{				
    				sideX = (orb.getX() - orb2.getX());
					sideY = (orb.getY() - orb2.getY());
					cLength = Math.sqrt(sideX*sideX + sideY*sideY);
					Fg = ((1.0)*orb2.getMass())/(cLength*cLength);
					angle = Math.asin(sideY/cLength);
					
  					if(sideX > 0)
  					{
  						orb.adjustVectorX((  goofyFactor*  (-1)*(    Math.pow(   (Math.abs(Math.cos(angle) * Fg ))   , (1.0/root)   )  )     )/3);		
  					}
  					else if(sideX < 0)
  					{
  						orb.adjustVectorX((  goofyFactor*  (    Math.pow(   (Math.abs(Math.cos(angle) * Fg ))   , (1.0/root)   )  )     )/3);			
  					}
  					if(sideY > 0)
  					{
  						orb.adjustVectorY(( goofyFactor*   (-1)*(    Math.pow(   (Math.abs(Math.sin(angle) * Fg ))   , (1.0/root)   )  )     )/3);
  					}
  					else if(sideY < 0)
  					{
  						orb.adjustVectorY((  goofyFactor*  (    Math.pow(   (Math.abs(Math.sin(angle) * Fg ))   , (1.0/root)   )  )     )/3);
  					}
    			}		
    		}  		
    	}
   		}
   	}
  	for(int i = 0; i<orbs.size(); i++)
  	{
  		MovableObject orb = orbs.get(i);
 	 	orb.setXY((orb.getX()+(orb.getVectorX())),(orb.getY()+(orb.getVectorY())));
  	}
  }
  public void updateDirection(int x, int y)
  {
    double bLength = Math.abs(player.getY()-y);
    double cLength = Math.sqrt(((x-player.getX())*(x-player.getX()))+((y-player.getY())*(y-player.getY())));
    double sin     = Math.asin(bLength/cLength);
    double dir     = sin*(180/Math.PI);
    testXY = "X: " + x + "  Y: " + y;
   
    if(x>player.getX()&&y>player.getY())
    {
    	player.setDirection((int)dir+90);
    }
   	if(x>player.getX()&&y<player.getY())
   	{
   		player.setDirection(90-(int)dir);	
   	}
   	if(x<player.getX()&&y<player.getY())
   	{
   		player.setDirection((int)dir+270);  	
   	}
   	if(x<player.getX()&&y>player.getY())
   	{
   		player.setDirection(270-(int)dir);
   	}
  }
  public void keyPressed(String keyString)
  {
    if (keyString.equals("UP"))
    {
    	
    }
    else if (keyString.equals("DOWN"))
    {
    
    }
    else if (keyString.equals("LEFT"))
    {
    
    }
    else if (keyString.equals("RIGHT"))
    {
    
    }
    else if(keyString.equals("SPACE"))
    {
    	keys[4]=true;
    	showLine=false;
    }
    
    else if (keyString.equals("S"))
    {
      createRandomSystem();
    }
    else if (keyString.equals("A"))
    {
    		
    keys[4]=true;
    
    }
    else if (keyString.equals("D"))
    {
     //removeNearestParicle(findNearestParticle());
     removeNearestParicle();
    }
    else if (keyString.equals("G"))
    {
      createPreOrb();
    }
    else if (keyString.equals("Q"))
    { 		
     	if(absorb)
    	{
    		absorb=false;
    		absorbButton.setBackground(Color.gray);		
    	}
    	else if(!absorb)
    	{
    		absorb=true;
    		absorbButton.setBackground(Color.LIGHT_GRAY);	
    	}
    }
    else if (keyString.equals("W"))
    { 		
     	if(!noImg)
    	{
    		noImg = true;
    		noImgButton.setBackground(Color.LIGHT_GRAY);
    	}
    	else if(noImg)
    	{
    		noImg = false;	
    		noImgButton.setBackground(Color.gray);
    	}	
    }
    else if (keyString.equals("E"))
    { 		
     	if(lightShow)
    	{
    		lightShow =false;
   			lightShowButton.setBackground(Color.gray);		
    	}
    	else if(!lightShow)
    	{
    		lightShow = true;
    		lightShowButton.setBackground(Color.LIGHT_GRAY);
    	}
    }
    else if (keyString.equals("R"))
    { 		
     	if(!bouncing)
    	{
    		bouncing = true;
    		bounceButton.setBackground(Color.LIGHT_GRAY);
    	}
    	else if(bouncing)
    	{
    		bouncing = false;		
    		bounceButton.setBackground(Color.gray);
    	}
    }
    else if (keyString.equals("F"))
    {
	 if(follow)
	 	follow=false;
	 else if(!follow && orbs.size() >0)
	 {
	 	follow=true;
	 	followIndex=findNearestParticle();
	 	xDis = screenWidth/2.0  - orbs.get(followIndex).getX();
		yDis = screenHeight/2.0 - orbs.get(followIndex).getY();	
	 }
    }
    else if (keyString.equals("C"))
    {
		orbs.clear();
		paths.clear();
		points.clear();
		dminOrbs.clear();
    }
	else if (keyString.equals("N"))
	{

	}
	else if (keyString.equals("H"))
	{
		if(help)
		{
		help=false;
		}
		else if(!help)
			help=true;
	}
	else if (keyString.equals("M"))
	{

	}
	else if (keyString.equals("1"))
    {
			paths.clear();
			points.clear();
    	for(int a = 0; a < orbs.size(); a++)
    	{
    		orbs.get(a).points.clear();
    	}
    	if(pathing)
    	{
    		pathing=false;
    		pathing1Button.setBackground(Color.gray);		
    	}
    	else if(pathing==false)
    	{
    		pathing = true;
    		pathing1Button.setBackground(Color.LIGHT_GRAY);
    	}
    }
    else if (keyString.equals("2"))
    {
			paths.clear();
			points.clear();
    	if(pathing2)
    	{
    		pathing2=false;
    		pathing2Button.setBackground(Color.gray);		
    	}
    	else if(pathing2==false)
    	{
    		pathing2 = true;
    		pathing2Button.setBackground(Color.LIGHT_GRAY);
    	}
    }
    repaint();
    

    // check to see if you need to fire
  } // end of method public void keyPressed(String keyString)


  // ***** MouseListener interface methods *****


  // start of mouseClicked(MouseEvent e) (MouseListener interface)
  public void mouseClicked(MouseEvent e) 
  {
  	mouseMoved(e);
    int xPos = e.getX();
    int yPos = e.getY();
    originX=xPos;
  	originY=yPos;
   
  }  // end of public void mouseClicked(MouseEvent e) 


  // start of mousePressed(MouseEvent e) (MouseListener interface)
  public void mousePressed(MouseEvent e) 
  {
  	int xPos = e.getX();
    int yPos = e.getY();
  	mouseMoved(e);
  	originX=xPos;
  	originY=yPos;
  	southPanel.requestFocus();
  	if(!keys[4])
  		showLine=true;
  	
  
  }  // end of public void mousePressed(MouseEvent e)


  public void mouseReleased(MouseEvent e) 
  {
  	mouseMoved(e);
   	
    dragging = false;
    int xPos = e.getX();
    int yPos = e.getY();
    
    //Create the Balls at this point
    
   if(!keys[4])
    createOrb();
    
    originX=xPos;
  	originY=yPos;
  	finalX = xPos;
    finalY = yPos;
    showLine=false;
   	
    
  }  // end of public void mouseReleased(MouseEvent e)


  public void mouseEntered(MouseEvent e) 
  {

  }  // end of public void mouseEntered(MouseEvent e)


  public void mouseExited(MouseEvent e) 
  {

  }  // end of public void mouseExited(MouseEvent e)


  // ***** MouseMotionListener interface methods *****


  public void mouseMoved(MouseEvent e) 
  {
    int xPos = e.getX();
    int yPos = e.getY();
    mouseX = e.getX();
    mouseY = e.getY();
    if(!showLine && !keys[4])
			{
	originX=xPos;
  	originY=yPos;
  	finalX=xPos;
  	finalY =yPos;
			}
    testXY = "X: " + xPos + "  Y: " + yPos;
    
  }  // end of public void mouseMoved(MouseEvent e) 


  public void mouseDragged(MouseEvent e) 
  {
  	mouseMoved(e);
    int xPos = e.getX();
    int yPos = e.getY();
    finalX = xPos;
    finalY = yPos;
    if(keys[4])
    {
    	for(int i=0;i<orbs.size();i++)
    	{
    		orbs.get(i).setX( orbs.get(i).getX() - (originX-finalX));
    		orbs.get(i).setY( orbs.get(i).getY() - (originY-finalY));	
    	}
    	if(pathing)
    	{	
	    	for(int i=0;i<orbs.size();i++)
	    	{
	    		for(int b = 0; b<orbs.get(i).points.size()-1;b=b+2)
	    		{   		
		    		orbs.get(i).points.set(b, orbs.get(i).points.get(b)-(originX-finalX) );
		    		orbs.get(i).points.set(b+1, orbs.get(i).points.get(b+1)-(originY-finalY) );	
	    		}
	    	}
    	}
    	for(int i = 0; i <dminOrbs.size();i++)
    	{
    		for(int b = 0; b<dminOrbs.get(i).points.size()-1;b=b+2)
    		{   		
	    		dminOrbs.get(i).points.set(b, dminOrbs.get(i).points.get(b)-(originX-finalX) );
	    		dminOrbs.get(i).points.set(b+1, dminOrbs.get(i).points.get(b+1)-(originY-finalY) );	
    		}
    	}	
    	if(pathing2)
    	{
    		for(int k=0;k<paths.size();k++)
    		{
    			paths.get(k).setX( paths.get(k).getX() - (originX-finalX));
    			paths.get(k).setY( paths.get(k).getY() - (originY-finalY));
    		}
    	}	
    		originX = e.getX();
    		originY = e.getY();
    }
      // end of if (dragging)
  }  // end of public void mouseDragged(MouseEvent e)

}
}