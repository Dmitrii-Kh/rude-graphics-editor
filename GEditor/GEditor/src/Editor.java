import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.filechooser.FileFilter;


public class Editor {
	
	int mode = 0;
	int xPad;
	int xf;
	int yf;
	int yPad;
	
	boolean pressed = false;
	Color maincolor;
	MyFrame f;
	MyPanel japan;
	JButton colorbutton;
	JColorChooser tcc;
	BufferedImage imag;
	boolean loading = false;
	String fileName;
	ScrFrame sp;
	float stroke = 3.0f;
    Image img;
    boolean saved = false;

	public Editor() {
		
		f = new MyFrame("Editor");
		f.setSize(1200, 1000);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setLocationRelativeTo(null);
		
		f.addWindowListener(new WindowListener() {
			 
	        public void windowActivated(WindowEvent event) {

	        }

	        public void windowClosed(WindowEvent event) {

	        }

	        public void windowClosing(WindowEvent event) {
	        	if(saved == false) {
	            Object[] options = { "Òàê!", "Í³!" };
	            int n = JOptionPane
	                    .showOptionDialog(event.getWindow(), "Çáåðåãòè?",
	                            "Ï³äòâåðäæåííÿ", JOptionPane.YES_NO_OPTION,
	                            JOptionPane.QUESTION_MESSAGE, null, options,
	                            options[0]);
	            if (n == 1) {
	                event.getWindow().setVisible(false);
	                System.exit(0);
	            } else {
	            	try {
						JFileChooser jf = new JFileChooser();
						TextFileFilter pngFilter = new TextFileFilter(".png");
						TextFileFilter jpgFilter = new TextFileFilter(".jpg");
						if (fileName == null) {
							jf.addChoosableFileFilter(pngFilter);
							jf.addChoosableFileFilter(jpgFilter);
							int result = jf.showSaveDialog(null);
							if (result == JFileChooser.APPROVE_OPTION) {
								fileName = jf.getSelectedFile().getAbsolutePath();
							}
						}
						if (jf.getFileFilter() == pngFilter) {
							ImageIO.write(imag, "png", new File(fileName + ".png"));
						} else {
							ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));
						}
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(f, "ÐŸÐ¾Ð¼Ð¸Ð»ÐºÐ°");
					}
	            	event.getWindow().setVisible(false);
	                System.exit(0);
	            }
	        	} else {
	        		event.getWindow().setVisible(false);
	                System.exit(0);
	        	}
	        }

	        public void windowDeactivated(WindowEvent event) {

	        }

	        public void windowDeiconified(WindowEvent event) {

	        }

	        public void windowIconified(WindowEvent event) {

	        }

	        public void windowOpened(WindowEvent event) {

	        }

	    });

		maincolor = Color.black;
		
		JMenuBar menuBar = new JMenuBar();
		f.setJMenuBar(menuBar);
		menuBar.setBounds(0, 0, 300, 75);
		
		JMenu fileMenu = new JMenu("File menu");
		menuBar.add(fileMenu);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				f.setVisible(true);
			}
		});

		Action loadAction = new AbstractAction("Open") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				JFileChooser jf = new JFileChooser();
				int result = jf.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						fileName = jf.getSelectedFile().getAbsolutePath();
						File iF = new File(fileName);
						jf.addChoosableFileFilter(new TextFileFilter(".png"));
						jf.addChoosableFileFilter(new TextFileFilter(".jpg"));
						imag = ImageIO.read(iF);
						loading = true;
						f.setSize(imag.getWidth() + 40, imag.getWidth() + 80);
						japan.setSize(imag.getWidth(), imag.getWidth());
						japan.repaint();
						
