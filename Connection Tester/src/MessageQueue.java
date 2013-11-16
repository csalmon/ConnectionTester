import java.util.concurrent.ConcurrentLinkedQueue;


public class MessageQueue {

	private ConcurrentLinkedQueue<Message> messages;
	
	public MessageQueue() {
		this.messages = new ConcurrentLinkedQueue<Message>();
	}
	
	public void enqueue(Message pMessage) {
		this.messages.add(pMessage);
	}
	
	public Message dequeue() {
		return(this.messages.poll());
	}
	
	public boolean isEmpty() {
		return(0 == messages.size());
	}
}
