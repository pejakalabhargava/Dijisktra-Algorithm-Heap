
package dijkstrafib;

import java.lang.*;
import java.util.*;

/**
 *
 * @author amitskatti
 */

class PNode
{
   Integer vertex;
   double dist;
   PNode next;
}


public class ArrayPriorityQueue {
            private PNode start;
            private Integer size;
 

        public ArrayPriorityQueue()
        {
          start=null;
          size=0;
        }
        
        public void enqueue(Integer vert, double d)
          {
            if(size==0)
                    {
                      start=new PNode();
                      start.next=null;
                      start.vertex=vert;
                      start.dist = d;

                    }
           else        
                    {
                         PNode newnode=new PNode();
                         newnode.vertex=vert;
                         newnode.dist = d;
                         newnode.next = start;
                         start = newnode;

                   }
          size++;
         }

        public PNode dequeueMin()
         {
            if(size==0)
            {
               System.out.println("Empty List ");
               return null;
            } 
            
            else if (size == 1)
            {
                PNode temp = start;
                start = null;
                size--;
                return temp;
            }
            
            else
            {
                PNode temp = start.next;
                PNode min = start;
                while (temp != null)
                {
                    if (temp.dist < min.dist)
                        min = temp;
                    temp = temp.next;
                 }
        
                temp = start;
                if(temp.dist == min.dist && temp.vertex.intValue() == min.vertex.intValue())
                    {
                        start = start.next;
                        size--;
                    }
                else {
                    while (temp.next != null)
                    {
                        if(temp.next.dist == min.dist && temp.next.vertex.intValue() == min.vertex.intValue())
                               break;
                        temp = temp.next;
                    }

                    temp.next = temp.next.next;
                    size--;
                }
                return min;
            }
        }

       public boolean isEmpty()
       {
               if(size == 0)
                       return true;
               else
                       return false;
       }

       public void decreaseKey(Integer index, double newval)
       {
           PNode temp = start;
           while (temp != null)
           {
               if (temp.vertex.intValue() == index.intValue())
               {
                   temp.dist = newval;
                        break;
               }
               temp = temp.next;
           }	
       }
       
       public void printQ ()
       {
           PNode temp = start;
           while (temp != null)
           {
               System.out.println (temp.vertex + "  " + temp.dist);
               temp = temp.next;
           }
       }
       
       public PNode ValueAt (Integer vert)
       {
           
           PNode temp = start;
           while (temp != null)
           {
               if (temp.vertex.intValue() == vert.intValue())
                    break;
               temp = temp.next;
           }
           return temp;
       }
       
}
