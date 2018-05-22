/*
 * GameForm.java
 *
 * Created on March 15, 2010, 2:48 AM
 */
package chessgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A window to contain a GamePanel and menu items
 * @author  Paul
 */

import chess.GamePanel;

public class GameWindow extends javax.swing.JFrame{

    GamePanel gameScreen;
    
    /** 
     * Creates new GameWindow 
     */
    public GameWindow() {
        initComponents();
        init();
    }
   
    /**
     * Initializes the game in the form
     */
    private void init()
    {
        gameScreen = new GamePanel(jPanel1.getWidth(), jPanel1.getHeight());
        jPanel1.add(gameScreen);
        
        
		
		JButton Surrender =new JButton("Surrender");
		Surrender.setBounds(900, 100, 100, 50);
		Surrender.addActionListener(new ActionListener(){    // actionPerformed duoc goi khi 1 action xuat hien

			@Override
			public void actionPerformed(ActionEvent arg0) {
			    int a=JOptionPane.showConfirmDialog(gameScreen,"Are you sure?");  
			    if(a==JOptionPane.YES_OPTION){  
			        gameScreen.newAiGame();
			    } 
			}
		});
		jPanel1.add(Surrender);
        
        
		JLabel Pawn = new JLabel("Pawn:");
		Pawn.setBounds(900, 150, 50, 50);
		jPanel1.add(Pawn);
		
		JLabel PawnValue = new JLabel();
		PawnValue.setBounds(950, 150, 50, 50);
		PawnValue.setText(String.valueOf(gameScreen.L.get(7).getImageNumber()));
		jPanel1.add(PawnValue);
		
		
		
		JLabel Rook = new JLabel("Rook:");
		Rook.setBounds(900, 200, 50, 50);
		jPanel1.add(Rook);
		
		JLabel RookValue = new JLabel();
		RookValue.setBounds(950, 200, 50, 50);
		RookValue.setText(String.valueOf(gameScreen.L.get(8).getImageNumber()));
		jPanel1.add(RookValue);
		
		
		
		JLabel Knight = new JLabel("Knight:");
		Knight.setBounds(900, 250, 50, 50);
		jPanel1.add(Knight);
		
		JLabel KnightValue = new JLabel();
		KnightValue.setBounds(950, 250, 50, 50);
		KnightValue.setText(String.valueOf(gameScreen.L.get(9).getImageNumber()));
		jPanel1.add(KnightValue);
		
		
		JLabel Bishop = new JLabel("Bishop:");
		Bishop.setBounds(900, 300, 50, 50);
		jPanel1.add(Bishop);
		
		JLabel BishopValue = new JLabel();
		BishopValue.setBounds(950, 300, 50, 50);
		BishopValue.setText(String.valueOf(gameScreen.L.get(10).getImageNumber()));
		jPanel1.add(BishopValue);
		
		
		
		JLabel Queen = new JLabel("Queen:");
		Queen.setBounds(900, 350, 50, 50);
		jPanel1.add(Queen);
		
		JLabel QueenValue = new JLabel();
		QueenValue.setBounds(950, 350, 50, 50);
		QueenValue.setText(String.valueOf(gameScreen.L.get(11).getImageNumber()));
		jPanel1.add(QueenValue);
    }
    
    private void initComponents() {

        jPanel1 = new JPanel();
        jMenuBar_Main = new JMenuBar();
        jMenu_Game = new javax.swing.JMenu();
        jMenuItem_New1P = new JMenuItem();
        jMenuItem_New2P = new JMenuItem();
        jMenuItem_Undo = new JMenuItem();
        jMenuItem_Close = new JMenuItem();
        jMenu_File = new JMenu();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chess Game");
        setLocationByPlatform(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        jMenu_Game.setText("Game");

        jMenuItem_New1P.setText("New 1-Player");
        jMenuItem_New1P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_New1PActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_New1P);

        jMenuItem_New2P.setText("New 2-Player");
        jMenuItem_New2P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_New2PActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_New2P);

        jMenuItem_Undo.setText("Undo");
        jMenuItem_Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UndoActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Undo);

        jMenuItem_Close.setText("Close");
        jMenuItem_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_CloseActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Close);

        jMenuBar_Main.add(jMenu_Game);





        setJMenuBar(jMenuBar_Main);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
private void jMenuItem_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_CloseActionPerformed
    this.dispose();
}//GEN-LAST:event_jMenuItem_CloseActionPerformed

private void jMenuItem_New2PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_New2PActionPerformed
    gameScreen.newGame();
}//GEN-LAST:event_jMenuItem_New2PActionPerformed


private void jMenuItem_UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UndoActionPerformed
    gameScreen.undo();
}//GEN-LAST:event_jMenuItem_UndoActionPerformed

private void jMenuItem_New1PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_New1PActionPerformed
    gameScreen.newAiGame();
}//GEN-LAST:event_jMenuItem_New1PActionPerformed

private void jPanel1_componentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1_componentResized
    // allow the game board to be resized
    if (jPanel1 != null && gameScreen != null) {
        int smallerDimension = jPanel1.getHeight();
        if (jPanel1.getWidth() < smallerDimension)
            smallerDimension = jPanel1.getWidth();
        // make sure the board stays square and completely visible
        gameScreen.setSize(smallerDimension, smallerDimension);
    }
}//GEN-LAST:event_jPanel1_componentResized

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar_Main;
    private javax.swing.JMenuItem jMenuItem_Close;
    private javax.swing.JMenuItem jMenuItem_New1P;
    private javax.swing.JMenuItem jMenuItem_New2P;
    private javax.swing.JMenuItem jMenuItem_Undo;
    private javax.swing.JMenu jMenu_File;
    private javax.swing.JMenu jMenu_Game;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
