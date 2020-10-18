package selective_repeat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


public class client 
{
    public static void main(String args[])
    {
        try
        {
            Socket s=new Socket("localhost",6696);            
            Scanner input = new Scanner(s.getInputStream());
            PrintWriter output = new PrintWriter(s.getOutputStream(), true);
            Scanner scan = new Scanner(System.in);
            
            int window_size = 10;
            System.out.println("Enter total no of items you want to send");
            int items = scan.nextInt();
             
            //int[] arr = new int[items];
            LinkedList<Integer> arr = new LinkedList<Integer>();
            //System.out.println("Enter the items");
            for(int i = 0; i < items; i++)
            {
                arr.add(i+1);
            }
            
            int counter = 0;
            
            if(items % window_size == 0)           
                counter = items / window_size;          
            else
                counter = (items / window_size) + 1;

            output.println(counter);
            output.println(items);
            int[] index = new int[counter];
            
            int y = items;
            for(int i = 0; i < counter; i++)
            {
                if(y > window_size)
                {
                    y -= window_size;
                    index[i] = window_size;
                }
                
                else
                {
                    index[i] = y;
                    break;
                }                   
            }
            
            int start_index = 0;
            int i = 0, j = 0;
            while(i < counter)
            {
                int send_no = start_index + index[i];
                output.println(index[i]);              
                System.out.print("Frame sent: " + i + " -- Items: ");
                for(j = start_index; j < send_no; j++)
                {
                    int a = arr.get(i);
                    System.out.print(" |" + a + "| ");
                    output.println(a); 
                    arr.remove(i);
                }
                start_index = j;  
                System.out.println("");
                
                //Y/N answer accept
                String ans = input.next();
                
                if(ans.equals("Y"))
                {
                    int total = input.nextInt();
                    for(int k = 0; k < total; k++)
                    {
                        int a = input.nextInt();
                        arr.addFirst(a);
                    }
                    start_index = 0;
                    
                    items = (items - index[i]) + total;
                    
                    if(items % window_size == 0)                   
                        counter = items / window_size;                   
                    else
                        counter = (items / window_size) + 1;
                                      
                    y = items;
                    for(int k = 0; k < counter; k++)
                    {
                        if(y > window_size)
                        {
                            y -= window_size;
                            index[k] = window_size;
                        }

                        else
                        {
                            index[k] = y;
                            break;
                        }                   
                    }
                    i = 0;
                }                
               else
                {
                    i++;
                }                   
            }            
        }

        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
