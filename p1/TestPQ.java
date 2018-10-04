/**
 * Filename: TestPQ.java Project: p1TestPQ Authors: Debra Deppeler, Julie Book
 *
 * Semester: Fall 2018 Course: CS400 Lecture: Epic lecture, 4
 * 
 * Note: Warnings are suppressed on methods that construct new instances of generic PriorityQueue
 * types. The exceptions that occur are caught and such types are not able to be tested with this
 * program.
 * 
 * Due Date: Before 10pm on September 17, 2018 Version: 2.0
 * 
 * Credits: NOTE: this is an individual assignment, you are not allowed to view or use code written
 * by anyone but yourself.
 * 
 * Bugs: no known bugs
 */


import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Runs black-box unit tests on the priority queue implementations passed in as command-line
 * arguments (CLA).
 * 
 * If a class with the specified class name does not exist or does not implement the
 * PriorityQueueADT interface, the class name is reported as unable to test.
 * 
 * If the class exists, but does not have a default constructor, it will also be reported as unable
 * to test.
 * 
 * If the class exists and implements PriorityQueueADT, and has a default constructor, the tests
 * will be run.
 * 
 * Successful tests will be reported as passed.
 * 
 * Unsuccessful tests will include: input, expected output, and actual output
 * 
 * Example Output: Testing priority queue class: PQ01 5 PASSED 0 FAILED 5 TOTAL TESTS RUN Testing
 * priority queue class: PQ02 FAILED test00isEmpty: unexpectedly threw
 * java.lang.NullPointerException FAILED test04insertRemoveMany: unexpectedly threw
 * java.lang.ArrayIndexOutOfBoundsException 3 PASSED 2 FAILED 5 TOTAL TESTS RUN
 * 
 * ... more test results here
 * 
 * @author deppeler
 */
public class TestPQ {

  // set to true to see call stack trace for exceptions
  private static final boolean DEBUG = false;

  /**
   * Run tests to determine if each Priority Queue implementation works as specified. User names the
   * Priority Queue types to test. If there are no command-line arguments, nothing will be tested.
   * 
   * @param args names of PriorityQueueADT implementation class types to be tested.
   */
  public static void main(String[] args) {
    for (int i = 0; i < args.length; i++)
      test(args[i]);

    if (args.length < 1)
      print("no PQs to test");
  }

  /**
   * Run all tests on each priority queue type that is passed as a classname.
   * 
   * If constructing the priority queue in the first test causes exceptions, then no other tests are
   * run.
   * 
   * @param className the name of the class that contains the priority queue implementation.
   */
  private static void test(String className) {
    print("Testing priority queue class: " + className);
    int passCount = 0;
    int failCount = 0;
    try {

      if (test00isEmpty(className))
        passCount++;
      else
        failCount++;
      if (test01getMaxEXCEPTION(className))
        passCount++;
      else
        failCount++;
      if (test02removeMaxEXCEPTION(className))
        passCount++;
      else
        failCount++;
      if (test03insertRemoveOne(className))
        passCount++;
      else
        failCount++;
      if (test04insertRemoveMany(className))
        passCount++;
      else
        failCount++;
      if (test05duplicatesAllowed(className))
        passCount++;
      else
        failCount++;
      if (test06manyDataItems(className))
        passCount++;
      else
        failCount++;
      if (test07insertTestEmpty(className))
        passCount++;
      else
        failCount++;
      if (test08insertOrder(className))
        passCount++;
      else
        failCount++;
      if (test09getMaxRemoveCheck(className))
        passCount++;
      else
        failCount++;

      String passMsg = String.format("%4d PASSED", passCount);
      String failMsg = String.format("%4d FAILED", failCount);
      print(passMsg);
      print(failMsg);

    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
      if (DEBUG)
        e.printStackTrace();
      print(className + " FAIL: Unable to construct instance of " + className);
    } finally {
      String msg = String.format("%4d TOTAL TESTS RUN", passCount + failCount);
      print(msg);
    }

  }

  /////////////////////////
  // Must test each operation of the PriorityQueueADT
  // Find and report cases that cause problems.
  // Do not try to fix or debug implementations.
  /////////////////////////

