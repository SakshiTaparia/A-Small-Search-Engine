class AVLtreeNode_PageEntry
{
	public String word;
	public Integer index;
	public AVLtreeNode_PageEntry Parent;
	public AVLtreeNode_PageEntry LeftChild;
	public AVLtreeNode_PageEntry RightChild;
	public Integer Height;

	public AVLtreeNode_PageEntry (String word, Integer index)
	{
		this.word = word;
		this.index = index;
		Parent = null;
		LeftChild = null;
		RightChild = null;
		Height = -1;
	}
}

// ASSUMPTIONS -
// There are no duplicates - left is strictly less and right is strictly more

class AVLtree_PageEntry
{
	AVLtreeNode_PageEntry root;

	public AVLtree_PageEntry ()
	{
		root = null;
	}

	public AVLtree_PageEntry (AVLtreeNode_PageEntry root)
	{
		this.root = root;
	}

	public boolean AVLtreeEmpty ()
	{return ( root.Height == -1 );}

	public boolean IsExternal (AVLtreeNode_PageEntry Node)
	{return (Node.Height == 0);}

	public boolean IsRoot (AVLtreeNode_PageEntry Node)
	{return (Node.Parent == null);}

	public Integer UpdateHeightTillNode (AVLtreeNode_PageEntry Node)
	{
		if (Node == null)
			return -1;

		Node.Height = Math.max(UpdateHeightTillNode(Node.LeftChild), UpdateHeightTillNode (Node.RightChild)) + 1;
		return (Node.Height);
	}

	public AVLtreeNode_PageEntry FindElement(Integer value)
	{
		if (root.index == value)
			return root;

		else if (root.index > value)
		{
			if (root.LeftChild == null)
				return null;

			AVLtree_PageEntry LeftSubtree = new AVLtree_PageEntry (root.LeftChild);
			return (LeftSubtree.FindElement(value));
		}

		else
		{
			if (root.RightChild == null)
				return null;

			AVLtree_PageEntry RightSubtree = new AVLtree_PageEntry (root.RightChild);
			return (RightSubtree.FindElement(value));
		}
	}

	public AVLtreeNode_PageEntry GetParentForInsertion (Integer value)
	{
		if (root.Height == 0)
			return root;

		else if (root.index > value)
		{
			if (root.LeftChild == null)
				return root;

			AVLtree_PageEntry LeftSubtree = new AVLtree_PageEntry (root.LeftChild);
			return (LeftSubtree.GetParentForInsertion(value));
		}

		else
		{
			if (root.RightChild == null)
				return null;

			AVLtree_PageEntry RightSubtree = new AVLtree_PageEntry (root.RightChild);
			return (RightSubtree.GetParentForInsertion(value));
		}
	}

	public AVLtreeNode_PageEntry GetInOrderSuccessor (AVLtreeNode_PageEntry Node)
	{
		if (Node.RightChild == null)
		{
			AVLtreeNode_PageEntry InOrderSuccessor = Node;

			while ( Node.Parent != null)
			{
				if (Node.Parent.index > InOrderSuccessor.index)
					return Node.Parent;

				Node = Node.Parent;
			}
			
// there is no successor
			return (null);
		}

		else
		{
			AVLtreeNode_PageEntry InOrderSuccessor = Node.RightChild;

			while (InOrderSuccessor.LeftChild != null)
				InOrderSuccessor = InOrderSuccessor.LeftChild;

			return (InOrderSuccessor);
		}
	}

