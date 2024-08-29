import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math;
import java.io.*;

class Vector2 {
    double x;
    double y;
    
    public String toString() { return "(" + this.x + ", " + this.y + ")"; }
    
    Vector2() {} // NOTE: x and y automatically initialized to zero
    Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }
    Vector2(double s) {
        this.x = s;
        this.y = s;
    }
    
    Vector2 plus(Vector2 other) { return new Vector2(this.x + other.x, this.y + other.y); }
    Vector2 minus(Vector2 other) { return new Vector2(this.x - other.x, this.y - other.y); }
    Vector2 times(double scalar) { return new Vector2(scalar * this.x, scalar * this.y); }
    Vector2 dividedBy(double scalar) { return this.times(1.0 / scalar); }
    double squaredLength() { return this.x * this.x + this.y * this.y; }
    double length() { return Math.sqrt(this.squaredLength()); }
    Vector2 directionVector() { return this.dividedBy(this.length()); }
    
    static double distanceBetween(Vector2 a, Vector2 b) { return (b.minus(a)).length(); }
    static Vector2 directionVectorFrom(Vector2 a, Vector2 b) { return (b.minus(a)).directionVector(); }
    static Vector2 lerp(double t, Vector2 a, Vector2 b) { return a.times(1.0 - t).plus(b.times(t)); }
    
    static final Vector2 right = new Vector2( 1.0,  0.0);
    static final Vector2 left  = new Vector2(-1.0,  0.0);
    static final Vector2 up    = new Vector2( 0.0,  1.0);
    static final Vector2 down  = new Vector2( 0.0, -1.0);
}

class Vector3 {
    double x;
    double y;
    double z;
    
    public String toString() { return "(" + this.x + ", " + this.y + ", " + this.z + ")"; }
    
    Vector3() { }
    
    Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector3(Vector3 p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
    Vector3(double s) {
        this.x = s;
        this.y = s;
        this.z = s;
    }
    
    Vector3 plus(Vector3 other) { return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z); }
    Vector3 minus(Vector3 other) { return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z); }
    Vector3 times(double scalar) { return new Vector3(scalar * this.x, scalar * this.y, scalar * this.z); }
    Vector3 dividedBy(double scalar) { return this.times(1.0 / scalar); }
    double squaredLength() { return this.x * this.x + this.y * this.y + this.z * this.z; }
    double length() { return Math.sqrt(this.squaredLength()); }
    Vector3 directionVector() { return this.dividedBy(this.length()); }
    
    static double distanceBetween(Vector3 a, Vector3 b) { return (b.minus(a)).length(); }
    static Vector3 directionVectorFrom(Vector3 a, Vector3 b) { return (b.minus(a)).directionVector(); }
    static Vector3 lerp(double t, Vector3 a, Vector3 b) { return a.times(1.0 - t).plus(b.times(t)); }
    
    static final Vector3 white     = new Vector3(1.0 , 1.0 , 1.0 );
    static final Vector3 lightGray = new Vector3(0.75, 0.75, 0.75);
    static final Vector3 gray      = new Vector3(0.5 , 0.5 , 0.5 );
    static final Vector3 darkGray  = new Vector3(0.25, 0.25, 0.25);
    static final Vector3 black     = new Vector3(0.0 , 0.0 , 0.0 );
    static final Vector3 red       = new Vector3(1.0 , 0.0 , 0.0 );
    static final Vector3 orange    = new Vector3(1.0 , 0.5 , 0.0 );
    static final Vector3 yellow    = new Vector3(1.0 , 1.0 , 0.0 );
    static final Vector3 green     = new Vector3(0.0 , 1.0 , 0.0 );
    static final Vector3 cyan      = new Vector3(0.0 , 1.0 , 1.0 );
    static final Vector3 blue      = new Vector3(0.0 , 0.0 , 1.0 );
    static final Vector3 magenta   = new Vector3(1.0 , 0.0 , 1.0 );
    static Vector3 rainbowSwirl(double time) {
        return new Vector3(_rainbowSwirlHelper(time, 0.0), _rainbowSwirlHelper(time, 0.33), _rainbowSwirlHelper(time, -0.33));
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    static double _rainbowSwirlHelper(double time, double offset) {
        return 0.5 + 0.5 * Math.cos(6.28 * (offset - time));
    }
}

class ExampleApp extends App {
    Vector2 chaserPosition;
    double time;
    
