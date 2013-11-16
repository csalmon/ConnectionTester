import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Serializer {

	public static byte[] serialize(Message pMessage) {
		try {
			ByteArrayOutputStream lByteStream = new ByteArrayOutputStream();
			ObjectOutputStream lObjOutput = new ObjectOutputStream(lByteStream);
			lObjOutput.writeObject(pMessage);
			lObjOutput.flush();
			byte[] lByteArray = lByteStream.toByteArray();
			lObjOutput.close();
			lObjOutput.close();
			return(lByteArray);
		} catch(Exception ex) {
			ex.printStackTrace();
			return(null);
		}
	}
	
	public static Message reconstruct(byte[] pByteArray) {
		try {		
			if(null == pByteArray || pByteArray.length == 0) {
				return(null);
			}
			ByteArrayInputStream lByteStream = new ByteArrayInputStream(pByteArray);
			ObjectInputStream lObjInput = new ObjectInputStream(lByteStream);
			Message lMsg = (Message)lObjInput.readObject();			
			lObjInput.close();
			lByteStream.close();
			return(lMsg);
		} catch(Exception ex) {
			ex.printStackTrace();
			return(null);
		}
	}
}