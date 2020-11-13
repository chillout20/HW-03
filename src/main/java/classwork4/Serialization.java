package classwork4;

import java.io.*;
import java.util.Scanner;

public class Serialization {
    // Part 1: create a class Person
    public static class Person implements Serializable {
        private String name;
        private Integer age;
        private String gender;
        private Boolean hasKids;
        private String phone;

        Person(String name, int age, String gender, boolean kids, String phone) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.hasKids = kids;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "Person{"
                    + "name='" + name + '\''
                    + ", age=" + age
                    + ", gender='" + gender + '\''
                    + ", hasKids=" + hasKids
                    + ", phone='" + phone + '\''
                    + '}';
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static void writeObjectToFile(Object obj, String fileName) throws IOException {
        FileOutputStream fout = new FileOutputStream(fileName);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        ObjectOutputStream dout = new ObjectOutputStream(bout);
        dout.writeObject(obj);
        dout.flush();
    }

    public static Object readObjectFromFile(String fileName)
            throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(fileName);
        BufferedInputStream bin = new BufferedInputStream(fin);
        ObjectInputStream din = new ObjectInputStream(bin);
        return din.readObject();
    }

    public static void testSerialization() throws IOException, ClassNotFoundException {
        String i = "Goodbye World";
        System.out.println(i.toString());

        byte[] bArray = serialize(i);
        System.out.println(bArray);

        Object o = deserialize(bArray);
        if (o instanceof Integer) {
            System.out.println((Integer) o);
        } else if (o instanceof String) {
            System.out.println((String) o);
        }

        writeObjectToFile(i, "hello.txt");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Part 2: create an instance of class Person
        //Person p = new Person("Vasilii", 40, "Male", false, "123-456-789");
        Person p = new Person("Ahmed", 26, "Male", false, "987-654-321");

        // Part 3: ask user to type a file name
        System.out.print("File name to write: ");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();

        // Part 4: write the object to file
        writeObjectToFile(p, filename);

        // Part 5: ask user to type another file name
        System.out.print("File name to read: ");
        filename = scanner.nextLine();

        // Part 6: read an object form file
        Object o = readObjectFromFile(filename);

        // Part 7: print result of reading
        if (o instanceof Person) System.out.println(p.toString());
    }
}
