//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           AVLTree.java
// Files:           AVLTreeADT.java, DuplicateKeyExcpetion.java
// Semester:        Fall 2018 
// Course:          CS400 Lecture: Epic Lecture 4
//
// Author:          Debra Deppeler, Julie Book
// Email:           jlsauer@wisc.edu
// Lecturer's Name: Andy Kuemmel
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    none
// Partner Email:   none
// Partner Lecturer's Name: none
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _x__ Write-up states that pair programming is allowed for this assignment.
//   _x__ We have both read and understand the course Pair Programming Policy.
//   _x__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         (identify each person and describe their help in detail)
// Online Sources:  
// https://www.geeksforgeeks.org/how-to-determine-if-a-binary-tree-is-balanced/
//  used this for education to help determine when and how to balance a tree
// Searching and Deleting code - Andy Kuemmel resources provided by class including the 
// SearchEngine.java program provided at beginning of class
// 
// https://www.geeksforgeeks.org/a-program-to-check-if-a-binary-tree-is-bst-or-not/
// got the idea here on how to check for a BST tree - using the recursive method
//
// * Bugs: no known bugs
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.lang.IllegalArgumentException;

/**
 * AVLtree implementation, will keep the tree balanced. Can insert, delete, search and print out 
 * the tree.
 * @author Julie Book
 * @version 1.0
 * @param <K>
 * 
 */
public class AVLTree<K extends Comparable<K>> implements AVLTreeADT<K> {

  // object variables
  int size; // size of the AVL tree
  BSTNode<K> parent;  // parent or top node of the AVL tree
  private BSTNode<K> previous; // node that is previous to the node that we are on

  /**
   * Class that represents a tree node.
   * @author Julie Book
   * @version 1.0
   * @param <K>
   */
  class BSTNode<K> {
    /* fields */
    private K key; // the value of the node
    private int height; // height of the existing node
    private BSTNode<K> left, right; // the children nodes of the key node

    /**
     * Constructor for a BST node.
     * @param key
     */
    BSTNode(K key) {
      height = 1;
      left = null;
      right = null;
      this.key = key;
    }

    /* accessors */
    /**
     * gets the right child value for the node
     * @return the right child for the node
     */
    public BSTNode<K> getRightChild() {
      return right;
    }

    /**
     * gets the left child value for the node
     * @return the left child for the node
     */
    public BSTNode<K> getLeftChild() {
      return left;
    }
    
    /**
     * gets the height value for the node
     * @return the value in height
     */
    public int getHeight() {
      return height;
    }

    /**
     * gets the key value for the node
     * @return the value in key
     */
    public K getKey() {
      return key;
    }

    /**
     * sets the right child node for the node
     * @param node to set in right child
     */
    public void setRightChild(BSTNode<K> child) {
      right = child;
    }

    /**
     * sets the left child node for the node
     * @param node to set in left child
     */
    public void setLeftChild(BSTNode<K> child) {
      left = child;
    }

    /**
     * sets the height for the node
     * @param value to set in height
     */
    public void setHeight(int height) {
      this.height = height;
    }

    /**
     * sets the key for the node
     * @param value to set in key
     */
    public void setKey(K key) {
      this.key = key;
    }

  }

  /**
   * constructor to create a new AVL tree, sets up the height and the parent node
   */
  public AVLTree() {
    size = 0;
    parent = null;
  }

