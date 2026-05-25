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

public class panelBarangMasuk extends javax.swing.JPanel {

    Connection conn = Handson5.getKoneksi();
    DefaultTableModel model;
    int idMasuk = 0;
    public panelBarangMasuk() {
        initComponents();
        tampilData();
        isiKategori();
        bersih();
    }
    private void tampilData() {
    model = new DefaultTableModel();
    model.addColumn("ID Masuk");
    model.addColumn("Kode Barang");
    model.addColumn("Nama Barang");
    model.addColumn("Kategori Barang");
    model.addColumn("Quantity");
    model.addColumn("Supplier/Vendor");
    model.addColumn("Tanggal Masuk");

    tblBarangMasuk.setModel(model);

        try {
            String sql = "SELECT * FROM barang_masuk ORDER BY id_masuk DESC";
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
         idMasuk = 0;
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        cmbKategoriBarang.setSelectedIndex(0);
        txtQuantity.setText("");
        txtSupplierVendor.setText("");

        txtKodeBarang.requestFocus();
    }
    private boolean validasiInput() {
        if (txtKodeBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode barang harus diisi!");
            txtKodeBarang.requestFocus();
            return false;
        }

        if (txtNamaBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama barang harus diisi!");
            txtNamaBarang.requestFocus();
            return false;
        }

        if (cmbKategoriBarang.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Kategori barang harus dipilih!");
            cmbKategoriBarang.requestFocus();
            return false;
        }

        if (txtQuantity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity harus diisi!");
            txtQuantity.requestFocus();
            return false;
        }

        if (txtSupplierVendor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Supplier/Vendor harus diisi!");
            txtSupplierVendor.requestFocus();
            return false;
        }

        try {
            Integer.parseInt(txtQuantity.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity harus berupa angka!");
            txtQuantity.requestFocus();
            return false;
        }

        return true;
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

        jPanel3 = new javax.swing.JPanel();
        txtKodeBarang = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbKategoriBarang = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtSupplierVendor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBarangMasuk = new javax.swing.JTable();
        txtSimpan = new javax.swing.JButton();
        txtSimpan1 = new javax.swing.JButton();
        txtSimpan2 = new javax.swing.JButton();

        jPanel3.setBackground(new java.awt.Color(255, 250, 240));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Barang Masuk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 18))); // NOI18N

        jLabel1.setText("Kode Barang");

        jLabel2.setText("Nama Barang");

        jLabel3.setText("Kategori Barang");

        cmbKategoriBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKategoriBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKategoriBarangActionPerformed(evt);
            }
        });

        jLabel4.setText("Supplier/Vendor");

        jLabel5.setText("Quantitiy");

        tblBarangMasuk.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBarangMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBarangMasukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBarangMasuk);

        txtSimpan.setBackground(new java.awt.Color(129, 145, 47));
        txtSimpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSimpan.setForeground(new java.awt.Color(248, 196, 99));
        txtSimpan.setText("SIMPAN");
        txtSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSimpanActionPerformed(evt);
            }
        });

        txtSimpan1.setBackground(new java.awt.Color(129, 145, 47));
        txtSimpan1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSimpan1.setForeground(new java.awt.Color(248, 196, 99));
        txtSimpan1.setText("EDIT");
        txtSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSimpan1ActionPerformed(evt);
            }
        });

        txtSimpan2.setBackground(new java.awt.Color(129, 145, 47));
        txtSimpan2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSimpan2.setForeground(new java.awt.Color(248, 196, 99));
        txtSimpan2.setText("HAPUS");
        txtSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSimpan2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSupplierVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKategoriBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 170, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbKategoriBarang))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSupplierVendor, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSimpan1ActionPerformed
        // TOMBOL EDIT
        if (idMasuk == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit dari tabel!");
            return;
        }

        if (!validasiInput()) {
            return;
        }

        try {
            String sql = "UPDATE barang_masuk SET "
                    + "kode_barang = ?, "
                    + "nama_barang = ?, "
                    + "kategori_barang = ?, "
                    + "quantity = ?, "
                    + "supplier_vendor = ? "
                    + "WHERE id_masuk = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtKodeBarang.getText());
            pst.setString(2, txtNamaBarang.getText());
            pst.setString(3, cmbKategoriBarang.getSelectedItem().toString());
            pst.setInt(4, Integer.parseInt(txtQuantity.getText()));
            pst.setString(5, txtSupplierVendor.getText());
            pst.setInt(6, idMasuk);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data barang masuk berhasil diedit!");
            tampilData();
            bersih();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengedit data: " + e.getMessage());
        }
    }//GEN-LAST:event_txtSimpan1ActionPerformed

    private void txtSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSimpanActionPerformed
        // TOMBOL SIMPAN
         if (!validasiInput()) {
            return;
        }

        try {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO barang_masuk "
                    + "(kode_barang, nama_barang, kategori_barang, quantity, supplier_vendor) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtKodeBarang.getText());
            pst.setString(2, txtNamaBarang.getText());
            pst.setString(3, cmbKategoriBarang.getSelectedItem().toString());
            pst.setInt(4, Integer.parseInt(txtQuantity.getText()));
            pst.setString(5, txtSupplierVendor.getText());
            pst.executeUpdate();

            String updateStok = "UPDATE barang SET stok = stok + ? WHERE kode_barang = ?";
            PreparedStatement pstStok = conn.prepareStatement(updateStok);
            pstStok.setInt(1, Integer.parseInt(txtQuantity.getText()));
            pstStok.setString(2, txtKodeBarang.getText());
            pstStok.executeUpdate();

            conn.commit();

            JOptionPane.showMessageDialog(this, "Data barang masuk berhasil disimpan!");
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
    }//GEN-LAST:event_txtSimpanActionPerformed

    private void tblBarangMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBarangMasukMouseClicked
        // KETIKA TABEL DI KLIK
        int baris = tblBarangMasuk.getSelectedRow();

        if (baris != -1) {
            idMasuk = Integer.parseInt(tblBarangMasuk.getValueAt(baris, 0).toString());
            txtKodeBarang.setText(tblBarangMasuk.getValueAt(baris, 1).toString());
            txtNamaBarang.setText(tblBarangMasuk.getValueAt(baris, 2).toString());
            cmbKategoriBarang.setSelectedItem(tblBarangMasuk.getValueAt(baris, 3).toString());
            txtQuantity.setText(tblBarangMasuk.getValueAt(baris, 4).toString());
            txtSupplierVendor.setText(tblBarangMasuk.getValueAt(baris, 5).toString());
        }
    }//GEN-LAST:event_tblBarangMasukMouseClicked

    private void txtSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSimpan2ActionPerformed
        // TOMBOL HAPUS
         if (idMasuk == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus dari tabel!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM barang_masuk WHERE id_masuk = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, idMasuk);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data barang masuk berhasil dihapus!");
                tampilData();
                bersih();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_txtSimpan2ActionPerformed

    private void cmbKategoriBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKategoriBarangActionPerformed
        tampilBarangBerdasarkanKategori();
    }//GEN-LAST:event_cmbKategoriBarangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbKategoriBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBarangMasuk;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JButton txtSimpan;
    private javax.swing.JButton txtSimpan1;
    private javax.swing.JButton txtSimpan2;
    private javax.swing.JTextField txtSupplierVendor;
    // End of variables declaration//GEN-END:variables

    
}
