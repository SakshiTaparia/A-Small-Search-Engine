class MyLinkedList <T>
{

// references for head and tail variables of the linked list
	public MyLinkedListNode <T> head;
	public MyLinkedListNode <T> tail;

	public MyLinkedList () {head = null; tail = null;}

	public MyLinkedListNode <T> gethead() 
	{return head;}

	public MyLinkedListNode <T> gettail() 
	{return tail;}

	public void sethead (MyLinkedListNode <T> h) 
	{head = h;}

	public void settail (MyLinkedListNode <T> t) 
	{tail = t;}

	public boolean isEmpty() 
	{return tail == null;} 

	public boolean isMember (T e)
	{
		MyLinkedListNode <T> temp = head;

		while(temp != null) 
			{if (temp.getelement() == e) return true; temp = temp.getnext();}

		return false;	
	}

	public void InsertFront (T e) 
	{
		head = new MyLinkedListNode <T> (e, head);

// If the list is initially empty
		if (tail == null)
			tail = head;
	}

	public void InsertRear(T a)
	{
		MyLinkedListNode <T> node = new MyLinkedListNode <T> (a, null);

// If the list is initially empty
		if (tail == null) 
			{tail= node; head = node; return;}

		else 
			{tail.setnext(node); tail = node; return;}
	}

	public void Delete (T e)
	{
		if (this.isMember(e) == false) return;

// When the list has a single node
		if (head == tail) 
			{head = null; tail = null; return;} 

// When the head itself needs to be deleted
		if (head.getelement() == e) 
			{head = head.getnext(); return;}

// When a general node or tail node has to be deleted
		MyLinkedListNode <T> temp = head;
		while (temp.getnext().getelement() != e) 
			temp = temp.getnext();

		temp.setnext(temp.getnext().getnext());
		if (temp.getnext() == null) 
			tail = temp;

		return;
	}
}
