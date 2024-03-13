package SimplePaintProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimplePaint extends JFrame {

    //To Save alot of shapes and to repaint it when any shape painted
    ArrayList<Shape> shapes = new ArrayList<>();
    //interface to any shape to can extends it and implements it's own draw method
    Shape currentShape = null;
    Color currentColor = Color.BLACK;
    // 0: line, 1: rectangle, 2: oval, 3: free pen , 4: erasser 
    int shapeType = 0;
    //for controlling draw and fill 
    boolean fillShape = false;

    
    public SimplePaint() {
        setTitle("Simple Paint Program");
        setSize(1100, 800);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        //Creation of Clear Button
        JButton clearButton = new JButton("Clear");
        //Anonymous Inner Class
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDrawing();
            }
        });

        //Creation of Color Button
        JButton colorButton = new JButton("Color");
        //Anonymous Inner Class
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", currentColor);
                if (newColor != null) {
                    currentColor = newColor;
                }
            }
        });

        //Creation of Fill Button
        JToggleButton fillButton = new JToggleButton("Fill");
        //Anonymous Inner Class
        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillShape = fillButton.isSelected();
            }
        });

        //Creation of Line Button
        JButton lineButton = new JButton("Line");
        //Anonymous Inner Class
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapeType = 0;
            }
        });

        //Creation of Rectangle Button
        JButton rectangleButton = new JButton("Rectangle");
        //Anonymous Inner Class
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeType = 1;
            }
        });

        //Creation of Oval Button
        JButton ovalButton = new JButton("Oval");
        //Anonymous Inner Class
        ovalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeType = 2;
            }
        });

        //Creation of Free Pen Button
        JButton freePenButton = new JButton("Free Pen");
        //Anonymous Inner Class
        freePenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeType = 3;
            }
        });

        //Creation of Erasser Button
        JButton ErasserButton = new JButton("Erasser");
        //Anonymous Inner Class
        ErasserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeType = 4;
            }
        });
        
        // first display the two names in one line but I make search about how to display it in two lines
        // JLabel nameLabel = new JLabel("Prepared By: Mostafa Wael --- Mostafa Moahmed");
        
        //display the our names in two lines
        JLabel PreparedBy = new JLabel("<html>Prepared By:<br>Mostafa Wael Abdelaziz<br>Mostafa Mohamed Abdelhamid</html>");
        controlPanel.add(clearButton);
        controlPanel.add(colorButton);
        controlPanel.add(fillButton);
        controlPanel.add(lineButton);
        controlPanel.add(rectangleButton);
        controlPanel.add(ovalButton);
        controlPanel.add(freePenButton);
        controlPanel.add(ErasserButton);
        // controlPanel.add(nameLabel);
        controlPanel.add(PreparedBy);

        //Location of the Buttons Bar
        add(controlPanel, BorderLayout.SOUTH);

        JPanel drawingPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(currentColor);
                for (Shape shape : shapes) {
                    shape.draw(g);
                }
                if (currentShape != null) {
                    currentShape.draw(g);
                }
            }
        };

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    shapes.add(currentShape);
                    currentShape = null;
                }
            }
        });

        //Confogration for my Drawing Space
        drawingPanel.addMouseMotionListener(new DrawingMouseMotionListener());
        add(drawingPanel, BorderLayout.CENTER);
        drawingPanel.setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void clearDrawing() {
        shapes.clear();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimplePaint();
            }
        });
    }

    int prevX, prevY, currX, currY;

    static class Erasser implements Shape {

        int x1, y1;

        public Erasser(int x1, int y1, Color color) {
            this.x1 = x1;
            this.y1 = y1;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillOval(x1, y1, 20, 20);
        }
    }

    interface Shape {

        void draw(Graphics g);
    }

    class Line implements Shape {

        int x1, y1, x2, y2;
        Color color;
        // int thikness = 20 ; 

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;

        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    class Rectangle implements Shape {

        int x, y, width, height;
        Color color;
        boolean filled;

        public Rectangle(int x, int y, int width, int height, Color color, boolean filled) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.filled = filled;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            if (filled) {
                g.fillRect(x, y, width, height);
            } else {
                g.drawRect(x, y, width, height);
            }
        }
    }

    class Oval implements Shape {

        int x, y, width, height;
        Color color;
        boolean filled;

        public Oval(int x, int y, int width, int height, Color color, boolean filled) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.filled = filled;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            if (filled) {
                g.fillOval(x, y, width, height);
            } else {
                g.drawOval(x, y, width, height);
            }
        }
    }

    class DrawingMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            currX = e.getX();
            currY = e.getY();
            switch (shapeType) {
                // for Drawing Lines
                case 0:
                    currentShape = new Line(prevX, prevY, currX, currY, currentColor);
                    break;
                // for Drawing Rectangles
                case 1:
                    currentShape = new Rectangle(Math.min(prevX, currX), Math.min(prevY, currY),
                            Math.abs(currX - prevX), Math.abs(currY - prevY), currentColor, fillShape);
                    break;
                // for Drawing Ovals
                case 2:
                    currentShape = new Oval(Math.min(prevX, currX), Math.min(prevY, currY),
                            Math.abs(currX - prevX), Math.abs(currY - prevY), currentColor, fillShape);
                    break;
                // for free pen
                case 3:
                    if (currentShape != null) {
                        shapes.add(currentShape);
                    }
                    currentShape = new Line(prevX, prevY, currX, currY, currentColor);
                    prevX = currX;
                    prevY = currY;
                    break;
                // for erasser
                case 4:
                    if (currentShape != null) {
                        shapes.add(currentShape);
                    }
                    currentShape = new Erasser(prevX, prevY, Color.white);
                    prevX = currX;
                    prevY = currY;
                    break;
                default:
                    break;
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}
