/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sfd;


import java.awt.HeadlessException;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import sfd.api.ApiClient;
import javax.swing.SwingWorker;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKey;
import sfd.crypto.RsaManagement;
import sfd.crypto.AesCipherService;
import javax.swing.JOptionPane;
import sfd.config.StaticUserSession;

/**
 *
 * @author HP
 */

public class FileSelectorForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FileSelectorForm.class.getName());
    private byte[] downloadedEncryptedFile;
    private String downloadedEncryptedAesKey;
    private SecretKey downloadedAesKey;
    private byte[] downloadedDecryptedFile;

    /**
     * Creates new form FileSelectorForm
     */
    public FileSelectorForm() {
        initComponents();
        txtDataDisplay.setLineWrap(true);
        txtDataDisplay.setWrapStyleWord(true);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {txtID.setText("1"); rbOpen.setSelected(true);btnEnter.doClick();
            }
        });
    }
        
    private int userId;
    private int currentFolderId = -1;

    public FileSelectorForm(int userId) {
        this();
        this.userId = userId;
    }
    
    
    
    private void downloadFile(int fileId) {

        btnEnter.setEnabled(false);
        txtDataDisplay.setText("Downloading encrypted file...");

        new SwingWorker<HttpResponse<String>, Void>() {

            @Override
            protected HttpResponse<String> doInBackground() throws Exception {

                ApiClient api = new ApiClient();

                return api.downloadEncryptedFile(userId, fileId);
            }

            @Override
            protected void done() {
                btnEnter.setEnabled(true);

                try {HttpResponse<String> response = get();

                    if (response.statusCode() == 200) {

                    String json = response.body();

                    Matcher keyMatcher = Pattern.compile("\"encryptedAesKey\"\\s*:\\s*\"([^\"]+)\"").matcher(json);

                    Matcher fileMatcher = Pattern.compile("\"encryptedFile\"\\s*:\\s*\"([^\"]+)\"").matcher(json);

                    if (!keyMatcher.find() || !fileMatcher.find()) {
                        throw new IllegalArgumentException("Invalid download response.");
                    }

                    downloadedEncryptedAesKey = keyMatcher.group(1);

                    downloadedEncryptedFile = Base64.getDecoder().decode(fileMatcher.group(1));
                                       
                    String privateKey =StaticUserSession.userDetailConfig.getPrivateKey();

                    RsaManagement rsa = new RsaManagement();

                    downloadedAesKey =rsa.dencryptAesKeyWithRsaPrivateKey(downloadedEncryptedAesKey,privateKey);

                    if (downloadedAesKey == null) {
                        throw new IllegalStateException("Failed to decrypt AES key.");
                    }
                    
                    AesCipherService aes = new AesCipherService();

                    downloadedDecryptedFile = aes.decryptFileWithAesKey(downloadedEncryptedFile,downloadedAesKey);
                    
                    Matcher nameMatcher = Pattern.compile("\"fileName\"\\s*:\\s*\"([^\"]+)\"").matcher(json);

                    String fileName;
                    if (nameMatcher.find()) {
                        fileName =new File(nameMatcher.group(1)).getName();
                    } else {
                        fileName= "downloaded_" + fileId;
                    }

                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Save Decrypted File");
                    chooser.setSelectedFile(new File(fileName));

                    int result = chooser.showSaveDialog(
                            FileSelectorForm.this);

                    if (result == JFileChooser.APPROVE_OPTION) {

                        File selectedFile = chooser.getSelectedFile();

                        if (selectedFile.exists()) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    FileSelectorForm.this,
                                    "File already exists. Overwrite it?",
                                    "Confirm Overwrite",
                                    JOptionPane.YES_NO_OPTION);

                            if (confirm != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }

                        Files.write(
                                selectedFile.toPath(),
                                downloadedDecryptedFile);

                        txtDataDisplay.setText("File downloaded and decrypted successfully! "+ "Saved to: "+ selectedFile.getAbsolutePath());
                    }
                    } else{
                            txtDataDisplay.setText("Download failed: "+ response.statusCode()+ "\n" + response.body());
                    }

                } catch (HeadlessException | IOException | IllegalArgumentException | IllegalStateException | InterruptedException | GeneralSecurityException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(FileSelectorForm.this," Download error: " + ex.getMessage());
                }
            }

        }.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        rbDownload = new javax.swing.JRadioButton();
        rbOpen = new javax.swing.JRadioButton();
        btnUploadFolder = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDataDisplay = new javax.swing.JTextArea();
        backgroundfileselectorLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FILE SELECTOR FORM ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 275, -1));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 36, -1));

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        getContentPane().add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 280, 40));

        buttonGroup1.add(rbDownload);
        rbDownload.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        rbDownload.setForeground(new java.awt.Color(255, 255, 255));
        rbDownload.setText("Download File");
        rbDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDownloadActionPerformed(evt);
            }
        });
        getContentPane().add(rbDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 240, -1));

        buttonGroup1.add(rbOpen);
        rbOpen.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        rbOpen.setForeground(new java.awt.Color(255, 255, 255));
        rbOpen.setText("Open Folder ");
        getContentPane().add(rbOpen, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 240, -1));

        btnUploadFolder.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        btnUploadFolder.setForeground(new java.awt.Color(255, 51, 102));
        btnUploadFolder.setText("Upload");
        btnUploadFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadFolderActionPerformed(evt);
            }
        });
        getContentPane().add(btnUploadFolder, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 380, 200, 50));

        btnEnter.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        btnEnter.setForeground(new java.awt.Color(255, 51, 102));
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });
        getContentPane().add(btnEnter, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 200, 50));

        txtDataDisplay.setColumns(20);
        txtDataDisplay.setRows(5);
        jScrollPane1.setViewportView(txtDataDisplay);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 500, 130));

        backgroundfileselectorLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backgroundhome.png"))); // NOI18N
        getContentPane().add(backgroundfileselectorLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void btnUploadFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadFolderActionPerformed
        if (currentFolderId == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please open your folder first.");
            return;
        }

        new UploadForm(userId, currentFolderId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnUploadFolderActionPerformed

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
    
        
        if (rbDownload.isSelected()) {
            int fileId;

            try {
                fileId = Integer.parseInt(txtID.getText().trim());

                if (fileId <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid File ID.");
                return;
            }

            downloadFile(fileId);
            return;
        }
        if (!rbOpen.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Please select Open Folder.");
            return;
        }

        int folderId;

        try {
            folderId = Integer.parseInt(txtID.getText().trim());

            if (folderId <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid Folder ID.");
            return;
        }

        btnEnter.setEnabled(false);
        txtDataDisplay.setText("Loading folder contents...");

        new SwingWorker<HttpResponse<String>, Void>() {

            @Override
            protected HttpResponse<String> doInBackground()
                    throws Exception {

                ApiClient api = new ApiClient();

                return api.getJson(
                        "/api/requestToViewFolderContents/" + folderId
                );
            }

            @Override
            protected void done() {
                btnEnter.setEnabled(true);

                try {
                    HttpResponse<String> response = get();

                    if (response.statusCode() == 200) {
                        currentFolderId = folderId;
                        StaticUserSession.userDetailConfig.setCurrentFolderId(currentFolderId);
                        txtDataDisplay.setText(response.body());
                    } else if (response.statusCode() == 404) {
                        txtDataDisplay.setText("Folder not found.");
                    } else {
                        txtDataDisplay.setText(
                                "Server returned: " + response.statusCode());
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            FileSelectorForm.this,
                            "Connection failed: " + ex.getMessage()
                    );
                }
            }
        }.execute();
    }//GEN-LAST:event_btnEnterActionPerformed

    private void rbDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDownloadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbDownloadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FileSelectorForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundfileselectorLbl;
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnUploadFolder;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbDownload;
    private javax.swing.JRadioButton rbOpen;
    private javax.swing.JTextArea txtDataDisplay;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}
