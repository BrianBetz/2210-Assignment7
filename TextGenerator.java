import java.io.File;
import java.io.IOException;

/**
 * TextGenerator.java. Creates an order K Markov model of the supplied source
 * text, and then outputs M characters generated according to the model.
 *
 * @author     Your Name (you@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2016-04-19
 *
 */
public class TextGenerator {

   /** Drives execution. */
   public static void main(String[] args) {
   
      if (args.length < 3) {
         System.out.println("Usage: java TextGenerator k length input");
         return;
      }
   
      // No error checking!
      int K = Integer.parseInt(args[0]);
      int M = Integer.parseInt(args[1]);
      if ((K < 0) || (M < 0)) {
         System.out.println("Error: Both K and M must be non-negative.");
         return;
      }
   
      File text;
      try {
         text = new File(args[2]);
         if (!text.canRead()) {
            throw new Exception();
         }
      }
      catch (Exception e) {
         System.out.println("Error: Could not open " + args[2] + ".");
         return;
      }
   
   
      // instantiate a MarkovModel with the supplied parameters and
      // generate sample output text ...
      
      MarkovModel monkey = new MarkovModel(K, text);
      String output = "";
      
      output += monkey.getFirstKgram();
      
      String prevKgram = monkey.getFirstKgram();
      
      while (output.length() < M) {
         if (monkey.getNextChar(prevKgram) == '\u0000') {
            String randomKgram = monkey.getRandomKgram();
            output += randomKgram;
            prevKgram = randomKgram; 
         }
         
         else {
            output += monkey.getNextChar(prevKgram);
            prevKgram = output.substring(output.length() - K);
         }
      }
      
      String out = output.substring(0, output.length());
      System.out.println(out);
   }
}
