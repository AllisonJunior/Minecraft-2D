package mod.utils;





public class Camera
{
      /* VARIÁVEIS DE CONTROLE DA POSIÇÃO DA CAMERA */
      private int camera_x , camera_y;
      
      
      
      /* CONSTRUTOR DA CAMERA */
      public Camera ( int initialX , int initialY ) { camera_x = initialX; camera_y = initialY; }
      
      
      
      /* RETORNA A POSIÇÃO [ X ] ATUAL DA CAMERA */
      public int getX () { return camera_x; }
      
      
      
      /* RETORNA A POSIÇÃO [ Y ] ATUAL DA CAMERA */
      public int getY () { return camera_y; }
      
      
      
      /* MOVE A POSIÇÃO DA CAMERA */
      public void move ( int dx , int dy ) { camera_x += dx; camera_y += dy; }
}
