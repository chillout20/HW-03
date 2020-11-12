package hw3;

import java.util.Iterator;
import java.util.Map;

public class HashMapExample
{
    public static void main(String[] args)
    {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("Vasilii", "012345");
        map.put("Duc", "0123456789");

        MyHashMap<String, String> mapTest = new MyHashMap<>();
        mapTest.putAll(map);
        //mapTest.get("Duc");
        if (mapTest.isEmpty()) System.out.println("Empty");

    }
}
