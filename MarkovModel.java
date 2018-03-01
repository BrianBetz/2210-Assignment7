import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Your Name (you@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2016-04-19
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;
   private int kValue;
   private String wholeText = "";

   // add other fields as you need them ...


   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
    
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }

   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
    
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }

   /**
    * Builds an order K Markov model of the string sourceText.
    */
    
   private void buildModel(int K, String sourceText) {
      kValue = K;
      wholeText = sourceText;
      
      String kGram = "";
      String copy = wholeText;
      char suffix = ' ';
      
      if (wholeText.length() == kValue) {
         kGram = copy.substring(0, K);
         suffix = '\u0000';
         copy = copy.substring(1, copy.length());
         model.put(kGram, String.valueOf(suffix));
         kGram = null;
      }
           
      while (kGram != null) {
       
         kGram = copy.substring(0, K);
         suffix = copy.charAt(K);
         copy = copy.substring(1, copy.length());
         String tempString = String.valueOf(suffix);
        
         if (model.containsKey(kGram)) {
            String value = "";
            value = model.get(kGram).concat(tempString);
            model.put(kGram, value);
         }
         
         if (!model.containsKey(kGram)) {
            model.put(kGram, tempString);
         }
         
         if (copy.length() == K) {
            kGram = copy.substring(0, K);
            if (model.containsKey(kGram)) {
               String value = model.get(kGram).concat(tempString);
               model.put(kGram, value);
               kGram = null;
            }
            
            else {
               suffix = '\u0000';
               model.put(kGram, String.valueOf(suffix));
               kGram = null;
            }
         }
                    
         if (copy.length() < K) {
            kGram = null;
         }
      }
      
   }

   /** Returns the first kgram found in the source text. */
   
   public String getFirstKgram() {
      return wholeText.substring(0, kValue);
   }

   /** Returns a kgram chosen at random from the source text. */
   
   public String getRandomKgram() {
   
      Random random = new Random();
      List<String> keys = new ArrayList<String>(model.keySet());
      String randomKey = keys.get(random.nextInt(keys.size()) ); 
      
      return randomKey;     
   }

   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    
   public Set<String> getAllKgrams() {
      return model.keySet();
   }

   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
    
   public char getNextChar(String kgram) {
      Random randomInt = new Random();
      char c = ' ';
      
      if (model.containsKey(kgram)) {
         char[] array = new char[model.get(kgram).length()];
         array = model.get(kgram).toCharArray();
         c = array[randomInt.nextInt(array.length)];
         
      }
     
      return c;
   }

   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    
   @Override
    public String toString() {
      return model.toString();
   }

}