  /**
   * Tests that getMax doesn't remove the max node and just returns the value
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if getMax doesn't remove the value from the priority queue
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test09getMaxRemoveCheck(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    try {
      Integer testInt1 = 1;
      Integer testInt2 = 2;
      Integer testInt3 = 3;

      // adding values the return should be 3, 2, 1
      pq.insert(testInt1);
      pq.insert(testInt2);
      pq.insert(testInt3);

      Integer returnInt;
      returnInt = pq.getMax();
      if (returnInt.compareTo(pq.getMax()) == 0) {
        return true;
      }

    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test09getMaxRemoveCheck: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print(
        "FAILED test09getMaxRemoveCheck: getMax removes the value instead of just returning the value");
    return false;
  }

  /**
   * Tests adding 10 random integers and that the getMax gets the right max
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if inserted items are returned in priority order
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test08insertOrder(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    Integer max = 0;

    try {
      // adding 10 random values between 1 and 9999, keeps track of the max
      Random rand = new Random();
      Integer randNum = 0;
      for (int i = 0; i < 10; i++) {
        randNum = rand.nextInt(9998) + 1;
        if (randNum > max) {
          max = randNum;
        }
        pq.insert(randNum);
      }
      // check that getMax is the max random number we added to the queue
      if (pq.getMax().compareTo(max) == 0) {
        return true;
      }

    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test08insertOrder: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print("FAILED test08insertOrder: getMax doesn't return correct value. Should return: " + max
        + " Instead returned: " + pq.getMax());
    return false;
  }

  /**
   * Tests adding values and checking that the isEmpty returns false
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if inserted items are returned in priority order
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test07insertTestEmpty(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<String> pq = newStringPQ(className);
    try {
      String testString1 = "one";
      String testString2 = "two";
      String testString3 = "three";

      pq.insert(testString1);
      pq.insert(testString2);
      pq.insert(testString3);

      if (pq.isEmpty() == false) {
        return true;
      }

    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test07insertTestEmpty: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print("FAILED test07insertTestEmpty: isEmpty not false after adding items");
    return false;
  }

  /**
   * Tests adding lots of values to ensure the structure resizes
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if inserted items are returned in priority order
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test06manyDataItems(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    try {
      for (int i = 0; i < 100000; i++) {
        pq.insert(i);
      }

      if (pq.isEmpty() == false) {
        return true;
      }
    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test06manyDataItems: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print("FAILED test06manyDataItems: structure doesn't resize when needed");
    return false;
  }

  /**
   * Tests adding duplicate values and make sure they are allowed
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if inserted items are returned in priority order
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test05duplicatesAllowed(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Double> pq = newDoublePQ(className);
    try {
      double testDouble1 = 1.11;

      pq.insert(testDouble1);
      pq.insert(testDouble1);

      if (pq.removeMax().compareTo(testDouble1) == 0) {
        if (pq.removeMax().compareTo(testDouble1) == 0) {
          return true;
        }
      }

    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test05duplicatesAllowed: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print("FAILED test05duplicatesAllowed: duplicate values not inserted");
    return false;
  }

  /**
   * Inserts many items and fails if removeMax does not return the max values in the priority order
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if inserted items are returned in priority order
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test04insertRemoveMany(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    String errorString = "";
    try {
      Integer testInt1 = 1;
      Integer testInt2 = 2;
      Integer testInt3 = 3;

      // adding values the return should be 3, 2, 1
      pq.insert(testInt1);
      pq.insert(testInt2);
      pq.insert(testInt3);

      Integer returnInt;
      returnInt = pq.removeMax();
      if (returnInt.compareTo(3) == 0) {
        returnInt = pq.removeMax();
        if (returnInt.compareTo(2) == 0) {
          returnInt = pq.removeMax();
          if (returnInt.compareTo(1) == 0) {
            return true;
          }
          else {
            errorString = errorString + "-" + returnInt; 
          }}
        else {
          errorString = errorString + "-" + returnInt;
        }}
      else {
        errorString = "" + returnInt;
      }
      

    }catch(Exception e) {
    if (DEBUG)
      e.printStackTrace();
    print("FAILED test04insertRemoveMany: unexpectedly threw " + e.getClass().getName());
    return false;
  }

  print("FAILED test04insertRemoveMany: inserted elements not removed in the right order" + 
      "Should return 3, 2, 1 instead returns " + errorString);
    return false;
  }

  /**
   * Confirm that inserting one item is returned by removeMax after it is called
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if removeMax returns the same value as inserted
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test03insertRemoveOne(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    Integer returnInt;
    Integer testInt = 16;
    try {
      pq.insert(testInt);
      returnInt = pq.removeMax();
      if (testInt.compareTo(returnInt) == 0) {
        return true;
      }

    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test03insertRemoveOne: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print(
        "FAILED test03insertRemoveOne: inserted element is not returned by removeMax. Should return " 
        + testInt + " instead returns " + returnInt);
    return false;
  }

  /**
   * Confirm that removeMax throws NoSuchElementException if called on an empty priority queue. Any
   * other exception indicates a fail.
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if removeMax on empty priority queue throws NoSuchElementException
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test02removeMaxEXCEPTION(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    try {
      pq.removeMax();
    } catch (NoSuchElementException e) {
      return true;
    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print(
        "FAILED test02removeMaxEXCEPTION: removeMax did not throw NoSuchElement exception on newly constructed PQ");
    return false;
  }

  /**
   * DO NOT EDIT -- provided as an example Confirm that getMax throws NoSuchElementException if
   * called on an empty priority queue. Any other exception indicates a fail.
   * 
   * @param className name of the priority queue implementation to test.
   * @return true if getMax on empty priority queue throws NoSuchElementException
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test01getMaxEXCEPTION(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    try {
      pq.getMax();
    } catch (NoSuchElementException e) {
      return true;
    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print(
        "FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
    return false;
  }

  /**
   * DO NOT EDIT THIS METHOD
   * 
   * @return true if able to construct Integer priority queue and the instance isEmpty.
   * 
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static boolean test00isEmpty(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    PriorityQueueADT<Integer> pq = newIntegerPQ(className);
    try {
      if (pq.isEmpty())
        return true;
    } catch (Exception e) {
      if (DEBUG)
        e.printStackTrace();
      print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
      return false;
    }
    print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
    return false;
  }

  /**
   * DO NOT EDIT THIS METHOD Constructs a max Priority Queue of Integer using the class that is
   * name.
   * 
   * @param className The specific Priority Queue to construct.
   * @return a PriorityQueue
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   */
  @SuppressWarnings({"unchecked"})
  public static final PriorityQueueADT<Integer> newIntegerPQ(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    Class<?> pqClass = Class.forName(className);
    Object obj = pqClass.newInstance();
    if (obj instanceof PriorityQueueADT) {
      return (PriorityQueueADT<Integer>) obj;
    }
    return null;
  }

