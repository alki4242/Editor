package alki4242;

import java.lang.Integer;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;

public class text_editor {
    String surum = "V1.2.8";
    String acilandosya = "s";
    Boolean kayitli = true;
    private JFrame frmTextEditor;
    public text_editor() {
			initalize();
		
      
        
    }
    public static void main(String[] args) {
        text_editor window = new text_editor();
        window.frmTextEditor.setVisible(true);
    }
	public void initalize() {
		File klasor = new File ("./kaynaklar");
		if (!klasor.exists()) {
			try {
				klasor.mkdirs();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
        		JOptionPane.showMessageDialog(frmTextEditor, "Kritik Dosya Hatası!\n" + e1);

			}
		}
        File f = new File("kaynaklar/ayarlar.ead");
        Properties props = new Properties();
        File rec = new File("kaynaklar/s.ead");
        File favs = new File("kaynaklar/f.ead");
        if (f.exists()) {
            try {
                FileReader reader = new FileReader(f, StandardCharsets.UTF_8);
                props.load(reader);
            } catch (Exception e) {
            		JOptionPane.showMessageDialog(frmTextEditor, "Kritik Dosya Hatası!\n" + e);
            }
        } else {
            try {
                props.setProperty("Font", "Calibri");
                props.setProperty("metinr", "java.awt.Color[r=0,g=0,b=0]");
                props.setProperty("metins", "java.awt.Color[r=0,g=0,b=0]");
                props.setProperty("metinb", "20");
                props.setProperty("kalin", "0");
                props.setProperty("arkaplan", "java.awt.Color[r=255,g=255,b=255]");
                props.setProperty("tabsize", "2");
                props.store(new FileOutputStream("kaynaklar/ayarlar.ead"), null);
                FileReader reader = new FileReader(f, StandardCharsets.UTF_8);
                props.load(reader);
            } catch (IOException g) {
        		JOptionPane.showMessageDialog(frmTextEditor, "Kritik Dosya Hatası!\n" + g);

            }
        }
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        String bfont = props.getProperty("Font");
        String bbmr = props.getProperty("metinr");
        Scanner bbmrs = new Scanner(bbmr);
        bbmrs.useDelimiter("\\D+");
        Color bmr = new Color(bbmrs.nextInt(), bbmrs.nextInt(), bbmrs.nextInt());
        String bbms = props.getProperty("metins");
        Scanner bbmss = new Scanner(bbms);
        bbmss.useDelimiter("\\D+");
        Color bms = new Color(bbmss.nextInt(), bbmss.nextInt(), bbmss.nextInt());
        String bbmb = props.getProperty("metinb");
        int bmb = Integer.decode(bbmb).intValue();
        String bmbt = props.getProperty("kalin");
        int mbt = Integer.decode(bmbt).intValue();
        String barkplan = props.getProperty("arkaplan");
        Scanner arkplans = new Scanner(barkplan);
        arkplans.useDelimiter("\\D+");
        Color arkplan = new Color(arkplans.nextInt(), arkplans.nextInt(), arkplans.nextInt());
        String btabb = props.getProperty("tabsize");
        int tabb = Integer.decode(btabb).intValue();
        frmTextEditor = new JFrame();
        frmTextEditor.setIconImage(null);
        frmTextEditor.setTitle("Editör " + surum + " - Başlıksız");
        frmTextEditor.setSize(500, 550);
        frmTextEditor.setBounds(100, 200, 550, 630);
        frmTextEditor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frmTextEditor.getContentPane().setLayout(new BorderLayout(0, 0));
        JTextArea textRegion = new JTextArea();
        frmTextEditor.getContentPane().add(textRegion, BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        textRegion.setTabSize(tabb);
        textRegion.setSelectedTextColor(bms);
        JLabel label = new JLabel();
        textRegion.setDragEnabled(true);
        textRegion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
            	
                String kelimeler = textRegion.getText();
                String kelimeayri[] = kelimeler.split("\\s");
                int karakterler = kelimeler.length();
                label.setText("Karakter Sayısı: " + karakterler + " Kelime Sayısı: " + kelimeayri.length);
                kayitli = false;
                

            }
        });
        frmTextEditor.setJMenuBar(menuBar);
        String text = textRegion.getText();
        UndoManager undo = new UndoManager();
        textRegion.getDocument().addUndoableEditListener(undo);
        textRegion.setFont(new Font(bfont, mbt, bmb));
        textRegion.setBackground(arkplan);
        textRegion.setForeground(bmr);
        textRegion.setTabSize(tabb);
        JMenu mnFile = new JMenu("Dosya");
        menuBar.add(mnFile);
        JMenu mnEdit = new JMenu("Düzenle");
        menuBar.add(mnEdit);
        JMenu mnekle = new JMenu("Ekle");
        menuBar.add(mnekle);
        JMenu mnbul = new JMenu("Bul");
        menuBar.add(mnbul);          
        JMenu mnbicim = new JMenu("Biçim");
        menuBar.add(mnbicim);       
        JMenu mninfo = new JMenu("Hakkında");
        menuBar.add(mninfo);       
        JLabel boyuty = new JLabel();
        //Dosya Menüsü
        JMenuItem mntmNew = new JMenuItem("Yeni Dosya");
        mnFile.add(mntmNew);
        JMenuItem mntmOpen = new JMenuItem("Aç");
    	mnFile.add(mntmOpen);
   	 	JMenuItem mntmSavef = new JMenuItem("Kaydet");
   	 	mnFile.add(mntmSavef);
        JMenuItem mntmSave = new JMenuItem("Farklı Kaydet");
   	 	mnFile.add(mntmSave);
   	 	JSeparator scizgi = new JSeparator();
	 	mnFile.add(scizgi);
   	 	JMenu sondosyalar = new JMenu("Son Dosyalar");
   	 	mnFile.add(sondosyalar);
   	 	JMenu favoriler = new JMenu("Favoriler");
   	 	mnFile.add(favoriler);
   	 	JSeparator ccizgi = new JSeparator();
   	 	mnFile.add(ccizgi);
   	 	JMenuItem mntmExit = new JMenuItem("Çıkış");
   	 	mnFile.add(mntmExit);
        boyuty.setText("Metin Boyutu");
        mnbicim.add(boyuty);
        boyuty.setToolTipText("Karakterlerin Büyüklüğünü Seçin");
        JSlider boyut = new JSlider(JSlider.HORIZONTAL,
                20, 50, bmb);
        boyut.setName("Metin Boyutu");
        boyut.setPaintTicks(true);
        boyut.setPaintLabels(true);
        boyut.setMajorTickSpacing(2);
        
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JList fontlar = new JList(fonts);
        fontlar.setSelectedValue(props.getProperty("Font"), false);
        JScrollPane sp = new JScrollPane(fontlar);     
        fontlar.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
            	
                String selectedFamilyName = fontlar.getSelectedValue().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("Font", fontlar.getSelectedValue().toString());
            }	
        });
        boyut.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	String selectedFamilyName = fontlar.getSelectedValue().toString();
            	
               
            	
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
                int k = boyut.getValue();
                String ks = String.valueOf(k);
                props.setProperty("metinb", ks);
            }
        });
        mnbicim.add(boyut);
        JLabel taby = new JLabel();
        taby.setText("Tab(Sekme) Boyutu");
        mnbicim.add(taby);
        taby.setToolTipText("Bir TAB(Sekme) Tuşu Basıldığı Zaman Atlanıcak Boşluk Sayısını Seçin");
        JSlider tab = new JSlider(JSlider.HORIZONTAL, 2, 8, 2);
        tab.setPaintTicks(true);
        tab.setPaintLabels(true);
        tab.setMajorTickSpacing(1);
        mnbicim.add(tab);
        tab.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                textRegion.setTabSize(tab.getValue());
                int k = boyut.getValue();
                String ks = String.valueOf(k);
                props.setProperty("tabsize", ks);
            }
        });
        
        JLabel fontbilgi = new JLabel();
        fontbilgi.setText("Fontlar");
        mnbicim.add(fontbilgi);
        mnbicim.add(sp);
        JButton ince = new JButton();
        ince.setText("Italik");
        ince.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedValue().toString();
                Font font = new Font(selectedFamilyName, Font.ITALIC, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "2");

            }
        });
        JButton kalin = new JButton();
        kalin.setText("Kalın");
        JMenuItem selecall = new JMenuItem("Hepsini Seç");
        selecall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));        
        selecall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.selectAll();
            }
        });
        mnEdit.add(selecall);
        kalin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedValue().toString();
                Font font = new Font(selectedFamilyName, Font.BOLD, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "1");

            }
        });
        JButton cizili = new JButton();
        cizili.setText("Sıfırla");
        cizili.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedValue().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "0");

            }
        });
        JLabel duzey = new JLabel();
        duzey.setText("Yazı Türü");
        mnbicim.add(duzey);
        mnbicim.add(ince);
        mnbicim.add(kalin);
        mnbicim.add(cizili);
        JSeparator mrenkcizgi = new JSeparator();
        mnbicim.add(mrenkcizgi);
        JMenuItem renksec = new JMenuItem("Seçili metin rengi");
        renksec.setToolTipText("İmleç İle Seçilen Metinlerin \n Görüntülenme Rengini Seçin");
        mnbicim.add(renksec);
        renksec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renkmetin = new JColorChooser();
                Color c = JColorChooser.showDialog(renkmetin, "Renk Menüsü", Color.black);
                textRegion.setSelectedTextColor(c);
                props.setProperty("metins", c.toString());

            }
        });
        JMenuItem metinyazi = new JMenuItem("Metin Rengi");
        metinyazi.setToolTipText("Yazılı Karakterlerin Rengini Seçin");
        metinyazi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renk = new JColorChooser();
                Color c = JColorChooser.showDialog(renk, "Renk Menüsü", Color.black);
                textRegion.setForeground(c);
                props.setProperty("metinr", c.toString());
            }
        });
        mnbicim.add(metinyazi);
        JMenuItem renksecarkaplan = new JMenuItem("Arkaplan rengi");
        renksecarkaplan.setToolTipText("Yazı Alanının Rengini Seçin");
        mnbicim.add(renksecarkaplan);
        renksecarkaplan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renk = new JColorChooser();
                Color c = JColorChooser.showDialog(renk, "Renk Menüsü", Color.white);
                textRegion.setBackground(c);
                props.setProperty("arkaplan", c.toString());
            }
        });
        JLabel bulbilgi = new JLabel();
        bulbilgi.setText("Bul |\n Aranacak metini yazın");
        mnbul.add(bulbilgi);
        JTextField bulyazi = new JTextField();
        mnbul.add(bulyazi);
        bulyazi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!bulyazi.getText().isEmpty()) {
                    String ara = bulyazi.getText();
                    int n = textRegion.getText().indexOf(ara);
                    if (n != -1) {
                        textRegion.select(n, n + ara.length());
                        label.setText("Aratıldı");
                    } else {
                        JOptionPane.showMessageDialog(frmTextEditor, "Aratılan değer bulunamadı");
                    }
                } else {
                    JOptionPane.showMessageDialog(frmTextEditor, "Lütfen değer giriniz!");
                }
            }
        });
        JLabel ayirbulrep = new JLabel();
        mnbul.add(ayirbulrep);
        JLabel repbilgi = new JLabel();
        repbilgi.setText("Bul ve Değiştir|\n Yeni metini yazın");
        mnbul.add(repbilgi);
        JTextField repyazi = new JTextField();
        mnbul.add(repyazi);
        repyazi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!bulyazi.getText().isEmpty()) {
                    String ara = bulyazi.getText();
                    int n = textRegion.getText().indexOf(ara);
                    if (n != -1) {
                        String reple = repyazi.getText();
                        textRegion.replaceRange(reple, n, n + ara.length());
                        label.setText("Değiştirildi");
                    } else {
                        JOptionPane.showMessageDialog(frmTextEditor, "Aratılan değer bulunamadı");
                    }
                } else {
                    JOptionPane.showMessageDialog(frmTextEditor, "Lütfen değiştirilecek değeri giriniz!");
                }

            }
        });
        JMenuItem mnclearall = new JMenuItem("Hepsini Temizle");
        mnclearall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textRegion.setText("");
                label.setText("Sayfa Temizlendi");
            }
        });
        mnEdit.add(mnclearall);
        JSeparator hepcizgi = new JSeparator();
        mnEdit.add(hepcizgi);
       
        mntmNew.setToolTipText("Yeni Bir Dosya Oluşturun");
        mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (kayitli) {
                    textRegion.setText("");
                    acilandosya = "s";
                    label.setText("Yeni Dosya açıldı");
                    kayitli = true;
                    frmTextEditor.setTitle("Editör " + surum + " - Yeni Dosya");
                } else {
                    int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Dosyayı kaydetmeden yeni dosya oluşturuyorsun emin misin?", "Uyarı!",
                            JOptionPane.YES_NO_OPTION);
                    if (uyari == JOptionPane.YES_OPTION) {
                        textRegion.setText("");
                        acilandosya = "s";
                        label.setText("Yeni Dosya açıldı");
                        frmTextEditor.setTitle("Editör " + surum + " - Yeni Dosya");
                        kayitli = true;

                    }
                }

            }
        });
        
        JMenuItem mninfoin = new JMenuItem("Hakkında");
        mninfoin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frmTextEditor, "Yapımcı: alki4242 \n Sürüm: " + surum);
            }
        });
        mninfo.add(mninfoin);
        
        DefaultListModel<String> l1 = new DefaultListModel<>();

       
        
        sondosyalar.setToolTipText("En Son Açtığınız Dosyaları Görüntüleyin");
        JMenuItem listemizle = new JMenuItem("Listeyi Temizle");
        listemizle.setToolTipText("Son Dosyalar Listesini Temizleyin");
        sondosyalar.add(listemizle);
        Scanner sondosyaaktar = null;
		try {
			sondosyaaktar = new Scanner(rec, StandardCharsets.UTF_8);
		} catch (Exception e1) {
			
		}
        try {       	
        
        while (sondosyaaktar.hasNextLine()) {
        	l1.addElement(sondosyaaktar.nextLine());
        }
        sondosyaaktar.close();
        } catch (Exception ihi) {}
        JList listele = new JList(l1);  
        sondosyalar.add(listele);
        listele.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {         	
            	if (s.getClickCount() == 2) {
        		  if (kayitli != true) {
        		  int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                          "Dikkat! Dosyayı kaydetmeden başka bir dosyayı açıyorsun emin misin?", "Uyarı!", JOptionPane.YES_NO_OPTION);
                  if (uyari == JOptionPane.YES_OPTION) {
        		  File file = new File(listele.getSelectedValue().toString());
                  try {
                	  acilandosya = file.getAbsolutePath();
                	 if (!file.getName().endsWith(".zedf")) ac(file,textRegion,label,listele,rec,l1);   
                	 if (file.getName().endsWith(".zedf")) zac(textRegion,label,rec,listele,l1);   
                	 l1.add(0,acilandosya);
                  } catch (Exception ex) {
                      JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                      label.setText("Dosya Açılamadı");
                  }
        		  
        	  }
        		  } else {
        			  File file = new File(listele.getSelectedValue().toString());
                      try {
                    	  acilandosya = file.getAbsolutePath();
                     	 if (!file.getName().endsWith(".zedf")) ac(file,textRegion,label,listele,rec,l1);  
                     	 if (file.getName().endsWith(".zedf")) zac(textRegion,label,rec,listele,l1);  
                     	l1.add(0,acilandosya);
                      } catch (Exception ex) {
                          JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                          label.setText("Dosya Açılamadı");
                      }
        		  }
        	  }}});
        listemizle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		l1.clear();
        		listele.repaint();
        		rec.delete();
        		try {
					rec.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
       
        favoriler.setToolTipText("Hızlıca erişmek istediğiniz dosyaları buraya sabitleyin");
        JMenuItem favorisilall = new JMenuItem("Hepsini Temizle");
        favoriler.add(favorisilall);
        favorisilall.setToolTipText("Tüm favori dosyalarını temizleyin");
        JMenuItem secilifavorisil = new JMenuItem("Seçili Olanı Temizle");
        favoriler.add(secilifavorisil);
        secilifavorisil.setToolTipText("Favoriler Listesinde seçili olan dosyayı listeden çıkartın");
        DefaultListModel<String> l2 = new DefaultListModel<>();
        Scanner favori = null;
		try {
			favori = new Scanner(favs, StandardCharsets.UTF_8);
		} catch (Exception e1) {
			
		}
        try {       	
        
        while (favori.hasNextLine()) {
        	l2.addElement(favori.nextLine());
        }
        favori.close();
        } catch (Exception ihi) {}
        JList fav = new JList(l2);
        
        JMenuItem acıkeklefav = new JMenuItem("Bu Dosyayı Ekle");
        favoriler.add(acıkeklefav);
        acıkeklefav.setToolTipText("Şuan açık olan dosyayı favorilere ekleyin");
        JMenuItem acıksilfav = new JMenuItem("Bu Dosyayı Çıkar");
        favoriler.add(acıksilfav);
        acıksilfav.setToolTipText("Şuan açık olan dosyayı favorilerden çıkartın");

        favorisilall.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		l2.clear();
        		fav.repaint();
        		favs.delete();
        		label.setText("Favoriler Temizlendi");
        		try {
					favs.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(frmTextEditor, e.getMessage());
				}
        	}
        });
       
        acıkeklefav.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		if (acilandosya != "s") {
        		try {
        		
        		if(l2.contains(acilandosya)) l2.removeElement(acilandosya);
        		 FileWriter rece = new FileWriter(favs, StandardCharsets.UTF_8);
                 BufferedWriter receb = new BufferedWriter(rece);
                 String st = "" , st1 = "";
                 for (int i = 0; i < fav.getModel().getSize(); i++) {                                	
                 	st = fav.getModel().getElementAt(i).toString();
                 	st1 = st1 + "\n" + st;
                 }
                 l2.add(0,acilandosya); 
                 receb.write(st1);
                 fav.repaint();
                 receb.flush();
                 receb.close();
                 label.setText("Açık Dosya Favorilere Eklendi");
        		} catch (Exception as) {
        			JOptionPane.showMessageDialog(frmTextEditor, as.getMessage());
        		}
  
        		
        		}       		       		
        	}
        });
        acıksilfav.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		if (acilandosya != "s") {
        		l2.removeElement(acilandosya);
        		try {
        		 FileWriter rece = new FileWriter(favs, StandardCharsets.UTF_8);
                 BufferedWriter receb = new BufferedWriter(rece);
                 String st = "" , st1 = "";
                 for (int i = 0; i < fav.getModel().getSize(); i++) {                                	
                 	st = fav.getModel().getElementAt(i).toString();
                 	st1 = st1 + "\n" + st;
                 }
                 receb.write(st1);
                 fav.repaint();
                 receb.flush();
                 receb.close();
                 label.setText("Açık Dosya Favorilerden Silindi");
        		} catch (Exception as) {
        			JOptionPane.showMessageDialog(frmTextEditor, as.getMessage());
        		}
        		
        		}       		       		
        	}
        });
        secilifavorisil.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {             		
        		try {
        		l2.removeElement(fav.getSelectedValue().toString());
        		 FileWriter rece = new FileWriter(favs, StandardCharsets.UTF_8);
                 BufferedWriter receb = new BufferedWriter(rece);
                 String st = "" , st1 = "";
                 for (int i = 0; i < fav.getModel().getSize(); i++) {                                	
                 	st = fav.getModel().getElementAt(i).toString();
                 	st1 = st1 + "\n" + st;
                 }
                 receb.write(st1);
                 fav.repaint();
                 receb.flush();
                 receb.close();
                 label.setText("Seçili Dosya Favorilerden Silindi");
        		} catch (Exception as) {
        			JOptionPane.showMessageDialog(frmTextEditor, as.getMessage());
        		}
        	}
        });
     
        textRegion.setDropTarget(new DropTarget() {
           public synchronized void drop(DropTargetDropEvent evt) {
        	   evt.acceptDrop(DnDConstants.ACTION_COPY);
        	   try {
        		   evt.acceptDrop(DnDConstants.ACTION_COPY);
                   List<File> droppedFiles = (List<File>) evt
                           .getTransferable().getTransferData(
                                   DataFlavor.javaFileListFlavor);
                   for (File file : droppedFiles) {
                	   if (kayitli) {
                		   label.setText("Dosya Aciliyor");
                		   
                		   acilandosya = file.getAbsolutePath();
                		   if (!file.getName().endsWith(".zedf")) ac(file,textRegion,label,listele,rec,l1);   
                      	 if (file.getName().endsWith(".zedf")) zac(textRegion,label,rec,listele,l1);         
                      	l1.add(0, file.getAbsolutePath());
                        //l1.addElement(acilandosya);
                        /*FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
                        BufferedWriter receb = new BufferedWriter(rece);
                        String st = "" , st1 = "";
                        for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
                        	st = listele.getModel().getElementAt(i).toString();
                        	st1 = st1 + "\n" + st;
                        }
                        receb.write(st1);
                        listele.repaint();
                        receb.flush();
                        receb.close();*/
       				} else {
       					 int onay = JOptionPane.showConfirmDialog(frmTextEditor,
       	                            "Dikkat! Mevcut dosyayı kaydetmeden başka bir dosya açıyorsun emin misin?", "Uyari!",
       	                            JOptionPane.YES_NO_OPTION);
       	                    if (onay == JOptionPane.YES_OPTION) {
       	                        label.setText("Dosya Aciliyor");
       	                     
       	                     if (!file.getName().endsWith(".zedf")) ac(file,textRegion,label,listele,rec,l1);   
       	                	 if (file.getName().endsWith(".zedf")) zac(textRegion,label,rec,listele,l1);         
       	                	l1.add(0, file.getAbsolutePath());
                             //l1.addElement(acilandosya);
                            /* FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
                             BufferedWriter receb = new BufferedWriter(rece);
                             String st = "" , st1 = "";
                             for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
                             	st = listele.getModel().getElementAt(i).toString();
                             	st1 = st1 + "\n" + st;
                             }
                             receb.write(st1);
                             listele.repaint();
                             receb.flush();
                             receb.close();*/
       	                        }                   
                   }
				
				}
        	   } catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	   
           }});
       
        
        fav.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent s) {
       		if (s.getClickCount() == 2) {
       			if (kayitli != true) {
      		 int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                     "Bu Favori Dosya Açılsın mı? \n(Kaydedilmeyen veriler silinecek)", "Uyarı!", JOptionPane.YES_NO_OPTION);
             if (uyari == JOptionPane.YES_OPTION) {
            	 File file = new File(fav.getSelectedValue().toString());
                 try {
                	 acilandosya = file.getAbsolutePath();
                	 
                	 if (!file.getName().endsWith(".zedf")) ac(file,textRegion,label,listele,rec,l1);   
                	 if (file.getName().endsWith(".zedf")) zac(textRegion,label,rec,listele,l1);           
                	 l1.add(0, acilandosya);
                     //l1.addElement(acilandosya);
                     /*FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
                     BufferedWriter receb = new BufferedWriter(rece);
                     String st = "" , st1 = "";
                     for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
                     	st = listele.getModel().getElementAt(i).toString();
                     	st1 = st1 + "\n" + st;
                     }
                     receb.write(st1);
                     listele.repaint();
                     receb.flush();
                     receb.close();
                     label.setText("Dosya Açıldı " + acilandosya);
                     frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
                     kayitli = true;*/
                     
                 } catch (Exception ex) {
                     JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                     label.setText("Dosya Açılamadı");
                 }   
             }
             fav.setSelectedValue(null, false);
             if (uyari == JOptionPane.NO_OPTION) {
            	 fav.setSelectedValue(null, false);
             }
      		       		  
      	  } else {
      		File file = new File(fav.getSelectedValue().toString());
            try {
            	 acilandosya = file.getAbsolutePath();
            	
            	 if (!file.getName().endsWith(".zedf")) ac(file,textRegion,label,listele,rec,l1);   
            	 if (file.getName().endsWith(".zedf")) zac(textRegion,label,rec,listele,l1);  
            	 l1.add(0, acilandosya);
                //l1.addElement(acilandosya);
                /*FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
                BufferedWriter receb = new BufferedWriter(rece);
                String st = "" , st1 = "";
                for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
                	st = listele.getModel().getElementAt(i).toString();
                	st1 = st1 + "\n" + st;
                }
                receb.write(st1);
                listele.repaint();
                receb.flush();
                receb.close();
                label.setText("Dosya Açıldı " + acilandosya);
                frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
                kayitli = true;*/
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                label.setText("Dosya Açılamadı");
            }    
      	  }
       		}}});
        
        mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmOpen.setToolTipText("Varolan Bir Dosyayı Açın");
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (kayitli) {               	
                    dac(label,textRegion,listele,l1,rec);
                } else {
                    int onay = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Mevcut dosyayı kaydetmeden başka bir dosya açıyorsun emin misin?", "Uyari!",
                            JOptionPane.YES_NO_OPTION);
                    if (onay == JOptionPane.YES_OPTION) {
                    	dac(label,textRegion,listele,l1,rec);
                    }
                }
                
            }
        });
        favoriler.add(fav);
        
        JSeparator bcizgi = new JSeparator();
        mnbicim.add(bcizgi);
        JMenuItem bicimkaydet = new JMenuItem("Biçimi Kaydet");
        bicimkaydet.setToolTipText("Mevcut Biçimleri Varsayılan Olarak Kaydedin");
        bicimkaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    props.store(new FileOutputStream("kaynaklar/ayarlar.ead"), null);
                } catch (Exception g) {
            		JOptionPane.showMessageDialog(frmTextEditor, "Kritik Dosya Hatası!\n" + g);

                }

                JOptionPane.showMessageDialog(frmTextEditor, "Biçim ayarlarınız başarıyla kaydedildi");
            }
        });
        mnbicim.add(bicimkaydet);
        JMenuItem bicimsifir = new JMenuItem("Varsayılan biçim");
        bicimsifir.setToolTipText("Biçim Ayarlarını Varsayılana Çevirin");
        bicimsifir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    props.setProperty("Font", "Calibri");
                    props.setProperty("metinr", "java.awt.Color[r=0,g=0,b=0]");
                    props.setProperty("metins", "java.awt.Color[r=0,g=0,b=0]");
                    props.setProperty("metinb", "20");
                    props.setProperty("kalin", "0");
                    props.setProperty("arkaplan", "java.awt.Color[r=255,g=255,b=255]");
                    props.setProperty("tabsize", "2");
                    props.store(new FileOutputStream("kaynaklar/ayarlar.ead"), null);
                } catch (IOException g) {
            		JOptionPane.showMessageDialog(frmTextEditor, "Kritik Dosya Hatası!\n" + g);

                }

                JOptionPane.showMessageDialog(frmTextEditor,
                        "Biçim ayarlarınız başarıyla varsayılana çevirildi uygulamayı yeniden başlatarak aktif edebilirsiniz");
            }
        });
        mnbicim.add(bicimsifir);
        
        JMenu zamanekle = new JMenu("Zaman");
        mnekle.add(zamanekle);
        JMenuItem saat = new JMenuItem("Saat");
        zamanekle.add(saat);
        saat.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LocalTime zaman = LocalTime.now();
        		 DateTimeFormatter zamanano = DateTimeFormatter.ofPattern("HH:mm:ss");
        		 kayitli = false;  
        		    String naonuz = zaman.format(zamanano);
        		textRegion.insert("[" + naonuz + "]", textRegion.getCaretPosition());
        	}
        });
        JMenuItem tarih = new JMenuItem("Tarih");
        zamanekle.add(tarih);
        tarih.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LocalDate zaman = LocalDate.now();
        		kayitli = false;  
        		textRegion.insert("[" + LocalDate.now() + "]", textRegion.getCaretPosition());
        	}
        });
        JMenuItem tarihvesaat = new JMenuItem("Tarih Ve Saat");
        zamanekle.add(tarihvesaat);
        tarihvesaat.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LocalTime zaman = LocalTime.now();
        		 DateTimeFormatter zamanano = DateTimeFormatter.ofPattern("HH:mm:ss");
        		 kayitli = false;  
        		    String naonuz = zaman.format(zamanano);
        		textRegion.insert("[" + LocalDate.now() + " " + naonuz + "]", textRegion.getCaretPosition());
        	}
        });
        JMenuItem dosyadanekle = new JMenuItem("Dosyadan Aktar");
        dosyadanekle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  JFileChooser filechooser = new JFileChooser("f:");
                  filechooser.setAcceptAllFileFilterUsed(true);
                  filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
                  int temp = filechooser.showDialog(null, "Seç");                   

                  label.setText("");
                  if (temp == JFileChooser.APPROVE_OPTION) {
                  	File files;
                      acilandosya = filechooser.getSelectedFile().getAbsolutePath();                     
                      	 files = new File(filechooser.getSelectedFile().getAbsolutePath());
                      try {
                    	  String str = "", str1 = "";
                          FileReader fileread = new FileReader(files, StandardCharsets.UTF_8);
                          BufferedReader bufferrd = new BufferedReader(fileread);
                          str1 = bufferrd.readLine();
                          while ((str = bufferrd.readLine()) != null) {
                              str1 = str1 + "\n" + str;
                          }
                          textRegion.insert(str1, textRegion.getCaretPosition());
                          label.setText("Dosyadan Aktarıldı");
                          kayitli = false;               
                      } catch (Exception ex) {
                          JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                          label.setText("Dosyadan Aktarılamadı");

                      }	
        	}
        	}});
        mnekle.add(dosyadanekle);
        mntmSave.setToolTipText("Düzenlenen Dosyayı Farklı Şekilde Kaydedin");
        mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F ,ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK ));
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Dosya kaydediliyor");
                JFileChooser filechooser = new JFileChooser("f:");
                filechooser.setAcceptAllFileFilterUsed(false);
                filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
                filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Zenginleştirilmiş Editör Dosyaları (.zedf)", "zedf"));
                int temp = filechooser.showDialog(null, "Farklı Kaydet");                   

                label.setText("");
                if (temp == JFileChooser.APPROVE_OPTION) {
                	File file;
                    acilandosya = filechooser.getSelectedFile().getAbsolutePath();                                                         
                    if (filechooser.getFileFilter().getDescription() == "Zenginleştirilmiş Editör Dosyaları (.zedf)") {
                    	if (!acilandosya.endsWith(".zedf")) {
                          	 file = new File(filechooser.getSelectedFile().getAbsolutePath()+ ".zedf");
                          } else {
                          	 file = new File(filechooser.getSelectedFile().getAbsolutePath());
                          }
                    	try {
                    	acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                    	zkaydet(file,textRegion,label,props,listele,rec,l1);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        label.setText("Dosya kaydedilemedi");

                    }
                    }
                    if (filechooser.getFileFilter().getDescription() == "Editör Dosyaları (.edf)") {
                    	if (!acilandosya.endsWith(".edf")) {
                       	 file = new File(filechooser.getSelectedFile().getAbsolutePath()+ ".edf");
                       } else {
                       	 file = new File(filechooser.getSelectedFile().getAbsolutePath());
                       }
                    	try {
                            acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                           kaydet(file,textRegion,label,listele,rec,l1);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya kaydedilemedi");

                        }
                        }
                }
            }
        });
        mntmSavef.setToolTipText("Düzenlenen Dosyayı Kaydedin");
        mntmSavef.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmSavef.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File(acilandosya);
                if (acilandosya != "s") {
                    if (file.exists()) {
                        try {
                        	if (!file.getName().endsWith(".zedf")) kaydet(file,textRegion,label,listele,rec,l1);
                        	if (file.getName().endsWith(".zedf")) zkaydet(file,textRegion,label,props,listele,rec,l1);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamadı lütfen farklı kaydediniz");
                    }
                } else {
                    label.setText("Dosya kaydediliyor");
                    JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamadığı için farklı kaydediliyor");
                    JFileChooser filechooser = new JFileChooser("f:");
                    filechooser.setAcceptAllFileFilterUsed(false);
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Zenginleştirilmiş Editör Dosyaları (.zedf)", "zedf"));
                    int temp = filechooser.showDialog(null, "Kaydet");                   

                    label.setText("");
                    if (temp == JFileChooser.APPROVE_OPTION) {
                    	File files;
                        acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        if (filechooser.getFileFilter().getDescription() == "Zenginleştirilmiş Editör Dosyaları (.zedf)") {
                        	if (!acilandosya.endsWith(".zedf")) {
                              	 file = new File(filechooser.getSelectedFile().getAbsolutePath()+ ".zedf");
                              } else {
                              	 file = new File(filechooser.getSelectedFile().getAbsolutePath());
                              }
                        	try {
                        	acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        	zkaydet(file,textRegion,label,props,listele,rec,l1);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya kaydedilemedi");

                        }
                        }
                        if (filechooser.getFileFilter().getDescription() == "Editör Dosyaları (.edf)") {
                        	if (!acilandosya.endsWith(".edf")) {
                           	 file = new File(filechooser.getSelectedFile().getAbsolutePath()+ ".edf");
                           } else {
                           	 file = new File(filechooser.getSelectedFile().getAbsolutePath());
                           }
                        	try {
                                acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                               kaydet(file,textRegion,label,listele,rec,l1);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                                label.setText("Dosya kaydedilemedi");

                            }
                            }
                    }
                }
            }
        });
        
       
        mntmExit.setToolTipText("Programdan Çıkış Yapın");
        mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (kayitli) {
                    System.exit(0);
                } else {
                    int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Dosyayı kaydetmeden çıkıyorsun emin misin?", "Uyarı!", JOptionPane.YES_NO_OPTION);
                    if (uyari == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }

            }
        });
        frmTextEditor.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (kayitli) {
                    System.exit(0);
                } else {
                    int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Dosyayı kaydetmeden çıkıyorsun emin misin?", "Uyarı!", JOptionPane.YES_NO_OPTION);
                    if (uyari == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    
                }
            }
        });
        
        JMenuItem mntmCut = new JMenuItem("Kes");
        mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.cut();
                label.setText("Seçili Metin Kesildi");

            }
        });
        mnEdit.add(mntmCut);

        JMenuItem mntmCopy = new JMenuItem("Kopyala");
        mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.copy();
                label.setText("Seçili Metin Kopyalandı");

            }
        });
        mnEdit.add(mntmCopy);

        JMenuItem mntmExi = new JMenuItem("Yapıştır");
        mntmExi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmExi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.paste();
                label.setText("Seçili Metin Yapıştırıldı");
            }
        });
        mnEdit.add(mntmExi);
        JSeparator yapistircizgi = new JSeparator();
        mnEdit.add(yapistircizgi);
        JMenuItem mntmUndo = new JMenuItem("Geri al");
        mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undo.canUndo()) {
                    undo.undo();
                    label.setText("Geri Alındı");
                } else {
                    label.setText("Geri Alınamadı");
                }

            }
        });
        mnEdit.add(mntmUndo);
        
        JMenuItem mntmRedo = new JMenuItem("Yinele");
      mntmRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        mntmRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undo.canRedo()) {
                    label.setText("Yinelendi");
                    undo.redo();
                } else {
                    label.setText("Yinelenemedi");
                }

            }
        });
        mnEdit.add(mntmRedo);
        JScrollPane scrollabletextRegion = new JScrollPane(textRegion);
        scrollabletextRegion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollabletextRegion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frmTextEditor.getContentPane().add(scrollabletextRegion);
        frmTextEditor.getContentPane().add(label, BorderLayout.PAGE_END);
        

    }
