/**
 * Filename: TestAVLTree.java Project: p2 Authors: Debra Deppeler, TODO: add your name here
 *
 * Semester: Fall 2018 Course: CS400 Lecture: TODO: replace with your lecture number
 * 
 * Due Date: as specified in Canvas Version: 1.0
 * 
 * Credits: TODO: name individuals and sources outside of course staff
 * 
 * Bugs: no known bugs, but not complete either
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.IllegalArgumentException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/** TODO: add class header comments here */
public class TestAVLTree {



  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}



  /**
   * Tests that an AVLTree is empty upon initialization.
   */
  @Test
  public void test01isEmpty() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    assertTrue("Tree expected to be be empty.", tree.isEmpty());
  }

  /**
   * Tests that an AVLTree is not empty after adding a node.
   */
  @Test
  public void test02isNotEmpty() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      tree.insert(1);
      assertFalse("Tree expected to be non-empty", tree.isEmpty());
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Tests functionality of a single delete following several inserts.
   */
  @Test
  public void test03insertManyDeleteOne() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      for (int i = 1; i < 20; i++) {
        tree.insert(i);
      }
      tree.delete(10);
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Delete threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Delete threw Illegal Arguement Exception");
    } catch (Exception e) {
      fail("Delete threw unexpected excpetion");
    }
  }

  /**
   * Tests functionality of many deletes following several inserts.
   */
  @Test
  public void test04insertManyDeleteMany() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      // enter 2000 values
      for (int i = 1; i < 2000; i++) {
        tree.insert(i);
      }
      
      // delete hundreds of values
      for (int i = 500; i < 700; i++) {
        tree.delete(i);
      }
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Deleting many threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Deleting many threw Illegal Arguement Exception");
    } catch (Exception e) {
      fail("Deleting many threw unexpected excpetion");
    }
  }

  // TODO: add tests to completely test the functionality of your AVLTree class
  /**
   * Tests functionality of delete if entered null throws exception
   */
  @Test
  public void test05DeleteThrowException() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      for (int i = 1; i < 20; i++) {
        tree.insert(i);
      }
      tree.delete(null);
      fail("Tried to delete null, should have thrown IllegalArgumetException.");
 
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Deleting null threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail("Deleting null threw unexpected excpetion");
    }
  }
  /**
   * Tests functionality of search if entered null throws exception
   */
  @Test
  public void test06SearchThrowException() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      for (int i = 1; i < 20; i++) {
        tree.insert(i);
      }
      tree.search(null);
      fail("Tried to search null, should have thrown IllegalArgumetException.");
 
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Searching null threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail("Searching null threw unexpected excpetion");
    }
  }
  /**
   * Tests functionality of insert if entered null throws exception
   */
  @Test
  public void test07InsertThrowException() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      tree.insert(1);
      tree.insert(null);
      fail("Tried to insert null, should have thrown IllegalArgumetException.");
 
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Inserting null threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail("Inserting null threw unexpected excpetion");
    }
  }
  /**
   * Tests functionality of insert if enter duplicate value should throw exception
   */
  @Test
  public void test08InsertDuplicateThrowException() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      tree.insert(1);
      tree.insert(1);
      fail("Tried to insert duplicate values, should have thrown DuplicateKeyException.");
 
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Tried to insert duplicate values, should have thrown DuplicateKeyException."
        + "instead threw IllegalArgumentExcpetion");
    } catch (Exception e) {
      fail("Tried to insert duplicate values, should have thrown DuplicateKeyException."
          + "instead threw unexpected Excpetion");
    }
  }
  /**
   * Tests functionality of a few inserts and deletes with String tree
   */
  @Test
  public void test09insertFewDeleteFew() {
    AVLTree<String> tree = new AVLTree<String>();
    try {
      tree.insert("abc");
      tree.insert("def");
      tree.insert("ghi");
      tree.insert("Julie");
      tree.insert("Book");
      tree.insert("Annabel");
      tree.insert("Bennett");
      tree.insert("Adam");
      tree.insert("Stacie");
      
      tree.delete("Julie");
      tree.delete("Bennett");
      tree.delete("abc");
      tree.delete("def");
      
      
      
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Delete/Insert a few threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Delete/Insert a few threw Illegal Arguement Exception");
    } catch (Exception e) {
      fail("Delete/Insert a few threw unexpected excpetion");
    }

  }
  /**
   * Tests functionality of many inserts and deletes and inserts again
   */
  @Test
  public void test10insertDeleteInsertAgain() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      for (int i = 1; i < 200; i++) {
        tree.insert(i);
      }

      for (int i = 50; i < 70; i++) {
        tree.delete(i);
      }
      
      for (int i = 500; i < 1000; i++) {
        tree.insert(i);
      }
      
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Insert/Delete/Insert threw Duplicate Key Excpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Insert/Delete/Insert threw Illegal Arguement Exception");
    } catch (Exception e) {
      fail("Insert/Delete/Insert threw unexpected excpetion");
    }
  }
  /**
   * Tests functionality of tree and ensures values are entered in ascending order
   */
  @Test
  public void test11ascendingOrder() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      for (int i = 1; i < 200; i++) {
        tree.insert(i);
      }
      tree.insert(1000);
      tree.delete(199);
      tree.delete(52);
      
      assertTrue("Tree is not in ascending order",tree.checkForBinarySearchTree());
      
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Checking for ascending order threw a DuplicateKeyException");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Checking for ascending order threw an IllegalArguementException");
    } catch (Exception e) {
      fail("Checking for ascending order threw an unexpected excpetion");
    }
  }
  /**
   * Tests that this tree is balanced
   */
  @Test
  public void test12CheckForBalancedTree() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      for (int i = 1; i < 200; i++) {
        tree.insert(i);
      }
      tree.insert(1000);
      tree.delete(199);
      tree.delete(52);
      
      int treeHeight = tree.getParent().getHeight();
      int treeLog2Height = (int) Math.ceil((Math.log(tree.getSize())/Math.log(2)));
      
      if (treeHeight > treeLog2Height) {
        fail("Tree Height is larger than the Log2 of the Size - not a balanced tree");
      }
      
      assertTrue("Tree is not a balanced tree",tree.checkForBalancedTree());
      
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Checking for a Balanced Tree threw a DuplicateKeyExcpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Checking for a Balanced Tree threw an IllegalArguementException");
    } catch (Exception e) {
      fail("Checking for a Balanced Tree threw an unexpected excpetion");
    }
  }
  /**
   * Tests that the tree size method
   */
  @Test
  public void test13CheckSize() {
    AVLTree<Integer> tree = new AVLTree<Integer>();
    try {
      int size;
      for (int i = 1; i < 200; i++) {
        tree.insert(i);
        
      }
      tree.insert(1000);
      tree.delete(199);
      tree.delete(52);
      
      // tree size is 198
      size = tree.getSize();
      if (size != 198) {
        fail("Size is not accurate. Should be 198, returned " + size);
      }
      
    } catch (DuplicateKeyException e) {
      System.out.println(e.getMessage());
      fail("Checking for tree size threw a DuplicateKeyExcpetion.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail("Checking for a tree size an IllegalArguementException");
    } catch (Exception e) {
      fail("Checking for a tree size threw an unexpected excpetion");
    }
  }
}
