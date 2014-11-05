package dijkstrafib;
import java.util.*;
/**
 *
 * @author amitskatti
 */

class BNode
{

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.vertex);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BNode other = (BNode) obj;
        if (!Objects.equals(this.vertex, other.vertex)) {
            return false;
        }
        return true;
    }
   Integer vertex;
   double dist;

   public BNode()
   {
       this.dist = 0;
       this.vertex = 0;
   }
}


public class BinaryHeapPriorityQueue{
    
	private ArrayList<BNode> heap;

	public BinaryHeapPriorityQueue() {
		heap = new ArrayList<BNode>();
	}

	private static <BNode> void swap(ArrayList<BNode> a, int i, int j) {
	BNode t = a.get(i);
	a.set(i, a.get(j));
	a.set(j, t);
	}

	private static int leftChild(int i) {
		return 2*i + 1;
	}

        private static int rightChild(int i) {
		return 2*i + 2;
	}

        private static int parent(int i) {
		return (i-1)/2;
	}

	public BNode extractMin() {
		if (heap.size() <= 0)
			return null;
		else {
                        BNode minVal = heap.get(0);
			heap.set(0, heap.get(heap.size()-1));  // Move last to position 0
			heap.remove(heap.size()-1);
			minHeapify(heap, 0);
			return minVal;
		}
	}

	public void insert(Integer vert, double d) {
                BNode element = new BNode();
                element.vertex = vert;
                element.dist = d;
                
		heap.add(element);       
		int loc = heap.size()-1; 

                while (loc > 0 && heap.get(loc).dist < heap.get(parent(loc)).dist) {
                        swap(heap, loc, parent(loc));
			loc = parent(loc);
		}
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}
        
        public void decreaseKey(Integer index, double newval){
            BNode b = new BNode(); 
            b.vertex = index;
            b.dist = newval;
            int heapIndex = heap.indexOf(b);
            heap.set(heapIndex, b);
            int loc = heapIndex;
            while (loc > 0 && heap.get(loc).dist < heap.get(parent(loc)).dist) 
            {
                   swap(heap, loc, parent(loc));
                   loc = parent(loc);
            }            
            
        }
        
        public BNode ValueAt (Integer vert)
        {
            BNode b = new BNode(); 
            b.vertex = vert;
            int heapIndex = heap.indexOf(b);
            return heap.get(heapIndex);
        }


	private static void minHeapify(ArrayList<BNode> a, int i) {
		int left = leftChild(i);    // index of node i's left child
		int right = rightChild(i);  // index of node i's right child
		int smallest;    // will hold the index of the node with the smallest element
	
                if (left <= a.size()-1 && a.get(left).dist < a.get(i).dist)
			smallest = left;   
		else
			smallest = i;      

                if (right <= a.size()-1 && a.get(right).dist < a.get(smallest).dist)
			smallest = right;  

                if (smallest != i) {
			swap(a, i, smallest);
			minHeapify(a, smallest);
		}
	} 


}
