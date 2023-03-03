import static org.junit.Assert.*;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.junit.Test;


public class SimpleGivenTests
{

   @Test
   public void singleNodeTree()
   {
      Tree t = new Tree();
      t.insert(9);
      
      assertEquals(1, t.size(9));
      assertEquals(0, t.size(8));
      assertEquals(0, t.size(10));
      
      t.insert(15);
      assertEquals(2, t.size(9));
      assertEquals(0, t.size(8));
      assertEquals(0, t.size(10));
      assertEquals(2, t.size(15));
      assertEquals(0, t.size(18));

      t = new Tree();
      t.insert(15);
      t.insert(9);
      assertEquals(2, t.size(9));
      assertEquals(0, t.size(8));
      assertEquals(0, t.size(10));
      assertEquals(2, t.size(15));
      assertEquals(0, t.size(18));
      
      assertEquals(9, t.get(0));
      assertEquals(15, t.get(1));


   }
   
   @Test
   public void oneSplitLeft()
   {
      Tree t = new Tree();
      t.insert(9);
      t.insert(15);
      t.insert(1);
      
      assertEquals(3, t.size(9));
      assertEquals(1, t.size(15));
      assertEquals(0, t.size(17));
      assertEquals(0, t.size(11));

      assertEquals(1, t.size(1));
      assertEquals(0, t.size(0));
      assertEquals(0, t.size(3));

      assertEquals(1, t.get(0));
      assertEquals(9, t.get(1));
      assertEquals(15, t.get(2));
      
      assertEquals(3,t.size());
   }
   
   @Test
   public void oneSplitRight()
   {
      Tree t = new Tree();
      t.insert(1);
      t.insert(9);
      t.insert(15);
      
      assertEquals(3, t.size(9));
      assertEquals(1, t.size(15));
      assertEquals(0, t.size(17));
      assertEquals(0, t.size(11));

      assertEquals(1, t.size(1));
      assertEquals(0, t.size(0));
      assertEquals(0, t.size(3));
      
      assertEquals(1, t.get(0));
      assertEquals(9, t.get(1));
      assertEquals(15, t.get(2));
      assertEquals(3,t.size());


   }

   @Test
   public void oneSplitMiddle()
   {
      Tree t = new Tree();
      t.insert(1);
      t.insert(15);
      t.insert(9);
      
      assertEquals(3, t.size(9));
      assertEquals(1, t.size(15));
      assertEquals(0, t.size(17));
      assertEquals(0, t.size(11));

      assertEquals(1, t.size(1));
      assertEquals(0, t.size(0));
      assertEquals(0, t.size(3));
      
      assertEquals(1, t.get(0));
      assertEquals(9, t.get(1));
      assertEquals(15, t.get(2));
      assertEquals(3,t.size());


   }
   
   @Test
   public void leftSplit() {
	   Tree t = new Tree();
	   
	   t.insert(1);
	   t.insert(5);
	   t.insert(9);
	   t.insert(-1);
	   t.insert(0);
	   
	   assertEquals(0, t.get(1));
	   assertEquals(5, t.size());
	   assertEquals(-1, t.get(0));
	   assertEquals(9, t.get(t.size() - 1));
	   assertEquals(5, t.get(3));
	   assertEquals(1, t.get(2));
   }
   
   public void leftToRootSplit() {
	   Tree t = new Tree();
	   
	   t.insert(1);
	   t.insert(5);
	   t.insert(9);
	   t.insert(-1);
	   t.insert(0);
	   t.insert(-2);
	   t.insert(-3);
	   
	   assertEquals(-3, t.get(0));
	   assertEquals(7, t.size());
	   assertEquals(-1, t.get(1));
	   assertEquals(9, t.get(t.size() - 1));
	   assertEquals(1, t.get(4));
   }
   
   @Test
   public void rightSplit() {
	   Tree t = new Tree();
	   
	   t.insert(1);
	   t.insert(5);
	   t.insert(9);
	   t.insert(10);
	   t.insert(11);
	   
	   assertEquals(1, t.get(0));
	   assertEquals(9, t.get(2));
	   assertEquals(10, t.get(3));
	   assertEquals(1, t.size(9));
	   assertEquals(11, t.get(t.size() - 1));
   }
   
   public void rightToRootSplit() {
	   Tree t = new Tree();
	   
	   t.insert(1);
	   t.insert(5);
	   t.insert(9);
	   t.insert(10);
	   t.insert(11);
	   t.insert(13);
	   t.insert(14);
	   
	   assertEquals(10, t.get(3));
	   assertEquals(7, t.size());
	   assertEquals(3, t.size(5));
	   assertEquals(5, t.get(1));	
	   assertEquals(14, t.get(t.size() - 1));
	   assertEquals(11, t.get(4));
   }
   
   public void midToRootSplit() {
	   Tree t = new Tree();
	   
	   t.insert(1);
	   t.insert(5);
	   t.insert(9);
	   t.insert(10);
	   t.insert(11);
	   t.insert(6);
	   t.insert(8);
	   
	   assertEquals(9, t.get(3));
	   assertEquals(7, t.size());
	   assertEquals(10, t.size(5));
	   assertEquals(6, t.get(2));	
	   assertEquals(11, t.get(t.size() - 1));
	   assertEquals(8, t.get(4));
   }

   
   @Test
   public void testDuplicates()
   {
      Tree t = new Tree();
      t.insert(1);
      t.insert(9);
      assertEquals(2, t.size(9));
      t.insert(15);
      t.insert(13);
      t.insert(20);
      t.insert(7);
      t.insert(4);
      t.insert(1);
      t.insert(9);
      t.insert(15);
      t.insert(1);
      t.insert(9);
      t.insert(15);
      t.insert(13);
      t.insert(20);
      t.insert(7);
      t.insert(4);
      t.insert(13);
      t.insert(20);
      t.insert(7);
      t.insert(4);

      assertEquals(7, t.size(9));
      assertEquals(3, t.size(4));
      assertEquals(3, t.size(15));

      assertEquals(0, t.size(12));
      assertEquals(1, t.size(13));
      assertEquals(0, t.size(14));
      assertEquals(0, t.size(19));
      assertEquals(1, t.size(20));
      assertEquals(0, t.size(21));

      assertEquals(1, t.size(1));
      assertEquals(0, t.size(0));
      assertEquals(0, t.size(3));

      assertEquals(0, t.size(6));
      assertEquals(1, t.size(7));
      assertEquals(0, t.size(8));
      
      assertEquals(1, t.get(0));
      assertEquals(4, t.get(1));
      assertEquals(7, t.get(2));
      assertEquals(9, t.get(3));
      assertEquals(13, t.get(4));
      assertEquals(15, t.get(5));
      assertEquals(20, t.get(6));
      assertEquals(7,t.size());
   }
   
   @Test
   public void testnegative() {
	   Tree t = new Tree();
	   t.insert(-10);
	   t.insert(-15);
	   t.insert(-20);
	   assertEquals(-15, t.get(1));
	   assertEquals(3, t.size());
   }


}