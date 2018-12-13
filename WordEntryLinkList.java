import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

class WordEntryLinkList extends MyLinkedList<WordEntry>

// Contains some additional methods with WordEntry object instead of generic object
{
	public MyLinkedListNode <WordEntry> head;
	public MyLinkedListNode <WordEntry> tail;

	public WordEntryLinkList () {head = null; tail = null;}

	public MyLinkedListNode <WordEntry> gethead() 
	{return head;}

	public MyLinkedListNode <WordEntry> gettail() 
	{return tail;}

	public void sethead (MyLinkedListNode <WordEntry> h) 
	{head = h;}

	public void settail (MyLinkedListNode <WordEntry> t) 
	{tail = t;}

	public boolean isEmpty() 
	{return tail == null;} 

	public boolean isMember (WordEntry e)
	{
		MyLinkedListNode <WordEntry> temp = head;

		while(temp != null) 
			{if (temp.getelement() == e) return true; temp = temp.getnext();}

		return false;	
	}

	public void InsertFront (WordEntry e) 
	{
		head = new MyLinkedListNode <WordEntry> (e, head);

// If the list is initially empty
		if (tail == null)
			tail = head;
	}

	public void InsertRear(WordEntry a)
	{
		MyLinkedListNode <WordEntry> node = new MyLinkedListNode <WordEntry> (a, null);

// If the list is initially empty
		if (tail == null) 
			{tail= node; head = node; return;}

		else 
			{tail.setnext(node); tail = node; return;}
	}

// This is the entra method of this class
// Given a name, this method returns the word entry associated with the name.
	public WordEntry GetMemberWord (String wordname)
	{
		MyLinkedListNode <WordEntry> temp = head;

		while (temp != null)
		{
			if( temp.getelement().word.equals(wordname) )
				return temp.getelement();

			else
				temp = temp.getnext();
		}

// Null is returned if no word entry exists for the input name
		return null;
	}
}
