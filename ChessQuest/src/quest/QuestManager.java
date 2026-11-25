package quest;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
	
	/**
	 * ChessGame과 소통해야함!
	 * 
	 * 이동, 포획, 체크 등의 퀘스트가 존재할텐데
	 * 무엇을 받아야하는가.
	 * 움직인 기물?
	 * 포획의 경우 Move를 받아도 알 수 있네
	 * 아니면 기물을 잡았냐 안잡았냐만 알 수 있으면 좋을듯
	 * 
	 * 프로모션 퀘스트
	 */
	
	private List<Quest> quests = new ArrayList<>();
	
	public void addQuest(Quest quest) {
		quests.add(quest);
	}
	
	public List<Quest> getQuests() {
		return quests;
	}
}
