
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Component;


/**
 * 
 * @author Harjodh Singh, Simon Photitay
 * Email: sing4262@mylaurier.ca
 * @version 1.1
 * Date Feb 1st 2018
 * 
 * This GUI is built to provide the client an easy way to connect to the bibliography server
 * Error handling is done on the inputs after connect is pressed. 
 * If failed, client receives a warning
 * This window is resizable, but contents are not.
 */

@SuppressWarnings("serial")
public class ConnectGUIv2 extends JFrame {
	BackendClient c = new BackendClient(); //the client object
	int portNum = 0;
	String ipAddress;
	
	private JPanel connectPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JLabel errorLabel;
	private JLabel requestLabel;
	private JButton sendButton;
	private JLabel serverReplyLabel;
	private JCheckBox bibtexCheckBox;
	private JTextField serverReplyTextField;
	private JLabel isbnLabel;
	private JLabel portLabel;
	private JLabel ipLabel;
	private JTextField isbnTextField;
	private JButton disconnectButton;
	private JTextArea requestTextField;

	/**
	 * Launch GUI and invoke later for thread safety
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectGUIv2 frame = new ConnectGUIv2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame and add components 
	 */
	public ConnectGUIv2() {
		setTitle("Client Side - Bib Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 503);
		connectPane = new JPanel();
		connectPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(connectPane);
	
		//Initialize labels using BoxLayout Manager
		ipLabel = new JLabel("IP Address: ");
		portLabel = new JLabel("Port Number: ");
		portLabel.setHorizontalAlignment(SwingConstants.LEFT);
		isbnLabel = new JLabel("ISBN: ");
		isbnLabel.setHorizontalAlignment(SwingConstants.LEFT);
		requestLabel = new JLabel("Request to Server:");
		serverReplyLabel = new JLabel("Server Reply");
		serverReplyLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Initialize text fields
		isbnTextField = new JTextField();
		isbnTextField.setHorizontalAlignment(SwingConstants.LEFT);
		isbnTextField.setColumns(10);
		serverReplyTextField = new JTextField();
		serverReplyTextField.setEditable(false);
		serverReplyTextField.setColumns(10);
		portTextField = new JTextField();
		portTextField.setColumns(10);
		ipTextField = new JTextField();
		ipTextField.setColumns(10);
		requestTextField = new JTextArea();
		requestTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
		requestTextField.setLineWrap(true);
		
		//Buttons and boxes
		bibtexCheckBox = new JCheckBox("Bibtex Format");		
		sendButton = new JButton("Send");
		sendButton.setEnabled(false); //enabled once connection successful
		sendButton.setBackground(Color.LIGHT_GRAY);
		sendButton.setHorizontalAlignment(SwingConstants.LEFT);
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setEnabled(false);
		disconnectButton.setBackground(Color.LIGHT_GRAY);
		disconnectButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		/*
		 * Set up the connect/send button action listener before adding all contents
		 */
		
		errorLabel = new JLabel("");//use this to display error messages to user, hidden otherwise.
		
		JButton connectButton = new JButton("Connect");
		connectButton.setHorizontalAlignment(SwingConstants.LEFT);
		connectButton.setBackground(Color.LIGHT_GRAY);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				try {
					portNum = Integer.parseInt(portTextField.getText());
					ipAddress = ipTextField.getText();
					if(!c.connect(ipAddress,portNum)) { //addresses back end
						throw new Exception("err");
					}
					//Disable ConnectButton - Enable Disconnect
					connectButton.setEnabled(false);
					disconnectButton.setEnabled(true);
					sendButton.setEnabled(true);
				}catch(Exception err) {
					errorLabel.setText("Error connecting to Port...Try again.");
				}
			}
		});
		
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent send) {
				//validate ISBN first
				if(isbnTextField.getText() != "") {
					if(!isbn13Validate(isbnTextField.getText())){
						errorLabel.setText("ISBN Invalid!");
					}else {
						if(!(c.send(isbnTextField.getText(),requestTextField))) {
							//return says unable to send  
							errorLabel.setText("Request Not Sent...Error. ");
						}
					}
				}else {
					c.send(requestTextField);
				}
			}
		});
		
		
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent disconnect) {
				//call back end
				c.disconnect();
			}
		});
		
		
		//Add contents to panel in order required to display
		connectPane.setLayout(new BoxLayout(connectPane, BoxLayout.X_AXIS));
		connectPane.add(portLabel);
		connectPane.add(portTextField);
		connectPane.add(ipLabel);
		connectPane.add(ipTextField);
		connectPane.add(errorLabel);
		connectPane.add(connectButton);
		connectPane.setLayout(new BoxLayout(connectPane, BoxLayout.Y_AXIS));
		connectPane.add(disconnectButton);
		connectPane.add(isbnLabel);
		connectPane.add(isbnTextField);
		connectPane.add(requestLabel);
		connectPane.add(requestTextField);
		connectPane.add(bibtexCheckBox);
		connectPane.add(sendButton);
		connectPane.add(serverReplyLabel);
		connectPane.add(serverReplyTextField);
	}
	
	
	//for checking ISBN is valid or not, uses 13 digit variation
	public boolean isbn13Validate(String isbn) {
		if(isbn==null) {
			return false;
		}else if(isbn.length() == 13) {
			int token,i,checkDigit = 10,total = 0 ,flag = 0; //flag 0 means last multiplied by 1
			for(i = 0; i<12;i++) {
				token = Integer.parseInt(isbn.substring(i,i+1));
				if(i%2 == 0) {
					if(flag == 0) {
						total = total + token*1;
						flag = 1;
					}else {
						total = total + (token*3);
					}
				}
				checkDigit = checkDigit - (total%10); if(checkDigit == 10) {checkDigit = 0;}		
				
			}
			return(checkDigit == Integer.parseInt(isbn.substring(12)));
		}else {
			return false;
		}
	}
	
	
}
