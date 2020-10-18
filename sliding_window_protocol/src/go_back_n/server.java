package go_back_n;
import java.util.*;
import java.io.*;
import java.net.*;

public class server 
{
    public static void main(String[] args)
    {
        try
        {
            int window_size = 10;
            ServerSocket ss=new ServerSocket(6696);  
            Socket s=ss.accept();//establishes connection  
            System.out.println("server connected with client and running.....");
            Scanner scan = new Scanner(s.getInputStream());
            Scanner input = new Scanner(System.in);
            PrintWriter output = new PrintWriter(s.getOutputStream(), true);
            
            int counter = scan.nextInt();
            System.out.println("The packet is divided into " + counter + " frames");
            
            int items = scan.nextInt();
            System.out.println("Total number of items are " + items);
            
            int i = 0;
            while(i < counter)
            {
                int receive_no = scan.nextInt();
                System.out.print("Frame received " + i + " -- Items: ");
                for(int j = 0; j < receive_no; j++)
                {
                    int item = scan.nextInt();
                    System.out.print(" |" + item + "| ");
                }
                System.out.println("");
                String mis = "Was there any missing acknowledgement?";
                System.out.println(mis);
                String ch = input.next();
                output.println(ch);
                
                if(ch.equals("Y"))
                {
                    System.out.println("Enter the missing acknowledgement");
                    int ack = input.nextInt();
                    output.println(ack);                   
                    items = items - ack;
                    
                    if(items % window_size == 0)                    
                        counter = items / window_size;                   
                    else
                        counter = (items / window_size) + 1;
                    
                    i = 0;
                }
                else
                    i++;
            }
        }
        
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
