import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;


class InvertedPageIndex
{
	public Myset <PageEntry> ListOfPages;
	public double NumberOfPages;
	public MyHashTable AllWordsHashMap;

	InvertedPageIndex ()
	{
		ListOfPages = new Myset<PageEntry>();
		AllWordsHashMap = new MyHashTable();
	}

	public void addPage(PageEntry p)
	{
		ListOfPages.AddElement(p);

		MyLinkedListNode<WordEntry> head = p.index.WordListOfAPage.gethead();
		while (head != null)
		{
			AllWordsHashMap.addPositionsForWord(head.getelement());
			head = head.getnext();
		}

		NumberOfPages++;

		return;
	}

	public WordEntry GetWordEntryOfWord (String str)
	{
	return AllWordsHashMap.GetWordEntryOfWord(str);	
	}

	public Myset<String> getPagesWhichContainWord(String str)
	{
		Myset <String> temp = new Myset<String> ();

		if (AllWordsHashMap.HashMap[AllWordsHashMap.getHashIndex(str)].GetMemberWord(str) == null)
			return null;

		WordEntry strW = AllWordsHashMap.HashMap[AllWordsHashMap.getHashIndex(str)].GetMemberWord(str);

		MyLinkedListNode<Position> pointer = strW.PositionList.gethead();

		while(pointer != null)
		{
			if (temp.IsMember(pointer.getelement().p.name))
			{
				pointer = pointer.getnext();
			}

			else 
			{
				temp.AddElement(pointer.getelement().p.name);
				pointer = pointer.getnext();
			}
		}

		return temp;
	}

	public Myset<PageEntry> getPageEntryWhichContainWord(String str)
	{
		Myset <PageEntry> temp = new Myset<PageEntry> ();

		if (AllWordsHashMap.GetWordEntryOfWord(str) == null)
			return null;

		WordEntry strW = AllWordsHashMap.GetWordEntryOfWord(str);

		MyLinkedListNode<Position> pointer = strW.PositionList.gethead();

		while(pointer != null)
		{
			if (temp.IsMember(pointer.getelement().p))
			{
				pointer = pointer.getnext();
			}

			else 
			{
				temp.AddElement(pointer.getelement().p);
				pointer = pointer.getnext();
			}
		}

		return temp;
	}

	public double NumberOfPagesWhichContainWord(String str)
	{
		double answer = 0.0;
		Myset <String> temp = new Myset<String> ();

		if (AllWordsHashMap.GetWordEntryOfWord(str) == null)
			return answer;

		WordEntry strW = AllWordsHashMap.GetWordEntryOfWord(str);

		MyLinkedListNode<Position> pointer = strW.PositionList.gethead();

		while(pointer != null)
		{
			if (temp.IsMember(pointer.getelement().p.name))
			{
				pointer = pointer.getnext();
			}

			else 
			{
				temp.AddElement(pointer.getelement().p.name);
				pointer = pointer.getnext();
				answer ++;
			}
		}

		return answer;
	}

	public double NumberOfPagesWhichContainPhrase(String[] str)
	{
		double answer = 0.0;

		Myset<PageEntry> AnswerSet = this.getPagesWhichContainPhrase(str);
		
		MyLinkedListNode <PageEntry> head = AnswerSet.set.gethead();

		while (head != null)
		{
			answer ++;
			head = head.getnext();
		}

		return answer;
	}

	public double InverseDocumentFrequency(String str)
	{
		double answer = Math.log(NumberOfPages/ this.NumberOfPagesWhichContainWord(str));
		return answer;
	}

	public double InverseDocumentFrequencyForPhrase (String[] str)
	{
		double answer = Math.log(NumberOfPages/ this.NumberOfPagesWhichContainPhrase(str));
		return answer;
	}

	public Myset<PageEntry> getPagesWhichContainPhrase(String[] str)
	{
		Myset<PageEntry> answer = new Myset<PageEntry>();

		answer = this.getPagesWhichContainAllWords(str);

		if (answer.IsEmpty())
			return answer;

		MyLinkedListNode <PageEntry> pointer = answer.set.gethead();

		while (pointer != null)
		{

			// checking if the words are together in every page one by one
			// saving all positions of the first word

			Myset <Position> PositionOfFirstWord = GetWordEntryOfWord(str[0]).GetPositionsInPage(pointer.getelement());

			MyLinkedListNode <Position> head = PositionOfFirstWord.set.gethead();

			Integer i = 0;
			Integer temp = 0;

			while (head != null)
			{

				AVLtreeNode_PageEntry FirstNode = pointer.getelement().PageAVLtree.FindElement(head.getelement().wordIndex); 

				for (i = 1; i < str.length; i++)
				{
					if (pointer.getelement().PageAVLtree.GetInOrderSuccessor(FirstNode) == null)
						break;

					if ( pointer.getelement().PageAVLtree.GetInOrderSuccessor(FirstNode).word .equals(str[i]))
						FirstNode = pointer.getelement().PageAVLtree.GetInOrderSuccessor(FirstNode);

					else 
						break;
				}

				if (i == str.length)
				{
					temp ++;
					break;
				}

				head = head.getnext();
			}

			if (temp == 0)
			{
				answer.DeleteElement(pointer.getelement());
			}

			pointer = pointer.getnext();
		}

		return answer;
	}

	public Myset<PageEntry> getPagesWhichContainAllWords(String[] str)
	{
		Myset<PageEntry> answer = new Myset<PageEntry>();

		if (getPageEntryWhichContainWord(str[0]) == null)
			return answer;

		answer = getPageEntryWhichContainWord(str[0]);

		for (Integer i=1; i < str.length; i++)
		{
			answer = answer.Intersection(getPageEntryWhichContainWord(str[i]));
		}
		return answer;
	}

	public Myset<PageEntry> getPagesWhichContainAnyWord(String[] str)
	{
		Myset<PageEntry> answer = new Myset<PageEntry>();

		if (getPageEntryWhichContainWord(str[0]) != null)
		answer = getPageEntryWhichContainWord(str[0]);

		for (Integer i=1; i < str.length; i++)
			answer = answer.Union(getPageEntryWhichContainWord(str[i]));

		return answer;
	}
}

