package simodev;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Simodev {

    static int montedongu = 0;
    static Sheet sheet;
    static Workbook wb = null;
    static int satir = 0;

    public static void main(String[] args) throws IOException {

        

        Scanner klavye = new Scanner(System.in);
        FileInputStream fis = new FileInputStream("deger.xlsx");
        
        wb = new XSSFWorkbook(fis);
        sheet = wb.getSheetAt(0);

        //BURADA EXCEL VERİSİNİN SATIR SAYISINI CEKTIM
        for (int satirsayisi = sheet.getLastRowNum(); satirsayisi >= 0; satirsayisi--) {
            final Row row = sheet.getRow(satirsayisi);
            if (row != null && row.getCell(0) != null) {
                satir = satirsayisi + 1;
                System.out.println(satir);
                break;
            }
        }
        
        monte mc = new monte();
        //BURADA SATIR SAYISI, CEKECEGIMIZ SUTUN NUMARASI VE EXCEL DOSYASININ YOLUNU GIRIYORUZ
        Double[] cikis =mc.veri_cek(satir, 1, fis);
       
       

        System.out.println();

        mc.frekans();
        System.out.println("---------------------------------------");
        System.out.println(" ORANLAR");
        System.out.println("---------------------------------------");
        mc.oranbulma();

        System.out.println("---------------------------------------");
        System.out.println(" KUMULATİF");
        System.out.println("---------------------------------------");
        mc.kumulatif();

        System.out.println("Kaç yil istiyorsunuz ");//EGER 100 GIRERSENIZ 1200 FARKLI DEGER OLUSUR HER AY ICIN 100.
        montedongu = klavye.nextInt();
        mc.yeniyilolusturma(montedongu);
        System.out.println("---------------------------------------");
        System.out.println(" ORTA KARE YONTEMI");
        System.out.println("---------------------------------------");
        mc.ortakare(5778, 10);//BASLANGIC SAYISI VE DONGU SAYISINI YAZIYORUZ
        System.out.println("---------------------------------------");
        System.out.println(" DOGRUSAL ESLIK YONTEMI");
        System.out.println("---------------------------------------");
        mc.dogrusal(5, 3, 16, 7, 100);//A C M Z VE DONGU SAYISINI SIRASIYLA YAZIYORUZ

    }

}
