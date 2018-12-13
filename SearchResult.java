class SearchResult
{
	PageEntry page;
	double relevance;

	public SearchResult (PageEntry p, double r)
	{
		page = p;
		relevance = r;
	}

    public PageEntry getPageEntry()
    {
    	return page;
    }

    public double getRelevance() 
    {
    	return relevance;
    }

    public Integer compareTo(SearchResult otherObject)
    {
    	if (otherObject.getRelevance() < relevance)
    		return 1;

    	else if (otherObject.getRelevance() > relevance)
    		return -1;

    	else 
    		return 0;
    }
}