//						sp = new ScrFrame(fileName);
//
//						sp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//						sp.setSize(1024, 800);
//						// sp.pack();
//						sp.setVisible(true);
						
					} catch (Exception ex) {
					}
				}
			}
		};
		
		
		Action saveAction = new AbstractAction("Save") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				try {
					JFileChooser jf = new JFileChooser();
					TextFileFilter pngFilter = new TextFileFilter(".png");
					TextFileFilter jpgFilter = new TextFileFilter(".jpg");
					if (fileName == null) {
						jf.addChoosableFileFilter(pngFilter);
						jf.addChoosableFileFilter(jpgFilter);
						int result = jf.showSaveDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							fileName = jf.getSelectedFile().getAbsolutePath();
						}
					}
					if (jf.getFileFilter() == pngFilter) {
						ImageIO.write(imag, "png", new File(fileName + ".png"));
					} else {
						ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));
					}
					saved = true;
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(f, "ÐŸÐ¾Ð¼Ð¸Ð»ÐºÐ°");
				}
			}
		};
		
		
		Action print = new AbstractAction("Print") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				PrinterJob pj = PrinterJob.getPrinterJob();
				pj.setJobName(" Print Component ");

				pj.setPrintable(new Printable() {
					public int print(Graphics pg, PageFormat pf, int pageNum) {
						if (pageNum > 0)
							return Printable.NO_SUCH_PAGE;

						Graphics2D g2 = (Graphics2D) pg;
						g2.translate(pf.getImageableX(), pf.getImageableY());

						return Printable.PAGE_EXISTS;
					}
				});
				if (pj.printDialog() == false)
					return;
				try {
					pj.print();
				} catch (PrinterException ex) {
				}
			}
		};
		
		Action newAction = new AbstractAction("New") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				f.dispose();
				new Editor();	
			}
		};
		
		Action scrAction = new AbstractAction("Scrollable Frame") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				JFileChooser jf = new JFileChooser();
				int result = jf.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						fileName = jf.getSelectedFile().getAbsolutePath();
						File iF = new File(fileName);
						jf.addChoosableFileFilter(new TextFileFilter(".png"));
						jf.addChoosableFileFilter(new TextFileFilter(".jpg"));
						imag = ImageIO.read(iF);
						loading = true;
						f.setSize(imag.getWidth() + 40, imag.getWidth() + 80);
						japan.setSize(imag.getWidth(), imag.getWidth());
						japan.repaint();
						
						sp = new ScrFrame(fileName);

						sp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						sp.setSize(1024, 800);
						// sp.pack();
						sp.setVisible(true);
						
					} catch (Exception ex) {
					}
				}
			}
		};
		
		Action copyAction = new AbstractAction("Cut") {
			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event) {
				
				try {
					
					ImageIO.write(imag, "png", new File("back" + ".png"));
			        new ScreenImage();
					
				} catch (Exception ex) { }    
				}
			};
			
			

			Action pasteAction = new AbstractAction("Paste") {
				
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent event) {
					
					try {
						
						File back = new File("back.png");
						img = ImageIO.read(back);
						
						File iF = new File("Capture.png");
						imag = ImageIO.read(iF);
						loading = true;	
						f.setSize(1200, 1000);
						japan.setSize(f.getWidth()-50, f.getWidth()-50);
						japan.repaint();
						
					} catch (Exception ex) { }
					}
				};

		
		JMenuItem loadMenu = new JMenuItem(loadAction);
		fileMenu.add(loadMenu);
		JMenuItem printmenu = new JMenuItem(print);
		fileMenu.add(printmenu);
		JMenuItem saveMenu = new JMenuItem(saveAction);
		fileMenu.add(saveMenu);
		JMenuItem newMenu = new JMenuItem(newAction);
		fileMenu.add(newMenu);
		JMenuItem scrMenu = new JMenuItem(scrAction);
		fileMenu.add(scrMenu);
		JMenuItem copyMenu = new JMenuItem(copyAction);
		fileMenu.add(copyMenu);
		JMenuItem pasteMenu = new JMenuItem(pasteAction);
		fileMenu.add(pasteMenu);
		
		
		japan = new MyPanel();
		japan.setBounds(50, 30, 30, 30);
		japan.setBackground(Color.white);
		japan.setOpaque(true);
		
