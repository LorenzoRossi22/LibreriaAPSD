package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> {

  public LLList(){
    super();
  }

  public LLList(TraversableContainer<Data> con){
    super();
    if (con != null) {
      con.TraverseForward(elem -> {
          InsertLast(elem);
          return false;
      });
    }
  }

  protected LLList(long size, LLNode<Data> head, LLNode<Data> tail){
    super();
    headref.Set(head);
    tailref.Set(tail);
    this.size.Assign(Natural.Of(size));
  }

  // FIX CRITICO: Deve ritornare boolean per rispettare l'interfaccia InsertableContainer
  @Override
  public boolean Insert(Data x) {
      InsertLast(x);
      return true; // Ritorna sempre true dopo l'inserimento
  }

  // FIX: GetFirst deve lanciare eccezione se vuota
  @Override
  public Data GetFirst() {
      if (IsEmpty()) throw new IndexOutOfBoundsException("Lista vuota");
      return super.GetFirst();
  }

  public LLChainBase<Data> NewChain(long a, LLNode<Data> node1, LLNode<Data> node2){
    return new LLList<>(a, node1, node2);
  }

  public boolean Apply(Data dat) {
    if (dat == null) return false;
    LLNode<Data> cur = headref.Get();
    while (cur != null) {
      if (dat.equals(cur.Get())) return true;
      Box<LLNode<Data>> nextBox = cur.GetNext();
      cur = nextBox == null ? null : nextBox.Get();
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    Object result = acc;
    LLNode<Data> cur = headref.Get();
    while (cur != null) {
        Object elem = cur.Get();
        if (result instanceof Number && elem instanceof Number) {
          result = ((Number) result).doubleValue() + ((Number) elem).doubleValue();
        } else {
          result = String.valueOf(result) + String.valueOf(elem);
        }
        Box<LLNode<Data>> nextBox = cur.GetNext();
        cur = nextBox == null ? null : nextBox.Get();
    }
    return result;
  }

  public void InsertAt(Data dat, Natural nat) {
    if (nat == null || dat == null) return;
    long index = nat.ToLong();
    if (index <= 0) {
        InsertFirst(dat);
        return;
    }
    if (index >= size.ToLong()) {
        InsertLast(dat);
        return;
    }
    LLNode<Data> cur = headref.Get();
    LLNode<Data> prev = null;
    for (long i = 0; i < index; i++) {
        prev = cur;
        cur = cur.GetNext().Get();
    }
    LLNode<Data> newNode = new LLNode<>(dat);
    prev.SetNext(newNode);
    newNode.SetNext(cur);
    size.Increment();
  }

  public List<Data> New() {
    return new LLList<>();
  }

  public MutableBackwardIterator BIterator(){
    return new MutableBackwardIterator() {
      long index = size.ToLong() - 1;
      long lastIndex = -1;

      public Object DataNPrev() {
          if (!IsValid()) return null;
          lastIndex = index;
          LLNode<Data> cur = headref.Get();
          for (long i = 0; i < index; i++) cur = cur.GetNext().Get();
          index--;
          return cur.Get();
      }

      public boolean IsValid() {
          return index >= 0;
      }

      public void Reset() {
          index = size.ToLong() - 1;
          lastIndex = -1;
      }

      public Object GetCurrent() {
          if (lastIndex == -1) return null;
          LLNode<Data> cur = headref.Get();
          for (long i = 0; i < lastIndex; i++) cur = cur.GetNext().Get();
          return cur.Get();
      }

      public void SetCurrent(Object dat) {
          if (lastIndex == -1) return;
          LLNode<Data> cur = headref.Get();
          for (long i = 0; i < lastIndex; i++) cur = cur.GetNext().Get();
          cur.Set((Data) dat);
      }
    };
  }

  public MutableForwardIterator FIterator(){
    return new MutableForwardIterator() {
      long index = 0;
      long lastIndex = -1;

      public Object DataNNext() {
          if (!IsValid()) return null;
          lastIndex = index;
          LLNode<Data> cur = headref.Get();
          for (long i = 0; i < index; i++) cur = cur.GetNext().Get();
          index++;
          return cur.Get();
      }

      public boolean IsValid() {
          return index < size.ToLong();
      }

      public void Reset() {
          index = 0;
          lastIndex = -1;
      }

      public Object GetCurrent() {
          if (lastIndex == -1) return null;
          LLNode<Data> cur = headref.Get();
          for (long i = 0; i < lastIndex; i++) cur = cur.GetNext().Get();
          return cur.Get();
      }

      public void SetCurrent(Object dat) {
          if (lastIndex == -1) return;
          LLNode<Data> cur = headref.Get();
          for (long i = 0; i < lastIndex; i++) cur = cur.GetNext().Get();
          cur.Set((Data) dat);
      }
    };
  }

  public void SetAt(Data dat, Natural nat){
    if (nat == null || dat == null) throw new NullPointerException();
    long index = nat.ToLong();
    LLNode<Data> cur = headref.Get();
    for (long i = 0; i < index && cur != null; i++) cur = cur.GetNext().Get();
    if (cur != null) cur.Set(dat);
  }

  public void SetFirst(Data dat){
    if (headref.Get() != null) headref.Get().Set(dat);
  }

  public void SetLast(Data dat){
    if (tailref.Get() != null) tailref.Get().Set(dat);
  }

  public MutableSequence<Data> Subsequence(Natural start, Natural end){
    LLList<Data> sub = new LLList<>();
    for (long i = start.ToLong(); i < end.ToLong(); i++) {
      LLNode<Data> cur = headref.Get();
      for (long j = 0; j < i; j++) cur = cur.GetNext().Get();
      sub.InsertLast(cur.Get());
    }
    return sub;
  }

  public void InsertFirst(Data dat){
    if (dat == null) return;
    LLNode<Data> newNode = new LLNode<>(dat);
    newNode.SetNext(headref.Get());
    headref.Set(newNode);
    if (tailref.Get() == null) tailref.Set(newNode);
    size.Increment();
  }

  public void InsertLast(Data dat){
    if (dat == null) return;
    LLNode<Data> newNode = new LLNode<>(dat);
    if (tailref.Get() != null) {
        tailref.Get().SetNext(newNode);
    } else {
        headref.Set(newNode);
    }
    tailref.Set(newNode);
    size.Increment();
  }
}