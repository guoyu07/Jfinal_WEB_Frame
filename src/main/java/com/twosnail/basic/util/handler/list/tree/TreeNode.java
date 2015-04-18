package com.twosnail.basic.util.handler.list.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

	private T t;
	
	private ArrayList<TreeNode<T>> array;
	
	TreeNode( T t ) {
		this.t = t;
		this.array = new ArrayList<TreeNode<T>>();
	}

	public T get() {
		return t;
	}
	
	public List<TreeNode<T>> getChildren() {
		return array;
	}
	
	void addChild( TreeNode<T> node ) {
		this.array.add( node );
	}
	
}