  /**
   * DO NOT EDIT THIS METHOD Constructs a max Priority Queue of Double using the class that is
   * named.
   * 
   * @param className The specific Priority Queue to construct.
   * @return a PriorityQueue
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   */
  @SuppressWarnings({"unchecked"})
  public static final PriorityQueueADT<Double> newDoublePQ(final String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    Class<?> pqClass = Class.forName(className);
    Object obj = pqClass.newInstance();
    if (obj instanceof PriorityQueueADT) {
      return (PriorityQueueADT<Double>) obj;
    }
    return null;
  }

  /**
   * DO NOT EDIT THIS METHOD Constructs a max Priority Queue of String using the class that is
   * named.
   * 
   * @param className The specific Priority Queue to construct.
   * @return a PriorityQueue
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   */
  @SuppressWarnings({"unchecked"})
  public static final PriorityQueueADT<String> newStringPQ(final String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    Class<?> pqClass = Class.forName(className);
    Object obj = pqClass.newInstance();
    if (obj instanceof PriorityQueueADT) {
      return (PriorityQueueADT<String>) obj;
    }
    return null;
  }


  /**
   * DO NOT EDIT THIS METHOD Write the message to the standard output stream. Always adds a new line
   * to ensure each message is on its own line.
   * 
   * @param message Text string to be output to screen or other.
   */
  private static void print(String message) {
    System.out.println(message);
  }

}
