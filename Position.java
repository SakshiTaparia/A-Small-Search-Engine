class Position
{
	public PageEntry p;
	public Integer wordIndex;

	Position (PageEntry p, Integer wordIndex)
	{
		this.p = p;
		this.wordIndex = wordIndex;
	}

// Getter functions for the variables
	PageEntry getPageEntry() {return p;}

    Integer getWordIndex() {return wordIndex;}
} 