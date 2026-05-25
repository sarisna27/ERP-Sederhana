/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package handson5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class panelLaporan extends javax.swing.JPanel {

    Connection conn = Handson5.getKoneksi();
    DefaultTableModel model;
    public panelLaporan() {
        initComponents();
        tampilLaporanDataBarang();
    }
    private void tampilLaporanDataBarang() {
        model = new DefaultTableModel();
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Harga Satuan");
        model.addColumn("Stok");

        tblLaporan.setModel(model);

        try {
            String sql = "SELECT * FROM barang ORDER BY kode_barang ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori_barang"),
                    rs.getInt("harga_satuan"),
                    rs.getInt("stok")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan data barang: " + e.getMessage());
        }
    }
    private void tampilLaporanBarangMasuk() {
        model = new DefaultTableModel();
        model.addColumn("ID Masuk");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Quantity");
        model.addColumn("Supplier/Vendor");
        model.addColumn("Tanggal Masuk");

        tblLaporan.setModel(model);

        try {
            String sql = "SELECT * FROM barang_masuk ORDER BY tanggal_masuk DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_masuk"),
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori_barang"),
                    rs.getInt("quantity"),
                    rs.getString("supplier_vendor"),
                    rs.getDate("tanggal_masuk")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan barang masuk: " + e.getMessage());
        }
    }
    private void tampilLaporanBarangKeluar() {
        model = new DefaultTableModel();
        model.addColumn("ID Keluar");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Quantity");
        model.addColumn("Warehouse");
        model.addColumn("Tanggal Keluar");

        tblLaporan.setModel(model);

        try {
            String sql = "SELECT * FROM barang_keluar ORDER BY tanggal_keluar DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_keluar"),
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori_barang"),
                    rs.getInt("quantity"),
                    rs.getString("warehouse"),
                    rs.getDate("tanggal_keluar")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan barang keluar: " + e.getMessage());
        }
    }
    private void tampilLaporanPenjualan() {
        model = new DefaultTableModel();
        model.addColumn("No Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Total Bayar");

        tblLaporan.setModel(model);

        try {
            String sql = "SELECT p.no_transaksi, p.tanggal_transaksi, p.nama_pelanggan, "
                    + "d.kode_barang, d.nama_barang, d.harga_satuan, d.jumlah, d.total_bayar "
                    + "FROM penjualan p "
                    + "JOIN detail_penjualan d ON p.no_transaksi = d.no_transaksi "
                    + "ORDER BY p.tanggal_transaksi DESC";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_transaksi"),
                    rs.getDate("tanggal_transaksi"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getInt("harga_satuan"),
                    rs.getInt("jumlah"),
                    rs.getInt("total_bayar")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan penjualan: " + e.getMessage());
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        btnLaporanDataBarang = new javax.swing.JButton();
        btnLaporanBarangMasuk = new javax.swing.JButton();
        btnLaporanBarangKeluar = new javax.swing.JButton();
        btnLaporanPenjualan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 250, 240));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Laporan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        tblLaporan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblLaporan);

        btnLaporanDataBarang.setBackground(new java.awt.Color(48, 109, 41));
        btnLaporanDataBarang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanDataBarang.setForeground(new java.awt.Color(231, 225, 177));
        btnLaporanDataBarang.setText("Laporan Data Barang");
        btnLaporanDataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanDataBarangActionPerformed(evt);
            }
        });

        btnLaporanBarangMasuk.setBackground(new java.awt.Color(48, 109, 41));
        btnLaporanBarangMasuk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanBarangMasuk.setForeground(new java.awt.Color(231, 225, 177));
        btnLaporanBarangMasuk.setText("Laporan Barang Masuk");
        btnLaporanBarangMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanBarangMasukActionPerformed(evt);
            }
        });

        btnLaporanBarangKeluar.setBackground(new java.awt.Color(48, 109, 41));
        btnLaporanBarangKeluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanBarangKeluar.setForeground(new java.awt.Color(231, 225, 177));
        btnLaporanBarangKeluar.setText("Laporan Barang Keluar");
        btnLaporanBarangKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanBarangKeluarActionPerformed(evt);
            }
        });

        btnLaporanPenjualan.setBackground(new java.awt.Color(48, 109, 41));
        btnLaporanPenjualan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanPenjualan.setForeground(new java.awt.Color(231, 225, 177));
        btnLaporanPenjualan.setText("Laporan Penjualan");
        btnLaporanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanPenjualanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLaporanDataBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLaporanBarangMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLaporanBarangKeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLaporanPenjualan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnLaporanDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporanBarangMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporanBarangKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporanPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLaporanDataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanDataBarangActionPerformed
        // TODO add your handling code here:
        tampilLaporanDataBarang();
    }//GEN-LAST:event_btnLaporanDataBarangActionPerformed

    private void btnLaporanBarangMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanBarangMasukActionPerformed
        // TODO add your handling code here:
        tampilLaporanBarangMasuk();
    }//GEN-LAST:event_btnLaporanBarangMasukActionPerformed

    private void btnLaporanBarangKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanBarangKeluarActionPerformed
        // TODO add your handling code here:
        tampilLaporanBarangKeluar();
    }//GEN-LAST:event_btnLaporanBarangKeluarActionPerformed

    private void btnLaporanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanPenjualanActionPerformed
        // TODO add your handling code here:
        tampilLaporanPenjualan();
    }//GEN-LAST:event_btnLaporanPenjualanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaporanBarangKeluar;
    private javax.swing.JButton btnLaporanBarangMasuk;
    private javax.swing.JButton btnLaporanDataBarang;
    private javax.swing.JButton btnLaporanPenjualan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLaporan;
    // End of variables declaration//GEN-END:variables
}