  /**
   * gets the size of the tree
   * @return size of the tree
   */
  public int getSize() {
    return size;
  }
  /**
   * gets the parent node of the tree
   * @return parent node
   */
  public BSTNode<K> getParent() {
    return parent;
  }
  /**
   * checks if the tree is empty
   * @return true if the tree is empty
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * inserts a node into the AVL tree
   * 
   * @param key that we are inserting
   * @exception throws DuplicateKeyException
   * @exception throws IllegalArgumentException
   */
  @Override
  public void insert(K key) throws DuplicateKeyException, IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("Cannot insert for null value");
    }
    parent = recursiveInsert(parent, key);
  }

  /**
   * recursive helper for insert
   * 
   * @param the node we are starting the insert on
   * @param the value/key that we are inserting
   * @exception will through an exception if there is a duplicate node added
   * @return the node that is the parent/root that we added
   */
  private BSTNode<K> recursiveInsert(BSTNode<K> current, K key) throws DuplicateKeyException {
    if (current == null) { // base case
      current = new BSTNode<K>(key);
      size++;
    } 
    else if (current.getKey().equals(key)) {
      throw new DuplicateKeyException("WARNING: failed to insert duplicate value: " + key + ".");
    } 
    else if (current.getKey().compareTo(key) > 0) { // go left
      current.setLeftChild(recursiveInsert(current.getLeftChild(), key));
      current.setHeight(getNodeHeight(current));
      if (Math.abs(getNodeBalance(current)) > 1) { // need to rebalance if node is not balanced
        current = rebalance(current);
      }
    } 
    else { // go right
      current.setRightChild(recursiveInsert(current.getRightChild(), key));
      current.setHeight(getNodeHeight(current));
      if (Math.abs(getNodeBalance(current)) > 1) { // need to rebalance if node is not balanced
        current = rebalance(current);
      }
    }
    return current;
  }

  /**
   * recalculates the height for the node based on the left and right child 
   * @param the node that we are calculating the height for
   * @return the height of the node passed in
   */
  private int getNodeHeight(BSTNode<K> current) {
    // check right height and left height
    int leftHeight = 0;
    int rightHeight = 0;

    if (current == null) { // nothing to balance return true
      return 0;
    }
    if (current.getLeftChild() != null) {
      leftHeight = current.getLeftChild().getHeight();
    }
    if (current.getRightChild() != null) {
      rightHeight = current.getRightChild().getHeight();

    }
    return (Math.max(rightHeight, leftHeight) + 1);
  }
  
  /**
   * gets the balance of each node
   * @param node that you are checking the balance for
   * @return the balance of the node passed in
   */
  private int getNodeBalance(BSTNode<K> current) {
    // assume they are leaves and don't have any height
    int leftHeight = 0;
    int rightHeight = 0;

    if (current == null) { // nothing to balance return true
      return 0;
    }
    if (current.getLeftChild() != null) {
      leftHeight = current.getLeftChild().getHeight();
    }
    if (current.getRightChild() != null) {
      rightHeight = current.getRightChild().getHeight();
    }
    return (rightHeight - leftHeight);
  }
  
  /**
   * rebalances the tree and nodes
   * 
   * @param node to rotate right
   * @return the parent node based on the rebalancing
   */
  private BSTNode<K> rebalance(BSTNode<K> node) {
    int nodeBalance = getNodeBalance(node);
    int nodeLeftChildBalance = getNodeBalance(node.getLeftChild());
    int nodeRightChildBalance = getNodeBalance(node.getRightChild());
    
    if (nodeBalance <= -2 && nodeLeftChildBalance > 0) {
      // Left-Right tree
      node.setLeftChild(rotateLeft(node.getLeftChild()));
      node = rotateRight(node);
    } 
    else if (nodeBalance >= 2 && nodeRightChildBalance < 0) {
      // Right-Left tree
      node.setRightChild(rotateRight(node.getRightChild()));
      node = rotateLeft(node);
    }
    else if (nodeBalance <= -2) {
      // Left-Left tree
      node = rotateRight(node);
    }
    else if (nodeBalance >= 2) {
      // Right-Right tree
      node = rotateLeft(node);
    }
    return node;
  }

  /**
   * rotates the node to the left and updates the heights of the nodes
   * @param node to rotate right
   * @return node that is the parent of the top node
   */
  private BSTNode<K> rotateLeft(BSTNode<K> node) {
    BSTNode<K> temp = node.getRightChild();
    node.setRightChild(temp.getLeftChild());
    temp.setLeftChild(node);

    // update heights
    node.setHeight(getNodeHeight(node));
    temp.setHeight(getNodeHeight(temp));

    return temp;
  }

  /**
   * rotates the node to the right and updates the heights of the nodes
   * @param node to rotate right
   * @return node that is the parent or top node
   */
  private BSTNode<K> rotateRight(BSTNode<K> node) {
    BSTNode<K> temp = node.getLeftChild();
    node.setLeftChild(temp.getRightChild());
    temp.setRightChild(node);
    
    // update heights
    node.setHeight(getNodeHeight(node));
    temp.setHeight(getNodeHeight(temp));
    
    return temp;
  }

  /**
   * Deletes key from the AVL tree
   * @param key
   * @throws IllegalArgumentException if try to delete null
   * reference: Andy Kuemmel code given at beginning of class (lecture 1) 
   */
  @Override
  public void delete(K key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("Cannot delete for null value");
    }
    parent = recursiveDeleteNode(parent, key);
  }

  /**
   * recursively searches for the node to remove if the node is not found, no changes are made if
   * the node is found, copies and promotes it to take the place of current while removing the
   * original node from the tree
   * @param the current node in our search
   * @param the id of the node to delete
   * @return the updated current node
   * reference: Andy Kuemmel code given at beginning of class (lecture 1) 
   */
  private BSTNode<K> recursiveDeleteNode(BSTNode<K> current, K key) {
    if (current == null) {
      return null; // tree is unchanged
    } else if (key.compareTo(current.getKey()) == 0) {
      current = removeAndPromote(current); // found value - take it out and move tree
      
      if (current != null) {
        current = rebalance(current);
      }
    } else if (key.compareTo(current.getKey()) < 0) {
      current.setLeftChild(recursiveDeleteNode(current.getLeftChild(), key));
      current.setHeight(getNodeHeight(current));
      current = rebalance(current);
      
    } else if (key.compareTo(current.getKey()) > 0) {
      current.setRightChild(recursiveDeleteNode(current.getRightChild(), key));
      current.setHeight(getNodeHeight(current));
      current = rebalance(current);
      
    }
    return current;
  }

  /**
   * removes the current Node and promotes the smallest node in the right subtree calls a helper
   * method smallestInTree also calls recursiveDeleteNode to remove the promoted node
   * @param current the node to remove
   * @return the updated promoted node
   * reference: Andy Kuemmel code given at beginning of class (lecture 1) 
   */
  private BSTNode<K> removeAndPromote(BSTNode<K> current) {
    // IF BOTH CHILDREN ARE NULL, RETURN NULL
    if (current.getRightChild() == null && current.getLeftChild() == null) {
      size--;
      return null;
    }
    // IF LEFT CHILD IS NULL RETURN THE OTHER CHILD
    else if (current.getLeftChild() == null) {
      size--;// update size after we remove
      return current.getRightChild();
    }
    // IF RIGHT CHILD IS NULL RETURN THE OTHER CHILD
    else if (current.getRightChild() == null) {
      size--;// update size after we remove
      return current.getLeftChild();
    }
    
    // IF current has 2 children, find the left-most value in the right subtree
    BSTNode<K> smallestRight = smallestInTree(current.getRightChild());

    // promote the smallestRight
    current.setKey(smallestRight.getKey());

    // remove the smallestRight from the right subtree
    current.setRightChild(recursiveDeleteNode(current.getRightChild(), smallestRight.getKey()));

    // return the updated promoted node
    return current;

  }

  /**
   * returns the smallest ID in this tree is called on an instance of this class from a main method
   * uses a recursive helper method that works on Nodes
   * @return the key of the smallest node
   * reference: Andy Kuemmel code given at beginning of class (lecture 1)  
   */
  private BSTNode<K> smallestInTree() {
    BSTNode<K> smallest = smallestInTree(parent);
    return smallest;
  }

  /**
   * recursive helper method to find the node with the smallest iD
   * @param current the current node we are considering
   * @return this node if left child is null, or the smallest node in the left subtree
   * reference: Andy Kuemmel code given at beginning of class (lecture 1)  
   */
  private BSTNode<K> smallestInTree(BSTNode<K> current) {
    BSTNode<K> leftChild = current.getLeftChild();

    if (leftChild == null) { // base case, add it here
      return current;
    } else { // go left
      return smallestInTree(leftChild);
    }
  }

  /**
   * Searches the AVL tree for the entered value
   * @param key - value that we are looking for
   * @exception throws IllegalArgumentException if searching for null
   * @return true if the value is found
   * 
   * reference: Andy Kummel code given at beginning of class 
   */
  @Override
  public boolean search(K key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("Cannot search for null value");
    }
    return recursiveSearch(parent, key);
  }

  /**
   * Helper method to search recursive method that tries to find the right key
   * @param current - node that we are searching for
   * @param key - the value that we are searching for
   * @return true if we find the key, false if we do not find the key
   * 
   * reference: Andy Kuemmel code given at beginning of class (lecture 1)  
   * 
   */
  private boolean recursiveSearch(BSTNode<K> current, K key) {
    if (current == null) { // first base case
      return false;
    } else if (current.getKey().equals(key)) { // second base case
      return true;
    } else if (key.compareTo(current.getKey()) < 0) {
      return recursiveSearch(current.getLeftChild(), key);
    } else {
      return recursiveSearch(current.getRightChild(), key);
    }
  }

  /**
   * Performs in-order traversal of AVL Tree
   * 
   * @return a String with all the keys, in order, with exactly one space between keys
   */
  @Override
  public String print() {
    return recursivePrint(parent);
  }

  /**
   * Helper method to print() recursive method that gets all the tree nodes in order
   * 
   * @return a String with all the keys, in order, with exactly one space between keys
   */
  private String recursivePrint(BSTNode<K> current) {
    String returnString = "";
    if (current != null) {
      returnString = returnString + recursivePrint(current.getLeftChild());
      returnString = returnString + " " + current.getKey();
      returnString = returnString + recursivePrint(current.getRightChild());
    }
    return returnString;
  }

  /**
   * prints out the AVL tree sideways 
   * source: Building Java Programs, 4th Ed., by Reges and Stepp, Ch 17
   */
  public void printSideways() {
    System.out.println("------------------------------------------");
    recursivePrintSideways(parent, "");
    System.out.println("------------------------------------------");

  }

  /**
   * recursive helper to printSideways() 
   * source: Building Java Programs, 4th Ed., by Reges and Stepp, Ch 17
   */
  private void recursivePrintSideways(BSTNode<K> current, String indent) {
    if (current != null) {
      recursivePrintSideways(current.getRightChild(), indent + "    ");
      System.out.println(indent + current.getKey() + "(" + current.getHeight() + ")");
      recursivePrintSideways(current.getLeftChild(), indent + "    ");
    }
  }

  /**
   * check the parent to see if the tree is balanced
   * 
   * @return true if the tree is balanced
   */
  @Override
  public boolean checkForBalancedTree() {
    // check  parent node only, will be unbalanced if the tree is not balanced
    if (getNodeBalance(parent) > 1 || getNodeBalance(parent) < -1) {
      return false;
    }
    return true;
  }

  /**
   * Checks if the tree follows the rules of a binary search tree, gets all the values in order
   * and checks that the previous one is smaller than the next one.
   * @return true if the tree follows the rules of a binary search tree
   * 
   */
  @Override
  public boolean checkForBinarySearchTree() {
    return recursiveCheckForBST(parent);
  }

  /**
   * Returns a boolean if the previous value is not less than the current value
   * uses previous node that is set at the class level
   * @param node of the value you want to start with
   * @return false if the tree is not in order
   */
  private boolean recursiveCheckForBST(BSTNode<K> current) {
    if (current != null) {
      
      if (!recursiveCheckForBST(current.getLeftChild())) {
        return false;
      }
      if (previous != null) {
        if (previous.getKey().compareTo(current.getKey()) > 0) {
          return false;
        }
      }
      previous = current;
      return recursiveCheckForBST(current.getRightChild());
    }
    return true;
  }
   /**
   * main method used for testing of the AVL tree class
   * @param string arguments
   */
  public static void main(String[] args) {
    // testing implementation of the AVL tree
    AVLTree<Integer> tree = new AVLTree<Integer>();
    AVLTree<Integer> tree2 = new AVLTree<Integer>();
    AVLTree<Integer> tree3 = new AVLTree<Integer>();
    AVLTree<Integer> tree4 = new AVLTree<Integer>();
    AVLTree<Integer> tree5 = new AVLTree<Integer>();
    try {
      
      System.out.println("Tree should be empty: " + tree.isEmpty());
      //inserting tree nodes
      tree.insert(50);
      tree.insert(30);
      tree.insert(60);
      tree.insert(55);
      tree.insert(70);
      System.out.println("Right-Right Rotation should happen - 60 should be parent");
      tree.insert(65);
      tree.printSideways();
      
      //inserting tree nodes
      tree2.insert(50);
      tree2.insert(30);
      tree2.insert(60);
      tree2.insert(10);
      tree2.insert(40);
      System.out.println("Left-Left Rotation should happen - 30 should be parent");
      tree2.insert(20);
      tree2.printSideways();
      
      //inserting tree nodes
      tree3.insert(50);
      tree3.insert(30);
      tree3.insert(60);
      tree3.insert(10);
      tree3.insert(40);
      System.out.println("Left-Right Rotation should happen - 40 should be parent");
      tree3.insert(35);
      tree3.printSideways();
      
      //inserting tree nodes
      tree4.insert(50);
      tree4.insert(30);
      tree4.insert(60);
      tree4.insert(55);
      tree4.insert(70);
      System.out.println("Right-Left Rotation should happen - 55 should be parent");
      tree4.insert(57);
      tree4.printSideways();
      
      //delete notes
      tree4.delete(55);
      tree4.delete(70);
      System.out.println("Delete 55 and 70 from tree");
      tree4.printSideways();
      
      
      System.out.println("Print out tree values in numberic order\n" + tree.print());
      
      System.out.println("Search for value 50 within the tree: " + tree.search(50));
      System.out.println("Is Tree Balanced: " + tree.checkForBalancedTree());

      System.out.println("Check for Binary Tree: " + tree.checkForBinarySearchTree());
      
      tree5.insert(10);
      tree5.insert(9);
      System.out.println("Check for Binary Tree: " + tree5.checkForBinarySearchTree());
      
            
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      e.printStackTrace();
    }
  }
}
