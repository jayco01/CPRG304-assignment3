package implementations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>
{
	
	private static final long serialVersionUID = 1L;
	
	private BSTreeNode<E> root;
	private int size;
	
	

	public BSTree() {
		super();
		this.root = null;
		this.size = 0;
	}

	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if (root == null) throw new NullPointerException("Binary Search Tree null");
		return root;
	}

	@Override
	public int getHeight() {
		return calculateHeight(root);
	}

	private int calculateHeight(BSTreeNode<E> node) {
		if (node == null) return 0;
		
		return 1 + Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight()));
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	@Override
	public boolean contains(E elementToSearch) throws NullPointerException {
		if (elementToSearch == null) throw new NullPointerException("Element to search is null.");
		
		return searchRecursive(root, elementToSearch) != null;
	}

	@Override
	public BSTreeNode<E> search(E elementToSearch) throws NullPointerException {
		if (elementToSearch == null) throw new NullPointerException("Unable to search Null element.");
		
		return searchRecursive(root, elementToSearch);
	}
	
	private BSTreeNode<E> searchRecursive(BSTreeNode<E> current, E elementToSearch) {
		if(current == null) return null; // if never found then return null
		
		int compare = elementToSearch.compareTo(current.getElement());
		
		if (compare == 0) {
			return current; // if found then return current
		}
		else if (compare < 0) {
			return searchRecursive(current.getLeft(), elementToSearch); // search left if elementToCompare is less than current
		} 
		else {
			return searchRecursive(current.getRight(), elementToSearch); // search right if elementToCompare is less than current
		}
		
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		if (newEntry == null) throw new NullPointerException("Entry cannot be null");
		
		if(root == null) {
			root = new BSTreeNode<>(newEntry);
			size++;
			return true;
		}
		
		return addRecursive(root, newEntry);
	}
	
	private boolean addRecursive(BSTreeNode<E> current, E newEntry) {
		int compare = newEntry.compareTo(current.getElement());
		
		if(compare == 0) {
			return false; //Duplicate found so do not add to BST
		} 
		else if(compare < 0) {
			if(current.getLeft() == null) {
				current.setLeft(new BSTreeNode<>(newEntry));
				size++;
				return true;
			}
			return addRecursive(current.getLeft(), newEntry);
		}
		else {
			if (current.getRight() == null) {
				current.setRight(new BSTreeNode<>(newEntry));
				size++;
				return true;
			}
			return addRecursive(current.getRight(), newEntry);
		}
		
	}


}
