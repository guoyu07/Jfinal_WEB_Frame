package com.twosnail.basic.util.handler.list.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 构建树结构数据
 * @author acer
 *
 */
public class TreeList {

	private TreeList() {}
	
	public static <T> List<TreeNode<T>> sort( List<T> list, SortHandler<T> handler ) {
		
		if( list == null || list.size() == 0 ) {
			return null;
		}
		
		ArrayList<TreeNode<T>> nodeList = new ArrayList<TreeNode<T>>();
		HashMap<Long,TreeNode<T>> nodeMap = new HashMap<Long,TreeNode<T>>();
		ArrayList<TreeNode<T>> unDealNodes = new ArrayList<TreeNode<T>>();
		
		long parentId = 0;
		TreeNode<T> parent = null;
		TreeNode<T> node = null;
		for( T t : list ) {
			node = new TreeNode<T>( t );
			parentId = handler.getParentId( t );
			if( parentId <= 0 ) {
				nodeList.add( node );
			} else {
				parent = nodeMap.get( parentId );
				if( parent == null ) {
					//最后在处理
					unDealNodes.add( node );
				} else {
					parent.addChild( node );
				}
			}
			
			nodeMap.put( handler.getId( t ), node );
		}
		
		int lastSize = unDealNodes.size();
		if( lastSize > 0 ) {
			Iterator<TreeNode<T>> it = null;
			while( true ) {
				it = unDealNodes.iterator();
				while( it.hasNext() ) {
					node = it.next();
					parent = nodeMap.get( handler.getId( node.get() ) );
					if( parent != null ) {
						parent.addChild( node );
						it.remove();
					}
				}
				if( lastSize == unDealNodes.size() ) {
					for( TreeNode<T> n : unDealNodes ) {
						nodeList.add( n );
					}
					break;
				} else {
					lastSize = unDealNodes.size();
				}
			}
		}
		
		return nodeList;
	}

	public static interface SortHandler<T> {
		
		/**
		 * 获得当前节点的id
		 * @param <T>
		 * @param t
		 * @return
		 */
		public long getId( T t );
		/**
		 * 获得父节点的id
		 * @param <T>
		 * @param t
		 * @return
		 */
		public long getParentId( T t );
	}
	
}
