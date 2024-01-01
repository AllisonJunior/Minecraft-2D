package mod.entities;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import static mod.assets.Blocks.*;
import static mod.assets.Map.map;
import static mod.entities.LoadPla.*;
import static mod.utils.Configs.*;
import static mod.utils.Control.isKeyPressed;





public class Player
{
      /* POSIÇÃO X E Y DO PLAYER */
      private int player_x , player_y;
      
      /* VELOCIDADE [ HORIZONTAL ] DO PLAYER */
      private int player_speed = 5;
      
      /* VELOCIDADE [ VERTICAL ] DO PLAYER */
      private int player_yvelocity = 0;
      
      /* CONSTANTE REFERENTE A FORÇA DO PULO */
      private int player_jumpstrength = -17;
      
      /* CHECAGEM DO PULO DO PLAYER */
      private boolean player_isjumping = false;
      
      /* INVERTE HORIZONTALMENTE O PLAYER */
      private boolean horizontal_side = false;
      
      
      
      /* DEFINIÇÃO DO ENUMERADOR COM OS ESTADOS DO PLAYER */
      private enum PlayerState { WALKING , STOPPED , JUMPING }
      
      /* INSTANCIAÇÃO DO 'PlayerState' PARA CONTROLE DE ESTADOS */
      public static PlayerState estado = PlayerState . STOPPED;
      
      
      
      /* CONSTRUTOR DO PLAYER EM SUAS POSIÇÕES INICIAIS NO PAÍNEL */
      public Player ( int initX , int initY ) { player_x = initX; player_y = initY; }
      
      
      
      /* MOVE A POSIÇÃO X DO PLAYER PARA A [ ESQUERDA ] */
      public void moveLeft  () { player_x -= player_speed; }
      
      /* MOVE A POSIÇÃO X DO PLAYER PARA A [ DIREITA ] */
      public void moveRight () { player_x += player_speed; }
      
      
      
      /* FAZ COM QUE O PLAYER PULE */
      public void jump () { if ( ! player_isjumping ) { player_yvelocity = player_jumpstrength; player_isjumping = true; } }
      
      
      
      /* RETORNA A POSIÇÃO [ X ] ATUAL DO PLAYER */
      public int getX () { return player_x; }
      
      /* RETORNA A POSIÇÃO [ Y ] ATUAL DO PLAYER */
      public int getY () { return player_y; }
      
      
      
      /* APLICA A GRÁVIDADE NO PLAYER ( física ) */
      public void applyGravity () { player_yvelocity += 1; }
      
      
      
      /* HIT-BOX DO PLAYER */
      public Rectangle getBounds () { return new Rectangle ( player_x , player_y , 64 , 128 ); }
      
      
      
      /* LÓGICA DE ATUALIZAÇÃO DO PLAYER COM BASE NAS TECLAS , RENDERIZAÇÃO E COLISÃO */
      public void update ()
      {
            // Após o clique de uma das teclas de movimento
            if ( isKeyPressed ( KeyEvent . VK_A ) || isKeyPressed ( KeyEvent . VK_D ) )
            {
              // Altera o estado do player para 'andando'
              estado = PlayerState . WALKING;
              
              
              // Caso clique na tecla 'a' [ O player olha e se move para a esquerda ]
              if ( isKeyPressed ( KeyEvent . VK_A ) ) { this . moveLeft ();  horizontal_side = true; }
                  
              // Caso clique na tecla 'd' [ O player olha e se move para a direita ]
              if ( isKeyPressed ( KeyEvent . VK_D ) ) { this . moveRight (); horizontal_side = false; }
            }
            
            // Caso não clique mais o player volta ao estado parado
            else estado = PlayerState . STOPPED;
            
            
            // Após o clique da tecla de pulo 'barra de espaço'
            if ( isKeyPressed ( KeyEvent . VK_SPACE ) )
            {
              // Muda o estado do player para pulando
              estado = PlayerState . JUMPING;
              
              // Faz com que o player pule
              this . jump ();
            }
            
            
            // Chamamos a função responsável pela atualização da posição do player
            updatePosition ();
            
            
            // Chamamos a função responsável pela colisão de blocos no mapa com o player
            checkCollisionWithBlocks ();
      }
      
      
      
