package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Queue<Data> extends ClearableContainer, InsertableContainer<Data>{ // Must extend ClearableContainer and InsertableContainer

  Data Head();
  void Dequeue();
  
  default Data HeadNDequeue(){
    Data tmp = Head();
    Dequeue();
    return tmp;
  }

  void Enqueue(Data dat);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  default void Clear(){
    while (!IsEmpty()) {
      Dequeue();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  default boolean Insert(Data dat){
    Enqueue(dat);
    return true;
  }

}
