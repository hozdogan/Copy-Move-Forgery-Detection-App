/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.print.DocFlavor;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Point;
import org.opencv.core.Rect;
public class Goruntu extends javax.swing.JFrame {

    class StackPoint
{
    private double x,y;
    public StackPoint(double a,double b)
    {
        x=a;
        y=b;
       
    }
    public StackPoint(){}
    
    public void setX(double val)
    {
        x=val;
    }
    public void setY(double valy)
    {
        y=valy;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
 }

    
    class İmageManipulation
    {
          private int width,heigth;
          public BufferedImage image;
          public String opencvpath="C:\\Users\\asus\\Desktop\\opencv_modules\\opencv\\build\\java\\x64\\opencv_java343.dll";
          protected String fpath="C:\\Users\\asus\\Documents\\NetBeansProjects\\Goruntu\\src\\imgproc\\";
          
        public BufferedImage GrayScale()
        {
        
        try
        {
            File f = new File(filepath);
            image=ImageIO.read(f);
            
            width=image.getWidth();
            heigth=image.getHeight();
            for(int row=0;row<heigth;row++) 
            {
                for(int col=0;col<width;col++)
                {
                    Color c = new Color(img.getRGB(col, row));
                    //int val=c.getRGB()//- değerler cıkıyo 32 bit sayı en anlamlı 8 alpha degeri diğerleri r g b sırayla
                    //System.out.println(val);
                    int red = (int)(c.getRed() * 0.3); 
                    int green = (int)(c.getGreen() * 0.59); 
                    int blue = (int)(c.getBlue() *0.11);
                    int sum=red+green+blue;
                     Color newColor = new Color(sum,sum,sum);
                     //img.setRGB(col, row, newColor.getRGB());
                     image.setRGB(col, row, newColor.getRGB());
                }
            }
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return image;
        }
        
       //ödev#####################################################################################################################
      public int[][] dctTransform(int [][] buf)
      {
          int i, j, k, l,m=buf.length,n=buf.length;  
    int [][] dct = new int[m][n]; 
  
    double dct1, sum,ci,cj;
    double pi=3.1415;
  
    for (i = 0; i < m; i++) { 
        for (j = 0; j < n; j++) { 
  
            // ci and cj depends on frequency as well as 
            // number of row and columns of specified matrix 
            if (i == 0)
            {
                ci = 1 / Math.sqrt(m);
            }      
            else
            {
                ci = (Math.sqrt(2)/Math.sqrt(m)); 
            }   
            if (j == 0) 
            {
                cj = 1 / Math.sqrt(n); 
            }
            else
            {
                cj =(Math.sqrt(2)/Math.sqrt(n));
            }
            // sum will temporarily store the sum of  
            // cosine signals 
            sum = 0.0; 
            for (k = 0; k < m; k++) { 
                for (l = 0; l < n; l++) { 
                    
                    dct1 = buf[k][l] * Math.cos((2 * k + 1) * i * pi / (2 * m))*Math.cos((2 * l + 1) * j * pi / (2 * n));         
                    sum = sum + dct1; 
                    //System.out.println("cj lerin double normal hali "+(double)ci+" flot hali "+ci+"\t"+pi);
                } 
            } 
            dct[i][j] = (int)(ci * cj * sum); 
        } 
    } 
  
        return dct;
      }

        public void Printdct()
      {
          int [][] block = new int[8][8];
          for(int i=0;i<block.length;i++)
          {
              for(int j=0;j<block[i].length;j++)
              {
                  block[i][j]=(int)Math.floor(255/(i*j+1));
              }
          } 
          int [][] blockimg = dctTransform(block);//DCT(block);
          
          for(int row=0;row<blockimg.length;row++)
                {
                    for(int col=0;col<blockimg[row].length;col++)
                    {
                        System.out.print(blockimg[row][col]+"\t");
                    }
                    System.out.print("\n");
                }
      }
 
      public BufferedImage copymoveforgery() throws IOException
      {
          BufferedImage startimg = GrayScale();
          System.load(opencvpath);
         
          int width=startimg.getWidth(),height=startimg.getHeight(),w_new=width-7,h_new=height-7;
          int vectorval=0;
          double [] tempdizi = new double[16];
          int [][] block = new int[8][8];
          for(int i=0;i<block.length;i++)
          {
              for(int j=0;j<block[i].length;j++)
              {
                  block[i][j]=1;
              }
          }
         
          int rowval=(width-7)*(height-7);
          double lexvectorthres=2.12;//1- 10
          double coorvectorthres=60;
          double ed,euclidsum=0.0;
          double [][] vectors = new double[rowval][18];
          ArrayList<StackPoint> spone = new ArrayList<StackPoint>();
          ArrayList<StackPoint> sptwo = new ArrayList<StackPoint>();
          
          //baslangıç
           for(int i=0;i<h_new;i++)
            {
            for(int j=0;j<w_new;j++)
            {
                for(int a=i;a<i+block.length;a++)
                {
                    for(int b=j;b<j+block.length;b++)
                    {
                       Color c = new Color(startimg.getRGB(b, a));
                       block[a-i][b-j]*=c.getRed();
                    }
                }
                
                int [][] blockimg = dctTransform(block);
                
                
                tempdizi[0]=blockimg[0][0];tempdizi[1]=blockimg[0][1];tempdizi[2]=blockimg[1][0];tempdizi[3]=blockimg[2][0];
                tempdizi[4]=blockimg[1][1];tempdizi[5]=blockimg[0][2];tempdizi[6]=blockimg[0][3];tempdizi[7]=blockimg[1][2];
                tempdizi[8]=blockimg[2][1];tempdizi[9]=blockimg[3][0];tempdizi[10]=blockimg[4][0];tempdizi[11]=blockimg[3][1];
                tempdizi[12]=blockimg[2][2];tempdizi[13]=blockimg[1][3];tempdizi[14]=blockimg[0][4];tempdizi[15]=blockimg[0][5];
                 
                for(int v=0;v<tempdizi.length;v++)
                {
                    tempdizi[v]=Math.floor(tempdizi[v]/4);
                    vectors[vectorval][v]=tempdizi[v];                  
                    //System.out.println(tempdizi[v]);
                }
                vectors[vectorval][16]=j;//row,col şeklinde
                vectors[vectorval][17]=i;
                vectorval++;
                //burda yapacağız dct quantizion
                for(int k=0;k<8;k++)
                {
                    for(int l=0;l<8;l++)
                    {
                        block[k][l]=1;
                    }
                }
            
        }
    }     
          System.out.println(vectorval);
          StackPoint pone;
          StackPoint ptwo;
          for(int r=0;r<vectors.length;r++)
          {
              for(int c=r+1;c<vectors.length;c++)
              {
                  for(int elem=0;elem<16;elem++)
                  {
                      euclidsum+=Math.pow((vectors[r][elem]-vectors[c][elem]),2);
                  }
                  ed=Math.sqrt(euclidsum);
                  euclidsum=0;
                  //System.out.println(ed);
                  if(ed<lexvectorthres)
                  {
                      double dis=Math.sqrt(Math.pow(vectors[r][16]-vectors[c][16],2)+Math.pow(vectors[r][17]-vectors[c][17],2));
                      if(dis>coorvectorthres)
                      {
                          
                          ptwo = new StackPoint();
                          ptwo.setX(vectors[c][16]);
                          ptwo.setY(vectors[c][17]);
                          pone = new StackPoint();
                          pone.setX(vectors[r][16]);
                          pone.setY(vectors[r][17]);
                          spone.add(pone);  
                          sptwo.add(ptwo);
                          System.out.println(pone.getY()+"\t "+pone.getX()+" \t"+ptwo.getY()+" \t"+ptwo.getX());
                      }
                          
                      }     
              }
          }
         
          //son asama block koordinatlarının birbirine yakınlığı
          //test hepsi çizilecek
          int cmpp=0;
          StackPoint spz = new StackPoint(0,0);
          double xk,yk,dis;
          for(int cmp = 0;cmp<spone.size();cmp++)
          {
              for(int rmp = cmp+1;rmp<spone.size();rmp++)
              {
                  xk = spone.get(rmp).getX()-spone.get(cmp).getX();
                  yk = spone.get(rmp).getY()-spone.get(cmp).getY();
                  dis=Math.sqrt(Math.pow(xk,2)+Math.pow(yk,2));
                   //System.out.println(" spone "+dis);
                  if(dis>=20)
                  {
                      cmpp++;
                      if(cmpp>spone.size()*0.72)
                      {
                          spone.set(cmp, spz);
                          cmpp=0;
                          break;
                      } 
                  }
              }
              
          }
          cmpp=0;
          for(int cmp = 0;cmp<sptwo.size();cmp++)
          {
              for(int rmp = cmp+1;rmp<sptwo.size();rmp++)
              {
                   xk = sptwo.get(rmp).getX()-sptwo.get(cmp).getX();
                   yk = sptwo.get(rmp).getY()-sptwo.get(cmp).getY();
                   dis=Math.sqrt(Math.pow(xk,2)+Math.pow(yk,2));
                   
                   if(dis>=20)
                  {
                      cmpp++;
                      if(cmpp>=sptwo.size()*0.72)
                      {
                          sptwo.set(cmp, spz);
                          cmpp=0;
                          break;
                      } 
                  }
              }
             
          }
          
         
          
        Mat source = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);//new Mat(startimg.getWidth(),startimg.getHeight(),Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        
        
        
        for(int i=0;i<spone.size();i++)
          {
              
              StackPoint p = spone.get(i);
              Imgproc.rectangle(source, new Point(p.getX(),p.getY()), new Point(p.getX()+8,p.getY()+8), new Scalar(0,0,0),1);
          }
          for(int i=0;i<sptwo.size();i++)
          {
              StackPoint p = sptwo.get(i);
              Imgproc.rectangle(source, new Point(p.getX(),p.getY()), new Point(p.getX()+8,p.getY()+8), new Scalar(255,255,255),1);
          }
            String path=fpath+"copymove.bmp";
            Imgcodecs.imwrite(path, source);
      
           File out = new File(path);
           BufferedImage img = ImageIO.read(out);
            return img;
         
        }
    