//		JScrollBar hbar = new JScrollBar(
//                JScrollBar.HORIZONTAL, 30, 20, 0, 300);
//        JScrollBar vbar = new JScrollBar(
//                JScrollBar.VERTICAL, 30, 40, 0, 300);
//         
//
//        hbar.setUnitIncrement(2);
//        hbar.setBlockIncrement(1);
//         
//        hbar.addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//              System.out.println("JScrollBar's current value = " + hbar.getValue());
//              japan.repaint();
//            }
//          });
//        
//        vbar.addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//              System.out.println("JScrollBar's current value = " + hbar.getValue());
//              japan.repaint();
//            }
//          });
//        
//        japan.add(hbar, BorderLayout.SOUTH);
//        japan.add(vbar, BorderLayout.EAST);
		
        
		
		f.add(japan);

		/*JScrollPane pane = new JScrollPane(japan, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setBounds(50,50,800,800);
		//pane.setPreferredSize(new Dimension(600,600));
		f.add(pane);*/

		JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);
		
		JButton penbutton = new JButton(new ImageIcon("pen1.png"));
		penbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 0;
			}
		});
		toolbar.add(penbutton);
		JButton brushbutton = new JButton(new ImageIcon("brush1.png"));
		brushbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 1;
			}
		});
		toolbar.add(brushbutton);
		JButton lasticbutton = new JButton(new ImageIcon("lastik1.png"));
		lasticbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 2;
			}
		});
		toolbar.add(lasticbutton);

		JButton textbutton = new JButton(new ImageIcon("text1.png"));
		textbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 3;
			}
		});
		toolbar.add(textbutton);

		JButton linebutton = new JButton(new ImageIcon("line1.png"));
		linebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 4;
			}
		});
		toolbar.add(linebutton);

		JButton elipsbutton = new JButton(new ImageIcon("ellipse11.png"));
		elipsbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 5;
			}
		});
		toolbar.add(elipsbutton);

		JButton rectbutton = new JButton(new ImageIcon("rect1.png"));
		rectbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 6;
			}
		});
		toolbar.add(rectbutton);
		
		JButton fillbutton = new JButton(new ImageIcon("bucket1.png"));
		fillbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 7;
			}
		});
		toolbar.add(fillbutton);
		
		JButton roundRectbutton = new JButton(new ImageIcon("RRect1.png"));
		roundRectbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 8;
			}
		});
		toolbar.add(roundRectbutton);
		
		JButton drawArc = new JButton(new ImageIcon("arc1.png"));
		drawArc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 9;
			}
		});
		toolbar.add(drawArc);
		
		JButton triangle = new JButton(new ImageIcon("tr1.png"));
		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 14;
			}
		});
		toolbar.add(triangle);
		
		JButton fillOval = new JButton(new ImageIcon("filledOval1.png"));
		fillOval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 10;
			}
		});
		toolbar.add(fillOval);
		
		JButton fillRect = new JButton(new ImageIcon("filleRect1.png"));
		fillRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 11;
			}
		});
		toolbar.add(fillRect);
		
		JButton fillRoundRect = new JButton(new ImageIcon("filledRRect1.png"));
		fillRoundRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 12;
			}
		});
		toolbar.add(fillRoundRect);
		
		JButton grad = new JButton(new ImageIcon("grad1.png"));
		grad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 13;
			}
		});
		toolbar.add(grad);
		
		JButton strokeinc = new JButton(new ImageIcon("plus.png"));
		strokeinc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 15;
			}
		});
		toolbar.add(strokeinc);
		
		JButton strokedec = new JButton(new ImageIcon("minus.png"));
		strokedec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mode = 16;
			}
		});
		toolbar.add(strokedec);
		

		toolbar.setBounds(0, 0, 50, 780);
		f.add(toolbar);

		///////////////////////////////////////////////////////////////
		
		
		JToolBar colorbar = new JToolBar("Colorbar", JToolBar.HORIZONTAL);
		colorbar.setBounds(900, 0, 200, 180);
		colorbutton = new JButton();
		colorbutton.setBackground(maincolor);
		colorbutton.setBounds(15, 5, 20, 20);
		colorbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				ColorDialog coldi = new ColorDialog(f, "Palette");
				coldi.setVisible(true);
			}
		});
		colorbar.add(colorbutton);

		JButton redbutton = new JButton();
		redbutton.setBackground(Color.red);
		redbutton.setBounds(40, 5, 15, 15);
		redbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.red;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(redbutton);

		JButton orangebutton = new JButton();
		orangebutton.setBackground(Color.orange);
		orangebutton.setBounds(60, 5, 15, 15);
		orangebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.orange;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(orangebutton);

		JButton yellowbutton = new JButton();
		yellowbutton.setBackground(Color.yellow);
		yellowbutton.setBounds(80, 5, 15, 15);
		yellowbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.yellow;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(yellowbutton);

		JButton greenbutton = new JButton();
		greenbutton.setBackground(Color.green);
		greenbutton.setBounds(100, 5, 15, 15);
		greenbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.green;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(greenbutton);

		JButton bluebutton = new JButton();
		bluebutton.setBackground(Color.blue);
		bluebutton.setBounds(120, 5, 15, 15);
		bluebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.blue;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(bluebutton);

		JButton cyanbutton = new JButton();
		cyanbutton.setBackground(Color.cyan);
		cyanbutton.setBounds(140, 5, 15, 15);
		cyanbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.cyan;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(cyanbutton);

		JButton magentabutton = new JButton();
		magentabutton.setBackground(Color.magenta);
		magentabutton.setBounds(160, 5, 15, 15);
		magentabutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.magenta;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(magentabutton);

		JButton whitebutton = new JButton();
		whitebutton.setBackground(Color.white);
		whitebutton.setBounds(180, 5, 15, 15);
		whitebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.white;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(whitebutton);

		JButton blackbutton = new JButton();
		blackbutton.setBackground(Color.black);
		blackbutton.setBounds(200, 5, 15, 15);
		blackbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				maincolor = Color.black;
				colorbutton.setBackground(maincolor);
			}
		});
		colorbar.add(blackbutton);

		colorbar.setLayout(null);
		f.add(colorbar);
		
		
