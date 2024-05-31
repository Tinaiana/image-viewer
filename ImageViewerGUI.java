import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.RescaleOp;

public class ImageViewerGUI extends JFrame implements ActionListener {
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;

    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = "/home/...";
    File file;
    BufferedImage originalImage;


    ImageViewerGUI() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        selectFileButton = new JButton("Choose Image");
        selectFileButton.addActionListener(this);
        showImageButton = new JButton("Show Image");
        showImageButton.addActionListener(this);
        brightnessButton = new JButton("Adjust Brightness");
        brightnessButton.addActionListener(this);
        grayscaleButton = new JButton("Grayscale Image");
        grayscaleButton.addActionListener(this);
        resizeButton = new JButton("Resize Image");
        resizeButton.addActionListener(this);
        closeButton = new JButton("exit");
        closeButton.addActionListener(this);

        mainPanel.add(selectFileButton);
        mainPanel.add(showImageButton);
        mainPanel.add(brightnessButton);
        mainPanel.add(grayscaleButton);
        mainPanel.add(resizeButton);
        mainPanel.add(closeButton);

        this.add(mainPanel);
    }

    public void chooseFileImage() {
        JFileChooser fileChooser = new JFileChooser(filePath);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showOriginalImage() {
        JPanel tempPanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(originalImage);
        JLabel imageLabel = new JLabel(imageIcon);
        tempPanel.add(imageLabel);

        JFrame tempFrame = new JFrame();
        tempFrame.add(tempPanel);
        tempFrame.setTitle("Original Image Viewer");
        tempFrame.setSize(originalImage.getWidth(), originalImage.getHeight());
        tempFrame.setVisible(true);
    }

    public void adjustBrightness() {

        JFrame panel = new JFrame();
        panel.setVisible(true);

        panel.setBounds(200,100,600,700);
        JButton result = new JButton("result");
        result.setBounds(190,50,100,100);
        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(300, 100, 200, 30);
        panel.add(brightnessTextField);

        JButton back = new JButton("Back");
        back.setBounds(20,50,100,100);
        panel.setLayout(null);
        panel.add(result);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);

            }
        });

        BufferedImage brightenedImage = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        float scaleFactor = brightnessTextField.getAlignmentX() ;
        Graphics2D graphics = brightenedImage.createGraphics();
        RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
        rescaleOp.filter(originalImage, brightenedImage);
        graphics.drawImage(brightenedImage, 0, 0, null);
        graphics.dispose();

        ImageIcon imageIcon = new ImageIcon(brightenedImage);
        JLabel imageLabel = new JLabel(imageIcon);
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame tempFrame = new JFrame();
                tempFrame.add(imageLabel);
                tempFrame.setTitle("Brightened Image Viewer");
                tempFrame.setSize(brightenedImage.getWidth(), brightenedImage.getHeight());
                tempFrame.setVisible(true);
            }
        });


    }


    public void convertToGrayscale() {
        BufferedImage grayImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        ImageIcon imageIcon = new ImageIcon(grayImage);
        JLabel imageLabel = new JLabel(imageIcon);

        JFrame tempFrame = new JFrame();
        tempFrame.add(imageLabel);
        tempFrame.setTitle("Grayscale Image Viewer");
        tempFrame.setSize(grayImage.getWidth(), grayImage.getHeight());
        tempFrame.setVisible(true);
    }

    public void resizeImage() {
        JFrame panel = new JFrame();
        panel.setVisible(true);

        panel.setBounds(200,100,600,700);
        JButton result = new JButton("result");
        result.setBounds(190,50,100,100);
        widthTextField = new JTextField();
        widthTextField.setBounds(200, 200, 200, 30);
        panel.add(widthTextField);
        heightTextField= new JTextField();
        heightTextField.setBounds(200, 300, 200, 30);
        panel.add(heightTextField);

        JButton back = new JButton("Back");
        back.setBounds(20,50,100,100);
        panel.setLayout(null);
        panel.add(result);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);

            }
        });

        Image scaledImage = originalImage.getScaledInstance(widthTextField.getWidth(), heightTextField.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(widthTextField.getWidth(), heightTextField.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(scaledImage, 0, 0, null);
        graphics2D.dispose();

        ImageIcon imageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(imageIcon);
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame tempFrame = new JFrame();

                tempFrame.add(imageLabel);
                tempFrame.setTitle("Resized Image Viewer");
                int t1 = widthTextField.getWidth();
                int t2 =heightTextField.getHeight();
                tempFrame.setSize( t1, t2);

                tempFrame.setLayout(null);
                tempFrame.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectFileButton) {
            chooseFileImage();
        } else if (e.getSource() == showImageButton) {
            showOriginalImage();
        } else if (e.getSource() == brightnessButton) {
            adjustBrightness();
        }else if (e.getSource() == grayscaleButton) {
            convertToGrayscale();
        } else if (e.getSource() == resizeButton) {
            resizeImage();
        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageViewerGUI());
    }
}