import java.io.*;
import java.util.*;

/**
 *
 * @author Zheng Xu
 * xuzheng712@gmail.com
 */
public class SpellCheck {

    static HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    static String[] words;

    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner scan = new Scanner(System.in);
      System.out.println("Enter file name of dictionary");
       String filename = scan.nextLine();
           // String filename="english.txt";
        try {
            File file = new File(filename);
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                map.put(line.toLowerCase(), Boolean.TRUE);
            }
            words = map.keySet().toArray(new String[0]);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        String input = "";
        while (true) {
            System.out.print("> ");
            input = scan.nextLine().toLowerCase();
            if (map.get(input) != null) {
                //word exist in the dictionary
                System.out.println(input);
            } else {
                //word doesn't exist, so correct the spelling
                System.out.println(spellcheck(input));
            }
        }


    }
    
    
   /*correct the spelling
    For each word in the dictionary, computes its edit distance with the input 
     returns the word with the minimum edit distance
     */
    static String spellcheck(String input) {
        int index=0;
        int min=editDistance(words[0],input);
        int length=words.length;
        for(int i=1;i<length;i++)
        {
            int distance=editDistance(words[i],input);
            if(distance<min)
            {
                index=i;
                min=distance;
            }
                   
        }

        return words[index];
    }
    /*
     computes the Levenshtein distance of two strings
     */
    static int editDistance(String word, String input)
    {
        int word_len=word.length();
        int input_len=input.length();
       int[][] lev=new int[word_len+1][input_len+1];
       for(int i=0;i<input_len+1;i++)
       {
           lev[0][i]=i;
       }
        for(int i=0;i<word_len+1;i++)
       {
           lev[i][0]=i;
       }
       for(int i=1;i<word_len+1;i++)
       {
           for(int j=1;j<input_len+1;j++)
           {
               int cost=0;
               if(word.charAt(i-1)!=input.charAt(j-1))
               {
                   //check for vowels
                  if(checkVowel(word.charAt(i-1))==false || checkVowel(input.charAt(j-1))==false )
                        cost=2;
                  else
                      cost=1;
               }
               lev[i][j]=Math.min(Math.min(lev[i-1][j]+1, lev[i][j-1]+1),lev[i-1][j-1]+cost);
           }
       }
       
        return lev[word_len][input_len];
    }
    //returns true if c is a vowel
    //false if otherwise
   static boolean checkVowel(char c)
    {
        if(c=='a'|| c=='e'|| c=='i'||c=='o' || c=='u')
        {
            return true;
        }
        else
            return false;
    }
   
}
