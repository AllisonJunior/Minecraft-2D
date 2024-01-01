package mod.assets;





public class Map
{
      /* DETALHES DO MAPA */
      public static final int MAP_WIDTH = 80;
      public static final int MAP_HEIGHT = 48;
      
      
      
      /* INSTANCIAÇÃO DO MAPA */
      public static final int [] [] map = new int [ MAP_WIDTH ] [ MAP_HEIGHT ];
      
      
      
      /* CONSTRUÇÃO DO MAPA */
      static
      {
            // CONFIGURAÇÃO INICIAL DO MAPA
            
            
              // Preenchimento inicial do mapa com ar
              for ( int x = 0; x < MAP_WIDTH; x++ ) for ( int y = 0; y < MAP_HEIGHT; y++ ) map [ x ] [ y ] = Blocks . AIR;
              
              // Contorno do mapa com bedrock
              // 1. Fundo
              for ( int times = 0 ; times < 80 ; times ++ ) map [ times ] [ 47 ] = Blocks . BEDROCK;
              // 2. Topo
              for ( int times = 0 ; times < 80 ; times ++ ) map [ times ] [ 0 ] = Blocks . BEDROCK;
              // 3. Esquerda
              for ( int times = 0 ; times < 47 ; times ++ ) map [ 0 ] [ times ] = Blocks . BEDROCK;
              // 4. Direita
              for ( int times = 0 ; times < 47 ; times ++ ) map [ 79 ] [ times ] = Blocks . BEDROCK;
              
              
            // CONFIGUREM AQUI A CONSTRUÇÃO DO MAPA
      }
}
