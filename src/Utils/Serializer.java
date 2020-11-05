package Utils;


import exceptions.SerializeException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    /**
     * Serialize the object in the binary file "filename"
     * @param filename the filename where the object will be saved
     * @param object the object to save
     */
    public static void serialize (String filename, Object object) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(object); // 3

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore a serialized(save) object
     * @param filename the filename where an object has been saved
     * @return the object restored
     */
    public static Object deserialize (String filename) throws SerializeException {
        Object object;
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            object = inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            throw new SerializeException("Error in deserialization of file: " + filename);
        }
        return object;
    }
}
