package EID_EXPORT;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.smartcardio.CardException;
import javax.swing.SwingWorker;

import be.fedict.commons.eid.client.BeIDCard;
import be.fedict.commons.eid.client.BeIDCards;
import be.fedict.commons.eid.client.CancelledException;
import be.fedict.commons.eid.client.FileType;
import be.fedict.commons.eid.consumer.Gender;
import be.fedict.commons.eid.consumer.Identity;
import be.fedict.commons.eid.consumer.tlv.TlvParser;

    @SuppressWarnings("restriction")
	public class cardReadProgressWorker extends SwingWorker<Object, Object> {
    	
    	public MyeID MyeID = new MyeID();

        @Override
        protected Object doInBackground() throws Exception, InterruptedException {
        		setProgress(10);
        		
        		// -------------------------------------------------------------------------------------------------------
        		// instantiate a BeIDCardManager with default settings (no logging)
        		// -------------------------------------------------------------------------------------------------------
        		final BeIDCards beIDCards = new BeIDCards();
        		// -------------------------------------------------------------------------------------------------------
        		// ask it for all CardTerminals that currently contain BeID cards (which
        		// may be none at all)
        		// -------------------------------------------------------------------------------------------------------
        		final Set<BeIDCard> cards = beIDCards.getAllBeIDCards();
        		System.out.println(cards.size() + " BeID Cards found");
        		// -------------------------------------------------------------------------------------------------------
        		// ask it for one BeID Card. This may block and interact with the user.
        		// -------------------------------------------------------------------------------------------------------

        		try {
        			final BeIDCard card = beIDCards.getOneBeIDCard();

        			// -------------------------------------------------------------------------------------------------------
        			// read identity file, decode it and print something containing card
        			// -------------------------------------------------------------------------------------------------------
        			try {
        				// Reading...
        				final byte[] idData = card.readFile(FileType.Identity);
        				System.out.println("card file readed");
        				setProgress(40);
        				
        				// Parsing...
        				final Identity id = TlvParser.parse(idData, Identity.class);
        				setProgress(45);

        				
        				// Photo...
        				byte[] imgByte = card.readFile(FileType.Photo);
        				setProgress(65);
        				
        				// Displaying...
        				BufferedImage photo = ImageIO.read(new ByteArrayInputStream(imgByte));
        				setProgress(70);
        				
        				// Save data into variable
          				String firstName = id.firstName;
        				String lastName = id.name;
        				String nat_id = id.nationalNumber;
        				String dob = format(id.dateOfBirth);
        				String gender;
        			    
        				if(id.gender == Gender.MALE) {
        					gender = "MALE";
        				} else {
        					gender = "FEMALE"; 
        				}
        				
        				setProgress(100);
        				
        				MyeID.setFirstName(firstName);
        				MyeID.setLastName(lastName);
        				MyeID.setNat_id(nat_id);
        				MyeID.setDob(dob); 
        				MyeID.setGender(gender);
        				MyeID.setPhoto(photo);
        				
        			} catch (final CardException cex) {
        				// TODO Auto-generated catch block
        				cex.printStackTrace();
        			} catch (final IOException iox) {
        				// TODO Auto-generated catch block
        				iox.printStackTrace();
        			}

        			// -------------------------------------------------------------------------------------------------------
        			// wait for removal of the card we've just read. (Not working???)
        			// -------------------------------------------------------------------------------------------------------
        			//System.out.println("Please remove the card now.");
        			//beIDCards.waitUntilCardRemoved(card);
        			//System.out.println("Thank you.");
        		} catch (final CancelledException cex) {
        			System.out.println("Cancelled By User");
        		}

            return null;
        }
        
        public MyeID getMyeID() {
			return MyeID;
        }
        
        public static String format(GregorianCalendar calendar){
    	    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    	    fmt.setCalendar(calendar);
    	    String dateFormatted = fmt.format(calendar.getTime());
    	    return dateFormatted;
    	}
    }

    