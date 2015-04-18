package com.twosnail.basic.util.tree;

import java.util.ArrayList;
import java.util.List;
/**   
 * @Title: TreeNode.java
 * @Description: TODO
 * @author: 两只蜗牛
 * @date: 2015年4月18日 下午1:10:38
 * @version: V1.0
 */
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
