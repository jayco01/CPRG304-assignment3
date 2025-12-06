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
	
    public BSTree(E element) {
        this.root = new BSTreeNode<>(element);
        this.size = 1;
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
	
	@Override
	public BSTreeNode<E> removeMin() {
		if (root == null) return null;
		
		if(root.getLeft() == null) {
			BSTreeNode<E> min = root;
			root = root.getRight();
			size++;
			return min;
		}
		return removeMinRecursive(null, root);
	}
	
	private BSTreeNode<E> removeMinRecursive(BSTreeNode<E> parent, BSTreeNode<E> current) {
		if (current.getLeft() == null) {
			parent.setLeft(current.getRight());
			size--;
			
			return current;
		}
		return removeMinRecursive(current, current.getLeft());
	}

	@Override
	public BSTreeNode<E> removeMax() {
		if(root == null) return null;
		
		if(root.getRight() == null) {
			BSTreeNode<E> max = root;
			root = root.getLeft();
			size--;
			return max;
		}
		
		return removeMaxRecursive(null, root);
	}
	
	private BSTreeNode<E> removeMaxRecursive(BSTreeNode<E> parent, BSTreeNode<E> current) {
        if (current.getRight() == null) {
            parent.setRight(current.getLeft());
            size--;
            return current;
        }
        return removeMaxRecursive(current, current.getRight());
    }


    @Override
    public Iterator<E> inorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        inorderRecursive(root, list);
        return new BSTIterator(list);
    }

    private void inorderRecursive(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null) return;
        inorderRecursive(node.getLeft(), list);
        list.add(node.getElement());
        inorderRecursive(node.getRight(), list);
    }

    @Override
    public Iterator<E> preorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        preorderRecursive(root, list);
        return new BSTIterator(list);
    }

    private void preorderRecursive(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null) return;
        list.add(node.getElement());
        preorderRecursive(node.getLeft(), list);
        preorderRecursive(node.getRight(), list);
    }

    @Override
    public Iterator<E> postorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        postorderRecursive(root, list);
        return new BSTIterator(list);
    }

    private void postorderRecursive(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null) return;
        postorderRecursive(node.getLeft(), list);
        postorderRecursive(node.getRight(), list);
        list.add(node.getElement());
    }

    private class BSTIterator implements Iterator<E> {
        private ArrayList<E> list;
        private int position;

        public BSTIterator(ArrayList<E> list) {
            this.list = list;
            this.position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < list.size();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();
            return list.get(position++);
        }
    }


}
