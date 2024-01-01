package mod;
import mod.entities.Player;
import mod.jill.BlockButton;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static mod.assets.Blocks.*;
import static mod.jill.LoadMin.*;
import static mod.utils.Configs.*;





public class Game extends JPanel
{
      /* CONSTRUÇÃO DA INTERFACE */
      public Game ()
      {
            // Configuração inicial do JPainel
            this . setPreferredSize ( new Dimension ( SCREEN_WIDTH , SCREEN_HEIGHT ) );
            this . setLayout ( null );
            this . addKeyListener ( game_control );
            this . setFocusable ( true );
            this . setCursor ( game_cursor );
            
            
            // Adicionamos o controlador do mouse
            this . addMouseListener ( new MouseAdapter ()
            {
                @Override public void mousePressed ( MouseEvent e )
                {
                         if ( SwingUtilities . isLeftMouseButton ( e ) )
                         {
                           int gridX = ( e . getX () + camera . getX () ) / BLOCK_SIZE;
                           int gridY = ( e . getY () + camera . getY () ) / BLOCK_SIZE;
                           processMousePress ( true , gridX , gridY );
                         }
                         else if ( SwingUtilities . isRightMouseButton ( e ) )
                         {
                             int gridX = ( e . getX () + camera . getX () ) / BLOCK_SIZE;
                             int gridY = ( e . getY () + camera . getY () ) / BLOCK_SIZE;
                             processMousePress ( false , gridX , gridY );
                         }
                }
            });
            
            
            // UI //
            
            // * LABEL QUE AVISA QUAL BLOCO FOI SELECIONADO * //
            game_selected_block . setBackground ( new Color ( 56 , 51 , 51 , 204 ) );
            game_selected_block . setForeground ( new Color ( 255 , 255 , 255 ) );
            game_selected_block . setFont ( new Font ( "Arial" , Font . BOLD , 18 ) );
            game_selected_block . setOpaque ( true );
            game_selected_block . setHorizontalAlignment ( SwingConstants . CENTER );
            game_selected_block . setBorder ( new CompoundBorder (  BorderFactory . createLineBorder ( new Color ( 26 , 28 , 26 )  , 3 ) , BorderFactory . createLineBorder ( new Color ( 89 , 100 , 89 ) , 1 ) ) );
            game_selected_block . setBounds ( 5 , 10 , 400 , 50 );
            this . add ( game_selected_block );
            
            
            // * BOTÕES DE SELEÇÃO DE BLOCOS * //
            
            new BlockButton ( 5 , 70 , GRASS , min_grass_block , "GRAMA" , game_selected_block , this );
            new BlockButton ( 5 , 135 , DIRT , min_dirt_block , "TERRA" , game_selected_block , this );
            new BlockButton ( 5 , 200 , STONE , min_stone_block , "PEDRA" , game_selected_block , this );
            new BlockButton ( 5 , 265 , LIGHT_LEAVES , min_L_leaves_block , "FOLHA CLARA" , game_selected_block , this );
            new BlockButton ( 5 , 330 , DARK_LEAVES , min_D_leaves_block , "FOLHA ESCURA" , game_selected_block , this );
            new BlockButton ( 5 , 395 , LIGHT_WOOD , min_L_wood_block , "MADEIRA CLARA" , game_selected_block , this );
            new BlockButton ( 5 , 460 , DARK_WOOD , min_D_wood_block , "MADEIRA ESCURA" , game_selected_block , this );
            new BlockButton ( 5 , 525 , RED_FLOWER , min_red_flower_block , "FLOR VERMELHA" , game_selected_block , this );
            new BlockButton ( 5 , 590 , YELLOW_FLOWER , min_yellow_flower_block , "FLOR AMARELA" , game_selected_block , this );
            new BlockButton ( 5 , 655 , BLUE_FLOWER , min_blue_flower_block , "FLOR AZUL" , game_selected_block , this );
            
            new BlockButton ( 70 , 70 , SAND , min_sand_block , "AREIA" , game_selected_block , this );
            new BlockButton ( 70 , 135 , COBBLESTONE , min_cobblestone , "PEDREGULHO" , game_selected_block , this );
            new BlockButton ( 70 , 200 , WATER , min_water_block , "ÁGUA" , game_selected_block , this );
            new BlockButton ( 70 , 265 , LAVA , min_lava_block , "LAVA" , game_selected_block , this );
            new BlockButton ( 70 , 330 , MILK , min_milk_block , "LEITE" , game_selected_block , this );
            new BlockButton ( 70 , 395 , LIGHT_GLASS , min_L_glass_block , "VIDRO CLARO" , game_selected_block , this );
            new BlockButton ( 70 , 460 , DARK_GLASS , min_D_glass_block , "VIDRO ESCURO" , game_selected_block , this );
            new BlockButton ( 70 , 525 , LIGHT_PLANK , min_L_plank_block , "MADEIRA CLARA" , game_selected_block , this );
            new BlockButton ( 70 , 590 , DARK_PLANK , min_D_plank_block , "MADEIRA ESCURA" , game_selected_block , this );
            new BlockButton ( 70 , 655 , RANDOM , min_random_block , "RANDOM" , game_selected_block , this );
            
            // UI //
            
            // Começa a música de fundo
            playMusic ();
            
            // Roda o jogo
            run ();
      }
      
      
      
