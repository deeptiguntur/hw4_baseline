package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private ArrayList<ExpenseTrackerModelListener> listeners = new ArrayList<ExpenseTrackerModelListener>();

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    // Calling stateChanged to update view from model
    stateChanged();

  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    // Calling stateChanged to update view from model
    stateChanged();
  }

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      // Calling stateChanged to update view from model
      stateChanged();
  }

  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      // Check if the listener is non-null and not already registered
      if (listener != null && !listeners.contains(listener)) {
          // Register the listener
          listeners.add(listener);
          return true;
      }
      // Listener is either null or already registered
      return false;
  }

  
  /**
   * Unregisters the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be unregistered
   * @return If the listener is non-null and already registered,
   *         then it will unregister
   */
  public void unregister(ExpenseTrackerModelListener listener) {
      // Check if the listener is non-null and  already registered
      if (listener != null && listeners.contains(listener)) {
          // Remove the listener
          listeners.remove(listener);
      }
  }

  /**
   * Returns the number of listeners registered
   *
   * @return Will return the size of the listener array
   */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      // Getting count of listeners
      int count = listeners.size();
      return count;
  }

  /**
   * Checks if listener is already registered
   *
   * @param listener The ExpenseTrackerModelListener to be checked
   * @return Will return True if listener is registered or else false
   */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      // Checking if listener exists
      if (listeners.contains(listener)) {
          return true;
      }
      return false;
  }

  /**
   * For every listener it will call the update function to notify about the changes
   */
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      // Notify all registered observers (listeners) about the state change
      for (ExpenseTrackerModelListener listener : listeners) {
        listener.update(this);
      }
  }
}