public void ac(File file,JTextArea textRegion,JLabel label,JList listele,File rec,DefaultListModel<String> l1) throws IOException {
    
         String str = "", str1 = "";
         FileReader fileread = new FileReader(file,StandardCharsets.UTF_8);
         BufferedReader bufferrd = new BufferedReader(fileread);
         textRegion.setText("");
         str1 = bufferrd.readLine();
         
         while ((str = bufferrd.readLine()) != null) {
             str1 = str1 + "\n" + str;
         } 
         if(l1.contains(acilandosya)) l1.removeElement(acilandosya); listele.repaint();
         FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
         BufferedWriter receb = new BufferedWriter(rece);
         String st = "" , st1 = "";
         for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
         	st = listele.getModel().getElementAt(i).toString();
         	st1 = st1 + "\n" + st;
         }
         receb.write(st1);
         listele.repaint();
         receb.flush();
         receb.close();    
         textRegion.setText(str1);
         acilandosya = file.getAbsoluteFile().getAbsolutePath();
         label.setText("Dosya Açıldı " + acilandosya);
         frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
         kayitli = true;                   
    
}    public void kaydet(File file,JTextArea textRegion,JLabel label,JList listele,File rec,DefaultListModel<String> l1) throws IOException {
	
     FileWriter filewriter = new FileWriter(file, StandardCharsets.UTF_8);
     BufferedWriter bufferwr = new BufferedWriter(filewriter);
     bufferwr.write(textRegion.getText());
     bufferwr.flush();
     bufferwr.close();
     if(l1.contains(acilandosya)) l1.removeElement(acilandosya); listele.repaint();
     FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
     BufferedWriter receb = new BufferedWriter(rece);
     String st = "" , st1 = "";
     for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
     	st = listele.getModel().getElementAt(i).toString();
     	st1 = st1 + "\n" + st;
     }
     receb.write(st1);
     listele.repaint();
     receb.flush();
     receb.close();  
     label.setText("Dosya kaydedildi " + file.getAbsolutePath());
     frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
     kayitli = true;     
	
} public void zkaydet(File file,JTextArea textRegion,JLabel label,Properties props,JList listele,File rec,DefaultListModel<String> l1) throws IOException {
	 
     File tempf = new File("./belge.edf");
     FileWriter filewriter = new FileWriter(tempf, StandardCharsets.UTF_8);
     BufferedWriter bufferwr = new BufferedWriter(filewriter);
     bufferwr.write(textRegion.getText());
     bufferwr.flush();
     bufferwr.close(); 
     FileOutputStream fos = new FileOutputStream(file);
     ZipOutputStream zos = new ZipOutputStream(fos);
     zos.putNextEntry(new ZipEntry(tempf.getName()));
     byte[] bytes = Files.readAllBytes(Paths.get(tempf.getName()));
     zos.write(bytes, 0, bytes.length);
     zos.closeEntry();                      
    FileOutputStream fo = new FileOutputStream("./bicim.ead");                        
     
         props.store(fo, null);
         fo.close();
                       
     
     File zif = new File("./bicim.ead");                                                                 
     zos.putNextEntry(new ZipEntry(zif.getName()));
     byte[] bytess = Files.readAllBytes(Paths.get(zif.getName()));
     zos.write(bytess, 0, bytess.length);
     zos.closeEntry();
     zos.close();
     zif.delete();
     tempf.delete();
     if(l1.contains(acilandosya)) l1.removeElement(acilandosya); listele.repaint();
     FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
     BufferedWriter receb = new BufferedWriter(rece);
     String st = "" , st1 = "";
     for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
     	st = listele.getModel().getElementAt(i).toString();
     	st1 = st1 + "\n" + st;
     }
     receb.write(st1);
     listele.repaint();
     receb.flush();
     receb.close();  
     label.setText("Dosya kaydedildi " + file.getAbsolutePath());
     frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
     kayitli = true;
	 
} public void zac(JTextArea textRegion,JLabel label,File rec,JList listele,DefaultListModel<String> l1) throws IOException {
	ZipFile zf = new ZipFile(acilandosya);
	
	  InputStream in = zf.getInputStream(zf.getEntry("bicim.ead"));
	  Properties tempprops = new Properties();
		tempprops.load(in); 
		int boyut = Integer.parseInt(tempprops.getProperty("metinb"));
		int kalin = Integer.parseInt(tempprops.getProperty("kalin"));
		Font font = new Font(tempprops.getProperty("Font"), kalin, boyut);
		textRegion.setFont(font); 
		String barkplan = tempprops.getProperty("arkaplan");
        Scanner arkplans = new Scanner(barkplan);
        arkplans.useDelimiter("\\D+");
        Color arkplan = new Color(arkplans.nextInt(), arkplans.nextInt(), arkplans.nextInt());
        textRegion.setBackground(arkplan);
        String barkplans = tempprops.getProperty("metinr");
        Scanner arkplanss = new Scanner(barkplans);
        arkplanss.useDelimiter("\\D+");
        Color arkplansa = new Color(arkplanss.nextInt(), arkplanss.nextInt(), arkplanss.nextInt());
        textRegion.setForeground(arkplansa);
        String barkplanss = tempprops.getProperty("metins");
        Scanner arkplansss = new Scanner(barkplan);
        arkplansss.useDelimiter("\\D+");
        Color arkplansas = new Color(arkplansss.nextInt(), arkplansss.nextInt(), arkplansss.nextInt());
        textRegion.setSelectedTextColor(arkplansas);
	  InputStream on = zf.getInputStream(zf.getEntry("belge.edf"));                 			
		String str = "", str1 = "";                      
        BufferedReader bufferrd = new BufferedReader(new InputStreamReader(on,StandardCharsets.UTF_8));
        textRegion.setText("");
        str1 = bufferrd.readLine();
        
        while ((str = bufferrd.readLine()) != null) {
            str1 = str1 + "\n" + str;
        }      
        if(l1.contains(acilandosya)) l1.removeElement(acilandosya); listele.repaint();
        FileWriter rece = new FileWriter(rec, StandardCharsets.UTF_8);
        BufferedWriter receb = new BufferedWriter(rece);
        String st = "" , st1 = "";
        for (int i = 0; i < listele.getModel().getSize(); i++) {                                	
        	st = listele.getModel().getElementAt(i).toString();
        	st1 = st1 + "\n" + st;
        }
        receb.write(st1);
        listele.repaint();
        receb.flush();
        receb.close();
        textRegion.setText(str1);
        zf.close();
        label.setText("Dosya Açıldı " + acilandosya);
        frmTextEditor.setTitle("Editör " + surum + " - " + acilandosya);
        kayitli = true;
} public void dac(JLabel label,JTextArea textRegion,JList listele,DefaultListModel<String> l1,File rec) {
	label.setText("Dosya Aciliyor");
    JFileChooser filechooser = new JFileChooser("f:");
    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Zenginleştirilmiş Editör Dosyaları (.zedf)", "zedf"));
    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Metin Dosyaları (.txt)", "txt"));

    int temp = filechooser.showDialog(null, "Aç");                   
    label.setText("");
    if (temp == JFileChooser.APPROVE_OPTION) {
        File file = new File(filechooser.getSelectedFile().getAbsolutePath());
        acilandosya = file.getAbsolutePath();          
        if (filechooser.getFileFilter().getDescription() != "Zenginleştirilmiş Editör Dosyaları (.zedf)") {
        try {
        	
			ac(file,textRegion,label,listele,rec,l1);
			l1.add(0, acilandosya);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frmTextEditor, e1.getMessage());
            label.setText("Dosya Açılamadı");
		}	      
        } if (filechooser.getFileFilter().getDescription() == "Zenginleştirilmiş Editör Dosyaları (.zedf)") {                    	
        try {
        	acilandosya = file.getAbsoluteFile().getAbsolutePath();
        	
        	zac(textRegion,label,rec,listele,l1);
        	l1.add(0, acilandosya);
                //l1.addElement(acilandosya);
                label.setText("Dosya Açıldı " + acilandosya);
                frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
                kayitli = true;
    		
        	                       		
        		 
        	                         	
        	
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
            label.setText("Dosya Açılamadı");
        }
        }
    }
}
}
//
