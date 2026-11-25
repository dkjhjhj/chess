package quest;

public abstract class Quest {
	protected String title;
	protected String description;
	
	protected int target;
	protected int progress;
	
	public Quest(String title, String description, int target) {
		this.title = title;
		this.description = description;
		this.target = target;
		this.progress = 0;
	}
	
	/**
	 * 퀘스트 종류
	 */
	
	public abstract void update();
	
	public boolean isCompleted() {
		return target <= progress;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getTarget() {
		return target;
	}
	
	public int getProgress() {
		return progress;
	}
}
