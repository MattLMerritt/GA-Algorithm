import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import static java.lang.Math.sqrt;
import java.util.Comparator;
import java.util.Arrays;

public class GeneticAlgorithm{
   static final String goal = ("INPUTSTRING").toLowerCase();//Make sure all charaters located in the alphabet.
   static int populationsize = 144*144;//must be a squared number(for perfect image)
   static boolean wordFound =false;
   static int mutationRate = 100;
   static int photoNum = 0;


   public static void main(String[] args)/*throws IOException*/{
      //Sets up the dimentions of the image
      int width = (int) sqrt(populationsize);
      int height = (int) sqrt(populationsize);
      
   
      //Creates first population
      Object[] wordbase = new Object[populationsize];//first gen
      Object[] parents = new Object[ (int) sqrt(populationsize)];//creates a parent pool small
      for(int i=0;i<wordbase.length;i++){
         System.out.println("object" + i);//debuging
         wordbase[i] = new wordObject(wordGen());//Creates object array of objects
      }
      Arrays.sort(wordbase);
      OutputAnImage(wordbase, width, height);
      
      //first generation.
      getInfo(wordbase);   
      FindParents(wordbase, parents);
      getInfo(parents);
      MattingObjects(wordbase,parents);
      getInfo(wordbase);  
      OutputAnImage(wordbase, width, height);
      
      //generations onward.
      while(!wordFound){
         FindMoreParents(wordbase,parents);
         getInfo(parents);
         MattingObjects(wordbase,parents);
         getInfo(wordbase);
         OutputAnImage(wordbase, width, height);
      }
      OutputAnImage(wordbase, width, height);
      System.out.println("!!!!!" + goal + " was found!!!!!");//cograts message.
   }
   
  //////////////////////////////////////////////////////////////METHODS///////////////////////////////////////////////////////////////////
  
   //Creates a random string of equal length to the goal.
   public static String wordGen(){
      final String alphabet = "abcdefghijklmnopqrstuvwxyz";
      Random r = new Random();
      String generatedWord ="";
      for(int x=0;x<goal.length();x++){
         generatedWord += (alphabet.charAt(r.nextInt(alphabet.length())));
      }
      return generatedWord;
   }
   
   
   //generates random char.
   public static char charGen(){
      final String alphabet = "abcdefghijklmnopqrstuvwxyz";
      Random r = new Random();
      String generatedWord ="";
      return (alphabet.charAt(r.nextInt(alphabet.length())));
   }
   
   
   //mutates a charater in an objects string.
   static String changeString(String s)
   {
      char[] characters = s.toCharArray();
      int rand = (int)(Math.random() * s.length());
      characters[rand] = charGen();
      return new String(characters);
   }

   
   //first time finding parents
   private static void FindParents(Object[] wordbase, Object[] parents){
      int counter = 0;
      for(int i=0;i<parents.length;i++){ 
         parents[counter] = new wordObject(((wordObject)wordbase[i]).getword());
         counter++;
      }
   }
   
   
   //finding parents onwards
   private static void FindMoreParents(Object[] wordbase, Object[] parents){//delete the title portion of the peramters?
      int counter = 0;
      for(int i=0;i<parents.length;i++){ 
         ((wordObject)parents[counter]).setWord(((wordObject)wordbase[i]).getword());
         counter++;
      }
   }


   //creates new population from parents
   public static void MattingObjects(Object[] wordbase, Object[] parents){ //matting procces
      Random r = new Random(); 
      for(int i=0;i<wordbase.length;i++){      
         String temp1 = (((wordObject)parents[r.nextInt(parents.length)]).getword()).substring(0,goal.length()/2);
         String temp2 = (((wordObject)parents[r.nextInt(parents.length)]).getword()).substring(goal.length()/2, goal.length()); 
         //creates the object from "DNA" of parent population.  
         ((wordObject)wordbase[i]).setWord(temp1 + temp2);
         
         //mutation
         if(r.nextInt(mutationRate/*mutation rate*/) == 0){
            ((wordObject)wordbase[i]).setWord(changeString(((wordObject)wordbase[i]).getword()));         
         }   
      }
      Arrays.sort(wordbase);//puts population in order(very important)
   }
   
   
   //returns deatils on current generation 
   public static void getInfo(Object[] wordbase){
      for(int i=0;i<wordbase.length;i++){//acceses the objects inside of the array.
         String temp1 = ((wordObject)wordbase[i]).getword();
         int temp2 = ((wordObject)wordbase[i]).fittness;
         int temp3 = ((wordObject)wordbase[i]).appeal;
         boolean temp4 = ((wordObject)wordbase[i]).matecapable;
         System.out.println("generated string: " + temp1 +" |fittness: " + temp2);//current generations results
         if(((wordObject)wordbase[i]).getword().equals(goal)){
            wordFound = true;
         }
      }
      System.out.println("_____________________________________________________________");
   }


   //creates an image
   private static void OutputAnImage(Object[] wordbaseee, int width, int height){
      //create buffered image object img
      BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      File f = null;
      
      int a =0;
      int r =0;
      int g =0;
      int b =0;
      int objectnum = 0;
         
      for(int y = 0; y < height; y++){
         for(int x = 0; x < width; x++){
            int temp = ((wordObject)wordbaseee[objectnum]).fittness;
            objectnum++;
            a = 255;
            r = 0;
            g = (int) Math.floor(( (double) temp/ (double) goal.length())*255.0);
            b = 0;
            int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
            img.setRGB(x, y, p);
         }
      }
      try{
         f = new File("/Users/admin/Desktop/GA-project/GAphotos/" + photoNum + ".png");///Users/admin/Desktop/GA-project/GAphotos/
         
         ImageIO.write(img, "png", f);
         photoNum++;
         System.out.println("Photo" + photoNum + " made succesfully.");
      }catch(IOException e){
         System.out.println("Error: " + e);
      }
   }
   
}