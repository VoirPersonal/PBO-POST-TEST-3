package model;

public class Anorganik extends Sampah {
    // Encapsulation - private field
    private boolean dapatDidaurUlang;
    
    public Anorganik(String jenis, double berat, boolean dapatDidaurUlang) {
        super(jenis, berat, "Anorganik");
        this.dapatDidaurUlang = dapatDidaurUlang;
    }
    
    // Getter dan Setter
    public boolean isDapatDidaurUlang() {
        return dapatDidaurUlang;
    }
    
    public void setDapatDidaurUlang(boolean dapatDidaurUlang) {
        this.dapatDidaurUlang = dapatDidaurUlang;
    }
    
    // Override abstract method
    @Override
    public String getInfoTambahan() {
        return "Daur Ulang: " + (dapatDidaurUlang ? "Ya" : "Tidak");
    }
    
    // Override method untuk memberikan info detail yang berbeda
    @Override
    public String getInfoDetail() {
        return super.getInfoDetail() + String.format(", Dapat Didaur Ulang: %s", 
                                                    dapatDidaurUlang ? "Ya" : "Tidak");
    }
    
    // Override method hitungHarga - sampah anorganik yang dapat didaur ulang lebih mahal
    @Override
    public double hitungHarga() {
        double hargaDasar = super.hitungHarga();
        if (dapatDidaurUlang) {
            return hargaDasar * 1.5; // 150% dari harga dasar jika dapat didaur ulang
        } else {
            return hargaDasar * 0.3; // 30% dari harga dasar jika tidak dapat didaur ulang
        }
    }
    
    // Method khusus untuk sampah anorganik
    public String getStatusLingkungan() {
        if (dapatDidaurUlang) {
            return "Ramah Lingkungan - Dapat Didaur Ulang";
        } else {
            return "Perlu Perhatian Khusus - Sulit Terurai";
        }
    }
    
    // Method untuk menghitung estimasi waktu terurai (dalam tahun)
    public int getEstimasiWaktuTerurai() {
        if (dapatDidaurUlang) {
            // Sampah yang dapat didaur ulang biasanya lebih mudah terurai
            if (getJenis().toLowerCase().contains("kertas")) {
                return 1; // 1 tahun
            } else if (getJenis().toLowerCase().contains("plastik")) {
                return 50; // 50 tahun
            } else if (getJenis().toLowerCase().contains("logam")) {
                return 100; // 100 tahun
            } else {
                return 25; // default 25 tahun
            }
        } else {
            // Sampah yang tidak dapat didaur ulang lebih lama terurai
            return 500; // 500 tahun
        }
    }
}