package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data>{ // Must extend ClearableContainer and InsertableContainer

  Data Top();
  void Pop();

  default Data TopNPop(){
    Data temp = Top();
    Pop();
    return temp;
  }

  default void SwapTop(Data dat){
    Pop();
    Push(dat);
  }
  
  default Data TopNSwap(Data dat){
    Data tmp = Top();
    Pop();
    Push(dat);
    return tmp;
  }

  void Push(Data dat);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  default void Clear(){
    while (Size().ToLong() > 0) {
      Pop();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  default boolean Insert(Data dat){
    Push(dat);
    return true;
  }
}
