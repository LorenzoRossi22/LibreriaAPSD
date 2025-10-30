package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XListITest;

public class LLListITest extends XListITest {

  public LLListITest() {
    System.out.println("**** LLListI ***************************************");
    name = "06 - LLListI";
  }

  @Override
  public void NewEmptyContainer() { container = new LLList<>(); }

  @Override
  public LLList<Long> GetNewEmptyContainer() { return new LLList<>(); }

  public LLList<Long> GetNewNonEmptyContainer(TraversableContainer<Long> con) { return new LLList<>(con); }

}
