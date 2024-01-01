package mod.utils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static mod.utils.Configs.*;





public class Control extends KeyAdapter
{
      /* TECLA PRESSIONADA */
      @Override public void keyPressed  ( KeyEvent e ) { game_keys [ e . getKeyCode () ] = true;  }
      
      
      
      /* TECLA SOLTA */
      @Override public void keyReleased ( KeyEvent e ) { game_keys [ e . getKeyCode () ] = false; }
      
      
      
      /* CONFIRMA O CLIQUE */
      public static boolean isKeyPressed ( int vkA ) { return game_keys [ vkA ]; }
}
