import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

class SearchEngine
{
	InvertedPageIndex MiniSearchEngine;

	SearchEngine()
	{
		MiniSearchEngine = new InvertedPageIndex();
	}

	public void performAction(String actionMessage) 
	{

// Split the action message and call appropriate methods for each query
		String [] splitwords = actionMessage.split(" ");

		try
		{

		if (splitwords[0].equals("queryFindPositionsOfWordInAPage"))
			this.queryFindPositionsOfWordInAPage(splitwords[1], splitwords[2]);

		else if (splitwords[0].equals("addPage"))
			this.addPage(splitwords[1]);

		else if (splitwords[0].equals("queryFindPagesWhichContainWord"))
			this.queryFindPagesWhichContainWord(splitwords[1]);

		else if (splitwords[0].equals("queryFindPagesWhichContainAllWords"))
		{
			String[] words = Arrays.copyOfRange(splitwords, 1, splitwords.length);
			this.queryFindPagesWhichContainAllWords(words);
		}

		else if (splitwords[0].equals("queryFindPagesWhichContainAnyOfTheseWords"))
		{
			String[] words = Arrays.copyOfRange(splitwords, 1, splitwords.length);
			this.queryFindPagesWhichContainAnyOfTheseWords(words);
		}

		else if (splitwords[0].equals("queryFindPagesWhichContainPhrase"))
		{
			String[] words = Arrays.copyOfRange(splitwords, 1, splitwords.length);
			this.queryFindPagesWhichContainPhrase(words);
		}

		else
			System.out.println("Query message not identified");
		}
		
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
// try and catch the expections in all the methods called.

	}

	public void queryFindPositionsOfWordInAPage (String Word, String DocumentName)
	{
		String word = Word;
		word = word.toLowerCase();

		if (word.equals("structures"))
			word = "structure";
		if (word.equals("stacks"))
			word = "stack";
		if (word.equals("applications"))
			word = "application";

		MyLinkedListNode <PageEntry> pointer = MiniSearchEngine.ListOfPages.set.gethead();

		while (pointer != null)
		{
			if (pointer.getelement().name.equals(DocumentName))
				break;
			else
				pointer = pointer.getnext();
		}

		if (pointer == null)
		{
			System.out.println("No webpage "+ DocumentName + " found");
			return;
		}

		else 
		{
			WordEntry strEntry = pointer.getelement().getPageIndex().WordListOfAPage.GetMemberWord(word);

			if (strEntry == null)
		{
			System.out.println("Webpage " + DocumentName + " does not contain word " + word);
			return;
		}			

		MyLinkedListNode<Position> head = strEntry.PositionList.gethead();

		String answer = " ";

			while (head != null && head.getelement().getPageEntry().name.equals(DocumentName))
			{
				answer = answer + head.getelement().getWordIndex() + ", ";
				head = head.getnext();
			}

			answer = answer.trim();
			answer = answer.substring(0, answer.length() - 1);

			System.out.println(answer);
		}

		return;
	}

	public void queryFindPagesWhichContainWord (String Word)
	{
		String word = Word.toLowerCase();

		if (word.equals("structures"))
			word = "structure";
		if (word.equals("stacks"))
			word = "stack";
		if (word.equals("applications"))
			word = "application";

		if (MiniSearchEngine.getPagesWhichContainWord(word) == null)
		{
			System.out.println("No webpage contains word " + Word);
			return;
		}

		MyLinkedListNode <String> iterator = MiniSearchEngine.getPagesWhichContainWord(word).set.gethead();
		
		while (iterator.getnext() != null)
		{
			System.out.print( iterator.getelement() + ", ");
			iterator = iterator.getnext();
		}

			System.out.println(iterator.getelement());

		return;
	}

	public void addPage (String WebPageName) throws Exception
	{
		PageEntry p = new PageEntry(WebPageName);
		MiniSearchEngine.addPage(p);	

		return;
	}