      /* ATUALIZAÇÃO DA POSIÇÃO DO PLAYER */
      private void updatePosition ()
      {
             // Verifica se o player não está colidindo com um bloco acima ou acima e à direita
             if ( ! isCollidingWithBlock ( ( player_x + camera . getX () ) / BLOCK_SIZE , ( player_y + player_yvelocity + camera . getY () ) / BLOCK_SIZE ) && ! isCollidingWithBlock ( ( player_x + 64 + camera . getX () ) / BLOCK_SIZE , ( player_y + player_yvelocity + camera . getY () ) / BLOCK_SIZE ) )
               // Atualiza a posição vertical do player com base na velocidade
               player_y += player_yvelocity;
                   
             // Caso haja colisão, mantém a posição vertical do player ( trata a colisão )
             else player_y += player_yvelocity;
            
             
             // Aplica a gravidade para o player ( Física )
             applyGravity ();
            
             
             // Processo de atualização da camera em relação ao player, para centralizar-lá
             camera . move ( ( int ) Math . round ( cameraInterpolation * ( player_x - camera . getX () - ( ( double ) SCREEN_WIDTH / 2 - ( double ) 64 / 2 ) ) ) , ( int ) Math . round ( cameraInterpolation * ( player_y - camera . getY () - ( ( double ) SCREEN_HEIGHT / 2 - ( double ) 128 / 2 ) ) ) );
      }
      
      
      
      /* ATUALIZAÇÃO DA CHECAGEM DA COLISÃO DO PLAYER COM OS BLOCOS DO MAPA */
      private void checkCollisionWithBlocks ()
      {
            // Instanciação da hit-box que será utilizada no player
            Rectangle playerBounds = getBounds ();
            
            
            // O mapa por completo é percorrido para executar a checagem de colisão
            for ( int x = 0 ; x < map . length ; x++ ) for ( int y = 0 ; y < map [ 0 ] . length ; y++ )
            {
               // Variável que armazena o tipo de bloco para checar se existe colisão ou não
               int blockType = map [ x ] [ y ];
               
               
               // Checamos se 'blockType' é um bloco de 'ar' , 'flor' , 'gate' , 'lava' , 'leite' , 'água' e caso não seja ele possui colisão e continuamos a checagem
               if ( blockType != AIR && blockType != RED_FLOWER && blockType != YELLOW_FLOWER && blockType != BLUE_FLOWER && blockType != WATER && blockType != LAVA && blockType != MILK )
               {
                 // A variável 'blockbounds' basicamente armazena a colisão do bloco que é a mesma para todos 64 x 64
                 Rectangle blockBounds = new Rectangle ( x * BLOCK_SIZE , y * BLOCK_SIZE , BLOCK_SIZE , BLOCK_SIZE );
                 
                 
                 // Este teste condicional verifica se a hitbox do jogador 'playerBounds' colide com a hitbox do bloco 'blockBounds' usando a técnica de colisão AABB ( Axis-Aligned Bounding Box )
                 if ( playerBounds . intersects ( blockBounds ) )
                 {
                   // Aqui, as variáveis 'dx' e 'dy' estão sendo calculadas para determinar a distância entre os centros do jogador e do bloco nas direções x e y , respectivamente, ou seja, calcular a colisão.
                   int dx = Math . abs ( ( playerBounds . x + playerBounds . width / 2  ) - ( blockBounds . x + blockBounds . width / 2  ) );
                   int dy = Math . abs ( ( playerBounds . y + playerBounds . height / 2 ) - ( blockBounds . y + blockBounds . height / 2 ) );
                   
                   
                   // Aqui, as variáveis 'overlapX" e 'overlapY' estão sendo calculadas para representar a colisão nas direções x e y entre as hitboxes do jogador e do bloco, ou seja, essas variáveis indicam o quanto os objetos estão colidindo.
                   int overlapX = ( playerBounds . width + blockBounds . width   ) / 2 - dx;
                   int overlapY = ( playerBounds . height + blockBounds . height ) / 2 - dy;
                   
                   
                   // Aqui verificamos se a colisão na direção x 'overlapX' é maior do que na direção y 'overlapY', ou seja, determinar se a colisão está ocorrendo mais na vertical ou na horizontal.
                   if ( overlapX > overlapY )
                   {
                     // Se a colisão está mais na vertical ajustamos a posição vertical do jogador para cima ou para baixo, dependendo da posição do bloco em relação ao jogador, e o contrário no caso do else abaixo. [ O 'player_isjumping' específico é para impedir que o player se agarre ao teto ]
                     if ( playerBounds . y < blockBounds . y ) { player_y = blockBounds.y - playerBounds.height; player_isjumping = false; }
                     else { player_y = blockBounds.y + blockBounds.height; player_isjumping = true; }
                     
                     // Reseta a posição vertical do player
                     player_yvelocity = 0;
                   }
                   
                   // Se a colisão não está mais na vertical, ou seja, está mais na horizontal fazemos...
                   else
                   {
                       // Aqui reposicionamos o player com base na colisão com a esquerda ou direita de um bloco
                       if ( playerBounds . x < blockBounds . x ) player_x = blockBounds . x - playerBounds . width;
                       else player_x = blockBounds.x + blockBounds.width;
                       
                       // Definimos a posição vertical do player ( Basicamente aqui fazemos com que ele não grude na parede ):
                       // A velocidade vertical do jogador é incrementada ( limitada a um máximo de 8 ) para simular um efeito de pulo, e a variável 'player_isjumping' é definida como verdadeira para indicar que o jogador está pulando.
                       player_yvelocity = Math . min ( player_yvelocity + 3 , 8 );
                       
                       // Define que o player está pulando para impedir que o player possa pular infinitamente
                       player_isjumping = true;
                   }
                                    
                 }
               }
            }
      }
      
      
      
