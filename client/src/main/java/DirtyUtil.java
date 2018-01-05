import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DirtyUtil {
  
  private static final char PKG_SEPARATOR = '.';
  
  private static final char DIR_SEPARATOR = '/';
  
  private static final String CLASS_FILE_SUFFIX = ".class";
  
  private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are "
                                                      + "you sure the package '%s' exists?";
  
  
  private DirtyUtil() {
  
  }
  
  
  public static void main(String[] var0) {
    
    
    List var1 = find("com.epam.ships");
    
    int var2 = 0;
    
    
    for (int var3 = 0; var3 < var1.size(); ++var3) {
      
      String var4 = ((Class) var1.get(var3)).getName();
      
      var2 += count(var4);
      
    }
    
    
    System.out.print(var2);
    
  }
  
  
  private static int count(String var0) {
    
    int var1 = 0;
    
    
    try {
      
      Class var2 = Class.forName(var0);
      
      
      Method[] var3 = var2.getDeclaredMethods();
      
      Method[] var4 = var3;
      
      int var5 = var3.length;
      
      
      for (int var6 = 0; var6 < var5; ++var6) {
        
        Method var7 = var4[var6];
        
        if (var7.getModifiers() == 0) {
          
          ++var1;
          
        }
        
      }
      
    } catch (ClassNotFoundException var8) {
      
      var8.printStackTrace();
      
    }
    
    
    return var1;
    
  }
  
  
  private static List<Class<?>> find(String var0) {
    
    String var1 = var0.replace('.', '/');
    
    URL var2 = Thread.currentThread().getContextClassLoader().getResource(var1);
    
    if (var2 == null) {
      
      throw new IllegalArgumentException(String.format("Unable to get resources from path '%s'. "
                                                           + "Are you sure the package '%s' " +
                                                           "exists?", var1, var0));
      
    } else {
      
      File var3 = new File(var2.getFile());
      
      ArrayList var4 = new ArrayList();
      
      File[] var5 = var3.listFiles();
      
      int var6 = var5.length;
      
      
      for (int var7 = 0; var7 < var6; ++var7) {
        
        File var8 = var5[var7];
        
        var4.addAll(find(var8, var0));
        
      }
      
      
      return var4;
      
    }
    
  }
  
  
  private static List<Class<?>> find(File var0, String var1) {
    
    ArrayList var2 = new ArrayList();
    
    String var3 = var1 + '.' + var0.getName();
    
    if (var0.isDirectory()) {
      
      File[] var4 = var0.listFiles();
      
      int var5 = var4.length;
      
      
      for (int var6 = 0; var6 < var5; ++var6) {
        
        File var7 = var4[var6];
        
        var2.addAll(find(var7, var3));
        
      }
      
    } else if (var3.endsWith(".class")) {
      
      int var9 = var3.length() - ".class".length();
      
      String var10 = var3.substring(0, var9);
      
      
      try {
        
        var2.add(Class.forName(var10));
        
      } catch (ClassNotFoundException var8) {
      
      }
      
    }
    
    
    return var2;
    
  }
  
}