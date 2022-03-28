package simodev;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class monte {

    Double dizi[] = new Double[Simodev.satir];
    int sayac = 0;
    int fr[] = new int[dizi.length];
    int frj[] = new int[4];
    int yenifre[] = new int[frj.length];
    Double oran[] = new Double[frj.length + 1];
    Double[] out = new Double[oran.length];
    final int[] intArray = new int[dizi.length];
    int yazdir[] = new int[oran.length];//4 farkli deger bu dizide tutuluyor
    int sayidizi[] = new int[40];
    int programsayac = 0;
    int onikiayverisi[] = new int[12];
    String onikiay[] = {"Ocak", "Subat", "Mart", "Nisan", "Mayis", "Haziran", "Temmuz", "Agustos", "Eylul", "Ekim", "Kasim", "Aralik"};
    Sheet sheet;

    ArrayList<Double> randsayilar = new ArrayList<>();
    ArrayList<Integer> gecicifrekans = new ArrayList<>();
    
    /*
        LEVENT BUGDAYCI 02175076054
    
    ILK MONTECARLO YU YAPTIM VERIYI EXCELDEN ALIP FREKANSINI ORANINI VE KUMULATIF DEGERLERINI
    BULDUKTAN SONRA RANDOM SAYI OLUSTURUP KUMULATIF DEGERLER ILE KONTROL ETTIRDIM.
    YENI OLUSAN YILLARIN VERILERININ FREKANSINI HEM AYRI AYRI BULDUM HEMDE TOPLU BIR SEKILDE
    YAZDIRDIM HER OLUSAN YILIN FREKANSI YAZIYOR VE OLUSAN TUM YILLARIN FREKANSI DA TOPLU BIR SEKILDE YAZIYOR HOCAM 
    SZIN ANLATTIGINIZ YERDE BEN TAM ANLAMADIM BEN DE IKISINI BIRDEN YAPTIM
    
    EN ALTTAKI IKI FONKSIYON ISE RANDOM SAYI OLUSTURMA 
    
    */
    
    //BURADA EXCEL VERIYI ALIP BIR DIZIYE KOYDUM
    public Double[] veri_cek(int satir, int sutun, FileInputStream fis) throws FileNotFoundException, IOException {
        Double value = null;

        sheet = Simodev.wb.getSheetAt(0);

        for (int i = 0; i < satir; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(sutun);
            value = cell.getNumericCellValue();
            dizi[i] = value;

        }
        System.out.println(dizi.length);

        return dizi;
    }
    //BURADA EXCELDEN ALDIGIM VERILERIN FREKANSINI BULUYORUM
    public void frekans() {

        for (int i = 0; i < intArray.length; ++i) {
            int intvalue;
            intvalue = (int) Math.round(dizi[i]);
            intArray[i] = intvalue;
        }
        Arrays.sort(intArray);
        int bakildimi = -1;
        for (int i = 0; i < intArray.length; i++) {
            int count = 1;
            for (int j = i + 1; j < intArray.length; j++) {
                if (intArray[i] == intArray[j]) {
                    count++;
                    fr[j] = bakildimi;
                }
            }
            if (fr[i] != bakildimi) {
                fr[i] = count;//AYNI OLANLARIN SAYISINI BULUYORUM
            }

        }

        System.out.println("---------------------------------------");
        System.out.println(" Degerler | Frekans");
        System.out.println("---------------------------------------");
        
        for (int i = 0; i < fr.length; i++) {
            if (fr[i] != bakildimi) {
                int gec = intArray[i];
                yazdir[sayac] = intArray[i];
                System.out.println("    " + gec + "    |    " + fr[i]);//EKRANA VERILERI YAZDIRDIM

                frj[sayac] = fr[i];

                sayac++;
            }

        }

    }
    //BURADA EXCEL VERILERININ FREKANSINI BULUYORUM
    public void oranbulma() {

        for (int i = 0; i < frj.length; i++) {
            oran[i] = (double) frj[i] / Simodev.satir;
            System.out.println("    " + yazdir[i] + "    |    " + oran[i]);
        }

    }
    //BURADA EXCEL VERILERININ KUMULATIFINI BULUYORUM
    public void kumulatif() {

        for (int i = 0; i < oran.length - 1; i++) {

            out[0] = 0.0;

            out[i + 1] = (double) out[i] + oran[i];

            System.out.println("    " + yazdir[i] + "    |    " + out[i]);
        }

    }
    int iterasyon = 12;
    int yil = 2022;
    
    
    
    //BURADAKI FONKSIYONDA DEGER OLARAK SADECE DONGU SAYISINI ALIYORUM
    public void yeniyilolusturma(int dongu) {

        int dongudeger = 0;

        for(int k = 0 ; k<dongu;k++) {
            for (int i = 0; i < iterasyon; i++) {
                Double randsayi = Math.random();//RANDOM SAYI OLUŞTURUYORUM
                for (int j = 0; j < 4; j++) {
                    gecicifrekans.add(0);//BURADA HER YIL ICIN AYRI AYRI FREKANS OLUSTURDUGUM DIZIYE 0 DEGERLERINI ATIYORUM KI FREKANS DEGERLERI DOGRU CIKSIN BURASI COK ONEMLI DEGIL
                }
                
                for (int j = 0; j < 4; j++) {
                    if (randsayi > out[j] && randsayi < out[j + 1]) {//BURADA OLUSAN RANDOM SAYIYI ILK DORT YILIN KUMULATIF DEGERLERIYLE KARSILASTIRIYORUM
                                                                     //VE HEM TOPLAM FREKANS ICIN DEGERLERI ATIYORUM HEM DE GECICI FREKANS ICIN ATIYORUM
                        yenifre[j] = yenifre[j] + 1;// TUM DEGERLERIN FREKANSLARI BU DIZIDE
                        onikiayverisi[i] = yazdir[j];
                        gecicifrekans.set(j, gecicifrekans.get(j) + 1);

                    }

                }

            }
            
            //HOCAM BURADA SIZ ILK YILIN VERISINI ISTEDIGINIZ ICIN ILK RANDOM YILI YAZDIRDIM EGER IF'I KALDIRIP ICINDEKI KODLARI CIKARIRSANIZ HER RANDOM YIL YAZILIR.
            if (dongudeger==0) {
                System.out.println("************* YIL " + yil++ + "*************");
                for (int i = 0; i < 12; i++) {

                    System.out.println("    " + onikiay[i] + "    |    " + onikiayverisi[i]);
                }
                for (int i = 0; i < 4; i++) {
                    System.out.println("    " + yazdir[i] + "    |    " + gecicifrekans.get(i));
                }
            

            }
            //HER YIL ICIN AYRI AYRI YAZDIRDIGIM ICIN BU ARRAYI TEMIZLIYORUM
            gecicifrekans.clear();

            
            dongudeger++;
            //RECURSIVE OLARAK KENDINI DONGU SAYISININ BIR EKSIGI KADAR CAGIRIYORUM
            

        }

        //100663296
        
        System.out.println("***********FREKANS KARSILASTIRMA**************************ORAN KARSILASTIRMA***********");
        for (int i = 0; i < 4; i++) {
            System.out.println("    " + yazdir[i] + "    |    " + frj[i] + "    ||    " + yazdir[i] + "    |    " + yenifre[i] + "    ||||    " + oran[i] + "    |    " + (double) yenifre[i] / (iterasyon * Simodev.montedongu));//ORANLARINI yenifre[i] / (iterasyon * Simodev.montedongu) KODU ILE YANI FREKANS DEGERINI TOPLAM DEGER SAYISINA BOLUNCA CIKAN SONUC ORAN OLUR

        }
        System.out.println("Dizideki 3,4,5,6 degerlerinin Frekanslarının ve Oranlarının karsilastirilmasi");
        

    }

    public void ortakare(int sayi, int dongu) {
        if (dongu > 0) {
            int sayac1 = 0;
            int sayideger = sayi;
            double randsayi = 0;
            sayi = sayi * sayi;
            for (int i = 0; sayi > 1; i++) {//BURADA SAYININ HER BASAMAGINI BIR DIZIYE ATIYORUM
                sayidizi[i] = sayi % 10;

                sayi = sayi / 10;
                sayac1++;
                //System.out.println(sayi);
                //System.out.println(sayidizi[i]);
            }
            //System.out.println(Arrays.toString(sayidizi));
            int hangibasamak1 = 3;//HANGI BASAMAKLAR ARASI OLDUGUNU BURADA GOSTERIYORUM
            int hangibasamak2 = 6;

            int us = 0;
            int yenisayi = 0;
            for (int i = hangibasamak1; i < hangibasamak2 + 1; i++) {

                //System.out.println(sayidizi[i-1]);
                int katsayi = (int) Math.pow(10, us);//BURADA SAYININ HANGI JAC BASAMAKLI OLDUGUNU YANI SAYININ 100LER BASAMAGI YA DA 10LAR BASAMAGI GIBI 

                yenisayi += sayidizi[i - 1] * katsayi;

                us++;
                int katsayi1 = (int) Math.pow(10, basamaksayisi(yenisayi));
                randsayi = (double) yenisayi / katsayi1;//BURADA SAYIYI 1 DEN KUCUK YAPMAK ICIN VIRGULLU HALE GETIRIYORUM ORNEK OLARAK SAYI 150 ISE 1000 E BOLUYORUM 0,150 OLSUN DIYE

            }
            randsayilar.add(randsayi);
            ortakare(yenisayi, dongu - 1);//BURADA ORTASINDAN ALINAN SAYIYI TEKRAR FONKSIYONA SOKUYORUM

        } else {
            System.out.println(randsayilar);
            System.out.println(randsayilar.size());
        }

    }

    public int basamaksayisi(int deger) {
        int sayi = deger;
        int basamak = 0;
        while (sayi > 0) {
            sayi /= 10;
            basamak++;
        }
        return basamak;

    }
    ArrayList<Double> dogrusalrandom = new ArrayList<>();

    public void dogrusal(int a, int c, int m, int z, int dongu) {

        if (dongu > 0) {//BURADA TAMAMEN SIZIN GOSTERDIGINIZ FORMULU UYGULADIM

            Double u = 0.0;

            u = (double) z / m;
            dogrusalrandom.add(u);
            z = (a * z + 3) % m;
            dogrusal(a, c, m, z, dongu - 1);

        } else {
            System.out.println(dogrusalrandom);
            System.out.println(dogrusalrandom.size());
        }

    }
    
    
        
        
    

}
