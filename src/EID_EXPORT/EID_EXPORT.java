package EID_EXPORT;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;


public class EID_EXPORT {
	static EID_EXPORT window;
	private ArrayList<String> natidArr = new ArrayList<String>();
	private ArrayList<MyeID> myeID = new ArrayList<MyeID>();
	JLabel Lblnewlabel;
	JProgressBar progressBar;
	private JFrame EID_EXPORT;
	private JButton btnRead;
	private JButton btnValidate;
	private JTextField txtLastname;
	private JTextField txtFirstname;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EID_EXPORT window = new EID_EXPORT();
					window.EID_EXPORT.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EID_EXPORT() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		EID_EXPORT = new JFrame();
		EID_EXPORT.setTitle("BEXPO Accreditation");
		EID_EXPORT.setBounds(100, 100, 611, 509);
		EID_EXPORT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{29, 101, 104, 59, 77, 82, 81, 18, 0};
		gridBagLayout.rowHeights = new int[]{52, 20, 14, 39, 156, 16, 46, 0, 0, 24, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		EID_EXPORT.getContentPane().setLayout(gridBagLayout);
		
		lblFirstName = new JLabel("First name :");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 5;
		gbc_lblFirstName.gridy = 0;
		EID_EXPORT.getContentPane().add(lblFirstName, gbc_lblFirstName);
		
		Lblnewlabel = new JLabel("");
		GridBagConstraints gbc_Lblnewlabel = new GridBagConstraints();
		gbc_Lblnewlabel.gridwidth = 3;
		gbc_Lblnewlabel.gridheight = 4;
		gbc_Lblnewlabel.insets = new Insets(0, 0, 5, 5);
		gbc_Lblnewlabel.gridx = 1;
		gbc_Lblnewlabel.gridy = 1;
		EID_EXPORT.getContentPane().add(Lblnewlabel, gbc_Lblnewlabel);

		initPhotoPreview();
		
		txtFirstname = new JTextField();
		GridBagConstraints gbc_txtFirstname = new GridBagConstraints();
		gbc_txtFirstname.gridwidth = 2;
		gbc_txtFirstname.insets = new Insets(0, 0, 5, 5);
		gbc_txtFirstname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstname.gridx = 5;
		gbc_txtFirstname.gridy = 1;
		EID_EXPORT.getContentPane().add(txtFirstname, gbc_txtFirstname);
		txtFirstname.setColumns(10);
		
		lblLastName = new JLabel("Last name :");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 5;
		gbc_lblLastName.gridy = 2;
		EID_EXPORT.getContentPane().add(lblLastName, gbc_lblLastName);
		txtFirstname.setEnabled(false);
		
		txtLastname = new JTextField();
		txtLastname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				btnValidate.setEnabled(true);
			}
		});
		GridBagConstraints gbc_txtLastname = new GridBagConstraints();
		gbc_txtLastname.gridwidth = 2;
		gbc_txtLastname.insets = new Insets(0, 0, 5, 5);
		gbc_txtLastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastname.gridx = 5;
		gbc_txtLastname.gridy = 3;
		EID_EXPORT.getContentPane().add(txtLastname, gbc_txtLastname);
		txtLastname.setColumns(10);
		txtLastname.setEnabled(false);
		
		btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRead.setEnabled(false);
				cardReadProgressWorker pw = new cardReadProgressWorker();
				pw.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        String name = evt.getPropertyName();
                        if (name.equals("progress")) {
                        	int progress = (int) evt.getNewValue();
                        	progressBar.setValue(progress);
                        	progressBar.revalidate();
                        	progressBar.repaint();
                        } else if (name.equals("state")) {
                            SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                            switch (state) {
                                case DONE:
                                	MyeID myeIDtmp = pw.getMyeID();
                                	txtFirstname.setText(myeIDtmp.getFirstName());
                                	if( !natidArr.contains( myeIDtmp.getNat_id() ) ) {
                                		natidArr.add(myeIDtmp.getNat_id());
                                		myeID.add(myeIDtmp);                                	
                                	}
                                	txtLastname.setText(myeIDtmp.getLastName());
                                	txtFirstname.setEnabled(false);
                                	txtLastname.setEnabled(false);
                                	Lblnewlabel.setIcon(new ImageIcon(myeIDtmp.getPhoto()));
                                    btnRead.setEnabled(true);
                                    btnValidate.setEnabled(true);
                                    break;
							default:
								break;
                         }
                        }
                    }
				});
                 pw.execute();
			}
		});
		

		GridBagConstraints gbc_btnRead = new GridBagConstraints();
		gbc_btnRead.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRead.anchor = GridBagConstraints.SOUTH;
		gbc_btnRead.insets = new Insets(0, 0, 5, 5);
		gbc_btnRead.gridx = 5;
		gbc_btnRead.gridy = 8;
		EID_EXPORT.getContentPane().add(btnRead, gbc_btnRead);
		
		btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> files_to_zip = new ArrayList<String>();
				for( MyeID mye_a: myeID) {
				
					// Define filename(s)
					String filename = mye_a.getLastName() + "_"+ mye_a.getDob();
				
					// Save jpg
					String jpgfilename = System.getenv("USERPROFILE") + "\\Documents\\" + filename + ".jpg";
					if( !files_to_zip.contains(jpgfilename) ) { 
							files_to_zip.add(jpgfilename);
					}
					File outputfile = new File(jpgfilename);
					try {
						ImageIO.write(mye_a.getPhoto(), "jpg", outputfile);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						System.out.println(e2.getMessage());
					}
				}
				
			    // Fill the excell  file
				String sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
				Excell a = new Excell();
				String ExcellPath = System.getenv("USERPROFILE") + "\\Documents\\";
				String filename = "EID_EXPORT_" + sdf;
				try {					
					InputStream template = this.getClass().getResourceAsStream("/template.xlsx");
					a.DumpToExcell(ExcellPath, filename, myeID, template);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(e1.getMessage());
				}
				
				// Now zip all files
				files_to_zip.add(ExcellPath + filename + ".xlsx");
				ZipIT z = new ZipIT();
				z.ZipITNow(System.getenv("USERPROFILE") + "\\Documents\\" + filename + ".zip", files_to_zip );
				
				// Cleanup everything
				for(String outputfilename: files_to_zip) {
					File outputfile = new File(outputfilename);
					outputfile.delete();
				}			
				
				// Restore photo preview icon
				initPhotoPreview();
				
				// Grey the Validate button
				btnValidate.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnValidate = new GridBagConstraints();
		gbc_btnValidate.insets = new Insets(0, 0, 5, 5);
		gbc_btnValidate.gridx = 6;
		gbc_btnValidate.gridy = 8;
		EID_EXPORT.getContentPane().add(btnValidate, gbc_btnValidate);
		btnValidate.setEnabled(false);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.anchor = GridBagConstraints.SOUTH;
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 6;
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 9;
		EID_EXPORT.getContentPane().add(progressBar, gbc_progressBar);
	}

	private void initPhotoPreview() {
		BufferedImage defaultPhoto;
		try {
			defaultPhoto = ImageIO.read(this.getClass().getResourceAsStream("/person.png"));
			Lblnewlabel.setIcon(new ImageIcon(defaultPhoto));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
