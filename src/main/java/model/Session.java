package model;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Cookie 를 사용하는 요청을 인증
public class Session {
  private static final long TTL = 5 * 1000;
  private Map<String, Long> sessions = new HashMap<>();
  private ArrayDeque<String> logQue = new ArrayDeque<>();

  // 요청을 보낸 사용자가 인증목록에 있는지 확인
  public boolean check(String uid) {
    return sessions.containsKey(uid);
  }

  /*
    로그인한 사용자를 logQue, sessions 에 삽입
    logQue 는 FIFO 구조로, 맨 앞에 위치한(로그인한지 가장 오래된) uid 를 확인할 수 있음
    sessions 는 HashMap 으로, (uid, 마지막으로 인증된 시각) 쌍을 원소로 가짐
   */
  public void put(String uid) {
    logQue.addLast(uid);
    sessions.put(uid, System.currentTimeMillis());
  }

  // TTL 적용: 로그인 유효 시간(5초) 배치잡 스레드 구현할 예정
  public void update() {
    String uid = logQue.peekFirst();
    if (System.currentTimeMillis() - sessions.get(uid) > TTL) {
      remove(uid);
    }
  }

  public void remove(String uid) {
    logQue.remove();
    sessions.remove(uid);
  }

  // 접근권한이 있는 모든 사용자를 반환
  public List getAll() {
    List<String> output = sessions.keySet().stream().collect(Collectors.toList());
    Collections.sort(output);
    return output;
  }

}
