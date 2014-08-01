package csv_reader;

/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.10
 * @date 01.08.2014
 */

class Csv_File
{
   private String nazwa;
   private static String separator;
   private static String csvFilePath;

   public Csv_File(String n, String s)
   {
      nazwa = n;
      separator = s;
   }
   public Csv_File(String f)
   {
	  csvFilePath = f;
   }
   public String getNazwa()
   {
      return nazwa;
   }

   public static String getSeparator()
   {
      return separator;
   }
   public static String getScvFilePath()
   {
	   return csvFilePath;
   }
   public void setNazwa(String n)
   {
      nazwa = n;
   }

   public static void setSeparator(String s)
   {
      separator = s;
   }
   public static void setCsvFilePath(String f)
   {
	   csvFilePath = f;
   }
}
