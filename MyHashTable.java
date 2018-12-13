import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

class MyHashTable
{
	public WordEntryLinkList[] HashMap = new WordEntryLinkList[1000];

	MyHashTable() 
	{
		for (Integer i =0; i<1000; i++)
		{
			HashMap[i] = new WordEntryLinkList();
		}
	}

	public Integer getHashIndex(String str)
	{
		str = str.toLowerCase();
		Integer value = 0;

		for (Integer i =0; i< str.length(); i++)
			value = value + str.charAt(i);

		return ( (value % 1000) );
	}

	public void addPositionsForWord(WordEntry w)
	{
		Integer hashindex = getHashIndex(w.word);

		WordEntry currentWordEntry = HashMap[hashindex].GetMemberWord(w.word);

		if (currentWordEntry == null)
		{
			HashMap[hashindex].InsertRear(w);
		}

		else 
		{
			currentWordEntry.addPositions(w.PositionList);
		}
	}

	public WordEntry GetWordEntryOfWord (String str)
	{
		Integer hashindex = getHashIndex(str);
		return (HashMap[hashindex].GetMemberWord(str));
	}
}