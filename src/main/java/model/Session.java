package model;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Session {
	private static final long TTL = 5 * 1000;
	private Map<String, Long> sessions = new HashMap<>();
	private ArrayDeque<String> logQue = new ArrayDeque<>();

	public boolean check(String uid) {
		return sessions.containsKey(uid);
	}

	public void put(String uid) {
		logQue.addLast(uid);
		sessions.put(uid, System.currentTimeMillis());
	}

	// TTL 적용: 로그인 유효 시간(5초) 배치잡 스레드 구현할 예정
	public void update() {
		String uid = logQue.peekFirst();
		if ( System.currentTimeMillis() - sessions.get(uid) > TTL) {
			remove(uid);
		}
	}

	public void remove(String uid) {
		logQue.remove();
		sessions.remove(uid);
	}

	public List getAll() {
		List<String> output = sessions.keySet().stream()
			.collect(Collectors.toList());
		Collections.sort(output);
		return output;
	}

}
