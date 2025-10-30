package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections.generic.XSortedChainITest;

public class LLSortedChainITest extends XSortedChainITest {

  public LLSortedChainITest() {
    System.out.println("**** LLSortedChainI ********************************");
    name = "08 - LLSortedChainI";
  }

  @Override
  public void NewEmptyContainer() { container = new LLSortedChain<>(); }

  @Override
  public LLSortedChain<Long> GetNewEmptyContainer() { return new LLSortedChain<>(); }

  public LLSortedChain<Long> GetNewNonEmptyContainer(TraversableContainer<Long> con) { return new LLSortedChain<>(con); }

}
