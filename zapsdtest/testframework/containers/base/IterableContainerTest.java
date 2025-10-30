package zapsdtest.testframework.containers.base;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.BackwardIterator;

import static org.junit.jupiter.api.Assertions.*;

public interface IterableContainerTest<Data, Con extends IterableContainer<Data>> extends TraversableContainerTest<Data, Con> {

  default void TestFIterator() {
    BeginTest("FIterator");
    ForwardIterator<Data> iterator = ThisContainer().FIterator();
    assertNotNull(iterator, "FIterator should return a non null ForwardIterator");
    EndTest();
  }

  default void TestBIterator() {
    BeginTest("BIterator");
    BackwardIterator<Data> iterator = ThisContainer().BIterator();
    assertNotNull(iterator, "FIterator should return a non null BackwardIterator");
    EndTest();
  }

  default void TestIsEqualSelf() {
    BeginTest("IsEqualSelf");
    boolean result = ThisContainer().IsEqual(ThisContainer());
    assertTrue(result, "Container should be equal to itself");
    EndTest();
  }

  default void TestIsEqual(IterableContainer<Data> otherContainer, boolean shouldWork) {
    BeginTest("IsEqual");
    boolean result = ThisContainer().IsEqual(otherContainer);
    if (shouldWork) {
      assertTrue(result, "IsEqual should return true");
    } else {
      assertFalse(result, "IsEqual should return false");
    }
    EndTest();
  }

  default void TestForwardIteration(boolean canBeNull, boolean edgeCase) {
    BeginTest("ForwardIteration");
    ForwardIterator<Data> iterator = ThisContainer().FIterator();
    if (edgeCase) {
      assertFalse(iterator.IsValid(), "Iterator is not valid");
    } else {
      int count = 0;
      while (iterator.IsValid()) {
        if (!canBeNull) assertNotNull(iterator.GetCurrent(), "Iterated element should not be null");
        iterator.Next();
        count++;
      }
      assertEquals(ThisContainer().Size().ToLong(), count,
      "Should iterate over all elements forwards");
    }
    EndTest();
  }

  default void TestBackwardIteration(boolean canBeNull, boolean edgeCase) {
    BeginTest("BackwardIteration");
    BackwardIterator<Data> iterator = ThisContainer().BIterator();
    if (edgeCase) {
      assertFalse(iterator.IsValid(), "Iterator is not valid");
    } else {
      int count = 0;
      while (iterator.IsValid()) {
        if (!canBeNull) assertNotNull(iterator.GetCurrent(), "Iterated element should not be null");
        iterator.Prev();
        count++;
      }
      assertEquals(ThisContainer().Size().ToLong(), count,
      "Should iterate over all elements forwards");
    }
    EndTest();
  }

}
