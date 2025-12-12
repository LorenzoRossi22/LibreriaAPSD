package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.sequences.SortedSequence;
import apsd.classes.utilities.Natural;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data>{ // Must extend OrderedChain and SortedSequence

  default Natural SearchPredecessor(Data dat){
    if (dat == null || Size().ToLong() == 0)  return null;

    long left = 0;
    long right = Size().ToLong() - 1;
    long result = -1;

    while (left <= right) {
        long mid = (left + right) / 2;
        Data midVal = GetAt(Natural.Of(mid));
        int cmp = midVal.compareTo(dat);

        if (cmp < 0) {
            result = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    if (result >= 0) {
        return Natural.Of(result);
    } else {
        return null;
    }
  }

  default Natural SearchSuccessor(Data dat){
    if (dat == null || Size().ToLong() == 0)  return null;

    long left = 0;
    long right = Size().ToLong() - 1;
    long result = -1;

    while (left <= right) {
        long mid = (left + right) / 2;
        Data midVal = GetAt(Natural.Of(mid));
        int cmp = midVal.compareTo(dat);

        if (cmp > 0) {
            result = mid;
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    if (result >= 0) {
        return Natural.Of(result);
    } else {
        return null;
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  default Natural Search(Data dat){
    if (dat == null || Size().ToLong() == 0)  return null;

    long left = 0;
    long right = Size().ToLong() - 1;

    while (left <= right) {
        long mid = (left + right) / 2;
        Data midVal = GetAt(Natural.Of(mid));
        int cmp = midVal.compareTo(dat);

        if (cmp == 0) {
            return Natural.Of(mid);
        } else if (cmp < 0) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  default void Intersection(SortedChain<Data> chn) {
    Natural i = Natural.ZERO, j = Natural.ZERO;
    while (i.compareTo(Size()) < 0 && j.compareTo(chn.Size()) < 0) {
      int cmp = GetAt(i).compareTo(chn.GetAt(j));
      if (cmp < 0) {
        RemoveAt(i);
      } else {
        j = j.Increment();
        if (cmp == 0) { i = i.Increment(); }
      }
    }
    while (i.compareTo(Size()) < 0) {
      RemoveAt(i);
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  default Data Min(){
    if (Size().ToLong() == 0) return null;
    return GetAt(Natural.ZERO);
  }

  default Data Max(){
    if (Size().ToLong() == 0) return null;
    return GetAt(Natural.Of(Size().ToLong() - 1));
  }

  default void RemoveMin(){
    Data min = Min();
    if (min != null) Remove(min);
  }

  default void RemoveMax(){
    Data max = Max();
    if (max != null) Remove(max);
  }

  default Data MinNRemove(){
    Data min = Min();
    if (min != null) Remove(min);
    return min;
  }

  default Data MaxNRemove(){
    Data max = Max();
    if (max != null) Remove(max);
    return max;
  }

  default Data Predecessor(Data dat){
    if (dat == null) return null;

    Data pred = null;
    Natural i = Natural.ZERO;
    boolean cont = false;
    while (i.compareTo(Size()) < 0 && cont == false) {
        Data cur = GetAt(i);
        if (cur.compareTo(dat) < 0) {
            pred = cur;
        } else {
            cont = true;
        }
        i = i.Increment();
    }
    return pred;
  }

  default Data Successor(Data dat){
    for (Natural i = Natural.ZERO; i.compareTo(Size()) < 0; i = i.Increment()) {
        if (GetAt(i).compareTo(dat) > 0) return GetAt(i);
    }
    return null;
  }

  default void RemovePredecessor(Data dat){
    Data pred = Predecessor(dat);
    if(pred != null) Remove(pred);
  }

  default void RemoveSuccessor(Data dat){
    Data succ = Successor(dat);
    if(succ!=null) Remove(succ);
  }

  default Data PredecessorNRemove(Data dat){
    Data pred = Predecessor(dat);
    if(pred != null) Remove(pred);
    return pred;
  }

  default Data SuccessorNRemove(Data dat){
    Data succ = Successor(dat);
    if(succ!=null) Remove(succ);
    return succ;
  }

}