package com.fbrs.zombieserver.map.serial;


import java.io.Serializable;
import java.util.ArrayList;


public class QuadTree implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Node root;
    private ArrayList<OverLayItemDescriptor> returnArray = new ArrayList<OverLayItemDescriptor>();

    // helper node data type
    public class Node implements Serializable {
    	   /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
        public float x, y;              // x- and y- coordinates
        public Node NW, NE, SE, SW;   // four subtrees
        public OverLayItemDescriptor value;           // associated data

        Node(float x, float y, OverLayItemDescriptor value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }


  /***********************************************************************
    *  Insert (x, y) into appropriate quadrant
    ***********************************************************************/
    public void insert(float x, float y, OverLayItemDescriptor value) {
        root = insert(root, x, y, value);
    }

    private Node insert(Node h, float x, float y, OverLayItemDescriptor value) {
        if (h == null) return new Node(x, y, value);
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, value);
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, value);
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, value);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, value);
        return h;
    }


  /***********************************************************************
    *  Range search.
    ***********************************************************************/

    public ArrayList<OverLayItemDescriptor> query2D(RectF rect, int maxDepth) {
    	returnArray.clear();
    	query2D(root, rect,0, maxDepth);
        return returnArray;
    }

    private void query2D(Node h, RectF rect,int depth, int maxDepth) {
        if (h == null || depth > maxDepth) return;
        float xmin = rect.left;
        float ymin = rect.bottom;
        float xmax = rect.right;
        float ymax = rect.top;
        if (rect.left <= h.x && rect.right >h.x && rect.bottom <= h.y && rect.top > h.y)
            returnArray.add(h.value);
        if ( less(xmin, h.x) &&  less(ymin, h.y)) query2D(h.SW, rect, depth+1, maxDepth);
        if ( less(xmin, h.x) && !less(ymax, h.y)) query2D(h.NW, rect, depth+1, maxDepth);
        if (!less(xmax, h.x) &&  less(ymin, h.y)) query2D(h.SE, rect, depth+1, maxDepth);
        if (!less(xmax, h.x) && !less(ymax, h.y)) query2D(h.NE, rect, depth+1, maxDepth);
    }


   /*************************************************************************
    *  helper comparison functions
    *************************************************************************/

    private boolean less(float k1, float k2) { return k1 < k2; }
    @SuppressWarnings("unused")
	private boolean eq  (float k1, float k2) { return k1 == k2; }
}
