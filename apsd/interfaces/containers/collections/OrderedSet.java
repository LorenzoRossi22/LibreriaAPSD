package apsd.interfaces.containers.collections;

public interface OrderedSet<Data> extends Set<Data>{ // Must extend Set

  default Data Min(){
    var iterator = FIterator();
    if (!iterator.IsValid()) return null;

    Data min = iterator.GetCurrent();
    iterator.Next();

    while (iterator.IsValid()) {
        Data cur = iterator.GetCurrent();
        if (((Comparable<Data>)cur).compareTo(min) < 0) {
            min = cur;
        }
        iterator.Next();
    }
    return min;
  }

  default void RemoveMin(){
    Data min = Min();
    if (min != null) Remove(min);
  }

  default Data MinNRemove(){
    Data min = Min();
    if (min != null) Remove(min);
    return min;
  }

  default Data Max(){
    var iterator = FIterator();
    if (!iterator.IsValid()) return null;

    Data max = iterator.GetCurrent();
    iterator.Next();

    while (iterator.IsValid()) {
        Data cur = iterator.GetCurrent();
        if (((Comparable<Data>)cur).compareTo(max) > 0) {
            max = cur;
        }
        iterator.Next();
    }
    return max;
  }

  default void RemoveMax(){
    Data max = Max();
    if(max!=null) Remove(max);
  }

  default Data MaxNRemove(){
    Data max = Max();
    if(max!=null) Remove(max);
    return max;
  }

  default Data Predecessor(Data dat){
    if (dat == null) return null;

    var iterator = FIterator();
    Data pred = null;

    while (iterator.IsValid()) {
        Data cur = iterator.GetCurrent();
        if (((Comparable<Data>)cur).compareTo(dat) < 0) {
            if (pred == null ||
                ((Comparable<Data>)cur).compareTo(pred) > 0) {
                pred = cur;
            }
        }
        iterator.Next();
    }
    return pred;
  }

  default void RemovePredecessor(Data dat){
    Data pred = Predecessor(dat);
    if (pred != null) Remove(pred);
  }

  default Data PredecessorNRemove(Data dat){
    Data pred = Predecessor(dat);
    if (pred != null) Remove(pred);
    return pred;
  }

  default Data Successor(Data dat){
    if (dat == null) return null;

    var iterator = FIterator();
    Data succ = null;

    while (iterator.IsValid()) {
        Data cur = iterator.GetCurrent();
        if (((Comparable<Data>)cur).compareTo(dat) > 0) {
            if (succ == null ||
                ((Comparable<Data>)cur).compareTo(succ) < 0) {
                succ = cur;
            }
        }
        iterator.Next();
    }
    return succ;
  }

  default void RemoveSuccessor(Data dat){
    Data succ = Successor(dat);
    if (succ != null) Remove(succ);
  }

  default Data SuccessorNRemove(Data dat){
    Data succ = Successor(dat);
    if (succ != null) Remove(succ);
    return succ;
  }

}
