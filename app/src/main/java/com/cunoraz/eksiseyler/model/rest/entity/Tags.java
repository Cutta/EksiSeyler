package com.cunoraz.eksiseyler.model.rest.entity;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class Tags {

    public static final Tag KULTUR = new Tag("kategori/kultur", "KÜLTÜR");

    public static final Tag BILIM = new Tag("kategori/bilim", "BİLİM");

    public static final Tag EGLENCE = new Tag("kategori/eglence", "EĞLENCE");

    public static final Tag YASAM = new Tag("kategori/yasam", "YAŞAM");

    public static final Tag SPOR = new Tag("kategori/spor", "SPOR");

    public static final Tag HABER = new Tag("kategori/haber", "HABER");

    public static final Tag EDEBIYAT = new Tag("kanal/edebiyat", "EDEBİYAT");

    public static final Tag ILISKILER = new Tag("kanal/iliskiler", "İLİŞKİLER");

    public static final Tag SIYASET = new Tag("kanal/siyaset", "SİYASET");

    public static final Tag OTOMOTIV = new Tag("kanal/otomotiv", "OTOMOTİV");

    public static final Tag MAGAZIN = new Tag("kanal/magazin", "MAGAZİN");

    public static final Tag MODA = new Tag("kanal/moda", "MODA");

    public static final Tag MOTOSIKLET = new Tag("kanal/motosiklet", "MOTOSİKLET");

    public static final Tag MUZIK = new Tag("kanal/muzik", "MÜZİK");

    public static final Tag OYUN = new Tag("kanal/oyun", "OYUN");

    public static final Tag PROGRAMLAMA = new Tag("kanal/programlama", "PROGRAMLAMA");

    public static final Tag SAGLIK = new Tag("kanal/saglik", "SAĞLIK");

    public static final Tag SANAT = new Tag("kanal/sanat", "SANAT");

    public static final Tag SINEMA = new Tag("kanal/sinema", "SİNEMA");

    public static final Tag SPOILER = new Tag("kanal/spoiler", "SPOILER");

    public static final Tag TARIH = new Tag("kanal/tarih", "TARİH");

    public static final Tag TEKNOLOJI = new Tag("kanal/teknoloji", "TEKNOLOJİ");

    public static final Tag YEME_ICME = new Tag("kanal/yeme-icme", "YEME İÇME");

    public static class Tag {
        private String endPoint;
        private String title;

        public Tag(String endPoint, String title) {
            this.endPoint = endPoint;
            this.title = title;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
