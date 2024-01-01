package mod.jill;
import mod.Game;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

import static mod.utils.Configs.*;





public class BlockButton extends JButton
{
      /* PALETA DE BORDAS */
      Border default_border = BorderFactory . createCompoundBorder
      (
            BorderFactory . createLineBorder ( new Color ( 26 , 28 , 26 )  , 3 ) ,
            BorderFactory . createLineBorder ( new Color ( 89 , 100 , 89 ) , 1 )
      );
      Border hover_border = BorderFactory . createCompoundBorder
      (
            BorderFactory . createLineBorder ( new Color (117, 111, 161)  , 3 ) ,
            BorderFactory . createLineBorder ( new Color ( 89 , 100 , 89 ) , 1 )
      );
      
      
      
      /* PALETA DE COR DE FUNDO */
      Color default_background = new Color ( 93 , 84 , 84 , 204 );
      Color hover_background = new Color ( 192 , 181, 181, 204 );
      
      
      
      /* CONSTRUTOR DO BOTÃO */
      public BlockButton ( int x , int y , int block , Icon what , String name , JLabel change , Game painel )
      {
            // Atributos do botão
            this . setBounds ( x , y , 55 , 55 );
            this . setBackground ( default_background );
            this . setBorder ( default_border );
            this . setIcon ( what );
            
            
            // Adiciona a alteração de texto
            this . addActionListener ( new ActionListener ()
            {
                @Override public void actionPerformed ( ActionEvent e ) { change . setText ( "BLOCO ATUAL: " + name ); }
            });
            
            
            // Alteração de borda
            this . addMouseListener ( new MouseAdapter ()
            {
                @Override public void mouseEntered ( MouseEvent e )
                {
                        setBorder ( hover_border );
                        setBackground ( hover_background );
                }
                  
                @Override public void mouseExited ( MouseEvent e )
                {
                        setBorder ( default_border );
                        setBackground ( default_background );
                }
            });
            
            
            // Controle do foco na janela ( evita a parada do KeyListener no Game )
            this . addFocusListener ( new FocusAdapter ()
            {
                @Override public void focusGained ( FocusEvent e )
                {
                        painel . requestFocusInWindow ();
                        SELECTED_BLOCK = block;
                }
            });
            
            
            // Adiciona esse botão ao paínel
            painel . add ( this );
      }
}
