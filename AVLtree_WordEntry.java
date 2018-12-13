class AVLtreeNode_WordEntry
{
	public Position position;
	public AVLtreeNode_WordEntry Parent;
	public AVLtreeNode_WordEntry LeftChild;
	public AVLtreeNode_WordEntry RightChild;
	public Integer Height;

	public AVLtreeNode_WordEntry (Position position)
	{
		this.position = position;
		Parent = null;
		LeftChild = null;
		RightChild = null;
		Height = -1;
	}
}

// ASSUMPTIONS -
// There are duplicates here - all duplictes will be in the left subtree

class AVLtree_WordEntry
{
	AVLtreeNode_WordEntry root;

	public AVLtree_WordEntry ()
	{
		root = null;
	}

	public AVLtree_WordEntry (AVLtreeNode_WordEntry root)
	{
		this.root = root;
	}

	public boolean AVLtreeEmpty ()
	{return ( root.Height == -1 );}

	public boolean IsExternal (AVLtreeNode_WordEntry Node)
	{return (Node.Height == 0);}

	public boolean IsRoot (AVLtreeNode_WordEntry Node)
	{return (Node.Parent == null);}

	public Integer UpdateHeightTillNode (AVLtreeNode_WordEntry Node)
	{
		if (Node == null)
			return -1;

		Node.Height = Math.max(UpdateHeightTillNode(Node.LeftChild), UpdateHeightTillNode (Node.RightChild)) + 1;
		return (Node.Height);
	}

	public AVLtreeNode_WordEntry FindElement(Position position)
	{
		if (root.position.wordIndex == position.wordIndex && root.position.p.name.equals(position.p.name))
			return root;

		else if (root.position.wordIndex >= position.wordIndex)
		{
			if (root.LeftChild == null)
				return null;

			AVLtree_WordEntry LeftSubtree = new AVLtree_WordEntry (root.LeftChild);
			return (LeftSubtree.FindElement(position));
		}

		else
		{
			if (root.RightChild == null)
				return null;

			AVLtree_WordEntry RightSubtree = new AVLtree_WordEntry (root.RightChild);
			return (RightSubtree.FindElement(position));
		}
	}

	public AVLtreeNode_WordEntry GetParentForInsertion (Position position)
	{
		if (root.Height == 0)
			return root;

		else if (root.position.wordIndex >= position.wordIndex)
		{
			if (root.LeftChild == null)
				return root;

			AVLtree_WordEntry LeftSubtree = new AVLtree_WordEntry (root.LeftChild);
			return (LeftSubtree.GetParentForInsertion(position));
		}

		else
		{
			if (root.RightChild == null)
				return null;

			AVLtree_WordEntry RightSubtree = new AVLtree_WordEntry (root.RightChild);
			return (RightSubtree.GetParentForInsertion(position));
		}
	}

	public void insert (Position position)
	{
		AVLtreeNode_WordEntry NewNode = new AVLtreeNode_WordEntry(position);

		if (root == null)
		{
			root = NewNode;
			root.Height = UpdateHeightTillNode(root);
			return;
		}

		NewNode.Parent = this.GetParentForInsertion(NewNode.position);

		if (NewNode.position.wordIndex <= NewNode.Parent.position.wordIndex)
			NewNode.Parent.LeftChild = NewNode;

		else
			NewNode.Parent.RightChild = NewNode;

		root.Height = UpdateHeightTillNode(root);

		// Rotations - 4 cases

		AVLtreeNode_WordEntry z = NewNode;

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

	public void LeftOfLeftChild (AVLtreeNode_WordEntry z, AVLtreeNode_WordEntry y, AVLtreeNode_WordEntry x)
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

	public void RightofRightChild (AVLtreeNode_WordEntry z, AVLtreeNode_WordEntry y, AVLtreeNode_WordEntry x)
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

	public void RightOfLeftChild (AVLtreeNode_WordEntry z, AVLtreeNode_WordEntry y, AVLtreeNode_WordEntry x)
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

	public void LeftOfRightChild (AVLtreeNode_WordEntry z, AVLtreeNode_WordEntry y, AVLtreeNode_WordEntry x)
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

	public void InOrderTraversal(AVLtreeNode_WordEntry root)
	{
		if (root == null)
			return;

		InOrderTraversal (root.LeftChild);
		System.out.print(root.position.wordIndex + " in page " + root.position.p.name);
		InOrderTraversal (root.RightChild);
	}
}