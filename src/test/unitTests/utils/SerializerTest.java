package test.unitTests.utils;

import Utils.Serializer;
import exceptions.SerializeException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class SerializerTest {

    private final String string = "hello serializer";
    private final String filename = "serializer.test";

    @Test
    public void pointShouldSerializeAndDeserialize() {
        // Deserialization step

        Serializer.serialize(filename, string);
        FileInputStream fileInputStream =  null;
        try {
            fileInputStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, fileInputStream);

        // Deserialization step
        String restoredString = null;
        try {
            restoredString = (String) Serializer.deserialize(filename);
        } catch (SerializeException e) {
            System.out.println("Deserialization failed ...");
        }
        assertEquals(string, restoredString);
    }
}
