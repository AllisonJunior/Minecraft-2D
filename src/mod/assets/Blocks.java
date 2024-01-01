package mod.assets;
import java.awt.*;
import static mod.assets.LoadRes.*;
import static mod.assets.Map.map;





public class Blocks
{
      /* CONSTANTE DO TAMANHO DO BLOCO */
      public static final int BLOCK_SIZE = 64;
      
      
      
      /* DEFINIÇÃO DO TIPO DE BLOCO */
      public static final int
      BEDROCK = -1 ,
      AIR = 0 ,
      GRASS = 1 ,
      DIRT = 2 ,
      STONE = 3 ,
      LIGHT_LEAVES = 4 ,
      DARK_LEAVES = 5 ,
      LIGHT_WOOD = 6 ,
      DARK_WOOD = 7 ,
      RED_FLOWER = 8 ,
      YELLOW_FLOWER = 9 ,
      BLUE_FLOWER = 10 ,
      SAND = 11 ,
      WATER = 12 ,
      LAVA = 13 ,
      LIGHT_GLASS = 14 ,
      DARK_GLASS = 15 ,
      LIGHT_PLANK = 16 ,
      DARK_PLANK = 17 ,
      RANDOM = 18 ,
      MILK = 19 ,
      COBBLESTONE = 20;
      
      
      
      
      /* CHECAGEM DO TIPO DE BLOCO */
      private static Image getImageForType ( int type )
      {
             return switch ( type )
             {
                   case BEDROCK       -> res_BEDROCK_BLOCK;
                   case AIR           -> res_AIR_BLOCK;
                   case GRASS         -> res_GRASS_BLOCK;
                   case DIRT          -> res_DIRT_BLOCK;
                   case STONE         -> res_STONE_BLOCK;
                   case LIGHT_LEAVES  -> res_LIGHT_LEAVES_BLOCK;
                   case DARK_LEAVES   -> res_DARK_LEAVES_BLOCK;
                   case LIGHT_WOOD    -> res_LIGHT_WOOD;
                   case DARK_WOOD     -> res_DARK_WOOD;
                   case RED_FLOWER    -> res_RED_FLOWER_BLOCK;
                   case YELLOW_FLOWER -> res_YELLOW_FLOWER_BLOCK;
                   case BLUE_FLOWER   -> res_BLUE_FLOWER_BLOCK;
                   case SAND          -> res_SAND_BLOCK;
                   case WATER         -> res_WATER_BLOCK;
                   case LAVA          -> res_LAVA_BLOCK;
                   case LIGHT_GLASS   -> res_LIGHT_GLASS_BLOCK;
                   case DARK_GLASS    -> res_DARK_GLASS_BLOCK;
                   case LIGHT_PLANK   -> res_LIGHT_PLANK_BLOCK;
                   case DARK_PLANK    -> res_DARK_PLANK_BLOCK;
                   case RANDOM        -> res_RANDOM_BLOCK;
                   case MILK          -> res_MILK_BLOCK;
                   case COBBLESTONE   -> res_COBBLESTONE_BLOCK;
                   default            -> null;
             };
      }
      
      
      
      /* RENDERIZAÇÃO DO MAPA */
      public static void render ( Graphics g )
      {
            for ( int x = 0 ; x < Map . MAP_WIDTH ; x ++ ) for ( int y = 0 ; y < Map . MAP_HEIGHT ; y ++ )
            {
               int blockType = map [ x ] [ y ];
               Image blockImage = getImageForType ( blockType );
               g . drawImage ( blockImage , x * BLOCK_SIZE , y * BLOCK_SIZE , BLOCK_SIZE , BLOCK_SIZE , null );
            }
      }
}
