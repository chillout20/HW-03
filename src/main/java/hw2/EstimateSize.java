package hw2;

public class EstimateSize {
    public static void main(String[] args) throws InterruptedException {
        // Get occupied memory without int
        System.out.println("Before GC:");
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        long occupiedBeforeGc = total - free;
        System.out.println("Total memory: " + total);
        System.out.println("Free memory: " + free);
        System.out.println("Memory occupied before GC " + occupiedBeforeGc);

        System.gc();
        Thread.sleep(10);

        System.out.println("After GC:");
        total = Runtime.getRuntime().totalMemory();
        free = Runtime.getRuntime().freeMemory();
        long occupiedAfterGc = total - free;
        System.out.println("Total memory: " + total);
        System.out.println("Free memory: " + free);
        System.out.println("Memory occupied after GC: " + occupiedAfterGc);

        // Test size of int
        int n = 10_000_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;

        System.out.println("After creating int array: ");
        total = Runtime.getRuntime().totalMemory();
        free = Runtime.getRuntime().freeMemory();
        long occupiedAfterInts = total - free;
        long occupiedByInts = occupiedAfterInts - occupiedAfterGc;
        System.out.println("Total memory: " + total);
        System.out.println("Free memory: " + free);
        System.out.println("Memory occupied by ints: " + occupiedByInts);
        System.out.println("Int size: " + occupiedByInts / Float.valueOf(n));

        // Test size of Int reference
        Integer[] b = new Integer[n];
        for (int i = 0; i <n ; i++) b[i] = null;

        System.out.println("After creating Int array: ");
        total = Runtime.getRuntime().totalMemory();
        free = Runtime.getRuntime().freeMemory();
        long occupiedAfterIntsRef = total - free;
        long occupiedByIntsRef = occupiedAfterIntsRef - occupiedAfterInts;
        System.out.println("Total memory: " + total);
        System.out.println("Free memory: " + free);
        System.out.println("Memory occupied by Int refs: " + occupiedByInts);
        System.out.println("Int ref size: " + occupiedByIntsRef / Float.valueOf(n));

        // Test size of string
        String[] c = new String[n];
        for (int i = 0; i < n; i++) c[i] = "Hello";

        System.out.println("After creating Int array: ");
        total = Runtime.getRuntime().totalMemory();
        free = Runtime.getRuntime().freeMemory();
        long occupiedAfterStr = total - free;
        long occupiedByStr = occupiedAfterStr - occupiedAfterIntsRef;
        System.out.println("Total memory: " + total);
        System.out.println("Free memory: " + free);
        System.out.println("Memory occupied by String: " + occupiedByInts);
        System.out.println("String size: " + occupiedByIntsRef / Float.valueOf(n));

    }
}
