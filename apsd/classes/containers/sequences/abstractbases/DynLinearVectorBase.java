package apsd.classes.containers.sequences.abstractbases;

import apsd.interfaces.containers.sequences.DynVector;
import apsd.classes.utilities.Natural;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data>{ 

  public DynLinearVectorBase(){
    if (arr == null) arr = (Data[]) new Object[0];
  }

  public void ArrayAlloc(Natural newsize){
    long n = newsize.ToLong();
    if (n >= Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");
    arr = (Data[]) new Object[(int) n];
    size = 0;
  }

  public Natural Size(){
    return Natural.Of(size);
  }

  public void Clear(){
    for (int i = 0; i < size; i++) {
      arr[i] = null;
    }
    size = 0;
  }

  public void Realloc(Natural newCapacity){
    long capLong = newCapacity.ToLong();
    if (capLong >= Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");

    int cap = (int) capLong;
    Data[] newArr = (Data[]) new Object[cap];
    int copyCount = 0;
    if (arr != null) {
        copyCount = (int) Math.min(size, cap);
        if (copyCount > 0)  System.arraycopy(arr, 0, newArr, 0, copyCount);
    }

    arr = newArr;
    if (size > cap) size = cap;
  }

  public void Expand(Natural newCapacity){
    long cap = newCapacity.ToLong();
    if (cap < 0) throw new IllegalArgumentException("Capacità negativa");

    if (arr == null) {
        ArrayAlloc(newCapacity);
        return;
    }

    if (arr.length < cap) Realloc(newCapacity);
  }

  public void Reduce(Natural newCapacity){
    long cap = newCapacity.ToLong();
    if (arr != null && arr.length > cap)  Realloc(newCapacity);
  }

  // --- FIX IMPORTANTI ---

  @Override
  public void InsertAt(Data dat, Natural nat){
    if (nat == null) throw new NullPointerException("Indice nullo");
    long idx = nat.ToLong();
    
    // Controllo corretto: si può inserire da 0 fino a size (incluso, per append)
    if (idx < 0 || idx > size) throw new IndexOutOfBoundsException("Indice non valido: " + idx);

    // Gestione Resize
    if (size >= arr.length) {
        long newCap = (arr.length == 0) ? 1 : arr.length * 2;
        Realloc(Natural.Of(newCap));
    }

    // Shift solo se non stiamo inserendo in coda
    if (idx < size) {
        ShiftRight(nat);
    }

    // Inserimento e incremento size
    arr[(int)idx] = dat;
    size++;
  }

  @Override
  public Data AtNRemove(Natural nat){
    long idx = ExcIfOutOfBound(nat); // Questo controlla idx < size
    Data val = GetAt(nat);

    // Shift solo se non stiamo rimuovendo l'ultimo
    if (idx < size - 1) {
        ShiftLeft(nat);
    } else {
        arr[(int)idx] = null; // Pulizia riferimento
    }

    size--;
    return val;
  }
}