package mod.utils;
import mod.entities.Player;

import javax.swing.*;
import java.awt.*;


public class Configs
{
      /* CONFIGURAÇÃO DA JANELA E CURSOR */
      public static final int SCREEN_WIDTH = 1344;
      public static final int SCREEN_HEIGHT = 768;
      public static final Toolkit toolkit = Toolkit . getDefaultToolkit ();
      public static Image cursorImage = toolkit . getImage ("src//res//cursor//cursor_def.png");
      public static Cursor game_cursor = toolkit . createCustomCursor ( cursorImage , new Point ( 0 , 0 ) , "customCursor" );
      
      
      
      /* THREAD DE LOOP DO JOGO */
      public static boolean isRunning = true;
      
      
      
      /* CONTROLE DE TECLAS */
      public static final Control game_control = new Control ();
      public static boolean [] game_keys = new boolean [ 256 ];
      
      
      
      /* INSTANCIAÇÃO DO PLAYER */
      public static Player game_player = new Player ( 400 , 200 );
      
      
      
      /* CONFIGURAÇÃO DA CAMERA */
      public static Camera camera = new Camera ( 0 , 0 );
      public static double cameraInterpolation = 0.1;
      
      
      
      /* SELEÇÃO DE BLOCOS E DEFINIÇÃO DE BLOCO INICIAL */
      public static int SELECTED_BLOCK = 17;
      
      
      
      /* LABEL DE INDICAÇÃO DE BLOCO */
      public static JLabel game_selected_block = new JLabel ( "BLOCO ATUAL: TÁBUA ESCURA" );
      
      /* PLANO DE FUNDO DO JOGO */
      public static ImageIcon game_back = new ImageIcon ( "src//res//background//parallax_07.jpg" );
}
