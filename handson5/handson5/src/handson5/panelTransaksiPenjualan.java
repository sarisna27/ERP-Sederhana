/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package handson5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class panelTransaksiPenjualan extends javax.swing.JPanel {

    Connection conn = Handson5.getKoneksi();
    DefaultTableModel model;
    int totalTransaksi = 0;
    public panelTransaksiPenjualan() {
        initComponents();
        buatTabel();
        isiKodeBarang();
        nomorOtomatis();

        txtTanggalTransaksi.setText(LocalDate.now().toString());

        txtNamaBarang.setEditable(false);
        txtKategoriBarang.setEditable(false);
        txtHargaSatuan.setEditable(false);
        txtTotalBayar.setEditable(false);
        txtKembalian.setEditable(false);
        
        autoHitung();
    }
    private void buatTabel() {
        model = new DefaultTableModel();
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Total");

        tblTransaksi.setModel(model);
    }
    private void nomorOtomatis() {
        try {
            String sql = "SELECT COUNT(*) AS jumlah FROM penjualan";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                int jumlah = rs.getInt("jumlah") + 1;
                txtNoTransaksi.setText("TRX" + String.format("%03d", jumlah));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat nomor transaksi: " + e.getMessage());
        }
    }
    private void isiKodeBarang() {
        cmbKodeBarang.removeAllItems();
        cmbKodeBarang.addItem("-- Pilih Kode Barang --");

        try {
            String sql = "SELECT kode_barang FROM barang";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                cmbKodeBarang.addItem(rs.getString("kode_barang"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat kode barang: " + e.getMessage());
        }
    }
    private void tampilBarang() {
        try {
            if (cmbKodeBarang.getSelectedItem() == null) {
                return;
            }

            String kodeBarang = cmbKodeBarang.getSelectedItem().toString();

            if (kodeBarang.equals("-- Pilih Kode Barang --")) {
                txtNamaBarang.setText("");
                txtKategoriBarang.setText("");
                txtHargaSatuan.setText("");
                return;
            }

            String sql = "SELECT nama_barang, kategori_barang, harga_satuan FROM barang WHERE kode_barang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kodeBarang);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtNamaBarang.setText(rs.getString("nama_barang"));
                txtKategoriBarang.setText(rs.getString("kategori_barang"));
                txtHargaSatuan.setText(rs.getString("harga_satuan"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data barang: " + e.getMessage());
        }
    }
    private int cekStok(String kodeBarang) {
        int stok = 0;

        try {
            String sql = "SELECT stok FROM barang WHERE kode_barang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kodeBarang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                stok = rs.getInt("stok");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cek stok: " + e.getMessage());
        }

        return stok;
    }
    private void bersih() {
        txtNoTransaksi.setText("");
        txtTanggalTransaksi.setText("");
        txtNamaBarang.setText("");
        txtKategoriBarang.setText("");
        txtHargaSatuan.setText("");
        txtJumlah.setText("");
        txtTotalBayar.setText("");
        txtBayaran.setText("");
        txtKembalian.setText("");
        txtNamaPelanggan.setText("");

        if (cmbKodeBarang.getItemCount() > 0) {
            cmbKodeBarang.setSelectedIndex(0);
        }

        nomorOtomatis();
        txtTanggalTransaksi.setText(java.time.LocalDate.now().toString());
    }
    private String ambilKategoriBarang(String kodeBarang) {
        String kategori = "";

        try {
            String sql = "SELECT kategori_barang FROM barang WHERE kode_barang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kodeBarang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                kategori = rs.getString("kategori_barang");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil kategori: " + e.getMessage());
        }

        return kategori;
    }
    //hitung otomatis
    private int ambilAngka(String teks) {
        if (teks == null || teks.trim().isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(teks.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void hitungTotalBayar() {
        int harga = ambilAngka(txtHargaSatuan.getText());
        int jumlah = ambilAngka(txtJumlah.getText());

        int total = harga * jumlah;
        txtTotalBayar.setText(String.valueOf(total));

        hitungKembalian();
    }

    private void hitungKembalian() {
        int total = ambilAngka(txtTotalBayar.getText());
        int bayaran = ambilAngka(txtBayaran.getText());

        int kembalian = bayaran - total;
        txtKembalian.setText(String.valueOf(kembalian));
    }
    
    private void autoHitung() {
        txtJumlah.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                hitungTotalBayar();
            }

            public void removeUpdate(DocumentEvent e) {
                hitungTotalBayar();
            }

            public void changedUpdate(DocumentEvent e) {
                hitungTotalBayar();
            }
        });

        txtBayaran.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                hitungKembalian();
            }

            public void removeUpdate(DocumentEvent e) {
                hitungKembalian();
            }

            public void changedUpdate(DocumentEvent e) {
                hitungKembalian();
            }
        });
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNoTransaksi = new javax.swing.JTextField();
        txtTanggalTransaksi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbKodeBarang = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtKategoriBarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHargaSatuan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTotalBayar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtBayaran = new javax.swing.JTextField();
        Kembalian = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JTextField();
        Kembalian1 = new javax.swing.JLabel();
        txtNamaPelanggan = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        btnSimpan1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(248, 196, 99));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transaksi Penjualan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N
        setForeground(new java.awt.Color(248, 196, 99));

        jLabel1.setText("No Transaksi");

        jLabel2.setText("Tanggal Transaksi");

        jLabel3.setText("Kode Barang");

        cmbKodeBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKodeBarangActionPerformed(evt);
            }
        });

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTransaksi);

        jLabel4.setText("Nama Barang");

        jLabel5.setText("Kategori Barang");

        jLabel6.setText("Harga Satuan");

        jLabel7.setText("Jumlah");

        jLabel8.setText("Total Bayar");

        jLabel9.setText("Bayaran");

        Kembalian.setText("Kembalian");

        Kembalian1.setText("Nama Pelanggan");

        btnSimpan.setBackground(new java.awt.Color(129, 145, 47));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(248, 196, 99));
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(129, 145, 47));
        btnCetak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(248, 196, 99));
        btnCetak.setText("CETAK STRUK");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        btnSimpan1.setBackground(new java.awt.Color(129, 145, 47));
        btnSimpan1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnSimpan1.setForeground(new java.awt.Color(248, 196, 99));
        btnSimpan1.setText("TAMBAH BARANG");
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtHargaSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtKategoriBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtBayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(Kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Kembalian1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNoTransaksi)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(txtTanggalTransaksi))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtTanggalTransaksi)
                    .addComponent(cmbKodeBarang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaBarang))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKategoriBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(btnSimpan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalBayar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHargaSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(txtJumlah)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtKembalian)
                                .addGap(8, 8, 8)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Kembalian1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKodeBarangActionPerformed
        tampilBarang();
    }//GEN-LAST:event_cmbKodeBarangActionPerformed

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        //Tambah Barang
        if (cmbKodeBarang.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Pilih kode barang terlebih dahulu!");
            return;
        }

        if (txtJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah harus diisi!");
            txtJumlah.requestFocus();
            return;
        }

        try {
            String kodeBarang = cmbKodeBarang.getSelectedItem().toString();
            String namaBarang = txtNamaBarang.getText();
            int harga = Integer.parseInt(txtHargaSatuan.getText());
            int jumlah = Integer.parseInt(txtJumlah.getText());
            int stok = cekStok(kodeBarang);

            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!");
                return;
            }

            if (jumlah > stok) {
                JOptionPane.showMessageDialog(this, "Stok tidak cukup! Stok tersedia: " + stok);
                return;
            }

            int total = harga * jumlah;

            model.addRow(new Object[]{
                kodeBarang,
                namaBarang,
                harga,
                jumlah,
                total
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan barang: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // SIMPAN
        if (tblTransaksi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Belum ada barang dalam transaksi!");
            return;
        }

        if (txtBayaran.getText().trim().isEmpty() || txtKembalian.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hitung pembayaran terlebih dahulu!");
            return;
        }

        if (txtNamaPelanggan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama pelanggan harus diisi!");
            txtNamaPelanggan.requestFocus();
            return;
        }

        try {
            conn.setAutoCommit(false);

            String sqlPenjualan = "INSERT INTO penjualan "
                    + "(no_transaksi, tanggal_transaksi, nama_pelanggan, total_transaksi, bayaran, kembalian) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstPenjualan = conn.prepareStatement(sqlPenjualan);
            pstPenjualan.setString(1, txtNoTransaksi.getText());
            pstPenjualan.setString(2, txtTanggalTransaksi.getText());
            pstPenjualan.setString(3, txtNamaPelanggan.getText());
            pstPenjualan.setInt(4, Integer.parseInt(txtTotalBayar.getText()));
            pstPenjualan.setInt(5, Integer.parseInt(txtBayaran.getText()));
            pstPenjualan.setInt(6, Integer.parseInt(txtKembalian.getText()));
            pstPenjualan.executeUpdate();

            for (int i = 0; i < tblTransaksi.getRowCount(); i++) {
                String kodeBarang = tblTransaksi.getValueAt(i, 0).toString();
                String namaBarang = tblTransaksi.getValueAt(i, 1).toString();
                int harga = Integer.parseInt(tblTransaksi.getValueAt(i, 2).toString());
                int jumlah = Integer.parseInt(tblTransaksi.getValueAt(i, 3).toString());
                int total = Integer.parseInt(tblTransaksi.getValueAt(i, 4).toString());

                String sqlDetail = "INSERT INTO detail_penjualan "
                        + "(no_transaksi, kode_barang, nama_barang, kategori_barang, harga_satuan, jumlah, total_bayar) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstDetail = conn.prepareStatement(sqlDetail);
                pstDetail.setString(1, txtNoTransaksi.getText());
                pstDetail.setString(2, kodeBarang);
                pstDetail.setString(3, namaBarang);
                pstDetail.setString(4, ambilKategoriBarang(kodeBarang));
                pstDetail.setInt(5, harga);
                pstDetail.setInt(6, jumlah);
                pstDetail.setInt(7, total);
                pstDetail.executeUpdate();

                String sqlStok = "UPDATE barang SET stok = stok - ? WHERE kode_barang = ?";
                PreparedStatement pstStok = conn.prepareStatement(sqlStok);
                pstStok.setInt(1, jumlah);
                pstStok.setString(2, kodeBarang);
                pstStok.executeUpdate();
            }

            conn.commit();

            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!");
            

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Rollback gagal: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + e.getMessage());

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal mengatur koneksi: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // CETAK
         if (tblTransaksi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Belum ada transaksi untuk dicetak!");
            return;
        }

        String struk = "PT. SUMBER BANGUNAN JAYA\n";
        struk += "============================\n";
        struk += "No Transaksi : " + txtNoTransaksi.getText() + "\n";
        struk += "Tanggal      : " + txtTanggalTransaksi.getText() + "\n";
        struk += "Pelanggan    : " + txtNamaPelanggan.getText() + "\n";
        struk += "============================\n";

        for (int i = 0; i < tblTransaksi.getRowCount(); i++) {
            struk += tblTransaksi.getValueAt(i, 1).toString() + "\n";
            struk += tblTransaksi.getValueAt(i, 3).toString() + " x "
                    + tblTransaksi.getValueAt(i, 2).toString()
                    + " = " + tblTransaksi.getValueAt(i, 4).toString() + "\n";
        }

        struk += "============================\n";
        struk += "Total     : " + txtTotalBayar.getText() + "\n";
        struk += "Bayar     : " + txtBayaran.getText() + "\n";
        struk += "Kembalian : " + txtKembalian.getText() + "\n";
        struk += "============================\n";
        struk += "Terima kasih.";

        JOptionPane.showMessageDialog(this, struk);
        
        bersih();
    }//GEN-LAST:event_btnCetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Kembalian;
    private javax.swing.JLabel Kembalian1;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpan1;
    private javax.swing.JComboBox<String> cmbKodeBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtBayaran;
    private javax.swing.JTextField txtHargaSatuan;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKategoriBarang;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JTextField txtTanggalTransaksi;
    private javax.swing.JTextField txtTotalBayar;
    // End of variables declaration//GEN-END:variables
}
