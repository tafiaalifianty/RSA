import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.math.BigInteger;
import java.io.IOException;
import java.util.*; 

class Generator {
  public static BigInteger generatePrime(){
    return BigInteger.probablePrime(512, new Random());
  }

  public static BigInteger[] generatePrimes(){
    BigInteger[] primes = new BigInteger[2];
    BigInteger x = null;
    BigInteger p = BigInteger.probablePrime(512, new Random());
    BigInteger q = BigInteger.probablePrime(512, new Random());
    
    System.out.print("A GCD(p,q)" + p.gcd(q));
    while ((p.gcd(q).equals(BigInteger.ONE)) == false){ //if not prime to each other
      p = BigInteger.probablePrime(512, new Random());
      q = BigInteger.probablePrime(512, new Random());
      System.out.print("B GCD(p,q)" + p.gcd(q));
    }
    primes[0] = p;
    primes[1] = q;

    return primes;
  }

  public static String bigIntToStr(BigInteger b){
    String base64 = "";
    // Convert ke Hex16
    String toHex = b.toString(16);
    // Encoding dengan Base64 (A-Za-z0-9+/ character set)
    base64 = Base64.getEncoder().encodeToString(toHex.getBytes());
    return base64;
  }

  public static BigInteger strToBigInt(String base64){
    BigInteger bigInteger = null;
    // Decode Base46 to hex
    byte[] b64Hex = Base64.getDecoder().decode(base64);
    // HEx to BigInt
    bigInteger = new BigInteger(new String(b64Hex), 16);
    return bigInteger;
  }

  public static void generateKeyFile(BigInteger[] pair){
    String eB64 = bigIntToStr(pair[0]);
    String dB64 = bigIntToStr(pair[1]);
    String nB64 = bigIntToStr(pair[2]);
    
    Path filePub = null;
    Path filePriv = null;

    try {
      filePub = Path.of("key.pub");
      filePriv = Path.of("key.pri");
      Files.writeString(filePub, eB64+"\n");
      Files.writeString(filePub, nB64, StandardOpenOption.APPEND);

      Files.writeString(filePriv, dB64+"\n");
      Files.writeString(filePriv, nB64, StandardOpenOption.APPEND);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void generateFile(String fileName, ArrayList<String> list, int operator){
    Path filePath = null;
    int line = 1;
    if(operator==0){
      fileName = "encrypted-" + fileName;
      // fileName = fileName+".encrypted";
    } else {
      // fileName = "[decrypted]" + fileName;
      fileName = "decrypted-" + fileName.split("\\.")[0] + "." + fileName.split("\\.")[1];
    }
    //try catch block
    try {
      filePath = Path.of(fileName);
      for(String s : list){
        if(line == 1){
          Files.writeString(filePath, s+"\n");
        } else if (line == list.size()){
          Files.writeString(filePath, s, StandardOpenOption.APPEND);
        } else {
          Files.writeString(filePath, s+"\n", StandardOpenOption.APPEND);
        }
        line++;
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void extractKey(String domain){
    BigInteger[] output = new BigInteger[2];
    Path path = null;

    try {
      path = Path.of(domain);
      List<String> read = Files.readAllLines(path);
      output[0] = strToBigInt(read.get(0));
      output[1] = strToBigInt(read.get(1));

    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    System.out.println(output[0]);
    System.out.println(output[1]);

    // return output;
  }
}
