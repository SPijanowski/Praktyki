package csv_reader;

class Csv_File
{
   private String nazwa;
   private String separator;

   public Csv_File(String n, String s)
   {
      nazwa = n;
      separator = s;
   }

   public String getNazwa()
   {
      return nazwa;
   }

   public String getSeparator()
   {
      return separator;
   }

   public void setNazwa(String n)
   {
      nazwa = n;
   }

   public void setSeparator(String s)
   {
      separator = s;
   }
}
