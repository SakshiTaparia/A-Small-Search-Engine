class PageIndex
{
	public WordEntryLinkList WordListOfAPage;

	PageIndex ()
	{
		WordListOfAPage = new WordEntryLinkList();
	}

	public void addPositionForWord(String str, Position p)
	{

// If the WordEntry does not exist, then create a new one
		if (WordListOfAPage.GetMemberWord(str) == null)

		{
			WordEntry Word = new WordEntry(str);
		    Word.addPosition(p);
		    WordListOfAPage.InsertRear(Word);
		}

// If the word is repeated, then upate the existing word entry corresponding to it
		else 
			WordListOfAPage.GetMemberWord(str).addPosition(p);

		return;
	}

	public WordEntryLinkList getWordEntries()
	{
		return WordListOfAPage;
	}

//
	public double NumberOfDisctinctWords()
	{
		MyLinkedListNode <WordEntry> pointer = WordListOfAPage.gethead();
		double count = 0.0;

		while (pointer != null)
		{
			count ++;
			pointer = pointer.getnext();
		}

		return count;
	}
}
