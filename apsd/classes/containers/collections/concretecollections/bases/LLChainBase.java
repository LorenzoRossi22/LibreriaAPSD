package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<Data> implements Chain<Data>{ // Must implement Chain

  protected final MutableNatural size = new MutableNatural();
  protected final Box<LLNode<Data>> headref = new Box<>();
  protected final Box<LLNode<Data>> tailref = new Box<>();

  public LLChainBase(){}

  public LLChainBase(TraversableContainer<Data> con) {
    size.Assign(con.Size());
    final Box<Boolean> first = new Box<>(true);
    con.TraverseForward(dat -> {
      LLNode<Data> node = new LLNode<>(dat);
      if (first.Get()) {
        headref.Set(node);
        first.Set(false);
      } else {
        tailref.Get().SetNext(node);
      }
      tailref.Set(node);
      return false;
    });
  }

  public abstract LLChainBase<Data> NewChain(long a, LLNode<Data> node1, LLNode<Data> node2);

  /* ************************************************************************ */
  /* Specific member functions from LLChainBase                               */
  /* ************************************************************************ */

  public MutableForwardIterator<Box<LLNode<Data>>> FRefIterator(){
    return new MutableForwardIterator<Box<LLNode<Data>>>() {

      private Box<LLNode<Data>> cur = headref;
      private Box<LLNode<Data>> start = headref;

      public boolean IsValid() {
        return cur != null && cur.Get() != null;
      }

      public void Reset() {
        cur = start;
      }

      public Box<LLNode<Data>> GetCurrent() {
        if (!IsValid()) return null;
        return cur;
      }

      public Box<LLNode<Data>> DataNNext() {
        if (!IsValid()) return null;
        Box<LLNode<Data>> ret = cur;
        LLNode<Data> node = cur.Get();
        if (node != null) {
          cur = node.GetNext();
        } else {
          cur = new Box<>();
        }
        return ret;
      }

      public void SetCurrent(Box<LLNode<Data>> val) {
        if (!IsValid()) return;
        if (val == null) cur.Set(null);
        else cur.Set(val.Get());
      }
    };
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  public Natural Size(){
    return size.ToNatural();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  public void Clear(){
    headref.Set(null);
    tailref.Set(null);
    size.Assign(Natural.ZERO);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove(Data dat) {
    if (dat == null) return false;
    final Box<LLNode<Data>> prd = new Box<>();
    return FRefIterator().ForEachForward(cur -> {
      LLNode<Data> node = cur.Get();
      if (node.Get().equals(dat)) {
        cur.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(prd.Get()); }
        size.Decrement();
        return true;
      }
      prd.Set(node);
      return false;
    });
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  public ForwardIterator<Data> FIterator(){
    final MutableForwardIterator<Box<LLNode<Data>>> ref = FRefIterator();

    return new ForwardIterator<Data>() {

      public boolean IsValid() {
        return ref.IsValid();
      }

      public void Reset() {
        ref.Reset();
      }

      public Data GetCurrent() {
        Box<LLNode<Data>> box = ref.GetCurrent();
        if (box == null || box.Get() == null) return null;
        return box.Get().Get();
      }

      public Data DataNNext() {
        Box<LLNode<Data>> nextBox = ref.DataNNext();
        if (nextBox == null || nextBox.Get() == null) return null;
        return nextBox.Get().Get();
      }
    };
  }

  public BackwardIterator<Data> BIterator(){
    int n = (int) size.ToLong();
    @SuppressWarnings("unchecked")
    final Data[] arr = (Data[]) new Object[Math.max(0, n)];
    int idx = 0;

    MutableForwardIterator<Box<LLNode<Data>>> iter = FRefIterator();
    while (iter.IsValid()) {
        Box<LLNode<Data>> b = iter.GetCurrent();
        if (b != null && b.Get() != null) {
            arr[idx++] = b.Get().Get();
        }
        iter.DataNNext();
    }

    final int filled = idx;

    return new BackwardIterator<Data>() {
        int cur = filled - 1;

        public boolean IsValid() {
            return cur >= 0 && cur < arr.length;
        }

        public void Reset() {
            cur = filled - 1;
        }

        public Data GetCurrent() {
            if (!IsValid()) return null;
            return arr[cur];
        }

        public Data DataNPrev() {
            if (!IsValid()) return null;
            return arr[cur--];
        }
    };
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  public Sequence SubSequence(Natural start, Natural end){
    long s = Math.max(0, start.ToLong());
    long e = Math.min(size.ToLong(), end.ToLong());
    if (s > e) {
        long tmp = s;
        s = e;
        e = tmp;
    }

    int len = (int)(e - s);
    @SuppressWarnings("unchecked")
    Data[] tmpArr = (Data[]) new Object[len];

    long i = 0;
    int j = 0;
    MutableForwardIterator<Box<LLNode<Data>>> it = FRefIterator();
    while (it.IsValid() && i < s) {
        it.DataNNext();
        i++;
    }
    while (it.IsValid() && j < len) {
        Box<LLNode<Data>> b = it.DataNNext();
        tmpArr[j++] = (b != null && b.Get() != null) ? b.Get().Get() : null;
    }

    return new Sequence() {
        public Natural Size() {
            return Natural.Of(tmpArr.length);
        }

        public Object GetAt(Natural pos) {
            int p = (int) pos.ToLong();
            return (p >= 0 && p < tmpArr.length) ? tmpArr[p] : null;
        }

        public ForwardIterator FIterator() {
            return new ForwardIterator() {
                int index = 0;
                public boolean IsValid() { return index >= 0 && index < tmpArr.length; }
                public void Reset() { index = 0; }
                public Object GetCurrent() { return IsValid() ? tmpArr[index] : null; }
                public Object DataNNext() { return IsValid() ? tmpArr[index++] : null; }
            };
        }

        public BackwardIterator BIterator() {
            return new BackwardIterator() {
                int index = tmpArr.length - 1;
                public boolean IsValid() { return index >= 0 && index < tmpArr.length; }
                public void Reset() { index = tmpArr.length - 1; }
                public Object GetCurrent() { return IsValid() ? tmpArr[index] : null; }
                public Object DataNPrev() { return IsValid() ? tmpArr[index--] : null; }
            };
        }

        public boolean Apply(Object dat) {
            for (Data d : tmpArr) {
                if ((d == null && dat == null) || (d != null && d.equals(dat))) return true;
            }
            return false;
        }

        public Object Apply(Object dat, Object acc) {
            Object result = acc;
            for (Data val : tmpArr) {
                if (result instanceof Number && val instanceof Number) {
                    result = ((Number) result).doubleValue() + ((Number) val).doubleValue();
                } else {
                    result = String.valueOf(result) + String.valueOf(val);
                }
            }
            return result;
        }

        public Sequence SubSequence(Natural start, Natural end) {
          return LLChainBase.this.SubSequence( Natural.Of(Math.max(0, start.ToLong())), Natural.Of(Math.min(tmpArr.length, end.ToLong())) );
        }
    };
  }

  public Data GetFirst(){
    LLNode<Data> h = headref.Get();
    if (h == null) return null;
    return h.Get();
  }

  public Data GetLast(){
    LLNode<Data> t = tailref.Get();
    if (t == null) return null;
    return t.Get();
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  public Data AtNRemove(Natural nat){
    long idx = nat.ToLong();
    long sz = size.ToLong();

    if (idx < 0 || idx >= sz) {
        throw new IndexOutOfBoundsException("Indice non valido : " + idx);
    }

    if (idx == 0) {
        LLNode<Data> first = headref.Get();
        if (first == null) {
            return null;
        }

        Data val = first.Get();

        Box<LLNode<Data>> next = first.GetNext();
        if (next == null) {
            headref.Set(null);
        } else {
            headref.Set(next.Get());
        }
        if (tailref.Get() == first) tailref.Set(null);
        size.Decrement();
        return val;
    }

    Box<LLNode<Data>> prevBox = headref;

    for (long i = 0; i < idx - 1; i++) {
        LLNode<Data> node = prevBox.Get();
        if (node == null) {
            throw new IndexOutOfBoundsException("Indice non valido : " + i);
        }
        prevBox = node.GetNext();
    }

    LLNode<Data> prev = prevBox.Get();
    LLNode<Data> target = prev.GetNext().Get();
    Data val = target.Get();

    Box<LLNode<Data>> nextBox = target.GetNext();
    if (nextBox == null) {
        prev.SetNext(null);
    } else {
        prev.SetNext(nextBox.Get());
    }

    if (tailref.Get() == target) {
        tailref.Set(prev);
    }

    size.Decrement();
    return val;
  }

  public void RemoveFirst(){
    if (size.ToLong() == 0) return;
    AtNRemove(Natural.ZERO);
  }

  public void RemoveLast(){
    if (size.ToLong() == 0) return;
    AtNRemove(Natural.Of(size.ToLong() - 1));
  }

  public Data FirstNRemove(){
    if (size.ToLong() == 0) return null;
    return AtNRemove(Natural.ZERO);
  }

  public Data LastNRemove(){
    if (size.ToLong() == 0) return null;
    return AtNRemove(Natural.Of(size.ToLong() - 1));
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  public boolean Filter(Predicate<Data> fun){
    if (fun == null) return false;
    boolean changed = false;

    MutableForwardIterator<Box<LLNode<Data>>> it = FRefIterator();
    while (it.IsValid()) {
      Box<LLNode<Data>> cur = it.GetCurrent();
      LLNode<Data> node = cur.Get();
      Data d = node == null ? null : node.Get();
      boolean keep = fun.Apply(d);
      if (!keep) {
        Box<LLNode<Data>> nextBox = (node == null) ? null : node.GetNext();
        cur.Set(nextBox == null ? null : nextBox.Get());
        if (tailref.Get() == node) {
          LLNode<Data> t = headref.Get();
          LLNode<Data> prev = null;
          while (t != null && t.GetNext() != null && t.GetNext().Get() != null) {
            prev = t;
            t = t.GetNext().Get();
          }
          tailref.Set(prev);
        }
        size.Decrement();
        changed = true;

      } else {
        it.DataNNext();
      }
    }
    return changed;
  }

}
