class MyLinkedListNode <T>
{

// T is used for generic method declaration
	public T element;
	public MyLinkedListNode <T> next;

	public MyLinkedListNode ( T element, MyLinkedListNode <T> next)
	{
		this.element = element;
		this.next = next;
	}

// Getter and setter functions
	public T getelement () 
	{return element;}

	public MyLinkedListNode <T> getnext () 
	{return next;}

	public void setelement ( T e ) 
	{ element =e;}

	public void setnext ( MyLinkedListNode <T> n ) 
	{ next =n;}
}