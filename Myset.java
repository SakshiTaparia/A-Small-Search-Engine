class Myset <T>
{
	public MyLinkedList <T> set = new MyLinkedList<T>();
	public Myset () {}

	public boolean IsEmpty () 
	{return set.isEmpty();}

	public Boolean IsMember(T o) 
	{return set.isMember(o);}

	public void AddElement(T element)
	{
        
// If the member already exists, it will not be added again in the set
		if (set.isMember(element) == true) return;
		else set.InsertRear(element);
	}

	public void DeleteElement(T element)
	{
		if (set.isMember(element)) {set.Delete(element);}
	}

    public Myset<T> Union(Myset<T> otherSet)
    {
    	Myset <T> union = new Myset<T>();

        if ( otherSet != null)
    	union = otherSet;

    	MyLinkedListNode <T> pointer = set.gethead();
    	while (pointer != null) 
    		{union.AddElement(pointer.getelement()); pointer = pointer.getnext();}

    	return union;
    }

    public Myset<T> Intersection(Myset<T> otherSet)
    {
        
    	Myset <T> intersection = new Myset<T>();

        if (otherSet == null)
            return intersection;

    	MyLinkedListNode <T> pointer = set.gethead();
    	while (pointer != null) 
    		{
    			if (otherSet.IsMember(pointer.getelement())) 
    			intersection.AddElement(pointer.getelement()); 
    			pointer = pointer.getnext();
    		}

    	return intersection;
    }
}
