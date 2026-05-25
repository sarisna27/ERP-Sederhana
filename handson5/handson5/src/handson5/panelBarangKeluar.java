/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package handson5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class panelBarangKeluar extends javax.swing.JPanel {

    Connection conn = Handson5.getKoneksi();
    DefaultTableModel model;
    int idKeluar = 0;
    public panelBarangKeluar() {
        initComponents();
        tampilData();
        isiKategori();
        bersih();
    }
    private void tampilData() {
        model = new DefaultTableModel();
        model.addColumn("ID Keluar");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori Barang");
        model.addColumn("Quantity");
        model.addColumn("Warehouse");
        model.addColumn("Tanggal Keluar");

        tblBarangKeluar.setModel(model);

        try {
            String sql = "SELECT * FROM barang_keluar ORDER BY id_keluar DESC";
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
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
        }
    }
    private void isiKategori() {
        cmbKategoriBarang.removeAllItems();
        cmbKategoriBarang.addItem("-- Pilih Kategori --");

            try {
                String sql = "SELECT DISTINCT kategori_barang FROM barang";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    cmbKategoriBarang.addItem(rs.getString("kategori_barang"));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gagal memuat kategori: " + e.getMessage());
                }
    }
    private void bersih() {
        idKeluar = 0;
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        cmbKategoriBarang.setSelectedIndex(0);
        txtQuantity.setText("");
        txtWarehouse.setText("");
        txtKodeBarang.requestFocus();
    }
    private boolean validasiInput() {
        if (txtKodeBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode barang harus diisi!");
            return false;
        }

        if (txtNamaBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama barang harus diisi!");
            return false;
        }

        if (cmbKategoriBarang.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Kategori barang harus dipilih!");
            return false;
        }

        if (txtQuantity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity harus diisi!");
            return false;
        }

        if (txtWarehouse.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Warehouse harus diisi!");
            return false;
        }

        try {
            Integer.parseInt(txtQuantity.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity harus berupa angka!");
            return false;
        }

        return true;
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
    private void tampilBarangBerdasarkanKategori() {
        try {
            if (cmbKategoriBarang.getSelectedItem() == null) {
                return;
            }

            String kategori = cmbKategoriBarang.getSelectedItem().toString();

            if (kategori.equals("-- Pilih Kategori --")) {
                txtKodeBarang.setText("");
                txtNamaBarang.setText("");
                return;
            }

            String sql = "SELECT kode_barang, nama_barang FROM barang WHERE kategori_barang = ? LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kategori);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtKodeBarang.setText(rs.getString("kode_barang"));
                txtNamaBarang.setText(rs.getString("nama_barang"));
            } else {
                txtKodeBarang.setText("");
                txtNamaBarang.setText("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data barang: " + e.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        txtKodeBarang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbKategoriBarang = new javax.swing.JComboBox<>();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBarangKeluar = new javax.swing.JTable();
        txtWarehouse = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();

        setBackground(new java.awt.Color(248, 196, 99));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Barang Keluar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        jLabel1.setText("Kode Barang");

        jLabel2.setText("Nama Barang");

        jLabel3.setText("Kategori Barang");

        cmbKategoriBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKategoriBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKategoriBarangActionPerformed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(129, 145, 47));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(248, 196, 99));
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(129, 145, 47));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(248, 196, 99));
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(129, 145, 47));
        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(248, 196, 99));
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tblBarangKeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBarangKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBarangKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBarangKeluar);

        jLabel4.setText("Warehouse");

        jLabel5.setText("Quantitiy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKategoriBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 170, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbKategoriBarang))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtWarehouse, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // edit
        if (idKeluar == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!");
            return;
        }

        if (!validasiInput()) {
            return;
        }

        try {
            String sql = "UPDATE barang_keluar SET "
                    + "kode_barang = ?, "
                    + "nama_barang = ?, "
                    + "kategori_barang = ?, "
                    + "quantity = ?, "
                    + "warehouse = ? "
                    + "WHERE id_keluar = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtKodeBarang.getText());
            pst.setString(2, txtNamaBarang.getText());
            pst.setString(3, cmbKategoriBarang.getSelectedItem().toString());
            pst.setInt(4, Integer.parseInt(txtQuantity.getText()));
            pst.setString(5, txtWarehouse.getText());
            pst.setInt(6, idKeluar);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data barang keluar berhasil diedit!");
            tampilData();
            bersih();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // SIMPAN
         if (!validasiInput()) {
            return;
        }

        String kodeBarang = txtKodeBarang.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        int stokSekarang = cekStok(kodeBarang);

        if (quantity > stokSekarang) {
            JOptionPane.showMessageDialog(this, "Stok tidak mencukupi! Stok tersedia: " + stokSekarang);
            return;
        }

        try {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO barang_keluar "
                    + "(kode_barang, nama_barang, kategori_barang, quantity, warehouse) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kodeBarang);
            pst.setString(2, txtNamaBarang.getText());
            pst.setString(3, cmbKategoriBarang.getSelectedItem().toString());
            pst.setInt(4, quantity);
            pst.setString(5, txtWarehouse.getText());
            pst.executeUpdate();

            String updateStok = "UPDATE barang SET stok = stok - ? WHERE kode_barang = ?";
            PreparedStatement pstStok = conn.prepareStatement(updateStok);
            pstStok.setInt(1, quantity);
            pstStok.setString(2, kodeBarang);
            pstStok.executeUpdate();

            conn.commit();

            JOptionPane.showMessageDialog(this, "Data barang keluar berhasil disimpan!");
            tampilData();
            bersih();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Rollback gagal: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal mengatur koneksi: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblBarangKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBarangKeluarMouseClicked
        //tabel di klik
        int baris = tblBarangKeluar.getSelectedRow();

        if (baris != -1) {
            idKeluar = Integer.parseInt(tblBarangKeluar.getValueAt(baris, 0).toString());
            txtKodeBarang.setText(tblBarangKeluar.getValueAt(baris, 1).toString());
            txtNamaBarang.setText(tblBarangKeluar.getValueAt(baris, 2).toString());
            cmbKategoriBarang.setSelectedItem(tblBarangKeluar.getValueAt(baris, 3).toString());
            txtQuantity.setText(tblBarangKeluar.getValueAt(baris, 4).toString());
            txtWarehouse.setText(tblBarangKeluar.getValueAt(baris, 5).toString());
        }
    }//GEN-LAST:event_tblBarangKeluarMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // hapus
        if (idKeluar == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM barang_keluar WHERE id_keluar = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, idKeluar);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data barang keluar berhasil dihapus!");
                tampilData();
                bersih();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void cmbKategoriBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKategoriBarangActionPerformed
        tampilBarangBerdasarkanKategori();
    }//GEN-LAST:event_cmbKategoriBarangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbKategoriBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBarangKeluar;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtWarehouse;
    // End of variables declaration//GEN-END:variables
}