      /* FUNÇÃO PARA DESTRUIR UM BLOCO */
      public static void destroyBlock ( int gridX , int gridY )
      {
            // Basicamente fazemos a seguinte checagem:
            //
            // 1 # [ gridX >= 0 e gridX < map . length ]       > Garantem que gridX esteja dentro dos limites horizontais da matriz do mapa.
            // 2 # [ gridY >= 0 e gridY < map [ 0 ] . length ] > Garantem que gridY esteja dentro dos limites verticais da matriz do mapa.
            // 3 # map [ gridX ] [ gridY ] != BEDROCK ]        > Verifica se o tipo de bloco na posição (gridX, gridY) não é um "BEDROCK" (um tipo especial de bloco que geralmente é indestrutível).
            //
            if ( gridX >= 0 && gridX < map . length && gridY >= 0 && gridY < map [ 0 ] . length && map [ gridX ] [ gridY ] != BEDROCK )
              map [ gridX ] [ gridY ] = AIR;
      }
      
      
      
      /* FUNÇÃO PARA COLOCAR UM BLOCO */
      public static void placeBlock ( int gridX , int gridY , int blockType )
      {
            // Basicamente fazemos a seguinte checagem:
            //
            // 1 # [ gridX >= 0 e gridX < map . length ]       > Garantem que gridX esteja dentro dos limites horizontais da matriz do mapa.
            // 2 # [ gridY >= 0 e gridY < map [ 0 ] . length ] > Garantem que gridY esteja dentro dos limites verticais da matriz do mapa.
            // 3 # [ map [ gridX ] [ gridY ] == AIR ]          > Verifica se a posição (gridX, gridY) na matriz do mapa está vazia ("AIR"), ou seja, se não há bloco nessa posição.
            //
            if ( gridX >= 0 && gridX < map . length && gridY >= 0 && gridY < map [ 0 ] . length && map [ gridX ] [ gridY ] == AIR )
              map [ gridX ] [ gridY ] = blockType;
      }
      
      
      
      /* FUNÇÃO USADA PARA VERIFICAR SE UMA COLISÃO OCORREU */
      public static boolean isCollidingWithBlock ( int gridX , int gridY )
      {
            // Checagem para saber se a colisão está fora dos limites válidos da matriz do mapa
            if ( gridX < 0 || gridX >= map . length || gridY < 0 || gridY >= map [ 0 ] . length )
              return true;
            
            // Se o tipo de bloco for diferente de 'AIR', isso significa que um bloco está presente naquela posição e a função retorna true, indicando que há uma colisão.
            return map [ gridX ] [ gridY ] != AIR;
      }
      
      
      
      /* CLASSE RESPONSÁVEL PELA RENDERIZAÇÃO DO PLAYER */
      public void render ( Graphics g )
      {
            // Imagem que será utilizada como referência do avatar 'player'
            Image StateImage = null;
            
            
            // Controle de estados para realizar a alteração de sprites
            switch ( estado )
            {
                  case JUMPING -> StateImage = player_JUMPING; // 05 frames
                  case WALKING -> StateImage = player_WALKING; // 10 frames
                  case STOPPED -> StateImage = player_STOPPED; // 01 frame
            }
            
            
            // Processo de flip-image para realizar a inversão horizontal do player
            if ( StateImage != null )
            {
              // Aqui pegamos a largura da imagem e a sua altura e salvamos em 2 variáveis
              int imageWidth  = StateImage . getWidth  ( null );
              int imageHeight = StateImage . getHeight ( null );
              
              
              // Aqui utilizamos a booleana de inversão de lado
              if ( horizontal_side )
              {
                // Cria uma cópia espelhada da imagem original
                BufferedImage mirroredImage = new BufferedImage ( imageWidth , imageHeight , BufferedImage . TYPE_INT_ARGB );
                Graphics2D g2d = mirroredImage . createGraphics ();
                
                
                // Inverte a imagem horizontalmente
                g2d . drawImage ( StateImage , imageWidth , 0 , - imageWidth , imageHeight , null );
                g2d . dispose ();
                
                
                // Desenha o player
                g . drawImage ( mirroredImage , player_x , player_y , null );
              }
              
              // Desenha o player
              else g . drawImage ( StateImage , player_x , player_y , null );
            }
      }
}