      /* EXECUTAR A MÚSICA DE FUNDO */
      private static void playMusic ()
      {
             try
             {
                File audioFile = new File ( "src//aud//C418.wav" );
                AudioInputStream audioStream = AudioSystem . getAudioInputStream ( audioFile );
                Clip clip = AudioSystem . getClip ();
                clip . open ( audioStream );
                clip . loop ( Clip . LOOP_CONTINUOUSLY );
             }
             catch ( UnsupportedAudioFileException | LineUnavailableException | IOException e ) { e.printStackTrace(); }
      }
      
      
      
      /* LOOP LÓGICO DO JOGO */
      public void run ()
      {
            Thread game_loop = new Thread
            (() ->
            {
                  long lastTime = System . nanoTime ();
                  double targetUpdatesPerSecond = 60.0;
                  double nsPerUpdate = 1_000_000_000 / targetUpdatesPerSecond;
                  double delta = 0.0;
                  
                  
                  while ( isRunning )
                  {
                       long now = System . nanoTime ();
                       delta += ( now - lastTime ) / nsPerUpdate;
                       lastTime = now;
                       
                       
                       while ( delta >= 1.0 )
                       {
                            update ();
                            
                            
                            // Ajuste da posição da câmera para centralizar no jogador
                            int cameraTargetX = game_player . getX () - ( SCREEN_WIDTH / 2 - 64 / 2 );
                            int cameraTargetY = game_player . getY () - ( SCREEN_HEIGHT / 2 - 128 / 2 );
                            
                            
                            camera . move ( ( int ) Math . round ( cameraInterpolation * ( cameraTargetX - camera . getX () ) ) , ( int ) Math . round ( cameraInterpolation * ( cameraTargetY - camera . getY () ) ) );
                            delta -= 1.0;
                       }
                       
                       
                       repaint ();
                  }
            });
            
            
            game_loop . start ();
      }
      
      
      
      /* CONTROLE ( LISTENER ) DO PROCESSO DE CLIQUE DO MOUSE PARA COLOCAR E DESTRUIR BLOCOS */
      public static void processMousePress ( boolean leftButton , int gridX , int gridY )
      {
            if ( leftButton ) Player . destroyBlock ( gridX , gridY );
            else Player . placeBlock ( gridX , gridY , SELECTED_BLOCK );
      }
      
      
      
      /* ATUALIZAÇÃO LÓGICA DE POSIÇÃO DO PLAYER */
      public static void update () { game_player . update (); }
      
      
      
      /* ATUALIZAÇÃO DA TELA */
      protected void paintComponent ( Graphics g )
      {
               super . paintComponent ( g );
            
               // Desenha o plano de fundo
               game_back . paintIcon ( this , g , 0 , 0 );
               
               
               // Cria uma imagem temporária para renderização fora da tela visível
               BufferedImage offscreenImage = new BufferedImage ( SCREEN_WIDTH , SCREEN_HEIGHT , BufferedImage . TYPE_INT_ARGB );
               Graphics2D offscreenGraphics = offscreenImage . createGraphics ();
            
               
               // Renderiza os elementos no contexto temporário, aplicando a translação da câmera
               offscreenGraphics . translate ( - camera . getX () , - camera . getY () );
               
               
               game_player . render ( offscreenGraphics );
               render ( offscreenGraphics );
               
               
               offscreenGraphics . translate ( camera . getX () , camera . getY () );
            
               
               // Aqui fazemos a renderização da imagem
               g . drawImage ( offscreenImage , 0 , 0 , null );
            
               
               // Dispose do contexto temporário
               offscreenGraphics . dispose ();
      }
}