    void setup() {
        chaserPosition = new Vector2();
        time = 0.0;
    }
    
    void loop() {
        if (!keyToggled('P')) { time += 0.0167; }
        if (mouseHeld) {
            chaserPosition = chaserPosition.plus(Vector2.directionVectorFrom(chaserPosition, mousePosition));
        }
        drawLine(chaserPosition, mousePosition, Vector3.white);
        drawCircle(chaserPosition, 2.0, Vector3.rainbowSwirl(time));
        drawCenterRectangle(mousePosition, new Vector2(4.0), Vector3.cyan);
    }
    
    public static void main(String[] arguments) {
        App app = new ExampleApp();
        app.setWindowBackgroundColor(Vector3.black);
        app.setWindowSizeInWorldUnits(64.0, 64.0);
        app.setWindowCenterInWorldUnits(0.0, 0.0);
        app.setWindowHeightInPixels(512);
        app.setWindowTopLeftCornerInPixels(64, 64);
        app.run();
    }
}

class App extends JPanel {
    // app
    void setup() {}
    void loop() {}
    void run() {
        setWindowBackgroundColor(_windowBackgroundColor);
        setWindowSizeInWorldUnits(_windowWidthInWorldUnits, _windowHeightInWorldUnits);
        setWindowCenterInWorldUnits(_windowCenterXInWorldUnits, _windowCenterYInWorldUnits);
        setWindowHeightInPixels(_windowHeightInPixels);
        setWindowTopLeftCornerInPixels(_windowTopLeftCornerXInPixels, _windowTopLeftCornerYInPixels);
        _jFrame.setVisible(true);
        
        while (!(hotkeysEnabled && keyPressed('q'))) {
            this.repaint();
            try { Thread.sleep(1000 / 60); } catch (Exception e) { }
        }
        System.exit(0);
    }
    void reset() { _resetCalled = true; }
    
