import mod.Game;
import javax.swing.*;
import static mod.utils.Configs.*;





public class Main
{
      /* Instanciação da classe estática que irá conter o jogo ( janela princípal ) */
      public static JFrame game_window = new JFrame ();
      
      
      
      /* Instanciação do icone da janela princípal */
      static ImageIcon game_icon = new ImageIcon ( "src/res/frame/game_icon.png" );
      
      
      
      /* Instânciação da classe que contem o jogo */
      static Game game_start = new Game ();
      
      
      
      /* Thread Main */
      public static void main ( String [] args )
      {
            // Definição dos atributos da janela
            game_window . setTitle ( "Minecraft 2D" );
            game_window . setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE );
            game_window . setResizable ( false );
            game_window . setSize ( SCREEN_WIDTH , SCREEN_HEIGHT );
            game_window . setIconImage ( game_icon . getImage () );
            game_window . setLocationRelativeTo ( null );
            
            
            // Adição do jogo a janela princípal
            game_window . add ( game_start );
            
            
            // Roda a janela
            game_window . setVisible ( true );
      }
}
