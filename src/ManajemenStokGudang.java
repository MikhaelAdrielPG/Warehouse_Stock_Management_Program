import java.util.InputMismatchException;
import java.util.Scanner;

public class ManajemenStokGudang {
    int maxKategori;
    int maxBarangPerKategori;
    final int INDEKS_STOK = 3;
    String[][][] gudang;
    Scanner scanner = new Scanner(System.in);

    // Fungsi untuk menampilkan menu pilihan kepada pengguna dan memproses inputnya
    public void menu() {
        // Buat variabel pilihan untuk menyimpan pilihan pengguna.
        int pilihan;

        // untuk inisialisasi tambah data barang,
        // dan meminta pengguna terlebih dahulu untuk memasukkan items dan jumlah data barang
        tambahDataBarang();

        // Lakukan perulangan sampai pengguna memilih untuk keluar.
        do {
            // Tampilkan menu.
            System.out.println("\nMENU");
            System.out.println("1. Tambah Stok Barang dari Barang yang Sudah Ada");
            System.out.println("2. Kurangi Stok Barang");
            System.out.println("3. Lihat Stok Barang");
            System.out.println("0. Keluar");
            System.out.print("Masukkan pilihan: ");

            // Membaca masukkan pengguna sebagai integer.
            try {
                pilihan = scanner.nextInt();

                // untuk memproses pilihan pengguna.
                switch (pilihan) {
                    case 1 -> tambahStokBarang();
                    case 2 -> kurangiStokBarang();
                    case 3 -> lihatStokBarang();
                    case 0 -> System.out.println("Terima kasih, telah menggunakan Program Manajemen Stok Gudang.");
                    default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (InputMismatchException e) {
                // Tangkap pengecualian jika pengguna memasukkan input yang bukan integer.
                System.out.println("Masukkan harus berupa angka. Silakan coba lagi.");
                scanner.nextLine(); // Konsumsi input yang tidak valid
                pilihan = -1; // Atur pilihan ke nilai yang tidak valid untuk melanjutkan loop
            }
        } while (pilihan != 0);
    }

    // Fungsi untuk menginisialisasi data gudang dengan nilai "0"
    public void initGudang() {
        // Inisialisasi gudang berdasarkan input pengguna
        gudang = new String[maxKategori][maxBarangPerKategori][INDEKS_STOK];

        // Looping untuk mengisi setiap elemen gudang dengan tanda "0" untuk menandakan data kosong
        for (int i = 0; i < maxKategori; i++) {
            for (int j = 0; j < maxBarangPerKategori; j++) {
                for (int k = 0; k < INDEKS_STOK; k++) {
                    gudang[i][j][k] = "0";  // Mengisi data gudang dengan tanda "0" untuk menandakan data kosong
                }
            }
        }
    }

    // Fungsi untuk menambahkan data barang ke gudang
    public void tambahDataBarang() {
        System.out.println("\nTAMBAH DATA STOK BARANG");

        // Meminta pengguna untuk memasukkan jumlah kategori
        System.out.print("Masukkan jumlah kategori: ");
        maxKategori = scanner.nextInt();

        // Meminta pengguna untuk memasukkan jumlah maksimal barang per kategori
        System.out.print("Masukkan jumlah maksimal barang per kategori: ");
        maxBarangPerKategori = scanner.nextInt();

        // Mengonsumsi karakter newline (\n) yang tersisa dari nextInt sebelumnya
        scanner.nextLine();

        // inisialisasi data untuk gudang
        initGudang();

        // Meminta pengguna untuk memasukkan kategori dan barang per kategori
        for (int i = 0; i < maxKategori; i++) {
            System.out.print("Masukkan kategori barang ke-" + (i + 1) + ": ");
            String kategori = scanner.nextLine();

            // Variabel untuk menyimpan indeks kategori yang akan diisi atau diperbarui
            int indexKategori = -1;

            // Mencari kategori yang belum terisi atau sudah ada di gudang
            for (int j = 0; j < maxKategori; j++) {
                if (gudang[j][0][0].equals("0")) {
                    // Jika kategori pada indeks j belum terisi,
                    // maka kategori tersebut dapat diisi
                    indexKategori = j;
                    break;
                } else if (gudang[j][0][0].equalsIgnoreCase(kategori)) {
                    // Jika kategori pada indeks j sudah ada dan sama dengan kategori yang dimasukkan pengguna,
                    // maka kategori tersebut sudah ada di gudang dan dapat diisi atau diperbarui
                    indexKategori = j;
                    break;
                }
            }

            // Jika indexKategori masih -1,
            // berarti gudang sudah penuh dan tidak dapat menambahkan kategori baru
            if (indexKategori == -1) {
                System.out.println("Gudang sudah penuh. Tidak dapat menambahkan kategori baru.");
                return;
            }

            for (int j = 0; j < maxBarangPerKategori; j++) {
                // Meminta pengguna untuk memasukkan nama barang
                System.out.print("Masukkan nama barang ke-" + (j + 1) + " untuk kategori " + kategori + ": ");
                String namaBarang = scanner.nextLine();

                // Mencari slot kosong untuk menambahkan data barang atau memperbarui stok barang yang sudah ada
                if (gudang[indexKategori][j][0].equals("0")) {
                    gudang[indexKategori][j][0] = kategori;
                    gudang[indexKategori][j][1] = namaBarang;

                    // Meminta pengguna untuk memasukkan stok awal barang
                    System.out.print("Masukkan stok awal barang: ");
                    int stokAwal = scanner.nextInt();
                    scanner.nextLine();
                    gudang[indexKategori][j][2] = String.valueOf(stokAwal); // Menginisialisasi stok barang dengan stok awal
                    System.out.println("Data barang berhasil ditambahkan.");
                } else if (gudang[indexKategori][j][1].equalsIgnoreCase(namaBarang)) {
                    // Jika nama barang pada indeks j sama dengan nama barang yang dimasukkan pengguna,
                    // maka barang sudah ada di gudang dan stok barang akan diperbarui
                    System.out.println("Barang sudah ada di gudang. Stok barang akan diperbarui.");
                    break;
                }
            }
        }
    }

    // Fungsi untuk mengurangi stok barang
    public void kurangiStokBarang() {
        System.out.println("\nKURANGI STOK BARANG");

        scanner.nextLine();

        // Meminta pengguna untuk memasukkan kategori barang
        System.out.print("Masukkan kategori barang: ");
        String kategori = scanner.nextLine();

        // Meminta pengguna untuk memasukkan nama barang
        System.out.print("Masukkan nama barang: ");
        String namaBarang = scanner.nextLine();

        // Variabel untuk menyimpan indeks kategori dan indeks barang yang sesuai dengan input pengguna
        int indexKategori = -1;
        int indexBarang = -1;

        // Mencari indeks kategori dan indeks barang yang sesuai dengan input pengguna
        for (int i = 0; i < maxKategori; i++) {
            for (int j = 0; j < maxBarangPerKategori; j++) {
                if (gudang[i][j][0].equalsIgnoreCase(kategori) && gudang[i][j][1].equalsIgnoreCase(namaBarang)) {
                    // Jika kategori dan nama barang pada indeks i dan j sesuai dengan input pengguna,
                    // maka simpan indeks tersebut
                    indexKategori = i;
                    indexBarang = j;
                    break;
                }
            }
        }

        // Jika indexKategori atau indexBarang masih -1,
        // berarti barang tidak ditemukan di gudang
        if (indexKategori == -1 || indexBarang == -1) {
            System.out.println("Barang tidak ditemukan di gudang.");
            return;
        }

        // Meminta pengguna untuk memasukkan jumlah stok yang akan dikurangi
        System.out.print("Masukkan jumlah stok yang akan dikurangi: ");
        int jumlahKurang = scanner.nextInt();
        scanner.nextLine();

        // Mengambil stok sekarang dari data gudang menggunakan indeks yang telah ditemukan
        int stokSekarang = Integer.parseInt(gudang[indexKategori][indexBarang][2]);
        // Jika stok sekarang dikurangi jumlah yang diminta pengguna kurang dari 0,
        // berarti stok barang tidak mencukupi, maka tampilkan pesan dan kembali dari fungsi
        if (stokSekarang - jumlahKurang < 0) {
            System.out.println("Stok barang tidak mencukupi.");
            return;
        }

        // Mengurangi stok barang dengan jumlah yang diminta pengguna
        gudang[indexKategori][indexBarang][2] = String.valueOf(stokSekarang - jumlahKurang);
        System.out.println("Stok barang berhasil dikurangi.");
    }

    // Fungsi untuk menambahkan stok barang dari barang yang sudah ada
    public void tambahStokBarang() {
        System.out.println("\nTAMBAH STOK BARANG");

        scanner.nextLine();

        // Minta input dari pengguna untuk kategori barang
        System.out.print("Masukkan kategori barang: ");
        String kategori = scanner.nextLine();

        // Minta input dari pengguna untuk nama barang
        System.out.print("Masukkan nama barang: ");
        String namaBarang = scanner.nextLine();

        // Inisialisasi indeks kategori dan indeks barang dengan nilai -1 (tidak ditemukan)
        int indexKategori = -1;
        int indexBarang = -1;

        // Mencari indeks kategori dan indeks barang yang sesuai dengan input pengguna
        for (int i = 0; i < maxKategori; i++) {
            for (int j = 0; j < maxBarangPerKategori; j++) {
                // Jika kategori dan nama barang cocok dengan data di gudang,
                // simpan indeks kategori dan indeks barang
                if (gudang[i][j][0].equalsIgnoreCase(kategori) && gudang[i][j][1].equalsIgnoreCase(namaBarang)) {
                    indexKategori = i;
                    indexBarang = j;
                    break;
                }
            }
        }

        // Jika kategori atau barang tidak ditemukan di gudang,
        // tampilkan pesan dan kembali dari fungsi
        if (indexKategori == -1 || indexBarang == -1) {
            System.out.println("Barang tidak ditemukan di gudang.");
            return;
        }

        // Minta input dari pengguna untuk jumlah stok yang akan ditambahkan
        System.out.print("Masukkan jumlah stok yang akan ditambahkan: ");
        int jumlahTambah = scanner.nextInt();
        scanner.nextLine();

        // Mengambil nilai stok saat ini dari gudang dan tambahkan dengan jumlah yang diminta
        int stokSekarang = Integer.parseInt(gudang[indexKategori][indexBarang][2]);
        gudang[indexKategori][indexBarang][2] = String.valueOf(stokSekarang + jumlahTambah);

        // Tampilkan pesan bahwa stok barang berhasil ditambahkan
        System.out.println("Stok barang berhasil ditambahkan.");
    }

    // Fungsi untuk menampilkan stok barang di gudang
    public void lihatStokBarang() {
        System.out.println("\nSTOK BARANG DI GUDANG");

        // Melakukan iterasi untuk setiap kategori
        for (int i = 0; i < maxKategori; i++) {
            System.out.println("----------------------------------------");
            // Menampilkan stok barang per kategori jika kategori tidak kosong (belum terisi)
            if (!gudang[i][0][0].equals("0")) {
                System.out.println("Kategori: " + gudang[i][0][0]);
                System.out.println();

                // Melakukan iterasi untuk setiap barang di dalam kategori
                for (int j = 0; j < maxBarangPerKategori; j++) {
                    // Menampilkan data barang jika data barang tidak kosong (belum terisi)
                    if (!gudang[i][j][0].equals("0")) {
                        System.out.println("Indeks Kategori: " + i + ", Indeks Barang: " + j);
                        System.out.println("Nama Barang: " + gudang[i][j][1] + ", Stok: " + gudang[i][j][2]);
                        System.out.println();
                    } else {
                        // Jika data barang kosong, berarti tidak ada lagi barang dalam kategori ini,
                        // maka hentikan iterasi barang
                        break;
                    }
                }
            } else {
                // Jika kategori kosong,
                // berarti tidak ada lagi kategori yang terisi,
                // maka hentikan iterasi kategori
                break;
            }
        }
        System.out.println("----------------------------------------");
    }
}