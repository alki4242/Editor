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
    String surum = "V1.0.97";
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
        JMenu mnbul = new JMenu("Bul");
        menuBar.add(mnbul);
        JMenu mnEdit = new JMenu("Düzenle");
        menuBar.add(mnEdit);
        JMenu mnbicim = new JMenu("Biçim");
        menuBar.add(mnbicim);
        JMenu mninfo = new JMenu("Hakkında");
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
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.BOLD, boyut.getValue());
                textRegion.setFont(font);
                props.setProperty("kalin", "1");

            }
        });
        JButton cizili = new JButton();
        cizili.setText("Sıfırla");
        cizili.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
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
        JMenuItem mntmNew = new JMenuItem("Yeni Dosya");
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

                    }
                }

            }
        });
        mnFile.add(mntmNew);
        JMenuItem mninfoin = new JMenuItem("Hakkında");
        mninfoin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frmTextEditor, "Yapımcı: alki4242 \n Sürüm: " + surum);
            }
        });
        mninfo.add(mninfoin);
        JMenuItem mntmSave = new JMenuItem("Farklı Kaydet");
        mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F , Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Dosya kaydediliyor");
                JFileChooser filechooser = new JFileChooser("f:");
                filechooser.setAcceptAllFileFilterUsed(false);
                filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
                int temp = filechooser.showSaveDialog(null);
                label.setText("");
                if (temp == JFileChooser.APPROVE_OPTION) {
                	File file;
                    acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                    
                    if (!acilandosya.endsWith(".edf") && filechooser.getFileFilter().getDescription() == "Editör Dosyaları (.edf)") {
                    	 file = new File(filechooser.getSelectedFile().getAbsolutePath()+ ".edf");
                    } else {
                    	 file = new File(filechooser.getSelectedFile().getAbsolutePath());
                    }
                    try {
                        acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        FileWriter filewriter = new FileWriter(file, false);
                        BufferedWriter bufferwr = new BufferedWriter(filewriter);
                        bufferwr.write(textRegion.getText());
                        bufferwr.flush();
                        bufferwr.close();
                        label.setText("Dosya kaydedildi " + file.getAbsolutePath());
                        frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
                        kayitli = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        label.setText("Dosya kaydedilemedi");

                    }
                }
            }
        });
        mnFile.add(mntmSave);

        JMenuItem mntmOpen = new JMenuItem("Aç");
        mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (kayitli) {
                    label.setText("Dosya Aciliyor");
                    JFileChooser filechooser = new JFileChooser("f:");
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
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
                            label.setText("Dosya Açıldı " + acilandosya);
                            frmTextEditor.setTitle("Editör " + surum + " - " + file.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya Açılamadı");
                        }
                    }
                } else {
                    int onay = JOptionPane.showConfirmDialog(frmTextEditor,
                            "Dikkat! Mevcut dosyayı kaydetmeden başka bir dosya açıyorsun emin misin?", "Uyari!",
                            JOptionPane.YES_NO_OPTION);
                    if (onay == JOptionPane.YES_OPTION) {
                        label.setText("Dosya Aciliyor");
                        JFileChooser filechooser = new JFileChooser("f:");
                        filechooser
                                .addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
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
                                label.setText("Dosya Açıldı");
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
        });
        mnFile.add(mntmOpen);
        JSeparator bcizgi = new JSeparator();
        mnbicim.add(bcizgi);
        JMenuItem bicimkaydet = new JMenuItem("Biçimi Kaydet");
        bicimkaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    props.store(new FileOutputStream("./ayarlar.ead"), null);
                } catch (IOException g) {

                }

                JOptionPane.showMessageDialog(frmTextEditor, "Biçim ayarlarınız başarıyla kaydedildi");
            }
        });
        mnbicim.add(bicimkaydet);
        JMenuItem bicimsifir = new JMenuItem("Varsayılan biçim");
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
                        "Biçim ayarlarınız başarıyla varsayılana çevirildi uygulamayı yeniden başlatarak aktif edebilirsiniz");
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
                        JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamadı lütfen farklı kaydediniz");
                    }
                } else {
                    label.setText("Dosya kaydediliyor");
                    JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamadığı için farklı kaydediliyor");
                    JFileChooser filechooser = new JFileChooser("f:");
                    filechooser.setAcceptAllFileFilterUsed(false);
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editör Dosyaları (.edf)", "edf"));
                    int temp = filechooser.showSaveDialog(null);
                    label.setText("");
                    if (temp == JFileChooser.APPROVE_OPTION) {
                    	File files;
                        acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        if (!acilandosya.endsWith(".edf") && filechooser.getFileFilter().getDescription() == "Editör Dosyaları (.edf)") {
                        	 files = new File(filechooser.getSelectedFile().getAbsolutePath()+ ".edf");
                        } else {
                        	 files = new File(filechooser.getSelectedFile().getAbsolutePath());
                        }
                        try {
                            FileWriter filewriter = new FileWriter(files, false);
                            BufferedWriter bufferwr = new BufferedWriter(filewriter);
                            bufferwr.write(textRegion.getText());
                            bufferwr.flush();
                            bufferwr.close();
                            label.setText("Dosya kaydedildi");
                            kayitli = true;
                            frmTextEditor.setTitle("Editör " + surum + " - " + files.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya kaydedilemedi");

                        }
                    }
                }
            }
        });
        mnFile.add(mntmSavef);
        JSeparator ccizgi = new JSeparator();
        mnFile.add(ccizgi);
        JMenuItem mntmExit = new JMenuItem("Çıkış");
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
    
}
//
