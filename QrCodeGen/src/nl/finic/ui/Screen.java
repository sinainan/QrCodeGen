package nl.finic.ui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Screen extends JFrame{

    private JPanel jpScreen;
    private JButton bGen;
    private JTextField tLink;
    private JButton bFile;
    private JTextField tFile;
    private String choosertitle;
    private String path;

    public Screen() {
        add(jpScreen);
        setSize(400, 400);
        setTitle("QrCodeGen - Finic");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        ImageIcon img = new ImageIcon("C:\\Users\\Sina Inan\\IdeaProjects\\QrCodeGen\\src\\nl\\finic\\qr-code.png");
        this.setIconImage(img.getImage());

        bGen.setBorder(BorderFactory.createEmptyBorder());
        bGen.setContentAreaFilled(false);
        bFile.setBorder(BorderFactory.createEmptyBorder());
        bFile.setContentAreaFilled(false);

        bGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createQr();
            }
        });

        bFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser;
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                //
                if (chooser.showOpenDialog(bFile) == JFileChooser.APPROVE_OPTION) {
                    path = chooser.getSelectedFile().toString();
                    System.out.println("getCurrentDirectory(): "
                            +  chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "
                            +  chooser.getSelectedFile());
                    JOptionPane.showMessageDialog(null,"File Location Has Been Selected To : " + path,"File Location",1);
                }
                else {
                    System.out.println("No Selection ");
                }


            }
        });
    }

    public void createQr(){
        try {
            String link = tLink.getText();
            String name = tFile.getText();

            String filePath =  path + "\\" +  name + ".png";
            String charset = "UTF-8"; // or "ISO-8859-1"

            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel >();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix matrix = new MultiFormatWriter().encode(new String(link.getBytes(charset), charset), BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));

            System.out.println("QR Code image created successfully!");
            System.out.println(filePath);
            System.out.println(name);

            JOptionPane.showMessageDialog(null,"Qr-Code has been created","Done",1);
            tLink.setText("");
            tFile.setText("");
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}