	public void insert ( String word, Integer index)
	{
		AVLtreeNode_PageEntry NewNode = new AVLtreeNode_PageEntry(word,index);

		if (root == null)
		{
			root = NewNode;
			root.Height = UpdateHeightTillNode(root);
			return;
		}

		NewNode.Parent = this.GetParentForInsertion(NewNode.index);

		if (NewNode.index < NewNode.Parent.index)
			NewNode.Parent.LeftChild = NewNode;

		else
			NewNode.Parent.RightChild = NewNode;

		root.Height = UpdateHeightTillNode(root);

		// Rotations - 4 cases

		AVLtreeNode_PageEntry z = NewNode;

		do 
		{
			z = z.Parent;

			if (z.LeftChild == null && z.RightChild == null)
				{}

			else if (z.LeftChild != null && z.RightChild == null)
			{
				if (z.LeftChild.Height == 0)
					{}

				else
				{
					if (z.LeftChild.LeftChild == null)
						this.RightOfLeftChild(z, z.LeftChild, z.LeftChild.RightChild);

					else if (z.LeftChild.RightChild == null)
						this.LeftOfLeftChild(z, z.LeftChild, z.LeftChild.LeftChild);
					
					else
					{
						if (z.LeftChild.LeftChild.Height < z.LeftChild.RightChild.Height)
						this.RightOfLeftChild(z, z.LeftChild, z.LeftChild.RightChild);
						
						else
						this.LeftOfLeftChild(z, z.LeftChild, z.LeftChild.LeftChild);	
					}
				}
			}

			else if (z.LeftChild == null && z.RightChild != null)
			{
				if (z.RightChild.Height == 0)
					{}

				else
				{
					if (z.RightChild.LeftChild == null)
						this.RightofRightChild(z, z.RightChild, z.RightChild.RightChild);

					else if (z.RightChild.RightChild == null)
						this.LeftOfRightChild(z, z.RightChild, z.RightChild.LeftChild);
					else
					{
						if (z.RightChild.LeftChild.Height < z.RightChild.RightChild.Height)
						this.RightofRightChild(z, z.RightChild, z.RightChild.RightChild);
						
						else
						this.LeftOfRightChild(z, z.RightChild, z.RightChild.LeftChild);	
					}
				}
			}

	        else
	        {
	        	if (z.LeftChild.Height - z.RightChild.Height < 2 && z.LeftChild.Height - z.RightChild.Height > -2)
	        		{}

	        	else 
	        	{
	        		if (z.LeftChild.Height > z.RightChild.Height)
	        		{
	        			if (z.LeftChild.LeftChild.Height > z.LeftChild.RightChild.Height)
	        				this.LeftOfLeftChild(z, z.LeftChild, z.LeftChild.LeftChild);
	        			else
	        				this.RightOfLeftChild(z, z.LeftChild, z.LeftChild.RightChild);
	        		}

	        		else 
	        		{
	        			if (z.RightChild.LeftChild.Height > z.RightChild.RightChild.Height)
	        				this.LeftOfRightChild(z, z.RightChild, z.RightChild.LeftChild);
	        			else
	        				this.RightofRightChild(z, z.RightChild, z.RightChild.RightChild);
	        		}
	        	}
	        }

		} while (z.Parent != null);

		return;
	}

	public void LeftOfLeftChild (AVLtreeNode_PageEntry z, AVLtreeNode_PageEntry y, AVLtreeNode_PageEntry x)
	{

		if (this.IsRoot(z) == true)
			root = y;
		else
		{
			if (z.Parent.RightChild == z)
				z.Parent.RightChild = y;
			else
				z.Parent.LeftChild = y;
		}

		y.Parent = z.Parent;
		z.LeftChild = y.RightChild;
		y.RightChild = z;
		z.Parent = y;

		root.Height = UpdateHeightTillNode(root);
	}

	public void RightofRightChild (AVLtreeNode_PageEntry z, AVLtreeNode_PageEntry y, AVLtreeNode_PageEntry x)
	{

		if (this.IsRoot(z) == true)
			root = y;
		else
		{
			if (z.Parent.RightChild == z)
				z.Parent.RightChild = y;
			else
				z.Parent.LeftChild = y;
		}

		y.Parent = z.Parent;
		z.RightChild = y.LeftChild; 
		y.LeftChild = z;
		z.Parent = y;



		root.Height = UpdateHeightTillNode(root);
	}

	public void RightOfLeftChild (AVLtreeNode_PageEntry z, AVLtreeNode_PageEntry y, AVLtreeNode_PageEntry x)
	{

		if (this.IsRoot(z) == true)
			root = x;
		else
		{
			if (z.Parent.RightChild == z)
				z.Parent.RightChild = x;
			else
				z.Parent.LeftChild = x;
		}

		y.RightChild = x.LeftChild;
		z.LeftChild = x.RightChild;
		x.Parent = z.Parent;
		z.Parent = x;
		x.RightChild = z;
		y.Parent = x;
		x.LeftChild = y;

		root.Height = UpdateHeightTillNode(root);
	}

	public void LeftOfRightChild (AVLtreeNode_PageEntry z, AVLtreeNode_PageEntry y, AVLtreeNode_PageEntry x)
	{

		if (this.IsRoot(z) == true)
			root = x;
		else
		{
			if (z.Parent.RightChild == z)
				z.Parent.RightChild = x;
			else
				z.Parent.LeftChild = x;
		}

		y.LeftChild = x.RightChild;
		z.RightChild = x.LeftChild;
		x.Parent = z.Parent;
		z.Parent = x;
		x.LeftChild = z;
		y.Parent = x;
		x.RightChild = y;

		root.Height = UpdateHeightTillNode(root);
	}

	public void InOrderTraversal(AVLtreeNode_PageEntry root)
	{
		if (root == null)
			return;

		InOrderTraversal (root.LeftChild);
		System.out.print(root.index + " ");
		InOrderTraversal (root.RightChild);
	}
}