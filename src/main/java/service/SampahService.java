package service;

import model.Sampah;
import model.Organik;
import model.Anorganik;
import java.util.ArrayList;

public class SampahService {
    // Encapsulation - private field
    private ArrayList<Sampah> dataSampah = new ArrayList<>();
    
    // Getter untuk akses data (read-only)
    public ArrayList<Sampah> getDataSampah() {
        return new ArrayList<>(dataSampah); // Return copy untuk menjaga encapsulation
    }
    
    // Method untuk mendapatkan jumlah data
    public int getJumlahData() {
        return dataSampah.size();
    }
    
    // Method untuk cek apakah data kosong
    public boolean isDataKosong() {
        return dataSampah.isEmpty();
    }
    
    public void tambahOrganik(String jenis, double berat, String tingkatDekomposisi) {
        try {
            dataSampah.add(new Organik(jenis, berat, tingkatDekomposisi));
            System.out.println("Data sampah organik berhasil ditambahkan!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void tambahAnorganik(String jenis, double berat, boolean dapatDidaurUlang) {
        try {
            dataSampah.add(new Anorganik(jenis, berat, dapatDidaurUlang));
            System.out.println("Data sampah anorganik berhasil ditambahkan!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void lihatData() {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data sampah.");
            return;
        }
        
        System.out.println("\nDATA SAMPAH");
        System.out.println("=================================================================");
        System.out.printf("%-5s %-20s %-10s %-15s %-20s %-15s%n", 
                         "No.", "Jenis", "Berat", "Kategori", "Info Tambahan", "Harga (Rp)");
        System.out.println("-----------------------------------------------------------------");
        
        double totalHarga = 0;
        for (int i = 0; i < dataSampah.size(); i++) {
            Sampah s = dataSampah.get(i);
            double harga = s.hitungHarga();
            totalHarga += harga;
            
            System.out.printf("%-5d %-20s %-10.1f %-15s %-20s %-15.0f%n", 
                             i + 1, s.getJenis(), s.getBerat(), s.getKategori(), 
                             s.getInfoTambahan(), harga);
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("Total Estimasi Nilai: Rp %.0f%n", totalHarga);
    }
    
    public void updateData(int index, String jenis, double berat) {
        if (index >= 0 && index < dataSampah.size()) {
            try {
                Sampah s = dataSampah.get(index);
                s.setJenis(jenis);
                s.setBerat(berat);
                System.out.println("Data berhasil diupdate!");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Index tidak valid!");
        }
    }
    
    public void hapusData(int index) {
        if (index >= 0 && index < dataSampah.size()) {
            dataSampah.remove(index);
            System.out.println("Data berhasil dihapus!");
        } else {
            System.out.println("Index tidak valid!");
        }
    }
    
    // Method untuk mendapatkan data berdasarkan index
    public Sampah getData(int index) {
        if (index >= 0 && index < dataSampah.size()) {
            return dataSampah.get(index);
        }
        return null;
    }
    
    // Fitur search yang diperbaiki
    public void cariData(String kataKunci) {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data sampah.");
            return;
        }
        
        ArrayList<Sampah> hasilPencarian = new ArrayList<>();
        
        for (Sampah s : dataSampah) {
            if (s.getJenis().toLowerCase().contains(kataKunci.toLowerCase()) || 
                s.getKategori().toLowerCase().contains(kataKunci.toLowerCase())) {
                hasilPencarian.add(s);
            }
        }
        
        if (hasilPencarian.isEmpty()) {
            System.out.println("Tidak ditemukan data dengan kata kunci '" + kataKunci + "'");
            return;
        }
        
        System.out.println("\nHASIL PENCARIAN: '" + kataKunci + "'");
        System.out.println("=================================================================");
        System.out.printf("%-5s %-20s %-10s %-15s %-20s %-15s%n", 
                         "No.", "Jenis", "Berat", "Kategori", "Info Tambahan", "Harga (Rp)");
        System.out.println("-----------------------------------------------------------------");
        
        for (int i = 0; i < hasilPencarian.size(); i++) {
            Sampah s = hasilPencarian.get(i);
            System.out.printf("%-5d %-20s %-10.1f %-15s %-20s %-15.0f%n", 
                             i + 1, s.getJenis(), s.getBerat(), s.getKategori(), 
                             s.getInfoTambahan(), s.hitungHarga());
        }
    }
    
    // Method tambahan untuk menampilkan detail lengkap
    public void lihatDetailData(int index) {
        if (index >= 0 && index < dataSampah.size()) {
            Sampah s = dataSampah.get(index);
            System.out.println("\n=== DETAIL DATA SAMPAH ===");
            System.out.println(s.getInfoDetail());
            System.out.printf("Estimasi Harga: Rp %.0f%n", s.hitungHarga());
            
            if (s instanceof Organik) {
                Organik organik = (Organik) s;
                System.out.printf("Estimasi Waktu Dekomposisi: %d hari%n", 
                                organik.getWaktuDekomposisi());
            } else if (s instanceof Anorganik) {
                Anorganik anorganik = (Anorganik) s;
                System.out.println("Status Lingkungan: " + anorganik.getStatusLingkungan());
                System.out.printf("Estimasi Waktu Terurai: %d tahun%n", 
                                anorganik.getEstimasiWaktuTerurai());
            }
        } else {
            System.out.println("Index tidak valid!");
        }
    }
    
    // Method untuk menghitung statistik
    public void tampilkanStatistik() {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data untuk ditampilkan statistiknya.");
            return;
        }
        
        int jumlahOrganik = 0;
        int jumlahAnorganik = 0;
        double beratOrganik = 0;
        double beratAnorganik = 0;
        double totalNilai = 0;
        
        for (Sampah s : dataSampah) {
            totalNilai += s.hitungHarga();
            if (s instanceof Organik) {
                jumlahOrganik++;
                beratOrganik += s.getBerat();
            } else if (s instanceof Anorganik) {
                jumlahAnorganik++;
                beratAnorganik += s.getBerat();
            }
        }
        
        System.out.println("\n=== STATISTIK BANK SAMPAH ===");
        System.out.println("Total Data: " + dataSampah.size());
        System.out.println("Sampah Organik: " + jumlahOrganik + " item (" + beratOrganik + " kg)");
        System.out.println("Sampah Anorganik: " + jumlahAnorganik + " item (" + beratAnorganik + " kg)");
        System.out.println("Total Berat: " + (beratOrganik + beratAnorganik) + " kg");
        System.out.printf("Total Estimasi Nilai: Rp %.0f%n", totalNilai);
    }
}