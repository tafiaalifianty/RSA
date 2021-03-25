import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class RSA{
  public static BigInteger[] generateTrinity(BigInteger p, BigInteger q){
    BigInteger[] pair = new BigInteger[3];

    BigInteger e = new BigInteger("65537"); //e=65537 (RSA Key distribution wikipedia)
    BigInteger n = p.multiply(q);// n=(p*q)

    BigInteger k = new BigInteger("2"); //constant, k=2
    BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // totient(n)
    // gcd(e, totient) harus 1
    // while ((totient.gcd(e).equals(BigInteger.ONE)) == false){ //if not prime to each other
    //   e.add(BigInteger.ONE);
    // }

    BigInteger d = e.modInverse(totient); // equals to: ((k*tot(n)) + 1) / e

    // Stores to pair(Key E,Key D, N)
    pair[0] = e;
    pair[1] = d;
    pair[2] = n;

    return pair;
  }

  public static void display(String file, String pub){
    Path path = null;
    System.out.println(); 
    try {
      path = Path.of(file);
      List<String> read = Files.readAllLines(path);
      for(String s : read){
        for(int i=0; i<s.length(); i++){
          System.out.print(s.charAt(i));
        }
        System.out.println();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  // String read = Files.readAllLines(path); *RERTURNS LIST OF STRING LIEN
  public static void encrypt(String msgFileName, String pubFileName){
    BigInteger bigInteger = null;
    BigInteger e = null;
    BigInteger n = null;

    List<String> message;
    List<String> publicPair;
    ArrayList<String> toWrite = new ArrayList<>();

    System.out.println(); 
    try {
      message = Files.readAllLines(Path.of(msgFileName));
      publicPair = Files.readAllLines(Path.of(pubFileName));

      e = Generator.strToBigInt(publicPair.get(0));
      n = Generator.strToBigInt(publicPair.get(1));

      for(String s : message){
        bigInteger = new BigInteger(1, s.getBytes());
        // return bigInteger.toString(16);
        // System.out.println("\nori bigint: "+ bigInteger);
        BigInteger encrypted = bigInteger.modPow(e, n);
        // System.out.println("---\nenc bigint: "+ encrypted);
        String enc = Generator.bigIntToStr(encrypted);
        toWrite.add(enc);
        // System.out.println("after modpower: "+encrypted);
        // System.out.println("---\nbefore enc: "+(new String(bigInteger.toByteArray()))); //print in original form
        // System.out.println("---\nafter enc: "+ enc); //print in original form
        // System.out.println("from enc: "+ Generator.strToBigInt(enc)+"\n"); //print in original form
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally{
      Generator.generateFile(msgFileName, toWrite, 0);
    }
  }

  public static ArrayList<String> encryptFile(File msgFile, File pubKey){
    BigInteger bigInteger = null;
    BigInteger e = null;
    BigInteger n = null;

    List<String> message;
    List<String> publicPair;
    ArrayList<String> toWrite = new ArrayList<>();
    ArrayList<String> toHex = new ArrayList<>();

    System.out.println(); 
    try {
      message = Files.readAllLines(Path.of(msgFile.getPath()));
      publicPair = Files.readAllLines(Path.of(pubKey.getPath()));

      e = Generator.strToBigInt(publicPair.get(0));
      n = Generator.strToBigInt(publicPair.get(1));

      for(String s : message){
        bigInteger = new BigInteger(1, s.getBytes());
        BigInteger encrypted = bigInteger.modPow(e, n);
        String enc = Generator.bigIntToStr(encrypted);
        toWrite.add(enc);
        String encHex = encrypted.toString(16);
        toHex.add(encHex);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally{
      Generator.generateFile(msgFile.getName(), toWrite, 0);
    }
    return toHex;
  }

  public static void decrypt(String encFilename, String privFilename){
    Path encPath = null;
    Path privPath = null;

    String origin = null;
    BigInteger iterator = null;
    BigInteger d = null;
    BigInteger n = null;

    List<String> encryptedText;
    List<String> privatePair;
    ArrayList<String> toWrite = new ArrayList<>();

    System.out.println(); 
    try {
      encPath = Path.of(encFilename);
      privPath = Path.of(privFilename);
      
      encryptedText = Files.readAllLines(encPath);
      privatePair = Files.readAllLines(privPath);

      d = Generator.strToBigInt(privatePair.get(0));
      n = Generator.strToBigInt(privatePair.get(1));
      
      for(String s : encryptedText){
        iterator = Generator.strToBigInt(s);
        iterator = iterator.modPow(d, n);
        origin = new String(iterator.toByteArray());
        toWrite.add(origin);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally{
      Generator.generateFile(encFilename, toWrite, 1);
    }
  }

  public static void decryptFile(File enc, File privKey){
    String origin = null;
    BigInteger iterator = null;
    BigInteger d = null;
    BigInteger n = null;

    List<String> encryptedText;
    List<String> privatePair;
    ArrayList<String> toWrite = new ArrayList<>();

    System.out.println(); 
    try {
      encryptedText = Files.readAllLines(Path.of(enc.getPath()));
      privatePair = Files.readAllLines(Path.of(privKey.getPath()));

      d = Generator.strToBigInt(privatePair.get(0));
      n = Generator.strToBigInt(privatePair.get(1));
      
      for(String s : encryptedText){
        iterator = Generator.strToBigInt(s);
        iterator = iterator.modPow(d, n);
        origin = new String(iterator.toByteArray());
        toWrite.add(origin);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally{
      Generator.generateFile(enc.getName(), toWrite, 1);
    }
  }
}