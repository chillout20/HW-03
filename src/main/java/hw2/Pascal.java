import java.util.Scanner;
import java.io.IOException;

public class Pascal
{
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter any number: ");
        int n = scan.nextInt();
        int[] currentArray = new int[n];
        currentArray[0] = 1;
        for (int i = 0; i < n; i++)
        {
            int[] a = new int[i+1];
            for (int j = 0; j <= i; j++)
            {
                if (j == 0)
                {
                    a[j] = currentArray[j];
                } else if (j == i){
                    a[j] = currentArray[j-1];
                } else
                {
                    a[j] = currentArray[j - 1] + currentArray[j];
                }
            }
            System.arraycopy(a, 0, currentArray, 0, a.length);
            for (int element: a) {
                System.out.print(element + " ");
            }
            System.out.printf("%n");
        }
    }
}