//////////////////////////////////////////////////////////////////////
		
		
		tcc = new JColorChooser(maincolor);
		tcc.getSelectionModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maincolor = tcc.getColor();
				colorbutton.setBackground(maincolor);
			}
		});
		
		
	
	japan.addMouseMotionListener(new MouseMotionAdapter() {
			
			public void mouseDragged(MouseEvent e) {
				if (pressed == true) {
					Graphics g = imag.getGraphics();
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(maincolor);
					switch (mode) {
					case 0:
						g2.drawLine(xPad, yPad, e.getX(), e.getY());
						break;
					case 1:
						g2.setStroke(new BasicStroke(stroke));
						g2.drawLine(xPad, yPad, e.getX(), e.getY());
						break;
					case 2:
						g2.setStroke(new BasicStroke(5.0f));
						g2.setColor(Color.WHITE);
						g2.drawLine(xPad, yPad, e.getX(), e.getY());
						break;
					}
					xPad = e.getX();
					yPad = e.getY();
				}
				japan.repaint();
			}
		});  
		
	japan.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e) {
				Graphics g = imag.getGraphics();
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(maincolor);
				switch (mode) {
				case 0:
					g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
					break;
				case 1:
					g2.setStroke(new BasicStroke(stroke));
					g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
					break;
				case 2:
					g2.setStroke(new BasicStroke(5.0f));
					g2.setColor(Color.WHITE);
					g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
					break;
				case 3:
					japan.requestFocus();
					break;
				case 7:
					g2.setColor(maincolor);
				    g2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
					break;
				case 13:
					GradientPaint redtowhite = new GradientPaint(0,0,maincolor,japan.getWidth(), 0,Color.WHITE);
					g2.setPaint(redtowhite);
					g2.fill (new Rectangle.Double(0, 0, japan.getWidth(), japan.getHeight()));
				}
				xPad = e.getX();
				yPad = e.getY();
				pressed = true;
				japan.repaint();
			}

			public void mousePressed(MouseEvent e) {
				xPad = e.getX();
				yPad = e.getY();
				xf = e.getX();
				yf = e.getY();
				pressed = true;
			}

			public void mouseReleased(MouseEvent e) {
				Graphics g = imag.getGraphics();
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(maincolor);
				int x1 = xf, x2 = xPad, y1 = yf, y2 = yPad;
				if (xf > xPad) {
					x2 = xf;
					x1 = xPad;
				}
				if (yf > yPad) {
					y2 = yf;
					y1 = yPad;
				}
				switch (mode) {
				case 4:
					g.drawLine(xf, yf, e.getX(), e.getY());
					break;
				case 5:
					g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
					break;
				case 6:
					g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
					break;
				case 8:
					g.drawRoundRect(x1, y1, (x2 - x1), (y2 - y1), 30, 30);
					break;
				case 9:
					g.fillArc(x1, y1, (x2 - x1), (y2 - y1), 30, 30);
					break;
				case 10:
					g.fillOval(x1, y1, (x2 - x1), (y2 - y1));
					break;
				case 11:
					g.fillRect(x1, y1, (x2 - x1), (y2 - y1));
					break;
				case 12:
					g.fillRoundRect(x1, y1, (x2 - x1), (y2 - y1), 30, 30);
					break;
				case 14:
					g.drawPolygon(new int[] {x1, x1+60, x1+120}, new int[] {y1+100, y1+10, y1+100}, 3);
					break;
				case 15:
					stroke = stroke + 7.0f;
					break;
				case 16:
					if(stroke > 3) {
					stroke = stroke - 7.0f;
					}
					break;
				}
				xf = 0;
				yf = 0;
				pressed = false;
				japan.repaint();
			}
	});
		
		
		japan.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				japan.requestFocus();
		}

			public void keyTyped(KeyEvent e) {
				if (mode == 3) {
					Graphics g = imag.getGraphics();
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(maincolor);
					g2.setStroke(new BasicStroke(2.0f));
					String str = new String("");
					str += e.getKeyChar();
					g2.setFont(new Font("Arial", 2, 12));
					g2.drawString(str, xPad, yPad);
					xPad += 10;
					japan.requestFocus();
					japan.repaint();
				}
			}
		});
		
		
		
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				if (loading == false) {
					japan.setSize(f.getWidth() - 40, f.getHeight() - 80);
					BufferedImage tempImage = new BufferedImage(japan.getWidth(), japan.getHeight(),
							BufferedImage.TYPE_INT_RGB);
					Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
					d2.setColor(Color.white);
					d2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
					tempImage.setData(imag.getRaster());
					imag = tempImage;
					japan.repaint();
				}
				loading = false;
			}
		});
		
		f.setLayout(null);
		f.setVisible(true);
		//f.setContentPane(new ScrollBarExample());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
		            // Set cross-platform Java L&F (also called "Metal")
		        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		    } 
		    catch (UnsupportedLookAndFeelException e) {
		       // handle exception
		    }
		    catch (ClassNotFoundException e) {
		       // handle exception
		    }
		    catch (InstantiationException e) {
		       // handle exception
		    }
		    catch (IllegalAccessException e) {
		       // handle exception
		    }
				new Editor();
			}
		});
	}

	class ColorDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ColorDialog(JFrame owner, String title) {
			super(owner, title, true);
			add(tcc);
			setSize(650, 400);
		}
	}

	class MyFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			super.paint(g);
		}

		public MyFrame(String title) {
			super(title);
		}
	}

	class MyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanel() {
		}

		public void paintComponent(Graphics g) {
			if (imag == null) {
				imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D d2 = (Graphics2D) imag.createGraphics();
				d2.setColor(Color.white);
				d2.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
			
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
			g.drawImage(imag, 0, 0, this);
	
		}
	}

	class TextFileFilter extends FileFilter {
		private String ext;

		public TextFileFilter(String ext) {
			this.ext = ext;
		}

		public boolean accept(java.io.File file) {
			if (file.isDirectory())
				return true;
			return (file.getName().endsWith(ext));
		}

		public String getDescription() {
			return "*" + ext;
		}
	}
	class ScrFrame extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		JLabel label = new JLabel();

		JScrollPane scrollPane = new JScrollPane();

		public ScrFrame(String fileName) {

			label.setIcon(new ImageIcon(fileName));

			scrollPane.setViewportView(label);

			add(scrollPane);

		}

	}
	
	
	class ScreenImage {


	    public ScreenImage() {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                    ex.printStackTrace();
	                }
	                try {
	                    Robot robot = new Robot();
	                    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	                    final BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));

	                    JFrame frame = new JFrame("Testing");
	                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	                    frame.add(new TestPane(screen));
	                    frame.setSize(400, 400);
	                    frame.setLocationRelativeTo(null);
	                    frame.setVisible(true);
	                } catch (AWTException exp) {
	                    exp.printStackTrace();
	                }
	            }
	        });
	    }

	    public class TestPane extends JPanel {

	        private BufferedImage master;

	        public TestPane(BufferedImage image) {
	            this.master = image;
	            setLayout(new BorderLayout());
	            final ImagePane imagePane = new ImagePane(image);
	            add(new JScrollPane(imagePane));

	            JButton btnSave = new JButton("Save");
	            add(btnSave, BorderLayout.SOUTH);

	            btnSave.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    try {
	                        BufferedImage img = imagePane.getSubImage();
	                        master = append(master, img);
	                        File save = new File("Capture.png");
	                        ImageIO.write(master, "png", save);
	                        imagePane.clearSelection();
	                        JOptionPane.showMessageDialog(TestPane.this, save.getName() + " was saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
	                    } catch (IOException ex) {
	                        ex.printStackTrace();
	                        JOptionPane.showMessageDialog(TestPane.this, "Failed to save capture", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                }

	                public BufferedImage append(BufferedImage master, BufferedImage sub) {

	                    // Create a new image which can hold both background and the
	                    // new image...
	                    BufferedImage newImage = new BufferedImage(sub.getWidth(), sub.getHeight(), BufferedImage.TYPE_INT_ARGB);
	                    // Get new image's Graphics context
	                    Graphics2D g2d = newImage.createGraphics();
	                    // Draw the old background
	                  //  g2d.drawImage(master, 0, 0, null);
	                    // Position and paint the new sub image...
	                    //int y = (newImage.getHeight() - sub.getHeight()) / 2;
	                    g2d.drawImage(sub, null, 0,0);
	                    g2d.dispose();

	                    return newImage;

	                }

	            });

	        }

	    }

	    public class ImagePane extends JPanel {

	        private BufferedImage background;
	        private Rectangle selection;

	        public ImagePane(BufferedImage img) {
	            background = img;
	            MouseAdapter ma = new MouseAdapter() {

	                private Point clickPoint;

	                @Override
	                public void mousePressed(MouseEvent e) {
	                    clickPoint = e.getPoint();
	                }

	                @Override
	                public void mouseDragged(MouseEvent e) {
	                    Point dragPoint = e.getPoint();

	                    int x = Math.min(clickPoint.x, dragPoint.x);
	                    int y = Math.min(clickPoint.y, dragPoint.y);
	                    int width = Math.abs(clickPoint.x - dragPoint.x);
	                    int height = Math.abs(clickPoint.y - dragPoint.y);

	                    selection = new Rectangle(x, y, width, height);
	                    repaint();

	                }

	            };

	            addMouseListener(ma);
	            addMouseMotionListener(ma);
	        }

	        public void clearSelection() {
	            selection = null;
	            repaint();
	        }

	        public BufferedImage getSubImage() {

	            BufferedImage img = null;
	            if (selection != null) {

	                img = background.getSubimage(selection.x, selection.y, selection.width, selection.height);

	            }
	            return img;

	        }

	        @Override
	        public Dimension getPreferredSize() {
	            return new Dimension(background.getWidth(), background.getHeight());
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g.create();
	            int x = (getWidth() - background.getWidth()) / 2;
	            int y = (getHeight() - background.getHeight()) / 2;
	            g2d.drawImage(background, x, y, this);
	            if (selection != null) {
	                Color stroke = UIManager.getColor("List.selectionBackground");
	                Color fill = new Color(stroke.getRed(), stroke.getGreen(), stroke.getBlue(), 128);
	                g2d.setColor(fill);
	                g2d.fill(selection);
	                g2d.setColor(stroke);
	                g2d.draw(selection);
	            }
	            g2d.dispose();
	        }

	    }


}
}