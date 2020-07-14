public class wordObject implements Comparable<wordObject>{
   int fittness;
   private String word;
   int appeal;//compared to other objects
   boolean matecapable;
   
   //constructor.
   public wordObject(String word){
      this.word = word;
      this.fittness = compareforfittness(this.word, GeneticAlgorithm.goal);
   }
   
   //gets fittness of word passed thorugh
   public static int compareforfittness(String word, String goal){
      int fittness = 0;
      char[] first  = word.toCharArray();
      char[] second = goal.toCharArray();
      //comparison
      for(int i =0;i<word.length();i++){
         
         if (first[i] == second[i]){
            fittness++;    
         }
      }
      return fittness;
   }        
   
   //getter for word.
   public String getword() {
      return word; 
   }
   
   //getter for word.
   public int getFittness(){
      return fittness;
   }
   
   //getter and setter for word and fittness.
   public void setWord(String newword){
      this.word = newword;
      this.fittness = compareforfittness(this.word, GeneticAlgorithm.goal);
   }
   
   
   
   //comparable method for creating sorted array(very important).
   public int compareTo(wordObject other) {
      int otherFittness = ((wordObject) other).getFittness(); 
      return otherFittness - this.fittness;
   }
   
}