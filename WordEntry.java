import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

// also need to accomodate duplicates
// implement using AVL trees 
// Also make positon AVL trees

class WordEntry
{
	public String word;
	public MyLinkedList <Position> PositionList = new MyLinkedList<Position>(); 
	public AVLtree_WordEntry PositionListTree = new AVLtree_WordEntry();

// Constructor
	WordEntry(String word)
	{
		this.word = word;
		
		PositionList.sethead(null);
		PositionList.settail(null);
	}

// Adding a position entry for a given word.
	public void addPosition(Position position)
	{
			PositionList.InsertRear(position);
			// PositionListTree.insert(position);
			return;
	}


// Add multiple position entries for the word
	public void addPositions(MyLinkedList<Position> positions)
	{
		/*MyLinkedList<Position> Duplicate = positions;

		MyLinkedListNode <Position> pointer = Duplicate.gethead();

		while (pointer != null)
		{
			PositionListTree.insert(pointer.getelement());
			pointer = pointer.getnext();
		}*/

		PositionList.gettail().setnext(positions.gethead());
		PositionList.settail(positions.gettail());

		return;
	}

// Returns a linked list of all position entries for the word corresponding to the word entry
	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		return PositionList;
	}

	public AVLtree_WordEntry getAllPositionsForThisWord_tree()
	{
		return PositionListTree;
	}

// Given a word and a page, this method gives a set of all locations of the word in that page
	/*public Myset <Position> GetPositionsInPage_tree (PageEntry pageentry, AVLtree_WordEntry tree)
	{
		Myset <Position> answer = new Myset <Position>();

		if (tree.root == null)
			return answer;

		if (tree.root.position.p.name.equals(pageentry.name))
			answer.AddElement(tree.root.position);

		AVLtree_WordEntry LeftTree = new AVLtree_WordEntry(tree.root.LeftChild);
		answer = answer.Union( GetPositionsInPage_tree(pageentry, LeftTree));

		AVLtree_WordEntry RightTree = new AVLtree_WordEntry(tree.root.RightChild);
		answer = answer.Union( GetPositionsInPage_tree(pageentry, RightTree));

		return answer;
	}*/

	public Myset <Position> GetPositionsInPage(PageEntry pageentry)
	{
		Myset <Position> answer = new Myset <Position>();

		MyLinkedListNode <Position> pointer = PositionList.gethead();

		while (pointer != null)
		{
			if (pointer.getelement().p.name == pageentry.name)
			answer.AddElement(pointer.getelement());

			pointer = pointer.getnext();
		}

		return answer;
	}

// used to calculate relevance
	public double getTermFrequency(PageEntry PageName)
	{
		MyLinkedListNode <Position> pointer = PositionList.gethead();
		double count = 0.0;

		while (pointer != null)
		{
			if (pointer.getelement().p.name == PageName.name)
			count ++;

			pointer = pointer.getnext();
		}

		if (count == 0.0)
			return count;

		else
			return (count/PageName.NumberOfWordsInPage());
	}
}