    // draw
    void drawString(String string, Vector2 _position, Vector3 color, int fontSize, boolean center) {
        // Suppress Mac warnings about missing Times and Lucida.
        PrintStream systemDotErr = System.err;
        System.setErr(new PrintStream(new NullOutputStream())); {
            _graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize)); 
            Vector2 position = _windowPixelFromWorld(_position);
            if (center) {
                FontMetrics fontMetrics = _graphics.getFontMetrics(); 
                position.x -= 0.5 * fontMetrics.stringWidth(string);
                position.y += 0.25 * fontMetrics.getHeight();
            }
            _graphicsSetColor(color);
            _graphics.drawString(string, (int) position.x, (int) position.y);
        } System.setErr(systemDotErr);
    }
    void drawLine(Vector2 _pointA, Vector2 _pointB, Vector3 color) {
        Vector2 pointA = _windowPixelFromWorld(_pointA);
        Vector2 pointB = _windowPixelFromWorld(_pointB);
        _graphicsSetColor(color);
        _graphics.drawLine((int) pointA.x, (int) pointA.y, (int) pointB.x, (int) pointB.y);
    }
    void drawCircle(Vector2 center, double radius, Vector3 color) { _drawCenterShape(center, new Vector2(2 * radius), color, 1); }
    void drawCenterRectangle(Vector2 center, Vector2 size, Vector3 color) { _drawCenterShape(center, size, color, 0); }
    void drawCornerRectangle(Vector2 _cornerA, Vector2 _cornerB, Vector3 color) { _drawCornerShape(_cornerA, _cornerB, color, 0); }
    
    // input
    Vector2 mousePosition;
    boolean mousePressed = false;
    boolean mouseHeld = false;
    boolean mouseReleased = false;
    boolean keyHeld(int key) { return _keyHeld.getOrDefault(_keyMakeCaseInvariant(key), false); }
    boolean keyPressed(int key) { return _keyPressed.getOrDefault(_keyMakeCaseInvariant(key), false); }
    boolean keyReleased(int key) { return _keyReleased.getOrDefault(_keyMakeCaseInvariant(key), false); }
    boolean keyToggled(int key) { return _keyToggled.getOrDefault(_keyMakeCaseInvariant(key), false); }
    boolean keyAnyPressed;
    char keyLastPressed;
    
    // window
    void setWindowBackgroundColor(double r, double g, double b) {
        setWindowBackgroundColor(new Vector3(r, g, b));
    }
    void setWindowBackgroundColor(Vector3 color) {
        _windowBackgroundColor = color;
        this.setBackground(_graphicsColorFromVector3(_windowBackgroundColor));
    }
    void setWindowSizeInWorldUnits(double width, double height) {
        _windowWidthInWorldUnits = width;
        _windowHeightInWorldUnits = height;
        
        _windowWidthInPixels = (int) (_windowPixelsPerWorldUnit() * _windowWidthInWorldUnits);
        _windowHeightInPixels = (int) (_windowPixelsPerWorldUnit() * _windowHeightInWorldUnits);
        _jFrame.setSize(_windowWidthInPixels, _windowHeightInPixels);
    }
    void setWindowCenterInWorldUnits(double x, double y) {
        _windowCenterXInWorldUnits = x;
        _windowCenterYInWorldUnits = y;
    }
    void setWindowHeightInPixels(int height) {
        _windowHeightInPixels = height;
        
        _windowWidthInPixels = (int) (_windowPixelsPerWorldUnit() * _windowWidthInWorldUnits);
        _jFrame.setSize(_windowWidthInPixels, _windowHeightInPixels);
    }
    void setWindowTopLeftCornerInPixels(int x, int y) {
        _windowTopLeftCornerXInPixels = x;
        _windowTopLeftCornerYInPixels = y;
        _jFrame.setLocation(x, y);
    }
    
    boolean hotkeysEnabled = true;
    
    ////////////////////////////////////////////////////////////////////////////
    
    public class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {}
    }
    
    Vector3 _windowBackgroundColor = Vector3.white;
    double _windowWidthInWorldUnits  = 16.0;
    double _windowHeightInWorldUnits = 16.0;
    double _windowCenterXInWorldUnits = 0.0;
    double _windowCenterYInWorldUnits = 0.0;
    int _windowHeightInPixels = 512;
    int _windowWidthInPixels;
    int _windowTopLeftCornerXInPixels = 256;
    int _windowTopLeftCornerYInPixels = 64;
    
    double _windowPixelsPerWorldUnit() { return _windowHeightInPixels / _windowHeightInWorldUnits; }
    Vector2 _windowPixelFromWorld(Vector2 sWorld) {
        Vector2 sPixel = new Vector2();
        double scale = _windowPixelsPerWorldUnit();
        sPixel.x = (int) (scale *                             ((sWorld.x - (_windowCenterXInWorldUnits - .5 *  _windowWidthInWorldUnits))));
        sPixel.y = (int) (scale * (_windowHeightInWorldUnits - (sWorld.y - (_windowCenterYInWorldUnits - .5 * _windowHeightInWorldUnits))));
        return sPixel;
    }
    Vector2 _windowWorldFromPixel(Vector2 sPixel) {
        Vector2 sWorld = new Vector2();
        double scale = _windowPixelsPerWorldUnit();
        sWorld.x =                             (sPixel.x / scale) + (_windowCenterXInWorldUnits - .5 *  _windowWidthInWorldUnits);
        sWorld.y = _windowHeightInWorldUnits - (sPixel.y / scale) + (_windowCenterYInWorldUnits - .5 * _windowHeightInWorldUnits);
        return sWorld;
    }
    
    Color _graphicsColorFromVector3(Vector3 color) {
        return new Color((float) color.x, (float) color.y, (float) color.z);
    }
    void _graphicsSetColor(Vector3 color) {
        _graphics.setColor(_graphicsColorFromVector3(color));
    }
    void _drawCenterShape(Vector2 center, Vector2 size, Vector3 color, int shapeType) {
        Vector2 halfSize = size.dividedBy(2.0);
        _drawCornerShape(center.minus(halfSize), center.plus(halfSize), color, shapeType);
    }
    void _drawCornerShape(Vector2 _cornerA, Vector2 _cornerB, Vector3 color, int shapeType) {
        Vector2 cornerA = _windowPixelFromWorld(_cornerA);
        Vector2 cornerB = _windowPixelFromWorld(_cornerB);
        
        // swap if necessary to make A lower-left and B upper-right
        if (cornerA.x > cornerB.x) { double tmp = cornerA.x; cornerA.x = cornerB.x; cornerB.x = tmp; }
        if (cornerA.y > cornerB.y) { double tmp = cornerA.y; cornerA.y = cornerB.y; cornerB.y = tmp; }
        
        _graphicsSetColor(color);
        int arg0 = (int) (cornerA.x);
        int arg1 = (int) (cornerA.y);
        int arg2 = (int) (cornerB.x - cornerA.x);
        int arg3 = (int) (cornerB.y - cornerA.y);
        if (shapeType == 0) {
            _graphics.fillRect(arg0, arg1, arg2, arg3);
        } else {
            _graphics.fillOval(arg0, arg1, arg2, arg3);
        }
    }
    void _drawLineStrip(Collection<Vector2> points, Vector3 color) { _drawLineStrip(points.toArray(new Vector2[0]), color); }
    void _drawLineStrip(Vector2[] points, Vector3 color) {
        int nPoints = points.length;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];
        
        for (int i = 0; i < nPoints; ++i) {
            Vector2 tmp = _windowPixelFromWorld(points[i]);
            xPoints[i] = (int) tmp.x;
            yPoints[i] = (int) tmp.y;
        }
        
        _graphicsSetColor(color);
        _graphics.drawPolyline(xPoints, yPoints, nPoints);
    }
    
    
    JFrame _jFrame;
    Hashtable<Integer, Boolean> _keyPressed = new Hashtable<>();
    Hashtable<Integer, Boolean> _keyHeld = new Hashtable<>();
    Hashtable<Integer, Boolean> _keyReleased = new Hashtable<>();
    Hashtable<Integer, Boolean> _keyToggled = new Hashtable<>();
    int _keyMakeCaseInvariant(int key) {
        if ('a' <= key && key <= 'z') {
            return 'A' + (key - 'a');
        }
        return key;
    }
    
    
    App() {
        super();
        
        {
            this.addMouseListener( new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mousePressed = true;
                    mouseHeld = true;
                }
                
                @Override public void mouseReleased(MouseEvent e) {
                    mouseHeld = false;
                    mouseReleased = true;
                }
            });
            
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
                synchronized (App.class) {
                    int key = event.getKeyCode();
                    if (event.getID() == KeyEvent.KEY_PRESSED) {
                        if (!keyHeld(key)) {
                            _keyPressed.put(key, true);
                            if (key < 256) { // FORNOW
                                keyAnyPressed = true;
                                keyLastPressed = (char) (key);
                                if ('A' <= keyLastPressed && keyLastPressed <= 'Z') {
                                    keyLastPressed = (char) ('a' + (keyLastPressed - 'A'));
                                }
                            }
                        }
                        _keyHeld.put(key, true);
                        _keyToggled.put(key, !_keyToggled.getOrDefault(key, false));
                    }
                    if (event.getID() == KeyEvent.KEY_RELEASED) {
                        _keyReleased.put(key, true);
                        _keyHeld.put(key, false);
                    }
                    return false;
                }
            });
        }
        {
            _jFrame = new JFrame("CS136");
            _jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _jFrame.getContentPane().add(this, BorderLayout.CENTER);
        }
    }
    
    boolean _resetCalled = false;
    boolean _initialized = false;
    Graphics _graphics;
    
    @Override 
    public void paintComponent(Graphics _graphics) {
        // NOTE: try-catch to actually kill the app on an error
        try {
            super.paintComponent(_graphics);
            this._graphics = _graphics;
            
            // XXX: resizeable window
            // {
            //     Rectangle rectangle = _jFrame.getBounds();
            //     _windowHeightInPixels = rectangle.height;
            //     _windowWidthInPixels = rectangle.width;
            // }
            
            {
                Point point;
                {
                    point = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(point, this);
                }
                this.mousePosition = _windowWorldFromPixel(new Vector2(point.x, point.y));
            }
            
            if (!_initialized || (hotkeysEnabled && keyPressed('r')) || _resetCalled) {
                _initialized = true;
                _resetCalled = false;
                setup();
                
                mousePressed = false;
                mouseHeld = false;
                mouseReleased = false;
                _keyPressed.clear();
                _keyHeld.clear();
                _keyReleased.clear();
                _keyToggled.clear();
                keyAnyPressed = false;
                keyLastPressed = 0;
            }
            
            loop();
            
            { // end of _jFrame
                mousePressed = false;
                mouseReleased = false;
                _keyPressed.clear();
                _keyReleased.clear();
                keyAnyPressed = false;
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
            System.exit(1);
        }
    }
    
    public static void main(String[] arguments) {
        new App().run();
    }
}