	public void queryFindPagesWhichContainAllWords( String[] queryWords )
	{
		String[] QueryWords = new String[queryWords.length];

		for (Integer i=0; i<queryWords.length; i++)
		{
			QueryWords[i] = queryWords[i].toLowerCase();

			if (QueryWords[i].equals("structures"))
			QueryWords[i] = "structure";
		    if (QueryWords[i].equals("stacks"))
			QueryWords[i] = "stack";
		    if (QueryWords[i].equals("applications"))
			QueryWords[i] = "application";
		}

		Myset<PageEntry> PageSet = MiniSearchEngine.getPagesWhichContainAllWords(QueryWords);

		if (PageSet.IsEmpty())
		{
			System.out.println("\n No webpage contains all the words");
			return;
		}

		Myset<SearchResult> PageWithRelevance = new Myset<SearchResult>();

		MyLinkedListNode <PageEntry> head = PageSet.set.gethead();

		while (head != null)
		{
			SearchResult s = new SearchResult( head.getelement(), head.getelement().getRelevanceOfPage(QueryWords, false, MiniSearchEngine));
			PageWithRelevance.AddElement(s);
			head = head.getnext();
		}

		MySort sort = new MySort();

		SearchResult[] answer = sort.sortThisList(PageWithRelevance);
		
		for (Integer i=0; i < answer.length-1; i++)
			System.out.print(answer[i].page.name + "(" + String.format("%.4f", answer[i].relevance) + "), ");

		System.out.print(answer[answer.length -1].page.name + "(" + String.format("%.4f", answer[answer.length -1].relevance) +")" );

		return;
	}

	public void queryFindPagesWhichContainAnyOfTheseWords( String[] queryWords )
	{
		String[] QueryWords = new String[queryWords.length];

		for (Integer i=0; i<queryWords.length; i++)
		{
			QueryWords[i] = queryWords[i].toLowerCase();

			if (QueryWords[i].equals("structures"))
			QueryWords[i] = "structure";
		    if (QueryWords[i].equals("stacks"))
			QueryWords[i] = "stack";
		    if (QueryWords[i].equals("applications"))
			QueryWords[i] = "application";
		}

		Myset<PageEntry> PageSet = MiniSearchEngine.getPagesWhichContainAnyWord(QueryWords);

		if (PageSet.IsEmpty())
		{
			System.out.println("\n No webpage contains any of the words");
			return;
		}

		Myset<SearchResult> PageWithRelevance = new Myset<SearchResult>();

		MyLinkedListNode <PageEntry> head = PageSet.set.gethead();

		while (head != null)
		{
			SearchResult s = new SearchResult( head.getelement(), head.getelement().getRelevanceOfPage(QueryWords, false, MiniSearchEngine));
			PageWithRelevance.AddElement(s);
			head = head.getnext();
		}

		MySort sort = new MySort();

		SearchResult[] answer = sort.sortThisList(PageWithRelevance);
		
		for (Integer i=0; i < answer.length-1; i++)
			System.out.print(answer[i].page.name + "(" + String.format("%.4f", answer[i].relevance) + "), ");

		System.out.print(answer[answer.length -1].page.name + "(" + String.format("%.4f", answer[answer.length -1].relevance) + ")" );

		return;
	}

	public void queryFindPagesWhichContainPhrase( String[] queryWords )
	{
		String[] QueryWords = new String[queryWords.length];

		for (Integer i=0; i < queryWords.length; i++)
		{
			QueryWords[i] = queryWords[i].toLowerCase();

			if (QueryWords[i].equals("structures"))
			QueryWords[i] = "structure";
		    if (QueryWords[i].equals("stacks"))
			QueryWords[i] = "stack";
		    if (QueryWords[i].equals("applications"))
			QueryWords[i] = "application";
		}

		Myset<PageEntry> PageSet = MiniSearchEngine.getPagesWhichContainPhrase(QueryWords);

		if (PageSet.IsEmpty())
		{
			System.out.println("\n No webpage contains the phrase");
			return;
		}

		Myset<SearchResult> PageWithRelevance = new Myset<SearchResult>();

		MyLinkedListNode <PageEntry> head = PageSet.set.gethead();

		while (head != null)
		{
			SearchResult s = new SearchResult( head.getelement(), head.getelement().getRelevanceOfPage(QueryWords, true, MiniSearchEngine));
			PageWithRelevance.AddElement(s);
			head = head.getnext();
		}


		MySort sort = new MySort();

		SearchResult[] answer = sort.sortThisList(PageWithRelevance);

		for (Integer i=0; i < answer.length-1; i++)
			System.out.print(answer[i].page.name + "(" + String.format("%.4f", answer[i].relevance) + "), ");

		System.out.print(answer[answer.length -1].page.name + "(" + String.format("%.4f", answer[answer.length -1].relevance) + ")" );

		return;
	}
}
