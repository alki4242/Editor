package alki4242;

import java.lang.Integer;
import java.lang.String;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.FileOutputStream;

public class text_editor {
    String surum = "V1.0.86";
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
        File f = new File("./ayarlar.ead");
        Properties props = new Properties();
        if (f.exists()) {
            try {
                FileReader reader = new FileReader(f);
                props.load(reader);
            } catch (Exception e) {

            }
        } else {
            try {
                props.setProperty("Font", "Helvetica Neue");
                props.setProperty("metinr", "java.awt.Color[r=0,g=0,b=0]");
                props.setProperty("metins", "java.awt.Color[r=0,g=0,b=0]");
                props.setProperty("metinb", "20");
                props.setProperty("kalin", "0");
                props.setProperty("arkaplan", "java.awt.Color[r=255,g=255,b=255]");
                props.setProperty("tabsize", "2");
                props.store(new FileOutputStream("./ayarlar.ead"), null);
                FileReader reader = new FileReader(f);
                props.load(reader);
            } catch (IOException g) {

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
        frmTextEditor.setTitle("Editor " + surum);
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
        textRegion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String kelimeler = textRegion.getText();
                String kelimeayri[] = kelimeler.split("\\s");
                int karakterler = kelimeler.length();
                label.setText("Karakter Say�s�: " + karakterler + " Kelime Say�s�: " + kelimeayri.length);
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
        JMenu mnbul = new JMenu("Bul");
        menuBar.add(mnbul);
        JMenu mnEdit = new JMenu("D�zenle");
        menuBar.add(mnEdit);
        JMenu mnbicim = new JMenu("Bi�im");
        menuBar.add(mnbicim);
        JMenu mninfo = new JMenu("Hakk�nda");
        menuBar.add(mninfo);
        JLabel boyuty = new JLabel();
        boyuty.setText("Metin Boyutu");
        mnbicim.add(boyuty);
        JSlider boyut = new JSlider(JSlider.HORIZONTAL,
                20, 50, bmb);
        boyut.setName("Metin Boyutu");
        boyut.setPaintTicks(true);
        boyut.setPaintLabels(true);
        boyut.setMajorTickSpacing(2);
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fontlar = new JComboBox(fonts);
        fontlar.setEditable(true);
        fontlar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("Font", fontlar.getSelectedItem().toString());
            }
        });
        boyut.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
                int k = boyut.getValue();
                String ks = String.valueOf(k);
                props.setProperty("metinb", ks);
            }
        });
        mnbicim.add(boyut);
        JLabel taby = new JLabel();
        taby.setText("Tab Boyutu");
        mnbicim.add(taby);
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
        mnbicim.add(fontlar);
        JButton ince = new JButton();
        ince.setText("Italik");
        ince.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.ITALIC, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "2");

            }
        });
        JButton kalin = new JButton();
        kalin.setText("Kal�n");
        JMenuItem selecall = new JMenuItem("Hepsini Se�");
        selecall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));        
        selecall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.selectAll();
            }
        });
        mnEdit.add(selecall);
        kalin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.BOLD, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "1");

            }
        });
        JButton cizili = new JButton();
        cizili.setText("S�f�rla");
        cizili.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "0");

            }
        });
        JLabel duzey = new JLabel();
        duzey.setText("Yaz� T�r�");
        mnbicim.add(duzey);
        mnbicim.add(ince);
        mnbicim.add(kalin);
        mnbicim.add(cizili);
        JMenuItem renksec = new JMenuItem("Se�ili metin rengi");
        mnbicim.add(renksec);
        renksec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renkmetin = new JColorChooser();
                Color c = JColorChooser.showDialog(renkmetin, "Renk Men�s�", Color.black);
                textRegion.setSelectedTextColor(c);
                props.setProperty("metins", c.toString());

            }
        });
        JMenuItem metinyazi = new JMenuItem("Metin Rengi");
        metinyazi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renk = new JColorChooser();
                Color c = JColorChooser.showDialog(renk, "Renk Men�s�", Color.black);
                textRegion.setForeground(c);
                props.setProperty("metinr", c.toString());
            }
        });
        mnbicim.add(metinyazi);
        JMenuItem renksecarkaplan = new JMenuItem("Arkaplan rengi");
        mnbicim.add(renksecarkaplan);
        renksecarkaplan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renk = new JColorChooser();
                Color c = JColorChooser.showDialog(renk, "Renk Men�s�", Color.white);
                textRegion.setBackground(c);
                props.setProperty("arkaplan", c.toString());
            }
        });
        JLabel bulbilgi = new JLabel();
        bulbilgi.setText("Bul |\n Aranacak metini yaz�n");
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
                        label.setText("Arat�ld�");
                    } else {
                        JOptionPane.showMessageDialog(frmTextEditor, "Arat�lan de�er bulunamad�");
                    }
                } else {
                    JOptionPane.showMessageDialog(frmTextEditor, "L�tfen de�er giriniz!");
                }
            }
        });
        JLabel ayirbulrep = new JLabel();
        mnbul.add(ayirbulrep);
        JLabel repbilgi = new JLabel();
        repbilgi.setText("Bul ve De�i�tir|\n Yeni metini yaz�n");
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
                        label.setText("De�i�tirildi");
                    } else {
                        JOptionPane.showMessageDialog(frmTextEditor, "Arat�lan de�er bulunamad�");
                    }
                } else {
                    JOptionPane.showMessageDialog(frmTextEditor, "L�tfen de�i�tirilecek de�eri giriniz!");
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
        JMenuItem mntmNew = new JMenuItem("Yeni Dosya");
        mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (kayitli) {
                    textRegion.setText("");
                    acilandosya = "s";
                    label.setText("Yeni Dosya a��ld�");
                    kayitli = true;
                } else {
                    int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Dosyay� kaydetmeden yeni dosya olu�turuyorsun emin misin?", "Uyar�!",
                            JOptionPane.YES_NO_OPTION);
                    if (uyari == JOptionPane.YES_OPTION) {
                        textRegion.setText("");
                        acilandosya = "s";
                        label.setText("Yeni Dosya a��ld�");
                    }
                }

            }
        });
        mnFile.add(mntmNew);
        JMenuItem mninfoin = new JMenuItem("Hakk�nda");
        mninfoin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frmTextEditor, "Yap�mc�: alki4242 \n S�r�m: " + surum);
            }
        });
        mninfo.add(mninfoin);
        JMenuItem mntmSave = new JMenuItem("Farkl� Kaydet");
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Dosya kaydediliyor");
                JFileChooser filechooser = new JFileChooser("f:");
                filechooser.setAcceptAllFileFilterUsed(false);
                filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Edit�r Dosyalar� (.edf)", "edf"));
                int temp = filechooser.showSaveDialog(null);
                label.setText("");
                if (temp == JFileChooser.APPROVE_OPTION) {
                    File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                    try {
                        acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        FileWriter filewriter = new FileWriter(file, false);
                        BufferedWriter bufferwr = new BufferedWriter(filewriter);
                        bufferwr.write(textRegion.getText());
                        bufferwr.flush();
                        bufferwr.close();
                        label.setText("Dosya kaydedildi " + file.getAbsolutePath());
                        frmTextEditor.setTitle("Editor " + surum + " - " + file.getName());
                        kayitli = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        label.setText("Dosya kaydedilemedi");

                    }
                }
            }
        });
        mnFile.add(mntmSave);

        JMenuItem mntmOpen = new JMenuItem("A�");
        mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (kayitli) {
                    label.setText("Dosya Aciliyor");
                    JFileChooser filechooser = new JFileChooser("f:");
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Edit�r Dosyalar� (.edf)", "edf"));
                    int temp = filechooser.showOpenDialog(null);
                    label.setText("");
                    if (temp == JFileChooser.APPROVE_OPTION) {
                        File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                        try {
                            String str = "", str1 = "";
                            FileReader fileread = new FileReader(file);
                            BufferedReader bufferrd = new BufferedReader(fileread);
                            str1 = bufferrd.readLine();
                            while ((str = bufferrd.readLine()) != null) {
                                str1 = str1 + "\n" + str;
                            }
                            textRegion.setText(str1);
                            acilandosya = file.getAbsoluteFile().getAbsolutePath();
                            label.setText("Dosya A��ld� " + acilandosya);
                            frmTextEditor.setTitle("Editor " + surum + " - " + file.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya A��lamad�");
                        }
                    }
                } else {
                    int onay = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Mevcut dosyay� kaydetmeden ba�ka bir dosya a��yorsun emin misin?", "Uyari!",
                            JOptionPane.YES_NO_OPTION);
                    if (onay == JOptionPane.YES_OPTION) {
                        label.setText("Dosya Aciliyor");
                        JFileChooser filechooser = new JFileChooser("f:");
                        filechooser
                                .addChoosableFileFilter(new FileNameExtensionFilter("Edit�r Dosyalar� (.edf)", "edf"));
                        int temp = filechooser.showOpenDialog(null);
                        label.setText("");
                        if (temp == JFileChooser.APPROVE_OPTION) {
                            File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                            try {
                                String str = "", str1 = "";
                                FileReader fileread = new FileReader(file);
                                BufferedReader bufferrd = new BufferedReader(fileread);
                                str1 = bufferrd.readLine();
                                while ((str = bufferrd.readLine()) != null) {
                                    str1 = str1 + "\n" + str;
                                }
                                textRegion.setText(str1);
                                acilandosya = file.getAbsoluteFile().getAbsolutePath();
                                label.setText("Dosya A��ld�");
                                frmTextEditor.setTitle("Editor " + surum + " - " + file.getName());
                                kayitli = true;
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                                label.setText("Dosya A��lamad�");
                            }
                        }
                    }
                }
            }
        });
        mnFile.add(mntmOpen);
        JMenuItem bicimkaydet = new JMenuItem("Bi�imi Kaydet");
        bicimkaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    props.store(new FileOutputStream("./ayarlar.ead"), null);
                } catch (IOException g) {

                }

                JOptionPane.showMessageDialog(frmTextEditor, "Bi�im ayarlar�n�z ba�ar�yla kaydedildi");
            }
        });
        mnbicim.add(bicimkaydet);
        JMenuItem bicimsifir = new JMenuItem("Varsay�lan bi�im");
        bicimsifir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    props.setProperty("Font", "Helvetica Neue");
                    props.setProperty("metinr", "java.awt.Color[r=0,g=0,b=0]");
                    props.setProperty("metins", "java.awt.Color[r=0,g=0,b=0]");
                    props.setProperty("metinb", "20");
                    props.setProperty("kalin", "0");
                    props.setProperty("arkaplan", "java.awt.Color[r=255,g=255,b=255]");
                    props.setProperty("tabsize", "2");
                    props.store(new FileOutputStream("./ayarlar.ead"), null);
                } catch (IOException g) {

                }

                JOptionPane.showMessageDialog(frmTextEditor,
                        "Bi�im ayarlar�n�z ba�ar�yla varsay�lana �evirildi uygulamay� yeniden ba�latarak aktif edebilirsiniz");
            }
        });
        mnbicim.add(bicimsifir);
        JMenuItem mntmSavef = new JMenuItem("Kaydet");
        mntmSavef.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmSavef.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File(acilandosya);
                if (acilandosya != "s") {
                    if (file.exists()) {
                        try {
                            FileWriter filewriter = new FileWriter(file);
                            BufferedWriter bufferwr = new BufferedWriter(filewriter);
                            bufferwr.write(textRegion.getText());
                            bufferwr.flush();
                            bufferwr.close();
                            label.setText("Dosya Kaydedildi " + file.getAbsolutePath());
                            kayitli = true;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamad� l�tfen farkl� kaydediniz");
                    }
                } else {
                    label.setText("Dosya kaydediliyor");
                    JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamad��� i�in farkl� kaydediliyor");
                    JFileChooser filechooser = new JFileChooser("f:");
                    filechooser.setAcceptAllFileFilterUsed(false);
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Edit�r Dosyalar� (.edf)", ".edf"));
                    int temp = filechooser.showSaveDialog(null);
                    label.setText("");
                    if (temp == JFileChooser.APPROVE_OPTION) {
                        acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        File files = new File(filechooser.getSelectedFile().getAbsolutePath());
                        try {
                            FileWriter filewriter = new FileWriter(files, false);
                            BufferedWriter bufferwr = new BufferedWriter(filewriter);
                            bufferwr.write(textRegion.getText());
                            bufferwr.flush();
                            bufferwr.close();
                            label.setText("Dosya kaydedildi");
                            kayitli = true;
                            frmTextEditor.setTitle("Editor " + surum + " - " + files.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya kaydedilemedi");

                        }
                    }
                }
            }
        });
        mnFile.add(mntmSavef);
        JMenuItem mntmExit = new JMenuItem("��k��");
        mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (kayitli) {
                    System.exit(0);
                } else {
                    int uyari = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Dosyay� kaydetmeden ��k�yorsun emin misin?", "Uyar�!", JOptionPane.YES_NO_OPTION);
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
                            "Dikkat! Dosyay� kaydetmeden ��k�yorsun emin misin?", "Uyar�!", JOptionPane.YES_NO_OPTION);
                    if (uyari == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    if (uyari == JOptionPane.NO_OPTION) {

                    }
                }
            }
        });
        mnFile.add(mntmExit);
        JMenuItem mntmCut = new JMenuItem("Kes");
        mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.cut();
                label.setText("Se�ili Metin Kesildi");

            }
        });
        mnEdit.add(mntmCut);

        JMenuItem mntmCopy = new JMenuItem("Kopyala");
        mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.copy();
                label.setText("Se�ili Metin Kopyalandi");

            }
        });
        mnEdit.add(mntmCopy);

        JMenuItem mntmExi = new JMenuItem("Yap��t�r");
        mntmExi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmExi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.paste();
                label.setText("Se�ili Metin Yapistirildi");
            }
        });
        mnEdit.add(mntmExi);

        JMenuItem mntmUndo = new JMenuItem("Geri al");
        mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undo.canUndo()) {
                    undo.undo();
                    label.setText("Geri Al�nd�");
                } else {
                    label.setText("Geri Al�namad�");
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
    
}