    //sınıf sonu
      
  
    }
    İmageManipulation mnp = new İmageManipulation();
    public String filepath;
    BufferedImage img=null;
    public Goruntu() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _sourceimg = new javax.swing.JLabel();
        _destimg = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("CopyMove");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setText("Print");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(_sourceimg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 542, Short.MAX_VALUE)
                .addComponent(_destimg)
                .addGap(207, 207, 207))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_sourceimg)
                    .addComponent(_destimg))
                .addContainerGap(419, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       JFileChooser filechoose = new JFileChooser();
        int retval=filechoose.showOpenDialog(filechoose);
        if(retval==JFileChooser.APPROVE_OPTION)
        {
            filepath=filechoose.getSelectedFile().getAbsolutePath();
            try
            {
                img=ImageIO.read(new File(filepath));
                _sourceimg.setSize(img.getWidth(), img.getHeight());
                _sourceimg.setIcon(new ImageIcon(img));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "User Click to Cancel");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       try
            {
                _destimg.setSize(mnp.copymoveforgery().getWidth(), mnp.copymoveforgery().getHeight());
                _destimg.setIcon(new ImageIcon(mnp.copymoveforgery()));
            }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        mnp.Printdct();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    
    public static void main(String args[]) {
       
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Goruntu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Goruntu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Goruntu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Goruntu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Goruntu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _destimg;
    private javax.swing.JLabel _sourceimg;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    // End of variables declaration//GEN-END:variables
}
