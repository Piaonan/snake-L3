package tools;

public interface IChatLogger {
	public void clientConnected(String ip);

	public void clientDisconnected(String ip);

	public void systemMessage(String msg);

	public void clientSnakeDie(String ip, int score);

	public void clientPlayAgain(String ip);

	public void clientDirection(String ip, Direction d);
	
	public void close();
}
