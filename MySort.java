class MySort
{

	public SearchResult[] sortThisList(Myset <SearchResult> listOfSortableEntries)
	{
		MyLinkedListNode<SearchResult> head = listOfSortableEntries.set.gethead();

		Integer n = 0;

		while (head != null)
		{
			n ++;
			head = head.getnext();
		}

		SearchResult[] Answerlist = new SearchResult[n];

		MyLinkedListNode<SearchResult> pointer = listOfSortableEntries.set.gethead();

		for (Integer i =0; i<n; i++)
		{
			Answerlist[i] = pointer.getelement();
			pointer = pointer.getnext();
		}

		SearchResult temp;

		for (Integer i = 0; i<n; i++)
		{
			for (Integer j=1; j<n-1; j++)
			{
				if ( Answerlist[j].compareTo(Answerlist[j+1]) == -1)
				{
					temp = Answerlist[j];
					Answerlist[j] = Answerlist[j+1];
					Answerlist[j+1] = temp;
				}
			}
		}

		return Answerlist;
	}
}