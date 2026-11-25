package quest.type;

import quest.Quest;

public class KnightMoveQuest extends Quest {
	public KnightMoveQuest() {
		super("나이트 도약", "나이트를 3회 이동", 3);
	}
	
	@Override
	public void update() {
		return;
	}
}
