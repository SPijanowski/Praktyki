package csv_reader;

class Csv_File
{
   private String nazwa;
   private String separator;
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

   public String getSeparator()
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

   public void setSeparator(String s)
   {
      separator = s;
   }
   public static void setCsvFilePath(String f)
   {
	   csvFilePath = f;
   }
}
