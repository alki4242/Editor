package alki4242;

import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.GraphicsEnvironment;

public class text_editor {
    String surum = "V1.0.31";
    String acilandosya = "s";
    private JFrame frmTextEditor;

    public text_editor() {
        initalize();
    }

    public static void main(String[] args) {
        text_editor window = new text_editor();
        window.frmTextEditor.setVisible(true);
    }

    public void initalize() {
        frmTextEditor = new JFrame();
        frmTextEditor.setIconImage(null);
        frmTextEditor.setTitle("Editor " + surum);
        frmTextEditor.setSize(500, 550);
        frmTextEditor.setBounds(100, 200, 550, 630);
        frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTextEditor.getContentPane().setLayout(new BorderLayout(0, 0));
        JTextArea textRegion = new JTextArea();
        frmTextEditor.getContentPane().add(textRegion, BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        textRegion.setTabSize(3);
        JLabel label = new JLabel();
        frmTextEditor.setJMenuBar(menuBar);
        String text = textRegion.getText();
        UndoManager undo = new UndoManager();
        textRegion.getDocument().addUndoableEditListener(undo);
        textRegion.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        JMenu mnFile = new JMenu("Dosya");
        menuBar.add(mnFile);
        JMenu mnEdit = new JMenu("Duzenle");
        menuBar.add(mnEdit);
        JMenu mnbicim = new JMenu("Bicim");
        menuBar.add(mnbicim);
        JMenu mninfo = new JMenu("Hakkinda");
        menuBar.add(mninfo);
        JLabel boyuty = new JLabel();
        boyuty.setText("Metin Boyutu");
        mnbicim.add(boyuty);
        JSlider boyut = new JSlider(JSlider.HORIZONTAL,
                20, 50, 20);
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
            }
        });
        boyut.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
            }
        });
        mnbicim.add(boyut);
        JLabel taby = new JLabel();
        taby.setText("Tab Boyutu");
        mnbicim.add(taby);
        JSlider tab = new JSlider(JSlider.HORIZONTAL, 2, 8, 3);
        tab.setPaintTicks(true);
        tab.setPaintLabels(true);
        tab.setMajorTickSpacing(1);
        mnbicim.add(tab);
        tab.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                textRegion.setTabSize(tab.getValue());
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
            }
        });
        JButton kalin = new JButton();
        kalin.setText("Kalin");
        JMenuItem selecall = new JMenuItem("Hepsini sec");
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
            }
        });
        JButton cizili = new JButton();
        cizili.setText("Sifirla");
        cizili.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFamilyName = fontlar.getSelectedItem().toString();
                Font font = new Font(selectedFamilyName, Font.PLAIN, boyut.getValue());
                textRegion.setFont(font);
            }
        });
        JLabel duzey = new JLabel();
        duzey.setText("Yazi Turu");
        mnbicim.add(duzey);
        mnbicim.add(ince);
        mnbicim.add(kalin);
        mnbicim.add(cizili);
        JMenuItem renksec = new JMenuItem("Secili metin rengi");
        mnbicim.add(renksec);
        renksec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renkmetin = new JColorChooser();
                Color c = JColorChooser.showDialog(renkmetin, "Renk Menusu", Color.black);
                textRegion.setSelectedTextColor(c);

            }
        });
        JMenuItem renksecarkaplan = new JMenuItem("Arkaplan rengi");
        mnbicim.add(renksecarkaplan);
        renksecarkaplan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JColorChooser renk = new JColorChooser();
                Color c = JColorChooser.showDialog(renk, "Renk Menusu", Color.black);
                textRegion.setBackground(c);
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
        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textRegion.setText("");
                acilandosya = "s";
                label.setText("Yeni Dosya acildi");
            }
        });
        mnFile.add(mntmNew);
        JMenuItem mninfoin = new JMenuItem("Hakkinda");
        mninfoin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frmTextEditor, "Yapimci: alki4242 \n Surum: " + surum);
            }
        });
        mninfo.add(mninfoin);
        JMenuItem mntmSave = new JMenuItem("Farkli Kaydet");
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Dosya kaydediliyor");
                JFileChooser filechooser = new JFileChooser("f:");
                filechooser.setAcceptAllFileFilterUsed(false);
                filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editor Dosyalari (.edf)", "edf"));
                int temp = filechooser.showSaveDialog(null);
                label.setText("");
                if (temp == JFileChooser.APPROVE_OPTION) {
                    File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                    try {
                        FileWriter filewriter = new FileWriter(file, false);
                        BufferedWriter bufferwr = new BufferedWriter(filewriter);
                        bufferwr.write(textRegion.getText());
                        bufferwr.flush();
                        bufferwr.close();
                        label.setText("Dosya kaydedildi");
                        frmTextEditor.setTitle("Editor " + surum + " - " + file.getName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        label.setText("Dosya kaydedilemedi");

                    }
                }
            }
        });
        mnFile.add(mntmSave);

        JMenuItem mntmOpen = new JMenuItem("Ac");
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Dosya Aciliyor");
                JFileChooser filechooser = new JFileChooser("f:");
                filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editor Dosyalari (.edf)", "edf"));
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
                        label.setText("Dosya Acildi");
                        frmTextEditor.setTitle("Editor " + surum + " - " + file.getName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                        label.setText("Dosya Acilamadı");
                    }
                }
            }

        });
        mnFile.add(mntmOpen);
        JMenuItem mntmSavef = new JMenuItem("Kaydet");

        mntmSavef.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File(acilandosya);
                if (acilandosya != "s") {
                    try {
                        FileWriter filewriter = new FileWriter(file);
                        BufferedWriter bufferwr = new BufferedWriter(filewriter);
                        bufferwr.write(textRegion.getText());
                        bufferwr.flush();
                        bufferwr.close();
                        label.setText("Dosya Kaydedildi");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                    }
                } else {
                    label.setText("Dosya kaydediliyor");
                    JOptionPane.showMessageDialog(frmTextEditor, "Dosya Bulunamadigi icin farkli kaydediliyor");
                    JFileChooser filechooser = new JFileChooser("f:");
                    filechooser.setAcceptAllFileFilterUsed(false);
                    filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Editor Dosyalari (.edf)", ".edf"));
                    int temp = filechooser.showSaveDialog(null);
                    label.setText("");
                    if (temp == JFileChooser.APPROVE_OPTION) {
                        acilandosya = filechooser.getSelectedFile().getAbsolutePath();
                        File files = new File(filechooser.getSelectedFile().getAbsolutePath());
                        try {
                            FileWriter filewriter = new FileWriter(file, false);
                            BufferedWriter bufferwr = new BufferedWriter(filewriter);
                            bufferwr.write(textRegion.getText());
                            bufferwr.flush();
                            bufferwr.close();
                            label.setText("Dosya kaydedildi");
                            frmTextEditor.setTitle("Editor " + surum + " - " + file.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                            label.setText("Dosya kaydedilemedi");

                        }
                    }
                }
            }
        });
        mnFile.add(mntmSavef);
        JMenuItem mntmExit = new JMenuItem("Cikis");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmTextEditor.dispatchEvent(new WindowEvent(frmTextEditor, WindowEvent.WINDOW_CLOSING));
            }
        });
        mnFile.add(mntmExit);
        JMenuItem mntmCut = new JMenuItem("Kes");
        mntmCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.cut();
                label.setText("Secili Metin Kesildi");

            }
        });
        mnEdit.add(mntmCut);

        JMenuItem mntmCopy = new JMenuItem("Kopyala");
        mntmCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.copy();
                label.setText("Secili Metin Kopyalandi");

            }
        });
        mnEdit.add(mntmCopy);

        JMenuItem mntmExi = new JMenuItem("Yapistir");
        mntmExi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.paste();
                label.setText("Secili Metin Yapistirildi");
            }
        });
        mnEdit.add(mntmExi);

        JMenuItem mntmUndo = new JMenuItem("Geri al");
        mntmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undo.canUndo()) {
                    undo.undo();
                    label.setText("Geri Alindi");
                } else {
                    label.setText("Geri Alinamadi");
                }

            }
        });
        mnEdit.add(mntmUndo);

        JMenuItem mntmRedo = new JMenuItem("Yinele");
        mntmRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undo.canRedo()) {
                    label.setText("Yinelendi");
                    undo.redo();
                } else {
                    label.setText("Yenilenemedi");
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
